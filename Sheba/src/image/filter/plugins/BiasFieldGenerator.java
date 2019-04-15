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

package image.filter.plugins;

import channel.DoubleChannel;
import image.DoubleImage;
import image.filter.ImageFilterPlugin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import math.PolynomialFactory;

/**
 * Generate a random illumination field for the input image given a polynomial degree
 * @author faroq
 */
public class BiasFieldGenerator  implements ImageFilterPlugin<DoubleImage, Object> {
    /**
     *  The degree of polynomial
     */
    private int polynomialDegree;
    /**
     * The width of the resulted image
     */
    private int width;
    /**
     * The height of the resulted image
     */
    private int height;
    
    /**
     * The parameters file
     */
    private File paramsFile;
    public BiasFieldGenerator(int polynomialDegree, int height, int width, File paramsFile  ) {
        this.polynomialDegree = polynomialDegree;
        this.width = width;
        this.height = height;
        this.paramsFile= paramsFile;
    }
    
    
    @Override
    public DoubleImage filter(DoubleImage inImage, Object... params) {
       DoubleChannel channel= new DoubleChannel(height, width);
       double []paramVector=null;
        // generate random parameters 
        PolynomialFactory pf= new PolynomialFactory();
        
        // read the params file
        try{
        BufferedReader bfr= new BufferedReader(new FileReader(paramsFile));
        String line=null;
        int numLine=1;
        
        while((line = bfr.readLine()) != null){
            if(line.startsWith("#") || line.trim().equals("\n")) continue; // ignot comments
            if(numLine==polynomialDegree){
                String [] tokens= line.split(",");
                paramVector= new double[tokens.length];
                for(int i=0; i<tokens.length;i++){
                    paramVector[i]= Double.parseDouble(tokens[i]);
                }
                    
            }
            numLine++;
        }
        } catch (IOException ex) {
            Logger.getLogger(BiasFieldGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
         for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                int d = 0;
                double intensity = 0;
                for (int k = 0; k <= polynomialDegree; k++) {
                    for (int l = 0; l <= k; l++) {
                        intensity += paramVector[d++] * Math.pow(r, k - l) * Math.pow(c, l);
                    }
                }
                channel.set(r,c,Math.abs(intensity));
            }
        }
        return new DoubleImage(channel);
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String description() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
