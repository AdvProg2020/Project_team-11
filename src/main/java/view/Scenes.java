package view;

import controller.AllAccountZone;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Scenes {

    public static Scene getRegisterAdminScene() {
        TextField firstName = createTextField("First Name");
        TextField lastName = createTextField("Last Name");
        TextField email = createTextField("Email");
        TextField phoneNumber = createTextField("Phone Number");
        TextField username = createTextField("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(300);
        password.setAlignment(Pos.CENTER);

        Button register = new Button("Register");
        register.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(25, firstName, lastName, email, phoneNumber, username, password, register);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }

    private static TextField createTextField(String name) {
        TextField textField = new TextField();
        textField.setPromptText(name);
        textField.setMaxWidth(300);
        textField.setAlignment(Pos.CENTER);
        return textField;
    }

    public static Scene getMainMenuScene() {
        Button signInOrLogout;
        if (AllAccountZone.getCurrentAccount() == null) {
            signInOrLogout = new Button("Sign in");
        } else {
            signInOrLogout = new Button("Logout");
        }
        signInOrLogout.setAlignment(Pos.CENTER);
        signInOrLogout.setPrefWidth(100);
        Button products = new Button("Products");
        products.setAlignment(Pos.CENTER);
        products.setPrefWidth(100);
        Button auctions = new Button("Auction");
        auctions.setAlignment(Pos.CENTER);
        auctions.setPrefWidth(100);

        VBox vBox = new VBox(25, signInOrLogout, products, auctions);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 600, 550);
    }
}
