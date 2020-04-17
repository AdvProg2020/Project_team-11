package view.menu.sellerMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class ViewPersonalInfoMenu extends Menu {

    public ViewPersonalInfoMenu(Menu parentMenu) {
        super("View Personal Info", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getEditPersonalInfoMenu());
        this.setSubmenus(submenus);
    }

    private Menu getEditPersonalInfoMenu() {
        return new Menu("Edit Personal Info", this) {
            @Override
            public void showAvailableMenus() {
                //probably empty
            }

            @Override
            public void execute() {
                //function
                this.parentMenu.execute();
            }
        };
    }
}
