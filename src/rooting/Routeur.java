/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooting;

import java.util.ArrayList;
import java.util.Hashtable;
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

    public List<Routeur>get2Hop( ){
        ArrayList<Routeur> routeurs2Hop = new ArrayList<>();
        voisin.stream().map((routeur) -> routeur.getVoisins()).forEachOrdered((voisins) -> {
            voisins.stream().filter((currentVoisin) -> (!voisin.contains(currentVoisin) && !routeurs2Hop.contains(currentVoisin))).forEachOrdered((currentVoisin) -> {
                routeurs2Hop.add(currentVoisin);
            });
        });
        return routeurs2Hop;
    }
    
    
    private int getIndiceDescriptor(Routeur routeur) {
        if(voisin.contains(routeur)){
            return routeur.getCount();
        }
        return -1;
    }
    
    boolean process = true;
    
    public Hashtable<String,Routeur> getLocalRelay(){
        if(!process) return null;
        process = false ;
        Hashtable<String,Routeur> localMultiPointRelay = new Hashtable<>();
        List<Routeur> voisins2Hop = this.get2Hop();
        for (Routeur voisin2hop : voisins2Hop) {
            int maxInd = -1;
            Routeur currentElu = null;
            for (Routeur routeur : voisin) {
                int indice = voisin2hop.getIndiceDescriptor(routeur);
                if(indice!=-1 && maxInd <= indice){
                    currentElu = routeur;
                    maxInd = indice;
                }
            }
            if(currentElu != null )localMultiPointRelay.put(currentElu.name,currentElu);
        }
        return localMultiPointRelay;
    }

    public List<Routeur> getVoisins() {
        return voisin;
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
