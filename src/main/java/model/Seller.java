package model;

import java.util.ArrayList;

public class Seller extends Account {
    private String companyName;
    private int wallet;
    private ArrayList<SellLog> sellHistory;

    public Seller( String username, String firstName, String lastName, String emailAddress, String phoneNumber,
                  String password, String companyName, int wallet) {
        super( username, firstName, lastName, emailAddress, phoneNumber, password);
        this.companyName = companyName;
        this.wallet = wallet;
        this.sellHistory = new ArrayList<>();
    }
}
