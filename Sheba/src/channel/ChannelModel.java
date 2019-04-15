/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel;

import util.Region;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * A skeletal model of the channel.
 * @author faroqal-tam
 */
public interface ChannelModel <T> {
     public Number getSafe(int r, int c, Number border);
     public void setSafe(int r, int c, Number value);
     //public Number [] getNeighborsVector(int row, int col, int width);
     //public Number[][] getNeighborsMatrix(int r, int c, int n);
     public ChannelModel <T> getSubChannel(int startR, int startC, int endR, int endC);
     //public T [][] getInternalMatrix();
     public int numRows();
     public int numCols();
     public char getCode();
     public Region getROI();
     public void setROI(Region roi);
     public void setROI(Rectangle rect);
     public void setROI(int r, int c, int lr, int lc);
     public void setROI(LinkedList<Point> pointList);
     public void clearROI();
     
     
}
