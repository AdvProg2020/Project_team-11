package consoleView.menu.adminMenu;

import controller.AdminZone;
import model.Discount;
import consoleView.menu.Menu;

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
        System.out.println(AdminZone.getDiscountCodes());
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
                    System.out.println(AdminZone.getDiscountInfo(code));
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
                    System.out.println("Which field do you want to edit? [start date - end date - discount percent" +
                            " - max discount - num of uses - allowed users]");
                    String field = scanner.nextLine().trim();
                    String newField;
                    if (field.equalsIgnoreCase("start date")) {
                        Date startDate;
                        do {
                            startDate = getDate("start ");
                        } while (discount.getEndDate().before(startDate));
                        discount.setStartDate(startDate);
                    } else if (field.equalsIgnoreCase("end date")) {
                        Date endDate;
                        do {
                            endDate = getDate("end ");
                        } while (discount.getStartDate().after(endDate));
                        discount.setEndDate(endDate);
                    } else if (field.equalsIgnoreCase("discount percent")) {
                        newField = checkInput("Enter discount percent", "^[1-9][0-9]?$");
                        discount.setDiscountPercent(Long.parseLong(newField));
                    } else if (field.equalsIgnoreCase("max discount")) {
                        newField = checkInput("Enter max discount", "\\d{1,18}");
                        discount.setMaxDiscount(Long.parseLong(newField));
                    } else if (field.equalsIgnoreCase("num of uses")) {
                        newField = checkInput("Enter num of uses", "\\d{1,9}");
                        int different = discount.getRepeatedTimes() - Integer.parseInt(newField);
                        for (String user : discount.getAllowedUsers()) {
                            AdminZone.getBuyerByUsername(user).getDiscountCodes().replace(discount.getCode(),
                                    AdminZone.getBuyerByUsername(user).getDiscountCodes().get(discount.getCode()) - different);
                        }
                        discount.setRepeatedTimes(Integer.parseInt(newField));
                    } else if (field.equalsIgnoreCase("allowed users")) {
                        System.out.println();
                        String state = checkInput("Do you want to add or remove buyer? [add - remove]",
                                "(?i)(add|remove)");
                        String username;
                        if (state.equalsIgnoreCase("add")) {
                            do {
                                username = checkInput("Enter Username", ".+");
                            } while (AdminZone.getBuyerByUsername(username) == null);
                            discount.getAllowedUsers().add(username);
                            AdminZone.getBuyerByUsername(username).addDiscountCodes(discount, discount.getRepeatedTimes());
                        } else {
                            do {
                                username = checkInput("Enter Username", ".+");
                            } while (AdminZone.getBuyerByUsername(username) == null);
                            discount.getAllowedUsers().remove(username);
                            AdminZone.getBuyerByUsername(username).getDiscountCodes().remove(discount.getCode());
                        }
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
//                System.out.println(AdminZone.removeDiscount(code));
                this.parentMenu.execute();
            }
        };
    }
}
