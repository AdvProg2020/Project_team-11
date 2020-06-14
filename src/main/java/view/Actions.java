package view;

import controller.AllAccountZone;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Actions {

    public static void register(ArrayList<String> info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (info.get(0) == null) {
            alert.setContentText("Choose a type");
            alert.show();
        } else if (!Validation.validateNames(info.get(1))) {
            alert.setContentText("Enter your first name.");
            alert.show();
        } else if (!Validation.validateNames(info.get(2))) {
            alert.setContentText("Enter your last name.");
            alert.show();
        } else if (!Validation.validateEmail(info.get(3))) {
            alert.setContentText("Email address is not valid.");
            alert.show();
        } else if (!Validation.validateLong(info.get(4))) {
            alert.setContentText("Phone number is not valid.");
            alert.show();
        } else if (!Validation.validateNames(info.get(5))) {
            alert.setContentText("Enter username.");
            alert.show();
        } else if (!Validation.validateNames(info.get(6))) {
            alert.setContentText("Enter your password.");
            alert.show();
        } else if (!info.get(0).equals("admin") && !Validation.validateLong(info.get(7))) {
            alert.setContentText("Balance format is not valid.");
            alert.show();
        } else if (info.get(0).equals("seller") && !Validation.validateNames(info.get(8))) {
            alert.setContentText("Enter Company name.");
            alert.show();
        } else if (!AllAccountZone.isUsernameValid(info.get(5))) {
            alert.setContentText("This username is already occupied.");
            alert.show();
        } else {
            AllAccountZone.createAccount(info);
            if (Scenes.getLastScene() == null)
                CommandProcessor.getStage().setScene(Scenes.getMainScene());
            else
                CommandProcessor.getStage().setScene(Scenes.getLastScene());
            CommandProcessor.getStage().setMaximized(false);
            CommandProcessor.getStage().setMaximized(true);
        }
    }

    public static void signIn(ArrayList<String> info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!Validation.validateNames(info.get(1))) {
            alert.setContentText("Enter username.");
            alert.show();
        } else if (!Validation.validateNames(info.get(2))) {
            alert.setContentText("Enter your password.");
            alert.show();
        } else {
            String result = AllAccountZone.loginUser(info);
            if (result.equals("Login successfully.")) {
                Scenes.getSignInOrOut().setText("Logout");

                Scenes.getSignInOrOut().setOnMouseClicked(e -> {
                    ((VBox) Scenes.getMainScene().getRoot()).getChildren().remove(Scenes.getAccountButton());
                    AllAccountZone.setCurrentAccount(null);

                    Scenes.getSignInOrOut().setText("Sign In");
                    Scenes.getSignInOrOut().setOnMouseClicked(event -> {
                        Scenes.setLastScene(CommandProcessor.getStage().getScene());
                        CommandProcessor.getStage().setScene(Scenes.getSignInScene());
                        CommandProcessor.getStage().setMaximized(false);
                        CommandProcessor.getStage().setMaximized(true);
                    });

                    CommandProcessor.getStage().setScene(Scenes.getMainScene());
                    CommandProcessor.getStage().setMaximized(false);
                    CommandProcessor.getStage().setMaximized(true);
                });

                CommandProcessor.getStage().setScene(Scenes.getLastScene());
                CommandProcessor.getStage().setMaximized(false);
                CommandProcessor.getStage().setMaximized(true);
            } else {
                alert.setContentText(result);
                alert.show();
            }
        }
    }
}
