/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel.math;

import channel.DoubleChannel;
import util.plugin.Plugin;

/**
 * A simple math calculator for double channel.
 * @author faroqal-tam
 */
public interface SimpleChannelMathPlugin extends Plugin{
    /**
     * Return a double value of a math operation applied on the input channel.
     * @param inChannel
     * @param param an optional double parameter list
     * @return 
     */
    public double calc(DoubleChannel inChannel, double ... param);
    
}
