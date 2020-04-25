package view.menu.adminMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class AdminMenu extends Menu {

    public AdminMenu(Menu parentMenu) {
        super("Admin", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new view.menu.adminMenu.ViewPersonalInfoMenu(this));
        submenus.add(new view.menu.adminMenu.ManageUsersMenu(this));
        submenus.add(new view.menu.adminMenu.ManageAllProductsMenu(this));
        submenus.add(getCreateDiscountCodeMenu());
        submenus.add(new view.menu.adminMenu.ViewDiscountCodesMenu(this));
        submenus.add(new view.menu.adminMenu.ManageRequestMenu(this));
        submenus.add(new view.menu.adminMenu.ManageCategoriesMenu(this));
        this.setSubmenus(submenus);
    }

    private Menu getCreateDiscountCodeMenu() {
        return new Menu("Create Discount Code", this) {
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
