/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Point;

/**
 *
 * @author faroqal-tam
 */
public class LabeledPoint extends Point {
    
    int label;

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public LabeledPoint(int label) {
        this.label = label;
    }

    public LabeledPoint(int label, Point p) {
        super(p);
        this.label = label;
    }

    public LabeledPoint(int label, int x, int y) {
        super(x, y);
        this.label = label;
    }
    public LabeledPoint( int x, int y) {
        super(x, y);
        this.label = -1; 
    }

    @Override
    public boolean equals(Object obj) {
       LabeledPoint lp= (LabeledPoint) obj;
       return (lp.x==x && lp.y==y);
    }

   
    
    
}
