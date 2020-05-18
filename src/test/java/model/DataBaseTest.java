package model;

import controller.FileProcess;
import org.junit.Assert;
import org.junit.Test;

public class DataBaseTest {
    private final DataBase dataBase = new DataBase();

    @Test
    public void classTest() {
        FileProcess.initialize();
        Assert.assertFalse(dataBase.getHasAdminAccountCreated());
    }
}
