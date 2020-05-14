package controller;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class AdminZone {

    public static String showAllRequests() {
        StringBuilder allRequests = new StringBuilder();
        for (Request request : DataBase.getDataBase().getAllRequests()) {
                allRequests.append(request.getId()).append(". ").append(request.getTopic()).append(" -> ")
                        .append(request.getStatus()).append("\n");
        }
        return String.valueOf(allRequests);
    }

    public static String viewRequestDetails(int requestId) {
        Request request = getRequestById(requestId);
        if (request == null) {
            return "invalid request ID";
        } else {
            if (request.getSender() != null) {
                return requestId + ". " + request.getTopic() + " : \n" +
                        request.getSender().getFirstName() + " " + request.getSender().getLastName() + "\n" +
                        request.getDescription();
            } else {
                return requestId + ". " + request.getTopic() + ": \n" +
                        request.getDescription();
            }
        }
    }

    public static String acceptRequest(int requestId) {
        Request request = getRequestById(requestId);
        if (request == null || !request.getStatus().equals("unseen")) {
            return "invalid request ID";
        } else if (request.getTopic().equals("create seller account")) {
            acceptRequestCreateSellerAccount(request);
        } else if (request.getTopic().equals("edit product")) {
            acceptRequestEditProduct(request);
        } else if (request.getTopic().equals("add product")) {
            acceptRequestAddProduct(request);
        } else if (request.getTopic().equals("add auction")) {
            acceptRequestAddAuction(request);
        } else if (request.getTopic().equals("edit auction")) {
            acceptRequestEditAuction(request);
        } else if (request.getTopic().equals("add comment")) {
            acceptRequestAddComment(request);
        }
        request.setStatus("accepted");
        return "Done";
    }

    private static Request getRequestById(int requestId) {
        for (Request request : DataBase.getDataBase().getAllRequests()) {
            if (request.getId() == requestId)
                return request;
        }
        return null;
    }

    private static void acceptRequestEditProduct(Request request) {
        String info = request.getDescription();
        ArrayList<String> splitInfo = new ArrayList<>(Arrays.asList(info.split(",")));
        Product product = getProductByIdAndSeller(Integer.parseInt(splitInfo.get(0)), (Seller) request.getSender());
        assert product != null;
        if (!splitInfo.get(1).equals("next"))
            product.getGeneralFeature().setName(splitInfo.get(1));
        if (!splitInfo.get(2).equals("next"))
            product.getGeneralFeature().setCompany(splitInfo.get(2));
        if (!splitInfo.get(3).equals("next"))
            product.getGeneralFeature().setPrice(Long.parseLong(splitInfo.get(3)));
        if (!splitInfo.get(4).equals("next"))
            product.getGeneralFeature().setStockStatus(Integer.parseInt(splitInfo.get(4)));
        if (!splitInfo.get(5).equals("next"))
            product.setDescription(splitInfo.get(5));
    }

    private static void acceptRequestCreateSellerAccount(Request request) {
        String info = request.getDescription();
        ArrayList<String> splitInfo = new ArrayList<>(Arrays.asList(info.split(",")));
        new Seller(splitInfo.get(0), splitInfo.get(1), splitInfo.get(2), splitInfo.get(3),
                splitInfo.get(4), splitInfo.get(5), splitInfo.get(6),Long.parseLong(splitInfo.get(7)));
    }

    private static void acceptRequestAddProduct(Request request) {
        String info = request.getDescription();
        int productId = Integer.parseInt(info);
        Product product = getProductByIdAndSeller(productId, (Seller) request.getSender());
        assert product != null;
        product.setStatus("accepted");
    }

    private static void acceptRequestAddAuction(Request request) {
        String info = request.getDescription();
        int auctionId = Integer.parseInt(info);
        Auction auction = getAuctionById(auctionId);
        assert auction != null;
        auction.setStatus("accepted");
    }

    private static void acceptRequestEditAuction(Request request) {
        String info = request.getDescription();
        ArrayList<String> splitInfo = new ArrayList<>(Arrays.asList(info.split(",")));
        Auction auction = getAuctionById(Integer.parseInt(splitInfo.get(0)));
        assert auction != null;
        if (!splitInfo.get(1).equals("next")) {
            ArrayList<String> productsId = new ArrayList<>(Arrays.asList(splitInfo.get(1).split("/")));
            ArrayList<Product> productList = new ArrayList<>();
            for (String productId : productsId) {
                Product product = getProductByIdAndSeller(Integer.parseInt(productId), (Seller) request.getSender());
                productList.add(product);
            }
            auction.setProductList(productList);
        }
        if (!splitInfo.get(2).equals("next")) {
            long milliSeconds = Long.parseLong(splitInfo.get(2));
            Date startDate = new Date(milliSeconds);
            auction.setStartDate(startDate);
        }
        if (!splitInfo.get(3).equals("next")) {
            long milliSeconds = Long.parseLong(splitInfo.get(3));
            Date endDate = new Date(milliSeconds);
            auction.setEndDate(endDate);
        }
        if (!splitInfo.get(4).equals("next")) {
            auction.setDiscountAmount(Integer.parseInt(splitInfo.get(4)));
        }
    }

    private static void acceptRequestAddComment(Request request) {
        Comment comment = Comment.getCommentById(Integer.parseInt(request.getDescription()));
        comment.setStatus("accepted");
    }

    public static Product getProductByIdAndSeller(int productId, Seller seller) {
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == productId && product.getGeneralFeature().getSeller().equals(seller))
                return product;
        }
        return null;
    }

    private static Auction getAuctionById(int auctionId) {
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            if (auction.getId() == auctionId)
                return auction;
        }
        return null;
    }

    public static String declineRequest(int requestId) {
        Request request = getRequestById(requestId);
        if (request == null || !request.getStatus().equals("unseen"))
            return "invalid request ID";
        else if (request.getTopic().equals("add comment"))
            declineRequestAddComment(request);
        else
            request.setStatus("declined");
        return "Done";
    }

    private static void declineRequestAddComment(Request request) {
        Comment comment = Comment.getCommentById(Integer.parseInt(request.getDescription()));
        comment.setStatus("rejected");
    }

    public static String showUsersInfo() {
        StringBuilder output = new StringBuilder();
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account instanceof Buyer || account instanceof Seller) {
                output.append(account.getUsername()).append("\n");
            }
        }
        return output.toString();
    }

    public static String showUserInfo(String username) {
        String accountType;
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account instanceof  Buyer) {
                accountType = "Buyer";
            } else {
                accountType = "Seller";
            }
            if (account.getUsername().equalsIgnoreCase(username)) {
                return accountType + " : \nName : " + account.getFirstName() + " " + account.getLastName() + "\nEmail : " +
                        account.getEmailAddress() + "\nPhone number : " + account.getPhoneNumber();
            }
        }
        return "invalid username";
    }

    public static String deleteUser(String username) {
        String output = "";
        Account accountDeleted = null;
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                output = username + "deleted.";
                accountDeleted = account;
            }
        }
        DataBase.getDataBase().getAllAccounts().remove(accountDeleted);
        if (output.equals(""))
            output = "invalid username";
        return output;
    }

    public static void createAdminProfile(ArrayList<String> info) {
        new Admin(info.get(0), info.get(1), info.get(2), info.get(3), info.get(4), info.get(5));
    }

    public static String removeProduct(int productId) {
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == productId) {
                products.add(product);
            }
        }
        DataBase.getDataBase().getAllProducts().removeAll(products);
        if (products.isEmpty())
            return "invalid product ID.";
        else
            return "product removed successfully.";
    }

    public static String showAllProducts() {
        StringBuilder output = new StringBuilder();
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            output.append(product.getId()).append(". ").append(product.getGeneralFeature().getName()).append("\n");
        }
        return output.toString();
    }

    public static Buyer getBuyerByUsername(String username) {
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account instanceof Buyer && account.getUsername().equals(username))
                return (Buyer) account;
        }
        return null;
    }

    public static void createDiscount(ArrayList<String> info, ArrayList<String> usernames) {
        Date startDate = new Date(Long.parseLong(info.get(1)));
        Date endDate = new Date(Long.parseLong(info.get(2)));
        long[] amount = new long[2];
        amount[0] = Long.parseLong(info.get(3));
        amount[1] = Long.parseLong(info.get(4));
        ArrayList<Buyer> users = new ArrayList<>();
        for (String username : usernames) {
            users.add(getBuyerByUsername(username));
        }
        new Discount(info.get(0), startDate, endDate, amount, Integer.parseInt(info.get(5)), users);
    }

    public static String showDiscounts() {
        StringBuilder output = new StringBuilder();
        for (Discount discount : DataBase.getDataBase().getAllDiscounts()) {
            output.append("Code : '").append(discount.getCode()).append("' ").append(discount.getAmount()[0])
                    .append("% discount, at most : ").append(discount.getAmount()[1]).append("$");
        }
        return output.toString();
    }

    public static Discount getDiscountByCode(String code) {
        for (Discount discount : DataBase.getDataBase().getAllDiscounts()) {
            if (discount.getCode().equals(code))
                return discount;
        }
        return null;
    }

    public static String showDiscountInfo(String code) {
        Discount discount = getDiscountByCode(code);
        StringBuilder usernames = new StringBuilder();
        for (Buyer user : discount.getAllowedUsers()) {
            usernames.append(user.getUsername()).append(", ");
        }
        return discount.getAmount()[0] + "% discount, at most : " + discount.getAmount()[1] + "$ from \"" +
                discount.getStartDate() + "\" to \"" + discount.getEndDate() + "\" " + discount.getRepeatedTimes() +
                " times for " + usernames;
    }

    public static String removeDiscount(String code) {
        Discount discount = getDiscountByCode(code);
        if (discount == null) {
            return "invalid code";
        } else {
            for (Buyer user : discount.getAllowedUsers()) {
                user.getDiscountCodes().remove(discount);
            }
            DataBase.getDataBase().getAllDiscounts().remove(discount);
            return "Done.";
        }
    }

    public static void addNewFeatureToCategory(Category category, String feature) {
        for (Product product : category.getProductList()) {
            product.getSpecialFeature().put(feature, "");
        }
    }

    public static void deleteFeatureFromCategory(Category category, String feature) {
        for (Product product : category.getProductList()) {
            product.getSpecialFeature().remove(feature);
        }
    }

    public static void renameFeatureOfCategory(Category category, String lastFeature, String feature) {
        for (Product product : category.getProductList()) {
            String value = product.getSpecialFeature().get(lastFeature);
            product.getSpecialFeature().remove(lastFeature);
            product.getSpecialFeature().put(feature, value);
        }
    }

    public static void createCategory(String name, ArrayList<String> features, ArrayList<Integer> productList) {
        ArrayList<Product> products = new ArrayList<>();
        for (Integer productId : productList) {
            products.add(SellerZone.getProductById(productId));
        }
        new Category(name, features, products);
    }

    public static void removeCategory(String name) {
        Category category = Category.getCategoryByName(name);
        for (Product product : category.getProductList()) {
            product.setCategory(null);
            product.setSpecialFeature(new HashMap<>());
        }
        DataBase.getDataBase().getAllCategories().remove(category);
    }
}
