package view;

public class Validation {

    public static boolean validateNames(String name) {
        return name != null;
    }

    public static boolean validateEmail(String email) {
        if (email == null) {
            return false;
        } else return email.matches(".+@.+\\.[a-zA-Z]{2,3}");
    }

    public static boolean validateInteger(String number) {
        if (number == null) {
            return false;
        } else return number.matches("\\d{1,9}");
    }

    public static boolean validateLong(String number) {
        if (number == null) {
            return false;
        } else return number.matches("\\d{1,18}");
    }

    public static boolean validateDate(String date) {
        if (date == null) {
            return false;
        } else return date.matches("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}$");
    }

    public static boolean validatePercent(String number) {
        if (validateInteger(number)) {
            int num = Integer.parseInt(number);
            return num > 0 && num < 100;
        }
        return false;
    }
}
