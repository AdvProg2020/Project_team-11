package view;

import controller.AdminZone;
import controller.AllAccountZone;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.tableViewData.Account;

import java.util.ArrayList;
import java.util.Arrays;

import static view.MainScenes.*;

public class AdminScene {

    public static Scene getAdminScene() {
        Button personalInfo = createButton("View Personal Info", 200);
        personalInfo.setOnMouseClicked(e -> getPersonalInfo());

        Button users = createButton("Manage Users", 200);
        users.setOnMouseClicked(e -> showUsers());

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

        openStage(gridPane, "Personal Info", 600, 550);
    }

    private static void showUsers() {
        TableColumn<Account, String> typeColumn = new TableColumn<>("Account Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));

        TableColumn<Account, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Account, String> lastNameColumn = new TableColumn<>("LastName");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Account, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(100);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Account, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setMinWidth(100);
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Account, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(100);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Account, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(100);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<Account, String> balanceColumn = new TableColumn<>("Wallet");
        balanceColumn.setMinWidth(100);
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("wallet"));

        TableColumn<Account, String> companyColumn = new TableColumn<>("Company");
        companyColumn.setMinWidth(100);
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        TableView<Account> tableView = new TableView<>();
        tableView.setItems(Account.getAccounts());
        tableView.getColumns().addAll(typeColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn,
                usernameColumn, passwordColumn, balanceColumn, companyColumn);

        TextField firstName = createTextField("First Name", 100);
        TextField lastName = createTextField("Last Name", 100);
        TextField email = createTextField("Email", 100);
        TextField phoneNumber = createTextField("Phone Number", 100);
        TextField username = createTextField("Username", 100);
        TextField password = createTextField("Password", 100);

        Button addButton = createButton("Add Admin", 100);
        addButton.setOnMouseClicked(e -> {
            ArrayList<String> info = new ArrayList<>(Arrays.asList("admin", firstName.getText(),
                    lastName.getText(), email.getText(), phoneNumber.getText(), username.getText(), password.getText()));
            if (Actions.register(info)) {
                tableView.setItems(Account.getAccounts());
                firstName.clear();
                lastName.clear();
                email.clear();
                phoneNumber.clear();
                username.clear();
                password.clear();
            }
        });
        Button removeButton = createButton("Remove", 100);
        removeButton.setOnMouseClicked(e -> {
            ObservableList<Account> accountSelected, allAccounts;
            allAccounts = tableView.getItems();
            accountSelected = tableView.getSelectionModel().getSelectedItems();

            accountSelected.forEach(account -> {
                if (!account.getAccountType().equals("Admin")) {
                    allAccounts.remove(account);
                    AdminZone.deleteUser(account.getUsername());
                }
            });
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(firstName, lastName, email, phoneNumber, username, password, addButton, removeButton);
        hBox.setPadding(new Insets(10, 10, 10, 10));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView, hBox);

        openStage(vBox, "Users", 900, 500);
    }

    private static void openStage(Parent root, String title, int width, int height) {
        Scene scene = new Scene(root, width, height);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(CommandProcessor.getStage());
        stage.show();
    }
}
