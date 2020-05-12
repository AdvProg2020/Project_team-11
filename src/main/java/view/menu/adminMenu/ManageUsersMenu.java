package view.menu.adminMenu;

import controller.AdminZone;
import view.menu.Menu;

import java.util.ArrayList;

public class ManageUsersMenu extends Menu {

    public ManageUsersMenu(Menu parentMenu) {
        super("Manage Users", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewUserMenu());
        submenus.add(getDeleteUserMenu());
        submenus.add(getCreateManagerProfileMenu());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println(AdminZone.showUsersInfo());
        super.execute();
    }

    private Menu getViewUserMenu() {
        return new Menu("View User", this) {
            @Override
            public void execute() {
                String username = checkInput("Enter username", ".+");
                System.out.println(AdminZone.showUserInfo(username));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getDeleteUserMenu() {
        return new Menu("Delete User", this) {
            @Override
            public void execute() {
                String username = checkInput("Enter username", ".+");
                System.out.println(AdminZone.deleteUser(username));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getCreateManagerProfileMenu() {
        return new Menu("Create Manager Profile", this) {
            @Override
            public void execute() {
                ArrayList<String> info = new ArrayList<>();
                info.add(checkInput("Enter first name", ".+"));
                info.add(checkInput("Enter last name", ".+"));
                info.add(checkInput("Enter email address", "^.+@.+\\.[a-zA-Z]{2,3}$"));
                info.add(checkInput("Enter phone number", "\\d+"));
                info.add(checkInput("Enter username", ".+"));
                info.add(checkInput("Enter password", ".+"));
                AdminZone.createAdminProfile(info);
                this.parentMenu.execute();
            }
        };
    }
}
