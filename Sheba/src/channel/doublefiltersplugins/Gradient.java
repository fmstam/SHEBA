/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel.doublefiltersplugins;

import channel.ChannelFilter;
import channel.ChannelFilterPlugin;
import channel.DoubleChannel;

/**
 * The gradient filter for both magnitude and direction.
 *
 * @author faroqal-tam
 */
public class Gradient implements ChannelFilterPlugin<DoubleChannel, Object> {

    /**
     * The type of gradient filter to apply.
     */
    ChannelFilter.GRADIENT_TYPE type;
    
    public Gradient(ChannelFilter.GRADIENT_TYPE type){
        this.type= type;
    }
    
    /**
     * Get the horizontal gradient of the input channel.
     *
     * @return
     */
    public DoubleChannel rowGradient(DoubleChannel inChannel) {

        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();

        DoubleChannel outChannel = new DoubleChannel(numRows, numCols);

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                outChannel.set(r, c, (inChannel.get(r + 1, c, -1) - inChannel.get(r - 1, c, -1)) / 2.0); //central differences
            }
        }
        return outChannel;
    }

    /**
     * Get the vertical gradient of the input channel;
     *
     * @return
     */
    public DoubleChannel colGradient(DoubleChannel inChannel) {
        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();

        DoubleChannel outChannel = new DoubleChannel(numRows, numCols);

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                outChannel.set(r, c, (inChannel.get(r, c + 1, -1) - inChannel.get(r, c - 1, -1)) / 2.0); //central differences
            }
        }
        return outChannel;
    }

    
    /**
     * The generalized gradient of the input channel.
     *
     * @param inChannel
     * @return
     * @see DoubleChannel
     */
    public DoubleChannel generalizedGradient(DoubleChannel inChannel) {
        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();

        DoubleChannel outChannel = new DoubleChannel(numRows, numCols);

        // calculate the differences of the pixel neghbors.
        for (int r = 1; r < numRows - 1; r++) {
            for (int c = 1; c < numCols - 1; c++) {
                double[] d = new double[4];
                d[0] = inChannel.get(r + 1, c) - inChannel.get(r - 1, c);
                d[1] = inChannel.get(r, c - 1) - inChannel.get(r, c + 1);
                d[2] = inChannel.get(r + 1, c + 1) - inChannel.get(r - 1, c - 1);
                d[3] = inChannel.get(r + 1, c - 1) - inChannel.get(r - 1, c + 1);
                double D = 0.0;
                for (double k : d) {
                    D += Math.pow(k, 2);
                }
                double gradient = (D + Math.sqrt(2) * d[0] * (d[2] + d[3]) - Math.sqrt(2) * d[1] * (d[2] - d[3]));
                outChannel.set(r, c, Math.sqrt(gradient));
            }
        }
        return outChannel;
    }

    /**
     * The gradient direction of the input channel.
     *
     * @param inChannel
     * @return
     */
    public DoubleChannel gradientDirection(DoubleChannel inChannel) {
        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();
        DoubleChannel outChannel = new DoubleChannel(numRows, numCols);
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                double vG = (inChannel.get(r, c + 1, -1) - inChannel.get(r, c - 1, -1)) / 2.0; //central differences
                double hG = (inChannel.get(r + 1, c, -1) - inChannel.get(r - 1, c, -1)) / 2.0; //central differences
                outChannel.set(r, c, Math.atan2(vG, hG));
            }
        }
        return outChannel;
    }

    
    public DoubleChannel gradientMagniude(DoubleChannel inChannel) {
        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();
        DoubleChannel outChannel = new DoubleChannel(numRows, numCols);
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                double vG = (inChannel.get(r, c + 1, -1) - inChannel.get(r, c - 1, -1)) / 2.0; //central differences
                double hG = (inChannel.get(r + 1, c, -1) - inChannel.get(r - 1, c, -1)) / 2.0; //central differences
                outChannel.set(r, c, Math.sqrt(vG*vG+hG*hG));
            }
        }
        return outChannel;
    }
    @Override
    public DoubleChannel filter(DoubleChannel inChannel, Object... params) {
        // check the params
        // if params are empty calcuate the magnitude
        if(params.length>0){
        switch(type){
            case Direction:
                return gradientDirection(inChannel);
            case Horizontal:
                return rowGradient(inChannel);
            case Vertical:
                return colGradient(inChannel);
            case Magnitude:
                return gradientMagniude(inChannel);
        }
        }
        return gradientMagniude(inChannel);
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
