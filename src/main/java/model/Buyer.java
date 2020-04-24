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

    public int getWallet() {
        return wallet;
    }

    public ArrayList<Discount> getDiscountCodes() {
        return discountCodes;
    }

    public ArrayList<BuyLog> getBuyHistory() {
        return buyHistory;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public void setDiscountCodes(ArrayList<Discount> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public void setBuyHistory(ArrayList<BuyLog> buyHistory) {
        this.buyHistory = buyHistory;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
