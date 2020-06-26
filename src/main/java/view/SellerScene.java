package view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.AdminZone;
import controller.AllAccountZone;
import controller.SellerZone;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Comment;

import java.lang.reflect.Type;
import java.util.*;

import static view.MainScenes.*;
import static view.MainScenes.createTextField;

public class SellerScene {

    public static Parent getSellerRoot() {
        Button personalInfo = createButton("View Personal Info", 300);
        personalInfo.setMinHeight(50);
        personalInfo.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(getPersonalInfo()));
        personalInfo.getStyleClass().add("account-button");

        Button salesHistory = createButton("View Sales History", 300);
        salesHistory.setMinHeight(50);
        salesHistory.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(viewSalesHistory()));
        salesHistory.getStyleClass().add("account-button");

        Button products = createButton("Manage Products", 300);
        products.setMinHeight(50);
        products.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(manageProducts()));
        products.getStyleClass().add("account-button");

        Button categories = createButton("show Categories", 300);
        categories.setMinHeight(50);
        categories.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(showCategories()));
        categories.getStyleClass().add("account-button");

        Button auctions = createButton("View Auctions", 300);
        auctions.setMinHeight(50);
        auctions.setOnMouseClicked(e -> MainScenes.getBorderPane().setCenter(manageAuctions()));
        auctions.getStyleClass().add("account-button");

        VBox vBox = new VBox(personalInfo, salesHistory, products, categories, auctions);
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
        Label companyLabel = createLabel("Company : ", 150);
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

        TextField companyText = createTextField("Company", 200);
        companyText.setText(personalInfo.get(6));
        companyText.setDisable(true);

        TextField walletText = createTextField("Wallet", 200);
        walletText.setText(personalInfo.get(7));
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
        Button companyButton = createButton("Edit", 100);
        companyButton.setOnMouseClicked(e -> Actions.editPersonalInfo(companyButton, companyText));
        Button walletButton = createButton("Edit", 100);
        walletButton.setOnMouseClicked(e -> Actions.editPersonalInfo(walletButton, walletText));

        GridPane gridPane = new GridPane();
        gridPane.addColumn(0, firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, usernameLabel,
                passwordLabel, companyLabel, walletLabel);
        gridPane.addColumn(1, firstNameText, lastNameText, emailText, phoneNumberText, usernameText, passwordText,
                companyText, walletText);
        gridPane.addColumn(2, firstNameButton, lastNameButton, emailButton, phoneNumberButton);
        gridPane.add(passwordButton, 2, 5);
        gridPane.add(companyButton, 2, 6);
        gridPane.add(walletButton, 2, 7);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent viewSalesHistory() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<String> salesHistory = new ArrayList<>(SellerZone.getSellerHistory());
        for (String sale : salesHistory) {
            Label label = createLabel(sale, 500);
            vBox.getChildren().add(label);
        }

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent manageProducts() {
        VBox idVBox = new VBox(20);
        idVBox.setAlignment(Pos.CENTER);

        VBox nameVBox = new VBox(20);
        nameVBox.setAlignment(Pos.CENTER);

        VBox removeVBox = new VBox(20);
        removeVBox.setAlignment(Pos.CENTER);

        HashMap<Integer, String> products = new HashMap<>(SellerZone.getSellerProducts());
        for (Map.Entry<Integer, String> entry : products.entrySet()) {
            Hyperlink id = new Hyperlink(String.valueOf(entry.getKey()));
            Label name = createLabel(entry.getValue(), 150);
            Button remove = createButton("Remove", 100);

            idVBox.getChildren().add(id);
            nameVBox.getChildren().add(name);
            removeVBox.getChildren().add(remove);

            remove.setOnMouseClicked(e -> {
                idVBox.getChildren().remove(id);
                nameVBox.getChildren().remove(name);
                removeVBox.getChildren().remove(remove);
                SellerZone.removeProduct(entry.getKey());
            });

            id.setOnMouseClicked(e -> {
                HashMap<String, String> productDetails =
                        new HashMap<>(SellerZone.getSellerProductDetails(Integer.parseInt(id.getText())));

                Label statusLabel = createLabel("Status : ", 100);
                TextField statusText = createTextField(productDetails.get("status"), 500);
                statusText.setDisable(true);

                Label nameLabel = createLabel("Name : ", 100);
                TextField nameText = createTextField("Name", 500);
                nameText.setText(productDetails.get("name"));
                nameText.setDisable(true);
                Button editName = createButton("Edit", 100);
                editName.setOnMouseClicked(event -> Actions.editProduct(nameText, editName, Integer.parseInt(id.getText())));

                Label companyLabel = createLabel("Company : ", 100);
                TextField companyText = createTextField("Company", 500);
                companyText.setText(productDetails.get("company"));
                companyText.setDisable(true);
                Button editCompany = createButton("Edit", 100);
                editCompany.setOnMouseClicked(event -> Actions.editProduct(companyText, editCompany, Integer.parseInt(id.getText())));

                Label priceLabel = createLabel("Price : ", 100);
                TextField priceText = createTextField("Price", 500);
                priceText.setText(productDetails.get("price"));
                priceText.setDisable(true);
                Button editPrice = createButton("Edit", 100);
                editPrice.setOnMouseClicked(event -> Actions.editProduct(priceText, editPrice, Integer.parseInt(id.getText())));

                Label auctionLabel = createLabel("Auction Price : ", 100);
                TextField auctionText = createTextField("Auction Price", 500);
                auctionText.setText(productDetails.get("auctionPrice"));
                auctionText.setDisable(true);

                Label stockLabel = createLabel("Stock : ", 100);
                TextField stockText = createTextField("Stock", 500);
                stockText.setText(productDetails.get("stock"));
                stockText.setDisable(true);
                Button editStock = createButton("Edit", 100);
                editStock.setOnMouseClicked(event -> Actions.editProduct(stockText, editStock, Integer.parseInt(id.getText())));

                Label categoryLabel = createLabel("Category : ", 100);
                TextField categoryText = createTextField("Category", 500);
                categoryText.setText(productDetails.get("category"));
                categoryText.setDisable(true);

                VBox featureLabels = new VBox(25);
                featureLabels.setAlignment(Pos.CENTER);

                VBox featureTexts = new VBox(20);
                featureTexts.setAlignment(Pos.CENTER);

                VBox featureEdits = new VBox(20);
                featureEdits.setAlignment(Pos.CENTER);

                Gson gson = new Gson();
                Type foundListType = new TypeToken<HashMap<String, String>>(){}.getType();
                HashMap<String, String> features = gson.fromJson(productDetails.get("feature"), foundListType);
                for (Map.Entry<String, String> featureEntry : features.entrySet()) {
                    Label featureLabel = createLabel(featureEntry.getKey() + " : ", 100);
                    TextField featureText = createTextField(featureEntry.getKey(), 500);
                    featureText.setText(featureEntry.getValue());
                    featureText.setDisable(true);
                    Button editFeature = createButton("Edit", 100);
                    editFeature.setOnMouseClicked(event -> Actions.editProduct(featureText, editFeature,
                            Integer.parseInt(id.getText())));

                    featureLabels.getChildren().add(featureLabel);
                    featureTexts.getChildren().add(featureText);
                    featureEdits.getChildren().add(editFeature);
                }

                Label descriptionLabel = createLabel("Description : ", 100);
                TextField descriptionText = createTextField("Description", 500);
                descriptionText.setText(productDetails.get("description"));
                descriptionText.setDisable(true);
                Button editDescription = createButton("Edit", 100);
                editDescription.setOnMouseClicked(event -> Actions.editProduct(descriptionText, editDescription,
                        Integer.parseInt(id.getText())));

                Label scoreLabel = createLabel("Score : ", 100);
                TextField scoreText = createTextField("Score", 500);
                scoreText.setText(productDetails.get("score"));
                scoreText.setDisable(true);

                Label rateNumberLabel = createLabel("Number of User Rated : ", 100);
                TextField rateNumberText = createTextField("Number Of User Rated", 500);
                rateNumberText.setText(productDetails.get("numOfUserRated"));
                rateNumberText.setDisable(true);

                VBox commentVBox = new VBox(20);
                commentVBox.setAlignment(Pos.CENTER);

                foundListType = new TypeToken<ArrayList<Comment>>(){}.getType();
                ArrayList<Comment> comments = gson.fromJson(productDetails.get("comments"), foundListType);
                Label commentLabel = createLabel("Comments : ", 100);
                for (Comment comment : comments) {
                    TextField commentText = createTextField("Comment", 500);
                    commentText.setText(comment.getBuyerName() + " : " + comment.getCommentText());
                    commentText.setDisable(true);
                    commentVBox.getChildren().add(commentText);
                }

                Button back = createButton("Back", 300);
                back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(manageProducts()));

                GridPane gridPane = new GridPane();
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setVgap(20);
                gridPane.setHgap(20);
                gridPane.addColumn(0, statusLabel, nameLabel, companyLabel, priceLabel, auctionLabel, stockLabel,
                        categoryLabel, featureLabels, descriptionLabel, scoreLabel, rateNumberLabel, commentLabel);
                gridPane.addColumn(1, statusText, nameText, companyText, priceText, auctionText, stockText,
                        categoryText, featureTexts, descriptionText, scoreText, rateNumberText, commentVBox, back);
                gridPane.add(editName, 2, 1);
                gridPane.add(editCompany, 2, 2);
                gridPane.add(editPrice, 2, 3);
                gridPane.add(editStock, 2, 5);
                gridPane.add(featureEdits, 2, 7);
                gridPane.add(editDescription, 2, 8);

                ScrollPane scrollPane = new ScrollPane(gridPane);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                MainScenes.getBorderPane().setCenter(scrollPane);
            });
        }

        Button add = createButton("Add Product", 150);
        add.setOnMouseClicked(e -> {
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(20);
            gridPane.setHgap(20);

            TextField name = createTextField("Name", 300);
            TextField company = createTextField("Company", 300);
            TextField price = createTextField("Price", 300);
            TextField stock = createTextField("Stock", 300);
            TextField description = createTextField("Description", 300);
            TextField category = createTextField("Category", 300);
            Button apply = createButton("Apply", 100);

            VBox features = new VBox(20);
            features.setAlignment(Pos.CENTER);

            gridPane.addColumn(0, name, company, price, stock, description, category, features);
            gridPane.add(apply, 1, 5);

            apply.setOnMouseClicked(event -> {
                if (AdminZone.isCategoryNameValid(category.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("There is no category with this name.");
                    alert.show();
                } else {
                    features.getChildren().clear();
                    ArrayList<String> categoryFeature =
                            new ArrayList<>(Objects.requireNonNull(SellerZone.getCategoryFeature(category.getText())));
                    for (String s : categoryFeature) {
                        TextField feature = createTextField(s, 300);
                        features.getChildren().add(feature);
                    }
                }
            });

            Button create = createButton("Create", 300);
            create.setOnMouseClicked(event -> {
                if (features.getChildren().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Enter Category.");
                    alert.show();
                } else {
                    HashMap<String, String> productFeature = new HashMap<>();
                    for (Node child : features.getChildren()) {
                        productFeature.put(((TextField) child).getPromptText(), ((TextField) child).getText());
                    }
                    if (Actions.createProduct(new ArrayList<>(Arrays.asList(name.getText(), company.getText(),
                            price.getText(), stock.getText(), description.getText(), category.getText())), productFeature)) {
                        MainScenes.getBorderPane().setCenter(manageProducts());
                    }
                }
            });
            gridPane.add(create, 0, 7);

            Button back = createButton("Back", 300);
            back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(manageProducts()));
            gridPane.add(back, 0, 8);

            ScrollPane scrollPane = new ScrollPane(gridPane);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            MainScenes.getBorderPane().setCenter(scrollPane);
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        gridPane.add(idVBox, 0, 0);
        gridPane.add(nameVBox, 1, 0);
        gridPane.add(removeVBox, 2, 0);
        gridPane.add(add, 1, 1);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent showCategories() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        for (String category : AllAccountZone.getCategories()) {
            Hyperlink hyperlink = new Hyperlink(category);

            hyperlink.setOnMouseClicked(e -> {
                VBox featureVBox = new VBox(20);
                featureVBox.setAlignment(Pos.CENTER);

                for (String feature : AdminZone.getCategoryFeature(hyperlink.getText())) {
                    Label label = createLabel(feature, 300);
                    featureVBox.getChildren().add(label);
                }

                Button back = createButton("Back", 300);
                back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(showCategories()));

                featureVBox.getChildren().add(back);

                ScrollPane scrollPane = new ScrollPane(featureVBox);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                MainScenes.getBorderPane().setCenter(scrollPane);
            });

            vBox.getChildren().add(hyperlink);
        }

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Parent manageAuctions() {
        //TODO : one product one auction
        VBox idVBox = new VBox(20);
        idVBox.setAlignment(Pos.CENTER);

        ArrayList<Integer> auctionsId = new ArrayList<>(SellerZone.getSellerAuctions());

        for (Integer id : auctionsId) {
            Hyperlink hyperlink = new Hyperlink(String.valueOf(id));

            hyperlink.setOnMouseClicked(e -> {

                Label statusLabel = createLabel("Status : ", 100);
                Label discountLabel = createLabel("Discount Percent : ", 100);
                Label startLabel = createLabel("Start Date : ", 100);
                Label endLabel = createLabel("End Date : ", 100);
                Label productLabel = createLabel("Products : ", 100);

                ArrayList<String> auctionDetail =
                        new ArrayList<>(SellerZone.getAuctionDetail(Integer.parseInt(hyperlink.getText())));

                TextField statusText = createTextField("Status", 400);
                statusText.setText(auctionDetail.get(0));
                statusText.setDisable(true);

                TextField discountText = createTextField("Discount", 400);
                discountText.setText(auctionDetail.get(1));
                discountText.setDisable(true);

                TextField startText = createTextField("Start [dd/mm/yyyy hh:mm:ss]", 400);
                startText.setText(auctionDetail.get(2));
                startText.setDisable(true);

                TextField endText = createTextField("End [dd/mm/yyyy hh:mm:ss]", 400);
                endText.setText(auctionDetail.get(3));
                endText.setDisable(true);

                VBox productTextFields = new VBox(20);
                productTextFields.setAlignment(Pos.CENTER);

                VBox productButton = new VBox(20);
                productButton.setAlignment(Pos.CENTER);

                for (int i = 4; i < auctionDetail.size(); i++) {
                    TextField productText = createTextField("Product", 400);
                    productText.setText(auctionDetail.get(i));
                    productText.setDisable(true);
                    productTextFields.getChildren().add(productText);

                    Button removeProduct = createButton("Remove", 100);
                    removeProduct.setOnMouseClicked(event -> {
                        SellerZone.removeProductFromAuction(Integer.parseInt(hyperlink.getText()),
                                Integer.parseInt(productText.getText().substring(0, productText.getText().indexOf("."))));
                        productTextFields.getChildren().remove(productText);
                        productButton.getChildren().remove(removeProduct);
                    });
                    productButton.getChildren().add(removeProduct);
                }

                Button editDiscount = createButton("Edit", 100);
                editDiscount.setOnMouseClicked(event -> Actions.editAuction(discountText, editDiscount,
                        Integer.parseInt(hyperlink.getText())));
                Button editStart = createButton("Edit", 100);
                editStart.setOnMouseClicked(event -> Actions.editAuction(startText, editStart,
                        Integer.parseInt(hyperlink.getText())));
                Button editEnd = createButton("Edit", 100);
                editEnd.setOnMouseClicked(event -> Actions.editAuction(endText, editEnd,
                        Integer.parseInt(hyperlink.getText())));

                TextField addText = createTextField("Product ID", 400);
                Button addProduct = createButton("Add Product", 100);
                addProduct.setOnMouseClicked(event -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    if (Validation.validateInteger(addText.getText())) {
                        if (SellerZone.getSellerAuctionById(Integer.parseInt(hyperlink.getText())).getProductList()
                                .contains(SellerZone.getProductById(Integer.parseInt(addText.getText())))) {
                            alert.setContentText("Already exist.");
                            alert.show();
                        } else if (SellerZone.hasProduct(Integer.parseInt(addText.getText()))) {
                            TextField productText = createTextField("Product", 400);
                            productText.setText(addText.getText() + ". " +
                                    SellerZone.getProductById(Integer.parseInt(addText.getText())).getGeneralFeature().getName());
                            productText.setDisable(true);
                            productTextFields.getChildren().add(productText);

                            Button removeProduct = createButton("Remove", 100);
                            removeProduct.setOnMouseClicked(ev -> {
                                productTextFields.getChildren().remove(productText);
                                productButton.getChildren().remove(removeProduct);
                                SellerZone.removeProductFromAuction(Integer.parseInt(hyperlink.getText()),
                                        Integer.parseInt(productText.getText().substring(0, productText.getText().indexOf("."))));
                            });
                            productButton.getChildren().add(removeProduct);

                            SellerZone.addProductToAuction(Integer.parseInt(hyperlink.getText()),
                                    Integer.parseInt(addText.getText()));
                            addText.clear();
                        } else {
                            alert.setContentText("You haven't this product.");
                            alert.show();
                        }
                    } else {
                        alert.setContentText("invalid product ID.");
                        alert.show();
                    }
                });

                Button back = createButton("Back", 300);
                back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(manageAuctions()));

                GridPane gridPane = new GridPane();
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setVgap(20);
                gridPane.setHgap(20);
                gridPane.addColumn(0, statusLabel, discountLabel, startLabel, endLabel, productLabel);
                gridPane.addColumn(1, statusText, discountText, startText, endText, productTextFields, addText, back);
                gridPane.add(editDiscount, 2, 1);
                gridPane.add(editStart, 2, 2);
                gridPane.add(editEnd, 2, 3);
                gridPane.add(productButton, 2, 4);
                gridPane.add(addProduct, 2, 5);

                ScrollPane scrollPane = new ScrollPane(gridPane);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                MainScenes.getBorderPane().setCenter(scrollPane);
            });

            idVBox.getChildren().add(hyperlink);
        }

        Button add = createButton("Add Auction", 150);
        add.setOnMouseClicked(e -> {
            TextField discount = createTextField("Discount", 400);
            TextField start = createTextField("Start Date [dd/mm/yyyy hh:mm:ss]", 400);
            TextField end = createTextField("End Date [dd/mm/yyyy hh:mm:ss]", 400);
            TextField product = createTextField("Product ID", 400);

            VBox productVBox = new VBox(20);
            productVBox.setAlignment(Pos.CENTER);

            VBox removeVBox = new VBox(20);
            removeVBox.setAlignment(Pos.CENTER);

            ArrayList<String> productsId = new ArrayList<>();

            Button addProduct = createButton("Add", 100);
            addProduct.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (product.getText().isEmpty()) {
                    alert.setContentText("Enter product ID.");
                    alert.show();
                } else if (SellerZone.hasProduct(Integer.parseInt(product.getText()))) {
                    if (productsId.contains(product.getText())) {
                        alert.setContentText("Already exist.");
                        alert.show();
                    } else {
                        productsId.add(product.getText());
                        TextField productText = createTextField("Product", 400);
                        productText.setText(product.getText() + ". " +
                                SellerZone.getProductById(Integer.parseInt(product.getText())).getGeneralFeature().getName());
                        productText.setDisable(true);
                        productVBox.getChildren().add(productText);

                        Button removeProduct = createButton("Remove", 100);
                        removeVBox.getChildren().add(removeProduct);
                        removeProduct.setOnMouseClicked(ev -> {
                            productsId.remove(productText.getText().substring(0, productText.getText().indexOf(".")));
                            productVBox.getChildren().remove(productText);
                            removeVBox.getChildren().remove(removeProduct);
                        });

                        product.clear();
                    }
                } else {
                    alert.setContentText("You haven't this product.");
                    alert.show();
                }
            });

            Button create = createButton("Create", 100);
            create.setOnMouseClicked(event -> {
                ArrayList<String> info = new ArrayList<>(Arrays.asList(discount.getText(), start.getText(), end.getText()));
                info.addAll(productsId);
                if (Actions.createAuction(info)) {
                    MainScenes.getBorderPane().setCenter(manageAuctions());
                }
            });

            Button back = createButton("Back", 100);
            back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(manageAuctions()));

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(20);
            gridPane.setHgap(20);
            gridPane.addColumn(0, discount, start, end, productVBox, product, create, back);
            gridPane.add(addProduct, 1, 4);
            gridPane.add(removeVBox, 1, 3);

            ScrollPane scrollPane = new ScrollPane(gridPane);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            MainScenes.getBorderPane().setCenter(scrollPane);
        });

        idVBox.getChildren().add(add);

        ScrollPane scrollPane = new ScrollPane(idVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }
}
