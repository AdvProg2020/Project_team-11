package Client.view.tableViewData;

import Client.view.ClientHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private int id;
    private String status;
    private String name;
    private long price;
    private String seller;
    private int stockStatus;
    private String categoryName;
    private HashMap<String, String> categoryFeature;
    private double averageScore;

    public Product(int id, String status, String name, long price, String seller, int stockStatus, String categoryName,
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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
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
        try {
            ClientHandler.getDataOutputStream().writeUTF("get table products");
            ClientHandler.getDataOutputStream().flush();
            String data = ClientHandler.getDataInputStream().readUTF();
            Type foundListType = new TypeToken<ArrayList<Product>>() {}.getType();
            list.addAll(new ArrayList<>(new Gson().fromJson(data, foundListType)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
