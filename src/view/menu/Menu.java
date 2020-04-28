package view.menu;

import controller.AllAccountZone;
import model.Account;
import model.DataBase;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Menu {
    private String name;
    protected Scanner scanner = new Scanner(System.in);
    protected ArrayList<Menu> submenus;
    protected Menu parentMenu;
//    protected static ArrayList<Menu> allMenus;

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
//        allMenus.add(this);
    }

    public void setSubmenus(ArrayList<Menu> submenus) {
        this.submenus = submenus;
    }

    public String getName() {
        return name;
    }

    public Menu getMenuByName(String name) {
        for (Menu menu : this.submenus) {
            if (menu.getName().equalsIgnoreCase(name))
                return menu;
        }
        return null;
    }

    public void showAvailableMenus() {
        System.out.println(this.getName() + " : ");
        if (AllAccountZone.getCurrentAccount() == null && !this.getName().equals("Sign in") &&
                !this.getName().equals("Logout"))
            System.out.println("Sign in");
        else if (AllAccountZone.getCurrentAccount() != null && !this.getName().equals("Logout") &&
                !this.getName().equals("Logout"))
            System.out.println("Logout");
        for (Menu menu : submenus) {
            System.out.println(menu.getName());
        }
        System.out.println("Help");
        if (this.parentMenu != null)
            System.out.println("Back");
        else
            System.out.println("Exit");
    }

    public void execute() {
        Menu nextMenu = this;
        String chosenMenu = scanner.nextLine().trim();
        if (chosenMenu.equalsIgnoreCase("exit") && parentMenu == null) {
            System.exit(1);
        } else if (chosenMenu.equalsIgnoreCase("back") && parentMenu != null) {
                nextMenu = this.parentMenu;
        } else if (chosenMenu.equalsIgnoreCase("help")) {
            this.showAvailableMenus();
        } else if (chosenMenu.equalsIgnoreCase("sign in") && !this.getName().equals("Sign in") &&
                !this.getName().equals("Logout") && AllAccountZone.getCurrentAccount() == null) {
            nextMenu = new SignInOrSingUpMenu(this);
        } else if (chosenMenu.equalsIgnoreCase("logout") && !this.getName().equals("Sign in") &&
                !this.getName().equals("Logout") && AllAccountZone.getCurrentAccount() != null) {
            nextMenu = new view.menu.LogoutMenu(this);
        } else if (getMenuByName(chosenMenu) != null) {
            nextMenu = getMenuByName(chosenMenu);
        } else {
            System.out.println("invalid command");
        }
        nextMenu.execute();
    }
}
