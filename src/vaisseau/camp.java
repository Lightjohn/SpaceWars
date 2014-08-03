/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vaisseau;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

/**
 *
 * @author john
 */
public class camp {

    LinkedList<vaisseau> flotte = new LinkedList<vaisseau>();
    int nbVaisseaux;
    int height;
    int witdh;
    boolean estRepublic;
    LinkedList<laser> tir;

    public camp(boolean estRepublic, int nbVaisseaux, int height, int width, LinkedList<laser> tir) {
        this.estRepublic = estRepublic;
        this.height = height;
        this.witdh = width;
        this.nbVaisseaux = nbVaisseaux;
        this.tir = tir;
        init_flotte();
    }

    private void init_flotte() {
        for (int i = 0; i < nbVaisseaux; i++) {
            if (estRepublic) {
                flotte.add(new vaisseau(true, 0, (height / nbVaisseaux) * i));
            } else {
                flotte.add(new vaisseau(false, 800, (height / nbVaisseaux) * i));
            }
        }
    }

    public boolean contain(int x, int y) {
        vaisseau tmp;
        boolean trouve = false;
        for (int i = 0; i < flotte.size(); i++) {
            tmp = flotte.get(i);
            if (!trouve && x > tmp.posx && x < (tmp.posx + tmp.taillex) && y > tmp.posy && y < (tmp.posy + tmp.tailley) && tmp.isAlive()) {
                tmp.isSelected = true;
                trouve = true;
            } else {
                tmp.isSelected = false;
            }
        }
        return trouve;
    }

    public vaisseau containAndRetrieve(int x, int y) {
        vaisseau tmp;
        boolean trouve = false;
        for (int i = 0; i < flotte.size(); i++) {
            tmp = flotte.get(i);
            if (tmp.isAlive()) {
                if (!trouve && x > tmp.posx && x < (tmp.posx + tmp.taillex) && y > tmp.posy && y < (tmp.posy + tmp.tailley)) {
                    return tmp;
                }
            }
        }
        return null;
    }

    public void drawVaisseaux(Graphics g, Image im) {
        g.setColor(Color.green);
        for (int i = 0; i < flotte.size(); i++) {
            vaisseau tmp = flotte.get(i);
            if (tmp.isAlive()) {
                if (tmp.posx < tmp.movPosx - 1 || tmp.posx > tmp.movPosx + 1 || tmp.posy < tmp.movPosy - 1 || tmp.posy > tmp.movPosy + 1) {
                    double a[] = calcDepl2(tmp.posx, tmp.posy, tmp.movPosx, tmp.movPosy);
                    tmp.posx += a[0];
                    tmp.posy += a[1];
                }
                g.drawImage(im, (int) tmp.posx, (int) tmp.posy, null);
                if (tmp.isSelected) {
                    g.drawRect((int) tmp.posx, (int) tmp.posy, tmp.taillex, tmp.tailley);
                }
            }
        }
        g.setColor(Color.black);
    }

    public void tire(int x, int y) {
        for (int i = 0; i < flotte.size(); i++) {
            vaisseau tmp = flotte.get(i);
            if (tmp.isSelected && tmp.isAlive()) {
                tmp.nbTir++;
                if (tmp.nbTir < 10) {
                    tir.add(new laser((int) tmp.posx + tmp.milieuX, (int) tmp.posy + tmp.milieuY, x, y, false,estRepublic));
                } else {
                    tir.add(new laser((int) tmp.posx + tmp.milieuX, (int) tmp.posy + tmp.milieuY, x, y, true,estRepublic));
                    tmp.nbTir = 0;
                }
                //System.out.println((tmp.posx+tmp.milieuX)+" "+(tmp.posy+tmp.milieuY)+" "+ x+" "+ y);
            }
        }
    }

    public void tire2(vaisseau depart, vaisseau fin) {
        int tirMax;
        if(depart.estRepublic){tirMax =100;}else{tirMax = 150;}
        depart.nbTir++;
        if (depart.nbTir < tirMax) {
            if(depart.nbTir%20 ==0){
                tir.add(new laser((int) depart.posx + depart.milieuX, (int) depart.posy + depart.milieuY,
                        (int) fin.posx+fin.taillex/2,(int) fin.posy+fin.tailley/2, false,estRepublic));
            }

        } else {
            tir.add(new laser((int) depart.posx + depart.milieuX, (int) depart.posy + depart.milieuY,
                    (int) fin.posx+fin.taillex/2,(int) fin.posy+fin.tailley/2, true,estRepublic));
            depart.nbTir = 0;
        }
        //System.out.println((tmp.posx+tmp.milieuX)+" "+(tmp.posy+tmp.milieuY)+" "+ x+" "+ y);
    }

    public void move(int x, int y) {
        for (int i = 0; i < flotte.size(); i++) {
            vaisseau tmp = flotte.get(i);
            if (tmp.isSelected && tmp.isAlive()) {
                tmp.movPosx = x - tmp.taillex / 2;
                tmp.movPosy = y - tmp.tailley / 2;
            }
        }
    }

    public LinkedList<vaisseau> getFlotte() {
        return flotte;
    }

    private double[] calcDepl2(double point1x, double point1y, int point2x, int point2y) {
        int deplacement = 2;
        double distx = point2x - point1x;
        double disty = point2y - point1y;
        int signex, signey;
        if (distx > 0) {
            signex = 1;
        } else {
            signex = -1;
        }
        if (disty > 0) {
            signey = 1;
        } else {
            signey = -1;
        }

        double[] out = new double[2];
        if (Math.abs(distx) > Math.abs(disty)) {
            out[0] = signex * deplacement;
            out[1] = signey * deplacement * Math.abs(disty / distx);
        } else {

            out[0] = signex * deplacement * Math.abs(distx / disty);
            out[1] = signey * deplacement;
        }
        return out;
    }

    public void chercheEnnemi(camp adverse) {
        vaisseau allie;
        vaisseau ennemi;
        for (int h = 0; h < flotte.size(); h++) {
            allie = flotte.get(h);
            if (allie.isAlive()) {
                for (int i = 0; i < adverse.flotte.size(); i++) {
                    ennemi= adverse.flotte.get(i);
                    if(allie.distance(ennemi) < 300  && ennemi.isAlive()){
                        allie.tirAuto = true;
                        tire2(allie,ennemi);
                        allie.tirAuto = false;
                    }
                }
            }
        }
    }
}
