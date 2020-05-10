package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Discount {
    private String code;
    private Date startDate;
    private Date endDate;
    private long[] amount;
    private int repeatedTimes;
    private ArrayList<Buyer> allowedUsers;

    public Discount(String code, Date startDate, Date endDate, long[] amount,
                                int repeatedTimes, ArrayList<Buyer> allowedUsers) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;//0 -> percent & 1 -> max.
        this.repeatedTimes = repeatedTimes;
        this.allowedUsers = new ArrayList<>(allowedUsers);
        DataBase.getDataBase().setAllDiscounts(this);
    }

    public String getCode() {
        return code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public long[] getAmount() {
        return amount;
    }

    public int getRepeatedTimes() {
        return repeatedTimes;
    }

    public ArrayList<Buyer> getAllowedUsers() {
        return allowedUsers;
    }
}
