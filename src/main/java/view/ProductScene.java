package view;

import controller.AdminZone;
import controller.AllAccountZone;
import controller.BuyerZone;
import controller.SellerZone;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Comment;
import model.Product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static view.MainScenes.*;

public class ProductScene {
    private static String sort = "date";
    private static FilterInfo filterInfo;

    public static FilterInfo getFilterInfo() {
        return filterInfo;
    }

    public static String getSort() {
        return sort;
    }

    public static Pane getProductsRoot() {
        ProductScene.filterInfo = new FilterInfo("", 0, Long.MAX_VALUE, "",
                0, new HashMap<>());

        GridPane productsGridPane = new GridPane();
        productsGridPane.setAlignment(Pos.CENTER);
        productsGridPane.setHgap(20);
        productsGridPane.setVgap(20);
        setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));

        ComboBox<String> sort = new ComboBox<>();
        sort.getItems().addAll("Price(Ascending)", "Price(Descending)", "Score", "Date");
        sort.setPromptText("Sort");
        sort.getSelectionModel().select(3);
        sort.setOnAction(e -> {
            ProductScene.sort = sort.getValue().toLowerCase();
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
        });

        TextField nameFilter = createTextField("Product Name / Company / Seller Name", 500);
        nameFilter.textProperty().addListener((list, oldText, newText) -> {
            filterInfo.setSearchBar(newText);
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
        });

        HBox hBox = new HBox(150, sort, nameFilter);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20, 20, 20, 20));

        TextField minPrice = createTextField("Minimum Price", 200);
        minPrice.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,18}")) {
                filterInfo.setMinimumPrice(Long.parseLong(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
            } else if (newText.isEmpty()) {
                filterInfo.setMinimumPrice(0);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
            }
        });
        TextField maxPrice = createTextField("Maximum Price", 200);
        maxPrice.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,18}")) {
                filterInfo.setMaximumPrice(Long.parseLong(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
            } else if (newText.isEmpty()) {
                filterInfo.setMaximumPrice(Long.MAX_VALUE);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
            }
        });
        TextField stock = createTextField("Minimum Stock", 200);
        stock.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,9}")) {
                filterInfo.setMinimumStockStatus(Integer.parseInt(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
            } else if (newText.isEmpty()) {
                filterInfo.setMinimumStockStatus(0);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
            }
        });

        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.getItems().add("--------");
        categoryFilter.getItems().addAll(AllAccountZone.getCategories());
        categoryFilter.setPromptText("Category");
        categoryFilter.setMinWidth(200);

        VBox filterVBox = new VBox(20);
        filterVBox.setAlignment(Pos.CENTER);
        filterVBox.setPadding(new Insets(20, 20, 20, 20));
        filterVBox.getChildren().addAll(minPrice, maxPrice, stock, categoryFilter);

        categoryFilter.setOnAction(e -> {
            filterInfo.setCategory(categoryFilter.getValue());
            AllAccountZone.setFilterCategoryFeature(categoryFilter.getValue(), "products");
            ArrayList<String> features = new ArrayList<>(AdminZone.getCategoryFeature(categoryFilter.getValue()));
            VBox filterFeature = new VBox(20);
            filterFeature.setAlignment(Pos.CENTER);
            for (String feature : features) {
                TextField textField = createTextField(feature, 200);
                filterFeature.getChildren().add(textField);
                textField.textProperty().addListener((list, oldText, newText) -> {
                    filterInfo.getFeature().replace(textField.getPromptText(), newText);
                    productsGridPane.getChildren().clear();
                    setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
                });
            }
            try {
                filterVBox.getChildren().remove(4);
            } catch (Exception ignored) {}
            filterVBox.getChildren().add(filterFeature);
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane, new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products")));
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setRight(filterVBox);
        borderPane.setCenter(productsGridPane);

        return borderPane;
    }

    public static void setProducts(GridPane gridPane, ArrayList<Product> products) {
        for (int i = 0; i < products.size()/5 + 1 ; i++) {
            for (int j = 0; j < 5 && 5*i + j < products.size(); j++) {
                Pane pane = new Pane();
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                ImageView productImage = null, rateImage = null, auctionImage = null;
                try {
                    productImage = new ImageView(new Image(new FileInputStream("Styles/Photos/product.png")));
                    rateImage = getRateImage(String.valueOf(products.get(5*i + j).getAverageScore()));
                    BuyerZone.setAuctionPrice();
                    double percent = (double) products.get(5*i + j).getGeneralFeature().getAuctionPrice() /
                            products.get(5*i + j).getGeneralFeature().getPrice();
                    auctionImage = getAuctionImage(1 - percent);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                productImage.setFitWidth(200);
                productImage.setFitHeight(200);
                productImage.setX(0);
                productImage.setY(0);
                rateImage.setFitWidth(140);
                rateImage.setFitHeight(40);
                rateImage.setX(30);
                rateImage.setY(160);
                Hyperlink hyperlink = new Hyperlink(String.valueOf(products.get(5*i + j).getId()));
                hyperlink.setMinWidth(200);
                hyperlink.setAlignment(Pos.CENTER);
                pane.getChildren().addAll(productImage, rateImage, hyperlink);
                if (auctionImage != null) {
                    auctionImage.setFitHeight(140);
                    auctionImage.setFitWidth(140);
                    auctionImage.setX(50);
                    auctionImage.setY(0);
                    pane.getChildren().add(auctionImage);
                }
                vBox.getChildren().addAll(pane, hyperlink);
                hyperlink.setOnMouseClicked(e ->
                        MainScenes.getBorderPane().setCenter(getProductRoot(Integer.parseInt(hyperlink.getText()))));
                gridPane.add(vBox, j, i);
            }
        }
    }

    private static ImageView getRateImage(String score) throws FileNotFoundException {
        switch (score) {
            case "0.0":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/0.png")));
            case "0.5":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/0.5.png")));
            case "1.0":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/1.png")));
            case "1.5":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/1.5.png")));
            case "2.0":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/2.png")));
            case "2.5":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/2.5.png")));
            case "3.0":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/3.png")));
            case "3.5":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/3.5.png")));
            case "4.0":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/4.png")));
            case "4.5":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/4.5.png")));
            case "5.0":
                return new ImageView(new Image(new FileInputStream("Styles/Photos/5.png")));
        }
        return null;
    }

    private static ImageView getAuctionImage(double percent) throws FileNotFoundException {
        switch ((int) (Math.round(percent * 10) * 10)) {
            case 10:
                return new ImageView(new Image(new FileInputStream("Styles/Photos/10.png")));
            case 20:
                return new ImageView(new Image(new FileInputStream("Styles/Photos/20.png")));
            case 30:
                return new ImageView(new Image(new FileInputStream("Styles/Photos/30.png")));
            case 40:
                return new ImageView(new Image(new FileInputStream("Styles/Photos/40.png")));
            case 50:
                return new ImageView(new Image(new FileInputStream("Styles/Photos/50.png")));
            case 60:
                return new ImageView(new Image(new FileInputStream("Styles/Photos/60.png")));
            case 70:
                return new ImageView(new Image(new FileInputStream("Styles/Photos/70.png")));
            case 80:
                return new ImageView(new Image(new FileInputStream("Styles/Photos/80.png")));
            case 90:
                return new ImageView(new Image(new FileInputStream("Styles/Photos/90.png")));
        }
        return null;
    }

    public static Parent getProductRoot(int productId) {
        GridPane info = new GridPane();
        info.setAlignment(Pos.CENTER);
        info.setHgap(20);
        info.setVgap(20);

        Product product = SellerZone.getProductById(productId);
        Label nameLabel = createLabel("Name : ", 100);
        Label nameText = createLabel(product.getGeneralFeature().getName(), 200);
        Label companyLabel = createLabel("Company : ", 100);
        Label companyText = createLabel(product.getGeneralFeature().getCompany(), 200);
        BuyerZone.setAuctionPrice();
        long price = product.getGeneralFeature().getPrice();
        long auctionPrice = product.getGeneralFeature().getAuctionPrice();
        Label priceLabel = createLabel("Price : ", 100);
        Label priceText = createLabel(String.valueOf(price), 200);
        Label auctionLabel, auctionText;
        Label sellerLabel = createLabel("Seller Username : ", 100);
        Label sellerText = createLabel(product.getGeneralFeature().getSeller(), 200);
        Label stockLabel = createLabel("Stock : ", 100);
        Label stockText = createLabel(String.valueOf(product.getGeneralFeature().getStockStatus()), 200);
        Label scoreLabel = createLabel("Score : ", 100);
        Label scoreText = createLabel(String.valueOf(product.getAverageScore()), 200);
        Label descriptionLabel = createLabel("Description : ", 100);
        Label descriptionText = createLabel(product.getDescription(), 200);
        Label categoryLabel = createLabel("Category : ", 100);
        Label categoryText = createLabel(product.getCategoryName(), 200);
        info.addColumn(0, nameLabel, companyLabel, sellerLabel, stockLabel, priceLabel);
        info.addColumn(1, nameText, companyText, sellerText, stockText, priceText);
        int i = 5;
        if (price != auctionPrice) {
            auctionLabel = createLabel("Auction Price : ", 100);
            auctionText = createLabel(String.valueOf(auctionPrice), 200);
            info.add(auctionLabel, 0, i);
            info.add(auctionText, 1, i++);
        }
        info.add(scoreLabel, 0, i);
        info.add(scoreText, 1, i++);
        info.add(descriptionLabel, 0, i);
        info.add(descriptionText, 1, i++);
        info.add(categoryLabel, 0, i);
        info.add(categoryText, 1, i++);
        for (Map.Entry<String, String> entry : product.getCategoryFeature().entrySet()) {
            Label featureLabel = createLabel(entry.getKey() + " : ", 100);
            Label featureText = createLabel(entry.getValue(), 200);
            info.add(featureLabel, 0, i);
            info.add(featureText, 1, i++);
        }

        Button rate = createButton("Rate", 150);
        rate.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (AllAccountZone.canUserBuyOrComment()) {
                if (BuyerZone.hasUserBoughtProduct(productId)) {
                    TextField score = createTextField("Score", 100);
                    Button rateScore = createButton("Rate", 100);
                    rateScore.setOnMouseClicked(event -> {
                        if (Actions.rate(score.getText())) {
                            BuyerZone.createRate(productId, Integer.parseInt(score.getText()));
                            MainScenes.getBorderPane().setCenter(getProductRoot(productId));
                        }
                    });
                    Button back = createButton("Back", 100);
                    back.setOnMouseClicked(ev -> MainScenes.getBorderPane().setCenter(getProductRoot(productId)));
                    HBox hBox = new HBox(20);
                    hBox.setAlignment(Pos.CENTER);
                    hBox.getChildren().addAll(score, rateScore, back);

                    MainScenes.getBorderPane().setCenter(hBox);
                } else {
                    alert.setContentText("You can't rate this product.");
                    alert.show();
                }
            } else {
                alert.setContentText("You should sign in as a buyer.");
                alert.show();
            }
        });

        Button compare = createButton("Compare", 150);
        compare.setOnMouseClicked(e -> {
            TextField productIdText = createTextField("Product ID", 100);
            Button ok = createButton("Compare", 100);
            ok.setOnMouseClicked(event -> {
                if (Actions.checkProductIdToCompare(productId, productIdText.getText())) {
                    String output = AllAccountZone.compareTwoProduct(productId, Integer.parseInt(productIdText.getText()));
                    GridPane gridPane = new GridPane();
                    gridPane.setAlignment(Pos.CENTER);
                    gridPane.setHgap(20);
                    gridPane.setVgap(20);
                    gridPane.addRow(0, createLabel("Feature", 100),
                            createLabel("Product1", 100), createLabel("Product2", 100));
                    int j = 1;
                    for (String featureCompare : output.split("-")) {
                        gridPane.addRow(j++, createLabel(featureCompare.split(",")[0], 100),
                                createLabel(featureCompare.split(",")[1], 100),
                                createLabel(featureCompare.split(",")[2], 100));
                    }
                    Button back = createButton("Back", 100);
                    back.setOnMouseClicked(ev -> MainScenes.getBorderPane().setCenter(getProductRoot(productId)));
                    gridPane.add(back, 1, j);

                    MainScenes.getBorderPane().setCenter(gridPane);
                }
            });
            Button back = createButton("Back", 100);
            back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(getProductRoot(productId)));

            VBox vBox = new VBox(20);
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(productIdText, ok, back);
            MainScenes.getBorderPane().setCenter(vBox);
        });

        Button comments = createButton("Comments", 150);
        comments.setOnMouseClicked(e -> {
            VBox vBox = new VBox(20);
            vBox.setAlignment(Pos.CENTER);
            for (Comment comment : SellerZone.getProductById(productId).getComments()) {
                if (comment.getStatus().equals("accepted")) {
                    vBox.getChildren().add(createLabel(comment.getBuyerName() + " : " + comment.getCommentText(),
                            500));
                }
            }
            Button add = createButton("Add a comment", 200);
            add.setOnMouseClicked(event -> {
                if (AllAccountZone.canUserBuyOrComment()) {
                    TextField comment = createTextField("Comment", 500);
                    Button send = createButton("Send", 100);
                    send.setOnMouseClicked(ev -> {
                        if (comment.getText() == null) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Enter your comment.");
                            alert.show();
                        } else {
                            AllAccountZone.createComment(comment.getText(), productId);
                            comment.clear();
                            vBox.getChildren().removeAll(comment, send);
                            vBox.getChildren().addAll(add);
                        }
                    });
                    vBox.getChildren().remove(add);
                    vBox.getChildren().addAll(comment, send);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("You should sign in as a buyer.");
                    alert.show();
                }
            });
            Button back = createButton("Back", 100);
            back.setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(getProductRoot(productId)));
            vBox.getChildren().addAll(add, back);

            MainScenes.getBorderPane().setCenter(vBox);
        });

        Button buy = createButton("Buy", 150);
        buy.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (AllAccountZone.canUserBuyOrComment()) {
                AllAccountZone.addProductToCart(productId);
                alert.setContentText("Product added to your cart successfully.");
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("You should sign in as a buyer.");
            }
            alert.show();
        });

        VBox buttons = new VBox(20);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.getChildren().addAll(rate, compare, comments, buy);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(30);
        gridPane.add(info, 0, 0);
        gridPane.add(buttons, 3, 0);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }
}
