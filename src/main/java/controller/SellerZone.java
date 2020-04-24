package controller;

import model.*;

public class SellerZone {

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
                for (Product soldProduct : ((SellLog) log).getSoldProducts()) {
                    if (soldProduct.equals(product)) {
                        buyersList.append(((SellLog) log).getBuyer());
                    }
                }
            }
        }
        return String.valueOf(buyersList);
    }

    public static String viewSellerProduct(int productId) {
        Product product = AllAccountZone.getProductById(productId);
        if (product == null || product.getGeneralFeature().getSeller() != AllAccountZone.getCurrentAccount()) {
            return "You haven't this product.";
        } else {
            return product.toString();
        }
    }

    public static String viewProductBuyers(int productId) {
        Product product = AllAccountZone.getProductById(productId);
        if (product == null || product.getGeneralFeature().getSeller() != AllAccountZone.getCurrentAccount()) {
            return "You haven't this product.";
        } else {
            return getProductBuyers(product);
        }
    }

    public static String editProduct(int productId) {
        Product product = AllAccountZone.getProductById(productId);
        if (product == null || product.getGeneralFeature().getSeller() != AllAccountZone.getCurrentAccount()) {
            return "You haven't this product.";
        } else {
            return "Edit";
        }
    }

    public static void sendEditProductRequest(String description) {
        new Request((Buyer) AllAccountZone.getCurrentAccount(), "edit product", description, "unseen");
    }
}
