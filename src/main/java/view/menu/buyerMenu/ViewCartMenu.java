package view.menu.buyerMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class ViewCartMenu extends Menu {

    public ViewCartMenu(Menu parentMenu) {
        super("View Cart", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getShowProductsInCartMenu());
        submenus.add(getViewProductInCart());
        submenus.add(getIncreaseProductMenu());
        submenus.add(getDecreaseProductMenu());
        submenus.add(getShowTotalPriceMenu());
        submenus.add(new PurchaseMenu(this));
        this.setSubmenus(submenus);
    }

    private Menu getShowProductsInCartMenu() {
        return new Menu("Show Products", this) {
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

    private Menu getViewProductInCart() {
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

    private Menu getIncreaseProductMenu() {
        return new Menu("Increase Product", this) {
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

    private Menu getDecreaseProductMenu() {
        return new Menu("Decrease Product", this) {
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

    private Menu getShowTotalPriceMenu() {
        return new Menu("Show Total Price", this) {
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
