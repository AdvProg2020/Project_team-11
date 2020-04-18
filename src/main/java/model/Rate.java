package model;

import java.util.ArrayList;

public class Rate {
    private Buyer buyer;
    private int score;
    private Product product;
    private static ArrayList<Rate> allRates = new ArrayList<>();

    public Rate(Buyer buyer, int score, Product product) {
        this.buyer = buyer;
        this.score = score;
        this.product = product;
        allRates.add(this);
    }
}
