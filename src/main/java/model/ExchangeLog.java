package model;

import java.util.Date;

public abstract class ExchangeLog {
    protected int id;
    protected Date date;
    protected static int numOfAllLogs = 1;

    public ExchangeLog(Date date) {
        this.id = numOfAllLogs;
        this.date = date;
        numOfAllLogs += 1;
        DataBase.getDataBase().setAllLogs(this);
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
