package consoleView.menu.productsMenu;

import controller.AllAccountZone;
import consoleView.menu.Menu;
import consoleView.menu.SignInMenu;

import java.util.ArrayList;

public class DigestMenu extends Menu {
    private int productId;

    public DigestMenu(Menu parentMenu, int productId) {
        super("Digest", parentMenu);
        this.productId = productId;
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getAddToCartMenu());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println("\nProduct info : ");
        System.out.println(AllAccountZone.showProductWithSellers(productId));
        super.execute();
    }

    private Menu getAddToCartMenu() {
        return new Menu("Add To Cart", this) {
            @Override
            public void execute() {
                if (AllAccountZone.getCurrentAccount() == null) {
                    System.out.println("You should login first.");
                    new SignInMenu(this).execute();
                } else {
                    System.out.println(AllAccountZone.addProductToCart(productId));
                }
                this.parentMenu.execute();
            }
        };
    }
}
