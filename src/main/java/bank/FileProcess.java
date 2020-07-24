package bank;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Bid;
import model.DataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class FileProcess {

    private static Formatter openFileToWrite(String address) {
        Formatter formatter = null;
        try {
            new File(address.substring(0, address.lastIndexOf("\\"))).mkdir();
            formatter = new Formatter(address);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    private static Scanner openFileToRead(String address) {
        Scanner scanner = null;
        try {
            File file = new File(address);
            scanner = new Scanner(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return scanner;
    }

    public static void initialize() {
        Gson gson = new Gson();
        Scanner scanner = FileProcess.openFileToRead("resources\\bank\\accounts.json");
        String data = scanner.nextLine();
        Type foundListType = new TypeToken<ArrayList<Account>>(){}.getType();
        ArrayList<Account> accounts = gson.fromJson(data, foundListType);
        Account.setAllAccounts(accounts);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\bank\\receipts.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Receipt>>(){}.getType();
        ArrayList<Receipt> receipts = gson.fromJson(data, foundListType);
        Receipt.setAllReceipts(receipts);
        scanner.close();
    }

    public static void writeDataOnFile() {
        Gson gson = new Gson();
        Formatter formatter = controller.FileProcess.openFileToWrite("resources\\bank\\accounts.json");
        formatter.format(gson.toJson(Account.getAllAccounts()));
        formatter.close();
        formatter = controller.FileProcess.openFileToWrite("resources\\bank\\receipts.json");
        formatter.format(gson.toJson(Receipt.getAllReceipts()));
        formatter.close();
    }
}
