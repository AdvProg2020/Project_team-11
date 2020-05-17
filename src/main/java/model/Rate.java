package model;

public class Rate {
    private final String buyerName;
    private final int score;
    private final int productId;

    public Rate(String buyerName, int score, int productId) {
        this.buyerName = buyerName;
        this.score = score;
        this.productId = productId;
        DataBase.getDataBase().setAllRates(this);
    }

    public String getBuyerName() {
        return buyerName;
    }

    public int getScore() {
        return score;
    }

    public int getProductId() {
        return productId;
    }
}
