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
import image.ImageException;
import image.filter.ImageFilterPlugin;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.st.Pixel;
import util.st.VSWatershed;

/**
 * Illumination correction using watershed transform for segmentation.
 *
 * @author faroq and António dos Anjos
 */
public class WatershedTransform implements ImageFilterPlugin<DoubleImage, Object> {

    @Override
    public DoubleImage filter(DoubleImage inImage, Object... params) {
        DoubleChannel wshChannel = null;
        try {
            int numRows = inImage.numRows();
            int numCols = inImage.numCols();
            // port to <code> Pixel </code> format
            Pixel[][] imageMatrix = new Pixel[numRows][numCols];
            DoubleChannel gray = inImage.getChannel(0); // asuming a grayscle image
            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {
                    imageMatrix[r][c] = new Pixel(r, c, (int) gray.get(r, c));
                }
            }

            VSWatershed vsw = new VSWatershed(imageMatrix, (int) Math.pow(2, inImage.numberOfChannels()) * 8); // asuming

            int[][] lines = vsw.getWatershedLines();

            wshChannel = new DoubleChannel(numRows, numCols);
            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {
                    wshChannel.set(r, c, (lines[r][c]!=0?255:0));
                }
            }

        } catch (ImageException ex) {
            Logger.getLogger(WatershedTransform.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new DoubleImage(wshChannel);

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
