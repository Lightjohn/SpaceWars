/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacewar;

import java.awt.HeadlessException;
import java.util.LinkedList;
import javax.swing.*;
import souris.mouse;
import spacewar.panel;
import vaisseau.camp;
import vaisseau.laser;

/**
 *
 * @author john
 */


public class fenetre extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    int nb_vaisseaux = 5;
    int widthScreen = 1024;
    int heightScreen = 743;
    LinkedList<laser> tir = new LinkedList<laser>();
    camp Republic;
    camp Empire;
    public fenetre() throws HeadlessException {
        Republic = new camp(true, nb_vaisseaux, widthScreen, heightScreen,tir);
        Empire = new camp(false, nb_vaisseaux, widthScreen, heightScreen,tir);
        panel pane = new panel(Republic,Empire,tir);
        pane.addMouseListener(new mouse(Republic,Empire));
        

        this.setTitle("Guerre");
        this.add(pane);
        this.setSize(widthScreen, heightScreen);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}
