package view.menu;

import model.Account;
import model.Admin;
import model.Buyer;
import model.DataBase;

import java.util.ArrayList;

public class SignInOrSingUpMenu extends Menu {

    public SignInOrSingUpMenu(Menu parentMenu) {
        super("Sign in", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getCreateAccountMenu());
        submenus.add(getLoginMenu());
        this.setSubmenus(submenus);
    }

    private Menu getCreateAccountMenu() {
        return new Menu("Create Account", this) {
            @Override
            public void showAvailableMenus() {
                System.out.println(this.getName());
                System.out.println("Enter your account's information or Back to return");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    System.out.println(this.parentMenu.getName());
                    this.parentMenu.execute();
                } else if (input.equalsIgnoreCase("help"))
                    this.showAvailableMenus();
                else if (input.startsWith("create account")){
                    String[] splitInput = input.split("\\s");
                    for ( Account account : DataBase.getDataBase().getAllAccounts()) {
                        if (splitInput[4] == account.getUsername()){
                            System.out.println("Username already taken");
                            this.execute();
                    }
                        if (splitInput[3].equalsIgnoreCase("Buyer")){

                        }
                    }
                }
                this.parentMenu.execute();
            }
        };
    }

    private Menu getLoginMenu() {
        return new Menu("Login", this) {
            @Override
            public void showAvailableMenus() {
                System.out.println(this.getName());
                System.out.println("Enter account's information or Back to return");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    System.out.println(this.parentMenu.getName());
                    this.parentMenu.execute();
                } else if (input.equalsIgnoreCase("help")){
                    this.showAvailableMenus();
                    this.execute();
                }
                else if (input.startsWith("login")){
                    String[] splitInput = input.split("\\s");
                    for (Account account: DataBase.getDataBase().getAllAccounts()) {
                        if (splitInput[2] == account.getUsername()){
                            System.out.println("Enter your password :");
                            input = scanner.nextLine();
                            if (input == account.getPassword()){
                                System.out.println("Successfuly logged in");
                                this.parentMenu.execute();
                            } else {
                                System.out.println("Wrong password");
                                this.execute();
                            }
                        }
                        System.out.println("User doesn't exist");
                        this.execute();
                    }
                } else if (input.equalsIgnoreCase("exit")) {
                    System.exit(1);
            }
        };
    }
}
