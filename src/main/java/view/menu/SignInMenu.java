package view.menu;

import controller.AllAccountZone;

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
            public void execute() {
                System.out.println(AllAccountZone.createAccount(getAllAccountInformation()));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getLoginMenu() {
        return new Menu("Login", this) {
            @Override
            public void execute() {
                ArrayList<String> info = new ArrayList<>();
                getAccountInfo(info, "Enter account type [admin - buyer - seller]",
                        "(?i)admin|buyer|seller|back", parentMenu);
                getAccountInfo(info, "Enter username", ".+", parentMenu);
                getAccountInfo(info, "Enter password", ".+", parentMenu);
                System.out.println(AllAccountZone.loginUser(info));
                this.parentMenu.execute();
            }
        };
    }

    private void getAccountInfo(ArrayList<String> accountInfo, String massage, String regex, Menu parentMenu) {
        String input = checkInput(massage, regex);
        if (input.equalsIgnoreCase("back")) {
            System.out.println(parentMenu.getName() + " :");
            parentMenu.execute();
        } else {
            accountInfo.add(input);
        }
    }

    private ArrayList<String> getAllAccountInformation() {
        ArrayList<String> accountInfo = new ArrayList<>();
        if (AllAccountZone.getHasAdminAccountCreated()) {
            getAccountInfo(accountInfo, "Enter account type [buyer - seller]",
                    "(?i)buyer|seller|back", parentMenu);
        } else {
            getAccountInfo(accountInfo, "Enter account type [admin - buyer - seller]",
                    "(?i)admin|buyer|seller|back", parentMenu);
        }
        getAccountInfo(accountInfo, "Enter first name", ".+", parentMenu);
        getAccountInfo(accountInfo, "Enter last name", ".+", parentMenu);
        getAccountInfo(accountInfo, "Enter email", "^.+@.+\\.[a-zA-Z]{2,3}$|back", parentMenu);
        getAccountInfo(accountInfo, "Enter phone number", "\\d+|back", parentMenu);
        while (true) {
            getAccountInfo(accountInfo, "Enter username", ".+", parentMenu);
            if (AllAccountZone.isUsernameValid(accountInfo.get(5))) {
                break;
            } else {
                System.out.println("This username is already occupied.");
                accountInfo.remove(5);
            }
        }
        getAccountInfo(accountInfo, "Enter password", ".+", parentMenu);
        if (accountInfo.get(0).equalsIgnoreCase("seller")) {
            getAccountInfo(accountInfo, "Enter company name", ".+", parentMenu);
            getAccountInfo(accountInfo, "Enter your money", "\\d+|back", parentMenu);
        } else if (accountInfo.get(0).equalsIgnoreCase("buyer")) {
            getAccountInfo(accountInfo, "Enter your money", "\\d+|back", parentMenu);
        }
        return accountInfo;
    }
}
