package view;

import controller.AllAccountZone;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Actions {

    public static boolean register(ArrayList<String> info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (info.get(0) == null) {
            alert.setContentText("Choose a type");
            alert.show();
        } else if (!Validation.validateNames(info.get(1))) {
            alert.setContentText("Enter first name.");
            alert.show();
        } else if (!Validation.validateNames(info.get(2))) {
            alert.setContentText("Enter last name.");
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
            alert.setContentText("Enter password.");
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
            return true;
        }
        return false;
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
                MainScenes.getSignInOrOut().setText("Logout");

                MainScenes.getSignInOrOut().setOnMouseClicked(e -> logout());

                CommandProcessor.getStage().setScene(MainScenes.getLastScene());
                CommandProcessor.getStage().setMaximized(false);
                CommandProcessor.getStage().setMaximized(true);
            } else {
                alert.setContentText(result);
                alert.show();
            }
        }
    }

    public static void logout() {
        ((VBox) MainScenes.getMainScene().getRoot()).getChildren().remove(MainScenes.getAccountButton());
        AllAccountZone.setCurrentAccount(null);

        MainScenes.getSignInOrOut().setText("Sign In");
        MainScenes.getSignInOrOut().setOnMouseClicked(event -> {
            MainScenes.setLastScene(CommandProcessor.getStage().getScene());
            CommandProcessor.getStage().setScene(MainScenes.getSignInScene());
            CommandProcessor.getStage().setMaximized(false);
            CommandProcessor.getStage().setMaximized(true);
        });

        CommandProcessor.getStage().setScene(MainScenes.getMainScene());
        CommandProcessor.getStage().setMaximized(false);
        CommandProcessor.getStage().setMaximized(true);
    }

    public static void editPersonalInfo(Button button, TextField textField) {
        {
            button.setText("Save");
            textField.setDisable(false);
            button.setOnMouseClicked(event -> {
                boolean isValid = false;
                switch (textField.getPromptText()) {
                    case "First Name":
                    case "Last Name":
                    case "Password":
                        isValid = Validation.validateNames(textField.getText());
                        break;
                    case "Email":
                        isValid = Validation.validateEmail(textField.getText());
                        break;
                    case "Phone Number":
                        isValid = Validation.validateLong(textField.getText());
                        break;
                }
                if (isValid) {
                    button.setText("Edit");
                    textField.setDisable(true);
                    AllAccountZone.editPersonalInfo(textField.getPromptText(), textField.getText());
                    button.setOnMouseClicked(e -> editPersonalInfo(button, textField));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid format.");
                    alert.show();
                }
            });
        }
    }
}
