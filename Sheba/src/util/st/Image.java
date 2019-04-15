/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util.st;

/**
 *
 * @author aanjos
 */
public class Image {
    int dataType;
    int numberOfPossibleIntensities;
    int height;
    int width;
    int nBands;

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getDataType(){
        return dataType;
    }

    public int getNumberOfPossibleIntensities(){
        return  numberOfPossibleIntensities;
    }

    public int getNumberOfBands(){
        return nBands;
    }
}
