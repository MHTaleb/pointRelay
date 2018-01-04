/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooting;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author taleb
 */
public class Routeur {
    
    
    
    private final String name;
    
    private final List<Routeur> voisin;
    
    private int x,y;// cordoné

    Routeur(String routName) {
        name = routName;
        voisin = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y; //To change body of generated methods, choose Tools | Templates.
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    void addVoisin(Routeur routeur) {
        if (!voisin.contains(routeur)) {
            voisin.add(routeur);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.x;
        hash = 89 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Routeur other = (Routeur) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return this.name.equals(other.name);
    }

    public boolean checkVoisin(Routeur routeur){
        return this.voisin.contains(routeur);
    }

    @Override
    public String toString() {
        return " "+name+" ";
    }

    Routeur getBestFit() {
        int max = -1;
        Routeur winner = null;
        for (Routeur routeur : voisin) {
            final int currentMax = routeur.getCount();
            if (max<=currentMax) {
                max = currentMax;
                winner = routeur;
            }
        }
        return winner;
    }
    
    public int getCount(){
        return voisin.size();
    }

    Routeur getBestFit(Routeur ignore) {
         int max = -1;
        Routeur winner = null;
        for (Routeur routeur : voisin) {
            final int currentMax = routeur.getCount();
            if (max<=currentMax && !routeur.equals(ignore)) {
                max = currentMax;
                winner = routeur;
            }
        }
        return winner;
    }
    
    
    
}
