package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CategoryTest {
    private final DataBase dataBase = new DataBase();
    private final Seller seller2 = new Seller("ali", "mohamadi", "ali@gmail.com",
            "09135467595", "mamad", "1234", "apple", 500);
    private final ProductInfo productInfo1 = new ProductInfo("A50", "sumsung", 200, seller2, 5);
    private final Product product1 = new Product("construction", productInfo1, "mobile",
            new HashMap<>(Map.of("RAM", "8", "Memory", "128GB", "network", "4.5G")), "very good");

    @Test
    public void classTest() {
        Category category = new Category("PC",
                new ArrayList<>(Arrays.asList("RAM", "Memory", "CPU")));
        Assert.assertFalse(dataBase.getAllCategories().isEmpty());
        category.setName("laptop");
        Assert.assertEquals("laptop", category.getName());
        Assert.assertEquals("RAM", category.getSpecialFeatures().get(0));
        category.addProductList(product1);
        Assert.assertEquals(product1, category.getProductList().get(0));
        Assert.assertEquals(category, Category.getCategoryByName("laptop"));
        Assert.assertNull(Category.getCategoryByName("PC"));
    }
}
