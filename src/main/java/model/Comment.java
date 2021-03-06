package model;

public class Comment {
    int id;
    private final String buyerName;
    private final int productId;
    private final String commentText;
    private String status;
    private final boolean hasUserBoughtProduct;
    private static int numOfAllComments;

    public Comment(String buyerName, int productId, String commentText, String status, boolean hasUserBoughtProduct) {
        this.id = numOfAllComments;
        Comment.numOfAllComments += 1;
        this.buyerName = buyerName;
        this.productId = productId;
        this.commentText = commentText;
        this.status = status;
        this.hasUserBoughtProduct = hasUserBoughtProduct;
        DataBase.getDataBase().setAllComments(this);
    }

    public String getBuyerName() {
        return buyerName;
    }

    public int getProductId() {
        return productId;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getStatus() {
        return status;
    }

    public boolean hasUserBoughtProduct() {
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

    public static void setNumOfAllComments(int numOfAllComments) {
        Comment.numOfAllComments = numOfAllComments;
    }
}
