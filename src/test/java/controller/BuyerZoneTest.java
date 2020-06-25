package controller;

import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class BuyerZoneTest {
    private final DataBase dataBase = new DataBase();
    private final Buyer buyer = new Buyer("ali", "mohamadi", "ali@gmail.com",
            "09135467595", "ali", "1234", 500);
    private final Seller seller1 = new Seller("jafar", "mohamadi", "ali@gmail.com",
            "09135467595", "jafar", "1234", "nobody" , 500);
    private final Seller seller2 = new Seller("ali", "mohamadi", "ali@gmail.com",
            "09135467595", "mamad", "1234", "apple", 500);
    private final Category category1 = new Category("PC", new ArrayList<>(Arrays.asList("RAM", "Memory", "CPU")));
    private final Category category2 = new Category("mobile", new ArrayList<>(Arrays.asList("RAM", "Memory", "network")));
    private final ProductInfo productInfo1 = new ProductInfo("A50", "sumsung", 200, seller2, 5);
    private final Product product1 = new Product("construction", productInfo1, "mobile",
            new HashMap<>(Map.of("RAM", "8", "Memory", "128GB", "network", "4.5G")), "very good");
    private final ProductInfo productInfo2 = new ProductInfo("iphoneX", "apple", 300, seller1, 10);
    private final Product product2 = new Product("editing", productInfo2, "mobile",
            new HashMap<>(Map.of("RAM", "16", "Memory", "256GB", "network", "4.5G")), "expensive");
    private final ProductInfo productInfo3 = new ProductInfo("k56C", "asus", 360, seller1, 1);
    private final Product product3 = new Product("accepted", productInfo3, "PC",
            new HashMap<>(Map.of("Memory", "2TB", "CPU", "corei7", "RAM", "16")), "hulu");
    private final Auction auction1 = new Auction(new ArrayList<>(Collections.singleton(product1)), "accepted", new Date(),
            new Date(new Date().getTime() + 1000000000), 40, "mamad");
    private final Auction auction2 = new Auction(new ArrayList<>(Collections.singleton(product2)), "accepted", new Date(),
            new Date(new Date().getTime() + 1000000000), 20, "jafar");
    private final BuyLog buyLog = new BuyLog(new Date(), 250, 150,
            new HashMap<>(Map.of(1, "jafar")), "ali", "sending");
    private final Comment comment = new Comment("ali", product2.getId(), "good", "construction",
            true);
    private final Rate rate = new Rate("ali", 4, product2.getId());
    private final Discount discount1 = new Discount("big discount", new Date(), new Date(new Date().getTime() + 1000000000),
            new long[]{25, 150}, 1, new ArrayList<>(Collections.singletonList("ali")));
    private final Discount discount2 = new Discount("small discount", new Date(), new Date(new Date().getTime() + 1000000000),
            new long[]{25, 150}, 3, new ArrayList<>());
    private final Discount discount3 = new Discount("another discount", new Date(), new Date(new Date().getTime() + 1000000000),
            new long[]{25, 150}, 1, new ArrayList<>(Collections.singletonList("ali")));
    private final Discount discount4 = new Discount("not started", new Date(new Date().getTime() + 100000000), new Date(new Date().getTime() + 1000000000),
            new long[]{25, 150}, 1, new ArrayList<>(Collections.singletonList("ali")));
    private final Discount discount5 = new Discount("expired", new Date(new Date().getTime() - 100000000), new Date(new Date().getTime() - 1000000),
            new long[]{25, 150}, 1, new ArrayList<>(Collections.singletonList("ali")));

    @Test
    public void showProductsInCartTest() {
        AllAccountZone.setCurrentAccount(buyer);
        buyer.setCart(product1);
        Assert.assertEquals(product1.getId() + ". A50 number: 1 200$ Brand : sumsung Average Score: 0.0\n",
                BuyerZone.getProductsInCart());
    }

    @Test
    public void changeNumberOFProductInCartTest() {
        AllAccountZone.setCurrentAccount(buyer);
        buyer.setCart(product1);
        Assert.assertEquals("You haven't this product", BuyerZone.changeNumberOFProductInCart(product2.getId(), 1));
        BuyerZone.changeNumberOFProductInCart(product1.getId(), -1);
        BuyerZone.removeProductFromCart();
        Assert.assertTrue(buyer.getCart().isEmpty());
    }

    @Test
    public void checkDiscountCodeTest() {
        AllAccountZone.setCurrentAccount(buyer);
        Assert.assertEquals("This code doesn't exist.", BuyerZone.checkDiscountCode("fake"));
        Assert.assertEquals("You can't use this code.", BuyerZone.checkDiscountCode(discount2.getCode()));
        buyer.addDiscountCodes(discount1, 1);
        Assert.assertEquals("Discount applied.", BuyerZone.checkDiscountCode(discount1.getCode()));
        buyer.addDiscountCodes(discount3, 0);
        Assert.assertEquals("You can't use this code anymore.", BuyerZone.checkDiscountCode(discount3.getCode()));
        buyer.addDiscountCodes(discount4, 1);
        Assert.assertTrue(BuyerZone.checkDiscountCode(discount4.getCode()).startsWith("You can't use this code until"));
        buyer.addDiscountCodes(discount5, 1);
        Assert.assertTrue(BuyerZone.checkDiscountCode(discount5.getCode()).startsWith("Code expired at"));
    }

    @Test
    public void canPayMoneyTest() {
        AllAccountZone.setCurrentAccount(buyer);
        buyer.setWallet(0);
        buyer.setCart(product1);
        buyer.setCart(product2);
        BuyerZone.changeNumberOFProductInCart(product1.getId(), 1);
        Assert.assertFalse(BuyerZone.canPayMoney());
        buyer.addDiscountCodes(discount1, 1);
        buyer.setActiveDiscount(discount1);
        Assert.assertFalse(BuyerZone.canPayMoney());
    }

    @Test
    public void payMoneyTest() {
        AllAccountZone.setCurrentAccount(buyer);
        buyer.setCart(product1);
        buyer.setCart(product2);
        buyer.addDiscountCodes(discount1, 3);
        buyer.setActiveDiscount(discount1);
        BuyerZone.payMoney();
        Assert.assertEquals(230, buyer.getWallet());
        Assert.assertEquals(620, seller2.getWallet());
        Assert.assertEquals(740, seller1.getWallet());
        Assert.assertNull(buyer.getActiveDiscount());
        Assert.assertEquals(4, DataBase.getDataBase().getAllLogs().size());
        Assert.assertTrue(buyer.getCart().isEmpty());
        Assert.assertEquals("invalid ID", BuyerZone.getOrderInfo(-2));
        Assert.assertTrue(BuyerZone.hasUserBoughtProduct(product1.getId()));
        Assert.assertFalse(BuyerZone.hasUserBoughtProduct(product3.getId()));
        BuyerZone.createRate(product1.getId(), 4);
        Assert.assertEquals(4, product1.getAverageScore(), 0.0);
        Assert.assertEquals(1, product1.getNumOfUsersRated());
    }

    @Test
    public void getOrdersTest() {
        AllAccountZone.setCurrentAccount(buyer);
        buyer.addBuyHistory(buyLog);
        Assert.assertTrue(BuyerZone.getOrders().startsWith(buyLog.getId() + "."));
        Assert.assertTrue(BuyerZone.getOrderInfo(buyLog.getId()).contains("seller : jafar\n250$ -> Discount = 150$\nsending"));
    }

    @Test
    public void getBuyerDiscountsTest() {
        AllAccountZone.setCurrentAccount(buyer);
        buyer.addDiscountCodes(discount1, 3);
        buyer.addDiscountCodes(discount3, 1);
//        Assert.assertTrue(BuyerZone.getBuyerDiscounts().startsWith("big discount : 3 times 25% discount Max = 150$ from "));
    }
}
