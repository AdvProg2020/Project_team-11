package model;

import java.util.ArrayList;

public class DateBase {
    private static DateBase dateBase;
    private ArrayList<Account> allAccounts = new ArrayList<>();
    private ArrayList<Auction> allAuctions = new ArrayList<>();
    private ArrayList<Category> allCategories = new ArrayList<>();
    private ArrayList<Comment> allComments = new ArrayList<>();
    private ArrayList<Discount> allDiscounts = new ArrayList<>();
    private ArrayList<ExchangeLog> allLogs = new ArrayList<>();
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Rate> allRates = new ArrayList<>();
    private ArrayList<Request> allRequests = new ArrayList<>();

    public DateBase() {
        dateBase = this;
    }

    public static DateBase getDateBase() {
        return dateBase;
    }

    //set an array to fields
    public void setAllAccounts(ArrayList<Account> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public void setAllAuctions(ArrayList<Auction> allAuctions) {
        this.allAuctions = allAuctions;
    }

    public void setAllCategories(ArrayList<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public void setAllComments(ArrayList<Comment> allComments) {
        this.allComments = allComments;
    }

    public void setAllDiscounts(ArrayList<Discount> allDiscounts) {
        this.allDiscounts = allDiscounts;
    }

    public void setAllLogs(ArrayList<ExchangeLog> allLogs) {
        this.allLogs = allLogs;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void setAllRates(ArrayList<Rate> allRates) {
        this.allRates = allRates;
    }

    public void setAllRequests(ArrayList<Request> allRequests) {
        this.allRequests = allRequests;
    }

    //add a value to field
    public void setAllAccounts(Account account) {
        this.allAccounts.add(account);
    }

    public void setAllAuctions(Auction auction) {
        this.allAuctions.add(auction);
    }

    public void setAllCategories(Category category) {
        this.allCategories.add(category);
    }

    public void setAllComments(Comment comment) {
        this.allComments.add(comment);
    }

    public void setAllDiscounts(Discount discount) {
        this.allDiscounts.add(discount);
    }

    public void setAllLogs(ExchangeLog log) {
        this.allLogs.add(log);
    }

    public void setAllProducts(Product product) {
        this.allProducts.add(product);
    }

    public void setAllRates(Rate rate) {
        this.allRates.add(rate);
    }

    public void setAllRequests(Request request) {
        this.allRequests.add(request);
    }

    //getter
    public ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public ArrayList<Discount> getAllDiscounts() {
        return allDiscounts;
    }

    public ArrayList<ExchangeLog> getAllLogs() {
        return allLogs;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public ArrayList<Rate> getAllRates() {
        return allRates;
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }
}
