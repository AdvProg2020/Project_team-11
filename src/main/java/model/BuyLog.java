package model;

import java.util.Date;
import java.util.HashMap;

public class BuyLog extends ExchangeLog {
    private final long paidAmount;
    private final long discountAmountApplied;
    private final HashMap<Integer, String> purchasedProductionsAndSellers;//product ID, seller Username
    private HashMap<Integer, Integer> numOfProduct;//product ID , number
    private final String buyerUsername;
    private String deliveryStatus;
    private String buyerAddress;
    private String phoneNumber;

    public BuyLog(Date date, long paidAmount, long discountAmountApplied,
                  HashMap<Integer, String> purchasedProductionsAndSellers,
                  HashMap<Integer, Integer> numOfProduct, String buyerUsername, String deliveryStatus,
                  String buyerAddress, String phoneNumber) {
        super(date);
        this.paidAmount = paidAmount;
        this.discountAmountApplied = discountAmountApplied;
        this.purchasedProductionsAndSellers = purchasedProductionsAndSellers;
        this.numOfProduct = numOfProduct;
        this.buyerUsername = buyerUsername;
        this.deliveryStatus = deliveryStatus;
        this.buyerAddress = buyerAddress;
        this.phoneNumber = phoneNumber;
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

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
