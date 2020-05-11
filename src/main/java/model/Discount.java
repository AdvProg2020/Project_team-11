package model;

import java.util.ArrayList;
import java.util.Date;

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
        for (Buyer user : allowedUsers) {
            user.getDiscountCodes().put(this, repeatedTimes);
        }
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDiscountPercent(long amount) {
        this.amount[0] = amount;
    }

    public void setMaxDiscount(long amount) {
        this.amount[1] = amount;
    }

    public void setRepeatedTimes(int repeatedTimes) {
        this.repeatedTimes = repeatedTimes;
    }

    public void setAllowedUsers(ArrayList<Buyer> allowedUsers) {
        this.allowedUsers = allowedUsers;
    }
}
