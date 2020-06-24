package view;

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

import static view.MainScenes.createButton;
import static view.MainScenes.createTextField;
import static view.MainScenes.createLabel;

public class ProductScene {
    private static String sort = "Date";
    private static FilterInfo filterInfo;

    public static FilterInfo getFilterInfo() {
        return filterInfo;
    }

    public static void setFilterInfo(FilterInfo filterInfo) {
        ProductScene.filterInfo = filterInfo;
    }

    public static String getSort() {
        return sort;
    }

    public static Pane getProductsRoot() {
        ComboBox<String> sort = new ComboBox<>();
        sort.getItems().addAll("Price(Ascending)", "Price(Descending)", "Score", "Date");
        sort.setPromptText("Sort");
        sort.getSelectionModel().select(3);
        sort.setOnAction(e -> {
            ProductScene.sort = sort.getValue().toLowerCase();
            //TODO : show product
        });

        ProductScene.filterInfo = new FilterInfo("", 0, Long.MAX_VALUE, "",
                "", "", 0, new HashMap<>());

        TextField nameFilter = createTextField("Product Name / Company / Seller Name", 500);
        //TODO : bind

        HBox hBox = new HBox(150, sort, nameFilter);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20, 20, 20, 20));

        TextField minPrice = createTextField("Minimum Price", 200);
        TextField maxPrice = createTextField("Maximum Price", 200);
        TextField stock = createTextField("Minimum Stock", 200);

        VBox filterVBox = new VBox(20);
        filterVBox.setAlignment(Pos.CENTER);
        filterVBox.setPadding(new Insets(20, 20, 20, 20));
        filterVBox.getChildren().addAll(minPrice, maxPrice, stock);

        GridPane productsGridPane = new GridPane();
        productsGridPane.setAlignment(Pos.CENTER);
        productsGridPane.setHgap(20);
        productsGridPane.setVgap(20);

        ArrayList<Product> products = new ArrayList<>(AllAccountZone.getProductsInSortAndFiltered("products"));
        for (int i = 0; i < products.size()/5 + 1 ; i++) {
            for (int j = 0; j < 5 && 5*i + j < products.size(); j++) {
                Hyperlink hyperlink = new Hyperlink(String.valueOf(products.get(5*i + j).getId()));
                hyperlink.setMinSize(100, 100);
                hyperlink.setOnMouseClicked(e -> {
                    //TODO : Product Scene
                });
                productsGridPane.add(hyperlink, j, i);
            }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setRight(filterVBox);
        borderPane.setCenter(productsGridPane);

        return borderPane;
    }
}
