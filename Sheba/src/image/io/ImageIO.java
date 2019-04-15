/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image.io;

import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.IntegerImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.TiledImage;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

/**
 * Common methods for input/output based on JAI methods.
 *
 * @author faroqal-tam and António dos Anjos
 */
public abstract class ImageIO {

    /**
     * Create a tiledimage given a matrix and a number of bands.
     *
     * @param intMatrix and integer matrix
     * @param numberOfBands number of channels
     * @param dataType
     * @return
     * 
     */
    public static TiledImage createImage(ImageModel image) throws ImageException {
        
        int height=image.numRows();
        int width=image.numCols();
        int nBands=image.numberOfChannels();
       

        // determine the image type
        int type=(image instanceof IntegerImage?DataBuffer.TYPE_BYTE:DataBuffer.TYPE_FLOAT);
        
        // initilaize the tiledimage 
        TiledImage imageGS;
        SampleModel sm = RasterFactory.createBandedSampleModel(DataBuffer.TYPE_BYTE, width, height, nBands);
        ColorModel cm = PlanarImage.createColorModel(sm);
        imageGS = new TiledImage(0, 0, width, height, 0, 0, sm, cm);
        WritableRaster raster = imageGS.getWritableTile(0,0);
     

        // put down the data
        if(type==DataBuffer.TYPE_BYTE){ // integer
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                for (int b = 0; b < nBands; b++) {
                    int value=(Integer)image.get(b, row, column);
                    if(value<0)value=0;
                    if(value>255) value=255;
                   raster.setSample(column, row , b,value);
                }
            }
        }
        }else{ // double
         for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                for (int b = 0; b < nBands; b++) {
                    int value=((Number)image.get(b, row, column)).intValue();
                    if(value<0)value=0;
                    if(value>255) value=255;
                   raster.setSample(column, row , b,value);
                }
            }
        }   
        }
        

        return imageGS;
    }

     public static ImageModel loadImage(String fileString) throws ImageException {
         File file= new File(fileString);
         return loadImage(file);
     }
    /**
     * Load an image from a file.
     *
     * @param file
     * @return shortimage.
     * @author António
     */
    public static ImageModel loadImage(File file) throws ImageException {

        // load it to planarimage
        PlanarImage pImage = JAI.create("fileload", file.getAbsolutePath());
        // get the image model
        SampleModel sm = pImage.getSampleModel();
        ImageModel image=null;
        int nBands = sm.getNumBands();
        int width = pImage.getWidth();
        int height = pImage.getHeight();
        int type=sm.getDataType();
        if(type==DataBuffer.TYPE_BYTE|| type==DataBuffer.TYPE_INT || type==DataBuffer.TYPE_SHORT)
            image = new IntegerImage(nBands, height, width);
        else
            image= new DoubleImage(nBands, height, width);

        // iterate over all pixels and fill to a short image
        RandomIter iterator = RandomIterFactory.create(pImage, null);
        int[] pix = new int[nBands];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                iterator.getPixel(column, row, pix);
                for (int i = 0; i < pix.length; i++) {
                    image.set(i, row, column,  pix[i]);
                }
            }
        }
       
        
        return image;
    }
    
    
    /**
     * Save a given image.
     * @param image
     * @param filepath
     * @throws ImageException 
     */
    public static void saveImage(ImageModel image, File filepath) throws ImageException{
        TiledImage ti = createImage(image);
        JAI.create("filestore", ti, filepath.getAbsolutePath(), "TIFF");
        System.gc();
    }
    
    public static void saveImagepng(ImageModel image, File filepath) throws ImageException{
        TiledImage ti = createImage(image);
        JAI.create("filestore", ti, filepath.getAbsolutePath(), "TIFF");
        System.gc();
    }
    public static void saveImage(ImageModel image,   String pathString) throws ImageException{
        File file= new File(pathString);
        saveImagepng(image, file);
    }
}
