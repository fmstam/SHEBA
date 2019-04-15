/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel;

import util.Region;
import math.MatrixMath;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import org.ejml.data.DenseMatrix64F;
import org.ejml.data.Matrix64F;
import org.ejml.simple.SimpleMatrix;

/**
 * A double 2-D matrix class that represent a single image channel.
 * @author faroqal-tam with base on a code written by António dos Anjos
 */
public class DoubleChannel extends SimpleMatrix implements ChannelModel<Double> {

    private char code; // R=red, G=green, S=grayscale, ......
    private Region ROI;

    @Override
    public char getCode() {
        return code;

    }

    public void setCode(char code) {
        this.code = code;
    }

    public DoubleChannel(char code, int numRows, int numCols, boolean bln, double... doubles) {
        super(numRows, numCols, bln, doubles);
        this.code = code;
    }

    public DoubleChannel(char code, double[][] doubles) {
        super(doubles);
        this.code = code;
    }

    public DoubleChannel(char code, int numRows, int numCols) {
        super(numRows, numCols);
        this.code = code;
    }

    public DoubleChannel(char code) {
        this.code = code;
    }

    public DoubleChannel(char code, double[] mat, int rows, int cols) {
        super(new DenseMatrix64F(rows, cols, true, mat));
        this.code = code;

    }

    public DoubleChannel(DoubleChannel channel) {
        super(channel);
        this.code = channel.getCode();
    }

    public DoubleChannel(ChannelModel channel) {
        super(channel.numRows(), channel.numCols());
        int rows = channel.numRows();
        int cols = channel.numCols();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double value = channel.getSafe(r, c, -1).doubleValue();
                set(r, c, value);
            }
        }

        this.code = channel.getCode();
    }

    /**
     * Return the data point at the position r (row) and (c) column, and the
     * nearest border (if border=-1) if r and/or c is out the matrix bounds.
     *
     * @param r the row
     * @param c the column
     * @param border if =-1 return the nearest border if r and/or c is out of
     * the the matrix bound.
     *
     */
    public double get(int r, int c, double border) {
        int rows = this.numRows();
        int cols = this.numCols();

        if ((r < 0) || (r >= rows) || (c < 0) || (c >= cols)) {
            if (border == -1) {
                if (r < 0) {
                    r = 0;
                }
                if (r >= rows) {
                    r = rows - 1;
                }
                if (c < 0) {
                    c = 0;
                }
                if (c >= cols) {
                    c = cols - 1;
                }
            } else {
                return border; //value specified by border
            }
        }
        return get(r, c);
    }

    /**
     * Return the data point in (r,c) cell, with border postponing. It can be
     * used by other method to support numeric different data types. Do not use
     * this method unless you are working with general purpose function. For
     * example, if you are writing a filter for double type channel use
     * <code>get</code> function instead.
     *
     * @param r
     * @param c
     * @param border
     * @see #get(int, int, double)
     * @return
     */
    @Override
    public Number getSafe(int r, int c, Number border) {
        return get(r, c, border.doubleValue());
    }

    /**
     * Set the data at (r,c) by "value" input. It supports any numeric type. Do
     * not use this method unless you are working with general purpose function.
     *
     * @param r
     * @param c
     * @param value
     */
    @Override
    public void setSafe(int r, int c, Number value) {
        set(r, c, value.doubleValue());
    }

    public double[] getRawData() {

        return this.mat.getData();
    }

    /**
     * Return a double matrix of of a given rectangle specified by the four
     * parameters.
     *
     * @param startR the start row
     * @param startC the start column
     * @param endR the end row
     * @param endC the end column
     * @return
     */
    public double[][] getMat(int startR, int startC, int endR, int endC, double border) {

        double inmat[][] = new double[endR - startR][endC - startC];
        for (int i = startR; i < endR; i++) {
            for (int j = startC; j < endC; j++) {
                inmat[i - startR][j - startC] = get(i, j, border);
            }
        }
        return inmat;
    }

    /**
     * Get a sub matrix of length
     * <code> n*n </code> centered at (c,r).
     *
     * @param r
     * @param c
     * @param n
     * @return
     */
    public double[][] getNeighborsMatrix(int r, int c, int n) {
        int radius = n / 2 + 1;
        double[][] neighbors = new double[n][n];
        for (int a = -radius; a <= radius; a++) {
            for (int b = -radius; b <= radius; b++) {
                neighbors[a + radius][b + radius] = get(r + a, c + b, -1.0);
            }
        }
        return neighbors;

    }

    /**
     * Get a sub matrix as in a row-wise vector of length
     * <code> n*n </code> centered at (c,r).
     *
     * @param r the row
     * @param c the column
     * @param n the width of the kernel
     * @return
     */
    public double[] getNeighborsVector(int r, int c, int n) {
        int radius = n / 2;
        double[] neighbors = new double[n * n];
        for (int a = -radius; a <= radius; a++) {
            for (int b = -radius; b <= radius; b++) {
                neighbors[(a + radius) * n + (b + radius)] = get(r + a, c + b, -1.0);
            }
        }
        return neighbors;

    }

    public double[] getRow(int i) {
        return extractVector(true, i).getMatrix().getData();
    }

    public double[] getColumn(int i) {
        return extractVector(false, i).getMatrix().getData();
    }

    public DoubleChannel(int numRows, int numCols, boolean rowMajor, double... data) {
        super(numRows, numCols, rowMajor, data);
    }

    public DoubleChannel(double[][] data) {
        super(data);
    }

    public DoubleChannel(int numRows, int numCols) {
        super(numRows, numCols);
    }

    public DoubleChannel(SimpleMatrix orig) {
        super(orig);
    }

    public DoubleChannel(DenseMatrix64F orig) {
        super(orig);
    }

    public DoubleChannel(Matrix64F orig) {
        super(orig);
    }

    public DoubleChannel() {
    }

    @Override
    public DoubleChannel getSubChannel(int startR, int startC, int endR, int endC) {
        return new DoubleChannel(extractMatrix(startR, endR, startC, endC));
    }

    /**
     * Return the internal matrix
     *
     * @return 2D double matrix
     */
    public double[][] getInternalMatrix() {
        double[][] inMat = new double[numRows()][numCols()];
        double[] internal = this.mat.getData();
        int index = 0;
        for (double[] vector : inMat) {
            System.arraycopy(internal, index, vector, 0, numCols());
            index += numCols();
        }
        return inMat;

    }

    // conversions goes here
    /**
     * Convert (binarize) this channel to
     * <code> IntegerChannel </code> given a threshold and two values.
     *
     * @param threshold
     * @param ubound
     * @param lbound
     * @return
     */
    public IntegerChannel toIntegerChannel(double threshold, int ubound, int lbound) {

        int[] intmat = MatrixMath.toInteger(this.mat.getData(), threshold, ubound, lbound);
        return new IntegerChannel('\0', intmat, numRows(), numCols());
    }

    /**
     * Convert this channel to
     * <code> IntegerChannel </code>.
     *
     * @return
     */
    public IntegerChannel toIntegerChannel() {

        int[] intmat = MatrixMath.toInteger(this.mat.getData());
        return new IntegerChannel(this.code, intmat, numRows(), numCols());
    }

    // here goes the handling of the ROI
    @Override
    public void setROI(Region roi) {
        this.ROI = roi;
    }

    @Override
    public void setROI(Rectangle rect) {
        this.ROI = new Region(rect);
    }

    @Override
    public void setROI(int r, int c, int lr, int lc) {
        this.ROI = new Region(r, c, lr, lc);
    }

    @Override
    public void setROI(LinkedList<Point> pointList) {
        this.ROI = new Region(pointList);
    }

    @Override
    public void clearROI() {
        if (ROI != null) {
            ROI.reset();
        }
    }

    @Override
    public Region getROI() {
        return ROI;
    }
}   