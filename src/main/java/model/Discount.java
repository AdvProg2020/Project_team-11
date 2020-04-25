package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Discount {
    private String code;
    private Date startDate;
    private Date endDate;
    private HashMap<Integer, Integer> amount;
    private int repeatedTimes;
    private ArrayList<Buyer> allowedUsers;

    public Discount(String code, Date startDate, Date endDate, HashMap<Integer, Integer> amount,
                    int repeatedTimes, ArrayList<Buyer> allowedUsers) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = new HashMap<>(amount);
        this.repeatedTimes = repeatedTimes;
        this.allowedUsers = new ArrayList<>(allowedUsers);
        DataBase.getDataBase().setAllDiscounts(this);
    }
}
