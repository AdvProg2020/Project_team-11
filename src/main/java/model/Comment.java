package model;

import java.util.ArrayList;

public class Comment {
    private Buyer buyer;
    private Product product;
    private String commentText;
    private String status;
    private boolean hasUserBoughtProduct;
    private static ArrayList<Comment> allComments = new ArrayList<>();

    public Comment(Buyer buyer, Product product, String commentText, String status, boolean hasUserBoughtProduct) {
        this.buyer = buyer;
        this.product = product;
        this.commentText = commentText;
        this.status = status;
        this.hasUserBoughtProduct = hasUserBoughtProduct;
        allComments.add(this);
    }
}
