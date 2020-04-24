package controller;

import model.Account;
import model.DataBase;
import model.Product;

public class AllAccountZone {
    private static Account currentAccount = null;

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        AllAccountZone.currentAccount = currentAccount;
    }

    public static Product getProductById(int Id) {
        for (Product product : DataBase.getDataBase().getAllProducts()) {
            if (product.getId() == Id)
                return product;
        }
        return null;
    }
}