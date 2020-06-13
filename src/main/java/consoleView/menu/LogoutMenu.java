package consoleView.menu;

import controller.AllAccountZone;

public class LogoutMenu extends Menu {

    public LogoutMenu(Menu parentMenu) {
        super("Logout", parentMenu);
    }

    @Override
    public void execute() {
        AllAccountZone.setCurrentAccount(null);
        Menu.getMainMenu().removeMainMenuSubmenu();
        System.out.println("logout successfully");
        Menu.getMainMenu().execute();
    }
}
