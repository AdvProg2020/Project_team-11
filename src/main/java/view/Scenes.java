package view;

import controller.AllAccountZone;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

public class Scenes {
    private static Scene lastScene;
    private static Button accountButton;
    private static Scene mainScene;
    private static Button signInOrOut;

    public static Scene getLastScene() {
        return lastScene;
    }

    public static void setLastScene(Scene lastScene) {
        Scenes.lastScene = lastScene;
    }

    public static void setAccountButton(Button accountButton) {
        Scenes.accountButton = accountButton;
    }

    public static Button getAccountButton() {
        return accountButton;
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static Button getSignInOrOut() {
        return signInOrOut;
    }

    public static Scene getRegisterAdminScene() {
        TextField firstName = createTextField("First Name", 300);
        TextField lastName = createTextField("Last Name", 300);
        TextField email = createTextField("Email", 300);
        TextField phoneNumber = createTextField("Phone Number", 300);
        TextField username = createTextField("Username", 300);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(300);
        password.setAlignment(Pos.CENTER);

        Button register = new Button("Create Account");
        register.setAlignment(Pos.CENTER);
        register.setOnMouseClicked(e -> {
            ArrayList<String> info = new ArrayList(Arrays.asList("admin",
                    firstName.getText(), lastName.getText(), email.getText(),
                    phoneNumber.getText(), username.getText(), password.getText()));
            Actions.register(info);
        });

        VBox vBox = new VBox(25, firstName, lastName, email, phoneNumber, username, password, register);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }

    private static TextField createTextField(String text, int minWidth) {
        TextField textField = new TextField();
        textField.setPromptText(text);
        textField.setMaxWidth(minWidth);
        textField.setAlignment(Pos.CENTER);
        return textField;
    }

    private static Button createButton(String text, int minWidth) {
        Button button = new Button(text);
        button.setAlignment(Pos.CENTER);
        button.setPrefWidth(minWidth);
        return button;
    }

    public static Scene getMainMenuScene() {
        Button signIn;
        signIn = createButton("Sign in", 100);
        signIn.setOnMouseClicked(e -> {
            Scenes.setLastScene(CommandProcessor.getStage().getScene());
            CommandProcessor.getStage().setScene(Scenes.getSignInScene());
            CommandProcessor.getStage().setMaximized(false);
            CommandProcessor.getStage().setMaximized(true);
        });
        signInOrOut = signIn;
        Button products = createButton("Products", 100);
        products.setOnMouseClicked(e -> {
            CommandProcessor.getStage().setScene(Scenes.getProductsScene());
            CommandProcessor.getStage().setMaximized(false);
            CommandProcessor.getStage().setMaximized(true);
        });
        Button auctions = createButton("Auction", 100);
        auctions.setOnMouseClicked(e -> {
            CommandProcessor.getStage().setScene(Scenes.getAuctionsScene());
            CommandProcessor.getStage().setMaximized(false);
            CommandProcessor.getStage().setMaximized(true);
        });

        VBox vBox = new VBox(25, signIn, products, auctions);
        vBox.setAlignment(Pos.CENTER);
        mainScene = new Scene(vBox, 600, 550);
        return mainScene;
    }

    public static Scene getProductsScene() {
        Button signInOrLogout;
        if (AllAccountZone.getCurrentAccount() == null) {
            signInOrLogout = createButton("Sign in", 100);
        } else {
            signInOrLogout = createButton("Logout", 100);
        }

        ChoiceBox<String> sort = new ChoiceBox<>();
        sort.getItems().addAll("Price(Ascending)", "Price(Descending)", "Score", "Date");

        Button filter = new Button("Filter ;)");

        //products

        VBox vBox = new VBox(25, signInOrLogout, sort, filter);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }

    public static Scene getAuctionsScene() {
        Button signInOrLogout;
        if (AllAccountZone.getCurrentAccount() == null) {
            signInOrLogout = createButton("Sign in", 100);
        } else {
            signInOrLogout = createButton("Logout", 100);
        }

        ChoiceBox<String> sort = new ChoiceBox<>();
        sort.getItems().addAll("Price(Ascending)", "Price(Descending)", "Score", "Date");

        Button filter = new Button("Filter ;)");

        //products

        VBox vBox = new VBox(25, signInOrLogout, sort, filter);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }

    public static Scene getAdminScene() {
        Button personalInfo = createButton("View Personal Info", 200);
        Button users = createButton("Manage Users", 200);
        Button products = createButton("Manage Products", 200);
        Button discountCodes = createButton("Manage Discount Codes", 200);
        Button requests = createButton("Manage Requests", 200);
        Button categories = createButton("Manage Categories", 200);

        VBox vBox = new VBox(25, personalInfo, users, products, discountCodes, requests, categories);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }

    public static Scene getSellerScene() {
        Button personalInfo = createButton("View Personal Info", 200);
        Button salesHistory = createButton("View Sales History", 200);
        Button products = createButton("Manage Products", 200);
        Button categories = createButton("show Categories", 200);
        Button auctions = createButton("View Auctions", 200);
        Button balance = createButton("View Balance", 200);

        VBox vBox = new VBox(25, personalInfo, salesHistory, products, categories, auctions, balance);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }

    public static Scene getBuyerScene() {
        Button personalInfo = createButton("View Personal Info", 200);
        Button cart = createButton("View Cart", 200);
        Button orders = createButton("View Orders", 200);
        Button balance = createButton("View Balance", 200);
        Button discountCodes = createButton("View Discount Codes", 200);

        VBox vBox = new VBox(25, personalInfo, cart, orders, balance, discountCodes);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }

    public static Scene getSignInScene() {
        ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll("Admin", "Seller", "Buyer");
        type.setMinWidth(300);
        type.setPromptText("Account Type");

        TextField username = createTextField("Username", 300);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(300);
        password.setAlignment(Pos.CENTER);

        Button signIn = new Button("Sign in");
        signIn.setAlignment(Pos.CENTER);
        signIn.setDisable(true);
        signIn.setOnMouseClicked(e -> {
            ArrayList<String> info = new ArrayList<>(Arrays.asList(type.getValue().toLowerCase(), username.getText(),
                    password.getText()));
            Actions.signIn(info);
        });

        Hyperlink createAccount = new Hyperlink("Create Account");
        createAccount.setOnMouseClicked(e -> {
            CommandProcessor.getStage().setScene(Scenes.getCreateAccountScene());
            CommandProcessor.getStage().setMaximized(false);
            CommandProcessor.getStage().setMaximized(true);
        });

        type.setOnAction(e -> signIn.setDisable(false));

        VBox vBox = new VBox(25, type, username, password, signIn, createAccount);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }

    public static Scene getCreateAccountScene() {
        ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll("Seller", "Buyer");
        type.setMinWidth(300);
        type.setPromptText("Account Type");

        TextField firstName = createTextField("First Name", 300);
        TextField lastName = createTextField("Last Name", 300);
        TextField email = createTextField("Email", 300);
        TextField phoneNumber = createTextField("Phone Number", 300);
        TextField username = createTextField("Username", 300);
        TextField balance = createTextField("Balance", 300);
        TextField company = createTextField("Company", 300);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(300);
        password.setAlignment(Pos.CENTER);

        Button register = new Button("Create Account");
        register.setAlignment(Pos.CENTER);
        register.setDisable(true);
        register.setOnMouseClicked(e -> {
                ArrayList<String> info = new ArrayList<>(Arrays.asList(type.getValue().toLowerCase(), firstName.getText(),
                        lastName.getText(), email.getText(), phoneNumber.getText(), username.getText(), password.getText(),
                        balance.getText(), company.getText()));
                Actions.register(info);
        });

        VBox vBox = new VBox(25, type, firstName, lastName, email, phoneNumber, username, password, balance, register);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<TextField> addedTextFields = new ArrayList<>();
        type.setOnAction(e -> {
            register.setDisable(false);
            if (type.getValue().equals("Buyer")) {
                vBox.getChildren().removeAll(addedTextFields);
            } else if (type.getValue().equals("Seller")) {
                vBox.getChildren().removeAll(addedTextFields);
                vBox.getChildren().remove(register);
                vBox.getChildren().addAll(company, register);
                addedTextFields.add(company);
            }
        });

        return new Scene(vBox, 600, 550);
    }
}
