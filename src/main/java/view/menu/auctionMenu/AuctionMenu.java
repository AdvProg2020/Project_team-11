package view.menu.auctionMenu;

import view.menu.Menu;
import view.menu.productMenu.ShowProductMenu;

import java.util.ArrayList;

public class AuctionMenu extends Menu {

    public AuctionMenu(Menu parentMenu) {
        super("Auction", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new ShowProductMenu(this));
        submenus.add(new FilteringMenu(this));
        submenus.add(new SortingMenu(this));
        this.setSubmenus(submenus);
    }
}
