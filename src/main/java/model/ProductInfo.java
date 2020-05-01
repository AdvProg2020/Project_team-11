package model;

public class ProductInfo {
    private String name;
    private String company;
    private long price;
    private long auctionPrice;
    private Seller seller;
    private int stockStatus;

    public ProductInfo(String name, String company, int price, Seller seller, int stockStatus) {
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

    public Seller getSeller() {
        return seller;
    }

    public int getStockStatus() {
        return stockStatus;
    }

    public void setAuctionPrice(long auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", price=" + price +
                ", seller=" + seller +
                ", stockStatus=" + stockStatus +
                '}';
    }
}
