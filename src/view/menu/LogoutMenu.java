package view.menu;

public class LogoutMenu extends Menu {

    public LogoutMenu(Menu parentMenu) {
        super("Logout", parentMenu);
    }

    @Override
    public void execute() {
        //things for logout
        System.out.println("logout successfully");
        this.parentMenu.execute();
    }
}
