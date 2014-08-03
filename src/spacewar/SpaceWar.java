/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacewar;

import spacewar.fenetre;

/**
 *
 * @author john
 */
public class SpaceWar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int timeImage = 20; //In order to have 50fps -> 1 image every 20ms
        fenetre a = new fenetre();
        a.setVisible(true);
        long begin, end;
        while(true){
            try {
                begin = System.currentTimeMillis();
                a.repaint();
                end = System.currentTimeMillis();
                if (end - begin < timeImage) {
                    Thread.sleep(timeImage-(end - begin));
                }
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
}
