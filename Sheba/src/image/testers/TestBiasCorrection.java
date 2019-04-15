/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image.testers;

import image.DoubleImage;
import image.ImageException;
import image.ImageModel;

import image.io.ImageIO;
import image.filter.ImageFilter;
import image.filter.plugins.IterativeBiasRemover_Cri;
import image.filter.plugins.IterativeBiasRemover_R2;
import image.filter.plugins.PolynomialBiasRemover;
import image.filter.plugins.WatershedBiasRemover_R2;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import math.GeneralMath;

/**
 *
 * @author faroqal-tam
 */
public class TestBiasCorrection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Memory size:" + Runtime.getRuntime().totalMemory() / (1024 * 1024));
            // TODO code application logic here
            
            String inpath = "/Users/faroq/Dropbox/dataset_bias/in";
            String outpath = "/Users/faroq/Dropbox/dataset_bias/out/";
            // parameters

            File[] files;
            File directory = new File(inpath);
            if (directory.isDirectory()) {
                files = directory.listFiles();
            } else {
                files = new File[1];
                files[0] = directory;
            }
            
            
            
            // remove the ones already in the output folder
//            List<File> aa = Arrays.asList(new File(outpath).listFiles());
//            List<File> bb = Arrays.asList(files);
//            
//            ArrayList<File> a = new ArrayList();
//            ArrayList<File> b =  new ArrayList();
//            a.addAll(aa);
//            b.addAll(bb);
//            
//            LinkedList<File> toremove = new LinkedList<File>();
//            for(File fa: a)
//                for(File fb:b)
//                    if(fa.getName().equals(fb.getName()))
//                        toremove.add(fb);
//            
//            b.removeAll(toremove);
//            
//
//            files =  new File[b.size()];
//            for(int i = 0; i < files.length; i++)
//                files[i] = b.get(i);
//                    
//            
            
            
            

            System.out.println("Image\tobserved CV\tTV\tcorrected CV\tTV");
            int length = files.length;
            for (int i = 0; i < length; i++) {

                String currfileString = files[i].getName();
                String currfileabsString = files[i].getAbsolutePath();
                if(currfileString.equals(".DS_Store")|| currfileString.equals(".dropbox"))continue;

                
                String currfileBString = outpath + currfileString ;


                System.out.println("Procssing file:" + currfileabsString + "\t" + i + " of " + length);
                ImageModel im = ImageIO.loadImage(currfileabsString);
                DoubleImage d = new DoubleImage(im);
                
                ImageFilter imageFilter = new ImageFilter();
                // for visulation sake, fit the observed image to a polynomial of degree 3
                //DoubleImage observedIF=imageFilter.filter(d, new Scale(0.125));
                //observedIF=imageFilter.filter(observedIF, new IlluminationSurface(IlluminationSurface.SurfaceType.Polynomial, 5));
                

                
                //IterativeBiasRemover_Cri p = new IterativeBiasRemover_Cri(15, 0.125, false, 0.05, false, outpath);
                //IterativeBiasRemover_R2 p = new IterativeBiasRemover_R2(15, 0.125, false, 0.05, false, outpath);
                //IterativeBiasRemover_Cri_PB p = new IterativeBiasRemover_Cri_PB(15, 0.125, false, 0.05, false, outpath);
                
                //WatershedBiasRemover_R2 p = new WatershedBiasRemover_R2(15, 0.125, false, 3, false, outpath);
                PolynomialBiasRemover p= new PolynomialBiasRemover(7, 0.125, false);
                //WatershedBiasRemover p= new WatershedBiasRemover(15, 0.125, true, 3, false, outpath);
                //IterativeBiasRemover_criterion p = new IterativeBiasRemover_criterion(15, 0.125, false, 0.05, false);
                
                DoubleImage c = imageFilter.filter(d, p);
                c=imageFilter.calculate(c, GeneralMath.normalize, 255.0);
                //ImageIO.saveImage(c, currfileResString);
               
                ImageIO.saveImage(c, currfileBString);
                
                //if(1==1) continue;
                //ImageIO.saveImage(p.getIfieldImage(), currfilecorrString);
                System.out.println("Memory size:" + Runtime.getRuntime().totalMemory() / (1024 * 1024));
//                Scale s = new Scale(0.125);
//                
//               
//
//                d = imageFilter.filter(d, s);
//                DoubleImage r1 = imageFilter.filter(c, s);
//                DoubleImage pimage = imageFilter.filter(p.getIfieldImage(), s);
//
//                pimage.setChannels(new DoubleChannel[]{pimage.getChannel(0), pimage.getChannel(0), pimage.getChannel(0)});
//                d = imageFilter.filter(d, new Append(Append.AppendingType.Horizontal), new DoubleImage[]{observedIF,r1,pimage});
//                ImageIO.saveImage(d, currfileResString);
//                
//                // System.out.println("Memory size:" + Runtime.getRuntime().totalMemory() / (1024 * 1024));
//                System.gc();
//                DoubleImage observed = new DoubleImage(im);
//                if (c.numberOfChannels() == 3) {
//                    c = (DoubleImage) ImageConvertor.convert(c, new Grayscale(), ImageConvertor.GRAYSCALE_WEIGHTS_BT709);
//                    observed = (DoubleImage) ImageConvertor.convert(observed, new Grayscale(), ImageConvertor.GRAYSCALE_WEIGHTS_BT709);
//                }
//                
//                
//                observedIF= imageFilter.filter(observedIF, new Scale(8));
//
//                //observed= imageFilter.filter(observed, new Channel.DoubleFIltersPlugins.Mean(5));
//                double observedTV= imageFilter.calculate(observedIF, new TV())[0];
//                double observedImageMean = imageFilter.calculate(observedIF, new Mean())[0];
//                double observedImageSTD = imageFilter.calculate(observedIF, new STDev())[0];
//
//                //c= imageFilter.filter(c, new Channel.DoubleFIltersPlugins.Mean(5));
//                c=imageFilter.filter(c, new Scale(0.125));
//                c=imageFilter.filter(c, new IlluminationSurface(IlluminationSurface.SurfaceType.Polynomial, 5));
//                c= imageFilter.filter(c, new Scale(8));
//                
//                //ImageIO.saveImage(c, currfilecorrString);
//                double correctedTV= imageFilter.calculate(c, new TV())[0];
//                double correctedImagemean = imageFilter.calculate(c, new Mean())[0];
//                double correctedImagestd = imageFilter.calculate(c, new STDev())[0];
//
//                System.out.print(currfileString);
//                System.out.print("\t" + (observedImageSTD / observedImageMean));
//                //System.out.print("\t" + observedTV);
//                System.out.println("\t" + (correctedImagestd / correctedImagemean));
//               // System.out.println("\t"+ correctedTV);
                System.gc();
            }
        } catch (ImageException ex) {
            Logger.getLogger(TestBiasCorrection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
