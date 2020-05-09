package view.menu.sellerMenu;

import controller.AdminZone;
import controller.AllAccountZone;
import controller.SellerZone;
import model.Category;
import model.DataBase;
import model.Product;
import model.Seller;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerMenu extends Menu {

    public SellerMenu(Menu parentMenu) {
        super("Seller", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new ViewPersonalInfoMenu(this));
        submenus.add(getViewCompanyInfoMenu());
        submenus.add(getSalesHistoryMenu());
        submenus.add(new ManageProducts(this));
        submenus.add(getAddProductMenu());
        submenus.add(getRemoveProductMenu());
        submenus.add(getShowCategories());
        submenus.add(new ViewAuctionsMenu(this));
        submenus.add(getViewBalanceMenu());
        this.setSubmenus(submenus);
    }

    private Menu getViewCompanyInfoMenu() {
        return new Menu("View Company Info", this) {
            @Override
            public void execute() {
                System.out.println(SellerZone.getCompanyInfo());
                this.parentMenu.execute();
            }
        };
    }

    private Menu getSalesHistoryMenu() {
        return new Menu("View Sales History", this) {
            @Override
            public void execute() {
                System.out.println(SellerZone.getSellerHistory());
                this.parentMenu.execute();
            }
        };
    }

    private Menu getAddProductMenu() {
        return new Menu("Add Product", this) {
            @Override
            public void execute() {
                System.out.println("Do you want to sale an existing product OR new product? [existing - new]");
                String productType = checkInput("Enter your answer", "(?i)(existing|new)");
                if (productType.equalsIgnoreCase("existing")) {
                    int productId = Integer.parseInt(checkInput("Enter product ID", "\\d+"));
                    while (true) {
                        if (SellerZone.getProductById(productId) == null) {
                            System.out.println("invalid product ID.");
                            productId = Integer.parseInt(checkInput("Enter product ID", "\\d+"));
                        } else {
                            HashMap<String, String> requestDescription = new HashMap<>(getProductInfo());
                            HashMap<String, String> categoryFeature = new HashMap<>();
                            for (String feature : SellerZone.getProductById(productId).getCategory().getSpecialFeatures()) {
                                categoryFeature.put(feature, checkInput(feature, ".+"));
                            }
                            SellerZone.sendAddProductRequest(productId,
                                    SellerZone.getProductById(productId).getCategory(), requestDescription, categoryFeature);
                            break;
                        }
                    }
                } else {
                    HashMap<String, String> requestDescription = new HashMap<>(getProductInfo());
                    String categoryName;
                    Category category;
                    do {
                        categoryName = checkInput("Category name", ".+");
                         category = SellerZone.getCategoryByName(categoryName);
                    } while (category == null);
                    HashMap<String, String> categoryFeature = new HashMap<>();
                    for (String feature : category.getSpecialFeatures()) {
                        categoryFeature.put(feature, checkInput(feature, ".+"));
                    }
                    SellerZone.sendAddProductRequest(0, category, requestDescription, categoryFeature);
                }
                this.parentMenu.execute();
            }
        };
    }

    private Menu getRemoveProductMenu() {
        return new Menu("Remove Product", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter product Id", "\\d+"));
                Product product = AdminZone.getProductByIdAndSeller(productId, (Seller) AllAccountZone.getCurrentAccount());
                if (product == null) {
                    System.out.println("You haven't this product.");
                } else {
                    DataBase.getDataBase().getAllProducts().remove(product);
                    System.out.println("Product removed from your products.");
                }
                this.parentMenu.execute();
            }
        };
    }

    private Menu getShowCategories() {
        return new Menu("Show Categories", this) {
            @Override
            public void execute() {
                AllAccountZone.showCategories();
                this.parentMenu.execute();
            }
        };
    }

    private Menu getViewBalanceMenu() {
        return new Menu("View Balance", this) {
            @Override
            public void execute() {
                System.out.println(AllAccountZone.viewUserBalance() + "$");
                this.parentMenu.execute();
            }
        };
    }

    private HashMap<String, String> getProductInfo() {
        HashMap<String, String> requestDescription = new HashMap<>();
        System.out.println("Enter your Product info : ");
        requestDescription.put("name", checkInput("Product name", ".+"));
        requestDescription.put("company", checkInput("Company or Brand", ".+"));
        requestDescription.put("price", checkInput("Price", "\\d+"));
        requestDescription.put("stock status", checkInput("Stock status", "\\d+"));
        requestDescription.put("description", checkInput("Description", ".+"));
        return requestDescription;
    }
}
