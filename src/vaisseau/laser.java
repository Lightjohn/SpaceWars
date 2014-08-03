/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vaisseau;

/**
 *
 * @author john
 */
public class laser {
    public double posDebutx;//postion debut du laser
    public double posDebuty;
    public double posFinx;//position fin du laser
    public double posFiny;
    public int butX;//dernier position connue du laser
    public int butY;
    public int celerite = 8;//vitesse de deplacement
    int taille = 8;//taille de l'engin ^^
    double distx;
    double disty;
    short signex,signey;
    public boolean grosLaser;
    public short temps = 20; //duree de vie, distance 
    public boolean estRepublic;
    int rand;
    
    public laser(int x, int y, int objX,int objY,boolean grosLaser, boolean estRepublic) {
        rand =(int)(Math.random()*60)-30;
        posDebutx = x;
        posDebuty = y;
        if (grosLaser) {
            posFinx = objX;
            posFiny = objY;
        } else {
            posFinx = x;
            posFiny = y;
        }
        butX = objX +rand;
        butY = objY +rand;
        distx = butX - posDebutx;
        disty = butY - posDebuty;
        if(distx>0){signex = 1;}else{signex=-1;}
        if(disty>0){signey = 1;}else{signey=-1;}
        this.grosLaser = grosLaser;
        this.estRepublic = estRepublic;
        
    }
    
    public void calcDeplacement(){
        if(Math.abs(distx)> Math.abs(disty)){
            posDebutx += signex*celerite;
            posDebuty += signey*celerite*Math.abs(disty/distx);
            posFinx = posDebutx + signex*taille;
            posFiny = posDebuty + signey*taille*Math.abs(disty/distx);
                    
        }else{
            posDebutx += signex*celerite*Math.abs(distx/disty);
            posDebuty += signey*celerite;
            posFinx = posDebutx + signex*taille*Math.abs(distx/disty);
            posFiny = posDebuty + signey*taille;
        }
        
        //System.out.println((int)posDebutx+" "+ (int)posDebuty+" "+(int)posFinx+" "+(int)posFiny);
    }
    
}
