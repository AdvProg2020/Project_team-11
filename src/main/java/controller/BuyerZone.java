package controller;

import model.Buyer;

import java.util.ArrayList;

public class BuyerZone {

    private static ArrayList<Buyer> allBuyers = new ArrayList<>();

    public static void addBuyer(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        allBuyers.add(new Buyer(username, firstName, lastName, emailAddress, phoneNumber, password, 0));
    }

    public ArrayList<Buyer> getAllBuyers() {
        return allBuyers;
    }
}
