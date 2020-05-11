package view.menu.productsMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class ShowProductMenu extends Menu {

    public ShowProductMenu(Menu parentMenu, int productId) {
        super("Show Product", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new DigestMenu(this, productId));
        submenus.add(getAttributeMenu());
        submenus.add(getCompareMenu());
        submenus.add(new CommentMenu(this));
        this.setSubmenus(submenus);
    }

    private Menu getAttributeMenu() {
        return new Menu("Attribute", this) {
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

    private Menu getCompareMenu() {
        return new Menu("Compare", this) {
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
