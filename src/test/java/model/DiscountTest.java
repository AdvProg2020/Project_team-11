package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DiscountTest {
    private final DataBase dataBase = new DataBase();

    @Test
    public void classTest() {
        Discount discount = new Discount("takhfif",
                new Date(123456),
                new Date(123456789),
                new long[]{25, 100},
                2,
                new ArrayList<>(Arrays.asList("ali", "mamad")));
        Assert.assertFalse(dataBase.getAllDiscounts().isEmpty());
        Assert.assertEquals("takhfif", discount.getCode());
        discount.setStartDate(new Date(1234567));
        Assert.assertEquals(1234567, discount.getStartDate().getTime());
        discount.setEndDate(new Date(123456789));
        Assert.assertEquals(123456789, discount.getEndDate().getTime());
        discount.setDiscountPercent(40);
        Assert.assertEquals(40, discount.getAmount()[0]);
        discount.setMaxDiscount(200);
        Assert.assertEquals(200, discount.getAmount()[1]);
        discount.setRepeatedTimes(5);
        Assert.assertEquals(5, discount.getRepeatedTimes());
        discount.setAllowedUsers(new ArrayList<>(Arrays.asList("ali", "jafar")));
        Assert.assertEquals("jafar", discount.getAllowedUsers().get(1));
    }
}
