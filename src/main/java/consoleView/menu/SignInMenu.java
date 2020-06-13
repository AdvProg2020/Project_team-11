package consoleView.menu;

import controller.AllAccountZone;
import model.DataBase;

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
                getInfo(info, "Enter account type [admin - buyer - seller]",
                        "(?i)admin|buyer|seller|back", parentMenu);
                getInfo(info, "Enter username", ".+", parentMenu);
                getInfo(info, "Enter password", ".+", parentMenu);
                System.out.println(AllAccountZone.loginUser(info));
                this.parentMenu.execute();
            }
        };
    }

    private ArrayList<String> getAllAccountInformation() {
        ArrayList<String> accountInfo = new ArrayList<>();
        if (DataBase.getDataBase().getHasAdminAccountCreated()) {
            getInfo(accountInfo, "Enter account type [buyer - seller]",
                    "(?i)buyer|seller|back", this);
        } else {
            getInfo(accountInfo, "Enter account type [admin - buyer - seller]",
                    "(?i)admin|buyer|seller|back", this);
        }
        getInfo(accountInfo, "Enter first name", ".+", this);
        getInfo(accountInfo, "Enter last name", ".+", this);
        getInfo(accountInfo, "Enter email", "^.+@.+\\.[a-zA-Z]{2,3}$|back", this);
        getInfo(accountInfo, "Enter phone number", "\\d+|back", this);
        while (true) {
            getInfo(accountInfo, "Enter username", ".+", this);
            if (AllAccountZone.isUsernameValid(accountInfo.get(5))) {
                break;
            } else {
                System.out.println("This username is already occupied.");
                accountInfo.remove(5);
            }
        }
        getInfo(accountInfo, "Enter password", ".+", this);
        if (accountInfo.get(0).equalsIgnoreCase("seller")) {
            getInfo(accountInfo, "Enter company name", ".+", this);
            getInfo(accountInfo, "Enter your money", "\\d{1,18}|back", this);
        } else if (accountInfo.get(0).equalsIgnoreCase("buyer")) {
            getInfo(accountInfo, "Enter your money", "\\d{1,18}|back", this);
        }
        return accountInfo;
    }
}
