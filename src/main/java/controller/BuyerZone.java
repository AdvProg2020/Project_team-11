package controller;

import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BuyerZone {

    public static HashMap<String, Integer> getProductsInCart() {
        HashMap<String, Integer> products = new HashMap<>();
        for (Integer productId : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().keySet()) {
            products.put(String.valueOf(productId),((Buyer) AllAccountZone.getCurrentAccount()).getCart().get(productId));
        }
        return products;
    }

    public static void changeNumberOFProductInCart(int productId, int number) {
        for (Map.Entry<Integer, Integer> entry : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().entrySet()) {
            if (entry.getKey() == productId) {
                entry.setValue(entry.getValue() + number);
            }
        }
    }

    public static void removeProductFromCart() {
        int removeProductId = 0;
        for (Map.Entry<Integer, Integer> entry : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().entrySet()) {
            if (entry.getValue() == 0) {
                removeProductId = entry.getKey();
                break;
            }
        }
        ((Buyer) AllAccountZone.getCurrentAccount()).getCart().remove(removeProductId);
    }

    public static String checkDiscountCode(String discountCode) {
        Buyer buyer = null;
        Discount discount = getDiscountByCode(discountCode);
        if (discount == null) {
            return "This code doesn't exist.";
        }
        for (String username : discount.getAllowedUsers()) {
            if (username.equals(AllAccountZone.getCurrentAccount().getUsername())) {
                buyer = AdminZone.getBuyerByUsername(username);
            }
        }
        if (buyer == null) {
            return "You can't use this code.";
        }
        if (buyer.getDiscountCodes().get(discount.getCode()) <= 0) {
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
        long totalPriceAfterDiscount = calculatePriceWithDiscountsAndAuctions();
        return totalPriceAfterDiscount <= buyer.getWallet();
    }

    public static long calculatePriceWithAuctions() {
        setAuctionPrice();
        long totalPrice = 0;
        for (Map.Entry<Integer, Integer> entry : ((Buyer) AllAccountZone.getCurrentAccount()).getCart().entrySet()) {
            totalPrice += SellerZone.getProductById(entry.getKey()).getGeneralFeature().getAuctionPrice() * entry.getValue();
        }
        return totalPrice;
    }

    public static long calculatePriceWithDiscountsAndAuctions() {
        Buyer buyer = (Buyer) AllAccountZone.getCurrentAccount();
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
        createLogs(buyer);
        removeActiveDiscount(buyer);
        buyer.getCart().clear();
    }

    private static void decreaseBuyerMoney(Buyer buyer) {
        long newMoney = buyer.getWallet() - calculatePriceWithDiscountsAndAuctions();
        buyer.setWallet(newMoney);
    }

    private static void increaseSellerMoney(Buyer buyer) {
        for (Map.Entry<Integer, Integer> entry : buyer.getCart().entrySet()) {
            Product product = SellerZone.getProductById(entry.getKey());
            Seller seller = (Seller) AllAccountZone.getAccountByUsername(product.getGeneralFeature().getSeller());
            long newMoney = seller.getWallet() + product.getGeneralFeature().getAuctionPrice() * entry.getValue();
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
        long paidAmount = calculatePriceWithDiscountsAndAuctions();
        long totalPrice = calculatePriceWithAuctions();
        HashMap<Integer, String> purchasedProducts = new HashMap<>();
        for (Integer productId : buyer.getCart().keySet()) {
            Product product = SellerZone.getProductById(productId);
            Seller seller = (Seller) AllAccountZone.getAccountByUsername(product.getGeneralFeature().getSeller());
            purchasedProducts.put(productId, seller.getFirstName() + " " + seller.getLastName());
        }
        BuyLog buyLog = new BuyLog(AllAccountZone.getCurrentDate(), paidAmount, totalPrice - paidAmount,
                purchasedProducts, AllAccountZone.getCurrentAccount().getUsername(), "sending");
        buyer.addBuyHistory(buyLog);
        for (Integer productId : buyer.getCart().keySet()) {
            Product product = SellerZone.getProductById(productId);
            SellLog sellLog = new SellLog(AllAccountZone.getCurrentDate(), product.getGeneralFeature().getAuctionPrice(),
                    product.getGeneralFeature().getPrice() - product.getGeneralFeature().getAuctionPrice(),
                    product.getGeneralFeature().getName(), AllAccountZone.getCurrentAccount().getFirstName() + " " +
                    AllAccountZone.getCurrentAccount().getLastName(), product.getGeneralFeature().getSeller(),
                    "sending");
            Seller seller = (Seller) AllAccountZone.getAccountByUsername(product.getGeneralFeature().getSeller());
            seller.addSellHistory(sellLog);
        }
    }

    public static void setAuctionPrice() {
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            for (Product product : auction.getProductList()) {
                double discountPercent = 0;
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

    public static ArrayList<String> getOrdersInfo() {
        ArrayList<String> orders = new ArrayList<>();
        StringBuilder productList = new StringBuilder();
        for (BuyLog buyLog  : ((Buyer) AllAccountZone.getCurrentAccount()).getBuyHistory()) {
            for (Map.Entry<Integer, String> entry : buyLog.getPurchasedProductionsAndSellers().entrySet()) {
                productList.append(SellerZone.getProductById(entry.getKey()).getGeneralFeature().getName())
                        .append(" seller : ").append(entry.getValue());
            }
            orders.add(buyLog.getId() + ". " + buyLog.getDate() + " : \n" + productList + "\n" +
                    buyLog.getPaidAmount() + "$ -> Discount = " + buyLog.getDiscountAmountApplied() + "$\n" +
                    buyLog.getDeliveryStatus());
        }
        return orders;
    }

    public static boolean hasUserBoughtProduct(int productId) {
        for (BuyLog buyLog  : ((Buyer) AllAccountZone.getCurrentAccount()).getBuyHistory()) {
            for (Integer boughtProductId : buyLog.getPurchasedProductionsAndSellers().keySet()) {
                if (boughtProductId == productId)
                    return true;
            }
        }
        return false;
    }

    public static void createRate(int productId, int score) {
        Product product = SellerZone.getProductById(productId);
        new Rate(AllAccountZone.getCurrentAccount().getUsername(), score, productId);
        double newScore = (product.getAverageScore() * product.getNumOfUsersRated() + score) / (product.getNumOfUsersRated() + 1);
        product.setAverageScore(newScore);
        product.addNumOfUsersRated();
    }

    public static ArrayList<String> getBuyerDiscounts() {
        ArrayList<String> discounts = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ((Buyer) AllAccountZone.getCurrentAccount()).getDiscountCodes().entrySet()) {
            Discount discount = getDiscountByCode(entry.getKey());
            discounts.add(discount.getCode() + " : " + entry.getValue() + " times " + discount.getAmount()[0] +
                    "% discount Max = " + discount.getAmount()[1] + "$ from '" + discount.getStartDate()
                     + "' to '" + discount.getEndDate());
        }
        return discounts;
    }
}
