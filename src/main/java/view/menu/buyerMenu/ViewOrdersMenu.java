package view.menu.buyerMenu;

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

    private Menu getShowOrderMenu() {
        return new Menu("Show Order", this) {
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

    private Menu getRateProductMenu() {
        return new Menu("Rate Product", this) {
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
