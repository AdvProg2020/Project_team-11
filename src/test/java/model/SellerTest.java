package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class SellerTest {
    private final DataBase dataBase = new DataBase();
    private final Seller seller2 = new Seller("ali", "mohamadi", "ali@gmail.com",
            "09135467595", "mamad", "1234", "apple", 500);
    private final ProductInfo productInfo1 = new ProductInfo("A50", "sumsung", 200, seller2, 5);
    private final Product product1 = new Product("construction", productInfo1, "mobile",
            new HashMap<>(Map.of("RAM", "8", "Memory", "128GB", "network", "4.5G")), "very good");

    @Test
    public void classTest() {
        Seller seller = new Seller("ali",
                "mamad",
                "asd@gmail.com",
                "654321",
                "alijoon",
                "1234",
                "company",
                500);
        Assert.assertFalse(dataBase.getAllAccounts().isEmpty());
        seller.setWallet(200);
        Assert.assertEquals(200, seller.getWallet());
        SellLog sellLog = new SellLog(new Date(), 200, 50, product1, "ali",
                "mamad", "sending");
        seller.addSellHistory(sellLog);
        Assert.assertEquals(sellLog, seller.getSellHistory().get(0));
        Assert.assertEquals("company", seller.getCompanyName());
    }
}
