/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vaisseau;

/**
 *
 * @author john
 */


public class vaisseau {
    public double posx;
    public double posy;
    public int movPosx;
    public int movPosy;
    public int taillex;
    public int tailley;
    public int milieuX;
    public int milieuY;
    public int life = 100;
    public String pathImage;
    boolean isSelected = false;
    boolean tirAuto = false;
    short nbTir =0;
    public boolean estRepublic;
    public vaisseau(boolean estRepublic,int posx,int posy) {
        this.estRepublic = estRepublic;
        if(estRepublic){
            this.posx = posx;
            this.posy = posy;
            movPosx = posx;
            movPosy = posy;
            milieuX = 75;
            milieuY = 50;
            taillex = 146;
            tailley = 100;
            pathImage = "clonemini.png";
        }else{
            this.posx = posx;
            this.posy = posy;
            movPosx = posx;
            movPosy = posy;
            milieuX = 105;
            milieuY = 48;
            taillex = 197;
            tailley = 100;
            pathImage = "destroyermini.png";
        }
    } 
    
    public double distance(vaisseau autre){
        double a = (autre.posx+autre.taillex/2)-(posx+taillex/2);
        double b = (autre.posy+autre.tailley/2)-(posy+tailley/2);
        return Math.sqrt(a*a +b*b);
    }
    
    public boolean isAlive(){
        return life > 0;
    }
}
