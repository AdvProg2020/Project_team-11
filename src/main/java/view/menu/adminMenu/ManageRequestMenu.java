package view.menu.adminMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class ManageRequestMenu extends Menu {

    public ManageRequestMenu(Menu parentMenu) {
        super("Manage Request", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewRequestDetailsMenu());
        submenus.add(getAcceptRequestMenu());
        submenus.add(getDeclineRequestMenu());
        this.setSubmenus(submenus);
    }

    private Menu getViewRequestDetailsMenu() {
        return new Menu("View Details", this) {
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

    private Menu getAcceptRequestMenu() {
        return new Menu("Accept Request", this) {
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

    private Menu getDeclineRequestMenu() {
        return new Menu("Decline Request", this) {
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
