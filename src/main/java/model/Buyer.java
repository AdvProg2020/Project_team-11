package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Buyer extends Account {
    private long wallet;
    private final HashMap<String, Integer> discountCodes;//discount code, number
    private final ArrayList<BuyLog> buyHistory;
    private final HashMap<Integer, Integer> cart;// product ID & number
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

    public HashMap<Integer, Integer> getCart() {
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

    public void setCart(int productId) {
        this.cart.put(productId, 1);
    }

    public void setActiveDiscount(Discount activeDiscount) {
        this.activeDiscount = activeDiscount;
    }

    public void decreaseDiscountCode(Discount discount) {
        int number = this.discountCodes.get(discount.getCode());
        this.discountCodes.replace(discount.getCode(), number - 1);
    }
}
