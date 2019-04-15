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
 * Max math plugin for a given channel considering its ROI
 *
 * @author faroqal-tam
 */
public class Min implements SimpleChannelMathPlugin {

    @Override
    public double calc(DoubleChannel inChannel, double... param) {
        double min = Double.MAX_VALUE;
        int rows = inChannel.numRows();
        int cols = inChannel.numCols();


        int r = 0;
        int c = 0;
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
        for (int row = r; row < rows; row++) {
            for (int col = c; col < cols; col++) {
                double current = inChannel.get(row, col, -1);
                if (min > current) {
                    min = current;
                }
            }
        }
        // case 2: non rectangular ROI

        return min;
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
