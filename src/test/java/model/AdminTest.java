package model;

import org.junit.Assert;
import org.junit.Test;

public class AdminTest {
    private final DataBase dataBase = new DataBase();

    @Test
    public void classTest() {
        Admin admin = new Admin("ali",
                "jafari",
                "ali@gmail.com",
                "09135498",
                "alij",
                "1234");
        Assert.assertFalse(dataBase.getAllAccounts().isEmpty());
        Assert.assertEquals("ali", admin.getFirstName());
        Assert.assertEquals("jafari", admin.getLastName());
        Assert.assertEquals("ali@gmail.com", admin.getEmailAddress());
        Assert.assertEquals("09135498", admin.getPhoneNumber());
        Assert.assertEquals("alij", admin.getUsername());
        Assert.assertEquals("1234", admin.getPassword());
        admin.setFirstName("mamad");
        Assert.assertEquals("mamad", admin.getFirstName());
        admin.setLastName("rahimi");
        Assert.assertEquals("rahimi", admin.getLastName());
        admin.setEmailAddress("mamad@gmail.com");
        Assert.assertEquals("mamad@gmail.com", admin.getEmailAddress());
        admin.setPhoneNumber("035312497");
        Assert.assertEquals("035312497", admin.getPhoneNumber());
        admin.setUsername("mamadr");
        Assert.assertEquals("mamadr", admin.getUsername());
        admin.setPassword("4321");
        Assert.assertEquals("4321", admin.getPassword());
    }
}
