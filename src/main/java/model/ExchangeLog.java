package model;

import java.util.Date;

public abstract class ExchangeLog {
    protected int id;
    protected Date date;

    public ExchangeLog(int id, Date date) {
        this.id = id;
        this.date = date;
        DataBase.getDataBase().setAllLogs(this);
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
