package controller;

import model.*;
import org.junit.Assert;
import org.junit.Test;
import consoleView.menu.MainMenu;
import consoleView.menu.Menu;
import consoleView.menu.auctionMenu.AuctionMenu;
import consoleView.menu.productsMenu.ProductsMenu;

import java.util.*;

public class AllAccountZoneTest {
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
    private final ProductsMenu productsMenu = new ProductsMenu(new MainMenu());
    private final AuctionMenu auctionMenu = new AuctionMenu(new MainMenu());
    private final ProductInfo productInfo1 = new ProductInfo("A50", "sumsung", 200, seller2, 5);
    private final Product product1 = new Product("accepted", productInfo1, "mobile",
            new HashMap<>(Map.of("RAM", "8", "Memory", "128GB", "network", "4.5G")), "very good");
    private final ProductInfo productInfo2 = new ProductInfo("iphoneX", "apple", 300, seller1, 10);
    private final Product product2 = new Product("accepted", productInfo2, "mobile",
            new HashMap<>(Map.of("RAM", "16", "Memory", "256GB", "network", "4.5G")), "expensive");
    private final ProductInfo productInfo3 = new ProductInfo("k56C", "asus", 360, seller1, 1);
    private final Product product3 = new Product("accepted", productInfo3, "PC",
            new HashMap<>(Map.of("Memory", "2TB", "CPU", "corei7", "RAM", "16")), "hulu");
    private final Auction auction = new Auction(new ArrayList<>(Collections.singleton(product1)), "accepted", new Date(),
            new Date(new Date().getTime() + 1000000000), 40, "mamad");
    private final BuyLog buyLog = new BuyLog(new Date(), 250, 150,
            new HashMap<>(Map.of(1, "jafar")), "ali", "sending");
    private final Comment comment = new Comment("ali", 1, "good", "accepted", true);

    @Test
    public void createAccountTest() {
        ArrayList<String> accountInfo = new ArrayList<>(Arrays.asList("admin", "ali", "mohamadi", "ali@gmail.com",
                                                                                "09135467595", "alimamad", "1234"));
        Assert.assertEquals("Successfully created.", AllAccountZone.createAccount(accountInfo));
        accountInfo = new ArrayList<>(Arrays.asList("buyer", "ali", "mohamadi", "ali@gmail.com",
                                                            "09135467595", "alimamad", "1234", "500"));
        Assert.assertEquals("Successfully created.", AllAccountZone.createAccount(accountInfo));
        accountInfo = new ArrayList<>(Arrays.asList("seller", "ali", "mohamadi", "ali@gmail.com",
                                                            "09135467595", "alimamad", "1234", "apple", "500"));
        Assert.assertEquals("Request sent. Wait for Admin agreement.", AllAccountZone.createAccount(accountInfo));
    }

    @Test
    public void isUsernameValidTest() {
        Assert.assertFalse(AllAccountZone.isUsernameValid("alimamad"));
        Assert.assertTrue(AllAccountZone.isUsernameValid("hasan"));
    }

    @Test
    public void loginUserTest() {
        Menu.setMainMenu(new MainMenu());
        ArrayList<String> info = new ArrayList<>(Arrays.asList("admin", "alimamad", "1234"));
        Assert.assertEquals("Login successfully.", AllAccountZone.loginUser(info));
        info = new ArrayList<>(Arrays.asList("admin", "alimamad", "123"));
        Assert.assertEquals("Wrong password.", AllAccountZone.loginUser(info));
        info = new ArrayList<>(Arrays.asList("buyer", "ali", "1234"));
        Assert.assertEquals("Login successfully.", AllAccountZone.loginUser(info));
        info = new ArrayList<>(Arrays.asList("buyer", "ali", "123"));
        Assert.assertEquals("Wrong password.", AllAccountZone.loginUser(info));
        info = new ArrayList<>(Arrays.asList("seller", "mamad", "1234"));
        Assert.assertEquals("Login successfully.", AllAccountZone.loginUser(info));
        info = new ArrayList<>(Arrays.asList("seller", "mamad", "123"));
        Assert.assertEquals("Wrong password.", AllAccountZone.loginUser(info));
        info = new ArrayList<>(Arrays.asList("admin", "mamadali", "1234"));
        Assert.assertEquals("Username not found.", AllAccountZone.loginUser(info));
    }

    @Test
    public void getAccountByUsernameTest() {
        Assert.assertEquals(admin, AllAccountZone.getAccountByUsername("alimamad"));
        Assert.assertNull(AllAccountZone.getAccountByUsername("hasan"));
    }

    @Test
    public void getCurrentAccountTest() {
        AllAccountZone.setCurrentAccount(admin);
        Assert.assertEquals(admin, AllAccountZone.getCurrentAccount());
    }

    @Test
    public void getCurrentDateTest() {
        Date actualDate = AllAccountZone.getCurrentDate();
        Assert.assertTrue(new Date().getTime() + ((4 * 3600 + 30 * 60) * 1000) - actualDate.getTime() < 100);
    }

    @Test
    public void ShowCategoriesTest() {
        Assert.assertEquals("PC\nmobile\n", AllAccountZone.getCategories());
    }

    @Test
    public void getCategoriesForFilterTest() {
        Assert.assertEquals("PC | mobile | ", AllAccountZone.getCategoriesForFilter());
    }

    @Test
    public void getCategoriesRegexTest() {
        Assert.assertEquals("(?i)(PC|mobile|", AllAccountZone.getCategoriesRegex());
    }

    @Test
    public void setFilterCategoryFeatureTest() {
        AllAccountZone.setFilterCategoryFeature("PC", productsMenu);
        Assert.assertEquals(new ArrayList<>(Arrays.asList("Memory", "CPU", "RAM")),
                                        new ArrayList<>(ProductsMenu.getFilter().getFeature().keySet()));
        AllAccountZone.setFilterCategoryFeature("PC", auctionMenu);
        Assert.assertEquals(new ArrayList<>(Arrays.asList("Memory", "CPU", "RAM")),
                new ArrayList<>(AuctionMenu.getFilter().getFeature().keySet()));
    }

    @Test
    public void getProductsInSortAndFilteredTest() {
        AllAccountZone.getProductsInSortAndFiltered(productsMenu);
        AllAccountZone.getProductsInSortAndFiltered(auctionMenu);
        ProductsMenu.getFilter().setCategory("mobile");
        ProductsMenu.getFilter().setFeature(new HashMap<>(Map.of("RAM", "16")));
        ProductsMenu.setSort("score");
        AllAccountZone.getProductsInSortAndFiltered(productsMenu);
    }

    @Test
    public void getAuctionProductsInSortAndFilteredTest() {
        AllAccountZone.getAuctionProductsInSortAndFiltered(productsMenu);
        AuctionMenu.getFilter().setCategory("mobile");
        AuctionMenu.setSort("Price(Descending)");
        AllAccountZone.getAuctionProductsInSortAndFiltered(auctionMenu);
    }

    @Test
    public void viewUserBalanceTest() {
        AllAccountZone.setCurrentAccount(buyer);
        Assert.assertEquals(500, AllAccountZone.viewUserBalance());
        AllAccountZone.setCurrentAccount(seller1);
        Assert.assertEquals(500, AllAccountZone.viewUserBalance());
    }

    @Test
    public void getPersonalInfoTest() {
        AllAccountZone.setCurrentAccount(buyer);
        Assert.assertEquals("name : ali mohamadi\nemail address : ali@gmail.com\nphone number :" +
                " 09135467595\nusername : ali\npassword : 1234", AllAccountZone.getPersonalInfo());
    }

    @Test
    public void showProductWithSellersTest() {
        Assert.assertEquals("Seller : jafar\niphoneX 300$ apple mobile Score : 0.0 expensive",
                AllAccountZone.showProductWithSellers(product2.getId()));
    }

    @Test
    public void addProductToCartTest() {
        AllAccountZone.setCurrentAccount(buyer);
        Assert.assertEquals("Done.", AllAccountZone.addProductToCart(1));
        AllAccountZone.addProductToCart(product2.getId());
        Assert.assertTrue(buyer.getCart().containsKey(product2));
    }

    @Test
    public void showProductAttributeTest() {
        Assert.assertEquals("Category : PC\nCPU : corei7\nMemory : 2TB\nRAM : 16\nhulu\n" +
                "score : 0.0 from 0 person", AllAccountZone.showProductAttribute(product3.getId()));
    }

    @Test
    public void compareTwoProductTest() {
        AllAccountZone.compareTwoProduct(product1.getId(),product2.getId());
        Assert.assertEquals("Cannot compared! You should enter a product in PC category",
                AllAccountZone.compareTwoProduct(product3.getId(),product1.getId()));
    }

    @Test
    public void showProductCommentsTest() {
        product2.addComments(comment);
        Assert.assertEquals("good\n", AllAccountZone.showProductComments(product2.getId()));
    }

    @Test
    public void createCommentTest() {
        AllAccountZone.setCurrentAccount(buyer);
        AllAccountZone.createComment("good product", 1);
        Assert.assertEquals("add comment", DataBase.getDataBase().getAllRequests().get(0).getTopic());
        buyer.addBuyHistory(buyLog);
        AllAccountZone.createComment("good product", 1);
    }
}
