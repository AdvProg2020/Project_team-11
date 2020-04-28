package view.menu;

import controller.*;
import controller.AdminZone;
import controller.AllAccountZone;
import controller.BuyerZone;
import controller.SellerZone;
import model.Account;
import model.Admin;
import model.Buyer;
import model.DataBase;

import java.util.ArrayList;

public class SignInOrSingUpMenu extends Menu {

    Admission admission = new Admission();

    public SignInOrSingUpMenu(Menu parentMenu) {
        super("Create account/Login", parentMenu);
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
                String[] splitInput = scanner.nextLine().split("\\s");
                if (splitInput[0].equalsIgnoreCase("help")) {
                    this.showAvailableMenus();
                    this.execute();
                } else if (splitInput[0].equalsIgnoreCase("back"))
                    this.parentMenu.execute();
                else if (splitInput[0].equalsIgnoreCase("cteate")){
                    if (admission.isThereAccountWhithUsername(splitInput[3])) {
                        System.out.println("Username already taken");
                        this.execute();
                    } else {
                        System.out.println("Enter your password :");
                        String password = scanner.nextLine();
                        if (admission.isPasswordValid(password)) {
                            System.out.println("Enter user information(first name, last name, email address, phone number)");
                            String[] userInfo = scanner.nextLine().split("\\s");
                            if (splitInput[2].equalsIgnoreCase("buyer")) {
                                BuyerZone.addBuyer(splitInput[3], userInfo[0], userInfo[1], userInfo[2], userInfo[3], password);
                            } else if (splitInput[2].equalsIgnoreCase("admin")) {
                                AdminZone.addAdmin(splitInput[3], userInfo[0], userInfo[1], userInfo[2], userInfo[3], password);
                            } else if (splitInput[2].equalsIgnoreCase("seller")) {
                                String companyName = scanner.nextLine();
                                SellerZone.addSeller(splitInput[3], userInfo[0], userInfo[1], userInfo[2], userInfo[3], password, companyName);
                            }
                        }
                        this.parentMenu.execute();
                    }
                }
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
                String[] splitInput = scanner.nextLine().split("\\s");
                if (splitInput[0].equalsIgnoreCase("help")) {
                    this.showAvailableMenus();
                    this.execute();
                } else if (splitInput[0].equalsIgnoreCase("back"))
                    this.parentMenu.execute();
                else if (splitInput[0].equalsIgnoreCase("login")){
                    System.out.println("Enter your password :");
                    String password = scanner.nextLine();
                    if (admission.isPasswordCorrect(splitInput[1], password)){
                        AllAccountZone.initialize(splitInput[1]);
                        System.out.println("Login successfully");
                        this.parentMenu.execute();
                    } else {
                        System.out.println("Wrong username or password");
                        this.execute();
                    }
                }
            }
        };
    }
}
