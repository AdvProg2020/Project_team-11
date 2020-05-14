package view.menu.productsMenu;

import java.util.HashMap;
import java.util.Map;

public class FilterInfo {
    private String category;
    private long minimumPrice;
    private long maximumPrice;
    private HashMap<String, String> feature;

    public FilterInfo(String category, long minimumPrice, long maximumPrice, HashMap<String, String> feature) {
        this.category = category;
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
        this.feature = new HashMap<>(feature);
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

    @Override
    public String toString() {
        return  "category = '" + category + "'\n" +
                "feature = " + getFeatureString() + "\n" +
                "minimumPrice = " + minimumPrice + "\n" +
                "maximumPrice = " + maximumPrice;
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
