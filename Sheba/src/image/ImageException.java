/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import java.util.HashMap;
import java.util.Map;

/**
 *  Image Common exceptions handler.
 * @author faroqal-tam
 */
public class ImageException extends Exception{

    static Map<String, String> errorMessages = new HashMap();
    // the possibe messages
    static{
        errorMessages.put("NO_CHANNELS","The Image has no channels");
        errorMessages.put("NO_SUCH_CHANNEL", "The image has no channel of index:"); // the index is needed
        errorMessages.put("GRAYSCALE_REQUIRED","Invalid image type, a grayscale image was expected ");
        errorMessages.put("IMAGE_CONVERSION_ERROR","Invalid conversion, input image' number of channel should be: ");
        errorMessages.put("UNSUPPORTED_IMAGE_FORMAT","Unsupported image format");
        errorMessages.put("CHANNEL_SIZE_ERROR","The channels do not have the same channel size");

       
    }
    public ImageException(String messageCode) {   
        super(errorMessages.get(messageCode));
    }
    
    public ImageException(String messageCode, int index) {   
        super(errorMessages.get(messageCode)+ String.valueOf(index));
    }
    
     
    
}
