package view;

import controller.AdminZone;
import controller.AllAccountZone;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Request;
import view.tableViewData.Account;
import view.tableViewData.Product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import static view.MainScenes.*;

public class AdminScene {

    public static Parent getAdminRoot() {
        Button personalInfo = createButton("View Personal Info", 300);
        personalInfo.setMinHeight(50);
        personalInfo.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(getPersonalInfo()));
        personalInfo.getStyleClass().add("account-button");

        Button users = createButton("Manage Users", 300);
        users.setMinHeight(50);
        users.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(showUsers()));
        users.getStyleClass().add("account-button");

        Button products = createButton("Manage Products", 300);
        products.setMinHeight(50);
        products.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(manageProducts()));
        products.getStyleClass().add("account-button");

        Button discountCodes = createButton("Manage Discount Codes", 300);
        discountCodes.setMinHeight(50);
        discountCodes.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(manageDiscounts()));
        discountCodes.getStyleClass().add("account-button");

        Button requests = createButton("Manage Requests", 300);
        requests.setMinHeight(50);
        requests.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(manageRequests()));
        requests.getStyleClass().add("account-button");

        Button categories = createButton("Manage Categories", 300);
        categories.setMinHeight(50);
        categories.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(manageCategories()));
        categories.getStyleClass().add("account-button");

        VBox vBox = new VBox(personalInfo, users, products, discountCodes, requests, categories);
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

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent showUsers() {
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
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView, hBox);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    public static Parent manageDiscounts() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<String> discounts = new ArrayList<>(AdminZone.getDiscountCodes());
        for (String code : discounts) {
            Hyperlink hyperlink = new Hyperlink(code);

            hyperlink.setOnMouseClicked(e -> {
                GridPane gridPane = new GridPane();
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setHgap(20);
                gridPane.setVgap(20);

                VBox textFields = new VBox(20);
                textFields.setAlignment(Pos.CENTER);

                VBox removeButtons = new VBox(20);
                removeButtons.setAlignment(Pos.CENTER);

                Label codeLabel = createLabel("Code : ", 150);
                Label startLabel = createLabel("Start Date : ", 150);
                Label endLabel = createLabel("End Date : ", 150);
                Label percentLabel = createLabel("Discount Percent : ", 150);
                Label maxLabel = createLabel("Max Discount : ", 150);
                Label repeatLabel = createLabel("Repeated Times : ", 150);
                Label usersLabel = createLabel("Allowed Users : ", 150);

                ArrayList<String> info = new ArrayList<>(AdminZone.getDiscountInfo(hyperlink.getText()));

                TextField codeText = createTextField("Discount Code", 500);
                codeText.setText(info.get(0));
                codeText.setDisable(true);

                TextField startText = createTextField("Start Date [dd/mm/yyyy hh:mm:ss]", 500);
                startText.setText(info.get(1));
                startText.setDisable(true);

                TextField endText = createTextField("End Date [dd/mm/yyyy hh:mm:ss]", 500);
                endText.setText(info.get(2));
                endText.setDisable(true);

                TextField percentText = createTextField("Discount Percent", 500);
                percentText.setText(info.get(3));
                percentText.setDisable(true);

                TextField maxText = createTextField("Max Discount", 500);
                maxText.setText(info.get(4));
                maxText.setDisable(true);

                TextField repeatText = createTextField("Repeated Times", 500);
                repeatText.setText(info.get(5));
                repeatText.setDisable(true);

                for (int i = 6; i < info.size(); i++) {
                    TextField userText = createTextField("Username", 500);
                    userText.setText(info.get(i));
                    userText.setDisable(true);

                    Button userButton = createButton("Remove", 100);
                    userButton.setOnMouseClicked(event -> {
                        try {
                            AdminZone.editDiscount("remove user", userText.getText(), info.get(0));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        textFields.getChildren().remove(userText);
                        removeButtons.getChildren().remove(userButton);
                        info.remove(userText.getText());
                    });

                    textFields.getChildren().add(userText);
                    removeButtons.getChildren().add(userButton);
                }

                Button startButton = createButton("Edit", 100);
                startButton.setOnMouseClicked(event -> {
                    Actions.editDiscount(startButton, startText, hyperlink.getText());
                    startText.setText(AdminZone.getDiscountInfo(hyperlink.getText()).get(1));
                });
                Button endButton = createButton("Edit", 100);
                endButton.setOnMouseClicked(event -> {
                    Actions.editDiscount(endButton, endText, hyperlink.getText());
                    endText.setText(AdminZone.getDiscountInfo(hyperlink.getText()).get(2));
                });
                Button percentButton = createButton("Edit", 100);
                percentButton.setOnMouseClicked(event -> Actions.editDiscount(percentButton, percentText, hyperlink.getText()));
                Button maxButton = createButton("Edit", 100);
                maxButton.setOnMouseClicked(event -> Actions.editDiscount(maxButton, maxText, hyperlink.getText()));
                Button repeatButton = createButton("Edit", 100);
                repeatButton.setOnMouseClicked(event -> Actions.editDiscount(repeatButton, repeatText, hyperlink.getText()));

                Button back = createButton("Back", 300);
                back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(manageDiscounts()));

                TextField addUserText = createTextField("Username", 500);
                Button addUserButton = createButton("Add", 100);
                addUserButton.setOnMouseClicked(event -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    if (AdminZone.getBuyerByUsername(addUserText.getText()) == null) {
                        alert.setContentText("There is no user with this username.");
                        alert.show();
                    } else {
                        if (info.contains(addUserText.getText())) {
                            alert.setContentText("Already exist.");
                            alert.show();
                        } else {
                            try {
                                AdminZone.editDiscount("add user", addUserText.getText(), info.get(0));
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }
                            TextField userText = createTextField("Username", 500);
                            userText.setText(addUserText.getText());
                            userText.setDisable(true);

                            Button userButton = createButton("Remove", 100);
                            userButton.setOnMouseClicked(ev -> {
                                try {
                                    AdminZone.editDiscount("remove user", userText.getText(), info.get(0));
                                    gridPane.getChildren().removeAll(userText, userButton);
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                                info.add(userText.getText());
                            });

                            textFields.getChildren().add(userText);
                            removeButtons.getChildren().add(userButton);
                            addUserText.clear();
                            info.add(addUserText.getText());
                        }
                    }
                });

                gridPane.addColumn(0, codeLabel, startLabel, endLabel, percentLabel, maxLabel, repeatLabel, usersLabel);
                gridPane.add(codeText, 1, 0);
                gridPane.add(startText, 1, 1);
                gridPane.add(endText, 1, 2);
                gridPane.add(percentText, 1, 3);
                gridPane.add(maxText, 1, 4);
                gridPane.add(repeatText, 1, 5);
                gridPane.add(textFields, 1, 6);
                gridPane.add(startButton, 2, 1);
                gridPane.add(endButton, 2, 2);
                gridPane.add(percentButton, 2, 3);
                gridPane.add(maxButton, 2, 4);
                gridPane.add(repeatButton, 2, 5);
                gridPane.add(removeButtons, 2, 6);
                gridPane.add(addUserText, 1, 7);
                gridPane.add(addUserButton, 2, 7);
                gridPane.add(back, 1, 8);

                ScrollPane scrollPane = new ScrollPane(gridPane);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                MainScenes.getBorderPane().setCenter(scrollPane);
            });

            vBox.getChildren().add(hyperlink);
        }

        TextField code = createTextField("Code", 150);
        TextField removeCode = createTextField("Code", 150);
        TextField startDate = createTextField("Start [dd/mm/yyyy hh:mm:ss]", 200);
        TextField endDate = createTextField("End [dd/mm/yyyy hh:mm:ss]", 200);
        TextField percent = createTextField("Percent", 100);
        TextField max = createTextField("Max Discount", 100);
        TextField repeat = createTextField("Repeated Times", 100);
        TextField user = createTextField("username", 200);

        //delete discount
        Button delete = createButton("Delete", 100);
        delete.setOnMouseClicked(e -> {
            if (Actions.deleteDiscount(removeCode.getText())) {
                MainScenes.getBorderPane().setCenter(manageDiscounts());
            }
        });

        HBox discountCodeHBox = new HBox(20);
        discountCodeHBox.setPadding(new Insets(10, 10, 10, 10));
        discountCodeHBox.getChildren().addAll(removeCode, delete);
        discountCodeHBox.setAlignment(Pos.CENTER);

        Button deleteDiscount = createButton("Delete Discount", 200);
        deleteDiscount.setOnMouseClicked(e -> {
            if (!vBox.getChildren().contains(discountCodeHBox))
                vBox.getChildren().add(discountCodeHBox);
        });

        //create discount
        ArrayList<String> discountInfo = new ArrayList<>();
        ArrayList<String> allowedUsers = new ArrayList<>();

        Button create = createButton("Create", 100);
        Button add = createButton("Add", 200);
        Button finish = createButton("Finish", 200);

        HBox discountFieldHBox = new HBox(20);
        discountFieldHBox.setPadding(new Insets(10, 10, 10, 10));
        discountFieldHBox.getChildren().addAll(code, startDate, endDate, percent, max, repeat, create);
        discountFieldHBox.setAlignment(Pos.CENTER);

        HBox allowedUserHBox = new HBox(20);
        allowedUserHBox.setPadding(new Insets(10, 10, 10, 10));
        allowedUserHBox.getChildren().addAll(user, add, finish);
        allowedUserHBox.setAlignment(Pos.CENTER);

        create.setOnMouseClicked(e -> {
            ArrayList<String> info = new ArrayList<>(Arrays.asList(code.getText(), startDate.getText(),
                    endDate.getText(), percent.getText(), max.getText(), repeat.getText()));
            if (Actions.createDiscount(info)) {
                discountInfo.addAll(info);
                vBox.getChildren().remove(discountFieldHBox);
                vBox.getChildren().add(allowedUserHBox);
            }
        });

        add.setOnMouseClicked(e -> {
            if (Actions.addUserToDiscount(user.getText())) {
                allowedUsers.add(user.getText());
                user.clear();
            }
        });

        finish.setOnMouseClicked(e -> {
            vBox.getChildren().remove(allowedUserHBox);
            AdminZone.createDiscount(discountInfo, allowedUsers);
            MainScenes.getBorderPane().setCenter(manageDiscounts());
        });

        Button addDiscount = createButton("Create Discount", 200);
        addDiscount.setOnMouseClicked(e -> {
            if (!vBox.getChildren().contains(discountFieldHBox))
                vBox.getChildren().add(discountFieldHBox);
        });

        vBox.getChildren().addAll(addDiscount, deleteDiscount);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent manageProducts() {
        TableColumn<Product, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> sellerColumn = new TableColumn<>("Seller");
        sellerColumn.setMinWidth(100);
        sellerColumn.setCellValueFactory(new PropertyValueFactory<>("seller"));

        TableColumn<Product, String> stockStatusColumn = new TableColumn<>("Stock Status");
        stockStatusColumn.setMinWidth(100);
        stockStatusColumn.setCellValueFactory(new PropertyValueFactory<>("stockStatus"));

        TableColumn<Product, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setMinWidth(100);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        TableColumn<Product, String> featureColumn = new TableColumn<>("Feature");
        featureColumn.setMinWidth(100);
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("categoryFeature"));

        TableColumn<Product, String> scoreColumn = new TableColumn<>("Average Score");
        scoreColumn.setMinWidth(100);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));

        TableView<Product> tableView = new TableView<>();
        tableView.setItems(Product.getProducts());
        tableView.getColumns().addAll(idColumn, statusColumn, nameColumn, priceColumn, sellerColumn, stockStatusColumn,
                categoryColumn, featureColumn, scoreColumn);

        Button removeButton = createButton("Remove", 100);
        removeButton.setOnMouseClicked(e -> {
            ObservableList<Product> productSelected, allProducts;
            allProducts = tableView.getItems();
            productSelected = tableView.getSelectionModel().getSelectedItems();

            productSelected.forEach(allProducts::removeAll);
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(removeButton);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setAlignment(Pos.CENTER_RIGHT);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView, hBox);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent manageRequests() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        int i = 0;
        for (Request request : AdminZone.getAllRequests()) {
            Hyperlink idHyperlink = new Hyperlink(String.valueOf(request.getId()));
            idHyperlink.setOnMouseClicked(e -> {
                GridPane requestGridPane = new GridPane();
                requestGridPane.setAlignment(Pos.CENTER);
                requestGridPane.setHgap(20);
                requestGridPane.setVgap(20);

                Label topicLabel = createLabel("Topic", 100);
                Label senderLabel = createLabel("Sender", 100);
                Label statusLabel = createLabel("Status", 100);
                Label descriptionLabel = createLabel("Description", 100);

                Label topic = createLabel(request.getTopic(), 500);
                Label sender = createLabel(request.getSenderName(), 500);
                Label status = createLabel(request.getStatus(), 500);
                Label description = createLabel(request.getDescription(), 500);

                Button back = createButton("Back", 150);
                back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(manageRequests()));

                requestGridPane.addColumn(0, topicLabel, senderLabel, statusLabel, descriptionLabel);
                requestGridPane.addColumn(1, topic, sender, status, description, back);

                MainScenes.getBorderPane().setCenter(requestGridPane);
            });

            Label topic = createLabel(request.getTopic(), 150);
            Label sender = createLabel(request.getSenderName(), 150);

            Button acceptButton = createButton("Accept", 100);
            Button declineButton = createButton("Decline", 100);

            if (request.getStatus().equals("unseen")) {
                gridPane.add(acceptButton, 3, i);
                gridPane.add(declineButton, 4, i);
                acceptButton.setOnMouseClicked(e -> {
                    AdminZone.acceptRequest(request.getId());
                    gridPane.getChildren().removeAll(acceptButton, declineButton);
                });
                declineButton.setOnMouseClicked(e -> {
                    AdminZone.declineRequest(request.getId());
                    gridPane.getChildren().removeAll(acceptButton, declineButton);
                });
            }

            gridPane.add(idHyperlink, 0, i);
            gridPane.add(topic, 1, i);
            gridPane.add(sender, 2, i++);
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent manageCategories() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        VBox nameVBox = new VBox(20);
        nameVBox.setAlignment(Pos.CENTER);

        VBox removeVBox = new VBox(20);
        removeVBox.setAlignment(Pos.CENTER);

        ArrayList<String> categories = new ArrayList<>(AllAccountZone.getCategories());
        for (String category : categories) {
            Hyperlink hyperlink = new Hyperlink(category);

            hyperlink.setOnMouseClicked(e -> {
                GridPane categoryGridPane = new GridPane();
                categoryGridPane.setAlignment(Pos.CENTER);
                categoryGridPane.setHgap(20);
                categoryGridPane.setVgap(20);

                Label nameLabel = createLabel("Name : ", 100);
                Label featureLabel = createLabel("Feature : ", 100);

                ArrayList<String> feature = new ArrayList<>(AdminZone.getCategoryFeature(hyperlink.getText()));

                TextField nameText = createTextField("Name", 500);
                nameText.setText(hyperlink.getText());
                nameText.setDisable(true);

                Button editNameButton = createButton("Edit", 100);
                editNameButton.setOnMouseClicked(event -> Actions.editCategory(nameText, editNameButton, nameText.getText()));

                VBox textFields = new VBox(20);
                textFields.setAlignment(Pos.CENTER);

                VBox removeButtons = new VBox(20);
                removeButtons.setAlignment(Pos.CENTER);

                VBox editButtons = new VBox(20);
                editButtons.setAlignment(Pos.CENTER);

                for (int i = 0; i < feature.size(); i++) {
                    TextField featureText = createTextField("Feature", 500);
                    featureText.setText(feature.get(i));
                    featureText.setDisable(true);

                    Button editFeatureButton = createButton("Edit", 100);
                    editFeatureButton.setOnMouseClicked(event ->
                            Actions.editCategory(featureText, editFeatureButton, nameText.getText()));

                    Button remove = createButton("Remove", 100);
                    remove.setOnMouseClicked(event -> {
                        AdminZone.editCategory("remove feature", featureText.getText(), nameText.getText(), "");
                        textFields.getChildren().remove(featureText);
                        removeButtons.getChildren().remove(remove);
                        editButtons.getChildren().remove(editFeatureButton);
                        feature.remove(featureText.getText());
                    });

                    textFields.getChildren().add(featureText);
                    removeButtons.getChildren().add(remove);
                    editButtons.getChildren().add(editFeatureButton);
                }

                Button back = createButton("Back", 300);
                back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(manageCategories()));

                TextField addFeatureText = createTextField("Feature", 500);
                Button addFeatureButton = createButton("Add", 100);
                addFeatureButton.setOnMouseClicked(event -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    if (addFeatureText.getText().isEmpty()) {
                        alert.setContentText("Enter feature.");
                        alert.show();
                    } else {
                        if (feature.contains(addFeatureText.getText())) {
                            alert.setContentText("Already exist.");
                            alert.show();
                        } else {
                            AdminZone.editCategory("add feature", addFeatureText.getText(), nameText.getText(), "");
                            TextField featureText = createTextField("Feature", 500);
                            featureText.setText(addFeatureText.getText());
                            featureText.setDisable(true);

                            Button removeFeatureButton = createButton("Remove", 100);
                            removeFeatureButton.setOnMouseClicked(ev -> {
                                AdminZone.editCategory("remove feature", featureText.getText(), nameText.getText(), "");
                                textFields.getChildren().remove(featureText);
                                removeButtons.getChildren().remove(removeFeatureButton);
                                feature.remove(featureText.getText());
                            });

                            Button editFeatureButton = createButton("Edit", 100);
                            editFeatureButton.setOnMouseClicked(ev ->
                                    Actions.editCategory(featureText, editFeatureButton, nameText.getText()));

                            textFields.getChildren().add(featureText);
                            removeButtons.getChildren().add(removeFeatureButton);
                            editButtons.getChildren().add(editFeatureButton);
                            addFeatureText.clear();
                            feature.add(addFeatureText.getText());
                        }
                    }
                });

                categoryGridPane.addColumn(0, nameLabel, featureLabel);
                categoryGridPane.addColumn(1, nameText, textFields, addFeatureText, back);
                categoryGridPane.addColumn(2, editNameButton, removeButtons, addFeatureButton);
                categoryGridPane.add(editButtons, 3, 1);

                ScrollPane scrollPane = new ScrollPane(categoryGridPane);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                MainScenes.getBorderPane().setCenter(scrollPane);
            });

            Button remove = createButton("Remove", 100);
            remove.setOnMouseClicked(e -> {
                nameVBox.getChildren().remove(hyperlink);
                removeVBox.getChildren().remove(remove);
                AdminZone.removeCategory(hyperlink.getText());
            });

            nameVBox.getChildren().add(hyperlink);
            removeVBox.getChildren().add(remove);
        }

        Button add = createButton("Add Category", 100);
        add.setOnMouseClicked(e -> {
            final String[] name = new String[1];
            ArrayList<String> feature = new ArrayList<>();

            TextField nameText = createTextField("Name", 150);
            Button create = createButton("Create", 100);

            HBox nameHBox = new HBox(10);
            nameHBox.getChildren().addAll(nameText, create);
            nameHBox.setPadding(new Insets(10, 10, 10, 10));
            nameHBox.setAlignment(Pos.CENTER);

            gridPane.add(nameHBox, 1, 4);

            TextField featureText = createTextField("Feature", 200);
            Button addFeature = createButton("Add", 100);
            Button finish = createButton("Finish", 100);

            HBox featureHBox = new HBox(10);
            featureHBox.getChildren().addAll(featureText, addFeature, finish);
            featureHBox.setPadding(new Insets(10, 10, 10, 10));
            featureHBox.setAlignment(Pos.CENTER);

            create.setOnMouseClicked(event -> {
                if (Actions.checkCategoryName(nameText.getText())) {
                    gridPane.getChildren().remove(nameHBox);
                    name[0] = nameText.getText();
                    gridPane.add(featureHBox, 1, 4);
                }
            });

            addFeature.setOnMouseClicked(event -> {
                if (Validation.validateNames(featureText.getText())) {
                    feature.add(featureText.getText());
                    featureText.clear();
                }
            });

            finish.setOnMouseClicked(event -> {
                gridPane.getChildren().remove(featureHBox);
                AdminZone.createCategory(name[0], feature);
                MainScenes.getBorderPane().setCenter(manageCategories());
            });
        });

        gridPane.addColumn(0, nameVBox);
        gridPane.addColumn(1, removeVBox);
        gridPane.add(add, 1, removeVBox.getChildren().size());

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }
}
