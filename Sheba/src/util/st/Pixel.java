/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.st;
/**
 *
 * @author aanjos
 */
public class Pixel implements Comparable<Pixel> {

    int intensity;
    int row;
    int column;


    public Pixel(int row, int column, int intensity) {
        this.row = row;
        this.column = column;
        this.intensity = intensity;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setIntensity(int intensity){
        this.intensity = intensity;
    }

    public int getIntensity() {
        return intensity;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public int compareTo(Pixel otherPixel) {
        return this.intensity - otherPixel.intensity;
    }
}
