/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aanjos and Faroq Al-Tam
 */
public class Kernels {

    public static double[][] sharpen() {
        double sharpenKernel[][] = {
            {-0.25, -0.25, -0.25},
            {-0.25, 3.0, -0.25},
            {-0.25, -0.25, -0.25}
        };

        return sharpenKernel;
    }

    
    
  /**
   * Return a Gaussian kernel in the form of a*coefficient eˆ(-Xˆ2)/2*sigmaˆ2
   * @param a the magnitude of the Gaussian
   * @param coefficient the coefficient of the kernel
   * @param width the width of the kernel
   * @return a 2-D Gaussian kernel.
   */   
  public static double [][] getFullGaussian(double a,double coefficient, int width){

      // sigma can be calculated from the width as
      double sigma= (width-1)/6; // please see below on how we calculate the width from sigma.
      int halfSize = width/2 ;
      double [][] k= new double[2*halfSize+1][2*halfSize+1];
      
      for (int r = -halfSize; r <= halfSize; r++) {
            for (int c = -halfSize; c <= halfSize; c++) {
                k[r + halfSize][c + halfSize] = gaussianFromPosition(r, c, a, coefficient,sigma*sigma);
            }
        }
      
      return k;
  }

  

 /**
  *  A Gaussian value at a given point xy.
  * @param x the x coordinate
  * @param y the y coordinate
  * @param a the magnitude of the function
  * @param coefficient the value at the function peak which will be degraded by the Gaussian
  * @param c the variance, or spread of the function
  * @return a double value of the Gaussian at the current point. 
  */ 
public static double gaussianFromPosition(double x, double y,double a, double coefficient, double c) {
        
        double x2 = x * x;
        double y2 = y * y;
        
        return coefficient * a * Math.exp(-((x2 + y2) / (2*c)));
 }

    
    //Gaussian
    public static double gaussianFromPosition(double x, double y, double sigma) {
        double x2 = x * x;
        double y2 = y * y;
        double sigma2 = sigma * sigma;
        return (1 / (2 * Math.PI * sigma2)) * Math.exp(-((x2 + y2) / (2 * sigma2)));
    }

    public static double[][] getFullGaussian(double sigma) {
        int width = (int) (6 * sigma+1 );

        int halfSize = width/2 ;
        double[][] k = new double[halfSize * 2 + 1][halfSize * 2 + 1];
        for (int r = -halfSize; r <= halfSize; r++) {
            for (int c = -halfSize; c <= halfSize; c++) {
                k[r + halfSize][c + halfSize] = gaussianFromPosition(r, c, sigma);
            }
        }
        return k;
    }
    //------------------------

    //LoG parece estar a funcar bem.
    private static double LoGFromPosition(double x, double y, double sigma) {
        double x2 = x * x;
        double y2 = y * y;
        double sigma2 = sigma * sigma;
        return (- 1 / (Math.PI * sigma2 * sigma2)) * (1.0 - ((x2 + y2) / (2 * sigma2))) * Math.exp(-((x2 + y2) / (2 * sigma2)));
    }

    public static double[][] getFullLoG(double sigma) {
        int width = (int) (6 * sigma + 1);

        int halfSize = width / 2;
        double[][] k = new double[halfSize * 2 + 1][halfSize * 2 + 1];
        for (int r = -halfSize; r <= halfSize; r++) {
            for (int c = -halfSize; c <= halfSize; c++) {
                k[r + halfSize][c + halfSize] = LoGFromPosition(r, c, sigma);
            }
        }
        return k;
    }
    //----------------------

    public static void normalizeKernel(double[][] k) {
        double sum = 0.0;
        for (int r = 0; r < k.length; r++) {
            for (int c = 0; c < k[0].length; c++) {
                sum += k[r][c];
            }
        }
        for (int r = 0; r < k.length; r++) {
            for (int c = 0; c < k[0].length; c++) {
                k[r][c] = k[r][c] / sum;
            }
        }
    }

    
            
    public static void printKerenlToFile(double [][] k, File file){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter bw= new BufferedWriter(fileWriter);
            bw.write("#rows:"+ k.length + "\tcolumns:"+k[0].length);
            bw.newLine();
            for (int r = 0; r < k.length; r++) {
               for (int c = 0; c < k[0].length; c++) {
                   bw.write(k[r][c]+ "\t");
               }
              bw.newLine();
              bw.flush();
           }
        } catch (IOException ex) {
            Logger.getLogger(Kernels.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Kernels.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
    }
    
    public static void printKerenlToFile(double [] k, File file){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter bw= new BufferedWriter(fileWriter);
            bw.write("#Length:"+ k.length);
            bw.newLine();
            for (int r = 0; r < k.length; r++) {
                   bw.write(k[r]+"\n");
                    
               }
              bw.newLine();
           bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(Kernels.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Kernels.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
    }
    
     public static void printKerenlToFile(int [] k, File file){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter bw= new BufferedWriter(fileWriter);
            bw.write("Length:"+ k.length);
            bw.newLine();
            for (int r = 0; r < k.length; r++) {
                   bw.write(k[r]+"\n");
                    
               }
              bw.newLine();
           bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(Kernels.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Kernels.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
    }
    public static void printKernel(double[][] k) {
        System.out.println("printing matrix\n");

        for (int r = 0; r < k.length; r++) {
            for (int c = 0; c < k[0].length; c++) {
                System.out.printf("%5.8f ", k[r][c]);
            }
            System.out.println();
        }
    }
    
    public static void printKernel(double[] k) {
        System.out.println("printing matrix\n");

        for (int r = 0; r < k.length; r++) {
                System.out.printf("%5.8f\t", k[r]);
            }
            
        
    }
    
     public static void printKernel(int[] k) {
        System.out.println("printing matrix\n");

        for (int r = 0; r < k.length; r++) {
                System.out.print(k[r]+"\t");
            }
            
        
    }
    
     public static void printintKernel(int[][] k) {
        System.out.println("printing matrix\n");

        for (int r = 0; r < k.length; r++) {
            for (int c = 0; c < k[0].length; c++) {
                System.out.print(k[r][c]);
            }
            System.out.println();
        }
    }
     
      public static void printpointKernel(ArrayList<Point> k, int radius) {
        System.out.println("printing matrix, Size="+k.size());
        
        for (int r = 0; r < k.size(); r++) {
            if(r%((2*radius)+1)==0)
            System.out.println();
            //for (int c = 0; c < k[0].length; c++) {
                System.out.print("("+k.get(r).x+","+k.get(r).y+")");
            
        }
    }
    
    /**
     * used in eroding a binary image using a dilate code
     */
    public static int [][] reflect (int [][] kernel){
        
        // assume it has the smame size;
        int [][] newkernel=new int [kernel.length][kernel.length];
        for(int i=0;i<kernel.length;i++)
            for(int j=0;j<kernel.length;j++){
                newkernel[i][j]=kernel[j][i];
            }
        return newkernel;
    }
    
    
    
    /**
     * generate a disk mask for math morphology
     * @param radius 
     * @return 
     */
    public static int [][] generateDiskKernel(int r){
        
                
		if (r <= 1) r = 1;
		int size = r + r + 1;
		int [][]se = new int[size][size]; // structuring element;
		double r2 = r * r;
                
		for (int v=-r; v<=r; v++){
			for (int u=-r; u<=r; u++){
				if (u*u+v*v <= r2)
					se[v+r][u+r] = 1;
			}
		}
        
        return se;
    }
    
    
    /**
     * generate a disk of points centered at an given point
     * @param r radius
     * @param center the xy center of the disk
     * @return 
     */
    public static ArrayList<Point> generateDiskAsPoints(int r, Point center){
        
                ArrayList<Point> neighbors= new ArrayList<Point>();
		if (r <= 1) r = 1;
		int size = r + r + 1;
		double r2 = r * r;
                
		for (int v=-r; v<=r; v++){
			for (int u=-r; u<=r; u++){
				if (u*u+v*v <= r2)
					neighbors.add(new Point(u+center.x,v+center.y));
//                                        else
//                                        neighbors.add(new Point(0,0));
			}
		}
        
        return neighbors;
    }
    
    
    public static int [][] generateemptyDiskKernel(int r){
        
                
		if (r <= 1) r = 1;
		int size = r + r + 1;
		int [][]se = new int[size][size]; // structuring element;
		double r2 =  (r) * (r);

		for (int v=-r; v<=r; v++){
			for (int u=-r; u<=r; u++){
				if (u*u+v*v<=r2 && u*u+v*v>r2-(r+r))
                                    
					se[v+r][u+r] = 1;
			}
		}
        
        return se;
    }
    
     public static int [][] generateInvertedDiskKernel(int r){
        
                
		if (r <= 1) r = 1;
		int size = r + r + 1;
		int [][]se = new int[size][size]; // structuring element;
		double r2 = (r) * (r);

		for (int v=-r; v<=r; v++){
			for (int u=-r; u<=r; u++){
				if (v*v+u*u==r2 )
					se[v+r][u+r] = 1;
			}
		}
        
        return se;
    }
     /**
     * generate an ellipse mask for math morphology
     * @param r1 
     * @param r2
     * @return 
     */
    public static int [][] generateEllipseKernel(int r1, int r2){
        
                
		if (r1 <= 1) r1 = 1;
		int size = r1 + r2 + 1;
		int [][]se = new int[r1+r1+1][r2+r2+1]; // structuring element;
		double a = r1 * r2;

		for (int v=-r1; v<=r1; v++){
			for (int u=-r2; u<=r2; u++){
				if (u*u+v*v <= a)
					se[v+r1][u+r2] = 255;
			}
		}
        
        return se;
    }
    
    public static int[][] generateSqureKernel(int r){
        int [][] kernel=new int[r][r];
        for(int i=0;i<r;i++)
            for(int j=0;j<r;j++)
                kernel[i][j]=255;
        return kernel;
    }
    
      public static int[][] generateInvertedSqureKernel(int r){
        int [][] kernel=new int[r][r];
        for(int i=0;i<r;i++)
            for(int j=0;j<r;j++)
                if((i==0&&j==0) || (i==r-1 && j==r-1) || (i==r-1 && j==0) || (i==0 &&j== r-1))
                 kernel[i][j]=255;
        return kernel;
    }
    
    
    public static int [][] generateCross(int r){
         int [][] kernel=new int[r][r];
         int mid=r/2;
        for(int i=0;i<r;i++)
            kernel[i][mid]=1;
            for(int j=0;j<r;j++)            
                kernel[mid][j]=1;
            
        return kernel;
        
    }
    
     public static int [][] generatedagger(int r){
         int [][] kernel=new int[r][r];
         int mid=r/2;
        for(int i=0;i<r;i++)
            kernel[i][mid/2]=1;
            for(int j=0;j<r;j++)            
                kernel[mid][j]=1;
            
        return kernel;
        
    }
    
    
}
