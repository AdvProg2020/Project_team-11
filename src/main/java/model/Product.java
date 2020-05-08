package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private int id;
    private String status;
    private ProductInfo generalFeature;
    private Category category;
    private HashMap<String, String> specialFeature;
    private String description;
    private double averageScore;
    private int numOfUsersRated;
    private ArrayList<Comment> comments;
    private static int numOfAllProducts;

    public Product(String status, ProductInfo generalFeature, Category category,
                   HashMap<String, String> specialFeature, String description, ArrayList<Comment> comments) {
        this.id = numOfAllProducts;
        numOfAllProducts += 1;
        this.status = status;
        this.generalFeature = generalFeature;
        this.category = category;
        this.specialFeature = new HashMap<>(specialFeature);
        this.description = description;
        this.averageScore = 0;
        this.numOfUsersRated = 0;
        this.comments = new ArrayList<>(comments);
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

    public Category getCategory() {
        return category;
    }

    public HashMap<String, String> getSpecialFeature() {
        return specialFeature;
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

    @Override
    public String toString() {
        return "id = " + id +
                ", status = '" + status + '\'' +
                ", generalFeature = " + generalFeature +
                ", category = " + category +
                ", specialFeature = " + specialFeature +
                ", description = '" + description + '\'' +
                ", averageScore = " + averageScore +
                ", comments = " + comments +
                '}';
    }
}
