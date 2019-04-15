/*
 * Copyright (C) 2013 faroq
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package image.testers;

import channel.doublefiltersplugins.Mean;
import image.DoubleImage;
import image.ImageException;
import image.ImageModel;
import image.IntegerImage;
import image.filter.ImageFilter;
import image.filter.plugins.WatershedTransform;
import image.io.ImageIO;
import image.segmentation.Segmentation;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author faroq
 */
public class TestSegmentation {

    public static void main(String[] args) {
        try {

            String inpath = "/Users/faroq/Documents/PhD/dataSet/gt/";
            String outpath = "/Users/faroq/Documents/PhD/dataSet/output/";
            // parameters

            File[] files;
            File directory = new File(inpath);

            if (directory.isDirectory()) {
                files = directory.listFiles();
            } else {
                files = new File[1];
                files[0] = directory;
            }

            System.out.println("Image\tobserved CV\tcorrected CV");
            int length = files.length;
            for (int i = 0; i < length; i++) {

                String currfileString = files[i].getName();
                String currfileabsString = files[i].getAbsolutePath();
                String currfilecorrString = outpath + currfileString ;
                
                if (currfileString.equals(".DS_Store")) {
                    continue;
                }
                Segmentation s = new Segmentation();
                ImageModel im = ImageIO.loadImage(currfileabsString);
                IntegerImage integerImage = s.segment(im, Segmentation.isoData);
                ImageIO.saveImage(integerImage, currfilecorrString);
            }
            
        } catch (ImageException ex) {
            Logger.getLogger(TestWaterShed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
