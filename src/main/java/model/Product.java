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
    private static int numOfAllProducts = 1;

    public Product(int id, String status, ProductInfo generalFeature, Category category,
                   HashMap<String, String> specialFeature, String description) {
        if (id == 0) {
            this.id = numOfAllProducts;
            numOfAllProducts += 1;
        } else {
            this.id = id;
        }
        this.status = status;
        this.generalFeature = generalFeature;
        this.category = category;
        this.specialFeature = new HashMap<>(specialFeature);
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSpecialFeature(HashMap<String, String> specialFeature) {
        this.specialFeature = specialFeature;
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
