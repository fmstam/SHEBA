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

package channel.math.plugins;

import channel.DoubleChannel;
import channel.math.BiChannelMathPlugin;

/**
 * Adjusted R2
 * @author faroq
 */
public class adjR2 implements BiChannelMathPlugin{

    @Override
    
    public double calc(DoubleChannel inChannel1, DoubleChannel inChannel2, double ... param) {
        int numRows= Math.min(inChannel1.numRows(), inChannel2.numRows());
        int numCols= Math.min(inChannel1.numCols(), inChannel2.numCols());
        
        double mu = 0.0;
        for(int r=0;r<numRows;r++)
            for(int c=0;c<numCols;c++){
                mu = mu + inChannel1.get(r,c,0);
            }
        mu = mu /(numRows*numCols);
        double ss = 0;
        double tot = 0;
        for(int r=0;r<numRows;r++)
            for(int c=0;c<numCols;c++){
                ss = ss +  Math.pow(inChannel1.get(r, c, 0)-inChannel2.get(r, c,0),2);
                tot = tot +  Math.pow(inChannel1.get(r, c, 0) - mu,2);
                        
            }
        // R^2
        double r2 = 1 - ss/tot;
        double adjr2 = r2 - (1 - r2) * (param[0]/((numRows*numCols)-param[0]-1));
        return r2;
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String description() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double calc(DoubleChannel inChannel1, DoubleChannel inChannel2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
