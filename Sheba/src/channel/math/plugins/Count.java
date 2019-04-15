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
package channel.math.plugins;

import channel.DoubleChannel;
import channel.math.SimpleChannelMathPlugin;
import util.Region;
import java.awt.Rectangle;

/**
 * Sum math plugin for a given channel considering its ROI
 * @author faroqal-tam
 */
public class Count implements SimpleChannelMathPlugin {

        
        @Override
        public double calc(DoubleChannel inChannel, double... param) {
                double count=0;
                int rows = inChannel.numRows();
                int cols = inChannel.numCols();
                
                double countWhat=0; // what to count param [0]
                double inverted=0;  // if param [1] = 1, count everything except param[0]
                
                if(param!=null)
                    countWhat = param[0];
                if(param.length>=1)
                    inverted=param[1];
                
                
                int r = 0 ;
                int c = 0 ;
                // check the ROI first 
                // case 1: rectanglular ROI
                Region roi = inChannel.getROI();
                if (roi != null && roi.getType() == Region.REGION_TYPE.Rectangle) {// change the rectangle
                    Rectangle rect = roi.getRect();
                    r = rect.y;
                    c = rect.x;
                    rows = rect.height;
                    cols = rect.width;
                }
                if(inverted==0){
                for (int row=r; row < rows; row++) {
                     for (int col=c; col < cols; col++) {
                         if(inChannel.get(row,col,-1)==countWhat)
                             count++;
                     }
                }  
                }
                else
                  for (int row=r; row < rows; row++) {
                     for (int col=c; col < cols; col++) {
                    if(inChannel.get(row,col,-1)!=countWhat)
                             count++;                     
                     }
                 }
                // case 2: non rectangular ROI
                
                return count;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    
