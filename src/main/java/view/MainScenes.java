package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

public class MainScenes {
    private static Button signInOrOut;
    private static BorderPane borderPane;

    public static BorderPane getBorderPane() {
        return borderPane;
    }

    public static Button getSignInOrOut() {
        return signInOrOut;
    }

    public static TextField createTextField(String promptText, int maxWidth) {
        TextField textField = new PersistentPromptTextField(null, promptText);
        textField.setMaxWidth(maxWidth);
        textField.setMinWidth(maxWidth);
        textField.setAlignment(Pos.CENTER);
        return textField;
    }

    public static Button createButton(String text, int minWidth) {
        Button button = new Button(text);
        button.setAlignment(Pos.CENTER);
        button.setMinWidth(minWidth);
        return button;
    }

    public static Label createLabel(String text, int minWidth) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(minWidth);
        return label;
    }

    public static Parent getRegisterAdminScene() {
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
            if (Actions.register(info)) {
                CommandProcessor.getStage().getScene().setRoot(getMainMenuScene());
                CommandProcessor.getStage().setMaximized(true);
            }
        });

        VBox vBox = new VBox(25, firstName, lastName, email, phoneNumber, username, password, register);
        vBox.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    public static Parent getMainMenuScene() {
        Button signIn;
        signIn = createButton("Sign in", 200);
        signIn.setOnMouseClicked(e -> {
            borderPane.setCenter(getSignInRoot());
        });
        signIn.getStyleClass().add("top-buttons");
        signInOrOut = signIn;
        Button products = createButton("Products", 200);
        products.setOnAction(e -> {
            borderPane.setCenter(ProductScene.getProductsRoot());
            borderPane.setLeft(null);
        });
        products.getStyleClass().add("top-buttons");
        Button auctions = createButton("Auction", 200);
        auctions.setOnMouseClicked(e -> {
            borderPane.setCenter(AuctionScene.getAuctionsRoot());
            borderPane.setLeft(null);
        });
        auctions.getStyleClass().add("top-buttons");

        HBox hBox = new HBox(20, products, auctions, signIn);
        hBox.setPadding(new Insets(20, 20, 20, 20));
        hBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(ProductScene.getProductsRoot());
        MainScenes.borderPane = borderPane;
        borderPane.setStyle("-fx-background-color: #C5CBE3");

        return borderPane;
    }

    public static Parent getSignInRoot() {
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
            borderPane.setCenter(getCreateAccountRoot());
        });

        type.setOnAction(e -> signIn.setDisable(false));

        Button back = createButton("Back", 150);
        back.setOnMouseClicked(e -> {
            borderPane.setCenter(ProductScene.getProductsRoot());
        });

        VBox vBox = new VBox(25, type, username, password, signIn, createAccount, back);
        vBox.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    public static Parent getCreateAccountRoot() {
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
                if (Actions.register(info)) {
                    borderPane.setCenter(ProductScene.getProductsRoot());
                }
        });

        Button back = createButton("Back", 150);
        back.setOnMouseClicked(e -> {
            borderPane.setCenter(getSignInRoot());
        });

        VBox vBox = new VBox(25, type, firstName, lastName, email, phoneNumber, username, password, balance, register, back);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<TextField> addedTextFields = new ArrayList<>();
        type.setOnAction(e -> {
            register.setDisable(false);
            if (type.getValue().equals("Buyer")) {
                vBox.getChildren().removeAll(addedTextFields);
            } else if (type.getValue().equals("Seller")) {
                vBox.getChildren().removeAll(addedTextFields);
                vBox.getChildren().removeAll(register, back);
                vBox.getChildren().addAll(company, register, back);
                addedTextFields.add(company);
            }
        });

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }
}
