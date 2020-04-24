package model;

import java.util.ArrayList;

public class Seller extends Account {
    private String companyName;
    private int wallet;
    private ArrayList<SellLog> sellHistory;

    public Seller(String firstName, String lastName, String emailAddress, int phoneNumber, String username,
                  String password, String companyName, int wallet) {
        super(firstName, lastName, emailAddress, phoneNumber, username, password);
        this.companyName = companyName;
        this.wallet = wallet;
        this.sellHistory = new ArrayList<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getWallet() {
        return wallet;
    }

    public ArrayList<SellLog> getSellHistory() {
        return sellHistory;
    }
}
