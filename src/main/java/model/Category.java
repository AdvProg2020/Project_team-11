package model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<String> specialFeatures;
//    private ArrayList<Category> subCategories; this is optional. think about it after ending main things.
    private ArrayList<Product> productList;
    private static ArrayList<Category> allCategories = new ArrayList<>();

    public Category(String name, ArrayList<String> specialFeatures, ArrayList<Product> productList) {
        this.name = name;
        this.specialFeatures = new ArrayList<>(specialFeatures);
        this.productList = new ArrayList<>(productList);
        allCategories.add(this);
    }
}
