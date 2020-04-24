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
        DataBase.getDataBase().setAllComments(this);
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getStatus() {
        return status;
    }

    public boolean isHasUserBoughtProduct() {
        return hasUserBoughtProduct;
    }
}
