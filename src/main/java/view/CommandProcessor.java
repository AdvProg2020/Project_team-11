package view;

import controller.FileProcess;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.util.Comparator;

public class CommandProcessor extends Application {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void runMenus(String[] args) {
        if (new File("resources\\admins.json").exists()) {
            FileProcess.initialize();
        }
        setStaticValues();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage stage = new Stage();
        CommandProcessor.stage = stage;
        stage.setTitle("Store");
        Scene scene;
        scene = Scenes.getMainMenuScene();
        if (!DataBase.getDataBase().getHasAdminAccountCreated()) {
            scene = Scenes.getRegisterAdminScene();
        }
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
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
