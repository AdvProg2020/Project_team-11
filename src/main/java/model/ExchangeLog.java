package model;

import java.util.ArrayList;
import java.util.Date;

public abstract class ExchangeLog {
    protected int id;
    protected Date date;
    protected static ArrayList<ExchangeLog> allLogs = new ArrayList<>();

    public ExchangeLog(int id, Date date) {
        this.id = id;
        this.date = date;
        allLogs.add(this);
    }
}
