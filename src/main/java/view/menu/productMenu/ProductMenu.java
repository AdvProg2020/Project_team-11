package view.menu.productMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class ProductMenu extends Menu {

    public ProductMenu(Menu parentMenu) {
        super("Products", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewCategoriesMenu());
        submenus.add(new FilteringMenu(this));
        submenus.add(new SortingMenu(this));
        submenus.add(getShowProductsMenu());
        submenus.add(new ShowProductMenu(this));
        this.setSubmenus(submenus);
    }

    private Menu getViewCategoriesMenu() {
        return new Menu("View Categories", this) {
            @Override
            public void showAvailableMenus() {
                //probably empty
            }

            @Override
            public void execute() {
                //function to print all categories
                this.parentMenu.execute();
            }
        };
    }

    private Menu getShowProductsMenu() {
        return new Menu("Show Products", this) {
            @Override
            public void showAvailableMenus() {
                //probably empty
            }

            @Override
            public void execute() {
                //function to show product sorted and filtered
                this.parentMenu.execute();
            }
        };
    }
}
