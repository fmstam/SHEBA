/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel.math;

import channel.DoubleChannel;
import util.Region;
import java.awt.Rectangle;

/**
 * General math class specialized for channel class.
 * @author faroqal-tam
 */
public  class ChannelMath {
    
   
    /**
     * A general invoker function for the channel math plugins.
     * @param inChannel
     * @param method
     * @param params
     * @return 
     */
    public double calculate(DoubleChannel inChannel,SimpleChannelMathPlugin method, double ... params){
        return method.calc(inChannel, params);
    }
    
}