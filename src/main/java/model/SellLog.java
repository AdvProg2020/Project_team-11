package model;

import java.util.ArrayList;
import java.util.Date;

public class SellLog extends ExchangeLog {
    private int receivedAmount;
    private int reducedAmountForAuction;
    private ArrayList<Product> soldProducts;
    private Buyer buyer;
    private String sendingStatus;

    public SellLog(int id, Date date, int receivedAmount, int reducedAmountForAuction,
                   ArrayList<Product> soldProducts, Buyer buyer, String sendingStatus) {
        super(id, date);
        this.receivedAmount = receivedAmount;
        this.reducedAmountForAuction = reducedAmountForAuction;
        this.soldProducts = new ArrayList<>(soldProducts);
        this.buyer = buyer;
        this.sendingStatus = sendingStatus;
    }

    public int getReceivedAmount() {
        return receivedAmount;
    }

    public int getReducedAmountForAuction() {
        return reducedAmountForAuction;
    }

    public ArrayList<Product> getSoldProducts() {
        return soldProducts;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public String getSendingStatus() {
        return sendingStatus;
    }
}
