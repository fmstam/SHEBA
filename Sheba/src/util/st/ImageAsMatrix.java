/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util.st;

import javax.media.jai.*;
import javax.media.jai.iterator.*;
import java.awt.image.*;


/**
 *
 * @author aanjos
 */
public class ImageAsMatrix extends Image{
    Pixel[][] imageGS;

    public ImageAsMatrix(PlanarImage pImage){
        SampleModel sm = pImage.getSampleModel();
        RandomIter iterator;

        super.nBands = sm.getNumBands();
        super.width = pImage.getWidth();
        super.height = pImage.getHeight();
        super.dataType = sm.getDataType(); System.out.println("DataType: "+ dataType);
        super.numberOfPossibleIntensities = (int) Math.pow(2, sm.getSampleSize(0));
        imageGS = new Pixel[height][width];

        if (nBands > 1){
            System.err.println("Warning! Not a Grayscale Image!!");
        }

        iterator = RandomIterFactory.create(pImage, null);

        int[] pix = new int[nBands];
        for (int row = 0; row < height; row++){
            for (int column = 0; column < width; column ++){
                iterator.getPixel(column, row, pix);
                imageGS[row][column] = new Pixel(row, column, pix[0]);
            }
        }
    }


    public Pixel[][] getImageMatrix(){
        return imageGS;
    }

    public int getIntensity(int r, int c){
        return imageGS[r][c].getIntensity();
    }

    public double[][] getDataAsDoubleMatrix(){
        double[][] data = new double[imageGS.length][imageGS[0].length];

        for (int r =0; r < imageGS.length; r++){
            for (int c = 0; c < imageGS[0].length; c++){
                data[r][c] = getIntensity(r, c);
            }
        }
        return data;
    }

}
