package controller;

import model.*;
import view.menu.Menu;
import view.menu.auctionMenu.AuctionMenu;
import view.menu.productsMenu.FilterInfo;
import view.menu.productsMenu.ProductsMenu;

import java.util.*;
import java.util.stream.Collectors;

public class AllAccountZone {
    private static Account currentAccount = null;

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        AllAccountZone.currentAccount = currentAccount;
    }

    public static Date getCurrentDate() {
        Date UTCDate = new Date();
        final Date diffDate = new Date((4 * 3600 + 30 * 60) * 1000);
        long sum = UTCDate.getTime() + diffDate.getTime();
        return new Date(sum);
    }

    public static String showCategories() {
        StringBuilder categories = new StringBuilder();
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            categories.append(category.getName()).append("\n");
        }
        return String.valueOf(categories);
    }

    public static String getCategoriesForFilter() {
        StringBuilder categories = new StringBuilder();
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            categories.append(category.getName()).append(" | ");
        }
        return String.valueOf(categories);
    }

    public static String getCategoriesRegex() {
        StringBuilder regex = new StringBuilder("(?i)(");
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            regex.append(category.getName()).append("|");
        }
        return String.valueOf(regex);
    }

    public static void setFilterCategoryFeature(String categoryName, Menu menu) {
        HashMap<String, String> feature = new HashMap<>();
        for (String specialFeature : Category.getCategoryByName(categoryName).getSpecialFeatures()) {
            feature.put(specialFeature, "");
        }
        if (menu instanceof ProductsMenu)
            ProductsMenu.getFilter().setFeature(feature);
        else
            AuctionMenu.getFilter().setFeature(feature);
    }

    public static String getProductsInSortAndFiltered(Menu menu) {
        StringBuilder output  = new StringBuilder();
        BuyerZone.setAuctionPrice();
        List<Product> products = getSortedProducts(getFilteredProduct(DataBase.getDataBase().getAllProducts(), menu), menu);
        for (Product product : products) {
            output.append(product.getId()).append(". ").append(product.getGeneralFeature().getName()).append("\n")
                    .append(product.getGeneralFeature().getCompany()).append(" ")
                    .append(product.getGeneralFeature().getAuctionPrice()).append("$\n")
                    .append(product.getAverageScore());
        }
        return String.valueOf(output);
    }

    public static String getAuctionProductsInSortAndFiltered(Menu menu) {
        StringBuilder output = new StringBuilder();
        BuyerZone.setAuctionPrice();
        List<Product> auctionProducts = DataBase.getDataBase().getAllProducts().stream()
                .filter(product ->
                        product.getGeneralFeature().getPrice() != product.getGeneralFeature().getAuctionPrice())
                .collect(Collectors.toList());
        List<Product> products = getSortedProducts(getFilteredProduct(auctionProducts, menu), menu);
        for (Product product : products) {
            output.append(product.getId()).append(". ").append(product.getGeneralFeature().getName()).append("\n")
                    .append(product.getGeneralFeature().getCompany()).append("\nOriginal Price : ")
                    .append(product.getGeneralFeature().getPrice()).append("$   --->>> Current Price : ")
                    .append(product.getGeneralFeature().getAuctionPrice()).append("$\n")
                    .append(product.getAverageScore());
        }
        return String.valueOf(output);
    }

    private static List<Product> getFilteredProduct(List<Product> products, Menu menu) {
        FilterInfo filterInfo;
        if (menu instanceof ProductsMenu)
            filterInfo = ProductsMenu.getFilter();
        else
            filterInfo = AuctionMenu.getFilter();
        return products.stream()
                .filter(product -> {
                    if (!filterInfo.getCategory().equals("") &&
                            !filterInfo.getCategory().equals(product.getCategory().getName()))
                        return false;
                    if (filterInfo.getMinimumPrice() > product.getGeneralFeature().getAuctionPrice())
                        return false;
                    if ((filterInfo.getMaximumPrice() < product.getGeneralFeature().getAuctionPrice()))
                        return false;
                    for (Map.Entry<String, String> entry : filterInfo.getFeature().entrySet()) {
                        if (!entry.getValue().equals("") &&
                                !entry.getValue().equals(product.getSpecialFeature().get(entry.getKey())))
                            return false;
                    }
                    return true;
                }).collect(Collectors.toList());
    }

    private static List<Product> getSortedProducts(List<Product> filteredProducts, Menu menu) {
        String sort;
        if (menu instanceof ProductsMenu)
             sort = ProductsMenu.getSort();
        else
            sort = AuctionMenu.getSort();
        return filteredProducts.stream().sorted((p1, p2) -> {
            if (sort.equals("price(ascending)")) {
                return Long.compare(p1.getGeneralFeature().getAuctionPrice(),
                        p2.getGeneralFeature().getAuctionPrice());
            } else if (sort.equals("price(descending)")) {
                return -1 * Long.compare(p1.getGeneralFeature().getAuctionPrice(),
                        p2.getGeneralFeature().getAuctionPrice());
            } else if (sort.equals("score")) {
                return -1 * Double.compare(p1.getAverageScore(), p2.getAverageScore());
            } else if (sort.equals("date")) {
                return -1 * Integer.compare(p1.getId(), p2.getId());
            }
            return 0;
        }).collect(Collectors.toList());
    }

    public static long viewUserBalance() {
        Account account = AllAccountZone.getCurrentAccount();
        if (account instanceof Buyer)
            return ((Buyer) account).getWallet();
        else
            return ((Seller) account).getWallet();
    }

    public static String getPersonalInfo() {
        Account account = AllAccountZone.getCurrentAccount();
        return "name : " + account.getFirstName() + " " + account.getLastName() +
                "\nemail address : " + account.getEmailAddress() + "\nphone number : " +
                account.getPhoneNumber() + "\nusername : " + account.getUsername() +
                "\npassword : " + account.getPassword();
    }

    public static String showProductWithSellers(int productId) {
        BuyerZone.setAuctionPrice();
        StringBuilder output = new StringBuilder();
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == productId)
                output.append("Seller : ").append(product.getGeneralFeature().getSeller().getUsername()).append(" ")
                        .append(product.getGeneralFeature().getName()).append(" ")
                        .append(product.getGeneralFeature().getAuctionPrice()).append(" $ ")
                        .append(product.getGeneralFeature().getCompany()).append(" ")
                        .append(product.getCategory().getName()).append(" Score : ")
                        .append(product.getAverageScore()).append(" ").append(product.getDescription()).append("\n");
        }
        return output.toString();
    }

    public static String addProductToCart(int productId, String sellerUsername) {
        Seller seller = getSellerByUsername(sellerUsername);
        if (seller == null) {
            return "invalid username.";
        } else {
            Product product = AdminZone.getProductByIdAndSeller(productId, seller);
            ((Buyer) AllAccountZone.getCurrentAccount()).setCart(product);
            return "Done.";
        }
    }

    public static Seller getSellerByUsername(String username) {
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account instanceof Seller && account.getUsername().equalsIgnoreCase(username))
                return (Seller) account;
        }
        return null;
    }

    public static String showProductAttribute(int productId) {
        Product product = null;
        for (Product product1 : DataBase.getDataBase().getAllProducts()) {
            if (product1.getId() == productId) {
                product = product1;
                break;
            }
        }
        String feature = "";
        for (Map.Entry<String, String> entry : product.getSpecialFeature().entrySet()) {
            feature += entry.getKey() + " : " + entry.getValue() + "\n";
        }
        return "Category : " + product.getCategory().getName() + "\n" + feature + product.getDescription() +
                "score : " + product.getAverageScore() + " " + product.getNumOfUsersRated() + "person";
    }

    public static String compareTwoProduct(int productId1, int productId2) {
        Product product1 = SellerZone.getProductById(productId1);
        Product product2 = SellerZone.getProductById(productId2);
        Category category = product1.getCategory();
        if (!category.getName().equals(product2.getCategory().getName())) {
            return "Cannot compared! You should enter a product in " + product1.getCategory().getName() + "category";
        } else {
            StringBuilder output = new StringBuilder();
            for (String feature : category.getSpecialFeatures()) {
                output.append(feature).append(",").append(product1.getSpecialFeature().get(feature)).append(",")
                        .append(product2.getSpecialFeature().get(feature)).append("-");
            }
            return output.toString();
        }
    }

    public static String showProductComments(int productId) {
        Product product = SellerZone.getProductById(productId);
        StringBuilder output = new StringBuilder();
        for (Comment comment : product.getComments()) {
            output.append(comment.getCommentText()).append("\n");
        }
        return output.toString();
    }

    public static void createComment(String text, int productId) {
        Buyer buyer = (Buyer) getCurrentAccount();
        Product product = SellerZone.getProductById(productId);
        boolean hasBought = false;
        for (BuyLog buyLog : buyer.getBuyHistory()) {
            if (buyLog.getPurchasedProductionsAndSellers().containsKey(product)) {
                hasBought = true;
                break;
            }
        }
        new Comment(buyer, product, text, "unseen", hasBought);
        // TODO : new request.
    }
}