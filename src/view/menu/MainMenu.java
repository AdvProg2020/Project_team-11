package view.menu;

import view.menu.auctionMenu.AuctionMenu;
import view.menu.productMenu.ProductMenu;

import java.util.ArrayList;

public class MainMenu extends Menu {

    public MainMenu() {
        super("Main Menu", null);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new ProductMenu(this));
        submenus.add(new AuctionMenu(this));
        this.setSubmenus(submenus);
    }
}
