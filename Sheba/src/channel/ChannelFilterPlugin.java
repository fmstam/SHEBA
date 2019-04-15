/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel;

import util.plugin.Plugin;

/**
 * A general purpose plug in for channel filtering.
 * @author faroqal-tam
 */
public interface ChannelFilterPlugin<T extends ChannelModel, I> extends Plugin{
  
   /**
    * A placeholder for filtering a single channel
    * @param t the channel type
    * @param i the parameters 
    * @return a filtered channel
    */
    public T filter(T inChannel, I... params);
    
   
    
}
