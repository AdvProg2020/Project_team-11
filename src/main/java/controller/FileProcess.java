package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;

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
        ArrayList<Account> allAccounts = new ArrayList<>();
        ArrayList<ExchangeLog> allLogs = new ArrayList<>();
        Scanner scanner = FileProcess.openFileToRead("resources\\admins.json");
        String data = scanner.nextLine();
        Type foundListType = new TypeToken<ArrayList<Admin>>(){}.getType();
        ArrayList<Admin> allAdmins = gson.fromJson(data, foundListType);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\sellers.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Seller>>(){}.getType();
        ArrayList<Seller> allSellers = gson.fromJson(data, foundListType);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\buyers.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Buyer>>(){}.getType();
        ArrayList<Buyer> allBuyers = gson.fromJson(data, foundListType);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\supports.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Support>>(){}.getType();
        ArrayList<Support> allSupports = gson.fromJson(data, foundListType);
        scanner.close();
        allAccounts.addAll(allAdmins);
        allAccounts.addAll(allSellers);
        allAccounts.addAll(allBuyers);
        allAccounts.addAll(allSupports);
        DataBase.getDataBase().setAllAccounts(allAccounts);
        scanner = FileProcess.openFileToRead("resources\\auctions.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Auction>>(){}.getType();
        ArrayList<Auction> auctions = gson.fromJson(data, foundListType);
        DataBase.getDataBase().setAllAuctions(auctions);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\categories.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Category>>(){}.getType();
        ArrayList<Category> categories = gson.fromJson(data, foundListType);
        DataBase.getDataBase().setAllCategories(categories);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\comments.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Comment>>(){}.getType();
        ArrayList<Comment> comments = gson.fromJson(data, foundListType);
        DataBase.getDataBase().setAllComments(comments);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\discounts.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Discount>>(){}.getType();
        ArrayList<Discount> discounts = gson.fromJson(data, foundListType);
        DataBase.getDataBase().setAllDiscounts(discounts);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\sell logs.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<SellLog>>(){}.getType();
        ArrayList<SellLog> sellLogs = gson.fromJson(data, foundListType);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\buy logs.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<BuyLog>>(){}.getType();
        ArrayList<BuyLog> buyLogs = gson.fromJson(data, foundListType);
        allLogs.addAll(sellLogs);
        allLogs.addAll(buyLogs);
        DataBase.getDataBase().setAllLogs(allLogs);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\products.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Product>>(){}.getType();
        ArrayList<Product> products = gson.fromJson(data, foundListType);
        DataBase.getDataBase().setAllProducts(products);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\rates.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Rate>>(){}.getType();
        ArrayList<Rate> rates = gson.fromJson(data, foundListType);
        DataBase.getDataBase().setAllRates(rates);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\requests.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<ArrayList<Request>>(){}.getType();
        ArrayList<Request> requests = gson.fromJson(data, foundListType);
        DataBase.getDataBase().setAllRequests(requests);
        scanner.close();
        scanner = FileProcess.openFileToRead("resources\\bank operation.json");
        data = scanner.nextLine();
        foundListType = new TypeToken<BankOperation>(){}.getType();
        BankOperation bankOperation = gson.fromJson(data, foundListType);
        DataBase.getDataBase().setBankOperation(bankOperation);
        scanner.close();

        scanner = FileProcess.openFileToRead("resources\\has admin.json");
        boolean hasAdmin = Boolean.parseBoolean(scanner.nextLine());
        DataBase.getDataBase().setHasAdminAccountCreated(hasAdmin);
        scanner.close();
    }

    public static void writeDataBaseOnFile() {
        Gson gson = new Gson();
        ArrayList<Admin> allAdmins = new ArrayList<>();
        ArrayList<Seller> allSellers = new ArrayList<>();
        ArrayList<Buyer> allBuyers = new ArrayList<>();
        ArrayList<Support> allSupports = new ArrayList<>();
        for (Account account : DataBase.getDataBase().getAllAccounts()) {
            if (account instanceof Admin)
                allAdmins.add((Admin) account);
            else if (account instanceof Seller)
                allSellers.add((Seller) account);
            else if (account instanceof Buyer)
                allBuyers.add((Buyer) account);
            else if (account instanceof Support)
                allSupports.add((Support) account);
        }
        ArrayList<SellLog> allSellLogs = new ArrayList<>();
        ArrayList<BuyLog> allBuyLogs = new ArrayList<>();
        for (ExchangeLog log : DataBase.getDataBase().getAllLogs()) {
            if (log instanceof BuyLog)
                allBuyLogs.add((BuyLog) log);
            else
                allSellLogs.add((SellLog) log);
        }
        Formatter formatter = FileProcess.openFileToWrite("resources\\admins.json");
        formatter.format(gson.toJson(allAdmins));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\sellers.json");
        formatter.format(gson.toJson(allSellers));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\buyers.json");
        formatter.format(gson.toJson(allBuyers));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\supports.json");
        formatter.format(gson.toJson(allSupports));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\auctions.json");
        formatter.format(gson.toJson(DataBase.getDataBase().getAllAuctions()));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\categories.json");
        formatter.format(gson.toJson(DataBase.getDataBase().getAllCategories()));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\comments.json");
        formatter.format(gson.toJson(DataBase.getDataBase().getAllComments()));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\discounts.json");
        formatter.format(gson.toJson(DataBase.getDataBase().getAllDiscounts()));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\sell logs.json");
        formatter.format(gson.toJson(allSellLogs));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\buy logs.json");
        formatter.format(gson.toJson(allBuyLogs));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\products.json");
        formatter.format(gson.toJson(DataBase.getDataBase().getAllProducts()));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\rates.json");
        formatter.format(gson.toJson(DataBase.getDataBase().getAllRates()));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\requests.json");
        formatter.format(gson.toJson(DataBase.getDataBase().getAllRequests()));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\bank operation.json");
        formatter.format(gson.toJson(DataBase.getDataBase().getBankOperation()));
        formatter.close();
        formatter = FileProcess.openFileToWrite("resources\\has admin.json");
        formatter.format(gson.toJson(DataBase.getDataBase().getHasAdminAccountCreated()));
        formatter.close();
    }
}
