package controller;

import server.Server;
import model.*;
import Client.view.*;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AllAccountZone {

    public static Date getCurrentDate() {
        Date UTCDate = new Date();
//        final Date diffDate = new Date((4 * 3600 + 30 * 60) * 1000);
        long sum = UTCDate.getTime() /*+ diffDate.getTime()*/;
        return new Date(sum);
    }

    public static ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            categories.add(category.getName());
        }
        return categories;
    }

    public static void setFilterCategoryFeature(String categoryName, ArrayList<String> features, String className) {
        HashMap<String, String> feature = new HashMap<>();
        if (!categoryName.equals("--------")) {
            for (String specialFeature : features) {
                feature.put(specialFeature, "");
            }
        }
        if (className.equals("products"))
            ProductScene.getFilterInfo().setFeature(feature);
        else
            AuctionScene.getFilterInfo().setFeature(feature);
    }

    public static ArrayList<Product> getProductsInSortAndFiltered(ArrayList<Product> products, String className) {
        List<Product> productsOut =
                getSortedProducts(getFilteredProduct(products, className), className);
        return new ArrayList<>(productsOut);
    }

    public static ArrayList<Product> getAuctionProductsInSortAndFiltered(ArrayList<Product> products, String className) {
        List<Product> auctionProducts = products.stream()
                .filter(product ->
                        product.getGeneralFeature().getPrice() != product.getGeneralFeature().getAuctionPrice())
                .collect(Collectors.toList());
        List<Product> productsOut = getSortedProducts(getFilteredProduct(auctionProducts, className), className);
        return new ArrayList<>(productsOut);
    }

    private static List<Product> getFilteredProduct(List<Product> products, String className) {
        FilterInfo filterInfo;
        if (className.equals("products"))
            filterInfo = ProductScene.getFilterInfo();
        else
            filterInfo = AuctionScene.getFilterInfo();
        return products.stream()
                .filter(product -> {
                    if (!product.getStatus().equals("accepted"))
                        return false;
                    if (!filterInfo.getCategory().equals("") && !filterInfo.getCategory().equals("--------") &&
                            !filterInfo.getCategory().equals(product.getCategoryName()))
                        return false;
                    if (filterInfo.getMinimumPrice() > product.getGeneralFeature().getAuctionPrice())
                        return false;
                    if (filterInfo.getMaximumPrice() < product.getGeneralFeature().getAuctionPrice())
                        return false;
                    if (!product.getGeneralFeature().getName().toLowerCase().contains(filterInfo.getSearchBar().toLowerCase()) &&
                            !product.getGeneralFeature().getSeller().toLowerCase().contains(filterInfo.getSearchBar().toLowerCase()) &&
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

    public static ArrayList<String> getPersonalInfo(Account account) {
        if (account instanceof Admin) {
            Admin admin = (Admin) account;
            return new ArrayList<>(Arrays.asList(admin.getFirstName(), admin.getLastName(), admin.getEmailAddress(),
                    admin.getPhoneNumber(), admin.getUsername(), admin.getPassword()));
        } else if (account instanceof Seller) {
            Seller seller = (Seller) account;
            return new ArrayList<>(Arrays.asList(seller.getFirstName(), seller.getLastName(), seller.getEmailAddress(),
                    seller.getPhoneNumber(), seller.getUsername(), seller.getPassword(), seller.getCompanyName(),
                    String.valueOf(seller.getWallet())));
        } else if (account instanceof Buyer) {
            Buyer buyer = (Buyer) account;
            return new ArrayList<>(Arrays.asList(buyer.getFirstName(), buyer.getLastName(), buyer.getEmailAddress(),
                    buyer.getPhoneNumber(), buyer.getUsername(), buyer.getPassword(), String.valueOf(buyer.getWallet())));
        }
        return null;
    }

    public static void addProductToCart(int productId, Account account) {
        ((Buyer) account).setCart(productId);
    }

    public static String compareTwoProduct(int productId1, int productId2) {
        Product product1 = SellerZone.getProductById(productId1);
        Product product2 = SellerZone.getProductById(productId2);
        Category category = SellerZone.getCategoryByName(product1.getCategoryName());
        if (!category.getName().equals(product2.getCategoryName())) {
            return "Cannot compared! You should enter a product in " + product1.getCategoryName() + " category.";
        } else {
            StringBuilder output = new StringBuilder();
            for (String feature : category.getSpecialFeatures()) {
                output.append(feature).append(",").append(product1.getCategoryFeature().get(feature)).append(",")
                        .append(product2.getCategoryFeature().get(feature)).append("-");
            }
            return output.toString();
        }
    }

    public static void createComment(String text, int productId, Account account) {
        Product product = SellerZone.getProductById(productId);
        boolean hasBought = BuyerZone.hasUserBoughtProduct(productId, account);
        Comment comment = new Comment(account.getUsername(), productId, text, "unseen", hasBought);
        product.getComments().add(comment);
        new Request(account.getUsername(), "add comment", String.valueOf(comment.getId()), "unseen");
    }

    public static void createAccount(ArrayList<String> info) {
        if (info.get(0).equalsIgnoreCase("admin")) {
            DataBase.getDataBase().setHasAdminAccountCreated(true);
            new Admin(info.get(1), info.get(2), info.get(3), info.get(4), info.get(5), info.get(6));
        } else if (info.get(0).equalsIgnoreCase("support")) {
            new Support(info.get(1), info.get(2), info.get(3), info.get(4), info.get(5), info.get(6));
        } else if (info.get(0).equalsIgnoreCase("seller")) {
            StringBuilder requestDescription = new StringBuilder();
            for (int i = 1; i <= 6; i++) {
                requestDescription.append(info.get(i)).append(",");
            }
            requestDescription.append(info.get(7)).append(",");
            new Request(info.get(5), "create seller account", requestDescription.toString(), "unseen");
        } else {
            long accountId = 0;
            try {
                Socket socket = new Socket("127.0.0.1", Server.getBankServerPort());
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dataOutputStream.writeUTF("create_account " + info.get(1) + " " + info.get(2) + " " +
                        info.get(5) + " " + info.get(6) + " " + info.get(6));
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF()); // TODO : debug mode
                accountId = Long.parseLong(dataInputStream.readUTF());
                dataOutputStream.writeUTF("get_token " + info.get(5) + " " + info.get(6));
                dataOutputStream.flush();
                String token = dataInputStream.readUTF();
                dataOutputStream.writeUTF("create_receipt " + token + " deposit 100000000 -1 " + accountId);
                dataOutputStream.flush();
                String id = dataInputStream.readUTF();
                dataOutputStream.writeUTF("pay " + id);
                dataOutputStream.flush();
                dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Buyer(info.get(1), info.get(2), info.get(3), info.get(4), info.get(5), info.get(6), accountId);
        }
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
                        return "Login successfully admin.";
                    }
                    return "Wrong password.";
                }
            } else if (info.get(0).equalsIgnoreCase("support")) {
                if (account instanceof Support && account.getUsername().equals(info.get(1))) {
                    if (account.getPassword().equals(info.get(2))) {
                        return "Login successfully support.";
                    }
                    return "Wrong password.";
                }
            } else if (info.get(0).equalsIgnoreCase("seller")) {
                if (account instanceof Seller && account.getUsername().equals(info.get(1))) {
                    if (account.getPassword().equals(info.get(2))) {
                        return "Login successfully seller.";
                    }
                    return "Wrong password.";
                }
            } else if (info.get(0).equalsIgnoreCase("buyer")) {
                if (account instanceof Buyer && account.getUsername().equals(info.get(1))) {
                    if (account.getPassword().equals(info.get(2))) {
                        return "Login successfully buyer.";
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

    public static void editPersonalInfo(String field, String value, Account account) {
        if (field.equalsIgnoreCase("first name")) {
            account.setFirstName(value);
        } else if (field.equalsIgnoreCase("last name")) {
            account.setLastName(value);
        } else if (field.equalsIgnoreCase("email")) {
            account.setEmailAddress(value);
        } else if (field.equalsIgnoreCase("phone Number")) {
            account.setPhoneNumber(value);
        } else if (field.equalsIgnoreCase("password")) {
            account.setPassword(value);
        } else if (field.equalsIgnoreCase("company")) {
            ((Seller) account).setCompanyName(value);
        }
    }

    public static boolean canUserBuyOrComment(Account account) {
        if (account == null) {
            return false;
        } else return account instanceof Buyer;
    }

    public static Category getCategoryByName(String name) {
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            if (category.getName().equalsIgnoreCase(name))
                return category;
        }
        return null;
    }

    public static Account getAccountByBankId(long id) {
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account instanceof Buyer) {
                if (((Buyer) account).getBankAccountId() == id)
                    return account;
            } else if (account instanceof Seller) {
                if (((Seller) account).getBankAccountId() == id)
                    return account;
            }
        }
        return null;
    }
}