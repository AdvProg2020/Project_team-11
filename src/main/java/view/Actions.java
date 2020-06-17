package view;

import controller.AdminZone;
import controller.AllAccountZone;
import controller.SellerZone;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Actions {

    public static boolean register(ArrayList<String> info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (info.get(0) == null) {
            alert.setContentText("Choose a type");
            alert.show();
        } else if (!Validation.validateNames(info.get(1))) {
            alert.setContentText("Enter first name.");
            alert.show();
        } else if (!Validation.validateNames(info.get(2))) {
            alert.setContentText("Enter last name.");
            alert.show();
        } else if (!Validation.validateEmail(info.get(3))) {
            alert.setContentText("Email address is not valid.");
            alert.show();
        } else if (!Validation.validateLong(info.get(4))) {
            alert.setContentText("Phone number is not valid.");
            alert.show();
        } else if (!Validation.validateNames(info.get(5))) {
            alert.setContentText("Enter username.");
            alert.show();
        } else if (!Validation.validateNames(info.get(6))) {
            alert.setContentText("Enter password.");
            alert.show();
        } else if (!info.get(0).equals("admin") && !Validation.validateLong(info.get(7))) {
            alert.setContentText("Balance format is not valid.");
            alert.show();
        } else if (info.get(0).equals("seller") && !Validation.validateNames(info.get(8))) {
            alert.setContentText("Enter Company name.");
            alert.show();
        } else if (!AllAccountZone.isUsernameValid(info.get(5))) {
            alert.setContentText("This username is already occupied.");
            alert.show();
        } else {
            AllAccountZone.createAccount(info);
            return true;
        }
        return false;
    }

    public static void signIn(ArrayList<String> info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!Validation.validateNames(info.get(1))) {
            alert.setContentText("Enter username.");
            alert.show();
        } else if (!Validation.validateNames(info.get(2))) {
            alert.setContentText("Enter your password.");
            alert.show();
        } else {
            String result = AllAccountZone.loginUser(info);
            if (result.equals("Login successfully.")) {
                MainScenes.getSignInOrOut().setText("Logout");

                MainScenes.getSignInOrOut().setOnMouseClicked(e -> logout());

                CommandProcessor.getStage().setScene(MainScenes.getLastScene());
                CommandProcessor.getStage().setMaximized(true);
            } else {
                alert.setContentText(result);
                alert.show();
            }
        }
    }

    public static void logout() {
        ((VBox) MainScenes.getMainScene().getRoot()).getChildren().remove(MainScenes.getAccountButton());
        AllAccountZone.setCurrentAccount(null);

        MainScenes.getSignInOrOut().setText("Sign In");
        MainScenes.getSignInOrOut().setOnMouseClicked(event -> {
            MainScenes.setLastScene(CommandProcessor.getStage().getScene());
            CommandProcessor.getStage().setScene(MainScenes.getSignInScene());
            CommandProcessor.getStage().setMaximized(true);
        });

        CommandProcessor.getStage().setScene(MainScenes.getMainScene());
        CommandProcessor.getStage().setMaximized(true);
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
                case "Wallet":
                    isValid = Validation.validateLong(textField.getText());
                    break;
            }
            if (isValid) {
                button.setText("Edit");
                textField.setDisable(true);
                AllAccountZone.editPersonalInfo(textField.getPromptText(), textField.getText());
                button.setOnMouseClicked(e -> editPersonalInfo(button, textField));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid format.");
                alert.show();
            }
        });
    }

    public static boolean createDiscount(ArrayList<String> info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!Validation.validateNames(info.get(0))) {
            alert.setContentText("Enter Code.");
            alert.show();
        } else if (!Validation.validateDate(info.get(1))) {
            alert.setContentText("Start Date is not valid.");
            alert.show();
        }  else if (!Validation.validateDate(info.get(2))) {
            alert.setContentText("End Date is not valid.");
            alert.show();
        }  else if (!Validation.validatePercent(info.get(3))) {
            alert.setContentText("Discount percent is not valid.");
            alert.show();
        }  else if (!Validation.validateLong(info.get(4))) {
            alert.setContentText("Max Discount is not valid.");
            alert.show();
        }  else if (!Validation.validateInteger(info.get(5))) {
            alert.setContentText("Repeated Times is not valid.");
            alert.show();
        }  else if (Integer.parseInt(info.get(5)) == 0) {
            alert.setContentText("Repeated Times is not valid.");
            alert.show();
        } else if (AdminZone.getDiscountByCode(info.get(0)) != null) {
            alert.setContentText("This code is already occupied.");
            alert.show();
        } else {
            Date start = null, end = null;
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                start = format.parse(info.get(1));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                end = format.parse(info.get(2));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            if (end.before(start)) {
                alert.setContentText("End date should be after start date.");
                alert.show();
            } else {
                info.set(1, String.valueOf(start.getTime()));
                info.set(2, String.valueOf(end.getTime()));
                return true;
            }
        }
        return false;
    }

    public static boolean deleteDiscount(String code) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!Validation.validateNames(code)) {
            alert.setContentText("Enter Code.");
            alert.show();
        } else if (AdminZone.getDiscountByCode(code) == null) {
            alert.setContentText("There is no discount with this code.");
            alert.show();
        } else {
            AdminZone.removeDiscount(code);
            return true;
        }
        return false;
    }

    public static boolean addUserToDiscount(String username) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!Validation.validateNames(username)) {
            alert.setContentText("Enter username.");
            alert.show();
        } else if (AdminZone.getBuyerByUsername(username) == null) {
            alert.setContentText("There is no user with this username.");
            alert.show();
        } else {
            return true;
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
                    AdminZone.editDiscount(textField.getPromptText(), textField.getText(), discountCode);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                button.setOnMouseClicked(e -> editDiscount(button, textField, discountCode));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid format.");
                alert.show();
            }
        });
    }

    public static boolean checkCategoryName(String name) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!Validation.validateNames(name)) {
            alert.setContentText("Enter category name.");
            alert.show();
        } else if (Category.getCategoryByName(name) != null) {
            alert.setContentText("This name is already occupied.");
            alert.show();
        } else {
            return true;
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
                AdminZone.editCategory(textField.getPromptText(), textField.getText(), categoryName, lastField);
                button.setOnMouseClicked(e -> editCategory(textField, button, categoryName));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid format.");
                alert.show();
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
                SellerZone.sendEditProductRequest(productId + "," + textField.getPromptText() + ","
                        + textField.getText());
                button.setOnMouseClicked(event -> editProduct(textField, button, productId));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid format.");
                alert.show();
            }
        });
    }

    public static boolean createProduct(ArrayList<String> info, HashMap<String, String> features) {
        boolean isFeaturesComplete = true;
        for (Map.Entry<String, String> entry : features.entrySet()) {
            if (!Validation.validateNames(entry.getValue())) {
                isFeaturesComplete = false;
                break;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!Validation.validateNames(info.get(0))) {
            alert.setContentText("Enter product name.");
            alert.show();
        } else if (!Validation.validateNames(info.get(1))) {
            alert.setContentText("Enter company.");
            alert.show();
        } else if (!Validation.validateLong(info.get(2))) {
            alert.setContentText("Price format is not valid.");
            alert.show();
        } else if (!Validation.validateInteger(info.get(3))) {
            alert.setContentText("Stock format is not valid.");
            alert.show();
        } else if (!Validation.validateNames(info.get(4))) {
            alert.setContentText("Enter Description.");
            alert.show();
        } else if (!Validation.validateNames(info.get(5))) {
            alert.setContentText("Enter category name.");
            alert.show();
        } else if (!isFeaturesComplete) {
            alert.setContentText("Complete features.");
            alert.show();
        } else {
            SellerZone.sendAddNewProductRequest(info, features);
            return true;
        }
        return false;
    }
}
