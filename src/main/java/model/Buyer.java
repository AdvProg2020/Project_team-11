package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Buyer extends Account {
    private int wallet;
    private ArrayList<Discount> discountCodes;
    private ArrayList<BuyLog> buyHistory;
    private HashMap<Product, Integer> cart;

    public Buyer(String firstName, String lastName, String emailAddress, int phoneNumber, String username,
                 String password, int wallet) {
        super(firstName, lastName, emailAddress, phoneNumber, username, password);
        this.wallet = wallet;
        this.discountCodes = new ArrayList<>();
        this.buyHistory = new ArrayList<>();
        this.cart = new HashMap<>();
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

    public HashMap<Product, Integer> getCart() {
        return cart;
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

    public void setCart(Product product) {
        this.cart.put(product, 1);
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
