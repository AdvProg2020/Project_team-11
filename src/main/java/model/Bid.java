package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Bid {
    private final int id;
    private static int numOfAllBids;
    private int productId;
    private Date startDate;
    private Date endDate;
    private String sellerName;
    private HashMap<Long, String> offeredPrice;
    private long maxPrice;
    private ArrayList<HashMap<String, String>> messages;

    public Bid(int productId, Date startDate, Date endDate, String sellerName, long maxPrice) {
        this.id = numOfAllBids;
        numOfAllBids += 1;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sellerName = sellerName;
        this.offeredPrice = new HashMap<>();
        this.maxPrice = maxPrice;
        messages = new ArrayList<>();
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

    public HashMap<Long, String> getOfferedPrice() {
        return offeredPrice;
    }

    public ArrayList<HashMap<String, String>> getMessages() {
        return messages;
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
