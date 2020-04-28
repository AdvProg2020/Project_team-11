package model;

import java.util.ArrayList;

public class Buyer extends Account {
    private int wallet;
    private ArrayList<Discount> discountCodes;
    private ArrayList<BuyLog> buyHistory;

    public Buyer( String username, String firstName, String lastName, String emailAddress, String phoneNumber,
                 String password, int wallet) {
        super( username, firstName, lastName, emailAddress, phoneNumber, password);
        this.wallet = wallet;
        this.discountCodes = new ArrayList<>();
        this.buyHistory = new ArrayList<>();
    }
}
