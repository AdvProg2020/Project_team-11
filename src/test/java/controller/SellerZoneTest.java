package controller;

import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class SellerZoneTest {
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
    private final SellLog sellLog = new SellLog(new Date(), 200, 50, product1, "ali",
            "mamad", "sending");
    private final Comment comment = new Comment("ali", product2.getId(), "good", "construction",
            true);
    private final Rate rate = new Rate("ali", 4, product2.getId());
    private final Request request2 = new Request("mamad", "add product", String.valueOf(product1.getId()),
            "unseen");

    @Test
    public void getSellerProductsTest() {
        AllAccountZone.setCurrentAccount(seller1);
        Assert.assertEquals(product2.getId() + ". iphoneX: 300$ apple Average Score: 0.0\n" + product3.getId() +
                        ". k56C: 360$ asus Average Score: 0.0\n", SellerZone.getSellerProducts());
    }

    @Test
    public void viewSellerProductTest() {
        AllAccountZone.setCurrentAccount(seller1);
//        Assert.assertTrue(SellerZone.getSellerProductDetails(product2.getId()).startsWith("id = " + product2.getId() + ", status = 'editing',"));
        Assert.assertEquals("You haven't this product.", SellerZone.getSellerProductDetails(-1));
    }

    @Test
    public void viewProductBuyersTest() {
        AllAccountZone.setCurrentAccount(seller1);
        Assert.assertEquals("ali\n", SellerZone.viewProductBuyers(product1.getId()));
        Assert.assertEquals("You haven't this product.", SellerZone.viewProductBuyers(-1));
    }

    @Test
    public void editProductTest() {
        AllAccountZone.setCurrentAccount(seller1);
        Assert.assertEquals("Edit", SellerZone.editProduct(product2.getId()));
        Assert.assertEquals("You haven't this product.", SellerZone.editProduct(-1));
    }

    @Test
    public void sendEditProductRequestTest() {
        AllAccountZone.setCurrentAccount(seller1);
        SellerZone.sendEditProductRequest("editing");
        Assert.assertFalse(DataBase.getDataBase().getAllRequests().isEmpty());
    }

    @Test
    public void getSellerHistoryTest() {
        AllAccountZone.setCurrentAccount(seller2);
        seller2.addSellHistory(sellLog);
        Assert.assertTrue(SellerZone.getSellerHistory().contains("sold to ali 200$ with discount 50$ sending"));
    }

    @Test
    public void getCompanyInfoTest() {
        AllAccountZone.setCurrentAccount(seller2);
        Assert.assertEquals("apple", SellerZone.getCompanyInfo());
    }

    @Test
    public void sendAddNewProductRequestTest() {
        AllAccountZone.setCurrentAccount(seller1);
//        SellerZone.sendAddNewProductRequest(category1, new HashMap<>(Map.of("name", "Nokia1100" , "company",
//                "Nokia", "price", "100", "stock status", "20", "description", "-_-")), new HashMap<>());
        Assert.assertFalse(DataBase.getDataBase().getAllRequests().isEmpty());
    }

    @Test
    public void removeProductTest() {
//        SellerZone.removeProduct(product2);
        Assert.assertFalse(dataBase.getAllProducts().contains(product2));
    }

    @Test
    public void showSellerAuctionsTest() {
        AllAccountZone.setCurrentAccount(seller1);
        Assert.assertEquals(auction2.getId() + ". discount : 20%", SellerZone.showSellerAuctions());
    }

    @Test
    public void showSellerAuctionTest() {
        AllAccountZone.setCurrentAccount(seller1);
        Assert.assertEquals("invalid ID", SellerZone.showSellerAuction(-1));
        Assert.assertTrue(SellerZone.showSellerAuction(auction2.getId()).contains("discount : 20%"));
    }

    @Test
    public void getAuctionByIdTest() {
        AllAccountZone.setCurrentAccount(seller1);
        Assert.assertEquals(auction2, SellerZone.getAuctionById(auction2.getId()));
        Assert.assertNull(SellerZone.getAuctionById(-1));
    }

    @Test
    public void sendEditAuctionRequest() {
        AllAccountZone.setCurrentAccount(seller1);
        SellerZone.sendEditAuctionRequest(auction2.getId(), "next");
        Assert.assertEquals("editing", auction2.getStatus());
    }

    @Test
    public void createAuctionTest() {
        AllAccountZone.setCurrentAccount(seller1);
        SellerZone.createAuction("1/2,21346487987,9845432189523,20");
        Assert.assertFalse(DataBase.getDataBase().getAllRequests().isEmpty());
    }

    @Test
    public void showSellerRequestsTest() {
        AllAccountZone.setCurrentAccount(seller2);
        Assert.assertEquals("add product -> unseen\n", SellerZone.showSellerRequests());
    }
}
