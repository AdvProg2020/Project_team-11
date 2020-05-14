package view.menu.sellerMenu;

import controller.SellerZone;
import view.menu.Menu;

import java.util.ArrayList;

public class ManageProducts extends Menu {

    public ManageProducts(Menu parentMenu) {
        super("Manage Products", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewProductMenu());
        submenus.add(getViewProductBuyersMenu());
        submenus.add(getEditProductMenu());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println("\nProducts : ");
        System.out.println(SellerZone.getSellerProducts());
        super.execute();
    }

    private Menu getViewProductMenu() {
        return new Menu("View Product", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter Product ID", "\\d+"));
                System.out.println(SellerZone.viewSellerProduct(productId));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getViewProductBuyersMenu() {
        return new Menu("View Product Buyers", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter Product ID", "\\d+"));
                System.out.println(SellerZone.viewProductBuyers(productId));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getEditProductMenu() {
        return new Menu("Edit Product", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter Product ID", "\\d+"));
                if (SellerZone.editProduct(productId).equals("Edit")) {
                    getChangedField(productId);
                } else {
                    System.out.println("You haven't this product.");
                }
                this.parentMenu.execute();
            }
        };
    }

    private void getChangedField(int productId) {
        String name, company, description, price, stockStatus;
        System.out.println("Enter the new field or 'next' to skip");
        name = checkInput("Enter name", ".+");
        company = checkInput("Enter company", ".+");
        price = checkInput("Enter price", "\\d+|next");
        stockStatus = checkInput("Enter stock status", "\\d+|next");
        description = checkInput("Enter description", ".+");
        String requestDescription = productId + "," + name + "," + company + "," + price + "," + stockStatus +
                "," + description;
        SellerZone.sendEditProductRequest(requestDescription);
    }
}
