/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import channel.ChannelModel;
import channel.DoubleChannel;

/**
 * A general interpolation class. It supports from linear to cubic BSpline
 * curves for both 1D and 2D data.
 *
 * @author faroqal-tam
 */
public class InterpolationFactory {

    

    

    /**
     * The biLinear InterpolationFactory function.
     *
     * @param u the row number
     * @param v the column number
     * @return the interpolated value
     * @auther António dos Anjos
     */
    public double biLinearInterpolation(double u, double v, ChannelModel channel) {
        int r0 = (int) u;
        int c0 = (int) v;

        double dr = u - r0; // dy
        double dc = v - c0; // dx

        double val1 = channel.getSafe(r0, c0, -1.0).doubleValue();
        double val2 = channel.getSafe(r0, c0 + 1, -1.0).doubleValue();
        double val3 = channel.getSafe(r0 + 1, c0 + 1, -1.0).doubleValue();
        double val4 = channel.getSafe(r0 + 1, c0, -1.0).doubleValue();

        double biLinInterp = linearInterpolation(linearInterpolation(val1, val2, dc), linearInterpolation(val3, val4, dc), dr);

        return biLinInterp;

    }

    /**
     * The linear interpolation function.
     *
     * @param val1 the first double value
     * @param val2 the second double value
     * @param displacement the displacement to the value to be interpolated
     * @return the interpolated value.
     */
    public double linearInterpolation(double val1, double val2, double displacement) {
        return val1 * (1 - displacement) + val2 * displacement;
    }

    /**
     * The Bicubic spline function. Nicely illustrated
     * here:http://www.paulinternet.nl/?page=bicubic
     *
     * @param p a vector of 4 data points
     * @param x the displacement to the input
     * @return the interpolated value
     *
     */
    public double cubicInterpolation(double[] p, double x) {
        return p[1] + 0.5 * x * (p[2] - p[0] + x * (2.0 * p[0] - 5.0 * p[1] + 4.0 * p[2] - p[3] + x * (3.0 * (p[1] - p[2]) + p[3] - p[0])));

    }

    /**
     * The bicubic interpolation function.
     *
     * @param u the row
     * @param v the column
     * @return the interpolated value.
     */
    public double bicubicInterpolation(double u, double v, ChannelModel channel) {
        int r0=(int) u;
        int c0=(int) v;
        double dr = u - r0; // dy
        double dc = v - c0; // dx
        double [] mat= new double [4];
        double [] res= new double [4];
        
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++) {
                mat[j]=channel.getSafe(i+r0, j+c0, -1).doubleValue();
            }
            res[i]=cubicInterpolation(mat, dc);
        }
        //mat=null;
       
        //System.gc();
        double value= cubicInterpolation(res, dr);
        //res=null;
        return value;

    }

    /**
     * The cubic BSpline interpolation. See page 725 in book:Digital Media
     * Processing DSP Algorithms in C
     *
     * @param p is double vector of 4 data points
     * @param x is the displacements vector
     * @return the interpolated value.
     */
    public double cubicBSplineInteroplation(double[] p, double[] x) {

        double interpolatedValue = 0;
        for (int i = 0; i < x.length; i++) {
            double absX = Math.abs(x[i]);
            double coffecient = 0;
            // now for each data point p we calculate the coffcient based on the distance x from the desired point to estimate.

            if (absX < 1) {
                coffecient = (1.0 / 6.0) * (Math.abs(Math.pow(x[i] + 2.0, 3.0)) - 4.0 * Math.abs(Math.pow(x[i] + 1.0, 3.0)) + 6.0 * Math.abs(Math.pow(x[i], 3.0)));
            } else if (absX <= 2) {
                coffecient = (1.0 / 6.0) * (Math.abs(Math.pow(x[i] + 2.0, 3.0)) - 4.0 * Math.abs(Math.pow(x[i] + 1.0, 3.0)) + 6.0 * Math.abs(Math.pow(x[i], 3.0)) - 4.0 * Math.abs(Math.pow(x[i] - 1.0, 3.0)));
            }

            // multiply the coffecient by the input data point
            interpolatedValue += p[i] * coffecient;
        }

        return interpolatedValue;
    }

    /**
     * The BiCubic BSpline interpolation function.
     *
     * @param u the number of the row
     * @param v the number of the column
     * @return the interpolated value.
     * @see See page 725 in book:Digital Media Processing DSP Algorithms in C.
     */
    public double biCubicBSplineInteroplation(double u, double v, ChannelModel channel) {
        int r0 = (int) u;
        int c0 = (int) v;
        double dr = u - r0; // dy
        double dc = v - c0; // dx
        // get the 16 neighbors
        double[][] mat = new double[4][4];
        for (int r = r0; r < r0 + 4; r++) {
            for (int c = c0; c < c0 + 4; c++) {
                mat[r][c] =  channel.getSafe(r, c, -1.0).doubleValue();
            }
        }

        double[] res = new double[4];
        double[] hdisplacements = new double[4];
        double[] vdisplacements = new double[4];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                hdisplacements[j] = Math.abs((double) j - 2.5); //  we assume the center is at the (c0,r0)
            }
            res[i] = cubicBSplineInteroplation(mat[i], hdisplacements);
            vdisplacements[i] = Math.abs((double) i - 2.5);
        }
        return cubicBSplineInteroplation(res, vdisplacements);
    }
}
