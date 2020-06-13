package consoleView.menu.productsMenu;

import controller.AllAccountZone;
import consoleView.menu.Menu;
import consoleView.menu.SignInMenu;

import java.util.ArrayList;

public class CommentMenu extends Menu {
    private int productId;

    public CommentMenu(Menu parentMenu, int productId) {
        super("Comment", parentMenu);
        this.productId = productId;
        ArrayList<Menu> submenus = new ArrayList<>();
        submenus.add(getAddCommentMenu());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println("\nComments : ");
        System.out.println(AllAccountZone.showProductComments(productId));
        super.execute();
    }

    private Menu getAddCommentMenu() {
        return new Menu("Add Comment", this) {
            @Override
            public void execute() {
                if (AllAccountZone.getCurrentAccount() == null) {
                    System.out.println("You should login first.");
                    new SignInMenu(this).execute();
                } else {
                    String commentText = checkInput("Enter your comment", ".+");
                    AllAccountZone.createComment(commentText, productId);
                }
                this.parentMenu.execute();
            }
        };
    }
}
