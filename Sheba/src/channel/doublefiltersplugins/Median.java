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

/**
 * The Median filter for a single double channel as a plug in.
 * 
 * @author faroqal-tam
 */
public class Median implements ChannelFilterPlugin<DoubleChannel, Object> {
private int width;

    public Median(int width) {
        this.width = width;
    }

    @Override
    public DoubleChannel filter(DoubleChannel inChannel, Object... params) {
        
        int rows = inChannel.numRows();
        int cols = inChannel.numCols();
        DoubleChannel outChannel = new DoubleChannel(inChannel.getCode(), rows, cols);
         double[] neighbors = new double[width * width];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int radius = width/2;
              
                for (int a = -radius; a <= radius; a++) {
                    for (int b = -radius; b <= radius; b++) {
                        neighbors[(a + radius) * width + (b + radius)] = inChannel.get(row + a, col + b, -1.0);
                    }
                }
                
                double median = MatrixMath.median(neighbors);
               
                outChannel.set(row, col, median);
                
                  
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