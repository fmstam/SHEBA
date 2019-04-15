/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import util.LabeledPoint;
import java.awt.Point;

/**
 *  A general class for a double data point (can be a gray scale pixel)
 * @author faroqal-tam
 */
public class DoubleLabledPoint extends LabeledPoint{
    
    double value;

    public DoubleLabledPoint(double value, int label) {
        super(label);
        this.value = value;
    }

    public DoubleLabledPoint(double value, int label, Point p) {
        super(label, p);
        this.value = value;
    }

    public DoubleLabledPoint(double value, int label, int x, int y) {
        super(label, x, y);
        this.value = value;
    }
  public DoubleLabledPoint(double value, int x, int y) {
        super (x, y);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    

    
}
