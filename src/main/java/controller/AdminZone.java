package controller;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AdminZone {

    public static String showAllRequests() {
        StringBuilder allRequests = new StringBuilder();
        for (Request request : DataBase.getDataBase().getAllRequests()) {
            if (request.getStatus().equals("unseen")) {
                allRequests.append(request.getId()).append(". ").append(request.getTopic()).append("\n");
            }
        }
        return String.valueOf(allRequests);
    }

    public static String viewRequestDetails(int requestId) {
        Request request = getRequestById(requestId);
        if (request == null) {
            return "invalid request ID";
        } else {
            return requestId + ". " + request.getTopic() + ": \n" +
                    request.getSender().getFirstName() + " " + request.getSender().getLastName() + "\n" +
                    request.getDescription();
        }
    }

    public static String acceptRequest(int requestId) {
        Request request = getRequestById(requestId);
        if (request == null || !request.getStatus().equals("unseen")) {
            return "invalid request ID";
        } else if (request.getTopic().equals("create seller account")) {
            acceptRequestCreateSellerAccount(request);
        } else if (request.getTopic().equals("edit product")) {
            acceptRequestEditProduct(request);
        } else if (request.getTopic().equals("add product")) {
            acceptRequestAddProduct(request);
        } else if (request.getTopic().equals("add auction")) {
            acceptRequestAddAuction(request);
        } else if (request.getTopic().equals("edit auction")) {
            acceptRequestEditAuction(request);
        }
        request.setStatus("accepted");
        return "Done";
    }

    private static Request getRequestById(int requestId) {
        for (Request request : DataBase.getDataBase().getAllRequests()) {
            if (request.getId() == requestId)
                return request;
        }
        return null;
    }

    private static void acceptRequestEditProduct(Request request) {
        String info = request.getDescription();
        ArrayList<String> splitInfo = new ArrayList<>(Arrays.asList(info.split(",")));
        Product product = getProductByIdAndSeller(Integer.parseInt(splitInfo.get(0)), request.getSender());
        assert product != null;
        if (!splitInfo.get(1).equals("next"))
            product.getGeneralFeature().setName(splitInfo.get(1));
        if (!splitInfo.get(2).equals("next"))
            product.getGeneralFeature().setCompany(splitInfo.get(2));
        if (!splitInfo.get(3).equals("next"))
            product.getGeneralFeature().setPrice(Long.parseLong(splitInfo.get(3)));
        if (!splitInfo.get(4).equals("next"))
            product.getGeneralFeature().setStockStatus(Integer.parseInt(splitInfo.get(4)));
        if (!splitInfo.get(5).equals("next"))
            product.setDescription(splitInfo.get(5));
    }

    private static void acceptRequestCreateSellerAccount(Request request) {
        String info = request.getDescription();
        ArrayList<String> splitInfo = new ArrayList<>(Arrays.asList(info.split(",")));
        new Seller(splitInfo.get(0), splitInfo.get(1), splitInfo.get(2), splitInfo.get(3),
                splitInfo.get(4), splitInfo.get(5), splitInfo.get(6),Long.parseLong(splitInfo.get(7)));
    }

    private static void acceptRequestAddProduct(Request request) {
        String info = request.getDescription();
        int productId = Integer.parseInt(info);
        Product product = getProductByIdAndSeller(productId, request.getSender());
        assert product != null;
        product.setStatus("accepted");
    }

    private static void acceptRequestAddAuction(Request request) {
        String info = request.getDescription();
        int auctionId = Integer.parseInt(info);
        Auction auction = getAuctionById(auctionId);
        assert auction != null;
        auction.setStatus("accepted");
    }

    private static void acceptRequestEditAuction(Request request) {
        String info = request.getDescription();
        ArrayList<String> splitInfo = new ArrayList<>(Arrays.asList(info.split(",")));
        Auction auction = getAuctionById(Integer.parseInt(splitInfo.get(0)));
        assert auction != null;
        if (!splitInfo.get(1).equals("next")) {
            ArrayList<String> productsId = new ArrayList<>(Arrays.asList(splitInfo.get(1).split("/")));
            ArrayList<Product> productList = new ArrayList<>();
            for (String productId : productsId) {
                Product product = getProductByIdAndSeller(Integer.parseInt(productId), request.getSender());
                productList.add(product);
            }
            auction.setProductList(productList);
        }
        if (!splitInfo.get(2).equals("next")) {
            long milliSeconds = Long.parseLong(splitInfo.get(2));
            Date startDate = new Date(milliSeconds);
            auction.setStartDate(startDate);
        }
        if (!splitInfo.get(3).equals("next")) {
            long milliSeconds = Long.parseLong(splitInfo.get(3));
            Date endDate = new Date(milliSeconds);
            auction.setStartDate(endDate);
        }
        if (!splitInfo.get(4).equals("next")) {
            auction.setDiscountAmount(Integer.parseInt(splitInfo.get(4)));
        }
    }

    private static Product getProductByIdAndSeller(int productId, Seller seller) {
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == productId && product.getGeneralFeature().getSeller().equals(seller))
                return product;
        }
        return null;
    }

    private static Auction getAuctionById(int auctionId) {
        for (Auction auction : DataBase.getDataBase().getAllAuctions()) {
            if (auction.getId() == auctionId)
                return auction;
        }
        return null;
    }

    public static String declineRequest(int requestId) {
        Request request = getRequestById(requestId);
        if (request == null || !request.getStatus().equals("unseen"))
            return "invalid request ID";
        else
            request.setStatus("declined");
        return "Done";
    }
}
