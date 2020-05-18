package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class BuyerTest {
    private final DataBase dataBase = new DataBase();
    private final Seller seller1 = new Seller("jafar", "mohamadi", "ali@gmail.com",
            "09135467595", "jafar", "1234", "nobody" , 500);
    private final Category category1 = new Category("PC", new ArrayList<>(Arrays.asList("RAM", "Memory", "CPU")));
    private final ProductInfo productInfo2 = new ProductInfo("iphoneX", "apple", 300, seller1, 10);
    private final Product product2 = new Product("editing", productInfo2, "mobile",
            new HashMap<>(Map.of("RAM", "16", "Memory", "256GB", "network", "4.5G")), "expensive");
    private final ProductInfo productInfo3 = new ProductInfo("k56C", "asus", 360, seller1, 1);
    private final Discount discount1 = new Discount("big discount", new Date(), new Date(new Date().getTime() + 1000000000),
            new long[]{25, 150}, 1, new ArrayList<>(Collections.singletonList("ali")));
    private final BuyLog buyLog = new BuyLog(new Date(), 250, 150,
            new HashMap<>(Map.of(1, "jafar")), "ali", "sending");

    @Test
    public void classTest() {
        Buyer buyer = new Buyer("ali",
                                "mohammadi",
                                "ali@yahoo.com",
                                "1235678",
                                "alimamad",
                                "1234",
                                500);
        Assert.assertFalse(dataBase.getAllAccounts().isEmpty());
        buyer.setWallet(100);
        Assert.assertEquals(100, buyer.getWallet());
        buyer.setActiveDiscount(discount1);
        Assert.assertEquals(discount1, buyer.getActiveDiscount());
        buyer.setCart(product2);
        Assert.assertEquals(new HashMap<>(Map.of(product2, 1)), buyer.getCart());
        buyer.addDiscountCodes(discount1, 1);
        Assert.assertFalse(buyer.getDiscountCodes().isEmpty());
        buyer.addBuyHistory(buyLog);
        Assert.assertEquals(buyLog, buyer.getBuyHistory().get(0));
        buyer.decreaseDiscountCode(discount1);
        Assert.assertEquals("0", buyer.getDiscountCodes().get(discount1.getCode()).toString());
        Assert.assertEquals("Buyer{firstName='ali', lastName='mohammadi', emailAddress='ali@yahoo.com'," +
                " phoneNumber=1235678}", buyer.toString());
    }
}
