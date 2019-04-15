/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image.conversion;

import channel.ChannelFilter;
import channel.DoubleChannel;
import channel.IntegerChannel;
import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.IntegerImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author faroqal-tam
 */
public abstract class ImageConvertor {
    
    public static final double [] GRAYSCALE_WEIGHTS_BT709= {0.2126 /* Red */, 0.7152 /* Green */, 0.0722 /* Blue */};
    public static final double [] GRAYSCALE_WEIGHTS_BT601= {0.299 /* Red */, 0.587 /* Green */, 0.114 /* Blue */};
    
    
   /**
    * The general invoker of the conversion plugins.
    * @param image input image
    * @param method the plugin 
    * @param params the additional params
    * @return 
    */
    public static ImageModel convert(ImageModel image, ImageConversionPlugin method, Object... params){ 
        try {
            return method.convert(image, params);
        } catch (ImageException ex) {
            Logger.getLogger(ImageConvertor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
   
     
}
