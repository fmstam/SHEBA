/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * The region inside and grid (image channel e.g.).
 *
 * @author faroqal-tam
 */
public class Region {
    
    public static enum REGION_TYPE {

        Rectangle, PointList, Empty
    };
    private Rectangle rect; // rectangel
    private LinkedList<Point> pointList; // the positions in a list
    private REGION_TYPE type;

    public Region(Rectangle rect) {
        this.rect = rect;
        this.type = REGION_TYPE.Rectangle;
    }
    
    public Region(int r, int c, int lr, int lc) {
        this.rect = new Rectangle(c, r, lc, lr);
        this.type = REGION_TYPE.Rectangle;
    }
    
    public Region(LinkedList<Point> pointList) {
        this.pointList = pointList;
        this.type = REGION_TYPE.PointList;
    }
    
    public void addPoint(Point point) {
        if (pointList == null) // create it
        {
            pointList = new LinkedList<>();
        }
        pointList.addFirst(point);
    }
    
    public Rectangle getRect() {
        return rect;
    }
    
    public LinkedList<Point> getPointList() {
        return pointList;
    }
    
    public REGION_TYPE getType() {
        return type;
    }
    
    /**
     * Empty the region
     */
    public void reset(){
        rect=null;
        pointList=null;
        type= REGION_TYPE.Empty;
    }
    
    
    public boolean isEmpty(){
        return type==REGION_TYPE.Empty;
    }
}
