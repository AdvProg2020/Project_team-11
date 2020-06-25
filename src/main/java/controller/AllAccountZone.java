package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.*;
import view.*;

import java.util.*;
import java.util.List;
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

    public static ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            categories.add(category.getName());
        }
        return categories;
    }

    public static String getCategoriesForFilter() {
        StringBuilder categories = new StringBuilder();
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            categories.append(category.getName()).append(" | ");
        }
        return categories.toString();
    }

    public static String getCategoriesRegex() {
        StringBuilder regex = new StringBuilder("(?i)(");
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            regex.append(category.getName()).append("|");
        }
        return regex.toString();
    }

    public static void setFilterCategoryFeature(String categoryName, String className) {
        HashMap<String, String> feature = new HashMap<>();
        for (String specialFeature : Category.getCategoryByName(categoryName).getSpecialFeatures()) {
            feature.put(specialFeature, "");
        }
        if (className.equals("products"))
            ProductScene.getFilterInfo().setFeature(feature);
        else
            AuctionScene.getFilterInfo().setFeature(feature);
    }

    public static ArrayList<Product> getProductsInSortAndFiltered(String className) {
        BuyerZone.setAuctionPrice();
        List<Product> products =
                getSortedProducts(getFilteredProduct(DataBase.getDataBase().getAllProducts(), className), className);
        return new ArrayList<>(products);
    }

    public static ArrayList<Product> getAuctionProductsInSortAndFiltered(String className) {
        BuyerZone.setAuctionPrice();
        List<Product> auctionProducts = DataBase.getDataBase().getAllProducts().stream()
                .filter(product ->
                        product.getGeneralFeature().getPrice() != product.getGeneralFeature().getAuctionPrice())
                .collect(Collectors.toList());
        List<Product> products = getSortedProducts(getFilteredProduct(auctionProducts, className), className);
        return new ArrayList<>(products);
    }

    private static List<Product> getFilteredProduct(List<Product> products, String className) {
        FilterInfo filterInfo;
        if (className.equals("products"))
            filterInfo = ProductScene.getFilterInfo();
        else
            filterInfo = AuctionScene.getFilterInfo();
        return products.stream()
                .filter(product -> {
                    if (!filterInfo.getCategory().equals("") &&
                            !filterInfo.getCategory().equals(product.getCategoryName()))
                        return false;
                    if (filterInfo.getMinimumPrice() > product.getGeneralFeature().getAuctionPrice())
                        return false;
                    if (filterInfo.getMaximumPrice() < product.getGeneralFeature().getAuctionPrice())
                        return false;
                    if (!product.getGeneralFeature().getName().toLowerCase().contains(filterInfo.getSearchBar().toLowerCase()) &&
                            !product.getGeneralFeature().getSeller().getUsername().toLowerCase().contains(filterInfo.getSearchBar().toLowerCase()) &&
                            !product.getGeneralFeature().getCompany().toLowerCase().contains(filterInfo.getSearchBar().toLowerCase()))
                        return false;
                    if (filterInfo.getMinimumStockStatus() > product.getGeneralFeature().getStockStatus())
                        return false;
                    for (Map.Entry<String, String> entry : filterInfo.getFeature().entrySet()) {
                        if (!entry.getValue().equals("") &&
                                !entry.getValue().equals(product.getCategoryFeature().get(entry.getKey())))
                            return false;
                    }
                    return true;
                }).collect(Collectors.toList());
    }

    private static List<Product> getSortedProducts(List<Product> filteredProducts, String className) {
        String sort;
        if (className.equals("products"))
            sort = ProductScene.getSort();
        else
            sort = AuctionScene.getSort();
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

    public static ArrayList<String> getPersonalInfo() {
        if (getCurrentAccount() instanceof Admin) {
            Admin admin = (Admin) AllAccountZone.getCurrentAccount();
            return new ArrayList<>(Arrays.asList(admin.getFirstName(), admin.getLastName(), admin.getEmailAddress(),
                    admin.getPhoneNumber(), admin.getUsername(), admin.getPassword()));
        } else if (getCurrentAccount() instanceof Seller) {
            Seller seller = (Seller) AllAccountZone.getCurrentAccount();
            return new ArrayList<>(Arrays.asList(seller.getFirstName(), seller.getLastName(), seller.getEmailAddress(),
                    seller.getPhoneNumber(), seller.getUsername(), seller.getPassword(), seller.getCompanyName(),
                    String.valueOf(seller.getWallet())));
        } else {
            Buyer buyer = (Buyer) AllAccountZone.getCurrentAccount();
            return new ArrayList<>(Arrays.asList(buyer.getFirstName(), buyer.getLastName(), buyer.getEmailAddress(),
                    buyer.getPhoneNumber(), buyer.getUsername(), buyer.getPassword(), String.valueOf(buyer.getWallet())));
        }
    }

    public static String showProductWithSellers(int productId) {
        BuyerZone.setAuctionPrice();
        StringBuilder output = new StringBuilder();
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == productId)
                output.append("Seller : ").append(product.getGeneralFeature().getSeller().getUsername()).append("\n")
                        .append(product.getGeneralFeature().getName()).append(" ")
                        .append(product.getGeneralFeature().getAuctionPrice()).append("$ ")
                        .append(product.getGeneralFeature().getCompany()).append(" ")
                        .append(product.getCategoryName()).append(" Score : ")
                        .append(product.getAverageScore()).append(" ").append(product.getDescription());
        }
        return output.toString();
    }

    public static String addProductToCart(int productId) {
        Product product = SellerZone.getProductById(productId);
        ((Buyer) AllAccountZone.getCurrentAccount()).setCart(product);
        return "Done.";
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
        for (Map.Entry<String, String> entry : product.getCategoryFeature().entrySet()) {
            feature += entry.getKey() + " : " + entry.getValue() + "\n";
        }
        return "Category : " + product.getCategoryName() + "\n" + feature + product.getDescription() +
                "\nscore : " + product.getAverageScore() + " from " + product.getNumOfUsersRated() + " person";
    }

    public static String compareTwoProduct(int productId1, int productId2) {
        Product product1 = SellerZone.getProductById(productId1);
        Product product2 = SellerZone.getProductById(productId2);
        Category category = SellerZone.getCategoryByName(product1.getCategoryName());
        if (!category.getName().equals(product2.getCategoryName())) {
            return "Cannot compared! You should enter a product in " + product1.getCategoryName() + " category";
        } else {
            StringBuilder output = new StringBuilder();
            for (String feature : category.getSpecialFeatures()) {
                output.append(feature).append(",").append(product1.getCategoryFeature().get(feature)).append(",")
                        .append(product2.getCategoryFeature().get(feature)).append("-");
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
            if (buyLog.getPurchasedProductionsAndSellers().containsKey(productId)) {
                hasBought = true;
                break;
            }
        }
        Comment comment = new Comment(buyer.getUsername(), productId, text, "unseen", hasBought);
        new Request(buyer.getUsername(), "add comment", String.valueOf(comment.getId()), "unseen");
    }

    public static String createAccount(ArrayList<String> info) {
        if (info.get(0).equalsIgnoreCase("admin")) {
            DataBase.getDataBase().setHasAdminAccountCreated(true);
            new Admin(info.get(1), info.get(2), info.get(3), info.get(4), info.get(5), info.get(6));
        } else if (info.get(0).equalsIgnoreCase("seller")) {
            StringBuilder requestDescription = new StringBuilder();
            for (int i = 1; i <= 6; i++) {
                requestDescription.append(info.get(i)).append(",");
            }
            requestDescription.append(info.get(8)).append(",");
            requestDescription.append(info.get(7)).append(",");
            new Request(info.get(5), "create seller account", requestDescription.toString(), "unseen");
            return "Request sent. Wait for Admin agreement.";
        } else {
            new Buyer(info.get(1), info.get(2), info.get(3), info.get(4), info.get(5), info.get(6),
                    Long.parseLong(info.get(7)));
        }
        return "Successfully created.";
    }

    public static boolean isUsernameValid(String username) {
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account.getUsername().equalsIgnoreCase(username))
                return false;
        }
        for (Request request : DataBase.getDataBase().getAllRequests()) {
            if (request.getSenderName().equals(username) && request.getStatus().equals("unseen"))
                return false;
        }
        return true;
    }

    public static String loginUser(ArrayList<String> info) {
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (info.get(0).equalsIgnoreCase("admin")) {
                if (account instanceof Admin && account.getUsername().equals(info.get(1))) {
                    if (account.getPassword().equals(info.get(2))) {
                        setCurrentAccount(account);
                        Button button = new Button("Admin");
                        button.setOnAction(e -> {
                            MainScenes.getBorderPane().setLeft(AdminScene.getAdminRoot());
                            MainScenes.getBorderPane().setCenter(AdminScene.getPersonalInfo());
                        });
                        button.setMinWidth(200);
                        button.setAlignment(Pos.CENTER);
                        button.getStyleClass().add("top-buttons");
                        ((HBox) MainScenes.getBorderPane().getTop()).getChildren().add(2, button);
                        button.fire();
                        return "Login successfully.";
                    }
                    return "Wrong password.";
                }
            } else if (info.get(0).equalsIgnoreCase("seller")) {
                if (account instanceof Seller && account.getUsername().equals(info.get(1))) {
                    if (account.getPassword().equals(info.get(2))) {
                        setCurrentAccount(account);
                        Button button = new Button("Seller");
                        button.setOnAction(e -> {
                            MainScenes.getBorderPane().setLeft(SellerScene.getSellerRoot());
                            MainScenes.getBorderPane().setCenter(SellerScene.getPersonalInfo());
                        });
                        button.setMinWidth(200);
                        button.setAlignment(Pos.CENTER);
                        button.getStyleClass().add("top-buttons");
                        ((HBox) MainScenes.getBorderPane().getTop()).getChildren().add(2, button);
                        button.fire();
                        return "Login successfully.";
                    }
                    return "Wrong password.";
                }
            } else if (info.get(0).equalsIgnoreCase("buyer")) {
                if (account instanceof Buyer && account.getUsername().equals(info.get(1))) {
                    if (account.getPassword().equals(info.get(2))) {
                        setCurrentAccount(account);
                        Button button = new Button("Buyer");
                        button.setOnAction(e -> {
                            MainScenes.getBorderPane().setLeft(BuyerScene.getBuyerRoot());
                            MainScenes.getBorderPane().setCenter(BuyerScene.getPersonalInfo());
                        });
                        button.setMinWidth(200);
                        button.setAlignment(Pos.CENTER);
                        button.getStyleClass().add("top-buttons");
                        ((HBox) MainScenes.getBorderPane().getTop()).getChildren().add(2, button);
                        button.fire();
                        return "Login successfully.";
                    }
                    return "Wrong password.";
                }
            }
        }
        return "Username not found.";
    }

    public static Account getAccountByUsername(String username) {
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account.getUsername().equals(username))
                return account;
        }
        return null;
    }

    public static void editPersonalInfo(String field, String value) {
        if (field.equalsIgnoreCase("first name")) {
            currentAccount.setFirstName(value);
        } else if (field.equalsIgnoreCase("last name")) {
            currentAccount.setLastName(value);
        } else if (field.equalsIgnoreCase("email")) {
            currentAccount.setEmailAddress(value);
        } else if (field.equalsIgnoreCase("phone Number")) {
            currentAccount.setPhoneNumber(value);
        } else if (field.equalsIgnoreCase("password")) {
            currentAccount.setPassword(value);
        } else if (field.equalsIgnoreCase("company")) {
            ((Seller) currentAccount).setCompanyName(value);
        } else if (field.equalsIgnoreCase("wallet")) {
            try {
                ((Seller) currentAccount).setWallet(Long.parseLong(value));
            } catch (Exception ex) {
                ((Buyer) currentAccount).setWallet(Long.parseLong(value));
            }
        }
    }
}