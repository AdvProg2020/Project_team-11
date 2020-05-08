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

    public static ExchangeLog getLogById(int logId) {
        for (ExchangeLog log : DataBase.getDataBase().getAllLogs()) {
            if (log.getId() == logId)
                return log;
        }
        return null;
    }
}
