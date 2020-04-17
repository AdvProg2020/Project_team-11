package view.menu;

import java.util.ArrayList;

public class SignInMenu extends Menu {

    public SignInMenu(Menu parentMenu) {
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
                System.out.println("Enter account's information or Back to return");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    System.out.println(this.parentMenu.getName());
                    this.parentMenu.execute();
                } else {
                    // receive information for new account
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
                // receive information for login account
                System.out.println("login successfuly");
                this.parentMenu.execute();
            }
        };
    }
}
