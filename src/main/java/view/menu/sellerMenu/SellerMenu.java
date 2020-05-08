package view.menu.sellerMenu;

import controller.AllAccountZone;
import view.menu.Menu;

import java.util.ArrayList;

public class SellerMenu extends Menu {

    public SellerMenu(Menu parentMenu) {
        super("Seller", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new ViewPersonalInfoMenu(this));
        submenus.add(getViewCompanyInfoMenu());
        submenus.add(getSalesHistoryMenu());
        submenus.add(new ManageProducts(this));
        submenus.add(getAddProductMenu());
        submenus.add(getRemoveProductMenu());
        submenus.add(getShowCategories());
        submenus.add(new ViewAuctionsMenu(this));
        submenus.add(getViewBalanceMenu());
        this.setSubmenus(submenus);
    }

    private Menu getViewCompanyInfoMenu() {
        return new Menu("View Company Info", this) {
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

    private Menu getSalesHistoryMenu() {
        return new Menu("View Sales History", this) {
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

    private Menu getAddProductMenu() {
        return new Menu("Add Product", this) {
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

    private Menu getRemoveProductMenu() {
        return new Menu("Remove Product", this) {
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

    private Menu getShowCategories() {
        return new Menu("Show Categories", this) {
            @Override
            public void execute() {
                AllAccountZone.showCategories();
                this.parentMenu.execute();
            }
        };
    }

    private Menu getViewBalanceMenu() {
        return new Menu("View Balance", this) {
            @Override
            public void execute() {
                System.out.println(AllAccountZone.viewUserBalance() + "$");
                this.parentMenu.execute();
            }
        };
    }
}
