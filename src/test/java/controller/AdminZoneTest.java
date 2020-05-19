package controller;

import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class AdminZoneTest {
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
    private final ProductInfo productInfo3 = new ProductInfo("k56C", "asus", 360, seller1, 1);
    private final Product product3 = new Product("accepted", productInfo3, "PC",
            new HashMap<>(Map.of("Memory", "2TB", "CPU", "corei7", "RAM", "16")), "hulu");
    private final Auction auction1 = new Auction(new ArrayList<>(Collections.singleton(product1)), "editing", new Date(),
            new Date(new Date().getTime() + 1000000000), 40, "mamad");
    private final Auction auction2 = new Auction(new ArrayList<>(Collections.singleton(product2)), "construction", new Date(),
            new Date(new Date().getTime() + 1000000000), 20, "jafar");
    private final BuyLog buyLog = new BuyLog(new Date(), 250, 150,
            new HashMap<>(Map.of(1, "jafar")), "ali", "sending");
    private final Comment comment = new Comment("ali", product2.getId(), "good", "construction",
            true);
    private final Rate rate = new Rate("ali", 4, product2.getId());
    private final Request request1 = new Request("ali", "create seller account",
            "bagher,kazemi,bagher@yahoo.com,09786325436,bagher,kazemi,big Boys,200", "unseen");
    private final Request request2 = new Request("mamad", "add product", String.valueOf(product1.getId()),
            "unseen");
    private final Request request3 = new Request("jafar", "edit product", product2.getId() +
            ",iphone11,orange,240,2", "unseen");
    private final Request request4 = new Request("jafar", "edit auction", auction2.getId() +
            ",1,1266543132,8952474654312,35", "unseen");
    private final Request request5 = new Request("mamad", "add auction", String.valueOf(auction2.getId()),
            "unseen");
    private final Request request6 = new Request("ali", "add comment", String.valueOf(comment.getId()),
            "unseen");
    private final Discount discount = new Discount("big discount", new Date(), new Date(new Date().getTime() + 1000000000),
            new long[]{25, 150}, 3, new ArrayList<>(Collections.singletonList("ali")));

    @Test
    public void showAllRequestsTest() {
        Assert.assertEquals(request1.getId() + ". create seller account -> unseen\n" + request2.getId() +
                ". add product -> unseen\n" + request3.getId() + ". edit product -> unseen\n" + request4.getId() +
                ". edit auction -> unseen\n" + request5.getId() + ". add auction -> unseen\n" +
                request6.getId() + ". add comment -> unseen\n", AdminZone.showAllRequests());
    }

    @Test
    public void viewRequestDetailsTest() {
        Assert.assertEquals("invalid request ID", AdminZone.viewRequestDetails(10));
        Assert.assertEquals(request3.getId() + ". edit product : \njafar mohamadi\n" + product2.getId() +
                ",iphone11,orange,240,2", AdminZone.viewRequestDetails(request3.getId()));
    }

    @Test
    public void acceptRequestTest() {
        Assert.assertEquals("invalid request ID", AdminZone.acceptRequest(-5));
        Assert.assertEquals("Done", AdminZone.acceptRequest(request1.getId()));
        Assert.assertEquals("Done", AdminZone.acceptRequest(request2.getId()));
        Assert.assertEquals("Done", AdminZone.acceptRequest(request3.getId()));
        Assert.assertEquals("Done", AdminZone.acceptRequest(request4.getId()));
        Assert.assertEquals("Done", AdminZone.acceptRequest(request5.getId()));
        Assert.assertEquals("Done", AdminZone.acceptRequest(request6.getId()));
    }

    @Test
    public void declineRequestTest() {
        AllAccountZone.setCurrentAccount(seller1);
        Assert.assertEquals("invalid request ID", AdminZone.declineRequest(-2));
        Assert.assertEquals("Done", AdminZone.declineRequest(request1.getId()));
        Assert.assertEquals("Done", AdminZone.declineRequest(request2.getId()));
        Assert.assertEquals("Done", AdminZone.declineRequest(request3.getId()));
        Assert.assertEquals("Done", AdminZone.declineRequest(request4.getId()));
        Assert.assertEquals("Done", AdminZone.declineRequest(request5.getId()));
        Assert.assertEquals("Done", AdminZone.declineRequest(request6.getId()));
    }

    @Test
    public void showUsersInfoTest() {
        Assert.assertEquals("ali\njafar\nmamad\n", AdminZone.showUsersInfo());
    }

    @Test
    public void showUserInfoTest() {
        Assert.assertEquals("Buyer : \nName : ali mohamadi\nEmail : ali@gmail.com\n" +
                "Phone number : 09135467595", AdminZone.showUserInfo("ali"));
        Assert.assertEquals("Seller : \nName : ali mohamadi\nEmail : ali@gmail.com\n" +
                "Phone number : 09135467595", AdminZone.showUserInfo("mamad"));
        Assert.assertEquals("invalid username", AdminZone.showUserInfo("kobra"));
    }

    @Test
    public void deleteUserTest() {
        Assert.assertEquals("invalid username", AdminZone.deleteUser("asghar"));
        Assert.assertEquals("ali deleted.", AdminZone.deleteUser("ali"));
        Assert.assertEquals("jafar deleted.", AdminZone.deleteUser("jafar"));
    }

    @Test
    public void createAdminProfileTest() {
        AdminZone.createAdminProfile(new ArrayList<>(Arrays.asList("ali", "meshki", "alim@gmail.com", "031549846",
                "ali official", "1367")));
        Assert.assertFalse(AllAccountZone.isUsernameValid("ali official"));
    }

    @Test
    public void removeProductTest() {
        Assert.assertEquals("product removed successfully.", AdminZone.removeProduct(product2.getId()));
        Assert.assertEquals("invalid product ID.", AdminZone.removeProduct(100));
    }

    @Test
    public void showAllProductsTest() {
        Assert.assertEquals(product1.getId() + ". A50\n" + product2.getId() + ". iphoneX\n" + product3.getId() +
                ". k56C\n", AdminZone.showAllProducts());
    }

    @Test
    public void getBuyerByUsernameTest() {
        Assert.assertEquals(buyer, AdminZone.getBuyerByUsername("ali"));
        Assert.assertNull(AdminZone.getBuyerByUsername("asghar"));
    }

    @Test
    public void createDiscountTest() {
        AdminZone.createDiscount(new ArrayList<>(Arrays.asList("takhfif", "98765421", "876543124", "25", "100", "3")),
                new ArrayList<>(Collections.singletonList("ali")));
        Assert.assertTrue(buyer.getDiscountCodes().containsKey("takhfif"));
    }

    @Test
    public void showDiscountsTest() {
        Assert.assertEquals("Code : 'big discount' 25% discount, at most : 150$\n", AdminZone.showDiscounts());
    }

    @Test
    public void getDiscountByCodeTest() {
        Assert.assertEquals(discount, AdminZone.getDiscountByCode("big discount"));
        Assert.assertNull(AdminZone.getDiscountByCode("fake"));
    }

    @Test
    public void showDiscountInfoTest() {
        Assert.assertEquals("25% discount, at most : 150$ from \"" + new Date() + "\" to \"" +
                new Date(new Date().getTime() + 1000000000)+ "\" 3 times for ali,", AdminZone.showDiscountInfo("big discount"));
    }

    @Test
    public void removeDiscountTest() {
        Assert.assertEquals("invalid code", AdminZone.removeDiscount("fake"));
        Assert.assertEquals("Done.", AdminZone.removeDiscount("big discount"));
    }

    @Test
    public void addNewFeatureToCategoryTest() {
        category1.addProductList(product3);
        AdminZone.addNewFeatureToCategory(category1, "Screen");
        Assert.assertTrue(product3.getCategoryFeature().containsKey("Screen"));
    }

    @Test
    public void deleteFeatureFromCategoryTest() {
        category1.addProductList(product3);
        AdminZone.deleteFeatureFromCategory(category1, "RAM");
        Assert.assertFalse(product3.getCategoryFeature().containsKey("RAM"));
    }

    @Test
    public void renameFeatureOfCategoryTest() {
        category1.addProductList(product3);
        AdminZone.renameFeatureOfCategory(category1, "CPU", "GPU");
        Assert.assertFalse(product3.getCategoryFeature().containsKey("CPU"));
        Assert.assertTrue(product3.getCategoryFeature().containsKey("GPU"));
    }

    @Test
    public void createCategoryTest() {
        AdminZone.createCategory("Car", new ArrayList<>(Arrays.asList("max speed", "acceleration", "is off road")));
        Assert.assertNotNull(SellerZone.getCategoryByName("Car"));
    }

    @Test
    public void removeCategoryTest() {
        category2.addProductList(product1);
        category2.addProductList(product2);
        AdminZone.removeCategory("mobile");
        Assert.assertNull(SellerZone.getCategoryByName("mobile"));
    }

    @Test
    public void isCategoryNameValidTest() {
        Assert.assertTrue(AdminZone.isCategoryNameValid("Car"));
        Assert.assertFalse(AdminZone.isCategoryNameValid("PC"));
    }
}
