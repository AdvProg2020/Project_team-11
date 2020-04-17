package view.menu.productMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class SortingMenu extends Menu {

    public SortingMenu(Menu parentMenu) {
        super("Sorting", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getShowAvailableSortMenu());
        submenus.add(getSortMenu());
        submenus.add(getCurrentSortMenu());
        submenus.add(getDisableSortMenu());
        this.setSubmenus(submenus);
    }

    private Menu getShowAvailableSortMenu() {
        return new Menu("Show Available Sorts", this) {
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

    private Menu getSortMenu() {
        return new Menu("Sort", this) {
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

    private Menu getCurrentSortMenu() {
        return new Menu("Show Current Sort", this) {
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

    private Menu getDisableSortMenu() {
        return new Menu("Disable Sort", this) {
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