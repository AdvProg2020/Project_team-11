package controller;

import server.Server;
import model.*;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AdminZone {

    public static ArrayList<Request> getAllRequests() {
        return DataBase.getDataBase().getAllRequests();
    }

    public static void removeRequest(int requestId) {
        Request request = getRequestById(requestId);
        DataBase.getDataBase().getAllRequests().remove(request);
    }

    public static void acceptRequest(int requestId) {
        Request request = getRequestById(requestId);
        if (request.getTopic().equalsIgnoreCase("create seller account")) {
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
        switch (splitInfo.get(1)) {
            case "Name":
                product.getGeneralFeature().setName(splitInfo.get(2));
                break;
            case "Company":
                product.getGeneralFeature().setCompany(splitInfo.get(2));
                break;
            case "Price":
                product.getGeneralFeature().setPrice(Long.parseLong(splitInfo.get(2)));
                break;
            case "Stock":
                product.getGeneralFeature().setStockStatus(Integer.parseInt(splitInfo.get(2)));
                break;
            case "Description":
                product.setDescription(splitInfo.get(2));
                break;
            default:
                product.getCategoryFeature().replace(splitInfo.get(1), splitInfo.get(2));
                break;
        }
        product.setStatus("accepted");
    }

    private static void acceptRequestCreateSellerAccount(Request request) {
        String info = request.getDescription();
        ArrayList<String> splitInfo = new ArrayList<>(Arrays.asList(info.split(",")));
        long accountId = 0;
        try {
            Socket socket = new Socket("127.0.0.1", Server.getBankServerPort());
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_account " + splitInfo.get(0) + " " + splitInfo.get(1) + " " +
                    splitInfo.get(4) + " " + splitInfo.get(5) + " " + splitInfo.get(5));
            dataOutputStream.flush();
            System.out.println(dataInputStream.readUTF()); // TODO : debug mode
            accountId = Long.parseLong(dataInputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Seller(splitInfo.get(0), splitInfo.get(1), splitInfo.get(2), splitInfo.get(3),
                splitInfo.get(4), splitInfo.get(5), splitInfo.get(6), accountId);
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
        Auction auction = getAuctionById(Integer.parseInt(splitInfo.get(2)));
        assert auction != null;
        switch (splitInfo.get(0)) {
            case "Discount":
                auction.setDiscountAmount(Integer.parseInt(splitInfo.get(1)));
                break;
            case "Start [dd/mm/yyyy hh:mm:ss]":
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = null;
                try {
                     date = format.parse(splitInfo.get(1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                auction.setStartDate(date);
                break;
            case "End [dd/mm/yyyy hh:mm:ss]":
                format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                date = null;
                try {
                    date = format.parse(splitInfo.get(1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                auction.setEndDate(date);
        }
        auction.setStatus("accepted");
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

    public static void declineRequest(int requestId) {
        Request request = getRequestById(requestId);
        if (request.getTopic().equalsIgnoreCase("create seller account"))
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
        Auction auction = getAuctionById(Integer.parseInt(request.getDescription()));
        auction.setStatus("rejected");
    }

    private static void declineRequestEditAuction(Request request) {
        Auction auction = getAuctionById(Integer.parseInt(request.getDescription().split(",")[2]));
        auction.setStatus("accepted");
    }

    public static ArrayList<Client.view.tableViewData.Account> getUsers() {
        ArrayList<Client.view.tableViewData.Account> accounts = new ArrayList<>();
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account instanceof  Buyer) {
                accounts.add(new Client.view.tableViewData.Account("Buyer", account.getFirstName(),
                        account.getLastName(), account.getEmailAddress(), account.getPhoneNumber(),
                        account.getUsername(), account.getPassword(), ((Buyer) account).getWallet(), ""));
            } else if (account instanceof Seller) {
                accounts.add(new Client.view.tableViewData.Account("Seller", account.getFirstName(),
                        account.getLastName(), account.getEmailAddress(), account.getPhoneNumber(), account.getUsername(),
                        account.getPassword(), ((Seller) account).getWallet(), ((Seller) account).getCompanyName()));
            } else if (account instanceof Admin) {
                accounts.add(new Client.view.tableViewData.Account("Admin", account.getFirstName(),
                        account.getLastName(), account.getEmailAddress(), account.getPhoneNumber(),
                        account.getUsername(), account.getPassword(), 0, ""));
            } else {
                accounts.add(new Client.view.tableViewData.Account("Support", account.getFirstName(),
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
        if (accountDeleted instanceof Buyer) {
            for (String discountCode : ((Buyer) accountDeleted).getDiscountCodes().keySet()) {
                Discount discount = getDiscountByCode(discountCode);
                discount.getAllowedUsers().remove(accountDeleted.getUsername());
            }
        } else if (accountDeleted instanceof Seller) {
            ArrayList<Auction> sellerAuctions = new ArrayList<>();
            for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
                if (auction.getSellerName().equals(accountDeleted.getUsername()))
                    sellerAuctions.add(auction);
            }
            DataBase.getDataBase().getAllAuctions().removeAll(sellerAuctions);
            ArrayList<Product> sellerProducts = new ArrayList<>();
            for (Product product : DataBase.getDataBase().getAllProducts()) {
                if (product.getGeneralFeature().getSeller().equals(accountDeleted.getUsername()))
                    sellerProducts.add(product);
            }
            DataBase.getDataBase().getAllProducts().removeAll(sellerProducts);
        }
        DataBase.getDataBase().getAllAccounts().remove(accountDeleted);
    }

    public static void removeProduct(int productId) {
        Product removedProduct = null;
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == productId) {
                removedProduct = product;
            }
        }
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
    }

    public static ArrayList<Client.view.tableViewData.Product> getAllProducts() {
        ArrayList<Client.view.tableViewData.Product> products = new ArrayList<>();
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            products.add(new Client.view.tableViewData.Product(product.getId(), product.getStatus(),
                    product.getGeneralFeature().getName(), product.getGeneralFeature().getPrice(),
                    product.getGeneralFeature().getSeller(), product.getGeneralFeature().getStockStatus(),
                    product.getCategoryName(), product.getCategoryFeature(), product.getAverageScore()));
        }
        return products;
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

    public static ArrayList<String> getDiscountCodes() {
        ArrayList<String> discountCodes = new ArrayList<>();
        for (Discount discount : DataBase.getDataBase().getAllDiscounts()) {
            discountCodes.add(discount.getCode());
        }
        return discountCodes;
    }

    public static Discount getDiscountByCode(String code) {
        for (Discount discount : DataBase.getDataBase().getAllDiscounts()) {
            if (discount.getCode().equals(code))
                return discount;
        }
        return null;
    }

    public static ArrayList<String> getDiscountInfo(String code) {
        Discount discount = getDiscountByCode(code);
        ArrayList<String> info = new ArrayList<>(Arrays.asList(discount.getCode(), discount.getStartDate().toString(),
                discount.getEndDate().toString(), String.valueOf(discount.getAmount()[0]),
                String.valueOf(discount.getAmount()[1]), String.valueOf(discount.getRepeatedTimes())));
        info.addAll(discount.getAllowedUsers());
        return info;
    }

    public static void removeDiscount(String code) {
        Discount discount = getDiscountByCode(code);
        for (String user : discount.getAllowedUsers()) {
            Buyer buyer = getBuyerByUsername(user);
            buyer.getDiscountCodes().remove(discount.getCode());
        }
        DataBase.getDataBase().getAllDiscounts().remove(discount);
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
        Category category = AllAccountZone.getCategoryByName(name);
        while (!category.getProductList().isEmpty()) {
            removeProduct(category.getProductList().get(0).getId());
        }
        DataBase.getDataBase().getAllCategories().remove(category);
    }

    public static void editDiscount(String field, String value, String code) throws ParseException {
        Discount discount = getDiscountByCode(code);
        assert discount != null;
        if (field.equals("Max Discount")) {
            discount.setMaxDiscount(Long.parseLong(value));
        } else if (field.equals("Discount Percent")) {
            discount.setDiscountPercent(Integer.parseInt(value));
        } else if (field.equals("Repeated Times")) {
            int different = discount.getRepeatedTimes() - Integer.parseInt(value);
            for (String user : discount.getAllowedUsers()) {
                AdminZone.getBuyerByUsername(user).getDiscountCodes().replace(discount.getCode(),
                        AdminZone.getBuyerByUsername(user).getDiscountCodes().get(discount.getCode()) - different);
            }
            discount.setRepeatedTimes(Integer.parseInt(value));
        } else if (field.startsWith("Start Date")) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = format.parse(value);
            discount.setStartDate(date);
        } else if (field.startsWith("End Date")) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = format.parse(value);
            discount.setEndDate(date);
        } else if (field.equals("remove user")) {
            discount.getAllowedUsers().remove(value);
            AdminZone.getBuyerByUsername(value).getDiscountCodes().remove(discount.getCode());
        } else if (field.equals("add user")) {
            discount.getAllowedUsers().add(value);
            AdminZone.getBuyerByUsername(value).addDiscountCodes(discount, discount.getRepeatedTimes());
        }
    }

    public static ArrayList<String> getCategoryFeature(String name) {
        Category category = AllAccountZone.getCategoryByName(name);
        if (category != null) {
            return category.getSpecialFeatures();
        } else {
            return new ArrayList<>();
        }
    }

    public static void editCategory(String field, String value, String categoryName, String lastFeature) {
        Category category = AllAccountZone.getCategoryByName(categoryName);
        assert category != null;
        if (field.equals("remove feature")) {
            category.getSpecialFeatures().remove(value);
            deleteFeatureFromCategory(category, value);
        } else if (field.equals("add feature")) {
            category.getSpecialFeatures().add(value);
            addNewFeatureToCategory(category, value);
        } else if (field.equals("Name")) {
            category.setName(value);
        } else if (field.equals("Feature")) {
            category.getSpecialFeatures().set(category.getSpecialFeatures().indexOf(lastFeature), value);
            AdminZone.renameFeatureOfCategory(category, lastFeature, value);
        }
    }

    public static void editBankOperation(String field, String newValue) {
        switch (field) {
            case "Commission":
                DataBase.getDataBase().getBankOperation().setCommission(Integer.parseInt(newValue));
                break;
            case "Minimum Money":
                DataBase.getDataBase().getBankOperation().setMinimumMoney(Long.parseLong(newValue));
                break;
        }
    }
}
