
import dao.ProductDAODatabase;
import gui.MainMenu;
import dao.ProductDaoInterface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dowwi431
 */
public class Administration {
    public static void main(String[] args) {
        ProductDaoInterface dao= new ProductDAODatabase();
        // create the frame instance
        MainMenu menu = new MainMenu(dao);

        // centre the frame on the screen
        menu.setLocationRelativeTo(null);

        // show the frame
        menu.setVisible(true);
    }
}
