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

import channel.ChannelFilter;
import channel.DoubleChannel;
import channel.doublefiltersplugins.Gradient;
import channel.doublefiltersplugins.IlluminationSurface;
import channel.doublefiltersplugins.Invert;
import channel.doublefiltersplugins.Scale;
import channel.math.plugins.Count;
import channel.math.plugins.Max;
import channel.math.plugins.Mean;
import channel.math.plugins.Min;
import channel.math.plugins.RMSE;
import channel.math.plugins.STDev;
import channel.math.plugins.Sum;
import channel.math.plugins.TV;
import image.conversion.ImageConvertor;
import image.conversion.plugins.Grayscale;
import image.conversion.plugins.HSBtoRGB;
import image.conversion.plugins.RGBtoHSB;
import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.filter.ImageFilter;
import image.io.ImageIO;
import math.GeneralMath;
import math.PolynomialFactory;
import optimization.DynamicProgramming;
import optimization.Solution;
import util.DoubleLabledPoint;
import util.LabeledPoint;
import util.plugin.Pipeline;
import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Iterative Bias field remover class is an implementation of an article:
 * Retrospective bias field correction in Microscopy images. Al-tam et. al. ....
 * The idea is to detect the background pixels (without segmentation) by using
 * dynamic programming. These pixels are then used to iteratively correct the
 * illumination field with a polynomial 2D function.
 *
 * @author faroqal-tam
 */
public class IterativeBiasRemover_Cri extends PolynomialBiasRemover {

    /**
     * The sampling of the background width factor comparing to the width and
     * height of the input image.
     */
    double sampleWidthFactor;
    /**
     * if the background is darker than the object set this to true
     */
    boolean darkBackground;

    /**
     * Minimum error to stop iterating
     */
    double minimumError;
    
    /**
     * The output path to save the intermediate resulting images
     */
    String outputPath;
    

    public IterativeBiasRemover_Cri(int polynomialDegree, double downSamplingFactor, boolean saveIfieldImage, double sampleWidthFactor, boolean darkBackground, String outputPath){
        super(polynomialDegree, downSamplingFactor, saveIfieldImage);
        this.sampleWidthFactor = sampleWidthFactor;
        this.darkBackground = darkBackground;
        this.outputPath = outputPath;
    }

    @Override
    public DoubleImage filter(DoubleImage inImage, Object... params) {

        DoubleImage outImage = null;
        DoubleImage hsbImage = null; // if is colored, extracting B value need the conversion to HSB.
        DoubleImage gimage = null;
        DoubleImage ifieldImage = null;
        ImageFilter imageFilter = new ImageFilter();
        try {
            // get illumination channel
            if (inImage.numberOfChannels() >= 3) { // RGB colored
                // convert it to HSB
                hsbImage = (DoubleImage) ImageConvertor.convert(inImage, new RGBtoHSB());
                // get the B channel and make an image out of it
                gimage = new DoubleImage(hsbImage.getChannel(2)); // the B channel    
            } else if (inImage.numberOfChannels() == 1) {
                gimage = inImage;
            } else { // unsupported image format exception
                throw new ImageException("UNSUPPORTED_IMAGE_FORMAT");
            }

            gimage = imageFilter.calculate(gimage, GeneralMath.normalize, 255.0);

            DoubleImage lastImage = null;

            // add a small value to avoid log(0);
            double shift = 1; // a small shifting value

            double lastSoothedTV = Double.MAX_VALUE; // infinity
            double sign = 0.0; // keep track of the function 
            int w = 3; // length of the window
            double tvs[] = new double[w]; // keep tracking of last two TVs for smoothing.
            Arrays.fill(tvs, Double.MAX_VALUE);
            // iterate
            for (int it = 1; it <= polynomialDegree; it++) {
                // THE STEPS:
                // downscale the illumination image to save time and memory
                ifieldImage = imageFilter.filter(gimage, new Scale(super.downSamplingFactor));
                ifieldImage = imageFilter.filter(ifieldImage, new channel.doublefiltersplugins.Mean(3));
                int numRows = ifieldImage.numRows();
                int numCols = ifieldImage.numCols();
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.add, shift); // add the shift
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.log); // put in log domain

                DoubleImage originalImage = new DoubleImage(ifieldImage); // keep copy of the original for the polynomial fitting
                // check if it needs to be inverted or not
                if (!darkBackground) {
                    ifieldImage = imageFilter.filter(ifieldImage, new Invert(Math.log(255.0)));
                }
                // put in log domain

                // now find the background samples
                LinkedList<DoubleLabledPoint> backgroundSamples = new LinkedList<DoubleLabledPoint>(); // to store the pixels and their positions
                Solution[] pathes = getBackgroundSamples(ifieldImage, sampleWidthFactor); // get the samples
                // store the pixels
                for (int k = 0; k < pathes.length; k++) {
                    if (pathes[k] == null) {
                        continue;
                    }
                    LabeledPoint[] points = pathes[k].getSolutionPoints();
                    for (LabeledPoint lp : points) {
                        //if(lp.getLabel()==0) continue;
                        backgroundSamples.addFirst(new DoubleLabledPoint(originalImage.get(lp.y, lp.x), lp.y, lp.x));
                    }
                }
                // solve and create polynomial surface
                DoubleLabledPoint[] pixels = backgroundSamples.toArray(new DoubleLabledPoint[backgroundSamples.size()]);
                PolynomialFactory pf = new PolynomialFactory();
                //double[] coefficients = pf.polynomialFit(pixels, it); // fit for the pixels
                double[] coefficients;
                double[][] surface;
                if (it == 1) {
                    coefficients = pf.polynomialFitQRpivot(pixels, it); // fit for the pixels

                    surface = pf.polynomial2Dnormalized(numRows, numCols, it, coefficients); // build a surface by using the coeffcients
                } else {
                    coefficients = pf.polynomialFitQRpivot(pixels, it); // fit for the pixels
                    surface = pf.polynomial2Dnormalized(numRows, numCols, it, coefficients); // build a surface by using the coeffcients

                }
                ifieldImage.setChannel(0, new DoubleChannel(surface)); // replace the channel

                // The bias is generated by fitting to a log image, so reverse the log before scaling
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.exp);

                // in order to stop, we calculate the totall variation 
                
                // scale back the ifield again
                ifieldImage = imageFilter.filter(ifieldImage, new Scale(1.0 / super.downSamplingFactor));

                DoubleImage mag = imageFilter.calculate(ifieldImage, GeneralMath.ceil);
                mag = imageFilter.filter(mag, new Invert());
                mag = imageFilter.filter(mag, new Gradient(ChannelFilter.GRADIENT_TYPE.Magnitude));
                double tv = imageFilter.calculate(mag, new Sum())[0];
                mag = imageFilter.calculate(mag, GeneralMath.normalize, 255.0);
                mag = imageFilter.calculate(mag, GeneralMath.ceil);
                
//                if(!outputPath.endsWith("/")) outputPath=outputPath+"/";
//                ImageIO.saveImage(mag, outputPath+"b" + it + ".tiff");

                
                // put the bias again in log with the actual image 
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.add, shift); // add the shift
                ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.log); // put in log domain

                // put the original image in log domain too
                gimage = imageFilter.calculate(gimage, GeneralMath.add, shift);
                gimage = imageFilter.calculate(gimage, GeneralMath.log);
                // subtract
                gimage = imageFilter.calculate(gimage, GeneralMath.minus, ifieldImage);
                // reverse the log 
                gimage = imageFilter.calculate(gimage, GeneralMath.exp);
                // subtract the shift value
                gimage = imageFilter.calculate(gimage, GeneralMath.subtract, shift);
                // normalize
                gimage = imageFilter.calculate(gimage, GeneralMath.normalize,255.0);

                // debug and save image
                
                //ImageIO.saveImage(gimage, "/Users/faroq/Documents/PhD/dataSet/output/t"+it+".tiff");

                // update the smoothing window
                if (tvs[w - 1] == Double.MAX_VALUE) {
                    tvs[w - 1] = tv;
                } else { //shift
                    for (int k = 0; k < w - 1; k++) {
                        tvs[k] = tvs[k + 1];
                    }
                    tvs[w - 1] = tv;
                }

                // calculate the smoothed tv
                double smoothedTV = 0.0;
                double ew=0.0;
                for (int k = w - 1; k >= 0; k--) {
                    if (tvs[k] == Double.MAX_VALUE) {
                        break;
                    }
                    ew++;
                    smoothedTV += tvs[k];
                }
                smoothedTV=smoothedTV/ew;
                
//                System.out.println("Iteration: " + it + "\t  lastTV:\t" + smoothedTV + "\t TV:\t" + tv);

                if (sign == -1.0) // the function is decreasing
                {
                    if (smoothedTV > lastSoothedTV) { // if the variation increases while the function was decreasing, STOP
                        System.out.print("Converged! best polynomial degree was:"+ (it-1) + "\n");
                        break;
                    }
                }
                
                // update
                lastImage = new DoubleImage(gimage);
                if(lastSoothedTV!=Double.MAX_VALUE)
                    sign= Math.signum(smoothedTV-lastSoothedTV);
                lastSoothedTV=smoothedTV;
                

            }

            //gimage = imageFilter.calculate(gimage, GeneralMath.times, 255.0); 
            if (hsbImage != null) { // we dealt with a colored image
                // if is colored replace the B channel in the HSB domain, and then, convert it back to RGB
                hsbImage.setChannel(2, lastImage.getChannel(0));
                // return the image back to RGB
                outImage = (DoubleImage) ImageConvertor.convert(hsbImage, new HSBtoRGB());
            } else { // it was a grayscale return the corrected image
                outImage = lastImage;//imageFilter.calculate(gimage, GeneralMath.times, 255.0);
            }
        } catch (ImageException ex) {
            Logger.getLogger(PolynomialBiasRemover.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (saveIfieldImage) {  // do we need to save the illumination field

            // use the polynomial to test the remaining illumination varation in the out image
            // put in grayscale if needed
            if (hsbImage != null) {
                this.ifieldImage = (DoubleImage) ImageConvertor.convert(outImage, new Grayscale(), ImageConvertor.GRAYSCALE_WEIGHTS_BT709);
            }
            Pipeline pipeline = new Pipeline();
            pipeline.addBullet(new Scale(downSamplingFactor)); // scale down
            pipeline.addBullet(new IlluminationSurface(IlluminationSurface.SurfaceType.Polynomial, 3)); // estimate the illumination field
            pipeline.addBullet(new Scale((1 / downSamplingFactor))); // scale up back    

            this.ifieldImage = imageFilter.run(outImage, pipeline); // run the pipeline on the image.

        }
        return outImage;
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
