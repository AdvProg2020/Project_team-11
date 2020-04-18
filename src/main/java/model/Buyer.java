package model;

import java.util.ArrayList;

public class Buyer extends Account {
    private int wallet;
    private ArrayList<Discount> discountCodes;
    private ArrayList<BuyLog> buyHistory;

    public Buyer(String firstName, String lastName, String emailAddress, int phoneNumber, String username,
                 String password, int wallet) {
        super(firstName, lastName, emailAddress, phoneNumber, username, password);
        this.wallet = wallet;
        this.discountCodes = new ArrayList<>();
        this.buyHistory = new ArrayList<>();
    }
}
