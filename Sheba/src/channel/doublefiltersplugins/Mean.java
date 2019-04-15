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
import channel.math.plugins.Sum;
import util.Region;
import java.awt.Rectangle;

/**
 * Mean low pass filter.
 *
 * @author faroqal-tam
 */
public class Mean implements ChannelFilterPlugin<DoubleChannel, Object> {

     private int width;

    public Mean(int width) {
        this.width = width;
    }
     
    @Override
    public DoubleChannel filter(DoubleChannel inChannel, Object... params) {
        
        int rows = inChannel.numRows();
        int cols = inChannel.numCols();
        DoubleChannel outChannel = new DoubleChannel(inChannel.getCode(), rows, cols);
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

        Region rectRegion = new Region(0, 0, 0, 0);
        inChannel.setROI(rectRegion);

        Rectangle rect = rectRegion.getRect();
        // define a calculation object

        // Sum plugin 
        Sum sum = new Sum();

        // use region of interest to calculate the mean in a fast implementation
        for (int row = r; row < rows; row++) {
            // calculate sum of the first neighborhood then add and subtract the first and second column in the neghbors respectively.
            // sum of the first neighbors of point in position (row,0).
            // define a new ROI
            rect.setBounds(-width, row - width, width, row + width); // the region of the first neighbors

            double fullSum = sum.calc(inChannel, null);

            // set the mean for the first pixel in the current row.
            double mean = fullSum / ((2 * width) * (2 * width));
            outChannel.set(row, 0, mean);

            // calculate the sum of the first column in the neighbors rectangle
            rectRegion.getRect().setBounds(-width, row - width, -width + 1, row + width);
            double firstLineSum = sum.calc(inChannel, null);


            // For the other columns, calculate the sum of the last column in the neighbors rectangle and: 
            // fullSum= fullSum+lastLineSum- fistLineSum
            for (int col = c + 1; col < cols; col++) {
                // calc the last column sum
                rect.setBounds(col + width - 1, row - width, col + width, row + width); // the last column in the rect
                double lastLineSum = sum.calc(inChannel, null);

                // update the sum
                fullSum = fullSum + lastLineSum - firstLineSum;
                // set the mean of the current point
                mean = fullSum / ((2 * width) * (2 * width));
                outChannel.set(row, col, mean);

                // calculate the fistLinesum 
                rect.setBounds(col - width, row - width, col - width + 1, row + width);
                firstLineSum = sum.calc(inChannel, null);

            }
        }

        //case 2: non rectangular ROI

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
