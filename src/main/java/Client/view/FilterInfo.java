package Client.view;

import java.util.HashMap;

public class FilterInfo {
    private String category;
    private long minimumPrice;
    private long maximumPrice;
    private String searchBar;
    private int minimumStockStatus;
    private HashMap<String, String> feature;

    public FilterInfo(String category, long minimumPrice, long maximumPrice, String searchBar,
                      int minimumStockStatus, HashMap<String, String> feature) {
        this.category = category;
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
        this.searchBar = searchBar;
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

    public String getSearchBar() {
        return searchBar;
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

    public void setSearchBar(String searchBar) {
        this.searchBar = searchBar;
    }

    public void setMinimumStockStatus(int minimumStockStatus) {
        this.minimumStockStatus = minimumStockStatus;
    }
}
