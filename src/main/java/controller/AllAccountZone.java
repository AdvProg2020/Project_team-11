package controller;

import model.Account;
import model.DataBase;
import model.Product;

import java.util.Date;

public class AllAccountZone {
    private static Account currentAccount = null;

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        AllAccountZone.currentAccount = currentAccount;
    }

    public static Date getCurrentDate() {
        Date UTCDate = new Date();
        final Date diffDate = new Date((4 * 3600 + 30 * 60) * 1000);
        long sum = UTCDate.getTime() + diffDate.getTime();
        return new Date(sum);
    }
}