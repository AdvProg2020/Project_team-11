package view.menu.productsMenu;

import java.util.HashMap;
import java.util.Map;

public class FilterInfo {
    private String category;
    private long minimumPrice;
    private long maximumPrice;
    private String productName;
    private String sellerName;
    private String company;
    private int minimumStockStatus;
    private HashMap<String, String> feature;

    public FilterInfo(String category, long minimumPrice, long maximumPrice, String productName, String sellerName,
                      String company, int minimumStockStatus, HashMap<String, String> feature) {
        this.category = category;
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
        this.productName = productName;
        this.sellerName = sellerName;
        this.company = company;
        this.minimumStockStatus = minimumStockStatus;
        this.feature = feature;
    }

    public String getCategory() {
        return category;
    }

    public long getMinimumPrice() {
        return minimumPrice;
    }

    public long getMaximumPrice() {
        return maximumPrice;
    }

    public HashMap<String, String> getFeature() {
        return feature;
    }

    public String getProductName() {
        return productName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getCompany() {
        return company;
    }

    public int getMinimumStockStatus() {
        return minimumStockStatus;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMinimumPrice(long minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public void setMaximumPrice(long maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public void setFeature(HashMap<String, String> feature) {
        this.feature = feature;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setMinimumStockStatus(int minimumStockStatus) {
        this.minimumStockStatus = minimumStockStatus;
    }

    @Override
    public String toString() {
        return "category='" + category + "'\n" +
                "feature=" + getFeatureString() + "\n" +
                "minimumPrice=" + minimumPrice + "\n" +
                "maximumPrice=" + maximumPrice + "\n" +
                "productName='" + productName + "'\n" +
                "sellerName='" + sellerName + "'\n" +
                "company='" + company + "'\n" +
                "minimumStockStatus=" + minimumStockStatus;
    }

    private String getFeatureString() {
        StringBuilder features = new StringBuilder();
        for (Map.Entry<String, String> entry : this.feature.entrySet()) {
            if (!entry.getValue().equals("")) {
                features.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
            }
        }
        return String.valueOf(features);
    }
}
