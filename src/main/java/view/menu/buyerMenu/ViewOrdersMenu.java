package view.menu.buyerMenu;

import controller.BuyerZone;
import view.menu.Menu;

import java.util.ArrayList;

public class ViewOrdersMenu extends Menu {

    public ViewOrdersMenu(Menu parentMenu) {
        super("View Orders", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getShowOrderMenu());
        submenus.add(getRateProductMenu());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println("\nOrders : ");
        System.out.println(BuyerZone.getOrders());
        super.execute();
    }

    private Menu getShowOrderMenu() {
        return new Menu("Show Order", this) {
           @Override
            public void execute() {
               int orderId = Integer.parseInt(checkInput("Enter Order ID", "\\d+"));
               System.out.println(BuyerZone.getOrderInfo(orderId));
               this.parentMenu.execute();
            }
        };
    }

    private Menu getRateProductMenu() {
        return new Menu("Rate Product", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter Product ID", "\\d+"));
                if (BuyerZone.hasUserBoughtProduct(productId)) {
                    int score = Integer.parseInt(checkInput("Enter Score [1-5]", "^[12345]$"));
                    BuyerZone.createRate(productId, score);
                    System.out.println("Done.");
                } else {
                    System.out.println("You didn't buy this product");
                }
                this.parentMenu.execute();
            }
        };
    }
}
