/*
 * Copyright (C) 2013 faroqal-tam
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
package channel.doublefiltersplugins;

import channel.ChannelFilterPlugin;
import channel.DoubleChannel;
import math.MatrixMath;
import math.PolynomialFactory;

/**
 *  Illumination surface estimator from the input image
 * @author faroqal-tam
 */
public class Center implements ChannelFilterPlugin<DoubleChannel, Object> {

    
    
     

    @Override
    public DoubleChannel filter(DoubleChannel inChannel, Object... params) {
        int rows = inChannel.numRows();
        int cols = inChannel.numCols();
               
        double mean = MatrixMath.mean(inChannel.getRawData());
        
        DoubleChannel outChannel = new DoubleChannel(inChannel.getCode(), rows, cols);
         
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double newvalue = (inChannel.get(row,col) - mean);
                outChannel.set(row, col, newvalue);
                
                  
            }
        }
        return outChannel;
    
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String description() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
