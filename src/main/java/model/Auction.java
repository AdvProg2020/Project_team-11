package model;

import java.util.ArrayList;
import java.util.Date;

public class Auction {
    private final int id;
    private ArrayList<Product> productList;
    private String status;
    private Date startDate;
    private Date endDate;
    private int discountAmount;
    private final String sellerName;
    private static int numOfAllAuctions;

    public Auction(ArrayList<Product> productList, String status, Date startDate, Date endDate,
                   int discountAmount, String sellerName) {
        this.id = numOfAllAuctions;
        numOfAllAuctions += 1;
        this.productList = new ArrayList<>(productList);
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountAmount = discountAmount;
        this.sellerName = sellerName;
        DataBase.getDataBase().setAllAuctions(this);
    }

    public int getId() {
        return id;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public String getStatus() {
        return status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public static void setNumOfAllAuctions(int numOfAllAuctions) {
        Auction.numOfAllAuctions = numOfAllAuctions;
    }
}
