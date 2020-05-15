import model.DataBase;
import view.CommandProcessor;

public class Main {

    public static void main(String[] args) {
        new DataBase();
        CommandProcessor.runMenus();
    }
}
