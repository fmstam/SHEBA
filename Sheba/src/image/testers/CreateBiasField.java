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

import channel.doublefiltersplugins.Scale;
import image.DoubleImage;
import image.ImageException;
import image.conversion.ImageConvertor;
import image.conversion.plugins.Grayscale;
import image.filter.ImageFilter;
import image.filter.plugins.BiasFieldGenerator;
import image.io.ImageIO;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import math.GeneralMath;
import math.PolynomialFactory;

/**
 *
 * @author faroq
 */
public class CreateBiasField {

//    
    
    public static void main(String[] args) {
        try {
            // TODO code application logic here
        String inpath = "/Users/faroq/Documents/PhD/dataSet/ifmodels/12image robuste x0,71.tif_gray.tiff";
        String outpath = "/Users/faroq/Documents/PhD/dataSet/output/";
        String model = "/Users/faroq/Documents/PhD/dataSet/biasmodels/6";
        int polynomialDegree=10; // to generate 8 images for each degree
        // parameters

        File[] files;
        File directory = new File(inpath);
        if (directory.isDirectory()) {
            files = directory.listFiles();
        } else {
            files = new File[1];
            files[0] = directory;
        }

        System.out.println("Image\tobserved CV\tTV\tcorrected CV\tTV");
        int length = files.length;
        ImageFilter imageFilter= new ImageFilter();
        DoubleImage image =null;
        DoubleImage polynomialImage;
        
        for (int i = 0; i < length; i++) {

            String currfileString = files[i].getName();
            String currfileabsString = files[i].getAbsolutePath();
            
            // generate polynomial images
            for(int degree=1;  degree<=polynomialDegree; degree++){
            String currfileResString = outpath + currfileString + "_p"+degree+".tiff";
            image = new DoubleImage(ImageIO.loadImage(currfileabsString));
           if(image.numberOfChannels()==3)
               image=(DoubleImage)ImageConvertor.convert(image, new Grayscale());
            image=imageFilter.filter(image, new Scale(0.1250));
            int height= image.numRows();
            int width= image.numCols();
            double [] par= new PolynomialFactory().polynomialFit(image.getChannel(0).getRawData(), height, width, degree);
            System.out.println(Arrays.toString(par));
            BiasFieldGenerator bfg= new BiasFieldGenerator(degree, height, width,new File(model));
            polynomialImage= imageFilter.filter(image, bfg);
            polynomialImage= imageFilter.calculate(polynomialImage, GeneralMath.normalize, 255.0);
            //polynomialImage= imageFilter.calculate(polynomialImage, GeneralMath.add, 50.0); // make minimum be >0
            ImageIO.saveImage(polynomialImage, currfileResString);
           }
        }
    }catch (ImageException ex) {
            Logger.getLogger(TestBiasCorrection.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
}
