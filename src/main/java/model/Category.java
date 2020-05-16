package model;

import java.util.ArrayList;

public class Category {
    private String name;
    private final ArrayList<String> specialFeatures;
//    private ArrayList<Category> subCategories; this is optional. think about it after ending main things.
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

    public static Category getCategoryByName(String name) {
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            if (category.getName().equalsIgnoreCase(name))
                return category;
        }
        return null;
    }
}
