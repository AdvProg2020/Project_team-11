package model;

import java.util.ArrayList;

public class Category {
    private String name;
    private final ArrayList<String> specialFeatures;
    private final ArrayList<Product> productList;

    public Category(String name, ArrayList<String> specialFeatures) {
        this.name = name;
        this.specialFeatures = new ArrayList<>(specialFeatures);
        this.productList = new ArrayList<>();
        DataBase.getDataBase().setAllCategories(this);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSpecialFeatures() {
        return specialFeatures;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void addProductList(Product product) {
        this.productList.add(product);
    }

    public void setName(String name) {
        this.name = name;
    }
}
