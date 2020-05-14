package controller;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class SellerZone {

    public static Product getProductById(int Id) {
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == Id &&
                    product.getGeneralFeature().getSeller().equals(AllAccountZone.getCurrentAccount()))
                return product;
        }
        return null;
    }

    public static String getSellerProducts() {
        StringBuilder productList = new StringBuilder();
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getGeneralFeature().getSeller().equals(AllAccountZone.getCurrentAccount())) {
                productList.append(product.getId()).append(". ").append(product.getGeneralFeature().getName()).
                        append(": ").append(product.getGeneralFeature().getPrice()).append("$ ").
                        append(product.getGeneralFeature().getCompany()).append(" Average Score: ").
                        append(product.getAverageScore()).append("\n");
            }
        }
        return String.valueOf(productList);
    }

    private static String getProductBuyers(Product product) {
        StringBuilder buyersList = new StringBuilder();
        for (ExchangeLog log : DataBase.getDataBase().getAllLogs()) {
            if (log instanceof SellLog) {
                if (((SellLog) log).getSoldProducts().equals(product)) {
                    buyersList.append(((SellLog) log).getBuyerName());
                }
            }
        }
        return String.valueOf(buyersList);
    }

    public static String viewSellerProduct(int productId) {
        Product product = getProductById(productId);
        if (product == null) {
            return "You haven't this product.";
        } else {
            return product.toString();
        }
    }

    public static String viewProductBuyers(int productId) {
        Product product = getProductById(productId);
        if (product == null) {
            return "You haven't this product.";
        } else {
            return getProductBuyers(product);
        }
    }

    public static String editProduct(int productId) {
        Product product = AdminZone.getProductByIdAndSeller(productId, (Seller) AllAccountZone.getCurrentAccount());
        if (product == null) {
            return "You haven't this product.";
        } else {
            return "Edit";
        }
    }

    public static void sendEditProductRequest(String description) {
        Request request = new Request((Seller) AllAccountZone.getCurrentAccount(), "edit product", description,
                "unseen");
        DataBase.getDataBase().setAllRequests(request);
    }

    public static String getSellerHistory() {
        StringBuilder output = new StringBuilder();
        for (SellLog sellLog : ((Seller) AllAccountZone.getCurrentAccount()).getSellHistory()) {
            output.append(sellLog.getId()).append(". at ").append(sellLog.getDate()).append(sellLog.getSoldProducts())
                    .append(" sold to ").append(sellLog.getBuyerName()).append(" ").append(sellLog.getReceivedAmount())
                    .append("$ with discount ").append(sellLog.getReducedAmountForAuction()).append("$ ")
                    .append(sellLog.getSendingStatus());
        }
        return output.toString();
    }

    public static String getCompanyInfo() {
        return ((Seller) AllAccountZone.getCurrentAccount()).getCompanyName();
    }

    public static Category getCategoryByName(String name) {
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            if (category.getName().equalsIgnoreCase(name))
                return category;
        }
        return null;
    }

    public static void sendAddProductRequest(int productId, Category category, HashMap<String, String> descriptions,
                                             HashMap<String, String> categoryFeature) {
        new Request((Seller) AllAccountZone.getCurrentAccount(), "add existing product",
                String.valueOf(productId), "unseen");
        ProductInfo productInfo = new ProductInfo(descriptions.get("name"), descriptions.get("company"),
                Long.parseLong(descriptions.get("price")), (Seller) AllAccountZone.getCurrentAccount(),
                Integer.parseInt(descriptions.get("stock status")));
        new Product(productId, "construction", productInfo, category, categoryFeature,
                descriptions.get("description"));
    }

    public static String showSellerAuctions() {
        Seller seller = (Seller) AllAccountZone.getCurrentAccount();
        StringBuilder output = new StringBuilder();
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            if (auction.getSeller().getUsername().equals(seller.getUsername())) {
                output.append(auction.getId()).append(".").append(" discount : ")
                        .append(auction.getDiscountAmount()).append("%");
            }
        }
        return output.toString();
    }

    public static String showSellerAuction(int auctionId) {
        Seller seller = (Seller) AllAccountZone.getCurrentAccount();
        StringBuilder output = new StringBuilder();
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            if (auction.getId() == auctionId && auction.getSeller().getUsername().equals(seller.getUsername()))
                output.append(auction.getId()).append(". from ").append(auction.getStartDate()).append(" to ")
                        .append(auction.getEndDate()).append(" for ").append(auction.getProductList())
                        .append(" discount : ").append(auction.getDiscountAmount()).append("%");
        }
        return output.toString();
    }

    public static Auction getAuctionById(int auctionId) {
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            if (auction.getSeller().equals(AllAccountZone.getCurrentAccount()) && auction.getId() == auctionId) {
                return auction;
            }
        }
        return null;
    }

    public static void sendEditAuctionRequest(int auctionId, String description) {
        new Request((Seller) AllAccountZone.getCurrentAccount(), "edit auction", description, "unseen");
        Auction auction = getAuctionById(auctionId);
        auction.setStatus("editing");
    }

    public static void createAuction(String info) {
        Seller seller = (Seller) AllAccountZone.getCurrentAccount();
        ArrayList<String> splitInfo = new ArrayList<>(Arrays.asList(info.split(",")));
        ArrayList<String> productsId = new ArrayList<>(Arrays.asList(splitInfo.get(0).split("/")));
        ArrayList<Product> productList = new ArrayList<>();
        for (String productId : productsId) {
            Product product = AdminZone.getProductByIdAndSeller(Integer.parseInt(productId), seller);
            productList.add(product);
        }
        long startDateMilliSec = Long.parseLong(splitInfo.get(1));
        long endDateMilliSec = Long.parseLong(splitInfo.get(2));
        Auction auction = new Auction(productList, "construction", new Date(startDateMilliSec),
                new Date(endDateMilliSec), Integer.parseInt(splitInfo.get(3)), seller);
        new Request((Seller) AllAccountZone.getCurrentAccount(), "add auction", String.valueOf(auction.getId()),
                "unseen");
    }
}
