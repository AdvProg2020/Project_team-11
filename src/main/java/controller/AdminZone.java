package controller;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
            return requestId + ". " + request.getTopic() + " : \n" +
                    AllAccountZone.getAccountByUsername(request.getSenderName()).getFirstName() + " " +
                    AllAccountZone.getAccountByUsername(request.getSenderName()).getLastName() + "\n" +
                    request.getDescription();
        }
    }

    public static String acceptRequest(int requestId) {
        Request request = getRequestById(requestId);
        if (request == null || !request.getStatus().equals("unseen")) {
            return "invalid request ID";
        } else if (request.getTopic().equalsIgnoreCase("create seller account")) {
            acceptRequestCreateSellerAccount(request);
        } else if (request.getTopic().equalsIgnoreCase("edit product")) {
            acceptRequestEditProduct(request);
        } else if (request.getTopic().equalsIgnoreCase("add product")) {
            acceptRequestAddProduct(request);
        } else if (request.getTopic().equalsIgnoreCase("add auction")) {
            acceptRequestAddAuction(request);
        } else if (request.getTopic().equalsIgnoreCase("edit auction")) {
            acceptRequestEditAuction(request);
        } else if (request.getTopic().equalsIgnoreCase("add comment")) {
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
        Product product = SellerZone.getProductById(Integer.parseInt(splitInfo.get(0)));
        assert product != null;
        if (!splitInfo.get(1).equals("next"))
            product.getGeneralFeature().setName(splitInfo.get(1));
        if (!splitInfo.get(2).equals("next"))
            product.getGeneralFeature().setCompany(splitInfo.get(2));
        if (!splitInfo.get(3).equals("next"))
            product.getGeneralFeature().setPrice(Long.parseLong(splitInfo.get(3)));
        if (!splitInfo.get(4).equals("next"))
            product.getGeneralFeature().setStockStatus(Integer.parseInt(splitInfo.get(4)));
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
        Product product = SellerZone.getProductById(productId);
        Category category = SellerZone.getCategoryByName(product.getCategoryName());
        category.addProductList(product);
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
                Product product = SellerZone.getProductById(Integer.parseInt(productId));
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
        else if (request.getTopic().equalsIgnoreCase("create seller account"))
            declineRequestCreateSellerAccount(request);
        else if (request.getTopic().equalsIgnoreCase("add comment"))
            declineRequestAddComment(request);
        else if (request.getTopic().equalsIgnoreCase("add product"))
            declineRequestAddProduct(request);
        else if (request.getTopic().equalsIgnoreCase("edit product"))
            declineRequestEditProduct(request);
        else if (request.getTopic().equalsIgnoreCase("add auction"))
            declineRequestAddAuction(request);
        else if (request.getTopic().equalsIgnoreCase("edit auction"))
            declineRequestEditAuction(request);
            request.setStatus("declined");
        return "Done";
    }

    private static void declineRequestCreateSellerAccount(Request request) {
        request.setStatus("declined");
    }

    private static void declineRequestAddComment(Request request) {
        Comment comment = Comment.getCommentById(Integer.parseInt(request.getDescription()));
        comment.setStatus("rejected");
    }

    private static void declineRequestAddProduct(Request request) {
        Product product = SellerZone.getProductById(Integer.parseInt(request.getDescription()));
        product.setStatus("rejected");
    }

    private static void declineRequestEditProduct(Request request) {
        Product product = SellerZone.getProductById(Integer.parseInt(request.getDescription().split(",")[0]));
        product.setStatus("accepted");
    }

    private static void declineRequestAddAuction(Request request) {
        Auction auction = SellerZone.getAuctionById(Integer.parseInt(request.getDescription()));
        auction.setStatus("rejected");
    }

    private static void declineRequestEditAuction(Request request) {
        Auction auction = SellerZone.getAuctionById(Integer.parseInt(request.getDescription().split(",")[0]));
        auction.setStatus("accepted");
    }

    public static ArrayList<view.tableViewData.Account> getUsers() {
        ArrayList<view.tableViewData.Account> accounts = new ArrayList<>();
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account instanceof  Buyer) {
                accounts.add(new view.tableViewData.Account("Buyer", account.getFirstName(),
                        account.getLastName(), account.getEmailAddress(), account.getPhoneNumber(),
                        account.getUsername(), account.getPassword(), ((Buyer) account).getWallet(), ""));
            } else if (account instanceof Seller) {
                accounts.add(new view.tableViewData.Account("Seller", account.getFirstName(),
                        account.getLastName(), account.getEmailAddress(), account.getPhoneNumber(), account.getUsername(),
                        account.getPassword(), ((Seller) account).getWallet(), ((Seller) account).getCompanyName()));
            } else {
                accounts.add(new view.tableViewData.Account("Admin", account.getFirstName(),
                        account.getLastName(), account.getEmailAddress(), account.getPhoneNumber(),
                        account.getUsername(), account.getPassword(), 0, ""));
            }
        }
        return accounts;
    }

    public static void deleteUser(String username) {
        Account accountDeleted = null;
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                accountDeleted = account;
            }
        }
        if (accountDeleted == null)
            return;
        if (accountDeleted instanceof Seller) {
            ArrayList<Auction> sellerAuctions = new ArrayList<>();
            for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
                if (auction.getSellerName().equals(accountDeleted.getUsername()))
                    sellerAuctions.add(auction);
            }
            DataBase.getDataBase().getAllAuctions().removeAll(sellerAuctions);
            ArrayList<Product> sellerProducts = new ArrayList<>();
            for (Product product : DataBase.getDataBase().getAllProducts()) {
                if (product.getGeneralFeature().getSeller().getUsername().equals(accountDeleted.getUsername()))
                    sellerProducts.add(product);
            }
            DataBase.getDataBase().getAllProducts().removeAll(sellerProducts);
        }
        DataBase.getDataBase().getAllAccounts().remove(accountDeleted);
    }

    public static void createAdminProfile(ArrayList<String> info) {
        new Admin(info.get(0), info.get(1), info.get(2), info.get(3), info.get(4), info.get(5));
    }

    public static String removeProduct(int productId) {
        Product removedProduct = null;
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == productId) {
                removedProduct = product;
            }
        }
        if (removedProduct == null)
            return "invalid product ID.";
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            auction.getProductList().remove(removedProduct);
        }
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            category.getProductList().remove(removedProduct);
        }
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment comment : DataBase.getDataBase().getAllComments()) {
            if (comment.getProductId() == productId)
                comments.add(comment);
        }
        DataBase.getDataBase().getAllComments().removeAll(comments);
        ArrayList<Rate> rates = new ArrayList<>();
        for (Rate rate : DataBase.getDataBase().getAllRates()) {
            if (rate.getProductId() == productId)
                rates.add(rate);
        }
        DataBase.getDataBase().getAllRates().removeAll(rates);
        DataBase.getDataBase().getAllProducts().remove(removedProduct);
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
        Discount discount = new Discount(info.get(0), startDate, endDate, amount, Integer.parseInt(info.get(5)), usernames);
        for (Buyer user : users) {
            user.addDiscountCodes(discount, discount.getRepeatedTimes());
        }
    }

    public static String showDiscounts() {
        StringBuilder output = new StringBuilder();
        for (Discount discount : DataBase.getDataBase().getAllDiscounts()) {
            output.append("Code : '").append(discount.getCode()).append("' ").append(discount.getAmount()[0])
                    .append("% discount, at most : ").append(discount.getAmount()[1]).append("$").append("\n");
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
        for (String user : discount.getAllowedUsers()) {
            usernames.append(user).append(",");
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
            for (String user : discount.getAllowedUsers()) {
                Buyer buyer = getBuyerByUsername(user);
                buyer.getDiscountCodes().remove(discount.getCode());
            }
            DataBase.getDataBase().getAllDiscounts().remove(discount);
            return "Done.";
        }
    }

    public static void addNewFeatureToCategory(Category category, String feature) {
        for (Product product : category.getProductList()) {
            product.getCategoryFeature().put(feature, "");
        }
    }

    public static void deleteFeatureFromCategory(Category category, String feature) {
        for (Product product : category.getProductList()) {
            product.getCategoryFeature().remove(feature);
        }
    }

    public static void renameFeatureOfCategory(Category category, String lastFeature, String feature) {
        for (Product product : category.getProductList()) {
            String value = product.getCategoryFeature().get(lastFeature);
            product.getCategoryFeature().remove(lastFeature);
            product.getCategoryFeature().put(feature, value);
        }
    }

    public static void createCategory(String name, ArrayList<String> features) {
        new Category(name, features);
    }

    public static void removeCategory(String name) {
        Category category = Category.getCategoryByName(name);
        while (!category.getProductList().isEmpty()) {
            removeProduct(category.getProductList().get(0).getId());
        }
        DataBase.getDataBase().getAllCategories().remove(category);
    }

    public static boolean isCategoryNameValid(String categoryName) {
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            if (category.getName().equals(categoryName)){
                return false;
            }
        }
        return true;
    }
}
