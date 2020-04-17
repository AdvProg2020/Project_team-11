package view.menu.adminMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class ManageAllProductsMenu extends Menu {

    public ManageAllProductsMenu(Menu parentMenu) {
        super("Manage All Product", parentMenu);
        ArrayList<Menu> submenu = new ArrayList<>();
        submenu.add(getRemoveMenu());
        this.setSubmenus(submenu);
    }

    private Menu getRemoveMenu() {
        return new Menu("Remove", this) {
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
