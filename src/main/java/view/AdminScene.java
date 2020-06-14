package view;

import controller.AllAccountZone;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

import static view.MainScenes.*;

public class AdminScene {

    public static Scene getAdminScene() {
        Button personalInfo = createButton("View Personal Info", 200);
        personalInfo.setOnMouseClicked(e -> getPersonalInfo());

        Button users = createButton("Manage Users", 200);

        Button products = createButton("Manage Products", 200);

        Button discountCodes = createButton("Manage Discount Codes", 200);

        Button requests = createButton("Manage Requests", 200);

        Button categories = createButton("Manage Categories", 200);

        Button logout = createButton("Logout", 200);
        logout.setOnMouseClicked(e -> Actions.logout());

        Button back = createButton("Back", 200);
        back.setOnMouseClicked(e -> {
            CommandProcessor.getStage().setScene(MainScenes.getMainScene());
            CommandProcessor.getStage().setMaximized(false);
            CommandProcessor.getStage().setMaximized(true);
        });

        VBox vBox = new VBox(25, personalInfo, users, products, discountCodes, requests, categories, logout, back);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }

    private static void getPersonalInfo() {
        Label firstNameLabel = createLabel("First Name : ", 150);
        Label lastNameLabel = createLabel("Last Name : ", 150);
        Label emailLabel = createLabel("Email : ", 150);
        Label phoneNumberLabel = createLabel("Phone Number : ", 150);
        Label usernameLabel = createLabel("Username : ", 150);
        Label passwordLabel = createLabel("Password : ", 150);

        ArrayList<String> personalInfo = new ArrayList<>(AllAccountZone.getPersonalInfo());

        TextField firstNameText = createTextField("First Name", 500);
        firstNameText.setText(personalInfo.get(0));
        firstNameText.setDisable(true);

        TextField lastNameText = createTextField("Last Name", 500);
        lastNameText.setText(personalInfo.get(1));
        lastNameText.setDisable(true);

        TextField emailText = createTextField("Email", 500);
        emailText.setText(personalInfo.get(2));
        emailText.setDisable(true);

        TextField phoneNumberText = createTextField("Phone Number", 500);
        phoneNumberText.setText(personalInfo.get(3));
        phoneNumberText.setDisable(true);

        TextField usernameText = createTextField("Username", 500);
        usernameText.setText(personalInfo.get(4));
        usernameText.setDisable(true);

        TextField passwordText = createTextField("Password", 500);
        passwordText.setText(personalInfo.get(5));
        passwordText.setDisable(true);

        Button firstNameButton = createButton("Edit", 100);
        firstNameButton.setOnMouseClicked(e -> Actions.editPersonalInfo(firstNameButton, firstNameText));
        Button lastNameButton = createButton("Edit", 100);
        lastNameButton.setOnMouseClicked(e -> Actions.editPersonalInfo(lastNameButton, lastNameText));
        Button emailButton = createButton("Edit", 100);
        emailButton.setOnMouseClicked(e -> Actions.editPersonalInfo(emailButton, emailText));
        Button phoneNumberButton = createButton("Edit", 100);
        phoneNumberButton.setOnMouseClicked(e -> Actions.editPersonalInfo(phoneNumberButton, phoneNumberText));
        Button passwordButton = createButton("Edit", 100);
        passwordButton.setOnMouseClicked(e -> Actions.editPersonalInfo(passwordButton, passwordText));

        GridPane gridPane = new GridPane();
        gridPane.addColumn(0, firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, usernameLabel, passwordLabel);
        gridPane.addColumn(1, firstNameText, lastNameText, emailText, phoneNumberText, usernameText, passwordText);
        gridPane.addColumn(2, firstNameButton, lastNameButton, emailButton, phoneNumberButton);
        gridPane.add(passwordButton, 2, 5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        Scene scene = new Scene(gridPane, 600, 550);
        Stage stage = new Stage();
        stage.setTitle("Personal Info");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(CommandProcessor.getStage());
        stage.show();
    }
}
