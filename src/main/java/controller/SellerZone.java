package controller;

import com.google.gson.Gson;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class SellerZone {

    public static Product getProductById(int Id) {
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == Id)
                return product;
        }
        return null;
    }

    public static HashMap<Integer, String> getSellerProducts(Account account) {
        HashMap<Integer, String> products = new HashMap<>();
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getGeneralFeature().getSeller().equals(account.getUsername())) {
                products.put(product.getId(), product.getGeneralFeature().getName());
            }
        }
        return products;
    }

    private static String getProductBuyers(Product product) {
        StringBuilder buyersList = new StringBuilder();
        for (ExchangeLog log : DataBase.getDataBase().getAllLogs()) {
            if (log instanceof SellLog) {
                if (((SellLog) log).getSoldProducts().equals(product)) {
                    buyersList.append(((SellLog) log).getBuyerName()).append("\n");
                }
            }
        }
        return buyersList.toString();
    }

    public static HashMap<String, String> getSellerProductDetails(int productId) {
        Product product = getProductById(productId);
        HashMap<String, String> productDetails= new HashMap<>();
        productDetails.put("status", product.getStatus());
        productDetails.put("name", product.getGeneralFeature().getName());
        productDetails.put("company", product.getGeneralFeature().getCompany());
        productDetails.put("price", String.valueOf(product.getGeneralFeature().getPrice()));
        BuyerZone.setAuctionPrice();
        productDetails.put("auctionPrice", String.valueOf(product.getGeneralFeature().getAuctionPrice()));
        productDetails.put("stock", String.valueOf(product.getGeneralFeature().getStockStatus()));
        productDetails.put("category", product.getCategoryName());
        Gson gson = new Gson();
        productDetails.put("feature", gson.toJson(product.getCategoryFeature()));
        productDetails.put("description", product.getDescription());
        productDetails.put("score", String.valueOf(product.getAverageScore()));
        productDetails.put("numOfUserRated", String.valueOf(product.getNumOfUsersRated()));
        productDetails.put("comments", gson.toJson(product.getComments()));
        return productDetails;
    }

    public static void sendEditProductRequest(String description, Account account) {
        new Request(account.getUsername(), "edit product", description, "unseen");
    }

    public static ArrayList<String> getSellerHistory(Account account) {
        ArrayList<String> saleHistory = new ArrayList<>();
        for (SellLog sellLog : ((Seller) account).getSellHistory()) {
             saleHistory.add(sellLog.getId() + ". at " + sellLog.getDate() + " " + sellLog.getSoldProducts() +
                     " sold to " + sellLog.getBuyerName() + " " + sellLog.getReceivedAmount() + "$ with discount " +
                     sellLog.getReducedAmountForAuction() + "$ " + sellLog.getSendingStatus());
        }
        return saleHistory;
    }

    public static Category getCategoryByName(String name) {
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            if (category.getName().equalsIgnoreCase(name))
                return category;
        }
        return null;
    }

    public static int sendAddNewProductRequest(ArrayList<String> info, HashMap<String, String> categoryFeature, Account account) {
        ProductInfo productInfo = new ProductInfo(info.get(0), info.get(1),
                Long.parseLong(info.get(2)), account.getUsername(), Integer.parseInt(info.get(3)));
        Product product = new Product("construction", productInfo, info.get(5), categoryFeature, info.get(4));
        new Request(account.getUsername(), "add product", String.valueOf(product.getId()), "unseen");
        return product.getId();
    }

    public static void removeProduct(int productId) {
        Product product = getProductById(productId);
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            auction.getProductList().remove(product);
        }
        for (Category category : DataBase.getDataBase().getAllCategories()) {
            category.getProductList().remove(product);
        }
        DataBase.getDataBase().getAllComments().removeIf(comment -> comment.getProductId() == productId);
        DataBase.getDataBase().getAllRates().removeIf(rate -> rate.getProductId() == productId);
        DataBase.getDataBase().getAllProducts().remove(product);
    }

    public static ArrayList<Integer> getSellerAuctions(Account account) {
        Seller seller = (Seller) account;
        ArrayList<Integer> auctionIds = new ArrayList<>();
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            if (auction.getSellerName().equals(seller.getUsername())) {
                auctionIds.add(auction.getId());
            }
        }
        return auctionIds;
    }

    public static ArrayList<String> getAuctionDetail(int auctionId, Account account) {
        Seller seller = (Seller) account;
        ArrayList<String> auctionDetails = new ArrayList<>();
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            if (auction.getId() == auctionId && auction.getSellerName().equals(seller.getUsername())) {
                auctionDetails.addAll(Arrays.asList(auction.getStatus(), String.valueOf(auction.getDiscountAmount()),
                        auction.getStartDate().toString(), auction.getEndDate().toString()));
                for (Product product : auction.getProductList()) {
                    auctionDetails.add(product.getId() + ". " + product.getGeneralFeature().getName());
                }
            }
        }
        return auctionDetails;
    }

    public static Auction getSellerAuctionById(int auctionId, Account account) {
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            if (auction.getSellerName().equals(account.getUsername()) && auction.getId() == auctionId) {
                return auction;
            }
        }
        return null;
    }

    public static void sendEditAuctionRequest(String field, String value, int auctionId, Account account) {
        new Request(account.getUsername(), "edit auction",
                field + "," + value + "," + auctionId, "unseen");
        Auction auction = getSellerAuctionById(auctionId, account);
        auction.setStatus("editing");
    }

    public static void createAuction(ArrayList<String> info, Account account) {
        Seller seller = (Seller) account;
        ArrayList<Product> productList = new ArrayList<>();
        for (int i = 3; i < info.size(); i++) {
            Product product = getProductById(Integer.parseInt(info.get(i)));
            productList.add(product);
        }
        long startDateMilliSec = Long.parseLong(info.get(1));
        long endDateMilliSec = Long.parseLong(info.get(2));
        Auction auction = new Auction(productList, "construction", new Date(startDateMilliSec),
                new Date(endDateMilliSec), Integer.parseInt(info.get(0)), seller.getUsername());
        new Request(seller.getUsername(), "add auction", String.valueOf(auction.getId()),
                "unseen");
    }

    public static String showSellerRequests(Account account) {
        StringBuilder sellerRequest = new StringBuilder();
        for (Request request : DataBase.getDataBase().getAllRequests()) {
            if (request.getSenderName().equals(account.getUsername())) {
                sellerRequest.append(request.getTopic()).append(" -> ").append(request.getStatus()).append("\n");
            }
        }
        return sellerRequest.toString();
    }

    public static boolean hasProduct(int productId, Account account) {
        Product product = getProductById(productId);
        if (product == null) {
            return false;
        } else if (product.getGeneralFeature().getSeller().equals(account.getUsername())) {
            return true;
        }
        return false;
    }


    public static void removeProductFromAuction(int auctionId, int productId, Account account) {
        Auction auction = getSellerAuctionById(auctionId, account);
        Product product = getProductById(productId);
        auction.getProductList().remove(product);
    }

    public static void addProductToAuction(int auctionId, int productId, Account account) {
        Auction auction = getSellerAuctionById(auctionId, account);
        Product product = getProductById(productId);
        auction.getProductList().add(product);
    }

    public static boolean hasProductAuction(int productId) {
        Product product = getProductById(productId);
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            if (auction.getProductList().contains(product))
                return true;
        }
        return false;
    }

    public static ArrayList<String> getBidDetails(Account account) {
        for (Bid bid : DataBase.getDataBase().getAllBids()) {
            if (bid.getSellerName().equals(account.getUsername()))
                if (AllAccountZone.getCurrentDate().before(bid.getEndDate()))
                    return new ArrayList<>(Arrays.asList(String.valueOf(bid.getProductId()), bid.getStartDate().toString(),
                            bid.getEndDate().toString(), String.valueOf(bid.getMaxPrice())));
        }
        return null;
    }

    public static void createBid(ArrayList<String> info, Account account) {
        long startDateMilliSec = Long.parseLong(info.get(1));
        long endDateMilliSec = Long.parseLong(info.get(2));
        Bid bid = new Bid(Integer.parseInt(info.get(0)), new Date(startDateMilliSec), new Date(endDateMilliSec),
                account.getUsername(), Long.parseLong(info.get(3)));
        getProductById(bid.getProductId()).setStatus("lock");
        new Thread(() -> {
            try {
                Thread.sleep(bid.getEndDate().getTime() - AllAccountZone.getCurrentDate().getTime());
                if (!bid.getOfferedPrice().isEmpty()) {
                    Account seller = AllAccountZone.getAccountByUsername(bid.getSellerName());
                    Account buyer = AllAccountZone.getAccountByUsername(bid.getOfferedPrice().get(bid.getMaxPrice()));
                    BuyerZone.payBidMoney(seller, buyer, bid.getMaxPrice(), bid.getProductId());
                }
                getProductById(bid.getProductId()).setStatus("accepted");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static String getBidIdByProductId(int productId) {
        for (Bid bid : DataBase.getDataBase().getAllBids()) {
            if (bid.getProductId() == productId)
                return String.valueOf(bid.getId());
        }
        return "";
    }

    public static Bid getBidById(int bidId) {
        for (Bid bid : DataBase.getDataBase().getAllBids()) {
            if (bid.getId() == bidId)
                return bid;
        }
        return null;
    }

    public static String offerBidPrice(int bidId, long offeredPrice, Account account) {
        Bid bid = getBidById(bidId);
        if (bid.getMaxPrice() >= offeredPrice) {
            return "failed";
        } else {
            //TODO : lock buyer money
            bid.getOfferedPrice().put(offeredPrice, account.getUsername());
            bid.setMaxPrice(offeredPrice);
            return "done";
        }
    }
}
