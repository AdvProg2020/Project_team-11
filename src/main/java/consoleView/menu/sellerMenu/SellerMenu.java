package consoleView.menu.sellerMenu;

import controller.AllAccountZone;
import controller.SellerZone;
import model.*;
import consoleView.menu.Menu;
import consoleView.menu.ViewPersonalInfoMenu;

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
        submenus.add(getShowSellerRequestsMenu());
        this.setSubmenus(submenus);
    }

    private Menu getShowSellerRequestsMenu() {
        return new Menu("Show Seller Requests", this) {
            @Override
            public void execute() {
                System.out.println(SellerZone.showSellerRequests());
                this.parentMenu.execute();
            }
        };
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
                System.out.println("\nSales : ");
                System.out.println(SellerZone.getSellerHistory());
                this.parentMenu.execute();
            }
        };
    }

    private Menu getAddProductMenu() {
        return new Menu("Add Product", this) {
            @Override
            public void execute() {
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
                SellerZone.sendAddNewProductRequest(category, requestDescription, categoryFeature);
                System.out.println("Request sent.");
                this.parentMenu.execute();
            }
        };
    }

    private Menu getRemoveProductMenu() {
        return new Menu("Remove Product", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter product Id", "\\d{1,9}"));
                Product product = SellerZone.getProductById(productId);
                if (product == null) {
                    System.out.println("You haven't this product.");
                } else {
                    SellerZone.removeProduct(product);
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
                System.out.println(AllAccountZone.showCategories());
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
        requestDescription.put("price", checkInput("Price", "\\d{1,18}"));
        requestDescription.put("stock status", checkInput("Stock status", "\\d{1,9}"));
        requestDescription.put("description", checkInput("Description", ".+"));
        return requestDescription;
    }
}
