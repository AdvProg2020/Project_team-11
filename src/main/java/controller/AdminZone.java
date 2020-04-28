package controller;

import model.Admin;

import java.util.ArrayList;

public class AdminZone {

    private static ArrayList<Admin> allAdmins = new ArrayList<>();

    public static void addAdmin (String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password ) {
        allAdmins.add(new Admin(username, firstName, lastName, emailAddress, phoneNumber, password));
    }

    public static ArrayList<Admin> getAllAdmins() {
        return allAdmins;
    }
}
