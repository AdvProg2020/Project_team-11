package model;

import java.util.Date;
import java.util.HashMap;

public class BuyLog extends ExchangeLog {
    private final long paidAmount;
    private final long discountAmountApplied;
    private final HashMap<Integer, String> purchasedProductionsAndSellers;//product ID, seller Username
    private final String buyerUsername;
    private String deliveryStatus;

    public BuyLog(Date date, long paidAmount, long discountAmountApplied,
                  HashMap<Integer, String> purchasedProductionsAndSellers, String buyerUsername, String deliveryStatus) {
        super(date);
        this.paidAmount = paidAmount;
        this.discountAmountApplied = discountAmountApplied;
        this.purchasedProductionsAndSellers = purchasedProductionsAndSellers;
        this.buyerUsername = buyerUsername;
        this.deliveryStatus = deliveryStatus;
    }

    public long getPaidAmount() {
        return paidAmount;
    }

    public long getDiscountAmountApplied() {
        return discountAmountApplied;
    }

    public HashMap<Integer, String> getPurchasedProductionsAndSellers() {
        return purchasedProductionsAndSellers;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }
}
