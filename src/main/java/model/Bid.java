package model;

import java.util.Date;
import java.util.HashMap;

public class Bid {
    private final int id;
    private static int numOfAllBids;
    private int productId;
    private Date startDate;
    private Date endDate;
    private String sellerName;
    private HashMap<String, Long> offeredPrice;
    private long maxPrice;

    public Bid(int productId, Date startDate, Date endDate, String sellerName, long maxPrice) {
        this.id = numOfAllBids;
        numOfAllBids += 1;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sellerName = sellerName;
        this.offeredPrice = new HashMap<>();
        this.maxPrice = maxPrice;
        DataBase.getDataBase().setAllBids(this);
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getSellerName() {
        return sellerName;
    }

    public HashMap<String, Long> getOfferedPrice() {
        return offeredPrice;
    }

    public long getMaxPrice() {
        return maxPrice;
    }

    public static void setNumOfAllBids(int numOfAllBids) {
        Bid.numOfAllBids = numOfAllBids;
    }

    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }
}
