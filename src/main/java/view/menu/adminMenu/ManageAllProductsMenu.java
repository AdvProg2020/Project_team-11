package view.menu.adminMenu;

import controller.AdminZone;
import view.menu.Menu;

import java.util.ArrayList;

public class ManageAllProductsMenu extends Menu {

    public ManageAllProductsMenu(Menu parentMenu) {
        super("Manage All Product", parentMenu);
        ArrayList<Menu> submenu = new ArrayList<>();
        submenu.add(getRemoveMenu());
        this.setSubmenus(submenu);
    }

    @Override
    public void execute() {
        System.out.println(AdminZone.getAllProducts());
        super.execute();
    }

    private Menu getRemoveMenu() {
        return new Menu("Remove", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter product ID", "\\d+"));
                System.out.println(AdminZone.removeProduct(productId));
                this.parentMenu.execute();
            }
        };
    }
}
