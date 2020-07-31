package Client.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.AllAccountZone;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static Client.view.Actions.showError;
import static Client.view.MainScenes.*;
import static Client.view.ProductScene.getProductRoot;
import static Client.view.ProductScene.getRateImage;
import static Client.view.ServerConnection.*;

public class BidScene {
    private static String sort = "Date";
    private static FilterInfo filterInfo;
    private static Gson gson = new Gson();

    public static FilterInfo getFilterInfo() {
        return filterInfo;
    }

    public static String getSort() {
        return sort;
    }

    public static Pane getBidRoot() {
        final Type[] foundListType = new Type[1];
        BidScene.filterInfo = new FilterInfo("", 0, Long.MAX_VALUE, "",
                0, new HashMap<>());

        GridPane productsGridPane = new GridPane();
        productsGridPane.setAlignment(Pos.CENTER);
        productsGridPane.setHgap(20);
        productsGridPane.setVgap(20);
        setProducts(productsGridPane,
                new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));

        ComboBox<String> sort = new ComboBox<>();
        sort.getItems().addAll("Price(Ascending)", "Price(Descending)", "Score", "Date");
        sort.setPromptText("Sort");
        sort.getSelectionModel().select(3);
        sort.setOnAction(e -> {
            BidScene.sort = sort.getValue().toLowerCase();
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane,
                    new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
        });

        TextField nameFilter = createTextField("Product Name / Company / Seller Name", 500);
        nameFilter.textProperty().addListener((list, oldText, newText) -> {
            filterInfo.setSearchBar(newText);
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane,
                    new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
        });

        HBox hBox = new HBox(150, sort, nameFilter);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20, 20, 20, 20));

        TextField minPrice = createTextField("Minimum Price", 200);
        minPrice.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,18}")) {
                filterInfo.setMinimumPrice(Long.parseLong(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
            } else if (newText.isEmpty()) {
                filterInfo.setMinimumPrice(0);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
            }
        });
        TextField maxPrice = createTextField("Maximum Price", 200);
        maxPrice.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,18}")) {
                filterInfo.setMaximumPrice(Long.parseLong(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
            } else if (newText.isEmpty()) {
                filterInfo.setMaximumPrice(Long.MAX_VALUE);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
            }
        });
        TextField stock = createTextField("Minimum Stock", 200);
        stock.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,9}")) {
                filterInfo.setMinimumStockStatus(Integer.parseInt(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
            } else if (newText.isEmpty()) {
                filterInfo.setMinimumStockStatus(0);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
            }
        });

        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.getItems().add("--------");
        try {
            String data = getCategories();
            foundListType[0] = new TypeToken<ArrayList<String>>() {}.getType();
            categoryFilter.getItems().addAll(new ArrayList<>(gson.fromJson(data, foundListType[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoryFilter.setPromptText("Category");
        categoryFilter.setMinWidth(200);

        VBox filterVBox = new VBox(20);
        filterVBox.setAlignment(Pos.CENTER);
        filterVBox.setPadding(new Insets(20, 20, 20, 20));
        filterVBox.getChildren().addAll(minPrice, maxPrice, stock, categoryFilter);

        categoryFilter.setOnAction(e -> {
            filterInfo.setCategory(categoryFilter.getValue());
            ArrayList<String> features = new ArrayList<>();
            try {
                String data = getCategoryFeature(categoryFilter.getValue());
                foundListType[0] = new TypeToken<ArrayList<String>>() {}.getType();
                features = gson.fromJson(data, foundListType[0]);
                AllAccountZone.setFilterCategoryFeature(categoryFilter.getValue(), features, "bid");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            VBox filterFeature = new VBox(20);
            filterFeature.setAlignment(Pos.CENTER);
            for (String feature : features) {
                TextField textField = createTextField(feature, 200);
                filterFeature.getChildren().add(textField);
                textField.textProperty().addListener((list, oldText, newText) -> {
                    filterInfo.getFeature().replace(textField.getPromptText(), newText);
                    productsGridPane.getChildren().clear();
                    setProducts(productsGridPane,
                            new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
                });
            }
            try {
                filterVBox.getChildren().remove(4);
            } catch (Exception ignored) {}
            filterVBox.getChildren().add(filterFeature);
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane,
                    new ArrayList<>(AllAccountZone.getBidProductInSortAndFiltered(getProducts(), "bid")));
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setRight(filterVBox);
        borderPane.setCenter(productsGridPane);

        return borderPane;
    }

    public static ArrayList<Product> getProducts() {
        try {
            Type foundListType = new TypeToken<ArrayList<Product>>(){}.getType();
            return gson.fromJson(getBidProducts(), foundListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setProducts(GridPane gridPane, ArrayList<Product> products) {
        for (int i = 0; i < products.size()/5 + 1 ; i++) {
            for (int j = 0; j < 5 && 5*i + j < products.size(); j++) {
                Pane pane = new Pane();
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                ImageView productImage = null, rateImage = null;
                try {
                    rateImage = getRateImage(String.valueOf(products.get(5*i + j).getAverageScore()));
                    productImage = new ImageView(new Image(new FileInputStream("Styles/Photos/p" +
                            products.get(5*i + j).getId() + ".png")));
                } catch (FileNotFoundException e) {
                    try {
                        productImage = new ImageView(new Image(new FileInputStream("Styles/Photos/product.png")));
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                productImage.setFitWidth(200);
                productImage.setFitHeight(200);
                productImage.setX(0);
                productImage.setY(0);
                rateImage.setFitWidth(140);
                rateImage.setFitHeight(40);
                rateImage.setX(30);
                rateImage.setY(160);
                pane.getChildren().addAll(productImage, rateImage);
                pane.setMinHeight(200);
                HBox hBox = new HBox();
                hBox.getChildren().addAll(createLabel(products.get(5*i + j).getGeneralFeature().getName(), 100),
                        createLabel(products.get(5 * i + j).getGeneralFeature().getAuctionPrice() + "$", 100));
                hBox.setAlignment(Pos.CENTER);
                Hyperlink productHyperlink = new Hyperlink(String.valueOf(products.get(5*i + j).getId()));
                productHyperlink.setMinWidth(200);
                productHyperlink.setAlignment(Pos.CENTER);
                Hyperlink bidHyperlink = new Hyperlink();
                try {
                    bidHyperlink.setText(getBidId(productHyperlink.getText()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bidHyperlink.setMinWidth(200);
                bidHyperlink.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(pane, hBox, productHyperlink, bidHyperlink);
                productHyperlink.setOnMouseClicked(e ->
                        MainScenes.getBorderPane().setCenter(getProductRoot(Integer.parseInt(productHyperlink.getText()))));
                bidHyperlink.setOnMouseClicked(e -> {
                    try {
                        if (isBuyer()) {
                            MainScenes.getBorderPane().setCenter(getBidPane(Integer.parseInt(bidHyperlink.getText())));
                        } else {
                            showError("You should login as a buyer.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                gridPane.add(vBox, j, i);
            }
        }
    }

    private static Parent getBidPane(int bidId) {
        VBox chatVBox = new VBox(20, getMessagePane(bidId), getSendMessageHBox(bidId));
        chatVBox.setAlignment(Pos.CENTER);

        VBox priceVBox = new VBox(20, getOffersPane(bidId), getOfferPriceHBox(bidId));
        priceVBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(20, chatVBox, priceVBox);
        hBox.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(hBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static ScrollPane getMessagePane(int bidId) {
        VBox messageVBox = new VBox(20);
        messageVBox.setAlignment(Pos.CENTER);
        ScrollPane scrollMessages = new ScrollPane(messageVBox);
        scrollMessages.setFitToWidth(true);
        scrollMessages.setFitToHeight(true);

        try {
            String data = getBidMessages(bidId);
            Type foundListType = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
            ArrayList<HashMap<String, String>> messages = gson.fromJson(data, foundListType);

            String username = getUsername();

            if (messages != null) {
                for (HashMap<String, String> message : messages) {
                    for (Map.Entry<String, String> entry : message.entrySet()) {
                        Label label;
                        if (entry.getKey().equals(username)) {
                            label = createLabel(entry.getValue(), 800);
                            label.setAlignment(Pos.CENTER_RIGHT);
                        } else {
                            label = createLabel(entry.getKey() + " : " + entry.getValue(), 800);
                            label.setAlignment(Pos.CENTER_LEFT);
                        }
                        messageVBox.getChildren().add(label);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scrollMessages;
    }

    private static HBox getSendMessageHBox(int bidId) {
        TextField messageText = createTextField("Message", 700);
        Button sendMessage = createButton("Send", 100);
        sendMessage.setOnMouseClicked(e -> {
            if (messageText.getText() != null) {
                Label message = createLabel(messageText.getText(), 500);
                message.setAlignment(Pos.CENTER_RIGHT);
                try {
                    sendMessageBid(bidId, message.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            messageText.clear();
        });

        HBox sendMessageHBox = new HBox(20, messageText, sendMessage);
        sendMessageHBox.setAlignment(Pos.BOTTOM_CENTER);

        return sendMessageHBox;
    }

    private static ScrollPane getOffersPane(int bidId) {
        VBox priceOfferedVBox = new VBox(20);
        priceOfferedVBox.setAlignment(Pos.CENTER);
        ScrollPane scrollOffers = new ScrollPane(priceOfferedVBox);
        scrollOffers.setFitToWidth(true);
        scrollOffers.setFitToHeight(true);

        try {
            String data = getBidOffers(bidId);
            Type foundListType = new TypeToken<HashMap<Long, String>>(){}.getType();
            HashMap<Long, String> offers = gson.fromJson(data, foundListType);

            String username = getUsername();

            ArrayList<Long> prices = new ArrayList<>(offers.keySet());
            prices.sort(Comparator.comparingLong(o -> o));
            for (Long price : prices) {
                Label label;
                if (offers.get(price).equals(username)) {
                    label = createLabel(String.valueOf(price), 200);
                    label.setAlignment(Pos.CENTER_RIGHT);
                } else {
                    label = createLabel(offers.get(price) + " : " + price, 200);
                    label.setAlignment(Pos.CENTER_LEFT);
                }
                priceOfferedVBox.getChildren().add(label);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scrollOffers;
    }

    private static HBox getOfferPriceHBox(int bidId) {
        TextField offeredPrice = createTextField("Offered Price", 200);
        Button offer = createButton("Offer", 100);
        offer.setOnMouseClicked(e -> {
            if (Actions.checkBidPrice(offeredPrice.getText(), bidId)) {
                offeredPrice.clear();
            }
        });

        HBox sendOfferHBox = new HBox(20, offeredPrice, offer);
        sendOfferHBox.setAlignment(Pos.BOTTOM_CENTER);

        return sendOfferHBox;
    }
}
