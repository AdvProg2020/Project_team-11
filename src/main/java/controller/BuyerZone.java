package controller;

import model.Buyer;
import model.Product;

import java.util.Map;

public class BuyerZone {

    public static String showProductsInCart() {
        StringBuilder productList = new StringBuilder();
        for (Product product : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().keySet()) {
            productList.append(product.getId()).append(". ").append(product.getGeneralFeature().getName()).
                    append(" number: ").append(((Buyer) AllAccountZone.getCurrentAccount()).getCart().get(product)).
                    append(" ").append(product.getGeneralFeature().getPrice()).append("$ ").
                    append(product.getGeneralFeature().getCompany()).append(" Average Score: ").
                    append(product.getAverageScore()).append("\n");
        }
        return String.valueOf(productList);
    }

    public static String changeNumberOFProductInCart(int productId, int number) {
        for (Map.Entry<Product, Integer> entry : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().entrySet()) {
            if (entry.getKey().getId() == productId) {
                entry.setValue(entry.getValue() + number);
                return "Done";
            }
        }
        return "You haven't this product";
    }

    public static int calculateTotalPrice() {
        int totalPrice = 0;
        for (Product product : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().keySet()) {
            totalPrice += product.getGeneralFeature().getPrice();
        }
        return totalPrice;
    }
}
