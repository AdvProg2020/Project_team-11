package model;

import java.util.ArrayList;
import java.util.Date;

public class Auction {
    private int id;
    private ArrayList<Product> productList;
    private String status;
    private Date startDate;
    private Date endDate;
    private int discountAmount;
    private static ArrayList<Auction> allAuction = new ArrayList<>();

    public Auction(int id, ArrayList<Product> productList, String status, Date startDate, Date endDate,
                   int discountAmount) {
        this.id = id;
        this.productList = new ArrayList<>(productList);
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountAmount = discountAmount;
        allAuction.add(this);
    }
}
