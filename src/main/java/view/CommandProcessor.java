package view;

import view.menu.MainMenu;
import view.menu.Menu;

public class CommandProcessor {

    public static void runMenus() {
        Menu currentMenu = new MainMenu();
        currentMenu.execute();
    }
}
