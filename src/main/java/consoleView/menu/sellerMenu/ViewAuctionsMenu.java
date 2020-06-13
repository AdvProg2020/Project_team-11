package consoleView.menu.sellerMenu;

import controller.AllAccountZone;
import controller.SellerZone;
import model.Product;
import consoleView.menu.Menu;

import java.util.ArrayList;
import java.util.Date;

public class ViewAuctionsMenu extends Menu {

    public ViewAuctionsMenu(Menu parentMenu) {
        super("View Auctions", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewAuctionMenu());
        submenus.add(getEditAuctionMenu());
        submenus.add(getAddAuctionMenu());
        this.setSubmenus(submenus);
    }


    @Override
    public void execute() {
        System.out.println("\nAuctions : ");
        System.out.println(SellerZone.showSellerAuctions());
        super.execute();
    }

    private Menu getViewAuctionMenu() {
        return new Menu("View Auction", this) {
            @Override
            public void execute() {
                int auctionId = Integer.parseInt(checkInput("Enter auction ID", "\\d{1,9}"));
                System.out.println(SellerZone.showSellerAuction(auctionId));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getEditAuctionMenu() {
        return new Menu("Edit Auction", this) {
            @Override
            public void execute() {
                int auctionId = Integer.parseInt(checkInput("Enter auction ID", "(\\d{1,9})"));
                if (SellerZone.getAuctionById(auctionId) != null) {
                    String requestDescription = getEditedField(auctionId);
                    SellerZone.sendEditAuctionRequest(auctionId, requestDescription);
                } else {
                    System.out.println("invalid auction ID.");
                }
                this.parentMenu.execute();
            }
        };
    }

    private Menu getAddAuctionMenu() {
        return new Menu("Add Auction", this) {
            @Override
            public void execute() {
                getNewAuctionField();
                this.parentMenu.execute();
            }
        };
    }

    private String getEditedField(int auctionId) {
        StringBuilder requestDescription = new StringBuilder(auctionId).append(",");
        System.out.println("Do you want to change product list? [yes-no]");
        String changeProductsState = scanner.nextLine().trim();
        if (changeProductsState.equalsIgnoreCase("yes")) {
            getProductList(requestDescription);
        } else {
            requestDescription.append("next,");
        }
        Date startDate, endDate;
        System.out.println("Do you want to change start date? [yes-no]");
        String changeStartDateState = scanner.nextLine().trim();
        if (changeStartDateState.equalsIgnoreCase("yes")) {
            do {
                startDate = getDate("start ");
            } while (SellerZone.getAuctionById(auctionId).getEndDate().before(startDate));
            requestDescription.append(startDate.getTime()).append(",");
        } else {
            requestDescription.append("next,");
        }
        System.out.println("Do you want to change end date? [yes-no]");
        String changeEndDateState = scanner.nextLine().trim();
        if (changeEndDateState.equalsIgnoreCase("yes")) {
            do {
                endDate = getDate("start ");
            } while (SellerZone.getAuctionById(auctionId).getStartDate().after(endDate));
            requestDescription.append(endDate.getTime()).append(",");
        } else {
            requestDescription.append("next,");
        }
        requestDescription.append(checkInput("Enter discount amount or 'next'", "(^[1-9][0-9]?$)|next"));
        return requestDescription.toString();
    }

    private void getProductList(StringBuilder requestDescription) {
        int productId = 0;
        System.out.println("Enter '-1' to end");
        while (productId != -1) {
            productId = Integer.parseInt(checkInput("Enter product ID", "-?\\d{1,9}"));
            Product product = SellerZone.getProductById(productId);
            if ((product == null ||
                    product.getGeneralFeature().getSeller().getUsername().equals(AllAccountZone.getCurrentAccount().getUsername())) &&
                    productId != -1) {
                System.out.println("invalid product ID.");
            } else {
                requestDescription.append(productId).append("/");
            }
        }
        requestDescription.replace(requestDescription.lastIndexOf("/"), requestDescription.length(), ",");
    }

    private void getNewAuctionField() {
        StringBuilder newField = new StringBuilder();
        getProductList(newField);
        Date startDate = getDate("start ");
        newField.append(startDate.getTime()).append(",");
        Date endDate = getDate("end ");
        newField.append(endDate.getTime()).append(",");
        newField.append(checkInput("Enter discount amount", "(^[1-9][0-9]?$)|next"));
        SellerZone.createAuction(newField.toString());
    }
}
