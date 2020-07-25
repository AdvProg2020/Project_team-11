package Client.view;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Application {
    private static final int STORE_SERVER_PORT = 8888;
    private static Stage stage;
    private static MediaPlayer mediaPlayer;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static Stage getStage() {
        return stage;
    }

    public static DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public static DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(new FileInputStream("Styles/Photos/shop.png")));
        ClientHandler.stage = stage;
        stage.setTitle("Store");
        Scene scene;

        Screen screen = Screen.getPrimary();
        Rectangle2D bound = screen.getBounds();

        boolean hasAdmin = false;
        try {
            Socket socket = new Socket("127.0.0.1", STORE_SERVER_PORT);
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("open app");
            dataOutputStream.flush();
            dataInputStream.readUTF();
            dataOutputStream.writeUTF("has admin");
            dataOutputStream.flush();
            hasAdmin = dataInputStream.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (hasAdmin) {
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
            ClientHandler.mediaPlayer = mediaPlayer;
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }).start();

        stage.setOnCloseRequest(e -> {
            try {
                dataOutputStream.writeUTF("exit");
                getDataOutputStream().flush();
                dataInputStream.readUTF();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
