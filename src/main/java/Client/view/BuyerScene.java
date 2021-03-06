package Client.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.FileProcess;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static Client.view.Actions.showError;
import static Client.view.Actions.showInfoBox;
import static Client.view.MainScenes.createButton;
import static Client.view.MainScenes.createTextField;
import static Client.view.MainScenes.createLabel;
import static Client.view.ProductScene.getProductRoot;
import static Client.view.ServerConnection.*;

public class BuyerScene {
    private static Gson gson = new Gson();

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

        Button support = createButton("Support", 300);
        support.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(getSupportScene()));
        support.setMinHeight(50);
        support.getStyleClass().add("account-button");

        VBox vBox = new VBox(personalInfo, cart, orders, discountCodes, support);
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
        Label walletLabel = createLabel("Wallet : ", 150);

        ArrayList<String> personalInfo = new ArrayList<>();
        try {
            String data = ServerConnection.getPersonalInfo();
            Type foundListType = new TypeToken<ArrayList<String>>() {}.getType();
            personalInfo = gson.fromJson(data, foundListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        Button charge = createButton("Charge", 100);
        charge.setOnMouseClicked(e -> MainScenes.getBorderPane()
                .setCenter(chargeWallet(Long.parseLong(walletText.getText()))));

        GridPane gridPane = new GridPane();
        gridPane.addColumn(0, firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, usernameLabel,
                passwordLabel, walletLabel);
        gridPane.addColumn(1, firstNameText, lastNameText, emailText, phoneNumberText, usernameText, passwordText,
                walletText);
        gridPane.addColumn(2, firstNameButton, lastNameButton, emailButton, phoneNumberButton);
        gridPane.add(passwordButton, 2, 5);
        gridPane.add(charge, 2, 6);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent chargeWallet(long wallet) {
        Label info = createLabel("Enter the amount of money you want to charge the wallet.", 300);
        TextField amount = createTextField("Amount", 200);
        Button next = createButton("next", 200);
        Button back = createButton("Back", 200);

        back.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(getPersonalInfo()));

        next.setOnMouseClicked(e -> {
            if (Validation.validateLong(amount.getText())) {
                int receiptId = 0;
                try {
                    receiptId = Integer.parseInt(createChargeReceipt(amount.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Label currentWallet = createLabel("Your current money in wallet : " + wallet + "$", 300);
                Label chargeInfo = createLabel("Money to add your wallet : " + amount.getText() + "$", 300);
                Label warning = createLabel("WARNING : You can't withdraw the money after charge.", 300);
                Button charge = createButton("Charge", 150);
                Button backToAmount = createButton("Back", 150);

                backToAmount.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(chargeWallet(wallet)));

                int finalReceiptId = receiptId;
                charge.setOnMouseClicked(event -> {
                    try {
                        if (payReceipt(finalReceiptId).startsWith("done")) {
                            increaseMoney(amount.getText());
                            showInfoBox("charge successfully.");
                            MainScenes.getBorderPane().setCenter(getPersonalInfo());
                        } else {
                            showError("You don't have enough money.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                VBox vBox = new VBox(20);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(currentWallet, chargeInfo, warning, charge, backToAmount);

                ScrollPane scrollPane = new ScrollPane(vBox);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                MainScenes.getBorderPane().setCenter(scrollPane);
            } else {
                showError("amount format is not valid.");
            }
        });

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(info, amount, next, back);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent viewCart() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        Label priceLabel = createLabel("Total Price", 50);
        TextField priceText = createTextField("Price", 100);
        try {
            priceText.setText(getPriceWithAuction());
        } catch (IOException e) {
            e.printStackTrace();
        }
        priceText.setDisable(true);
        Button purchase = createButton("Purchase", 50);
        purchase.setOnMouseClicked(e -> {
            Label label = createLabel("Enter receiver information and discount code.", 500);
            TextField address = createTextField("Address", 500);
            TextField phoneNumber = createTextField("Phone Number", 500);
            TextField discount = createTextField("Discount Code", 500);
            Button applyDiscount = createButton("Apply Discount", 500);
            applyDiscount.setOnMouseClicked(event -> {
                if (Actions.checkReceiverInfo(address.getText(), phoneNumber.getText()) && discount.getText() != null) {
                    String result = "";
                    try {
                        result = checkDiscount(discount.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (result.equals("Discount applied.")) {
                        showInfoBox("Discount applied.");
                        paymentRoot(address.getText(), phoneNumber.getText());
                    } else {
                        showError(result);
                    }
                }
            });
            Button next = createButton("Next", 500);
            next.setOnMouseClicked(event -> {
                if (Actions.checkReceiverInfo(address.getText(), phoneNumber.getText())) {
                    paymentRoot(address.getText(), phoneNumber.getText());
                }
            });
            Button back = createButton("Back", 500);
            back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(viewCart()));

            VBox infoVBox = new VBox(20);
            infoVBox.setAlignment(Pos.CENTER);
            infoVBox.getChildren().addAll(label, address, phoneNumber, discount, applyDiscount, next, back);
            MainScenes.getBorderPane().setCenter(infoVBox);
        });

        HashMap<String, Integer> products = new HashMap<>();
        try {
            String data = getProductInCart();
            Type foundType = new TypeToken<HashMap<String, Integer>>() {}.getType();
            products = gson.fromJson(data, foundType);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            increase.setFitWidth(40);
            String productName = "", auctionPrice = "";
            try {
                productName = getProductName(Integer.parseInt(entry.getKey()));
                auctionPrice = getAuctionPrice(Integer.parseInt(entry.getKey()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Label name = createLabel(productName, 50);
            Label price = createLabel(auctionPrice + "$", 50);
            ImageView productImage = null;
            try {
                productImage = new ImageView(new Image(new FileInputStream("Styles/Photos/p" +
                        entry.getKey() + ".png")));
            } catch (FileNotFoundException e) {
                try {
                    productImage = new ImageView(new Image(new FileInputStream("Styles/Photos/product.png")));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            productImage.setFitWidth(200);
            productImage.setFitHeight(200);
            HBox hBox = new HBox(20);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(productImage, hyperlink, name, price, decrease, textField, increase);
            vBox.getChildren().add(hBox);

            decrease.setOnMouseClicked(e -> {
                try {
                    changeNumber(Integer.parseInt(hyperlink.getText()), -1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                textField.setText(String.valueOf(Integer.parseInt(textField.getText()) - 1));
                try {
                    priceText.setText(getPriceWithAuction());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (textField.getText().equals("0")) {
                    try {
                        removeProductInCart();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    vBox.getChildren().remove(hBox);
                }
            });
            increase.setOnMouseClicked(e -> {
                try {
                    if (changeNumber(Integer.parseInt(hyperlink.getText()), 1)) {
                        textField.setText(String.valueOf(Integer.parseInt(textField.getText()) + 1));
                        String totalPrice = getPriceWithAuction();
                        priceText.setText(totalPrice);
                    } else {
                        showError("There isn't any more of this product.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(priceLabel, priceText, purchase);
        if (!priceText.getText().equals("0"))
            vBox.getChildren().add(hBox);

        return vBox;
    }

    private static void paymentRoot(String address, String phoneNumber) {
        long priceWithDiscount = 0, totalPrice = 0;
        boolean hasFile = false;
        try {
            totalPrice = Long.parseLong(getPriceWithAuction());
            priceWithDiscount = getBuyerPrice();
            hasFile = hasFileInCart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Label filePath = createLabel("Enter Path to save the files", 200);
        TextField path = createTextField("Path", 200);
        HBox hBox = new HBox(20, filePath, path);
        hBox.setAlignment(Pos.CENTER);
        Label price = createLabel("Total Price : " + priceWithDiscount + "$", 200);
        Label discountAmount =
                createLabel("Discount : " + (totalPrice - priceWithDiscount) + "$", 200);
        Button payWallet = createButton("Pay from wallet", 250);
        Button payBank = createButton("Pay from bank account", 250);

        VBox payInfo = new VBox(20);
        payInfo.setAlignment(Pos.CENTER);
        if (hasFile) {
            payInfo.getChildren().add(hBox);
        }
        payInfo.getChildren().addAll(price, discountAmount, payWallet, payBank);
        MainScenes.getBorderPane().setCenter(payInfo);

        boolean finalHasFile = hasFile;
        payWallet.setOnMouseClicked(ev -> {
            if (!finalHasFile || path.getText() != null) {
                try {
                    if (canPay()) {
                        if (finalHasFile) {
                            String data = getFilesIdInCart();
                            Type foundListType = new TypeToken<ArrayList<Integer>>(){}.getType();
                            ArrayList<Integer> fileIds = gson.fromJson(data, foundListType);
                            for (Integer fileId : fileIds) {
                                Formatter formatter = FileProcess.openFileToWrite(path.getText() + "\\" + fileId + ".txt");
                                Scanner scanner = new Scanner(new File("resources\\files\\p" + fileId + ".txt"));
                                while (scanner.hasNextLine()) {
                                    formatter.format(scanner.nextLine() + "\n");
                                }
                                formatter.close();
                                scanner.close();
                            }
                        }
                        pay("wallet", address, phoneNumber);
                        showInfoBox("Purchase Completed.\nThank you for buying.");
                        MainScenes.getBorderPane().setCenter(viewOrders());
                    } else {
                        try {
                            showError("You don't have enough money.\n" +
                                    "There should be at least " + getMinMoney() + "$ left in your wallet.");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showError("Enter the path.");
            }
        });

        payBank.setOnMouseClicked(ev -> {
            if (!finalHasFile || path.getText() != null) {
                try {
                    if (finalHasFile) {
                        String data = getFilesIdInCart();
                        Type foundListType = new TypeToken<ArrayList<Integer>>(){}.getType();
                        ArrayList<Integer> fileIds = gson.fromJson(data, foundListType);
                        for (Integer fileId : fileIds) {
                            Formatter formatter = FileProcess.openFileToWrite(path.getText() + "\\" + fileId + ".txt");
                            Scanner scanner = new Scanner(new File("resources\\files\\p" + fileId + ".txt"));
                            while (scanner.hasNextLine()) {
                                formatter.format(scanner.nextLine() + "\n");
                            }
                            formatter.close();
                            scanner.close();
                        }
                    }
                    pay("bank", address, phoneNumber);
                    showInfoBox("Purchase Completed.\nThank you for buying.");
                    MainScenes.getBorderPane().setCenter(viewOrders());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showError("Enter the path.");
            }
        });
    }

    private static Parent viewOrders() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<String> orders = new ArrayList<>();
        try {
            String data = getOrders();
            Type foundListType = new TypeToken<ArrayList<String>>() {}.getType();
            orders = gson.fromJson(data, foundListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String order : orders) {
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

        ArrayList<String> discounts = new ArrayList<>();
        try {
            String data = getBuyerDiscounts();
            Type foundListType = new TypeToken<ArrayList<String>>() {}.getType();
            discounts = gson.fromJson(data, foundListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String discount : discounts) {
            Label label = createLabel(discount, 500);
            vBox.getChildren().add(label);
        }

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    public static Parent getSupportScene() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        Label info = createLabel("Click one of the online supports to start chat.", 500);
        vBox.getChildren().add(info);

        try {
            String data = getOnlineSupports();
            Type foundListType = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> onlineSupports = gson.fromJson(data, foundListType);

            for (String support : onlineSupports) {
                Hyperlink hyperlink = new Hyperlink(support);

                hyperlink.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(getChatRoot(support)));

                vBox.getChildren().add(hyperlink);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent getChatRoot(String supportUsername) {
        VBox messageVBox = new VBox(10);
        messageVBox.setAlignment(Pos.TOP_CENTER);

        try {
            String data1 = getLastSupportMessages(supportUsername);
            Type foundListType1 = new TypeToken<ArrayList<HashMap<String, String>>>() {}.getType();
            ArrayList<HashMap<String, String>> messages = gson.fromJson(data1, foundListType1);

            if (messages != null) {
                for (HashMap<String, String> message : messages) {
                    for (Map.Entry<String, String> entry : message.entrySet()) {
                        Label messageLabel = createLabel(entry.getValue(), 500);
                        if (entry.getKey().equals(supportUsername)) {
                            messageLabel.setAlignment(Pos.CENTER_LEFT);
                        } else {
                            messageLabel.setAlignment(Pos.CENTER_RIGHT);
                        }
                        messageVBox.getChildren().add(messageLabel);
                    }
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
                    sendMessageSupport(supportUsername, "me", textField.getText());
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
