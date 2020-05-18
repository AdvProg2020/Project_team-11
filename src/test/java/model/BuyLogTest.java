package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BuyLogTest {
    private final DataBase dataBase = new DataBase();

    @Test
    public void classTest() {
        BuyLog buyLog = new BuyLog(new Date(),
                450,
                150,
                new HashMap<>(Map.of(1, "ali")),
                "mamad",
                "sending");
        Assert.assertFalse(dataBase.getAllLogs().isEmpty());
        Assert.assertEquals(0, buyLog.getId());
        Assert.assertTrue(new Date().getTime() - buyLog.getDate().getTime() < 10);
        Assert.assertEquals(450, buyLog.getPaidAmount());
        Assert.assertEquals(150, buyLog.getDiscountAmountApplied());
        Assert.assertEquals("mamad", buyLog.getBuyerUsername());
        Assert.assertEquals("sending", buyLog.getDeliveryStatus());
        Assert.assertEquals("ali", buyLog.getPurchasedProductionsAndSellers().get(1));
    }
}
