package model;

public class Comment {
    private Buyer buyer;
    private Product product;
    private String commentText;
    private String status;
    private boolean hasUserBoughtProduct;

    public Comment(Buyer buyer, Product product, String commentText, String status, boolean hasUserBoughtProduct) {
        this.buyer = buyer;
        this.product = product;
        this.commentText = commentText;
        this.status = status;
        this.hasUserBoughtProduct = hasUserBoughtProduct;
        DateBase.getDateBase().setAllComments(this);
    }
}
