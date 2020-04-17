package view.menu.adminMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class AdminMenu extends Menu {

    public AdminMenu(Menu parentMenu) {
        super("Admin", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new ViewPersonalInfoMenu(this));
        submenus.add(new ManageUsersMenu(this));
        submenus.add(new ManageAllProductsMenu(this));
        submenus.add(getCreateDiscountCodeMenu());
        submenus.add(new ViewDiscountCodesMenu(this));
        submenus.add(new ManageRequestMenu(this));
        submenus.add(new ManageCategoriesMenu(this));
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
