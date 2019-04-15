/*
 * Copyright (C) 2013 faroqal-tam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package image.filter.plugins;

import channel.DoubleChannel;
import channel.doublefiltersplugins.IlluminationSurface;
import channel.doublefiltersplugins.Invert;
import channel.doublefiltersplugins.Scale;
import channel.math.plugins.Max;
import channel.math.plugins.Mean;
import channel.math.plugins.Min;
import image.conversion.ImageConvertor;
import image.conversion.plugins.Grayscale;
import image.conversion.plugins.HSBtoRGB;
import image.conversion.plugins.RGBtoHSB;
import image.DoubleImage;
import image.ImageException;
import image.filter.ImageFilter;
import math.GeneralMath;
import math.PolynomialFactory;
import optimization.DynamicProgramming;
import optimization.Solution;
import util.DoubleLabledPoint;
import util.LabeledPoint;
import util.plugin.PipeLine;
import java.awt.Point;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.st.Pixel;
import util.st.VSWatershed;

/**
 * Iterative Bias field remover class is an implementation of an article:
 * Retrospective bias field correction in Microscopy images. Al-tam et. al. ....
 * The idea is to detect the background pixels (without segmentation) by using
 * watershed transform. These pixels are then used to iteratively correct the
 * illumination field with a polynomial 2D function.
 *
 * @author faroqal-tam and António dos Anjos
 */
public class WatershedBiasRemover_old extends PolynomialBiasRemover {

    /**
     * The sampling of the background width factor comparing to the width and
     * height of the input image.
     */
    double sampleWidthFactor;
    /**
     * if the background is darker than the object set this to true
     */
    boolean darkBackground;

    public WatershedBiasRemover_old(int polynomialDegree, double downSamplingFactor, boolean saveIfieldImage, double sampleWidthFactor, boolean darkBackground) {
        super(polynomialDegree, downSamplingFactor, saveIfieldImage);
        this.sampleWidthFactor = sampleWidthFactor;
        this.darkBackground = darkBackground;
    }

    @Override
    public DoubleImage filter(DoubleImage inImage, Object... params) {

        DoubleImage outImage = null;
        DoubleImage hsbImage = null; // if is colored, extracting B value need the conversion to HSB.
        DoubleImage illumniationImage = null;
        DoubleImage ifieldImage = null;
        ImageFilter imageFilter = new ImageFilter();
        try {
            // get illumination channel
            if (inImage.numberOfChannels() >= 3) { // RGB colored
                // convert it to HSB
                hsbImage = (DoubleImage) ImageConvertor.convert(inImage, new RGBtoHSB());
                // get the B channel and make an image out of it
                illumniationImage = new DoubleImage(hsbImage.getChannel(2)); // the B channel    
            } else if (inImage.numberOfChannels() == 1) {
                illumniationImage = inImage;
            } else { // unsupported image format exception
                throw new ImageException("UNSUPPORTED_IMAGE_FORMAT");
            }

            DoubleImage orgImage = new DoubleImage(illumniationImage);

                // subtract
            //illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.normalize, 255.0);
            //illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.times,255);
            double mean = 0;
            // add a small value to avoid log(0);
            double shift = 1; // a small shifting value

            orgImage = imageFilter.calculate(orgImage, GeneralMath.add, shift);
            orgImage = imageFilter.calculate(orgImage, GeneralMath.log);
            // iterate
            for (int it = polynomialDegree; it <= polynomialDegree; it++) {
                // THE STEPS:
                // downscale the illumination image to save time and memory
                ifieldImage = imageFilter.filter(illumniationImage, new Scale(super.downSamplingFactor));
                ifieldImage = imageFilter.filter(ifieldImage, new channel.doublefiltersplugins.Mean(3));
                int numRows = ifieldImage.numRows();
                int numCols = ifieldImage.numCols();
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.add, shift); // add the shift
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.log); // put in log domain

                DoubleImage originalImage = new DoubleImage(ifieldImage); // keep copy of the original for the polynomial fitting
                // check if it needs to be inverted or not
                if (!darkBackground) {
                    ifieldImage = imageFilter.filter(ifieldImage, new Invert(Math.log(255.0+shift)));
                }
                // put in log domain

                // now find the background samples using watershed
                // solve and create polynomial surface
                DoubleLabledPoint[] pixels = getlines(ifieldImage, originalImage);
                PolynomialFactory pf = new PolynomialFactory();
                double[] coefficients = pf.polynomialFit(pixels, it); // fit for the pixels
                double[][] surface = pf.polynomial2D(numRows, numCols, it, coefficients); // build a surface by using the coeffcients
                ifieldImage.setChannel(0, new DoubleChannel(surface)); // replace the channel

                // Standraize by subtracting the mean 
                // subtract the mean
                mean = imageFilter.calculate(ifieldImage, new Mean())[0]; // single channel
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.subtract, mean);
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.exp);
                // scale back the ifield again
                ifieldImage = imageFilter.filter(ifieldImage, new Scale(1.0 / super.downSamplingFactor));
                // subtract the ifield image from the original
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.add, shift); // add the shift
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.log); // put in log domain

                // put original in log domain too
                illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.add, shift);
                illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.log);
                // subtract
                illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.minus, ifieldImage);
                // reverse the log by the exp 
                illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.exp);
                // subtract the shift value
                illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.subtract, shift);
                // normalize
                illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.normalize, 255.0);
                //System.gc();
            }
            if (hsbImage != null) { // we dealt with a colored image
                // if is colored replace the B channel in the HSB domain, and then, convert it back to RGB
                hsbImage.setChannel(2, illumniationImage.getChannel(0));
                // return the image back to RGB
                outImage = (DoubleImage) ImageConvertor.convert(hsbImage, new HSBtoRGB());
            } else { // it was a grayscale return the corrected image
                outImage = illumniationImage;//imageFilter.calculate(illumniationImage, GeneralMath.times, 255.0);
            }
        } catch (ImageException ex) {
            Logger.getLogger(PolynomialBiasRemover.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (saveIfieldImage) {  // do we need to save the illumination field

            // use the polynomial to test the remaining illumination varation in the out image
            // put in grayscale if needed
//            if (hsbImage != null) {
//                this.ifieldImage = (DoubleImage) ImageConvertor.convert(outImage, new Grayscale(), ImageConvertor.GRAYSCALE_WEIGHTS_BT709);
//            }
//            PipeLine pipeline = new PipeLine();
//            pipeline.addBullet(new Scale(downSamplingFactor)); // scale down
//            pipeline.addBullet(new IlluminationSurface(IlluminationSurface.SurfaceType.Polynomial, 3)); // estimate the illumination field
//            pipeline.addBullet(new Scale((1 / downSamplingFactor))); // scale up back    
//
//            this.ifieldImage = imageFilter.run(outImage, pipeline); // run the pipeline on the image.
            ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.exp);
                // subtract the shift value
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.subtract, 1);
                // normalize
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.normalize, 255.0);
        
            this.ifieldImage=ifieldImage;

        }
        return outImage;
    }

    /**
     * A helper function to convert the watershed lines to a vector or pixels.
     *
     * @param inImage
     * @return
     */
    private DoubleLabledPoint[] getlines(DoubleImage inImage, DoubleImage orginalImage) {
        LinkedList<DoubleLabledPoint> linesList = null;
        try {
            int numRows = inImage.numRows();
            int numCols = inImage.numCols();
            Pixel[][] imageMatrix = new Pixel[numRows][numCols];
            DoubleChannel gray = inImage.getChannel(0); // asuming a grayscle image
            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {
                    imageMatrix[r][c] = new Pixel(r, c, (int) gray.get(r, c));
                }
            }
            VSWatershed vsw = new VSWatershed(imageMatrix, (int) Math.pow(2, inImage.numberOfChannels()) * 8); // asuming
            int[][] lines = vsw.getWatershedLines();
            linesList = new LinkedList<>();

            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {
                    if (lines[r][c] != 0) {
                        linesList.addLast(new DoubleLabledPoint(orginalImage.get(r, c), r, c));
                    }
                }
            }

        } catch (ImageException ex) {
            Logger.getLogger(WatershedBiasRemover.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linesList.toArray(new DoubleLabledPoint[linesList.size()]);
    }

    /**
     * Find the best paths in the image samples. Dynamic programming is used to
     * find the best paths in the background
     *
     * @param aimage
     * @return
     */
    public Solution[] getBackgroundSamples(DoubleImage image, double sampleWidth) throws ImageException {

        if (image.numberOfChannels() > 1) {// exception, accept only single channeled images
            throw new ImageException("GRAYSCALE_REQUIRED");
        }

        // get the channel
        DoubleChannel channel = image.getChannel(0);
        int numRows = channel.numRows();
        int numCols = channel.numCols();

        // find the vertical pathes
        int verticalSpacing = 0;
        int horizontalSpacing = 0;

        if (sampleWidth == -1) {
            sampleWidth = 0.05; // default
        }
        verticalSpacing = (int) (numRows * 0.05);
        horizontalSpacing = (int) (numCols * 0.05);

        Solution[] verticalPathes = getPathes(channel, verticalSpacing);
        // find horizontal pathes
        DoubleChannel transChannel = new DoubleChannel(channel.transpose());
        Solution[] horizontalPathes = getPathes(transChannel, horizontalSpacing);

        for (Solution s : horizontalPathes) {
            if (s == null) {
                continue;
            }
            // swap x and y to reverse the transpose
            for (Point p : s.getSolutionPoints()) {
                int temp = p.x;
                p.x = p.y;
                p.y = temp;
            }

        }
        // the union of the lines are the background samples
        return util.Utils.union(verticalPathes, horizontalPathes);
    }

    private Solution[] getPathes(DoubleChannel channel, int sampleWidth) {

        int numRows = channel.numRows();
        int numCols = channel.numCols();

        int lines = numCols / sampleWidth;
        Solution[] pathes = new Solution[lines];
        DynamicProgramming dp = new DynamicProgramming();
        int sindex = 0;
        for (int i = 0; i < numCols - sampleWidth; i += sampleWidth) {
            double[][] mat = channel.getMat(0, i, numRows, i + sampleWidth, -1);

            //calculate the solution
            Solution solution = dp.cheapestPath(mat);
            Point[] solPoints = solution.getSolutionPoints();
            for (Point p : solPoints) {
                p.x += (sindex) * sampleWidth;
            }
            pathes[sindex++] = solution;
        }
        return pathes;
    }
}
