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

    public ArrayList<String> getSpecialFeature() {
        return specialFeature;
    }

    public String getDescription() {
        return description;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", generalFeature=" + generalFeature +
                ", category=" + category +
                ", specialFeature=" + specialFeature +
                ", description='" + description + '\'' +
                ", averageScore=" + averageScore +
                ", comments=" + comments +
                '}';
    }
}
