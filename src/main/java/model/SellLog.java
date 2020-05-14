package model;

import java.util.Date;

public class SellLog extends ExchangeLog {
    private long receivedAmount;
    private long reducedAmountForAuction;
    private Product soldProduct;
    private String buyerName;
    private String sellerUsername;
    private String sendingStatus;

    public SellLog(Date date, long receivedAmount, long reducedAmountForAuction,
                   Product soldProduct, String buyerName, String sellerUsername, String sendingStatus) {
        super(date);
        this.receivedAmount = receivedAmount;
        this.reducedAmountForAuction = reducedAmountForAuction;
        this.soldProduct = soldProduct;
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

    public Product getSoldProducts() {
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
