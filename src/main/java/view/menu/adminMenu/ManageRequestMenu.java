package view.menu.adminMenu;

import controller.AdminZone;
import view.menu.Menu;

import java.util.ArrayList;

public class ManageRequestMenu extends Menu {

    public ManageRequestMenu(Menu parentMenu) {
        super("Manage Request", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getViewRequestDetailsMenu());
        submenus.add(getAcceptRequestMenu());
        submenus.add(getDeclineRequestMenu());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println("\nRequests :");
        System.out.println(AdminZone.showAllRequests());
        super.execute();
    }

    private Menu getViewRequestDetailsMenu() {
        return new Menu("View Details", this) {
            @Override
            public void execute() {
                int requestId = Integer.parseInt(checkInput("Enter Request ID", "\\d+"));
                System.out.println(AdminZone.viewRequestDetails(requestId));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getAcceptRequestMenu() {
        return new Menu("Accept Request", this) {
            @Override
            public void execute() {
                int requestId = Integer.parseInt(checkInput("Enter Request ID", "\\d+"));
                System.out.println(AdminZone.acceptRequest(requestId));
                this.parentMenu.execute();
            }
        };
    }

    private Menu getDeclineRequestMenu() {
        return new Menu("Decline Request", this) {
            @Override
            public void execute() {
                int requestId = Integer.parseInt(checkInput("Enter Request ID", "\\d+"));
                System.out.println(AdminZone.declineRequest(requestId));
                this.parentMenu.execute();
            }
        };
    }
}
