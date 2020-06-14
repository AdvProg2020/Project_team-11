package consoleView.menu.auctionMenu;

import controller.AllAccountZone;
import consoleView.menu.Menu;
import consoleView.menu.productsMenu.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AuctionMenu extends Menu {
    private static String sort = "date";
    private static FilterInfo filter = new FilterInfo("", 0, Long.MAX_VALUE, "",
            "", "", 0, new HashMap<>());

    public static String getSort() {
        return sort;
    }

    public static FilterInfo getFilter() {
        return filter;
    }

    public static void setSort(String sort) {
        AuctionMenu.sort = sort;
    }

    public AuctionMenu(Menu parentMenu) {
        super("Auction", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getShowProductsMenu());
        submenus.add(new FilteringMenu(this));
        submenus.add(new SortingMenu(this));
        submenus.add(getShowProductMenu());
        this.setSubmenus(submenus);
    }

    private Menu getShowProductsMenu() {
        return new Menu("Show Products", this) {
            @Override
            public void execute() {
                System.out.println(AllAccountZone.getAuctionProductsInSortAndFiltered(parentMenu));
                this.parentMenu.execute();
            }
        };
    }
}