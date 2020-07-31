package Client.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static Client.view.MainScenes.*;
import static Client.view.ServerConnection.*;

public class SupportScene {
    private static Gson gson = new Gson();

    public static Parent getSupportRoot() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);

        try {
            String data = getMessageSenders();
            Type foundListType = new TypeToken<Set<String>>() {}.getType();
            Set<String> senders = gson.fromJson(data, foundListType);

            for (String sender : senders) {
                Button senderButton = createButton(sender, 300);
                senderButton.setMinHeight(50);
                senderButton.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(getChatRoot(sender)));
                senderButton.getStyleClass().add("account-button");

                vBox.getChildren().add(senderButton);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent getChatRoot(String sender) {
        VBox messageVBox = new VBox(10);
        messageVBox.setAlignment(Pos.TOP_CENTER);

        try {
            String data = getMessages(sender);
            Type foundListType = new TypeToken<ArrayList<HashMap<String, String>>>() {}.getType();
            ArrayList<HashMap<String, String>> messages = gson.fromJson(data, foundListType);

            for (HashMap<String, String> message : messages) {
                for (Map.Entry<String, String> entry : message.entrySet()) {
                    Label messageLabel = createLabel(entry.getValue(), 500);
                    if (entry.getKey().equals(sender)) {
                        messageLabel.setAlignment(Pos.CENTER_LEFT);
                    } else {
                        messageLabel.setAlignment(Pos.CENTER_RIGHT);
                    }
                    messageVBox.getChildren().add(messageLabel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextField textField = createTextField("Message", 800);
        Button send = createButton("Send", 100);

        send.setOnMouseClicked(e -> {
            if (textField.getText() != null) {
                Label message = createLabel(textField.getText(), 500);
                message.setAlignment(Pos.CENTER_RIGHT);
                messageVBox.getChildren().add(message);
                try {
                    sendMessageSupport("me", sender, textField.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            textField.clear();
        });

        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(textField, send);
        VBox sendVBox = new VBox(hBox);
        sendVBox.setAlignment(Pos.BOTTOM_CENTER);

        ScrollPane scrollPane = new ScrollPane(messageVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(scrollPane, sendVBox);

        return vBox;
    }
}
