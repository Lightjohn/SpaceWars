/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import vaisseau.camp;
import vaisseau.laser;
import vaisseau.vaisseau;

/**
 *
 * @author john
 */
public class panel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    camp republic;
    camp empire;
    Image fond;
    Image rep;
    Image emp;
    LinkedList<laser> tir;

    public panel(camp listRepublic, camp listEmpire, LinkedList<laser> tir) {
        this.empire = listEmpire;
        this.republic = listRepublic;
        try {
            fond = ImageIO.read(new File("space.png"));
            rep = ImageIO.read(new File("clonemini.png"));
            emp = ImageIO.read(new File("destroyermini.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tir = tir;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(fond, 0, 0, this);
        republic.drawVaisseaux(g, rep);
        empire.drawVaisseaux(g, emp);
        chercheEnnemi();
        drawTir(g);
    }

    private void drawTir(Graphics g) {
        
        g.setColor(Color.red);
        for (int i = 0; i < tir.size(); i++) {
            laser tmp = tir.get(i);
            //Si on ne tire pas un gros laser
            if (!tmp.grosLaser) {
                tmp.calcDeplacement();
                int mod = tmp.celerite / 2;
                //Si on à pas encore atteint l'objectif
                if (tmp.posDebutx < tmp.butX - mod || tmp.posDebutx > tmp.butX + mod || tmp.posDebuty < tmp.butY - mod || tmp.posDebuty > tmp.butY + mod) {
                    if (isEnnemyTouch(tmp)) {
                        tir.remove(i);
                    }
                    g.drawLine((int) tmp.posDebutx, (int) tmp.posDebuty, (int) tmp.posFinx, (int) tmp.posFiny);
                } else {
                    tir.remove(i);
                }
            } else {
                //on tire le gros laser une fois sur 10
                isEnnemyTouch(tmp);
                if(tmp.estRepublic){
                    g.setColor(Color.blue.darker());
                    for(int a =-3;a<3;a++){
                        //Republique: un gros coup
                        g.drawLine((int) tmp.posDebutx, (int) tmp.posDebuty+a, (int) tmp.butX, (int) tmp.butY);
                    }
                }else{
                    //Empire:  deux gros lasers
                    g.setColor(Color.MAGENTA.darker().darker());
                    for(int a =0;a<3;a++){
                    g.drawLine((int) tmp.posDebutx, (int) tmp.posDebuty+a+25, (int) tmp.butX, (int) tmp.butY);
                    g.drawLine((int) tmp.posDebutx, (int) tmp.posDebuty+a-25, (int) tmp.butX, (int) tmp.butY);
                    }
                }
                
                tmp.temps--;
                if(tmp.temps == 0){
                    tir.remove(i);
                }
            }
        }
        g.setColor(Color.black);
    }
    
    private void chercheEnnemi(){
        republic.chercheEnnemi(empire);
        empire.chercheEnnemi(republic);
    }
    /**
     * Vérifie qu'il y a un ennemi sur le chemin et si oui lui enleve de la vie
     * 
     * Return true si le laser à touché
     * 
     * @param x
     * @param y
     */
    private boolean isEnnemyTouch(laser tmp){
        vaisseau touche = null;
        //si un vaisseau est sur le trajet
        if ((touche = empire.containAndRetrieve((int) tmp.posFinx, (int) tmp.posFiny)) != null 
                || (touche = republic.containAndRetrieve((int) tmp.posFinx, (int) tmp.posFiny)) != null) {
            //si c'est pas un tir de son propre camp
            if (touche.estRepublic && !tmp.estRepublic || !touche.estRepublic && tmp.estRepublic) {
                if (tmp.grosLaser) {
                    touche.life-=2;//CEDI est un DPS
                } else {
                    touche.life-=1;
                }
                return true;
            }
        } 
        return false;
    }
}

