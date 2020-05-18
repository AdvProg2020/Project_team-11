package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class AuctionTest {
    private final DataBase dataBase = new DataBase();
    private final Seller seller2 = new Seller("ali", "mohamadi", "ali@gmail.com",
            "09135467595", "mamad", "1234", "apple", 500);
    private final Category category2 = new Category("mobile", new ArrayList<>(Arrays.asList("RAM", "Memory", "network")));
    private final ProductInfo productInfo1 = new ProductInfo("A50", "sumsung", 200, seller2, 5);
    private final Product product1 = new Product("construction", productInfo1, "mobile",
            new HashMap<>(Map.of("RAM", "8", "Memory", "128GB", "network", "4.5G")), "very good");

    @Test
    public void classTest() {
        Auction auction = new Auction(new ArrayList<>(Collections.singleton(product1)), "accepted", new Date(),
                new Date(new Date().getTime() + 1000000000), 40, "mamad");
        Assert.assertFalse(dataBase.getAllAuctions().isEmpty());
        Assert.assertEquals(0, auction.getId());
        Assert.assertEquals("accepted", auction.getStatus());
        Assert.assertEquals("mamad", auction.getSellerName());
        Assert.assertEquals(40, auction.getDiscountAmount());
        Assert.assertTrue(auction.getProductList().contains(product1));
        Assert.assertTrue(new Date().getTime() - auction.getStartDate().getTime() < 100);
        Assert.assertTrue(new Date(new Date().getTime() + 1000000000).getTime() - auction.getEndDate().getTime() < 100);
        auction.setProductList(new ArrayList<>());
        Assert.assertTrue(auction.getProductList().isEmpty());
        auction.setEndDate(new Date());
        Assert.assertTrue(new Date().getTime() - auction.getEndDate().getTime() < 100);
        auction.setStartDate(new Date(1000));
        Assert.assertEquals(1000, auction.getStartDate().getTime());
        auction.setStatus("editing");
        Assert.assertEquals("editing", auction.getStatus());
        auction.setDiscountAmount(50);
        Assert.assertEquals(50, auction.getDiscountAmount());
        Auction.setNumOfAllAuctions(10);
    }
}
