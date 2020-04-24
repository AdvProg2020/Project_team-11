package model;

import java.util.ArrayList;
import java.util.Date;

public class BuyLog extends ExchangeLog {
    private int paidAmount;
    private int discountAmountApplied;
    private ArrayList<Product> purchasedProductions;
    private Seller seller;
    private String deliveryStatus;

    public BuyLog(int id, Date date, int paidAmount, int discountAmountApplied,
                  ArrayList<Product> purchasedProductions, Seller seller, String deliveryStatus) {
        super(id, date);
        this.paidAmount = paidAmount;
        this.discountAmountApplied = discountAmountApplied;
        this.purchasedProductions = new ArrayList<>(purchasedProductions);
        this.seller = seller;
        this.deliveryStatus = deliveryStatus;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getDiscountAmountApplied() {
        return discountAmountApplied;
    }

    public ArrayList<Product> getPurchasedProductions() {
        return purchasedProductions;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}
