package view.menu.productMenu;

import view.menu.Menu;

import java.util.ArrayList;

public class CommentMenu extends Menu {

    public CommentMenu(Menu parentMenu) {
        super("Compare", parentMenu);
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getAddCommentMenu());
        this.setSubmenus(submenus);
    }

    private Menu getAddCommentMenu() {
        return new Menu("Add Comment", this) {
            @Override
            public void showAvailableMenus() {
                //probably empty
            }

            @Override
            public void execute() {
                //function
                this.parentMenu.execute();
            }
        };
    }
}
