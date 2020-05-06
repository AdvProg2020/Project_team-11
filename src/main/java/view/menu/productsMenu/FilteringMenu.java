package view.menu.productsMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class FilteringMenu extends Menu {

    public FilteringMenu(Menu parentMenu) {
        super("Filtering", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getShowAvailableFilters());
        submenus.add(getFilterMenu());
        submenus.add(getCurrentFiltersMenu());
        submenus.add(getDisableFilterMenu());
        this.setSubmenus(submenus);
    }

    private Menu getShowAvailableFilters() {
        return new Menu("Show Available Filters", this) {
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

    private Menu getFilterMenu() {
        return new Menu("Filter", this) {
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

    private Menu getCurrentFiltersMenu() {
        return new Menu("Show Current Filters", this) {
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

    private Menu getDisableFilterMenu() {
        return new Menu("Disable Filter", this) {
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
