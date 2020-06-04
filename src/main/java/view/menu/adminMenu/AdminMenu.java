package view.menu.adminMenu;

import controller.AdminZone;
import view.menu.Menu;
import view.menu.ViewPersonalInfoMenu;

import java.util.ArrayList;
import java.util.Date;

public class AdminMenu extends Menu {

    public AdminMenu(Menu parentMenu) {
        super("Admin", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new ViewPersonalInfoMenu(this));
        submenus.add(new ManageUsersMenu(this));
        submenus.add(new ManageAllProductsMenu(this));
        submenus.add(getCreateDiscountCodeMenu());
        submenus.add(new ViewDiscountCodesMenu(this));
        submenus.add(new ManageRequestMenu(this));
        submenus.add(new ManageCategoriesMenu(this));
        this.setSubmenus(submenus);
    }

    private Menu getCreateDiscountCodeMenu() {
        return new Menu("Create Discount Code", this) {
            @Override
            public void execute() {
                AdminZone.createDiscount(getDiscountInfo(), getAllowedUsers());
                System.out.println("Discount created.");
                this.parentMenu.execute();
            }
        };
    }

    private ArrayList<String> getDiscountInfo() {
        ArrayList<String> discountInfo = new ArrayList<>();
        discountInfo.add(checkInput("Enter Code", ".+"));
        Date startDate = getDate("start ");
        discountInfo.add(String.valueOf(startDate.getTime()));
        Date endDate;
        do {
             endDate = getDate("end ");
        } while (startDate.after(endDate));
        discountInfo.add(String.valueOf(endDate.getTime()));
        discountInfo.add(checkInput("Enter discount percent", "^[1-9][0-9]?$"));
        discountInfo.add(checkInput("Enter max discount amount", "\\d{1,18}"));
        discountInfo.add(checkInput("Enter number of times can use this code", "\\d{1,9}"));
        return discountInfo;
    }

    private ArrayList<String> getAllowedUsers() {
        String username;
        ArrayList<String> allowedUsers = new ArrayList<>();
        System.out.println("Print 'end of inserting usernames' to end");
        do {
            username = checkInput("Enter username", ".+");
            if (AdminZone.getBuyerByUsername(username) == null && !username.equals("end of inserting usernames"))
                System.out.println("invalid username");
            else if (AdminZone.getBuyerByUsername(username) != null && !username.equals("end of inserting usernames"))
                allowedUsers.add(username);
        } while (!username.equals("end of inserting usernames"));
        return allowedUsers;
    }
}
