package view.tableViewData;

import controller.AdminZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Seller;

import java.util.HashMap;

public class Product {
    private int id;
    private String status;
    private String name;
    private long price;
    private Seller seller;
    private int stockStatus;
    private String categoryName;
    private HashMap<String, String> categoryFeature;
    private double averageScore;

    public Product(int id, String status, String name, long price, Seller seller, int stockStatus, String categoryName,
                   HashMap<String, String> categoryFeature, double averageScore) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.stockStatus = stockStatus;
        this.categoryName = categoryName;
        this.categoryFeature = categoryFeature;
        this.averageScore = averageScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(int stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public HashMap<String, String> getCategoryFeature() {
        return categoryFeature;
    }

    public void setCategoryFeature(HashMap<String, String> categoryFeature) {
        this.categoryFeature = categoryFeature;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public static ObservableList<Product> getProducts() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        list.addAll(AdminZone.getAllProducts());
        return list;
    }
}
