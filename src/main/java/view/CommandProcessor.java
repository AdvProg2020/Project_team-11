package view;

import controller.FileProcess;
import view.menu.MainMenu;
import view.menu.Menu;

import java.io.*;

public class CommandProcessor {

    public static void runMenus() {
        if (new File("resources\\admins.json").exists()) {
            FileProcess.initialize();
        }
        Menu currentMenu = new MainMenu();
        Menu.setMainMenu(currentMenu);
        currentMenu.execute();
    }
}
