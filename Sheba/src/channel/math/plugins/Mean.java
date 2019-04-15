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

/**
 * The mean plugin of the a given channel
 * @author faroqal-tam
 */
public class Mean implements SimpleChannelMathPlugin {

        @Override
        public double calc(DoubleChannel inChannel, double... param) {
           double size=inChannel.numRows()*inChannel.numCols();
           Region roi = inChannel.getROI();
           // case 1: rectangle
                if (roi != null && roi.getType() == Region.REGION_TYPE.Rectangle) {// change the rectangle
                    size=roi.getRect().getHeight()*roi.getRect().getWidth();
                }
           // case 2: non rectangle
                
           return new Sum().calc(inChannel, null)/size;    
                
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

