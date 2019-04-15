/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image.conversion;

import util.plugin.Plugin;
import image.ImageException;
import image.ImageModel;

/**
 * Image convertor plug in interface.
 * @author faroqal-tam
 */
public interface ImageConversionPlugin<T extends ImageModel,I> extends Plugin {
    public ImageModel convert(T image, I... params) throws ImageException;
    
}
