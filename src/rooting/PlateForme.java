/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooting;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author taleb
 */
public class PlateForme {

    public PlateForme(JPanel base, ServiceRooting sr) {
        this.base = base;
        this.sr = sr;
        drawer = new LineDrawer();
    }
    
    
    
    private final JPanel base;
    
    private final LineDrawer drawer;
    
    private final ServiceRooting sr;
    
    public void addRouter(int x,int y){
    
        String routName = JOptionPane.showInputDialog("Veuillez saisir le nom du routeur");
        
        final JLabel jLabel = new JLabel(routName);
        jLabel.setIcon(new ImageIcon(getClass().getResource("rout.png")));
        jLabel.setText(routName);
        
        
        JPopupMenu pm = new JPopupMenu();
        JMenuItem addVoisinMenuItem = new JMenuItem("Ajouter Voisin");
        pm.add(addVoisinMenuItem);
        
        JMenuItem lanceRequeteMenuItem = new JMenuItem("Lancer Requete");
        pm.add(lanceRequeteMenuItem);
        
        
        jLabel.setComponentPopupMenu(pm);
        jLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        
        jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        
        jLabel.setVisible(true);
        jLabel.setLocation(x, y);
        final Routeur routeur = new Routeur(routName);
        routeur.setX(x);
        routeur.setY(y);
        
        sr.addRouter(routName, routeur);
        
        addVoisinMenuItem.addActionListener((ActionEvent al)->{
            int count = sr.getRoutersCount();
            if(count>1){
                List<String> routersListKeys = sr.getRoutersList(routName);
                int selectedOption = JOptionPane.showOptionDialog(null, "Choisir Voisin", "Choisir voisin", 0, 0, null, routersListKeys.toArray(), routersListKeys.get(0));
                System.out.println("selected option = "+selectedOption);
                
                // recuperer le routeur selectioner
                String routerKey = routersListKeys.get(selectedOption);
                Routeur r = sr.getRouterByName(routerKey);
                // ajouter le routeur selection comme voisin
                r.addVoisin(routeur);
                routeur.addVoisin(r);
                
                // dessiner une ligne entre le routeur et son voisin
                int x1 = sr.getRouterLocationX(routersListKeys.get(selectedOption));
                int y1 = sr.getRouterLocationY(routersListKeys.get(selectedOption));
                
                drawer.addLine(x+20, y+20, x1+20, y1+20);
                drawer.draw(base);
            }
        });
        
        
        lanceRequeteMenuItem.addActionListener(al->{
            int count = sr.getRoutersCount();
            if(count>1){
                List<String> routersListKeys = sr.getRoutersList(routName);
                int selectedOption = JOptionPane.showOptionDialog(null, "Choisir Destination", "Choisir Destination", 0, 0, null, routersListKeys.toArray(), routersListKeys.get(0));
                System.out.println("selected option = "+selectedOption);
                // recuperation de la destination
                String routerKey = routersListKeys.get(selectedOption);
                Routeur destination = sr.getRouterByName(routerKey);
                // calcul
                String resolve = sr.resolve(routeur,destination);
                JOptionPane.showMessageDialog(null, resolve);
                redraw();
            }
        });
        
        base.add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(x, y, -1, -1));
        base.revalidate();
       // base.updateUI();
       // base.repaint();
       
    }
    
    public void redraw(){
        drawer.draw(base);
    }
}
