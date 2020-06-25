package view;

import controller.AdminZone;
import controller.AllAccountZone;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

import static view.MainScenes.createTextField;

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
        setProducts(productsGridPane);

        ComboBox<String> sort = new ComboBox<>();
        sort.getItems().addAll("Price(Ascending)", "Price(Descending)", "Score", "Date");
        sort.setPromptText("Sort");
        sort.getSelectionModel().select(3);
        sort.setOnAction(e -> {
            ProductScene.sort = sort.getValue().toLowerCase();
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane);
        });

        TextField nameFilter = createTextField("Product Name / Company / Seller Name", 500);
        nameFilter.textProperty().addListener((list, oldText, newText) -> {
            filterInfo.setSearchBar(newText);
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane);
        });

        HBox hBox = new HBox(150, sort, nameFilter);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20, 20, 20, 20));

        TextField minPrice = createTextField("Minimum Price", 200);
        minPrice.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,18}")) {
                filterInfo.setMinimumPrice(Long.parseLong(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane);
            } else if (newText.isEmpty()) {
                filterInfo.setMinimumPrice(0);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane);
            }
        });
        TextField maxPrice = createTextField("Maximum Price", 200);
        maxPrice.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,18}")) {
                filterInfo.setMaximumPrice(Long.parseLong(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane);
            } else if (newText.isEmpty()) {
                filterInfo.setMaximumPrice(Long.MAX_VALUE);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane);
            }
        });
        TextField stock = createTextField("Minimum Stock", 200);
        stock.textProperty().addListener((list, oldText, newText) -> {
            if (newText.matches("\\d{1,9}")) {
                filterInfo.setMinimumStockStatus(Integer.parseInt(newText));
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane);
            } else if (newText.isEmpty()) {
                filterInfo.setMinimumStockStatus(0);
                productsGridPane.getChildren().clear();
                setProducts(productsGridPane);
            }
        });

        ComboBox<String> categoryFilter = new ComboBox<>();
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
                    setProducts(productsGridPane);
                });
            }
            try {
                filterVBox.getChildren().remove(4);
            } catch (Exception ignored) {}
            filterVBox.getChildren().add(filterFeature);
            productsGridPane.getChildren().clear();
            setProducts(productsGridPane);
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setRight(filterVBox);
        borderPane.setCenter(productsGridPane);

        return borderPane;
    }

    private static void setProducts(GridPane gridPane) {
        ArrayList<Product> products = new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products"));
        for (int i = 0; i < products.size()/5 + 1 ; i++) {
            for (int j = 0; j < 5 && 5*i + j < products.size(); j++) {
                Hyperlink hyperlink = new Hyperlink(String.valueOf(products.get(5*i + j).getId()));
                hyperlink.setMinSize(100, 100);
                hyperlink.setOnMouseClicked(e -> {
                    //TODO : Product Scene
                });
                gridPane.add(hyperlink, j, i);
            }
        }
    }

    public static void getProductRoot() {
        
    }
}
