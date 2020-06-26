package view;

import controller.AdminZone;
import controller.AllAccountZone;
import controller.BuyerZone;
import controller.SellerZone;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Product;

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
                hyperlink.setMinSize(200, 200);
                hyperlink.setOnMouseClicked(e ->
                        MainScenes.getBorderPane().setCenter(getProductRoot(Integer.parseInt(hyperlink.getText()))));
                gridPane.add(hyperlink, j, i);
            }
        }
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
        int i = 5;
        if (price != auctionPrice) {
             auctionLabel = createLabel("Auction Price : ", 100);
             auctionText = createLabel(product.getGeneralFeature().getName(), 200);
             info.add(auctionLabel, 0, i);
             info.add(auctionText, 1, i++);
        }
        Label sellerLabel = createLabel("Seller Username : ", 100);
        Label sellerText = createLabel(product.getGeneralFeature().getSeller().getUsername(), 200);
        Label stockLabel = createLabel("Stock : ", 100);
        Label stockText = createLabel(String.valueOf(product.getGeneralFeature().getStockStatus()), 200);
        info.addColumn(0, nameLabel, companyLabel, sellerLabel, stockLabel, priceLabel);
        info.addColumn(1, nameText, companyText, sellerText, stockText, priceText);
        for (Map.Entry<String, String> entry : product.getCategoryFeature().entrySet()) {
            Label featureLabel = createLabel(entry.getKey() + " : ", 100);
            Label featureText = createLabel(entry.getValue(), 200);
            info.add(featureLabel, 0, i);
            info.add(featureText, 1, i++);
        }

        Button compare = createButton("Compare", 150);
        Button comments = createButton("Comments", 150);
        Button digest = createButton("Digest", 150);

        VBox buttons = new VBox(20);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.getChildren().addAll(compare, comments, digest);

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
