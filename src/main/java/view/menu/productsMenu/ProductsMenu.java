package view.menu.productsMenu;

import controller.AllAccountZone;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsMenu extends Menu {
    private static String sort = "date";
    private static FilterInfo filter = new FilterInfo("", 0, Long.MAX_VALUE, "",
            "", "", 0, new HashMap<>());

    public static String getSort() {
        return sort;
    }

    public static void setSort(String sort) {
        ProductsMenu.sort = sort;
    }

    public static FilterInfo getFilter() {
        return filter;
    }

    public ProductsMenu(Menu parentMenu) {
        super("Products", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewCategoriesMenu());
        submenus.add(new FilteringMenu(this));
        submenus.add(new SortingMenu(this));
        submenus.add(getShowProductsMenu());
        submenus.add(getShowProductMenu());
        this.setSubmenus(submenus);
    }

    private Menu getViewCategoriesMenu() {
        return new Menu("View Categories", this) {
            @Override
            public void execute() {
                System.out.println(AllAccountZone.showCategories());
                this.parentMenu.execute();
            }
        };
    }

    private Menu getShowProductsMenu() {
        return new Menu("Show Products", this) {
            @Override
            public void execute() {
                System.out.println(AllAccountZone.getProductsInSortAndFiltered(parentMenu));
                this.parentMenu.execute();
            }
        };
    }
}
