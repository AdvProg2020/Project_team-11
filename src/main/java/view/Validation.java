package view;

public class Validation {

    public static boolean validateNames(String name) {
        return !name.isEmpty();
    }

    public static boolean validateEmail(String email) {
        if (email.isEmpty()) {
            return false;
        } else return email.matches(".+@.+\\.[a-zA-Z]{2,3}");
    }

    public static boolean validateInteger(String number) {
        if (number.isEmpty()) {
            return false;
        } else return number.matches("\\d{1,9}");
    }

    public static boolean validateLong(String number) {
        if (number.isEmpty()) {
            return false;
        } else return number.matches("\\d{1,18}");
    }
}
