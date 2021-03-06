package model;

public class ProductInfo {
    private String name;
    private String company;
    private long price;
    private long auctionPrice;
    private final String seller;
    private int stockStatus;

    public ProductInfo(String name, String company, long price, String seller, int stockStatus) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.auctionPrice = price;
        this.seller = seller;
        this.stockStatus = stockStatus;
    }


    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public long getPrice() {
        return price;
    }

    public long getAuctionPrice() {
        return auctionPrice;
    }

    public String getSeller() {
        return seller;
    }

    public int getStockStatus() {
        return stockStatus;
    }

    public void setAuctionPrice(long auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setStockStatus(int stockStatus) {
        this.stockStatus = stockStatus;
    }
}
