package view.menu.adminMenu;

import controller.AdminZone;
import controller.AllAccountZone;
import controller.SellerZone;
import model.Category;
import view.menu.Menu;

import java.util.ArrayList;

public class ManageCategoriesMenu extends Menu {

    public ManageCategoriesMenu(Menu parentMenu) {
        super("Manage Categories", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getEditCategoryMenu());
        submenus.add(getAddCategoryMenu());
        submenus.add(getRemoveCategoryMenu());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println(AllAccountZone.showCategories());
        super.execute();
    }

    private Menu getEditCategoryMenu() {
        return new Menu("Edit Category", this) {
            @Override
            public void execute() {
                String categoryName = checkInput("Enter category name", ".+");
                try {
                    Category category = Category.getCategoryByName(categoryName);
                    System.out.println("Which field do you want to edit? [name - feature - product list]");
                    String field = scanner.nextLine().trim();
                    String newField;
                    if (field.equalsIgnoreCase("name")) {
                        newField = checkInput("Enter name", ".+");
                        category.setName(newField);
                    } else if (field.equalsIgnoreCase("feature")) {
                        System.out.println("Do you want to [add - remove - rename] a feature?");
                        String state = scanner.nextLine().trim();
                        if (state.equalsIgnoreCase("add")) {
                            newField = checkInput("Enter new feature", ".+");
                            category.getSpecialFeatures().add(newField);
                            AdminZone.addNewFeatureToCategory(category, newField);
                        } else if (state.equalsIgnoreCase("remove")) {
                            newField = checkInput("Enter an existing feature", ".+");
                            category.getSpecialFeatures().remove(newField);
                            AdminZone.deleteFeatureFromCategory(category, newField);
                        } else if (state.equalsIgnoreCase("rename")) {
                            String lastFeature = checkInput("Enter an existing feature", ".+");
                            newField = checkInput("Enter renamed feature", ".+");
                            category.getSpecialFeatures().set(category.getSpecialFeatures().indexOf(lastFeature), newField);
                            AdminZone.renameFeatureOfCategory(category, lastFeature, newField);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("invalid name");
                }
                this.parentMenu.execute();
            }
        };
    }

    private Menu getAddCategoryMenu() {
        return new Menu("Add Category", this) {
            @Override
            public void execute() {
                String name = checkInput("Enter category name", ".+");
                ArrayList<String> features = new ArrayList<>();
                String feature;
                System.out.println("Enter 'end of features' to end");
                do {
                    feature = checkInput("Enter feature", ".+");
                    if (!feature.equalsIgnoreCase("end of features"))
                        features.add(feature);
                } while (feature.equalsIgnoreCase("end of features"));
                ArrayList<Integer> productList = new ArrayList<>();
                String productId;
                do {
                    productId = checkInput("Enter Product ID", "\\d+|end");
                    if (!productId.equalsIgnoreCase("end") &&
                            SellerZone.getProductById(Integer.parseInt(productId)) == null) {
                        System.out.println("invalid product ID.");
                    } else if (!productId.equalsIgnoreCase("end") &&
                            SellerZone.getProductById(Integer.parseInt(productId)) != null) {
                        productList.add(Integer.parseInt(productId));
                    }
                } while (!productId.equalsIgnoreCase("end"));
                AdminZone.createCategory(name, features, productList);
                this.parentMenu.execute();
            }
        };
    }

    private Menu getRemoveCategoryMenu() {
        return new Menu("Remove Category", this) {
            @Override
            public void execute() {
                String name = checkInput("Enter name", ".+");
                if (Category.getCategoryByName(name) == null) {
                    System.out.println("invalid name");
                } else {
                    AdminZone.removeCategory(name);
                }
                this.parentMenu.execute();
            }
        };
    }
}
