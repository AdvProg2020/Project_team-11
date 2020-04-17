package view.menu.buyerMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class BuyerMenu extends Menu {

    public BuyerMenu(Menu parentMenu) {
        super("Buyer", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new ViewPersonalInfoMenu(this));
        submenus.add(new ViewCartMenu(this));
        submenus.add(new ViewOrdersMenu(this));
        submenus.add(getViewBalanceMenu());
        submenus.add(getViewDiscountCodesMenu());
        this.setSubmenus(submenus);
    }

    private Menu getViewBalanceMenu() {
        return new Menu("View Balance", this) {
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

    private Menu getViewDiscountCodesMenu() {
        return new Menu("View Discount Codes", this) {
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
