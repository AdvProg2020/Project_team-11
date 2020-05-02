package model;

import java.util.ArrayList;

public class Seller extends Account {
    private String companyName;
    private long wallet;
    private ArrayList<SellLog> sellHistory;

    public Seller(String firstName, String lastName, String emailAddress, String phoneNumber, String username,
                  String password, String companyName, long wallet) {
        super(firstName, lastName, emailAddress, phoneNumber, username, password);
        this.companyName = companyName;
        this.wallet = wallet;
        this.sellHistory = new ArrayList<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public long getWallet() {
        return wallet;
    }

    public ArrayList<SellLog> getSellHistory() {
        return sellHistory;
    }

    public void setWallet(long wallet) {
        this.wallet = wallet;
    }

    public void addSellHistory(SellLog sellHistory) {
        this.sellHistory.add(sellHistory);
    }
}
