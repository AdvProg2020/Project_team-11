package view.menu;

import controller.AllAccountZone;
import view.menu.Menu;

import java.util.ArrayList;

public class ViewPersonalInfoMenu extends Menu {

    public ViewPersonalInfoMenu(Menu parentMenu) {
        super("View Personal Info", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getEditPersonalInfoMenu());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println(AllAccountZone.getPersonalInfo());
        super.execute();
    }

    private Menu getEditPersonalInfoMenu() {
        return new Menu("Edit Personal Info", this) {
            @Override
            public void execute() {
                System.out.println("Enter filed [first name - last name - email - phone number - password]");
                String field = scanner.nextLine().trim();
                setAccountField(field);
                this.parentMenu.execute();
            }
        };
    }

    private void setAccountField(String field) {
        String newField;
        if (field.equalsIgnoreCase("first name")) {
            newField = checkInput("Enter first name", ".+");
            AllAccountZone.getCurrentAccount().setFirstName(newField);
        } else if (field.equalsIgnoreCase("last name")) {
            newField = checkInput("Enter last name", ".+");
            AllAccountZone.getCurrentAccount().setLastName(newField);
        } else if (field.equalsIgnoreCase("email")) {
            newField = checkInput("Enter email address", "^.+@.+\\.[a-zA-Z]{2,3}$");
            AllAccountZone.getCurrentAccount().setEmailAddress(newField);
        } else if (field.equalsIgnoreCase("phone number")) {
            newField = checkInput("Enter phone number", "\\d+");
            AllAccountZone.getCurrentAccount().setPhoneNumber(newField);
        } else if (field.equalsIgnoreCase("password")) {
            newField = checkInput("Enter password", ".+");
            AllAccountZone.getCurrentAccount().setPassword(newField);
        }
    }
}
