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

import channel.DoubleChannel;
import channel.doublefiltersplugins.Append;
import channel.doublefiltersplugins.IlluminationSurface;
import channel.doublefiltersplugins.Scale;
import channel.math.plugins.Mean;
import channel.math.plugins.STDev;
import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.conversion.ImageConvertor;
import image.conversion.plugins.Grayscale;
import image.filter.ImageFilter;
import image.filter.plugins.IterativeBiasRemover;
import image.io.ImageIO;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class used to collect the results TestCVed by a given method
 * @author faroq
 */
public class TestCV {
public static void main(String[] args) {
        try {
            String originalpath = "/Users/faroq/Documents/PhD/dataSet/gray/";
            String inpath = "/Users/faroq/Documents/PhD/dataSet/rs2/watershed/";
            String outpath = "/Users/faroq/Documents/PhD/dataSet/output/";
            // parameters
           
            File[] files;
            File directory = new File(inpath);
            File orginalDir= new File(originalpath);
            if (directory.isDirectory()) {
                files = directory.listFiles();
            } else {
                files = new File[1];
                files[0] = directory;
            }
            
            File[]  originalFiles= orginalDir.listFiles();

            System.out.println("Image\tobserved CV\tcorrected CV");
            int length = files.length;
            ImageFilter imageFilter= new ImageFilter();
            for (int i = 0; i < length; i++) {

                String currfileString = files[i].getName();
                String currfileabsString = files[i].getAbsolutePath();
               
                String currfilecorrString = outpath + currfileString + "_cor.tiff";
                String currfileResString = outpath + currfileString + "_res.tiff";
                 if(currfileString.equals(".DS_Store")) continue;

               // System.out.println("Procssing file:" + currfileabsString + "\t" + i + " of " + length);

                ImageModel im = ImageIO.loadImage(currfileabsString);
                DoubleImage observed = new DoubleImage(im);
                DoubleImage originalImage= new DoubleImage(ImageIO.loadImage(originalFiles[i].getAbsolutePath()));
                 
                Scale s = new Scale(0.125); // a scaler to downscale the images
                IlluminationSurface is= new IlluminationSurface(IlluminationSurface.SurfaceType.Polynomial, 5);
                 // for visulation sake, fit the images to a polynomial of degree 5
                DoubleImage originalIF=imageFilter.filter(originalImage, s);
                originalIF=imageFilter.filter(originalIF,is);
                
                DoubleImage observedIF= imageFilter.filter(observed, s);
                observedIF=imageFilter.filter(observedIF, is);
                
                originalImage= imageFilter.filter(originalImage, s);
                
                observed= imageFilter.filter(observed, s);
                
                originalImage= imageFilter.filter(originalImage, new Append(Append.AppendingType.Horizontal), new DoubleImage[]{originalIF,observed,observedIF});
                ImageIO.saveImage(originalImage, currfileResString);
                // scale the observedIF back to the original size
                is.setPolyDegree(8);
                observedIF= imageFilter.filter(observedIF, is);
                double observedImageMean= imageFilter.calculate(observedIF, new Mean())[0];
                double observedImageSTD= imageFilter.calculate(observedIF, new STDev())[0];
                
                  
                System.out.print(currfileString);
                System.out.println("\t"+(observedImageSTD/observedImageMean) );
                System.gc();
            }
        }catch (ImageException ex) {
            Logger.getLogger(TestBiasCorrection.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    }
