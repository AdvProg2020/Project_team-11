package controller;

import controller.AllAccountZone;
import model.Seller;

import java.util.ArrayList;

public class SellerZone {
    private static ArrayList<Seller> allSellers = new ArrayList<>();

    public static void addSeller(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password, String companyName) {
        allSellers.add(new Seller(username, firstName, lastName, emailAddress, phoneNumber, password, companyName, 0));
    }

    public static ArrayList<Seller> getAllSellers() {
        return allSellers;
    }
}
