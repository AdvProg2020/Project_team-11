package view.menu.productsMenu;

import controller.AllAccountZone;
import controller.SellerZone;
import view.menu.Menu;

import java.util.ArrayList;

public class ShowProductMenu extends Menu {

    public ShowProductMenu(Menu parentMenu, int productId) {
        super("Show Product", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(new DigestMenu(this, productId));
        submenus.add(getAttributeMenu(productId));
        submenus.add(getCompareMenu(productId));
        submenus.add(new CommentMenu(this, productId));
        this.setSubmenus(submenus);
    }

    private Menu getAttributeMenu(int productId) {
        return new Menu("Attribute", this) {
            @Override
            public void execute() {
                System.out.println(AllAccountZone.showProductAttribute(productId));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getCompareMenu(int productId1) {
        return new Menu("Compare", this) {
            @Override
            public void execute() {
                int productId2 = Integer.parseInt(checkInput("Enter product ID", "\\d+"));
                if (SellerZone.getProductById(productId2) == null) {
                    System.out.println("invalid ID");
                } else {
                    String output = AllAccountZone.compareTwoProduct(productId1, productId2);
                    if (output.startsWith("Cannot")) {
                        System.out.println(output);
                    } else {
                        System.out.println("+------------------------------+--------------------+--------------------+");
                        System.out.println("|           Feature            |      product1      |      product2      |");
                        System.out.println("+------------------------------+--------------------+--------------------+");
                        for (String info : output.split("-")) {
                            System.out.format("| %-28s | %-18s | %-18s |\n", info.split(",")[0],
                                    info.split(",")[1], info.split(",")[2]);
                        }
                    }
                }
                this.parentMenu.execute();
            }
        };
    }
}
