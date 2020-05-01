package controller;

import model.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BuyerZone {

    public static String showProductsInCart() {
        StringBuilder productList = new StringBuilder();
        for (Product product : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().keySet()) {
            productList.append(product.getId()).append(". ").append(product.getGeneralFeature().getName()).
                    append(" number: ").append(((Buyer) AllAccountZone.getCurrentAccount()).getCart().get(product)).
                    append(" ").append(product.getGeneralFeature().getPrice()).append("$ ").
                    append(product.getGeneralFeature().getCompany()).append(" Average Score: ").
                    append(product.getAverageScore()).append("\n");
        }
        return String.valueOf(productList);
    }

    public static String changeNumberOFProductInCart(int productId, int number) {
        for (Map.Entry<Product, Integer> entry : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().entrySet()) {
            if (entry.getKey().getId() == productId) {
                entry.setValue(entry.getValue() + number);
                return "Done";
            }
        }
        return "You haven't this product";
    }

    public static void removeProductFromCart() {
        Product removeProduct = null;
        for (Map.Entry<Product, Integer> entry : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().entrySet()) {
            if (entry.getValue() == 0) {
                removeProduct = entry.getKey();
                break;
            }
        }
        ((Buyer) AllAccountZone.getCurrentAccount()).getCart().remove(removeProduct);
    }

    public static String checkDiscountCode(String discountCode) {
        Buyer buyer = null;
        Discount discount = getDiscountByCode(discountCode);
        if (discount == null) {
            return "This code doesn't exist.";
        }
        for (Buyer user : discount.getAllowedUsers()) {
            if (user.getUsername().equals(AllAccountZone.getCurrentAccount().getUsername())) {
                buyer = user;
            }
        }
        if (buyer == null) {
            return "You can't use this code.";
        }
        if (buyer.getDiscountCodes().get(discount) == 0) {
            return "You can't use this code anymore.";
        }
        Date currentDate = AllAccountZone.getCurrentDate();
        if (currentDate.before(discount.getStartDate())) {
            return "You can't use this code until " + discount.getStartDate();
        }
        if (currentDate.after(discount.getEndDate())) {
            return "Code expired at " + discount.getEndDate();
        }
        buyer.setActiveDiscount(discount);
        return "Discount applied.";
    }

    public static Discount getDiscountByCode(String discountCode) {
        for (Discount discount : DataBase.getDataBase().getAllDiscounts()) {
            if (discount.getCode().equals(discountCode)) {
                return discount;
            }
        }
        return null;
    }

    public static boolean canPayMoney() {
        Buyer buyer = (Buyer) AllAccountZone.getCurrentAccount();
        long totalPriceAfterDiscount = calculatePriceWithDiscountsAndAuctions(buyer);
        return totalPriceAfterDiscount <= buyer.getWallet();
    }

    public static long calculatePriceWithAuctions() {
        setAuctionPrice();
        long totalPrice = 0;
        for (Product product : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().keySet()) {
            totalPrice += product.getGeneralFeature().getAuctionPrice();
        }
        return totalPrice;
    }

    private static long calculatePriceWithDiscountsAndAuctions(Buyer buyer) {
        long totalPrice = calculatePriceWithAuctions();
        if (buyer.getActiveDiscount() == null) {
            return totalPrice;
        } else {
            double discountPercent = buyer.getActiveDiscount().getAmount()[0] / 100.0;
            long maxDiscount = buyer.getActiveDiscount().getAmount()[1];
            if (discountPercent * totalPrice > maxDiscount) {
                return totalPrice - maxDiscount;
            } else {
                return  (long) (totalPrice * (1 - discountPercent));
            }
        }
    }

    public static void payMoney() {
        Buyer buyer = ((Buyer) AllAccountZone.getCurrentAccount());
        decreaseBuyerMoney(buyer);
        increaseSellerMoney(buyer);
        removeActiveDiscount(buyer);
        createLogs(buyer);
        buyer.getCart().clear();
    }

    private static void decreaseBuyerMoney(Buyer buyer) {
        long newMoney = buyer.getWallet() - calculatePriceWithDiscountsAndAuctions(buyer);
        buyer.setWallet(newMoney);
    }

    private static void increaseSellerMoney(Buyer buyer) {
        for (Map.Entry<Product, Integer> entry : buyer.getCart().entrySet()) {
            Seller seller = entry.getKey().getGeneralFeature().getSeller();
            long newMoney = seller.getWallet() + entry.getKey().getGeneralFeature().getAuctionPrice() * entry.getValue();
            seller.setWallet(newMoney);
        }
    }

    private static void removeActiveDiscount(Buyer buyer) {
        if (buyer.getActiveDiscount() != null) {
            Discount discount = buyer.getActiveDiscount();
            buyer.decreaseDiscountCode(discount);
            buyer.setActiveDiscount(null);
        }
    }

    private static void createLogs(Buyer buyer) {
        long paidAmount = calculatePriceWithDiscountsAndAuctions(buyer);
        long totalPrice = calculatePriceWithAuctions();
        HashMap<Product, String> purchasedProducts = new HashMap<>();
        for (Product product : buyer.getCart().keySet()) {
            purchasedProducts.put(product, product.getGeneralFeature().getSeller().getFirstName() + " " +
                    product.getGeneralFeature().getSeller().getLastName());
        }
        BuyLog buyLog = new BuyLog(AllAccountZone.getCurrentDate(), paidAmount, totalPrice - paidAmount,
                purchasedProducts, "sending");
        buyer.addBuyHistory(buyLog);
        for (Product product : buyer.getCart().keySet()) {
            SellLog sellLog = new SellLog(AllAccountZone.getCurrentDate(), product.getGeneralFeature().getAuctionPrice(),
                    product.getGeneralFeature().getPrice() - product.getGeneralFeature().getAuctionPrice(),
                    product, AllAccountZone.getCurrentAccount().getFirstName() + " " +
                    AllAccountZone.getCurrentAccount().getLastName(), "sending");
            product.getGeneralFeature().getSeller().addSellHistory(sellLog);
        }
    }

    public static void setAuctionPrice() {
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            for (Product product : auction.getProductList()) {
                double discountPercent = 1;
                if (AllAccountZone.getCurrentDate().after(auction.getStartDate()) &&
                    AllAccountZone.getCurrentDate().before(auction.getEndDate()) &&
                    auction.getStatus().equals("accepted")) {
                    discountPercent = auction.getDiscountAmount() / 100.0;
                }
                product.getGeneralFeature().setAuctionPrice((long)
                        (product.getGeneralFeature().getPrice() * (1 - discountPercent)));
            }
        }
    }
}
