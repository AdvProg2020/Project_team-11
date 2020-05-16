package view;

import controller.FileProcess;
import model.*;
import view.menu.MainMenu;
import view.menu.Menu;

import java.io.*;
import java.util.Comparator;

public class CommandProcessor {

    public static void runMenus() {
        if (new File("resources\\admins.json").exists()) {
            FileProcess.initialize();
        }
        setStaticValues();
        Menu currentMenu = new MainMenu();
        Menu.setMainMenu(currentMenu);
        currentMenu.execute();
    }

    private static void setStaticValues() {
        if (!DataBase.getDataBase().getAllAuctions().isEmpty()) {
            Auction.setNumOfAllAuctions(DataBase.getDataBase().getAllAuctions().stream()
                    .max(Comparator.comparingInt(Auction::getId)).get().getId() + 1);
        } else {
            Auction.setNumOfAllAuctions(1);
        }
        if (!DataBase.getDataBase().getAllComments().isEmpty()) {
            Comment.setNumOfAllComments(DataBase.getDataBase().getAllComments().stream()
                    .max(Comparator.comparingInt(Comment::getId)).get().getId() + 1);
        } else {
            Comment.setNumOfAllComments(1);
        }
        if (!DataBase.getDataBase().getAllLogs().isEmpty()) {
            ExchangeLog.setNumOfAllLogs(DataBase.getDataBase().getAllLogs().stream()
                    .max(Comparator.comparingInt(ExchangeLog::getId)).get().getId() + 1);
        } else {
            ExchangeLog.setNumOfAllLogs(1);
        }
        if (!DataBase.getDataBase().getAllProducts().isEmpty()) {
            Product.setNumOfAllProducts(DataBase.getDataBase().getAllProducts().stream()
                    .max(Comparator.comparingInt(Product::getId)).get().getId() + 1);
        } else {
            Product.setNumOfAllProducts(1);
        }
        if (!DataBase.getDataBase().getAllRequests().isEmpty()) {
            Request.setNumOfAllRequest(DataBase.getDataBase().getAllRequests().stream()
                    .max(Comparator.comparingInt(Request::getId)).get().getId() + 1);
        } else {
            Request.setNumOfAllRequest(1);
        }
    }
}
