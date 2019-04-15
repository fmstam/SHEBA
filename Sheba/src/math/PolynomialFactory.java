/*
 * Copyright (C) 2013 faroqal-tam
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
package math;

import java.util.Arrays;
import java.util.Random;
import org.ejml.factory.LinearSolver;
import org.ejml.factory.LinearSolverFactory;
import org.ejml.ops.NormOps;
import util.DoubleLabledPoint;
import org.ejml.simple.SimpleMatrix;

/**
 * Polynomials manipulator class.
 *
 * @author faroqal-tam
 */
public class PolynomialFactory {

    /**
     * Fit a 2D polynomial surface to a 2D data arranged in grid by using the
     * Least Squares Approach.
     *
     * @param data the input data as a double vector
     * @param numRows the number of rows
     * @param numCols the number of columns.
     * @param polyDegree the degree of the polynomial
     * @return a vector of coefficients of the 2D polynomials
     */
    public double[] polynomialFit(double[] data, int numRows, int numCols, int polyDegree) {

        // calcualte the (L^T L) matrix
        // number of parameters of polynomial of degree polyDegree is (n+1)(n+2)/2
        int thetaLength = (polyDegree + 1) * (polyDegree + 2) / 2;

        // buil the matrices, ejml gives good performance, please see
        SimpleMatrix L = new SimpleMatrix((numRows * numCols), thetaLength); // the design matrix
        SimpleMatrix F = new SimpleMatrix(numRows * numCols, 1, true, data); // the data 

        // build up the design matrix
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int offset = j + i * numCols;
                int c = 0;
                for (int k = 0; k <= polyDegree; k++) {
                    for (int l = 0; l <= k; l++) {
                        L.set(offset, c++, Math.pow(i, k - l) * Math.pow(j, l));
                        // System.out.print("x^"+ (k - l)+ "y^"+ l+ " ");
                    }
                }
                // System.out.println();
            }
        }

        // solve the normal equations
        SimpleMatrix Lcpy = new SimpleMatrix(L);
        SimpleMatrix LT = L.transpose();
        
        

        Lcpy = LT.mult(Lcpy);
        
        // uncomment for L2 reguralization: 
//        double [] diagnoal = new double[Lcpy.getMatrix().numRows];
//        Arrays.fill(diagnoal, 100);
//        SimpleMatrix l2 = SimpleMatrix.diag(diagnoal);
//        Lcpy = Lcpy.plus(l2);
        
        
        SimpleMatrix theta = Lcpy.solve(LT.mult(F));
        theta.reshape(1, thetaLength);
        return theta.getMatrix().getData();

    }
    
    /**
     * Generate random parameter of a given polynomial degree.
     * @param polyDegree the degree of the polynomial
     * @param paramMaxscale the maximum possible value in the parameters.
     * @param allowNegative if <code> true </code> negative parameters will be allowed in the parameter set.
     * @return a double vector representing the parameter set of length <code> (polyDegree + 1) * (polyDegree + 2) / 2 </code>
     */
    public double[] randomPolyParameters(int polyDegree, double paramMaxscale, boolean allowNegative) {
        int thetaLength = (polyDegree + 1) * (polyDegree + 2) / 2;
        double[] params = new double[thetaLength];
        Random r = new Random();
        params[0]= paramMaxscale;
        for (int i = 1; i < thetaLength; i++) {
            double randValue = r.nextDouble()*(r.nextDouble()>0.5?3.0:0.005);
            double isNegative = r.nextDouble();
            if(r.nextDouble()>0.7) randValue=paramMaxscale;
            if (isNegative > 0.2 && allowNegative) {
                params[i] = -randValue;
            } else {
                params[i] = randValue;
            }
        }
        
        // subtract the mean to standize
        
        
        return params;

    }
    
    
    
    
    /**
     * Build a 2-D polynomial surface given the coefficient list
     * @param numRows
     * @param numCols
     * @param polyDegree
     * @param paramVector
     * @return a double matrix of the surface
     */
    public double [][] polynomial2D(int numRows, int numCols, int polyDegree, double[] paramVector){
        double [][] p2d= new double[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                int i = 0;
                double intensity = 0;
                for (int k = 0; k <= polyDegree; k++) {
                    for (int l = 0; l <= k; l++) {
                        intensity += paramVector[i++] * Math.pow(r, k - l) * Math.pow(c, l);
                    }
                }
                p2d[r][c]= intensity;
            }
        }
        return p2d;
    }
    
    
        /**
     * Build a 2-D polynomial surface given the coefficient list
     * @param numRows
     * @param numCols
     * @param polyDegree
     * @param paramVector
     * @return a double matrix of the surface
     */
    public double [][] polynomial2Dnormalized_PB(int numRows, int numCols, int polyDegree,int index, double[] paramVector){
        double [][] p2d= new double[numRows][numCols];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                int i = 0;
                double intensity = 0;
                int pos_index = 0;
                for (int k = 0; k <= polyDegree; k++) {
                    for (int l = 0; l <= k; l++) {
                        double nr=((double)r)/((double) numRows);
                        double nc=((double)c)/((double) numCols);
                        intensity += paramVector[pos_index] * Math.pow(nr, k - l) * Math.pow(nc, l);
                        if (pos_index == index - 1){
                            k = polyDegree + 1;
                            break;
                        }else
                            pos_index = pos_index + 1;
                        
                    }
                }
                p2d[r][c]= intensity;
            }
        }
        
        
        return p2d;
    }
    
     
        /**
     * Build a 2-D polynomial surface given the coefficient list
     * @param numRows
     * @param numCols
     * @param polyDegree
     * @param paramVector
     * @return a double matrix of the surface
     */
    public double [][] polynomial2Dnormalized(int numRows, int numCols, int polyDegree, double[] paramVector){
        double [][] p2d= new double[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                int i = 0;
                double intensity = 0;
                for (int k = 0; k <= polyDegree; k++) {
                    for (int l = 0; l <= k; l++) {
                        double nr=((double)r)/((double) numRows);
                        double nc=((double)c)/((double) numCols);
                        intensity += paramVector[i++] * Math.pow(nr, k - l) * Math.pow(nc, l);
                    }
                }
                p2d[r][c]= intensity;
            }
        }
        return p2d;
    }
    
    
    /**
     * Fit the a polynomial to a set of points
     * @param 
     * @param domainWidth
     * @param polyDegree
     * @return 
     */
    public double[] polynomialFit(DoubleLabledPoint[] pixels, int polyDegree) {

        // calcualte the (L^T L) matrix
        // number of parameters of polynomial of degree polyDegree is (n+1)(n+2)/2
        int thetaLength = (polyDegree + 1) * (polyDegree + 2) / 2;
        SimpleMatrix L = new SimpleMatrix(pixels.length, thetaLength);
        SimpleMatrix F = new SimpleMatrix(pixels.length, 1);
        // build up the polynomial
        double maxx=1;
        double maxy=1;
//        for (DoubleLabledPoint gp : pixels) {
//            if(gp.x>maxx)
//                maxx=gp.x;
//             if(gp.y>maxy)
//                maxy=gp.y;
//          }
        
        int offset = 0;
        for (DoubleLabledPoint gp : pixels) {
            double i = ((double)gp.x)/maxx;
            double j = ((double)gp.y)/maxy;
            //int offset = j + i * domainWidth;
            int c = 0;
            for (int k = 0; k <= polyDegree; k++) {
                for (int l = 0; l <= k; l++) {
                    L.set(offset, c++, Math.pow(i, k - l) * Math.pow(j, l));
                }
            }
            F.set(offset++, 0, gp.getValue());
        }


        // solve the normal equations
        SimpleMatrix Lcpy = new SimpleMatrix(L);
        SimpleMatrix LT = L.transpose();
        Lcpy = LT.mult(Lcpy);
       System.out.println("Rank:"+ Lcpy.svd(true).rank()+ " parameters:"+ (thetaLength) );

        System.out.println("cond:"+ NormOps.normP2(Lcpy.getMatrix())*NormOps.normP2(Lcpy.invert().getMatrix())+ " parameters:"+ (thetaLength) );

        SimpleMatrix theta = Lcpy.solve(LT.mult(F));
        
        theta.reshape(1, thetaLength);
        return theta.getMatrix().getData();
    }

    
   
    
    /**
     * Fit the a polynomial to a set of points using the QR pivot with householder decomposition.
     * @param 
     * @param domainWidth
     * @param polyDegree
     * @return 
     */
    public double[] polynomialFitQRpivot(DoubleLabledPoint[] pixels, int polyDegree) {

        // calcualte the (L^T L) matrix
        // number of parameters of polynomial of degree polyDegree is (n+1)(n+2)/2
        int thetaLength = (polyDegree + 1) * (polyDegree + 2) / 2;
        SimpleMatrix L = new SimpleMatrix(pixels.length, thetaLength);
        SimpleMatrix F = new SimpleMatrix(pixels.length, 1);
        SimpleMatrix theta= new SimpleMatrix(thetaLength,1);
        
        // build up the polynomial
        double maxx=1;
        double maxy=1;
        for (DoubleLabledPoint gp : pixels) {
            if(gp.x>maxx)
                maxx=gp.x;
             if(gp.y>maxy)
                maxy=gp.y;
          }
        
        int offset = 0;
        for (DoubleLabledPoint gp : pixels) {
            double i = ((double)gp.x)/maxx;
            double j = ((double)gp.y)/maxy;
            //int offset = j + i * domainWidth;
            int c = 0;
            for (int k = 0; k <= polyDegree; k++) {
                for (int l = 0; l <= k; l++) {
                    L.set(offset, c++, Math.pow(i, k - l) * Math.pow(j, l));
                }
            }
            F.set(offset++, 0, gp.getValue());
        }

        
       
        
       
        LinearSolver ls= LinearSolverFactory.leastSquaresQrPivot(true, false);
        //LinearSolver ls = LinearSolverFactory.pseudoInverse(true);
        
        ls.setA(L.getMatrix());
        //System.out.println("quality: "+ ls.quality());
        ls.solve(F.getMatrix(), theta.getMatrix());
        return theta.getMatrix().getData();
        
        
    }



    
        public double[] polynomialFitQRpivot(double[] data, int numRows, int numCols, int polyDegree) {

        int thetaLength = (polyDegree + 1) * (polyDegree + 2) / 2;
        SimpleMatrix theta= new SimpleMatrix(thetaLength,1);

        // buil the matrices, ejml gives good performance, please see
        SimpleMatrix L = new SimpleMatrix((numRows * numCols), thetaLength); // the design matrix
        SimpleMatrix F = new SimpleMatrix(numRows * numCols, 1, true, data); // the data 

        // build up the design matrix
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int offset = j + i * numCols;
                int c = 0;
                for (int k = 0; k <= polyDegree; k++) {
                    for (int l = 0; l <= k; l++) {
                        L.set(offset, c++, Math.pow(i, k - l) * Math.pow(j, l));
                        // System.out.print("x^"+ (k - l)+ "y^"+ l+ " ");
                    }
                }
                // System.out.println();
            }
        }

        
       
        
       
        LinearSolver ls= LinearSolverFactory.leastSquaresQrPivot(true, false);
        //LinearSolver ls = LinearSolverFactory.pseudoInverse(true);
        
        ls.setA(L.getMatrix());
        //System.out.println("quality: "+ ls.quality());
        ls.solve(F.getMatrix(), theta.getMatrix());
        return theta.getMatrix().getData();
        
        
    }


/**
     * Fit the a polynomial to a set of points using the QR pivot with householder decomposition.
     * @param 
     * @param domainWidth
     * @param polyDegree
     * @return 
     */
    public double[] polynomialFitQRpivot_PB(DoubleLabledPoint[] pixels, int polyDegree, int index) {

        // calcualte the (L^T L) matrix
        // number of parameters of polynomial of degree polyDegree is (n+1)(n+2)/2
        int athetaLength = (polyDegree + 1) * (polyDegree + 2) / 2;
        SimpleMatrix L = new SimpleMatrix(pixels.length, athetaLength);
        SimpleMatrix F = new SimpleMatrix(pixels.length, 1);
        SimpleMatrix theta= new SimpleMatrix(index,1);
        
        // build up the polynomial
        double maxx=1;
        double maxy=1;
        for (DoubleLabledPoint gp : pixels) {
            if(gp.x>maxx)
                maxx=gp.x;
             if(gp.y>maxy)
                maxy=gp.y;
          }
        
        int offset = 0;
        for (DoubleLabledPoint gp : pixels) {
            double i = ((double)gp.x)/maxx;
            double j = ((double)gp.y)/maxy;
            //int offset = j + i * domainWidth;
            int c = 0;
            for (int k = 0; k <= polyDegree; k++) {
                for (int l = 0; l <= k; l++) {
                    L.set(offset, c++, Math.pow(i, k - l) * Math.pow(j, l));
                    //System.out.print("x^" + (k-l) + "* y^" + l + " + ");
                }
                
            }
            //System.out.print("\n");
            F.set(offset++, 0, gp.getValue());
        }

        
       
        
        // extract L with current index
        L = L.extractMatrix(0 , pixels.length, 0, index);
        LinearSolver ls= LinearSolverFactory.leastSquaresQrPivot(true, false);
        
        ls.setA(L.getMatrix());
        //System.out.println("quality: "+ ls.quality());
        ls.solve(F.getMatrix(), theta.getMatrix());
        return theta.getMatrix().getData();
        
        
    }

    
   
}
