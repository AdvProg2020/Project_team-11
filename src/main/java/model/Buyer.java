package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Buyer extends Account {
    private long wallet;
    private final HashMap<String, Integer> discountCodes;//discount code, number
    private final ArrayList<BuyLog> buyHistory;
    private final HashMap<Product, Integer> cart;
    private Discount activeDiscount;

    public Buyer(String firstName, String lastName, String emailAddress, String phoneNumber, String username,
                 String password, long wallet) {
        super(firstName, lastName, emailAddress, phoneNumber, username, password);
        this.wallet = wallet;
        this.discountCodes = new HashMap<>();
        this.buyHistory = new ArrayList<>();
        this.cart = new HashMap<>();
    }

    public long getWallet() {
        return wallet;
    }

    public HashMap<String, Integer> getDiscountCodes() {
        return discountCodes;
    }

    public ArrayList<BuyLog> getBuyHistory() {
        return buyHistory;
    }

    public HashMap<Product, Integer> getCart() {
        return cart;
    }

    public Discount getActiveDiscount() {
        return activeDiscount;
    }

    public void setWallet(long wallet) {
        this.wallet = wallet;
    }

    public void addDiscountCodes(Discount discount, int number) {
        this.discountCodes.put(discount.getCode(), number);
    }

    public void addBuyHistory(BuyLog buyHistory) {
        this.buyHistory.add(buyHistory);
    }

    public void setCart(Product product) {
        this.cart.put(product, 1);
    }

    public void setActiveDiscount(Discount activeDiscount) {
        this.activeDiscount = activeDiscount;
    }

    public void decreaseDiscountCode(Discount discount) {
        int number = this.discountCodes.get(discount.getCode());
        this.discountCodes.replace(discount.getCode(), number - 1);
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
