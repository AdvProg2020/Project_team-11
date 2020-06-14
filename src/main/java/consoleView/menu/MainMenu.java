package consoleView.menu;

import consoleView.menu.auctionMenu.AuctionMenu;
import consoleView.menu.productsMenu.ProductsMenu;

import java.util.ArrayList;

public class MainMenu extends Menu {

    public MainMenu() {
        super("Main Menu", null);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new ProductsMenu(this));
        submenus.add(new AuctionMenu(this));
        this.setSubmenus(submenus);
    }
}