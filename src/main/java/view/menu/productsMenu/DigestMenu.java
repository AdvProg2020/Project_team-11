package view.menu.productsMenu;

import controller.AllAccountZone;
import view.menu.Menu;
import view.menu.SignInMenu;

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
        System.out.println(AllAccountZone.showProductWithSellers(productId));
        super.execute();
    }

    private Menu getAddToCartMenu() {
        return new Menu("Add To Cart", this) {
            @Override
            public void execute() {
                if (AllAccountZone.getCurrentAccount() == null) {
                    new SignInMenu(this).execute();
                } else {
                    String sellerUsername = checkInput("Enter seller username", ".+");
                    System.out.println(AllAccountZone.addProductToCart(productId, sellerUsername));
                }
                this.parentMenu.execute();
            }
        };
    }
}
