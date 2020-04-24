import view.CommandProcessor;
import model.DataBase;

public class Main {

    public static void main(String[] args) {
        new DataBase();
        CommandProcessor.runMenus();
    }
}
