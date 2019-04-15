/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image.segmentation;

import util.plugin.Plugin;
import image.ImageModel;
import image.IntegerImage;

/**
 *
 * @author faroqal-tam
 */
public interface ImageSegmentationPlugin<T extends ImageModel, I> extends Plugin{
    
     public IntegerImage segment(T image, I... params);
     public String getName();
     public String getDescription();
     
    
}
