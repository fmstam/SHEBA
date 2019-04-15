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
package image.conversion.plugins;

import channel.ChannelModel;
import channel.DoubleChannel;
import image.conversion.ImageConversionPlugin;
import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.IntegerImage;
import java.awt.Color;

/**
 * HSB to RGB image convertor
 *
 * @author faroqal-tam
 */
public class HSBtoRGB implements ImageConversionPlugin<ImageModel, Object> {

    @Override
    public ImageModel convert(ImageModel input, Object... params) throws ImageException {

        int numChannels = input.numberOfChannels();
        if (numChannels < 3) {
            throw new ImageException("MAGE_CONVERSION_ERROR", 3);
        }

        int numRows = ((ChannelModel) (input.getChannel(0))).numRows();
        int numCols = ((ChannelModel) (input.getChannel(0))).numCols();

        ChannelModel[] channels = new ChannelModel[3]; // ignor the alpha image or any channel after 3;

        for(int i=0;i<3;i++)
            channels[i]=new DoubleChannel(numRows, numCols);
        ChannelModel channelH = (ChannelModel) input.getChannel(0); // red channel
        ChannelModel channelS = (ChannelModel) input.getChannel(1); // green channel
        ChannelModel channelB = (ChannelModel) input.getChannel(2); // blue channel
         // Color color
        Color color =null;
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                // convert from RGB to HSB using java Color class

                float h = channelH.getSafe(r, c, -1).floatValue();
                float s = channelS.getSafe(r, c, -1).floatValue();
                float b = channelB.getSafe(r, c, -1).floatValue();
                // convert to color
                color = new Color(Color.HSBtoRGB(h, s, b));
                
                channels[0].setSafe(r, c, color.getRed());
                channels[1].setSafe(r, c, color.getGreen());
                channels[2].setSafe(r, c, color.getBlue());
           }
        }

        // decided what type of output should we have   
        ImageModel outImage = null;
        if (input instanceof DoubleImage) {
            outImage = (DoubleImage) input;

            return new DoubleImage(channels);
        } else if (input instanceof IntegerImage) {
            IntegerImage image = (IntegerImage) input;

            return new IntegerImage(channels);
        }

        // unknow type
        return null;
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
