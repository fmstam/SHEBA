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

import channel.math.plugins.RMSE;
import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.filter.ImageFilter;
import image.io.ImageIO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import math.GeneralMath;

/**m
 * This class used to collect the results TestCVed by a given method
 * @author faroq
 */
public class TestRMSE_One_VS_All {
public static void main(String[] args) throws IOException {
        try {
            
            String gtpath = "/Users/faroq/Dropbox/dataset_bias/gray/";
            String inpath = "/Users/faroq/Dropbox/dataset_bias/DP/";
            // parameters
           
            File[] files;
            File directory = new File(gtpath);
            File gtDir= new File(inpath );
            File [] originalFiles=gtDir.listFiles();
            if (directory.isDirectory()) {
                files = directory.listFiles();
            } else {
                files = new File[1];
                files[0] = directory;
            }
            
            

            System.out.println("Results of wsd");
          
            BufferedWriter resultFile = new BufferedWriter(new FileWriter("/home/faroq/Dropbox/dataset_bias/WST.txt"));
            int length = files.length;
            ImageFilter imageFilter= new ImageFilter();
            for (int i = 0; i < length; i++) {

                String currfileString = files[i].getName();
                if(currfileString.contains(".DS_Store")) continue;
                String currfileabsString = files[i].getAbsolutePath();
                LinkedList<String> imgs =  new LinkedList<>();
                for(int j=0;j<originalFiles.length;j++){
                    String processedFilename=files[i].getName().split("\\.")[0].toLowerCase(); 
                    String originalFilename=originalFiles[j].getName().split("\\.")[0].toLowerCase();
                    if(processedFilename.equals(originalFilename)){
                        imgs.addFirst(originalFiles[j].getAbsolutePath());
                    }
                }
                    if(imgs.isEmpty()){
                        System.err.println("can not find file for:"+ currfileabsString);
                        System.exit(0);
                    }
                    
                    
                    if(imgs.size()<18) continue;

                double rmse = 0;
               
                double [] rmses =  new double[imgs.size()];
                int j=0;
                for(String img: imgs)     {
  
                ImageModel im = ImageIO.loadImage(currfileabsString);
                ImageModel originalIm= ImageIO.loadImage(img);
                
                DoubleImage processedim= new DoubleImage(im);
                DoubleImage originalim= new DoubleImage(originalIm);
                
                originalim=imageFilter.calculate(originalim, GeneralMath.normalize);
                processedim=imageFilter.calculate(processedim, GeneralMath.normalize);           
                // calculate the rmse between them  
                rmses[j] = imageFilter.calculate(originalim, processedim, new RMSE())[0];
                j= j + 1;
                rmse= rmse +  imageFilter.calculate(originalim, processedim, new RMSE())[0];
                } 
                
                rmse = rmse / (double)imgs.size();
                double stdev = 0;
                for( j = 0; j < imgs.size(); j++){
                    stdev = stdev +((rmses[j] - rmse) * (rmses[j] - rmse));
                }
                 stdev = Math.sqrt(stdev/imgs.size() );
                
                System.out.println(files[j].getName()+"\t"+rmse+"\t"+stdev);
                resultFile.write(files[j].getName()+"\t"+rmse+"\t"+stdev);
                            resultFile.flush();

                
            }
            resultFile.close();
        }catch (ImageException ex) {
            Logger.getLogger(TestBiasCorrection.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    }
