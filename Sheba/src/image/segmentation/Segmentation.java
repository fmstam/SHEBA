/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image.segmentation;

import channel.ChannelModel;
import channel.DoubleChannel;
import channel.IntegerChannel;
import clustering.GeneralClustering;
import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.IntegerImage;
import math.MatrixMath;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A collection of several segmentation methods.
 *
 * @author faroqal-tam
 */
public class Segmentation {

    // Object vs Background pixels
    

    /**
     * A general segmentation plugin invoker method for segmentation.
     *
     * @param image
     * @param method the plug in
     * @param params additional parameters
     * @return
     * @throws ImageException
     */
    public static IntegerImage segment(ImageModel image, ImageSegmentationPlugin method, Object... params) throws ImageException {
        // only grayscale is required
        if (image.numberOfChannels() > 1) {
            throw new ImageException("GRAYSCALE_REQUIRED");
        }
        return method.segment(image, params);
    }
    
    /**
     * Convert the input image into a binary image 
     * @param image
     * @param threshold
     * @return IntegerImage of a single channel of two values
     * @throws ImageException 
     */
    
    public static IntegerImage toBinary(ImageModel image, int threshold) throws ImageException {
        // only grayscale is required
        if (image.numberOfChannels() > 1) {
            throw new ImageException("GRAYSCALE_REQUIRED");
        }
        ChannelModel channelModel = (ChannelModel) image.getChannel(0);
        int rows = channelModel.numRows();
        int cols = channelModel.numCols();
        IntegerImage result = new IntegerImage(1, rows, cols);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (((Number) image.get(r, c)).doubleValue() <= threshold) {
                    result.set(r, c, ImageModel.OBJECT);
                } else {
                    result.set(r, c, ImageModel.BACKGROUND);
                }
            }
        }
        return result;

    }
    /**
     * ************************** plugins *************************************
     */
    public static ImageSegmentationPlugin<ImageModel, Object> isoData = new ImageSegmentationPlugin<ImageModel, Object>() {
        @Override
        public IntegerImage segment(ImageModel input, Object... params) {
            int level = 0;
            IntegerImage binary = null;
            try {
                // calc histogram
                int[] histogram = null;
                if (input instanceof DoubleImage) {
                    histogram = MatrixMath.histogram(((DoubleChannel) input.getChannel(0)).getRawData(), ImageModel.OBJECT);
                } else {
                    histogram = MatrixMath.histogram(((IntegerChannel) input.getChannel(0)).getRawData(), ImageModel.OBJECT);
                }
                // iso data clustering
                level =  GeneralClustering.IsoData(histogram);
                binary = toBinary(input, level);
                
            } catch (ImageException ex) {
                Logger.getLogger(Segmentation.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            return binary;
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getDescription() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        
    };

    
}
