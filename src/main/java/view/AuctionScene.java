package view;

import controller.AllAccountZone;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class AuctionScene {
    private static String sort = "Date";
    private static FilterInfo filterInfo;

    public static FilterInfo getFilterInfo() {
        return filterInfo;
    }

    public static void setFilterInfo(FilterInfo filterInfo) {
        AuctionScene.filterInfo = filterInfo;
    }

    public static String getSort() {
        return sort;
    }

    public static Pane getAuctionsRoot() {
        AuctionScene.filterInfo = new FilterInfo("", 0, Long.MAX_VALUE, "",
                0, new HashMap<>());
        ChoiceBox<String> sort = new ChoiceBox<>();
        sort.getItems().addAll("Price(Ascending)", "Price(Descending)", "Score", "Date");

        Button filter = new Button("Filter ;)");

        //products

        VBox vBox = new VBox(25, sort, filter);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }
}
