package model;

public class Rate {
    private final String buyerName;
    private final int score;
    private final Product product;

    public Rate(String buyerName, int score, Product product) {
        this.buyerName = buyerName;
        this.score = score;
        this.product = product;
        DataBase.getDataBase().setAllRates(this);
    }

    public String getBuyerName() {
        return buyerName;
    }

    public int getScore() {
        return score;
    }

    public Product getProduct() {
        return product;
    }
}
