/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.st;

import javax.media.jai.*;
import java.awt.image.*;
import java.awt.Point;

/**
 *
 * @author aanjos
 */
public class MatrixAsImage extends Image {

    private TiledImage imageGS;

    public MatrixAsImage(Pixel[][] pixelMatrix, int numberOfBands, int dataType) {
        nBands = numberOfBands;
        height = pixelMatrix.length;
        width = pixelMatrix[0].length;
        this.dataType = dataType;

        int[] newData = new int[height * width * nBands];
        int offset;

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                offset = row * width * nBands + column * nBands;
                for (int b = 0; b < nBands; b++) {
                    newData[offset + b] = pixelMatrix[row][column].getIntensity();
                }
            }
        }

        DataBufferInt dbf = new DataBufferInt(newData, width * height);
        SampleModel sm = RasterFactory.createBandedSampleModel(dataType, width, height, nBands);
        ColorModel cm = PlanarImage.createColorModel(sm);
        Raster raster = RasterFactory.createWritableRaster(sm, dbf, new Point(0, 0));

        imageGS = new TiledImage(0, 0, width, height, 0, 0, sm, cm);
        imageGS.setData(raster);
    }

    public MatrixAsImage(int[][] intMatrix, int numberOfBands, int dataType) {
        nBands = numberOfBands;
        height = intMatrix.length;
        width = intMatrix[0].length;
        this.dataType = dataType;



        int[] newData = new int[height * width * nBands];
        int offset;

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                offset = row * width * nBands + column * nBands;
                for (int b = 0; b < nBands; b++) {
                    newData[offset + b] = intMatrix[row][column];
                }
            }
        }

        DataBufferInt dbf = new DataBufferInt(newData, width * height);
        SampleModel sm = RasterFactory.createBandedSampleModel(dataType, width, height, nBands);
        ColorModel cm = PlanarImage.createColorModel(sm);
        Raster raster = RasterFactory.createWritableRaster(sm, dbf, new Point(0, 0));

        imageGS = new TiledImage(0, 0, width, height, 0, 0, sm, cm);
        imageGS.setData(raster);
    }

    public PlanarImage getPlanarImage() {
        return imageGS;
    }
}
