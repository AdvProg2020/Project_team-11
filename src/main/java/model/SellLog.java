package model;

import java.util.Date;

public class SellLog extends ExchangeLog {
    private final long receivedAmount;
    private final long reducedAmountForAuction;
    private final String soldProduct;
    private int number;
    private final String buyerName;
    private final String sellerUsername;
    private String sendingStatus;

    public SellLog(Date date, long receivedAmount, long reducedAmountForAuction, String soldProduct,
                   int number, String buyerName, String sellerUsername, String sendingStatus) {
        super(date);
        this.receivedAmount = receivedAmount;
        this.reducedAmountForAuction = reducedAmountForAuction;
        this.soldProduct = soldProduct;
        this.number = number;
        this.buyerName = buyerName;
        this.sellerUsername = sellerUsername;
        this.sendingStatus = sendingStatus;
    }

    public long getReceivedAmount() {
        return receivedAmount;
    }

    public long getReducedAmountForAuction() {
        return reducedAmountForAuction;
    }

    public String getSoldProducts() {
        return soldProduct;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getSendingStatus() {
        return sendingStatus;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }
}
