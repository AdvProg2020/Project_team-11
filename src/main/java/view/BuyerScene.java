package view;

import controller.AllAccountZone;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import static view.MainScenes.createButton;
import static view.MainScenes.createTextField;
import static view.MainScenes.createLabel;

public class BuyerScene {

    public static Pane getBuyerRoot() {
        Button personalInfo = createButton("View Personal Info", 300);
        personalInfo.setMinHeight(50);
        personalInfo.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(getPersonalInfo()));

        Button cart = createButton("View Cart", 300);
        cart.setMinHeight(50);
        Button orders = createButton("View Orders", 300);
        orders.setMinHeight(50);
        Button discountCodes = createButton("View Discount Codes", 300);
        discountCodes.setMinHeight(50);

        VBox vBox = new VBox(personalInfo, cart, orders, discountCodes);
        vBox.setAlignment(Pos.TOP_CENTER);

        return vBox;
    }

    public static Parent getPersonalInfo() {
        Label firstNameLabel = createLabel("First Name : ", 150);
        Label lastNameLabel = createLabel("Last Name : ", 150);
        Label emailLabel = createLabel("Email : ", 150);
        Label phoneNumberLabel = createLabel("Phone Number : ", 150);
        Label usernameLabel = createLabel("Username : ", 150);
        Label passwordLabel = createLabel("Password : ", 150);
        Label walletLabel = createLabel("Balance : ", 150);

        ArrayList<String> personalInfo = new ArrayList<>(AllAccountZone.getPersonalInfo());

        TextField firstNameText = createTextField("First Name", 200);
        firstNameText.setText(personalInfo.get(0));
        firstNameText.setDisable(true);

        TextField lastNameText = createTextField("Last Name", 200);
        lastNameText.setText(personalInfo.get(1));
        lastNameText.setDisable(true);

        TextField emailText = createTextField("Email", 200);
        emailText.setText(personalInfo.get(2));
        emailText.setDisable(true);

        TextField phoneNumberText = createTextField("Phone Number", 200);
        phoneNumberText.setText(personalInfo.get(3));
        phoneNumberText.setDisable(true);

        TextField usernameText = createTextField("Username", 200);
        usernameText.setText(personalInfo.get(4));
        usernameText.setDisable(true);

        TextField passwordText = createTextField("Password", 200);
        passwordText.setText(personalInfo.get(5));
        passwordText.setDisable(true);

        TextField walletText = createTextField("Wallet", 200);
        walletText.setText(personalInfo.get(6));
        walletText.setDisable(true);

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
        Button walletButton = createButton("Edit", 100);
        walletButton.setOnMouseClicked(e -> Actions.editPersonalInfo(walletButton, walletText));

        GridPane gridPane = new GridPane();
        gridPane.addColumn(0, firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, usernameLabel,
                passwordLabel, walletLabel);
        gridPane.addColumn(1, firstNameText, lastNameText, emailText, phoneNumberText, usernameText, passwordText,
                walletText);
        gridPane.addColumn(2, firstNameButton, lastNameButton, emailButton, phoneNumberButton);
        gridPane.add(passwordButton, 2, 5);
        gridPane.add(walletButton, 2, 6);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }
}
