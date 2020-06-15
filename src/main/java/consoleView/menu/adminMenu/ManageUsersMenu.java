package consoleView.menu.adminMenu;

import controller.AdminZone;
import controller.AllAccountZone;
import consoleView.menu.Menu;

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
        System.out.println("\nUsers : ");
        System.out.println(AdminZone.getUsers());
        super.execute();
    }

    private Menu getViewUserMenu() {
        return new Menu("View User", this) {
            @Override
            public void execute() {
                String username = checkInput("Enter username", ".+");
//                System.out.println(AdminZone.showUserInfo(username));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getDeleteUserMenu() {
        return new Menu("Delete User", this) {
            @Override
            public void execute() {
                String username = checkInput("Enter username", ".+");
//                System.out.println(AdminZone.deleteUser(username));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getCreateManagerProfileMenu() {
        return new Menu("Create Manager Profile", this) {
            @Override
            public void execute() {
                ArrayList<String> info = new ArrayList<>();
                getInfo(info, "Enter first name", ".+", this);
                getInfo(info, "Enter last name", ".+", this);
                getInfo(info, "Enter email", "^.+@.+\\.[a-zA-Z]{2,3}$|back", this);
                getInfo(info, "Enter phone number", "\\d+|back", this);
                while (true) {
                    getInfo(info, "Enter username", ".+", this);
                    if (AllAccountZone.isUsernameValid(info.get(5))) {
                        break;
                    } else {
                        System.out.println("This username is already occupied.");
                        info.remove(5);
                    }
                }
                getInfo(info, "Enter password", ".+", this);
                AdminZone.createAdminProfile(info);
                System.out.println("Admin added.");
                this.parentMenu.execute();
            }
        };
    }
}
