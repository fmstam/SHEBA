/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel.doublefiltersplugins;

import channel.ChannelFilterPlugin;
import channel.ChannelModel;
import channel.DoubleChannel;
import channel.IntegerChannel;
import math.InterpolationFactory;

/**
 * Scaler of the channels.
 * @author faroqal-tam
 */
public class Scale implements ChannelFilterPlugin<ChannelModel, Object>{
    private double scaleFactor;

    public Scale(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
    
     @Override
        public ChannelModel filter(ChannelModel inChannel, Object... params) {

            
            int rows = (int) Math.round(inChannel.numRows() * scaleFactor);
            int cols = (int) Math.round(inChannel.numCols() * scaleFactor);
            InterpolationFactory interpolationFactory = new InterpolationFactory();
            // create output channel
            ChannelModel outChannel = null;
            if (inChannel instanceof DoubleChannel) {
                outChannel = new DoubleChannel(inChannel.getCode(), rows, cols);
            } else if (inChannel instanceof IntegerChannel) {
                outChannel = new IntegerChannel(inChannel.getCode(), rows, cols);
            }

            double u, v;
            Number newValue;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    //u = r * (1 / scaleFactor); // approach 1
                    //v = c * (1 / scaleFactor);
                    u = (r + 0.5) / scaleFactor - 0.5; // approach 2
                    v = (c + 0.5) / scaleFactor - 0.5;

                    if (u < 0) { // because 0 is at the center of pixel 0; can be ignored if using approach 1
                        u = 0;
                    }
                    if (v < 0) {
                        v = 0;
                    }
                    
                    // interpolate with bicubic
                    newValue = interpolationFactory.bicubicInterpolation(u, v, inChannel);
                    outChannel.setSafe(r, c, newValue);

                }
                //System.out.println(" After create Memory size:"+ Runtime.getRuntime().totalMemory()/(1024*1024));
                //System.gc();
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
