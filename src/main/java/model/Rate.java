package model;

public class Rate {
    private Buyer buyer;
    private int score;
    private Product product;

    public Rate(Buyer buyer, int score, Product product) {
        this.buyer = buyer;
        this.score = score;
        this.product = product;
        DataBase.getDataBase().setAllRates(this);
    }
}
