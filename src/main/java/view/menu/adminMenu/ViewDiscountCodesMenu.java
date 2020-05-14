package view.menu.adminMenu;

import controller.AdminZone;
import model.Buyer;
import model.Discount;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Date;

public class ViewDiscountCodesMenu extends Menu {

    public ViewDiscountCodesMenu(Menu parentMenu) {
        super("View Discount Codes", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewDiscountCodeMenu());
        submenus.add(getEditDiscountCodeMenu());
        submenus.add(getRemoveDiscountCodeMenu());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println("\nDiscounts : ");
        System.out.println(AdminZone.showDiscounts());
        super.execute();
    }

    private Menu getViewDiscountCodeMenu() {
        return new Menu("View Discount Code", this) {
            @Override
            public void execute() {
                String code = checkInput("Enter code", ".+");
                if (AdminZone.getDiscountByCode(code) == null)
                    System.out.println("invalid code");
                else
                    System.out.println(AdminZone.showDiscountInfo(code));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getEditDiscountCodeMenu() {
        return new Menu("Edit Discount Code", this) {
            @Override
            public void execute() {
                String code = checkInput("Enter code", ".+");
                Discount discount;
                if ((discount = AdminZone.getDiscountByCode(code)) != null){
                    System.out.println("Which field do you want to edit? [code - start date - end date - discount percent" +
                            " - max discount - num of uses - allowed users]");
                    String field = scanner.nextLine().trim();
                    String newField;
                    if (field.equalsIgnoreCase("code")) {
                        newField = checkInput("Enter Code", ".+");
                        discount.setCode(newField);
                    } else if (field.equalsIgnoreCase("start date")) {
                        Date date = getDate("start ");
                        discount.setStartDate(date);
                    } else if (field.equalsIgnoreCase("end date")) {
                        Date date = getDate("end ");
                        discount.setEndDate(date);
                    } else if (field.equalsIgnoreCase("discount percent")) {
                        newField = checkInput("Enter discount percent", "^[1-9][0-9]?$");
                        discount.setDiscountPercent(Long.parseLong(newField));
                    } else if (field.equalsIgnoreCase("max discount")) {
                        newField = checkInput("Enter max discount", "\\d{1,18}");
                        discount.setMaxDiscount(Long.parseLong(newField));
                    } else if (field.equalsIgnoreCase("num of uses")) {
                        // TODO : change in buyer account
                        newField = checkInput("Enter num of uses", "\\d{1,9}");
                        discount.setRepeatedTimes(Integer.parseInt(newField));
                    } else if (field.equalsIgnoreCase("allowed users")) {
                        // TODO : add or remove buyer.
                        ArrayList<Buyer> allowedUsers = new ArrayList<>();
                        String username;
                        System.out.println("'end of inserting usernames' to end");
                        do {
                            username = checkInput("Enter username", ".+");
                            Buyer user = AdminZone.getBuyerByUsername(username);
                            if (user == null && !username.equals("end of inserting usernames"))
                                System.out.println("invalid username");
                            else if (user != null && !username.equals("end of inserting usernames"))
                                allowedUsers.add(user);
                        } while (!username.equals("end of inserting usernames"));
                        discount.setAllowedUsers(allowedUsers);
                    }
                } else {
                    System.out.println("invalid code");
                }
                this.parentMenu.execute();
            }
        };
    }

    private Menu getRemoveDiscountCodeMenu() {
        return new Menu("Remove Discount Code", this) {
            @Override
            public void execute() {
                String code = checkInput("Enter code", ".+");
                System.out.println(AdminZone.removeDiscount(code));
                this.parentMenu.execute();
            }
        };
    }
}
