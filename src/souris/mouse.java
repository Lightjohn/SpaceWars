/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package souris;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import vaisseau.camp;

/**
 *
 * @author john
 */
public class mouse implements MouseListener{
    camp Republic;
    camp Empire;
    public mouse(camp republic, camp empire ) {
        this.Empire = empire;
        this.Republic = republic;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getButton() == MouseEvent.BUTTON1){
            Republic.contain(me.getX(), me.getY());
            Empire.contain(me.getX(), me.getY());
        }else if(me.getButton() == MouseEvent.BUTTON3){
            Republic.move(me.getX(), me.getY());
            Empire.move(me.getX(), me.getY());
        }else if(me.getButton() == MouseEvent.BUTTON2){
            Republic.tire(me.getX(), me.getY());
            Empire.tire(me.getX(), me.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
}
