/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image.filter;

import channel.ChannelFilter;
import channel.ChannelFilterPlugin;
import channel.DoubleChannel;
import channel.math.BiChannelMathPlugin;
import channel.math.SimpleChannelMathPlugin;
import util.plugin.PipeLine;
import image.DoubleImage;
import image.ImageException;
import math.SimpleMathPulgin;
import math.VectorMathPlugin;
import math.BiMathPlugin;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Image filtering class.
 *
 * @author faroqal-tam
 */
public class ImageFilter {

    /**
     * The filtering method given an image and a filter of the channels.
     *
     * @param image the input image.
     * @param method the pluging method <code>ChannelFilter</code>
     * @param params additional parameters
     * @see ChannelFilterPlugin
     */
    public DoubleImage filter(DoubleImage image, ChannelFilterPlugin method, Object... params) {
        DoubleImage result = null;
        try {

            // create channels vector
            int numChannels = image.numberOfChannels();
            DoubleChannel[] channels = new DoubleChannel[numChannels];

            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                DoubleChannel inChannel = (DoubleChannel) image.getChannel(i);
                channels[i] = ChannelFilter.filter(inChannel, method, params);
            }

            result = new DoubleImage(channels);


        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return the resulted image
        return result;

    }

    public DoubleImage filter(DoubleImage image, ChannelFilterPlugin method, DoubleImage... params) {
        DoubleImage result = null;
        try {

            // create channels vector
            int numChannels = image.numberOfChannels();
            DoubleChannel[] channels = new DoubleChannel[numChannels];

            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                DoubleChannel inChannel = (DoubleChannel) image.getChannel(i);
                DoubleChannel[] otherChannels = new DoubleChannel[params.length];
                for (int j = 0; j < params.length; j++) {
                    otherChannels[j] = params[j].getChannel(i);
                }

                channels[i] = ChannelFilter.filter(inChannel, method, otherChannels);
            }

            result = new DoubleImage(channels);


        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return the resulted image
        return result;

    }

    public DoubleImage filter(DoubleImage image, ImageFilterPlugin filter, Object... params) {
        return (DoubleImage) filter.filter(image, params);
    }

    public DoubleImage run(DoubleImage image, PipeLine pipeLine) {
        DoubleImage result = null;
        try {

            // create channels vector
            int numChannels = image.numberOfChannels();
            DoubleChannel[] channels = new DoubleChannel[numChannels];
            // use the same pipline

            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                PipeLine copyPipline = new PipeLine(pipeLine);
                DoubleChannel inChannel = image.getChannel(i);
                channels[i] = ChannelFilter.run(inChannel, copyPipline);
            }
            result = new DoubleImage(channels);

        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Calculation of the input image of type double
     *
     * @param image the input image
     * @param method a pulgin vector of type <code> VectorMathPlugin </code>
     * @return
     */
    public DoubleImage calculate(DoubleImage image, VectorMathPlugin method, double... params) {
        DoubleImage result = null;
        try {

            // create channels vector
            int numChannels = image.numberOfChannels();
            DoubleChannel[] channels = new DoubleChannel[numChannels];
            
            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                DoubleChannel inChannel = image.getChannel(i);
                channels[i] = ChannelFilter.calculate(inChannel, method, params);               
            }
            result = new DoubleImage(channels);
            
        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return the resulted image
        return result;
    }

    /**
     * Apply a mathematical between two images.
     *
     * @param image The first image
     * @param method the vector math plugin <code> VectorMathPlugin </code>
     * @param otherImage the second image
     *
     */
    public DoubleImage calculate(DoubleImage image, BiMathPlugin method, DoubleImage otherImage) {
        DoubleImage result = null;
        try {

            // create channels vector
            int numChannels = image.numberOfChannels();
            DoubleChannel[] channels = new DoubleChannel[numChannels];
            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                DoubleChannel inChannel = image.getChannel(i);
                DoubleChannel inChannelOther = otherImage.getChannel(i);
                channels[i] = ChannelFilter.calculate(inChannel, method, inChannelOther);
            }
            result = new DoubleImage(channels);

        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return the resulted image
        return result;
    }
    
    
    /**
     * Apply a given math operation on the image.
     *
     * @param image the input image.
     * @param method a math plug in of type <code> SimpleMathPlugin </code>
     * @return
     * @see SimpleMathPulgin
     *
     */
    public DoubleImage calculate(DoubleImage image, SimpleMathPulgin method) {
        DoubleImage result = null;
        try {

            // create channels vector
            int numChannels = image.numberOfChannels();
            DoubleChannel[] channels = new DoubleChannel[numChannels];
            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                DoubleChannel inChannel = image.getChannel(i);
                channels[i] = ChannelFilter.calculate(inChannel, method);
            }
            result = new DoubleImage(channels);

        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return the resulted image
        return result;
    }

    public double[] calculate(DoubleImage image, SimpleChannelMathPlugin method, double ... params) {
        double[] result = null;
        try {

            // create channels vector
            int numChannels = image.numberOfChannels();
            result = new double[numChannels];
            DoubleChannel[] channels = new DoubleChannel[numChannels];
            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                DoubleChannel inChannel = image.getChannel(i);
                result[i] = method.calc(inChannel,params);
            }


        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return the resulted image
        return result;
    }
    
    public double[] calculate(DoubleImage image1, DoubleImage image2, BiChannelMathPlugin method) {
        double[] result = null;
        try {

            // create channels vector
            int numChannels = image1.numberOfChannels();
            result = new double[numChannels];
            DoubleChannel[] channels = new DoubleChannel[numChannels];
            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                DoubleChannel inChannel1 = image1.getChannel(i);
                DoubleChannel inChannel2= image2.getChannel(i);
                // apply the method
                result[i] = method.calc(inChannel1, inChannel2);
            }


        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return the resulted image
        return result;
    }
    
    public double[] calculate(DoubleImage image1, DoubleImage image2, BiChannelMathPlugin method, double ... param) {
        double[] result = null;
        try {

            // create channels vector
            int numChannels = image1.numberOfChannels();
            result = new double[numChannels];
            DoubleChannel[] channels = new DoubleChannel[numChannels];
            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                DoubleChannel inChannel1 = image1.getChannel(i);
                DoubleChannel inChannel2= image2.getChannel(i);
                // apply the method
                result[i] = method.calc(inChannel1, inChannel2, param);
            }


        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return the resulted image
        return result;
    }
    
    
    public DoubleImage calculate(DoubleImage image, PipeLine pipeLine) {
        DoubleImage result = null;
        try {

            // create channels vector
            int numChannels = image.numberOfChannels();
            DoubleChannel[] channels = new DoubleChannel[numChannels];
            // use the same pipline

            // filter each channel
            for (int i = 0; i < numChannels; i++) {
                PipeLine copyPipline = new PipeLine(pipeLine);
                DoubleChannel inChannel = image.getChannel(i);
                channels[i] = ChannelFilter.calculate(inChannel, copyPipline);
            }
            result = new DoubleImage(channels);

        } catch (ImageException ex) {
            Logger.getLogger(ImageFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
