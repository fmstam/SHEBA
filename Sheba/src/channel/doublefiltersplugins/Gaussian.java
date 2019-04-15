/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel.doublefiltersplugins;

import channel.ChannelFilter;
import channel.ChannelFilterPlugin;
import channel.DoubleChannel;

/**
 * The Gaussian filter of a given channel.
 * @author faroqal-tam
 */
public  class Gaussian implements ChannelFilterPlugin<DoubleChannel, Object> {
     @Override
        public DoubleChannel filter(DoubleChannel inChannel, Object... params) {
            double sigma = (Double)params[0];
            double[][] kernel = util.Kernels.getFullGaussian(sigma);
            return ChannelFilter.convolve(inChannel, kernel);
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
