/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author taleb
 */
public class LineDrawer {
    
    private class Line{
        private final int x,y,x1,y1;

        public Line(int x, int y, int x1, int y1) {
            this.x = x;
            this.y = y;
            this.x1 = x1;
            this.y1 = y1;
        }

        public int getX() {
            return x;
        }

        public int getX1() {
            return x1;
        }

        public int getY() {
            return y;
        }

        public int getY1() {
            return y1;
        }
        
    }
    
    private final List<Line> lignes;

    public LineDrawer() {
        lignes = new ArrayList<>();
    }
    
    void addLine(int x, int y, int x1, int y1){
        lignes.add(new Line(x, y, x1, y1));
    }
    
    void draw(JComponent component){
        Graphics2D pensil = (Graphics2D) component.getGraphics();
        pensil.setColor(new Color(12, 106, 15));
        pensil.setStroke(new BasicStroke(2));
        pensil.setFont(new Font("", Font.BOLD, Font.BOLD));
        lignes.stream().forEach((line) -> pensil.drawLine(line.getX(), line.getY(), line.getX1(), line.getY1()));
        
    }
    
}
