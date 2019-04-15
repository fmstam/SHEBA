/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image.conversion.plugins;

import channel.ChannelFilter;
import channel.DoubleChannel;
import channel.IntegerChannel;
import image.conversion.ImageConversionPlugin;
import image.conversion.ImageConvertor;
import image.DoubleImage;
import image.ImageModel;
import image.IntegerImage;

/**
 * Gray scale convertor class. 
 * @author faroqal-tam
 */
public class Grayscale implements ImageConversionPlugin<ImageModel, Object>{
     @Override
        public ImageModel convert(ImageModel input, Object... params) {
            
            if(input instanceof DoubleImage){
                 DoubleImage image= (DoubleImage)input;
                 DoubleChannel channel= ChannelFilter.combine(image.getChannels(), ImageConvertor.GRAYSCALE_WEIGHTS_BT709);
                 return new DoubleImage(channel);
            }
            else if(input instanceof IntegerImage){
                 IntegerImage image= (IntegerImage)input;
                 IntegerChannel channel= ChannelFilter.combine(image.getChannels(), ImageConvertor.GRAYSCALE_WEIGHTS_BT709);
                 return new IntegerImage(channel);
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
