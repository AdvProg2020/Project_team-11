package view.menu;

public class LogoutMenu extends Menu {

    /*
    this Menu will be add to execute when somebody has signed in
     */
    public LogoutMenu(Menu parentMenu) {
        super("Logout", parentMenu);
    }

    @Override
    public void showAvailableMenus() {
        System.out.println(this.getName());
    }

    @Override
    public void execute() {
        //things for logout
        System.out.println("logout successfully");
        this.parentMenu.execute();
    }
}
