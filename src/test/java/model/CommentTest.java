package model;

import org.junit.Assert;
import org.junit.Test;

public class CommentTest {
    private final DataBase dataBase = new DataBase();

    @Test
    public void classTest() {
        Comment.setNumOfAllComments(1);
        Comment comment = new Comment("ali",
                                    5,
                                    "very ashghal",
                                    "accepted",
                                    true);
        Assert.assertFalse(dataBase.getAllComments().isEmpty());
        Assert.assertEquals(1, comment.getId());
        Assert.assertEquals("very ashghal", comment.getCommentText());
        Assert.assertEquals("ali", comment.getBuyerName());
        Assert.assertEquals(5, comment.getProductId());
        comment.setStatus("rejected");
        Assert.assertEquals("rejected", comment.getStatus());
        Assert.assertTrue(comment.hasUserBoughtProduct());
        Assert.assertEquals(comment, Comment.getCommentById(1));
        Assert.assertNull(Comment.getCommentById(-1));
    }
}
