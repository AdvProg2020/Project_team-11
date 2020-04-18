package model;

public class ProductInfo {
    private String name;
    private String company;
    private int price;
    private Seller seller;
    private int stockStatus;

    public ProductInfo(String name, String company, int price, Seller seller, int stockStatus) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.seller = seller;
        this.stockStatus = stockStatus;
    }
}
