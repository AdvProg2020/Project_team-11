package Client.view;

import com.google.gson.Gson;
import controller.FileProcess;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Client.view.MainScenes.getSignInRoot;
import static Client.view.ClientHandler.getDataInputStream;
import static Client.view.ClientHandler.getDataOutputStream;

public class Actions {
    private static Gson gson = new Gson();

    public static void showError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(text);
        alert.show();
    }

    public static void showInfoBox(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.show();
    }

    public static boolean register(ArrayList<String> info) {
        if (info.get(0) == null) {
            showError("Choose a type");
        } else if (!Validation.validateNames(info.get(1))) {
            showError("Enter first name.");
        } else if (!Validation.validateNames(info.get(2))) {
            showError("Enter last name.");
        } else if (!Validation.validateEmail(info.get(3))) {
            showError("Email address is not valid.");
        } else if (!Validation.validateLong(info.get(4))) {
            showError("Phone number is not valid.");
        } else if (!Validation.validateNames(info.get(5))) {
            showError("Enter username.");
        } else if (!Validation.validateNames(info.get(6))) {
            showError("Enter password.");
        } else if (info.get(0).equals("seller") && !Validation.validateNames(info.get(7))) {
            showError("Enter Company name.");
        } else {
            try {
                getDataOutputStream().writeUTF("check username");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(info.get(5));
                getDataOutputStream().flush();
                if (getDataInputStream().readBoolean()) {
                    getDataOutputStream().writeUTF("register");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(gson.toJson(info));
                    getDataOutputStream().flush();
                    getDataInputStream().readUTF();
                } else {
                    showError("This username is already occupied.");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static void signIn(ArrayList<String> info) {
        if (!Validation.validateNames(info.get(1))) {
            showError("Enter username.");
        } else if (!Validation.validateNames(info.get(2))) {
            showError("Enter your password.");
        } else {
            String result = "";
            try {
                getDataOutputStream().writeUTF("sign in");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(gson.toJson(info));
                getDataOutputStream().flush();
                result = getDataInputStream().readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.startsWith("Login successfully")) {
                MainScenes.getSignInOrOut().setText("Logout");
                if (result.contains("admin")) {
                    Button button = new Button("Admin");
                    button.setOnAction(e -> {
                        MainScenes.getBorderPane().setLeft(AdminScene.getAdminRoot());
                        MainScenes.getBorderPane().setCenter(AdminScene.getPersonalInfo());
                    });
                    button.setMinWidth(200);
                    button.setAlignment(Pos.CENTER);
                    button.getStyleClass().add("top-buttons");
                    ((HBox) MainScenes.getBorderPane().getTop()).getChildren().add(3, button);
                    button.fire();
                } else if (result.contains("support")) {
                    Button button = new Button("Support");
                    button.setOnAction(e -> {
                        MainScenes.getBorderPane().setLeft(SupportScene.getSupportRoot());
                        MainScenes.getBorderPane().setCenter(new VBox());
                    });
                    button.setMinWidth(200);
                    button.setAlignment(Pos.CENTER);
                    button.getStyleClass().add("top-buttons");
                    ((HBox) MainScenes.getBorderPane().getTop()).getChildren().add(3, button);
                    button.fire();
                } else if (result.contains("seller")) {
                    Button button = new Button("Seller");
                    button.setOnAction(e -> {
                        MainScenes.getBorderPane().setLeft(SellerScene.getSellerRoot());
                        MainScenes.getBorderPane().setCenter(SellerScene.getPersonalInfo());
                    });
                    button.setMinWidth(200);
                    button.setAlignment(Pos.CENTER);
                    button.getStyleClass().add("top-buttons");
                    ((HBox) MainScenes.getBorderPane().getTop()).getChildren().add(3, button);
                    button.fire();
                } else {
                    Button button = new Button("Buyer");
                    button.setOnAction(e -> {
                        MainScenes.getBorderPane().setLeft(BuyerScene.getBuyerRoot());
                        MainScenes.getBorderPane().setCenter(BuyerScene.getPersonalInfo());
                    });
                    button.setMinWidth(200);
                    button.setAlignment(Pos.CENTER);
                    button.getStyleClass().add("top-buttons");
                    ((HBox) MainScenes.getBorderPane().getTop()).getChildren().add(3, button);
                    button.fire();
                }

                MainScenes.getSignInOrOut().setOnMouseClicked(e -> logout());
            } else {
                showError(result);
            }
        }
    }

    public static void logout() {
        ((HBox) MainScenes.getBorderPane().getTop()).getChildren().remove(3);
        try {
            getDataOutputStream().writeUTF("logout");
            getDataOutputStream().flush();
            getDataInputStream().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainScenes.getSignInOrOut().setText("Sign In");
        MainScenes.getSignInOrOut().setOnMouseClicked(event -> MainScenes.getBorderPane().setCenter(getSignInRoot()));

        MainScenes.getBorderPane().setCenter(ProductScene.getProductsRoot());
        MainScenes.getBorderPane().setLeft(null);
    }

    public static void editPersonalInfo(Button button, TextField textField) {
        button.setText("Save");
        textField.setDisable(false);
        button.setOnMouseClicked(event -> {
            boolean isValid = false;
            switch (textField.getPromptText()) {
                case "First Name":
                case "Last Name":
                case "Password":
                case "Company":
                    isValid = Validation.validateNames(textField.getText());
                    break;
                case "Email":
                    isValid = Validation.validateEmail(textField.getText());
                    break;
                case "Phone Number":
                    isValid = Validation.validateLong(textField.getText());
                    break;
            }
            if (isValid) {
                button.setText("Edit");
                textField.setDisable(true);

                try {
                    getDataOutputStream().writeUTF("edit personal info");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getPromptText());
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getText());
                    getDataOutputStream().flush();
                    getDataInputStream().readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                button.setOnMouseClicked(e -> editPersonalInfo(button, textField));
            } else {
                showError("Invalid format.");
            }
        });
    }

    public static boolean createDiscount(ArrayList<String> info) {
        if (!Validation.validateNames(info.get(0))) {
            showError("Enter Code.");
        } else if (!Validation.validateDate(info.get(1))) {
            showError("Start Date is not valid.");
        }  else if (!Validation.validateDate(info.get(2))) {
            showError("End Date is not valid.");
        }  else if (!Validation.validatePercent(info.get(3))) {
            showError("Discount percent is not valid.");
        }  else if (!Validation.validateLong(info.get(4))) {
            showError("Max Discount is not valid.");
        }  else if (!Validation.validateInteger(info.get(5))) {
            showError("Repeated Times is not valid.");
        }  else if (Integer.parseInt(info.get(5)) == 0) {
            showError("Repeated Times is not valid.");
        } else {
            try {
                getDataOutputStream().writeUTF("check discount code");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(info.get(0));
                getDataOutputStream().flush();
                if (getDataInputStream().readBoolean()) {
                    showError("This code is already occupied.");
                } else {
                    Date start = null, end = null;
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        start = format.parse(info.get(1));
                        end = format.parse(info.get(2));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    if (end.before(start)) {
                        showError("End date should be after start date.");
                    } else {
                        info.set(1, String.valueOf(start.getTime()));
                        info.set(2, String.valueOf(end.getTime()));
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteDiscount(String code) {
        if (!Validation.validateNames(code)) {
            showError("Enter Code.");
        } else {
            try {
                getDataOutputStream().writeUTF("check discount code");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(code);
                getDataOutputStream().flush();
                if (getDataInputStream().readBoolean()) {
                    getDataOutputStream().writeUTF("remove discount");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(code);
                    getDataOutputStream().flush();
                    getDataInputStream().readUTF();
                    return true;
                } else {
                    showError("There is no discount with this code.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean addUserToDiscount(String username) {
        if (!Validation.validateNames(username)) {
            showError("Enter username.");
        } else {
            try {
                getDataOutputStream().writeUTF("check username");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(username);
                getDataOutputStream().flush();
                if (getDataInputStream().readBoolean()) {
                    showError("There is no user with this username.");
                } else {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void editDiscount(Button button, TextField textField, String discountCode) {
        button.setText("Save");
        textField.setDisable(false);
        button.setOnMouseClicked(event -> {
            boolean isValid = false;
            switch (textField.getPromptText()) {
                case "Max Discount":
                    isValid = Validation.validateLong(textField.getText());
                    break;
                case "Discount Percent":
                    isValid = Validation.validatePercent(textField.getText());
                    break;
                case "Repeated Times":
                    isValid = Validation.validateInteger(textField.getText());
                    break;
                case "Start Date [dd/mm/yyyy hh:mm:ss]":
                case "End Date [dd/mm/yyyy hh:mm:ss]":
                    isValid = Validation.validateDate(textField.getText());
            }
            if (isValid) {
                //TODO : check dates
                button.setText("Edit");
                textField.setDisable(true);
                try {
                    getDataOutputStream().writeUTF("edit discount");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getPromptText());
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getText());
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(discountCode);
                    getDataOutputStream().flush();
                    getDataInputStream().readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                button.setOnMouseClicked(e -> editDiscount(button, textField, discountCode));
            } else {
                showError("Invalid format.");
            }
        });
    }

    public static boolean checkCategoryName(String name) {
        if (!Validation.validateNames(name)) {
            showError("Enter category name.");
        } else {
            try {
                getDataOutputStream().writeUTF("check category name");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(name);
                getDataOutputStream().flush();
                if (getDataInputStream().readBoolean()) {
                    return true;
                } else {
                    showError("This name is already occupied.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void editCategory(TextField textField, Button button, String categoryName) {
        String lastField = textField.getText();
        button.setText("Save");
        textField.setDisable(false);
        button.setOnMouseClicked(event -> {
            boolean isValid = false;
            switch (textField.getPromptText()) {
                case "Name":
                    isValid = checkCategoryName(textField.getText());
                    break;
                case "Feature":
                    isValid = Validation.validateNames(textField.getText());
            }
            if (isValid) {
                button.setText("Edit");
                textField.setDisable(true);
                try {
                    getDataOutputStream().writeUTF("edit category");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getPromptText());
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getText());
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(categoryName);
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(lastField);
                    getDataOutputStream().flush();
                    getDataInputStream().readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                button.setOnMouseClicked(e -> editCategory(textField, button, categoryName));
            } else {
                showError("Invalid format.");
            }
        });
    }

    public static void editProduct(TextField textField, Button button, int productId) {
        button.setText("Send Request");
        textField.setDisable(false);
        button.setOnMouseClicked(e -> {
            boolean isValid;
            switch (textField.getPromptText()) {
                case "Price":
                    isValid = Validation.validateLong(textField.getText());
                    break;
                case "Stock":
                    isValid = Validation.validateInteger(textField.getText());
                    break;
                case "Name":
                case "Company":
                case "Description":
                default:
                    isValid = Validation.validateNames(textField.getText());
            }
            if (isValid) {
                button.setText("Edit");
                textField.setDisable(true);
                try {
                    getDataOutputStream().writeUTF("edit product");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(productId + "," + textField.getPromptText() + "," + textField.getText());
                    getDataOutputStream().flush();
                    getDataInputStream().readUTF();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                button.setOnMouseClicked(event -> editProduct(textField, button, productId));
            } else {
                showError("Invalid format.");
            }
        });
    }

    public static boolean createProduct(ArrayList<String> info, HashMap<String, String> features, ImageView imageView,
                                        boolean hasImage, File file, String extension) {
        boolean isFeaturesComplete = true;
        for (Map.Entry<String, String> entry : features.entrySet()) {
            if (!Validation.validateNames(entry.getValue())) {
                isFeaturesComplete = false;
                break;
            }
        }
        if (!Validation.validateNames(info.get(0))) {
            showError("Enter product name.");
        } else if (!Validation.validateNames(info.get(1))) {
            showError("Enter company.");
        } else if (!Validation.validateLong(info.get(2))) {
            showError("Price format is not valid.");
        } else if (!Validation.validateInteger(info.get(3))) {
            showError("Stock format is not valid.");
        } else if (!Validation.validateNames(info.get(4))) {
            showError("Enter Description.");
        } else if (!Validation.validateNames(info.get(5))) {
            showError("Enter category name.");
        } else if (!isFeaturesComplete) {
            showError("Complete features.");
        } else {
            String id = "";
            try {
                getDataOutputStream().writeUTF("add product");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(gson.toJson(info));
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(gson.toJson(features));
                getDataOutputStream().flush();
                id = getDataInputStream().readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int productId = Integer.parseInt(id);
            if (hasImage) {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
                try {
                    ImageIO.write(bufferedImage, "png", new File("Styles/Photos/p" + productId + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (extension.equals("txt")) {
                try {
                    Formatter formatter = FileProcess.openFileToWrite("resources\\files\\p" + productId + "." + extension);
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        formatter.format(scanner.nextLine() + "\n");
                    }
                    formatter.close();
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }


    public static boolean createAuction(ArrayList<String> info) {

        if (!Validation.validatePercent(info.get(0))) {
            showError("Enter a number between 0 and 100.");
        } else if (!Validation.validateDate(info.get(1))) {
            showError("Start date format is not valid.");
        } else if (!Validation.validateDate(info.get(2))) {
            showError("End date format is not valid.");
        } else {
            Date start = null, end = null;
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                start = format.parse(info.get(1));
                end = format.parse(info.get(2));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            if (end.before(start)) {
                showError("End date should be after start date.");
            } else {
                info.set(1, String.valueOf(start.getTime()));
                info.set(2, String.valueOf(end.getTime()));
                try {
                    getDataOutputStream().writeUTF("add auction");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(gson.toJson(info));
                    getDataOutputStream().flush();
                    getDataInputStream().readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    public static void editAuction(TextField textField, Button button, int auctionId) {
        button.setText("Send Request");
        textField.setDisable(false);
        button.setOnMouseClicked(event -> {
            boolean isValid = false;
            switch (textField.getPromptText()) {
                case "Discount":
                    isValid = Validation.validatePercent(textField.getText());
                    break;
                case "Start [dd/mm/yyyy hh:mm:ss]":
                case "End [dd/mm/yyyy hh:mm:ss]":
                    isValid = Validation.validateDate(textField.getText());
            }
            //TODO : check date
            if (isValid) {
                button.setText("Edit");
                textField.setDisable(true);
                try {
                    getDataOutputStream().writeUTF("edit auction");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getPromptText());
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getText());
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(String.valueOf(auctionId));
                    getDataOutputStream().flush();
                    getDataInputStream().readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                button.setOnMouseClicked(e -> editAuction(textField, button, auctionId));
            } else {
                showError("Invalid format.");
            }
        });
    }

    public static boolean checkReceiverInfo(String address, String number) {
        if (!Validation.validateNames(address)) {
            showError("Enter Address.");
        } else if (!Validation.validateLong(number)) {
            showError("Invalid Phone Number.");
        } else {
            return true;
        }
        return false;
    }

    public static boolean checkProductIdToCompare(int productId1, String productId2) {
        if (productId2 == null) {
            showError("Enter product ID to compare.");
        } else if (!Validation.validateInteger(productId2)) {
            showError("Enter a number.");
        } else if (productId1 == Integer.parseInt(productId2)) {
            showError("Choose a different product.");
        } else {
            try {
                getDataOutputStream().writeUTF("check product id");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(productId2);
                getDataOutputStream().flush();
                if (getDataInputStream().readBoolean()) {
                    getDataOutputStream().writeUTF("can compare product");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(String.valueOf(productId1));
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(productId2);
                    getDataOutputStream().flush();
                    String output = getDataInputStream().readUTF();
                    if (output.startsWith("Cannot")) {
                        showError(output);
                    } else {
                        return true;
                    }
                } else {
                    showError("Invalid ID.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean rate(String scoreText) {
        if (scoreText == null) {
            showError("Enter score.");
        } else if (!Validation.validateInteger(scoreText)) {
            showError("Invalid format.");
        } else {
            int score = Integer.parseInt(scoreText);
            if (score > 5 || score < 0) {
                showError("Enter a number from 0 to 5.");
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean checkBankInfo(String commission, String minimumMoney, String bankPassword) {
        if (!Validation.validatePercent(commission)) {
            showError("Enter a percent as commission.");
        } else if (!Validation.validateLong(minimumMoney)) {
            showError("Minimum money format is not valid.");
        } else if (!Validation.validateNames(bankPassword)) {
            showError("Enter Bank Password.");
        } else {
            return true;
        }
        return false;
    }

    public static void editBankInfo(Button button, TextField textField) {
        button.setText("Save");
        textField.setDisable(false);
        button.setOnMouseClicked(event -> {
            boolean isValid = false;
            switch (textField.getPromptText()) {
                case "Commission":
                    isValid = Validation.validatePercent(textField.getText());
                    break;
                case "Minimum Money":
                    isValid = Validation.validateLong(textField.getText());
            }
            if (isValid) {
                button.setText("Edit");
                textField.setDisable(true);

                try {
                    getDataOutputStream().writeUTF("edit bank operation");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getPromptText());
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(textField.getText());
                    getDataOutputStream().flush();
                    getDataInputStream().readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                button.setOnMouseClicked(e -> editPersonalInfo(button, textField));
            } else {
                showError("Invalid format.");
            }
        });
    }

    public static boolean createBid(ArrayList<String> info) throws IOException {
        if (!Validation.validateInteger(info.get(0))) {
            showError("Enter a number as product ID.");
        } else if (!Validation.validateDate(info.get(1))) {
            showError("Start date format is not valid.");
        } else if (!Validation.validateDate(info.get(2))) {
            showError("End date format is not valid.");
        } else if (!Validation.validateLong(info.get(3))) {
            showError("Price format is not valid.");
        } else {
            getDataOutputStream().writeUTF("seller has product");
            getDataOutputStream().flush();
            getDataOutputStream().writeUTF(info.get(0));
            getDataOutputStream().flush();
            if (getDataInputStream().readBoolean()) {
                getDataOutputStream().writeUTF("get product stock");
                getDataOutputStream().flush();
                getDataOutputStream().writeUTF(info.get(0));
                getDataOutputStream().flush();
                int stock = getDataInputStream().readInt();
                if (stock > 0) {
                    Date start = null, end = null;
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        start = format.parse(info.get(1));
                        end = format.parse(info.get(2));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    if (end.before(start)) {
                        showError("End date should be after start date.");
                    } else {
                        info.set(1, String.valueOf(start.getTime()));
                        info.set(2, String.valueOf(end.getTime()));
                        try {
                            getDataOutputStream().writeUTF("add bid");
                            getDataOutputStream().flush();
                            getDataOutputStream().writeUTF(gson.toJson(info));
                            getDataOutputStream().flush();
                            getDataInputStream().readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                } else {
                    showError("Stock is empty.");
                }
            } else {
                showError("You don't have this product.");
            }
        }
        return false;
    }

    public static boolean checkBidPrice(String offeredPrice, int bidId) {
        if (!Validation.validateLong(offeredPrice)) {
            showError("Price format is not valid.");
        } else {
            try {
                getDataOutputStream().writeUTF("get wallet");
                getDataOutputStream().flush();
                long wallet = getDataInputStream().readLong();

                getDataOutputStream().writeUTF("get bid price");
                getDataOutputStream().flush();
                getDataOutputStream().writeInt(bidId);
                getDataOutputStream().flush();
                long price = getDataInputStream().readLong();

                if (Long.parseLong(offeredPrice) <= price) {
                    showError("Offer price more than " + price + "$.");
                } else if (Long.parseLong(offeredPrice) > wallet) {
                    showError("Charge your wallet. You have only " + wallet + "$.");
                } else {
                    getDataOutputStream().writeUTF("offer bid price");
                    getDataOutputStream().flush();
                    getDataOutputStream().writeInt(bidId);
                    getDataOutputStream().flush();
                    getDataOutputStream().writeUTF(offeredPrice);
                    getDataOutputStream().flush();
                    if (getDataInputStream().readUTF().equals("done")) {
                        return true;
                    } else {
                        getDataOutputStream().writeUTF("get bid price");
                        getDataOutputStream().flush();
                        getDataOutputStream().writeInt(bidId);
                        getDataOutputStream().flush();
                        price = getDataInputStream().readLong();
                        showError("Offer price more than " + price + "$.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
