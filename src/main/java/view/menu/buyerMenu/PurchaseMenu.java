package view.menu.buyerMenu;

import view.menu.Menu;

public class PurchaseMenu extends Menu {

    public PurchaseMenu(Menu parentMenu) {
        super("Purchase", parentMenu);
    }

    /*
    this menu has three internal menu that should show in order
     */
    private Menu getPurchaseMenu() {
        return new Menu("Purchase", this) {
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
