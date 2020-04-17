package view.menu.sellerMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class ManageProducts extends Menu {

    public ManageProducts(Menu parentMenu) {
        super("Manage Products", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewProduct());
        submenus.add(getViewProductBuyersMenu());
        submenus.add(getEditProductMenu());
        this.setSubmenus(submenus);
    }

    private Menu getViewProduct() {
        return new Menu("View Product", this) {
            @Override
            public void showAvailableMenus() {
                //probably empty
            }

            @Override
            public void execute() {
                //function
                this.parentMenu.execute();
            }
        };
    }

    private Menu getViewProductBuyersMenu() {
        return new Menu("View Product Buyers", this) {
            @Override
            public void showAvailableMenus() {
                //probably empty
            }

            @Override
            public void execute() {
                //function
                this.parentMenu.execute();
            }
        };
    }

    private Menu getEditProductMenu() {
        return new Menu("Edit Product", this) {
            @Override
            public void showAvailableMenus() {
                //probably empty
            }

            @Override
            public void execute() {
                //function
                this.parentMenu.execute();
            }
        };
    }
}
