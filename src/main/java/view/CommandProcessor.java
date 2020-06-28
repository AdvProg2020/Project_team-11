package view;

import controller.FileProcess;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;

public class CommandProcessor extends Application {
    private static Stage stage;
    private static MediaPlayer mediaPlayer;

    public static Stage getStage() {
        return stage;
    }

    public void run(String[] args) {
        if (new File("resources\\admins.json").exists()) {
            FileProcess.initialize();
        }
        setStaticValues();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(new FileInputStream("Styles/Photos/shop.png")));
        CommandProcessor.stage = stage;
        stage.setTitle("Store");
        Scene scene;

        Screen screen = Screen.getPrimary();
        Rectangle2D bound = screen.getBounds();
        if (DataBase.getDataBase().getHasAdminAccountCreated()) {
            scene = new Scene(MainScenes.getMainMenuScene(), bound.getWidth(), bound.getHeight());
        } else {
            scene = new Scene(MainScenes.getRegisterAdminScene(), bound.getWidth(), bound.getHeight());
        }
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        new Thread(() -> {
            Media media = new Media(new File("Styles/Sound/background music.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            CommandProcessor.mediaPlayer = mediaPlayer;
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }).start();

        stage.setOnCloseRequest(e -> FileProcess.writeDataBaseOnFile());
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
