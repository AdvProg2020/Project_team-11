package view;

import controller.AllAccountZone;
import controller.BuyerZone;
import controller.SellerZone;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static view.MainScenes.createButton;
import static view.MainScenes.createTextField;
import static view.MainScenes.createLabel;
import static view.ProductScene.getProductRoot;

public class BuyerScene {

    public static Parent getBuyerRoot() {
        Button personalInfo = createButton("View Personal Info", 300);
        personalInfo.setMinHeight(50);
        personalInfo.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(getPersonalInfo()));
        personalInfo.getStyleClass().add("account-button");

        Button cart = createButton("View Cart", 300);
        cart.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(viewCart()));
        cart.setMinHeight(50);
        cart.getStyleClass().add("account-button");

        Button orders = createButton("View Orders", 300);
        orders.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(viewOrders()));
        orders.setMinHeight(50);
        orders.getStyleClass().add("account-button");

        Button discountCodes = createButton("View Discount Codes", 300);
        discountCodes.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(viewDiscounts()));
        discountCodes.setMinHeight(50);
        discountCodes.getStyleClass().add("account-button");

        VBox vBox = new VBox(personalInfo, cart, orders, discountCodes);
        vBox.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
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

    private static Parent viewCart() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        Label priceLabel = createLabel("Total Price", 50);
        TextField priceText = createTextField("Price", 50);
        priceText.setText(String.valueOf(BuyerZone.calculatePriceWithAuctions()));
        priceText.setDisable(true);
        Button purchase = createButton("Purchase", 50);
        purchase.setOnMouseClicked(e -> {
            Label label = createLabel("Enter receiver information and discount code.", 500);
            TextField address = createTextField("Address", 500);
            TextField phoneNumber = createTextField("Phone Number", 500);
            TextField discount = createTextField("Discount Code", 500);
            Button applyDiscount = createButton("Apply Discount", 500);
            applyDiscount.setOnMouseClicked(event -> {
                if (Actions.checkReceiverInfo(address.getText(), phoneNumber.getText())) {
                    String result = BuyerZone.checkDiscountCode(discount.getText());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (result.equals("Discount applied.")) {
                        alert.setContentText("Discount applied.");
                        alert.show();
                        paymentRoot();
                    } else {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText(result);
                        alert.show();
                    }
                }
            });
            Button next = createButton("Next", 500);
            next.setOnMouseClicked(event -> {
                if (Actions.checkReceiverInfo(address.getText(), phoneNumber.getText())) {
                    paymentRoot();
                }
            });
            Button back = createButton("Back", 500);
            back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(viewCart()));

            VBox infoVBox = new VBox(20);
            infoVBox.setAlignment(Pos.CENTER);
            infoVBox.getChildren().addAll(label, address, phoneNumber, discount, applyDiscount, next, back);
            MainScenes.getBorderPane().setCenter(infoVBox);
        });

        HashMap<String, Integer> products = new HashMap<>(BuyerZone.getProductsInCart());
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            Hyperlink hyperlink = new Hyperlink(entry.getKey());
            hyperlink.setOnMouseClicked(e ->
                    MainScenes.getBorderPane().setCenter(getProductRoot(Integer.parseInt(hyperlink.getText()))));
            ImageView decrease = null;
            try {
                decrease = new ImageView(new Image(new FileInputStream("Styles/Photos/minus.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            decrease.setFitHeight(25);
            decrease.setFitWidth(25);
            TextField textField = createTextField("Number", 50);
            textField.setDisable(true);
            textField.setText(String.valueOf(entry.getValue()));
            ImageView increase = null;
            try {
                increase = new ImageView(new Image(new FileInputStream("Styles/Photos/plus.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            increase.setFitHeight(25);
            increase.setFitWidth(25);
            HBox hBox = new HBox(20);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(hyperlink, decrease, textField, increase);
            vBox.getChildren().add(hBox);

            decrease.setOnMouseClicked(e -> {
                BuyerZone.changeNumberOFProductInCart(Integer.parseInt(hyperlink.getText()), -1);
                textField.setText(String.valueOf(Integer.parseInt(textField.getText()) - 1));
                priceText.setText(String.valueOf(BuyerZone.calculatePriceWithAuctions()));
                if (textField.getText().equals("0")) {
                    BuyerZone.removeProductFromCart();
                    vBox.getChildren().remove(hBox);
                }
            });
            increase.setOnMouseClicked(e -> {
                BuyerZone.changeNumberOFProductInCart(Integer.parseInt(hyperlink.getText()), 1);
                textField.setText(String.valueOf(Integer.parseInt(textField.getText()) + 1));
                priceText.setText(String.valueOf(BuyerZone.calculatePriceWithAuctions()));
            });
        }

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(priceLabel, priceText, purchase);
        if (!priceText.getText().equals("0"))
            vBox.getChildren().add(hBox);

        return vBox;
    }

    private static void paymentRoot() {
        long priceWithDiscount = BuyerZone.calculatePriceWithDiscountsAndAuctions();
        long totalPrice = BuyerZone.calculatePriceWithAuctions();
        Label price = createLabel("Total Price : " + priceWithDiscount + "$", 200);
        Label discountAmount =
                createLabel("Discount : " + (totalPrice - priceWithDiscount) + "$", 200);
        Button payment = createButton("Pay", 200);
        payment.setOnMouseClicked(ev -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (BuyerZone.canPayMoney()) {
                BuyerZone.payMoney();
                alert.setContentText("Purchase Completed.\nThank you for buying.");
                alert.show();
                MainScenes.getBorderPane().setCenter(viewOrders());
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("You don't have enough money.");
                alert.show();
            }
        });

        VBox payInfo = new VBox(20);
        payInfo.setAlignment(Pos.CENTER);
        payInfo.getChildren().addAll(price, discountAmount, payment);
        MainScenes.getBorderPane().setCenter(payInfo);
    }

    private static Parent viewOrders() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        for (String order : BuyerZone.getOrdersInfo()) {
            Label label = createLabel(order, 600);
            vBox.getChildren().add(label);
        }

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent viewDiscounts() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<String> discounts = new ArrayList<>(BuyerZone.getBuyerDiscounts());
        for (String discount : discounts) {
            Label label = createLabel(discount, 500);
            vBox.getChildren().add(label);
        }

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }
}
