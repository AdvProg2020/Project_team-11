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


    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public int getPrice() {
        return price;
    }

    public Seller getSeller() {
        return seller;
    }

    public int getStockStatus() {
        return stockStatus;
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
