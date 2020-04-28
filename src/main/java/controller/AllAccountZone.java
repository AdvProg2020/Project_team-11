package controller;

import model.Account;
import model.DataBase;

public class AllAccountZone {
    private static Account currentAccount = null;

    public static void initialize(String username){
        currentAccount = DataBase.getDataBase().getAccountByUsername(username);
    }

    public static void logout(){
        currentAccount = null;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        AllAccountZone.currentAccount = currentAccount;
    }
}