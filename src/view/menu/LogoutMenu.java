package view.menu;

import controller.AllAccountZone;

public class LogoutMenu extends Menu {

    public LogoutMenu(Menu parentMenu) {
        super("Logout", parentMenu);
    }

    @Override
    public void execute() {
        AllAccountZone.logout();
        System.out.println("logout successfully");
        this.parentMenu.execute();
    }
}
