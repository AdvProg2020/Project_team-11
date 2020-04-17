package view.menu.sellerMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class ViewAuctionsMenu extends Menu {

    public ViewAuctionsMenu(Menu parentMenu) {
        super("View Auctions", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewAuctionMenu());
        submenus.add(getEditAuctionMenu());
        submenus.add(getAddAuctionMenu());
        this.setSubmenus(submenus);
    }

    private Menu getViewAuctionMenu() {
        return new Menu("View Auction", this) {
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

    private Menu getEditAuctionMenu() {
        return new Menu("Edit Auction", this) {
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

    private Menu getAddAuctionMenu() {
        return new Menu("Add Auction", this) {
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
