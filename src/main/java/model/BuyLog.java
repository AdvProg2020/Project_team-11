package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyLog extends ExchangeLog {
    private long paidAmount;
    private long discountAmountApplied;
    private HashMap<Product, String> purchasedProductionsAndSellers;
    private String deliveryStatus;

    public BuyLog(Date date, long paidAmount, long discountAmountApplied,
                  HashMap<Product, String> purchasedProductionsAndSellers, String deliveryStatus) {
        super(date);
        this.paidAmount = paidAmount;
        this.discountAmountApplied = discountAmountApplied;
        this.purchasedProductionsAndSellers = purchasedProductionsAndSellers;
        this.deliveryStatus = deliveryStatus;
    }

    public long getPaidAmount() {
        return paidAmount;
    }

    public long getDiscountAmountApplied() {
        return discountAmountApplied;
    }

    public HashMap<Product, String> getPurchasedProductionsAndSellers() {
        return purchasedProductionsAndSellers;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}
