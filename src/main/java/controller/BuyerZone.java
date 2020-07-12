package controller;

import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BuyerZone {

    public static HashMap<String, Integer> getProductsInCart(Account account) {
        HashMap<String, Integer> products = new HashMap<>();
        for (Integer productId : ((Buyer) account).getCart().keySet()) {
            products.put(String.valueOf(productId),((Buyer) account).getCart().get(productId));
        }
        return products;
    }

    public static boolean changeNumberOFProductInCart(int productId, int number, Account account) {
        for (Map.Entry<Integer, Integer> entry : ((Buyer) account).getCart().entrySet()) {
            if (entry.getKey() == productId) {
                if (SellerZone.getProductById(productId).getGeneralFeature().getStockStatus() < entry.getValue() + number)
                    return false;
                entry.setValue(entry.getValue() + number);
                return true;
            }
        }
        return false;
    }

    public static void removeProductFromCart(Account account) {
        int removeProductId = 0;
        for (Map.Entry<Integer, Integer> entry : ((Buyer) account).getCart().entrySet()) {
            if (entry.getValue() == 0) {
                removeProductId = entry.getKey();
                break;
            }
        }
        ((Buyer) account).getCart().remove(removeProductId);
    }

    public static String checkDiscountCode(String discountCode, Account account) {
        Buyer buyer = null;
        Discount discount = getDiscountByCode(discountCode);
        if (discount == null) {
            return "This code doesn't exist.";
        }
        for (String username : discount.getAllowedUsers()) {
            if (username.equals(account.getUsername())) {
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

    public static boolean canPayMoney(Account account) {
        Buyer buyer = (Buyer) account;
        long totalPriceAfterDiscount = calculatePriceWithDiscountsAndAuctions(account);
        return totalPriceAfterDiscount <= buyer.getWallet();
    }

    public static long calculatePriceWithAuctions(Account account) {
        setAuctionPrice();
        long totalPrice = 0;
        for (Map.Entry<Integer, Integer> entry : ((Buyer) account).getCart().entrySet()) {
            totalPrice += SellerZone.getProductById(entry.getKey()).getGeneralFeature().getAuctionPrice() * entry.getValue();
        }
        return totalPrice;
    }

    public static long calculatePriceWithDiscountsAndAuctions(Account account) {
        Buyer buyer = (Buyer) account;
        long totalPrice = calculatePriceWithAuctions(account);
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

    public static void payMoney(Account account) {
        Buyer buyer = ((Buyer) account);
        decreaseBuyerMoney(buyer);
        increaseSellerMoney(buyer);
        createLogs(buyer);
        removeActiveDiscount(buyer);
        buyer.getCart().clear();
    }

    private static void decreaseBuyerMoney(Buyer buyer) {
        long newMoney = buyer.getWallet() - calculatePriceWithDiscountsAndAuctions(buyer);
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
        long paidAmount = calculatePriceWithDiscountsAndAuctions(buyer);
        long totalPrice = calculatePriceWithAuctions(buyer);
        HashMap<Integer, String> purchasedProducts = new HashMap<>();
        HashMap<Integer, Integer> numOfProduct = new HashMap<>();
        for (Integer productId : buyer.getCart().keySet()) {
            Product product = SellerZone.getProductById(productId);
            Seller seller = (Seller) AllAccountZone.getAccountByUsername(product.getGeneralFeature().getSeller());
            purchasedProducts.put(productId, seller.getFirstName() + " " + seller.getLastName());
            numOfProduct.put(productId, buyer.getCart().get(productId));
        }
        BuyLog buyLog = new BuyLog(AllAccountZone.getCurrentDate(), paidAmount, totalPrice - paidAmount,
                purchasedProducts, numOfProduct, buyer.getUsername(), "sending");
        buyer.addBuyHistory(buyLog);
        for (Integer productId : buyer.getCart().keySet()) {
            Product product = SellerZone.getProductById(productId);
            SellLog sellLog = new SellLog(AllAccountZone.getCurrentDate(), product.getGeneralFeature().getAuctionPrice(),
                    product.getGeneralFeature().getPrice() - product.getGeneralFeature().getAuctionPrice(),
                    product.getGeneralFeature().getName(), buyer.getCart().get(productId),
                    buyer.getFirstName() + " " + buyer.getLastName(), product.getGeneralFeature().getSeller(),
                    "sending");
            Seller seller = (Seller) AllAccountZone.getAccountByUsername(product.getGeneralFeature().getSeller());
            seller.addSellHistory(sellLog);
            product.getGeneralFeature().setStockStatus(product.getGeneralFeature().getStockStatus() -
                    buyer.getCart().get(productId));
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

    public static ArrayList<String> getOrdersInfo(Account account) {
        ArrayList<String> orders = new ArrayList<>();
        StringBuilder productList = new StringBuilder();
        for (BuyLog buyLog  : ((Buyer) account).getBuyHistory()) {
            for (Map.Entry<Integer, String> entry : buyLog.getPurchasedProductionsAndSellers().entrySet()) {
                productList.append(SellerZone.getProductById(entry.getKey()).getGeneralFeature().getName())
                        .append(" seller : ").append(entry.getValue()).append("\n");
            }
            orders.add(buyLog.getId() + ". " + buyLog.getDate() + " : \n\n" + productList + "\n" +
                    buyLog.getPaidAmount() + "$ -> Discount = " + buyLog.getDiscountAmountApplied() + "$\n" +
                    buyLog.getDeliveryStatus());
        }
        return orders;
    }

    public static boolean hasUserBoughtProduct(int productId, Account account) {
        for (BuyLog buyLog  : ((Buyer) account).getBuyHistory()) {
            for (Integer boughtProductId : buyLog.getPurchasedProductionsAndSellers().keySet()) {
                if (boughtProductId == productId)
                    return true;
            }
        }
        return false;
    }

    public static void createRate(int productId, int score, Account account) {
        Product product = SellerZone.getProductById(productId);
        new Rate(account.getUsername(), score, productId);
        double newScore = (product.getAverageScore() * product.getNumOfUsersRated() + score) / (product.getNumOfUsersRated() + 1);
        product.setAverageScore(newScore);
        product.addNumOfUsersRated();
    }

    public static ArrayList<String> getBuyerDiscounts(Account account) {
        ArrayList<String> discounts = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ((Buyer) account).getDiscountCodes().entrySet()) {
            Discount discount = getDiscountByCode(entry.getKey());
            discounts.add(discount.getCode() + " : " + entry.getValue() + " times " + discount.getAmount()[0] +
                    "% discount Max = " + discount.getAmount()[1] + "$ from '" + discount.getStartDate()
                     + "' to '" + discount.getEndDate());
        }
        return discounts;
    }
}
