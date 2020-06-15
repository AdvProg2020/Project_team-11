package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private final int id;
    private String status;
    private final ProductInfo generalFeature;
    private String categoryName;
    private HashMap<String, String> categoryFeature;
    private String description;
    private double averageScore;
    private int numOfUsersRated;
    private ArrayList<Comment> comments;
    private static int numOfAllProducts;

    public Product(String status, ProductInfo generalFeature, String categoryName,
                   HashMap<String, String> categoryFeature, String description) {
        this.id = numOfAllProducts;
        numOfAllProducts += 1;
        this.status = status;
        this.generalFeature = generalFeature;
        this.categoryName = categoryName;
        this.categoryFeature = new HashMap<>(categoryFeature);
        this.description = description;
        this.averageScore = 0;
        this.numOfUsersRated = 0;
        this.comments = new ArrayList<>();
        DataBase.getDataBase().setAllProducts(this);
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public ProductInfo getGeneralFeature() {
        return generalFeature;
    }

    public String  getCategoryName() {
        return categoryName;
    }

    public HashMap<String, String> getCategoryFeature() {
        return categoryFeature;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public int getNumOfUsersRated() {
        return numOfUsersRated;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public void addNumOfUsersRated() {
        this.numOfUsersRated += 1;
    }

    public void addComments(Comment comments) {
        this.comments.add(comments);
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryFeature(HashMap<String, String> categoryFeature) {
        this.categoryFeature = categoryFeature;
    }

    public static void setNumOfAllProducts(int numOfAllProducts) {
        Product.numOfAllProducts = numOfAllProducts;
    }

    public void setNumOfUsersRated(int numOfUsersRated) {
        this.numOfUsersRated = numOfUsersRated;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "id = " + id +
                ", status = '" + status + '\'' +
                ", generalFeature = " + generalFeature +
                ", category = " + categoryName +
                ", specialFeature = " + categoryFeature +
                ", description = '" + description + '\'' +
                ", averageScore = " + averageScore +
                ", comments = " + comments +
                '}';
    }
}
