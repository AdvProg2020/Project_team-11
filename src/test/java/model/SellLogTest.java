package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SellLogTest {
    private final DataBase dataBase = new DataBase();
    private final Seller seller2 = new Seller("ali", "mohamadi", "ali@gmail.com",
            "09135467595", "mamad", "1234", "apple", 500);
    private final ProductInfo productInfo1 = new ProductInfo("A50", "sumsung", 200, seller2, 5);
    private final Product product1 = new Product("construction", productInfo1, "mobile",
            new HashMap<>(Map.of("RAM", "8", "Memory", "128GB", "network", "4.5G")), "very good");

    @Test
    public void classTest() {
        ExchangeLog.setNumOfAllLogs(1);
        SellLog sellLog = new SellLog(new Date(123456),
                400,
                100,
                product1,
                "jafar",
                "mamad",
                "sending");
        Assert.assertEquals(123456, sellLog.getDate().getTime());
        Assert.assertEquals(400, sellLog.getReceivedAmount());
        Assert.assertEquals(100, sellLog.getReducedAmountForAuction());
        Assert.assertEquals(product1, sellLog.getSoldProducts());
        Assert.assertEquals("jafar", sellLog.getBuyerName());
        Assert.assertEquals("mamad", sellLog.getSellerUsername());
        Assert.assertEquals("sending", sellLog.getSendingStatus());
        Assert.assertEquals(sellLog, ExchangeLog.getLogById(1));
        Assert.assertNull(ExchangeLog.getLogById(-1));
    }
}
