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

import channel.doublefiltersplugins.Mean;
import image.DoubleImage;
import image.ImageException;
import image.filter.ImageFilter;
import image.filter.plugins.WatershedTransform;
import image.io.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author faroq
 */
public class ClusteringTester {
    public static void main(String[] args) {
         try {
             DoubleImage inImage= new DoubleImage(ImageIO.loadImage("/Users/faroq/Documents/PhD/dataSet/input/AB.png"));
             ImageFilter imageFilter= new ImageFilter();
             
             
             inImage= imageFilter.filter(inImage, new Mean(10));
             ImageIO.saveImage(inImage, "/Users/faroq/Documents/PhD/dataSet/output/1-24_mean.tif");
             DoubleImage lines= imageFilter.filter(inImage, new WatershedTransform());
             ImageIO.saveImage(lines, "/Users/faroq/Documents/PhD/dataSet/output/1-24.tif");
         } catch (ImageException ex) {
             Logger.getLogger(TestWaterShed.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
