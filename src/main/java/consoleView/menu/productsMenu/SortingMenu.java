package consoleView.menu.productsMenu;

import consoleView.menu.Menu;
import consoleView.menu.auctionMenu.AuctionMenu;

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
            public void execute() {
                System.out.println("Price(Ascending) | Price(Descending) | Score | Date");//...
                this.parentMenu.execute();
            }
        };
    }

    private Menu getSortMenu() {
        return new Menu("Sort", this) {
            @Override
            public void execute() {
                String field = checkInput("Enter an available sort",
                        "(?i)(Price\\(Ascending\\)|Price\\(Descending\\)|Score|Date)").toLowerCase();
                if (parentMenu.getParentMenu() instanceof ProductsMenu) {
                     ProductsMenu.setSort(field);
                } else {
                    AuctionMenu.setSort(field);
                }
                this.parentMenu.execute();
            }
        };
    }

    private Menu getCurrentSortMenu() {
        return new Menu("Show Current Sort", this) {
            @Override
            public void execute() {
                if (parentMenu.getParentMenu() instanceof ProductsMenu)
                    System.out.println(ProductsMenu.getSort());
                else
                    System.out.println(AuctionMenu.getSort());
                this.parentMenu.execute();
            }
        };
    }

    private Menu getDisableSortMenu() {
        return new Menu("Disable Sort", this) {
            @Override
            public void execute() {
                if (parentMenu.getParentMenu() instanceof ProductsMenu)
                    ProductsMenu.setSort("date");
                else
                    AuctionMenu.setSort("date");
                this.parentMenu.execute();
            }
        };
    }
}