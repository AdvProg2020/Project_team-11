package view.menu;

import controller.AllAccountZone;
import controller.SellerZone;
import view.menu.productsMenu.ShowProductMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    private final String name;
    protected Scanner scanner = new Scanner(System.in);
    protected ArrayList<Menu> submenus;
    protected Menu parentMenu;
    protected static Menu mainMenu;

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
    }

    public void setSubmenus(ArrayList<Menu> submenus) {
        this.submenus = submenus;
    }

    public void addSubmenus(Menu submenu) {
        this.submenus.add(submenu);
    }

    public void removeSubmenu(Menu submenu) {
        this.submenus.remove(submenu);
    }

    public static void setMainMenu(Menu mainMenu) {
        Menu.mainMenu = mainMenu;
    }

    public static Menu getMainMenu() {
        return mainMenu;
    }

    public String getName() {
        return name;
    }

    public Menu getParentMenu() {
        return parentMenu;
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
            nextMenu = new SignInMenu(this);
        } else if (chosenMenu.equalsIgnoreCase("logout") && !this.getName().equals("Sign in") &&
                !this.getName().equals("Logout") && AllAccountZone.getCurrentAccount() != null) {
            nextMenu = new LogoutMenu(this);
        } else if (getMenuByName(chosenMenu) != null) {
            nextMenu = getMenuByName(chosenMenu);
        } else {
            System.out.println("invalid command");
        }
        nextMenu.execute();
    }

    protected String checkInput(String massage, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        String input;
        do {
            System.out.print(massage + " : ");
            input = scanner.nextLine().trim();
            matcher = pattern.matcher(input);
        } while (!matcher.matches());
        return input;
    }


    protected Date getDate(String filed) {
        String input = checkInput("Enter " + filed + "date [dd/mm/yyyy hh:mm:ss]",
                "^\\d{2}\\/\\d{2}\\/\\d{4} \\d{2}:\\d{2}:\\d{2}$");
        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        Date date;
        while (true) {
            try {
                date = format.parse(input);
                break;
            } catch (Exception ex) {
                System.out.println("Ha ha !!!!");
            }
        }
        return date;
    }

    protected Menu getShowProductMenu() {
        return new Menu("Show Product", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter product ID", "\\d+"));
                if (SellerZone.getProductById(productId) == null) {
                    System.out.println("invalid ID");
                } else {
                    new ShowProductMenu(parentMenu, productId);
                }
            }
        };
    }
}
