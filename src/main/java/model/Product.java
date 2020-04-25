package model;

import java.util.ArrayList;

public class Product {
    private int id;
    private String status;
    private ProductInfo generalFeature;
    private Category category;
    private ArrayList<String> specialFeature;
    private String description;
    private int averageScore;
    private ArrayList<Comment> comments;

    public Product(int id, String status, ProductInfo generalFeature,
                   Category category, ArrayList<String> specialFeature,
                   String description, int averageScore, ArrayList<Comment> comments) {
        this.id = id;
        this.status = status;
        this.generalFeature = generalFeature;
        this.category = category;
        this.specialFeature = new ArrayList<>(specialFeature);
        this.description = description;
        this.averageScore = averageScore;
        this.comments = new ArrayList<>(comments);
        DataBase.getDataBase().setAllProducts(this);
    }
}
