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

import channel.doublefiltersplugins.Center;
import channel.doublefiltersplugins.Standarize;
import channel.math.plugins.RMSE;
import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.IntegerImage;
import image.filter.ImageFilter;
import image.io.ImageIO;
import image.segmentation.Segmentation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import math.GeneralMath;

/**m
 * This class used to collect the results TestCVed by a given method
 * @author faroq
 */
public class TestRMSE1 {
public static void main(String[] args) throws IOException {
        try {
            
            String originalpath = "/Users/faroq/Dropbox/dataset_bias/gray/";
            String inpath = "/Users/faroq/Dropbox/dataset_bias/new/";
            // parameters
           
            File[] files;
            File directory = new File(inpath);
            File originalDir= new File(originalpath);
            File [] originalFiles=originalDir.listFiles();
            if (directory.isDirectory()) {
                files = directory.listFiles();
            } else {
                files = new File[1];
                files[0] = directory;
            }
            
            

            System.out.println("Results of wsd");
          
            BufferedWriter resultFile = new BufferedWriter(new FileWriter("/Users/faroq/Dropbox/dataset_bias/new.txt"));
            
            
            int length = files.length;
            ImageFilter imageFilter= new ImageFilter();
            for (int i = 0; i < length; i++) {

                String currfileString = files[i].getName();
                if(currfileString.contains(".DS_Store")) continue;
                String currfileabsString = files[i].getAbsolutePath();
                String curroriginalString="";
                for(int j=0;j<originalFiles.length;j++){
                    String processedFilename=files[i].getName().split("\\.")[0].toLowerCase(); 
                    String originalFilename=originalFiles[j].getName().split("\\.")[0].toLowerCase();
                    if(processedFilename.equals(originalFilename)){
                        curroriginalString=originalFiles[j].getAbsolutePath();
                        break;
                    }
                }
                    if(curroriginalString.equals("")){
                        System.err.println("can not find file for:"+ currfileabsString);
                        continue;
                        
                        //System.exit(0);
                    }
                
                
                
                 System.out.print(currfileString + "<-->" + curroriginalString);
               


                
  
                ImageModel im = ImageIO.loadImage(currfileabsString);
                ImageModel originalIm= ImageIO.loadImage(curroriginalString);
                
                DoubleImage processedim= new DoubleImage(im);
                DoubleImage originalim= new DoubleImage(originalIm);
                
                //originalim = imageFilter.filter(originalim, new Center());
                //processedim=imageFilter.filter(processedim, new Center());
                originalim=imageFilter.calculate(originalim, GeneralMath.normalize);
                processedim=imageFilter.calculate(processedim, GeneralMath.normalize);
                
                     
                
                
                 
                // calculate the rmse between them  
                double rmse= imageFilter.calculate(originalim, processedim, new RMSE())[0];
                
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
                  
               
                System.out.println("\t"+(rmse) );
                resultFile.write(files[i].getName()+","+rmse+"\n");
                            resultFile.flush();

                
            }
            resultFile.close();
        }catch (ImageException ex) {
            Logger.getLogger(TestBiasCorrection.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    }
