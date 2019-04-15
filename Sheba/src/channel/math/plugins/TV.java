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

import channel.ChannelFilter;
import channel.DoubleChannel;
import channel.doublefiltersplugins.Gradient;
import channel.math.SimpleChannelMathPlugin;
import math.GeneralMath;

/**
 * The total variation calculator. It is the sum of the absolute values of the gradient magnitude of the input channel.
 * @author faroq
 */
public class TV implements SimpleChannelMathPlugin{

    @Override
    public double calc(DoubleChannel inChannel, double... param) {
        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();

        DoubleChannel outChannel = new DoubleChannel(inChannel);
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if(outChannel.get(r, c)<0)
                    outChannel.set(r,c,0);
                else
                if(outChannel.get(r, c)>255)
                    outChannel.set(r,c,255);
            }
        }
        
        outChannel=ChannelFilter.calculate(outChannel, GeneralMath.normalize,255);
        double tv_l1=0.0;
        double tv_l0=0.0;
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                
                double vG = (inChannel.get(r, c + 1, -1) - inChannel.get(r, c - 1, -1)) / 2.0; //central differences
                double hG = (inChannel.get(r + 1, c, -1) - inChannel.get(r - 1, c, -1)) / 2.0; //central differences
                  
               double difference=Math.sqrt(vG*vG+hG*hG);
               System.out.println(difference);
                 // l1 first norm
                // tv_l1+=Math.abs((inChannel.get(r, c + 1, -1) - inChannel.get(r, c - 1, -1))) + Math.abs((inChannel.get(r + 1, c, 0) - inChannel.get(r, c, 0)));
                 // l0 zero norm
                if(difference>0)
                    tv_l0+=1;
                 
            }
        }     
     return tv_l0;
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
