package controller;

import model.*;

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
        Product product = getProductById(productId);
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
}
