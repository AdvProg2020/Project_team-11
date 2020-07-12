package Client.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.AllAccountZone;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import static Client.view.ClientHandler.getDataInputStream;
import static Client.view.ClientHandler.getDataOutputStream;
import static Client.view.MainScenes.createTextField;
import static Client.view.ProductScene.getProducts;
import static Client.view.ProductScene.setProducts;

public class AuctionScene {
    private static String sort = "Date";
    private static FilterInfo filterInfo;
    private static Gson gson = new Gson();

    public static FilterInfo getFilterInfo() {
        return filterInfo;
    }

    public static String getSort() {
        return sort;
    }

    public static Pane getAuctionsRoot() {
        final Type[] foundListType = new Type[1];
        AuctionScene.filterInfo = new FilterInfo("", 0, Long.MAX_VALUE, "",
                0, new HashMap<>());

        GridPane productsGridPane = new GridPane();
        productsGridPane.setAlignment(Pos.CENTER);
        productsGridPane.setHgap(20);
        productsGridPane.setVgap(20);
        setProducts(productsGridPane,
                new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));

        ComboBox<String> sort = new ComboBox<>();
        sort.getItems().addAll("Price(Ascending)", "Price(Descending)", "Score", "Date");
        sort.setPromptText("Sort");
        sort.getSelectionModel().select(3);
        sort.setOnAction(e -> {
            AuctionScene.sort = sort.getValue().toLowerCase();
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane,
                    new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
        });

        TextField nameFilter = createTextField("Product Name / Company / Seller Name", 500);
        nameFilter.textProperty().addListener((list, oldText, newText) -> {
            filterInfo.setSearchBar(newText);
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane,
                    new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
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
                        new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
            } else if (newText.isEmpty()) {
                filterInfo.setMinimumPrice(0);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
            }
        });
        TextField maxPrice = createTextField("Maximum Price", 200);
        maxPrice.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,18}")) {
                filterInfo.setMaximumPrice(Long.parseLong(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
            } else if (newText.isEmpty()) {
                filterInfo.setMaximumPrice(Long.MAX_VALUE);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
            }
        });
        TextField stock = createTextField("Minimum Stock", 200);
        stock.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,9}")) {
                filterInfo.setMinimumStockStatus(Integer.parseInt(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
            } else if (newText.isEmpty()) {
                filterInfo.setMinimumStockStatus(0);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane,
                        new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
            }
        });

        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.getItems().add("--------");
        try {
            getDataOutputStream().writeUTF("get categories");
            getDataOutputStream().flush();
            String data = getDataInputStream().readUTF();
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
            AllAccountZone.setFilterCategoryFeature(categoryFilter.getValue(), "products");
            ArrayList<String> features = new ArrayList<>();
            try {
                getDataOutputStream().writeUTF("get category feature");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(categoryFilter.getValue());
                getDataOutputStream().flush();
                String data = getDataInputStream().readUTF();
                foundListType[0] = new TypeToken<ArrayList<String>>() {}.getType();
                features = gson.fromJson(data, foundListType[0]);
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
                            new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
                });
            }
            try {
                filterVBox.getChildren().remove(4);
            } catch (Exception ignored) {}
            filterVBox.getChildren().add(filterFeature);
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane,
                    new ArrayList<>(AllAccountZone.getAuctionProductsInSortAndFiltered(getProducts(), "products")));
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setRight(filterVBox);
        borderPane.setCenter(productsGridPane);

        return borderPane;
    }
}
