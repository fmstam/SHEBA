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

import channel.doublefiltersplugins.IlluminationSurface;
import channel.doublefiltersplugins.Scale;
import channel.math.plugins.Max;
import image.conversion.ImageConvertor;
import image.conversion.plugins.HSBtoRGB;
import image.conversion.plugins.RGBtoHSB;
import image.DoubleImage;
import image.ImageException;
import image.filter.ImageFilter;
import image.filter.ImageFilterPlugin;
import image.io.ImageIO;
import math.GeneralMath;
import util.plugin.PipeLine;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bias field remover by fitting a polynomial with a given degree. It is a plug
 * in filter for double image type.
 *
 * @author faroqal-tam
 */
public class PolynomialBiasRemover implements ImageFilterPlugin<DoubleImage, Object> {

    /**
     * The degree of the polynomial
     */
    protected int polynomialDegree;
    protected double downSamplingFactor;
    protected boolean saveIfieldImage;
    protected DoubleImage ifieldImage;

    public DoubleImage getIfieldImage() {
        return ifieldImage;
    }

    public PolynomialBiasRemover(int polynomialDegree, double downSamplingFactor, boolean saveIfieldImage) {
        this.polynomialDegree = polynomialDegree;
        this.downSamplingFactor = downSamplingFactor;
        this.saveIfieldImage = saveIfieldImage;
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



            // build a pipeline to scale image and estmate the polynomial surface 
            PipeLine pipeline = new PipeLine();
            pipeline.addBullet(new Scale(downSamplingFactor)); // scale down
            pipeline.addBullet(new IlluminationSurface(IlluminationSurface.SurfaceType.Polynomial, polynomialDegree)); // estimate the illumination field
            pipeline.addBullet(new Scale((1 / downSamplingFactor))); // scale up back    
            ifieldImage = imageFilter.run(illumniationImage, pipeline); // run the pipeline on the image.

            // divide and normalize
            illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.div, ifieldImage);
            illumniationImage = imageFilter.calculate(illumniationImage, GeneralMath.normalize);
            //illumniationImage=ImageFilter.calculate(illumniationImage, GeneralMath.times, new double[]{255.0});

            if (hsbImage != null) { // we dealt with a colored image
                // if is colored replace the B channel in the HSB image, and then, convert it back to RGB
                hsbImage.setChannel(2, illumniationImage.getChannel(0));
                // discard illumniationImage
                illumniationImage = null;
                // return the image back to RGB
                outImage = (DoubleImage) ImageConvertor.convert(hsbImage, new HSBtoRGB());
            } else { // it was a grayscale return the corrected image
                outImage = imageFilter.calculate(illumniationImage, GeneralMath.times, 255.0);
            }
        } catch (ImageException ex) {
            Logger.getLogger(PolynomialBiasRemover.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (saveIfieldImage) {
            if (hsbImage != null) { // colored, normalize then put in 255
                double max = imageFilter.calculate(ifieldImage, new Max())[0];
                this.ifieldImage = imageFilter.calculate(ifieldImage, GeneralMath.normalize);
                this.ifieldImage = imageFilter.calculate(this.ifieldImage, GeneralMath.times, 255.0);
            } else { // just return the actual surface
                this.ifieldImage = ifieldImage;
            }
        }
        


        return outImage;
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String description() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
