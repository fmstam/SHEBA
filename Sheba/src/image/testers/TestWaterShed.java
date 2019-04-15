/*
 * Copyright (C) 2013 faroq
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

package image.testers;

import channel.ChannelFilter;
import channel.doublefiltersplugins.Gradient;
import channel.doublefiltersplugins.Invert;
import channel.doublefiltersplugins.Mean;
import image.DoubleImage;
import image.ImageException;
import image.filter.ImageFilter;
import image.filter.plugins.WatershedTransform;
import image.io.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;
import math.GeneralMath;

/**
 *
 * @author faroq
 */
public class TestWaterShed {
     public static void main(String[] args) {
         try {
             DoubleImage inImage= new DoubleImage(ImageIO.loadImage("/Users/faroq/Documents/b2.tiff"));
             ImageFilter imageFilter= new ImageFilter();
             //inImage= imageFilter.filter(inImage, new Gradient(ChannelFilter.GRADIENT_TYPE.Magnitude));
              inImage= imageFilter.filter(inImage, new Mean(1));
//             inImage= imageFilter.filter(inImage, new Gradient(ChannelFilter.GRADIENT_TYPE.Magnitude));
//             inImage=imageFilter.calculate(inImage, GeneralMath.normalize,255);
              inImage= imageFilter.filter(inImage, new Invert());

             //ImageIO.saveImage(inImage, "/Users/faroq/Documents/PhD/dataSet/output/1-24_mean.tif");
             DoubleImage lines= imageFilter.filter(inImage, new WatershedTransform());
             //lines= imageFilter.filter(inImage, new Gradient(ChannelFilter.GRADIENT_TYPE.Magnitude));
             //lines = imageFilter.calculate(lines, GeneralMath.times, 255.0); 

             ImageIO.saveImage(lines, "/Users/faroq/Documents/PhD/dataSet/output/1-24.tif");
         } catch (ImageException ex) {
             Logger.getLogger(TestWaterShed.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
