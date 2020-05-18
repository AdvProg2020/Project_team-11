package model;

import org.junit.Assert;
import org.junit.Test;

public class RequestTest {
    private final DataBase dataBase = new DataBase();

    @Test
    public void classTest() {
        Request.setNumOfAllRequest(1);
        Request request = new Request("mamad", "add product", "1", "unseen");
        Assert.assertFalse(dataBase.getAllRequests().isEmpty());
        request.setStatus("accepted");
        Assert.assertEquals("accepted", request.getStatus());
        Assert.assertEquals(1, request.getId());
        Assert.assertEquals("mamad", request.getSenderName());
        Assert.assertEquals("add product", request.getTopic());
        Assert.assertEquals("1", request.getDescription());
    }
}
