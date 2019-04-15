/*
 * This file is to be used only by the reviewers of IEEE TPAMI for assessing the IIRIC article. 
    
 06/2014
 */
package image.testers;

import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.filter.ImageFilter;
import image.filter.plugins.IterativeBiasRemover_Cri;
import image.filter.plugins.IterativeBiasRemover_Cri_PB;
import image.filter.plugins.PolynomialBiasRemover;
import image.filter.plugins.WatershedBiasRemover;
import image.io.ImageIO;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import math.GeneralMath;

/**
 * This is the main class to test the implementation of the method described in
 * IIRIC article. It is implemented within a package "sheba", which is necessary
 * to run this application. A copy of this package (sheba.jar) is accompanied
 * the project of this class. Besides these external package files are required:
 * - ejml-0.23.jar
 * - jai_core.jar
 * - jai_codec.jar
 * - jama-1.0.2.jar
 * - mlibwrapper_jai.jar
 * 
 * If NetBeans was not able to find them, please do the
 * following: 
 * 1- Right click on the "Library" folder in the IIRIC project tree in NetBeans.
 * 2- Choose "Add Jar/Folder". 
 * 3- Locate the them in your computer and click choose.
 *
 * @author XXXXX
 */
public class IIRIC {

    public static void main(String[] args) {

        // path of images
        // grayscale images are expecte
        String inpath = "/Users/faroq/Documents/testCIDRE/input/";
        // to test the provied test set uncomment this line
        //inpath = "input/";
        String outpath = "/Users/faroq/Documents/testCIDRE/output/";
            
            
        File[] files;
        File directory = new File(inpath);
        if (directory.isDirectory()) {
            files = directory.listFiles();
        } else {
            files = new File[1];
            files[0] = directory;
        }

        int length = files.length;
        for (int i = 0; i < length; i++) {
            try {
                String currfileString = files[i].getName();
                String currfileabsString = files[i].getAbsolutePath();
                if (currfileString.equals(".DS_Store")) {
                    continue;
                }

                String currfilecorrString = outpath + currfileString.substring(0,currfileString.indexOf("."))+"_clean_"+ currfileString.substring(currfileString.indexOf("."),currfileString.length());
                System.out.println("Procssing file:" + currfileabsString + "\t" + i + " of " + length);
                
                // load the observed image
                ImageModel im = ImageIO.loadImage(currfileabsString);
                DoubleImage inImage = new DoubleImage(im);

                ImageFilter imageFilter = new ImageFilter();

                // params for the method
                int d_max = 4; // 15 iterations
                double downscaleFacotr = 0.25; // scale image down to speed up
                boolean saveBiasImage = true;   // to keep a copy of the recovred bias
                double samplingFactor = 0.05;   // the sampling factor of the sectioning approach of the article
                boolean darkBackground = true; // whether the image has darkback ground 

                IterativeBiasRemover_Cri_PB p = new IterativeBiasRemover_Cri_PB(d_max, downscaleFacotr, saveBiasImage, samplingFactor, darkBackground, outpath);

                // to test the watershed sampling, comment (IterativeBiasRemover_Cri p =  ......) line and uncomment the follong two lines
                //int smoothingWidth = 3;
                 //WatershedBiasRemover p = new WatershedBiasRemover(d_max, downscaleFacotr, saveBiasImage, smoothingWidth, false,outpath);
                 
                
                // General poly
                //PolynomialBiasRemover p = new PolynomialBiasRemover(15, downscaleFacotr, true);
                // Correcte the source image
                DoubleImage corr = imageFilter.filter(inImage, p);
                corr = imageFilter.calculate(corr, GeneralMath.normalize, 255.0); // normalize
                ImageIO.saveImage(corr, currfilecorrString); // save the result

                // clean up
                System.gc();
                
            } catch (ImageException ex) {
                Logger.getLogger(IIRIC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
