package view.menu.productMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class DigestMenu extends Menu {

    public DigestMenu(Menu parentMenu) {
        super("Digest", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getAddToCartMenu());
        this.setSubmenus(submenus);
    }

    private Menu getAddToCartMenu() {
        return new Menu("Add To Cart", this) {
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
