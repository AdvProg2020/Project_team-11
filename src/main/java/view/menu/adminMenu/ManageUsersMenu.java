package view.menu.adminMenu;

import view.menu.Menu;

public class ManageUsersMenu extends Menu {

    public ManageUsersMenu(Menu parentMenu) {
        super("Manage Users", parentMenu);
        submenus.add(getViewUsersMenu());
        submenus.add(getDeleteUserMenu());
        submenus.add(getCreateManagerProfileMenu());
        this.setSubmenus(submenus);
    }

    private Menu getViewUsersMenu() {
        return new Menu("View Users", this) {
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

    private Menu getDeleteUserMenu() {
        return new Menu("Delete User", this) {
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

    private Menu getCreateManagerProfileMenu() {
        return new Menu("Create Manager Profile", this) {
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
