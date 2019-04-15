/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel.doublefiltersplugins;

import channel.ChannelFilterPlugin;
import channel.DoubleChannel;

/**
 * Invert the input channel using a given maximum, 255 is used as default to apply the inversion.
 * @author faroqal-tam
 */
public  class Invert implements ChannelFilterPlugin<DoubleChannel, Object> {

    private double maxValue;

    public Invert(double maxValue) {
        this.maxValue = maxValue;
    }
    
    public Invert() {
        this.maxValue = 255.0;
    }
    
    @Override
    public DoubleChannel filter(DoubleChannel inChannel, Object... params) {
        
        
        if (params.length>0) { // do we have something here?
            maxValue = (Double) params[0]; // the maximum is expected here
        }
        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();
        DoubleChannel outChannel = new DoubleChannel(numRows, numCols);
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                outChannel.set(r, c, maxValue - inChannel.get(r, c));
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
