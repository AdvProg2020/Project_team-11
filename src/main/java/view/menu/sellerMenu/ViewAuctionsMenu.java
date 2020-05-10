package view.menu.sellerMenu;

import controller.AdminZone;
import controller.AllAccountZone;
import controller.SellerZone;
import model.Seller;
import view.menu.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        System.out.println(SellerZone.showSellerAuctions());
        super.execute();
    }

    private Menu getViewAuctionMenu() {
        return new Menu("View Auction", this) {
            @Override
            public void execute() {
                int auctionId = Integer.parseInt(checkInput("Enter auction ID", "\\d+"));
                System.out.println(SellerZone.showSellerAuction(auctionId));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getEditAuctionMenu() {
        return new Menu("Edit Auction", this) {
            @Override
            public void execute() {
                int auctionId = Integer.parseInt(checkInput("Enter auction ID", "(\\d+)"));
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
            startDate = getDate("");
            requestDescription.append(startDate.getTime()).append(",");
        } else {
            requestDescription.append("next,");
        }
        System.out.println("Do you want to change end date? [yes-no]");
        String changeEndDateState = scanner.nextLine().trim();
        if (changeEndDateState.equalsIgnoreCase("yes")) {
            endDate = getDate("");
            requestDescription.append(endDate.getTime()).append(",");
        } else {
            requestDescription.append("next,");
        }
        String description;
        do {
            description = checkInput("Enter discount amount or 'next'", "(\\d+)|next");
        } while (!description.equals("next") &&
                (Integer.parseInt(description) == 0 || Integer.parseInt(description) >= 100));
        requestDescription.append(description);
        return requestDescription.toString();
    }

    private void getProductList(StringBuilder requestDescription) {
        int productId = 0;
        System.out.println("Enter '-1' to end");
        while (productId != -1) {
            productId = Integer.parseInt(checkInput("Enter product ID", "-?\\d+"));
            if (AdminZone.getProductByIdAndSeller(productId, (Seller) AllAccountZone.getCurrentAccount()) == null) {
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
        String description;
        do {
            description = checkInput("Enter discount amount", "(\\d+)|next");
        } while (Integer.parseInt(description) == 0 || Integer.parseInt(description) >= 100);
        newField.append(description);
        SellerZone.createAuction(newField.toString());
    }
}
