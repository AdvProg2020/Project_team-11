package view.menu.buyerMenu;

import controller.BuyerZone;
import view.menu.Menu;

import java.util.ArrayList;

public class ViewCartMenu extends Menu {

    public ViewCartMenu(Menu parentMenu) {
        super("View Cart", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getShowProductsInCartMenu());
        submenus.add(getShowProductMenu());
        submenus.add(getIncreaseProductMenu());
        submenus.add(getDecreaseProductMenu());
        submenus.add(getShowTotalPriceMenu());
        submenus.add(getPurchaseMenu());
        this.setSubmenus(submenus);
    }

    private Menu getShowProductsInCartMenu() {
        return new Menu("Show Products", this) {
            @Override
            public void execute() {
                System.out.println(BuyerZone.showProductsInCart());
                this.parentMenu.execute();
            }
        };
    }

    private Menu getIncreaseProductMenu() {
        return new Menu("Increase Product", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter Product ID", "\\d+"));
                System.out.println(BuyerZone.changeNumberOFProductInCart(productId, 1));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getDecreaseProductMenu() {
        return new Menu("Decrease Product", this) {
            @Override
            public void execute() {
                int productId = Integer.parseInt(checkInput("Enter Product ID", "\\d+"));
                System.out.println(BuyerZone.changeNumberOFProductInCart(productId, -1));
                BuyerZone.removeProductFromCart();
                this.parentMenu.execute();
            }
        };
    }

    private Menu getShowTotalPriceMenu() {
        return new Menu("Show Total Price", this) {
            @Override
            public void execute() {
                System.out.println(BuyerZone.calculatePriceWithAuctions());
                this.parentMenu.execute();
            }
        };
    }

    private Menu getPurchaseMenu() {
        return new Menu("Purchase", this) {
            @Override
            public void execute() {
                getReceiveInfoMenu().execute();
                getCheckDiscountCodeMenu().execute();
                this.parentMenu.execute();
            }
        };
    }

    private Menu getReceiveInfoMenu() {
        return new Menu("Receive Information", parentMenu) {
            @Override
            public void execute() {
                System.out.println("Enter Receiver Information or 'back'.");
                String address = receiveInfo("address");
                String phoneNumber = receiveInfo("phone number");
            }
        };
    }

    private String receiveInfo(String field) {
        System.out.print("Enter " + field + ": ");
        String command = scanner.nextLine().trim();
        if (command.equalsIgnoreCase("back")) {
            this.parentMenu.execute();
        } else if (command.equalsIgnoreCase("help")) {
            this.showAvailableMenus();
            receiveInfo(field);
        }
        return command;
    }

    private Menu getCheckDiscountCodeMenu() {
        return new Menu("Check discount code", parentMenu) {
            @Override
            public void showAvailableMenus() {
                System.out.println("Enter Discount Code or 'back' to cancel the purchase or 'next' to pay the money.");
            }

            @Override
            public void execute() {
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("back")) {
                    this.parentMenu.execute();
                } else if (command.equalsIgnoreCase("help")) {
                    this.showAvailableMenus();
                    this.execute();
                } else if (command.equalsIgnoreCase("next")) {
                    getPaymentMenu().execute();
                } else {
                    if (BuyerZone.checkDiscountCode(command).equals("Discount applied.")) {
                        System.out.println("Discount applied.");
                        getPaymentMenu().execute();
                    } else {
                        System.out.println(BuyerZone.checkDiscountCode(command));
                        this.execute();
                    }
                }
            }
        };
    }

    private Menu getPaymentMenu() {
        return new Menu("Payment", parentMenu) {
            @Override
            public void execute() {
                if (BuyerZone.canPayMoney()) {
                    BuyerZone.payMoney();
                    System.out.println("Purchase Completed.\nThank you for buying.");
                    Menu.getMainMenu().execute();
                } else {
                    System.out.println("You don't have enough money.");
                    this.parentMenu.execute();
                }
            }
        };
    }
}