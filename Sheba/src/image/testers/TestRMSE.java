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
import channel.math.BiChannelMathPlugin;
import channel.math.plugins.Mean;
import channel.math.plugins.RMSE;
import channel.math.plugins.STDev;
import channel.math.plugins.Sum;
import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.IntegerImage;
import image.conversion.ImageConvertor;
import image.conversion.plugins.Grayscale;
import image.filter.ImageFilter;
import image.filter.plugins.IterativeBiasRemover;
import image.io.ImageIO;
import image.segmentation.Segmentation;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import math.GeneralMath;

/**
 * This class used to collect the results TestCVed by a given method
 * @author faroq
 */
public class TestRMSE {
public static void main(String[] args) {
        try {
            
            String originalpath = "/Users/faroq/Documents/PhD/dataSet/seg_gt/";
            String inpath = "/Users/faroq/Documents/PhD/dataSet/rs/iter/";
            String outpath = "/Users/faroq/Documents/PhD/dataSet/output/";
            // parameters
           
            File[] files;
            File directory = new File(inpath);
            
            if (directory.isDirectory()) {
                files = directory.listFiles();
            } else {
                files = new File[1];
                files[0] = directory;
            }
            
            

            System.out.println("Image\tobserved CV\tcorrected CV");
            int length = files.length;
            ImageFilter imageFilter= new ImageFilter();
            for (int i = 0; i < length; i++) {

                String currfileString = files[i].getName();
                if(currfileString.equals(".DS_Store")) continue;
                String currfileabsString = files[i].getAbsolutePath();
               
                String currfilecorrString = outpath + currfileString + "_cor.tiff";
                String currfileResString = outpath + currfileString + "_res.tiff";
               

                //System.out.println("Procssing file:" + currfileabsString + "\t" + i + " of " + length);

                String []imageno=currfileString.split("_"); // image name is of the formate sourceNo-PolynomialDegree.tiff
                File originalImageFile= new File(originalpath+imageno[0]+".tiff");
  
                ImageModel im = ImageIO.loadImage(currfileabsString);
                DoubleImage observed = new DoubleImage(im);
                DoubleImage originalImage= new DoubleImage(ImageIO.loadImage(originalImageFile));
                
                // do segmentation of the observed
                 Segmentation s = new Segmentation();
                 IntegerImage integerImage = s.segment(im, Segmentation.isoData);
                 
                // calculate the rmse between them  
                double rmse= imageFilter.calculate(new DoubleImage(integerImage), originalImage, new RMSE())[0];
                
//                Scale s = new Scale(0.125); // a scaler to downscale the images
//                IlluminationSurface is= new IlluminationSurface(IlluminationSurface.SurfaceType.Polynomial, 5);
//                 // for visulation sake, fit the images to a polynomial of degree 5
//                DoubleImage originalIF=imageFilter.filter(originalImage, s);
//                originalIF=imageFilter.filter(originalIF,is);
//                
//                DoubleImage observedIF= imageFilter.filter(observed, s);
//                observedIF=imageFilter.filter(observedIF, is);
//                
//                originalImage= imageFilter.filter(originalImage, s);
//                
//                observed= imageFilter.filter(observed, s);
//                
//                originalImage= imageFilter.filter(originalImage, new Append(Append.AppendingType.Horizontal), new DoubleImage[]{originalIF,observed,observedIF});
//                ImageIO.saveImage(originalImage, currfileResString);
//                // scale the observedIF back to the original size
//                is.setPolyDegree(8);
//                observedIF= imageFilter.filter(observedIF, is);
//                // the ground truth image number is the first number of the processed image
//                
                  
                System.out.print(currfileString);
                System.out.println("\t"+(rmse) );
                System.gc();
            }
        }catch (ImageException ex) {
            Logger.getLogger(TestBiasCorrection.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    }
