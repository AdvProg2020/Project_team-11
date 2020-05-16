package model;

public class Comment {
    int id;
    private String buyerName;
    private Product product;
    private String commentText;
    private String status;
    private boolean hasUserBoughtProduct;
    private static int numOfAllComments = 1;

    public Comment(String buyerName, Product product, String commentText, String status, boolean hasUserBoughtProduct) {
        this.id = numOfAllComments;
        Comment.numOfAllComments += 1;
        this.buyerName = buyerName;
        this.product = product;
        this.commentText = commentText;
        this.status = status;
        this.hasUserBoughtProduct = hasUserBoughtProduct;
        DataBase.getDataBase().setAllComments(this);
    }

    public String getBuyerName() {
        return buyerName;
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

    public int getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Comment getCommentById(int id) {
        for (Comment comment : DataBase.getDataBase().getAllComments()) {
            if (comment.getId() == id)
                return comment;
        }
        return null;
    }
}
