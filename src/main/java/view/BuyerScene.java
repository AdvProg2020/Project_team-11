package view;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import static view.MainScenes.createButton;
import static view.MainScenes.createTextField;
import static view.MainScenes.createLabel;

public class BuyerScene {

    public static Scene getBuyerScene() {
        Button personalInfo = createButton("View Personal Info", 200);
        Button cart = createButton("View Cart", 200);
        Button orders = createButton("View Orders", 200);
        Button balance = createButton("View Balance", 200);
        Button discountCodes = createButton("View Discount Codes", 200);

        Button logout = createButton("Logout", 200);
        logout.setOnMouseClicked(e -> Actions.logout());

        Button back = createButton("Back", 200);
        back.setOnMouseClicked(e -> {
            CommandProcessor.getStage().setScene(MainScenes.getMainScene());
            CommandProcessor.getStage().setMaximized(true);
        });

        VBox vBox = new VBox(25, personalInfo, cart, orders, balance, discountCodes);
        vBox.setAlignment(Pos.CENTER);

        Screen screen = Screen.getPrimary();
        Rectangle2D bound = screen.getBounds();
        return new Scene(vBox, bound.getWidth(), bound.getHeight());
    }
}
