package model;

import org.junit.Assert;
import org.junit.Test;

public class RateTest {
    private final DataBase dataBase = new DataBase();

    @Test
    public void classTest() {
        Rate rate = new Rate("ali", 4, 12);
        Assert.assertFalse(dataBase.getAllRates().isEmpty());
        Assert.assertEquals("ali", rate.getBuyerName());
        Assert.assertEquals(4, rate.getScore());
        Assert.assertEquals(12, rate.getProductId());
    }
}
