package controller;

import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.*;

public class FileProcessTest {
    private final DataBase dataBase = new DataBase();
    private final Admin admin = new Admin("ali", "mohamadi", "ali@gmail.com",
            "09135467595", "alimamad", "1234");
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
    private final BuyLog buyLog = new BuyLog(new Date(), 250, 150,
            new HashMap<>(Map.of(1, "jafar")), "ali", "sending");
    private final SellLog sellLog = new SellLog(new Date(), 200, 50, product1, "ali",
            "mamad", "sending");

    @Test
    public void initializeTest() {
        FileProcess.initialize();
        Assert.assertTrue(new File("resources\\admins.json").exists());
    }

    @Test
    public void writeDataBaseOnFileTest() {
        FileProcess.writeDataBaseOnFile();
        Assert.assertTrue(new File("resources\\admins.json").exists());
    }
}
