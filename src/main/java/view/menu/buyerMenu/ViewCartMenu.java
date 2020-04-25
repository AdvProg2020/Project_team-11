package view.menu.buyerMenu;

import controller.BuyerZone;
import view.menu.Menu;
import view.menu.productMenu.ShowProductMenu;

import java.util.ArrayList;

public class ViewCartMenu extends Menu {

    public ViewCartMenu(Menu parentMenu) {
        super("View Cart", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getShowProductsInCartMenu());
        submenus.add(new ShowProductMenu(this));
        submenus.add(getIncreaseProductMenu());
        submenus.add(getDecreaseProductMenu());
        submenus.add(getShowTotalPriceMenu());
        submenus.add(new PurchaseMenu(this));
        this.setSubmenus(submenus);
    }

    private Menu getShowProductsInCartMenu() {
        return new Menu("Show Products", this) {
            @Override
            public void execute() {
                System.out.println(BuyerZone.showProductsInCart());
                this.parentMenu.execute();
            }
        };
    }

    private Menu getIncreaseProductMenu() {
        return new Menu("Increase Product", this) {
            @Override
            public void execute() {
                int productId = checkInputProductId();
                System.out.println(BuyerZone.changeNumberOFProductInCart(productId, 1));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getDecreaseProductMenu() {
        return new Menu("Decrease Product", this) {
            @Override
            public void execute() {
                int productId = checkInputProductId();
                System.out.println(BuyerZone.changeNumberOFProductInCart(productId, -1));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getShowTotalPriceMenu() {
        return new Menu("Show Total Price", this) {
            @Override
            public void execute() {
                System.out.println(BuyerZone.calculateTotalPrice());
                this.parentMenu.execute();
            }
        };
    }
}