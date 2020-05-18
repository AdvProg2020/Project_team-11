package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class ProductTest {
    private final DataBase dataBase = new DataBase();
    private final Seller seller1 = new Seller("jafar", "mohamadi", "ali@gmail.com",
            "09135467595", "jafar", "1234", "nobody" , 500);

    @Test
    public void classTest() {
        Product.setNumOfAllProducts(1);
        ProductInfo productInfo = new ProductInfo("A50", "sumsung", 200, seller1, 5);
        Product product = new Product("editing", productInfo, "mobile", new HashMap<>(Map.of("ram", "8")), "...");
        Assert.assertFalse(dataBase.getAllProducts().isEmpty());
        Assert.assertEquals(1, product.getId());
        productInfo.setName("A70");
        Assert.assertEquals("A70", product.getGeneralFeature().getName());
        productInfo.setCompany("Sumsung");
        Assert.assertEquals("Sumsung", product.getGeneralFeature().getCompany());
        productInfo.setPrice(250);
        Assert.assertEquals(250, product.getGeneralFeature().getPrice());
        productInfo.setAuctionPrice(200);
        Assert.assertEquals(200, product.getGeneralFeature().getAuctionPrice());
        Assert.assertEquals(seller1, product.getGeneralFeature().getSeller());
        productInfo.setStockStatus(10);
        Assert.assertEquals(10, product.getGeneralFeature().getStockStatus());
        product.setStatus("accepted");
        Assert.assertEquals("accepted", product.getStatus());
        product.setDescription("good");
        Assert.assertEquals("good", product.getDescription());
        product.setCategoryName("Mobile");
        Assert.assertEquals("Mobile", product.getCategoryName());
        product.setAverageScore(5);
        Assert.assertEquals(5, product.getAverageScore(), 0.0);
        product.setCategoryFeature(new HashMap<>(Map.of("ram", "6")));
        Assert.assertEquals("6", product.getCategoryFeature().get("ram"));
        product.addNumOfUsersRated();
        Assert.assertEquals(1, product.getNumOfUsersRated());
        Comment comment = new Comment("ali", product.getId(), "good", "construction", true);
        product.addComments(comment);
        Assert.assertEquals(new ArrayList<>(Collections.singleton(comment)), product.getComments());
        Assert.assertTrue(product.toString().startsWith("id = 1, status"));
    }
}
