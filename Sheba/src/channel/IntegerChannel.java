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

/**
 * An integer 2-D matrix for representing integer type images
 *
 * @author faroqal-tam
 */
public class IntegerChannel implements ChannelModel<Short> {

    private char code;
    private int[] matrix;
    private int numRows;
    private int numCols;
    private Region ROI;

    public IntegerChannel(char code, int[][] matrix) {
        this.code = code;
        this.numRows = matrix.length;
        this.numCols = matrix[0].length;
        this.matrix = new int[numRows * numCols];
        int index = 0;
        for (int[] vector : matrix) {
            System.arraycopy(vector, 0, this.matrix, index, numCols);
            index += numCols;
        }
    }

    public IntegerChannel(char code, int[] matrix, int rows, int cols) {
        this.code = code;
        this.numRows = rows;
        this.numCols = cols;
        this.matrix = matrix;


    }

    public IntegerChannel(IntegerChannel channel) {
        this.code = channel.code;
        int rows = channel.numRows();
        int cols = channel.numCols();
        int[][] inMatrix = channel.getInternalMatrix();

        this.matrix = new int[rows * cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(inMatrix[i], 0, this.matrix[i], 0, cols);
        }
    }

    public IntegerChannel(char code, int rows, int columns) {
        this.code = code;
        this.matrix = new int[rows * columns];
        numRows = rows;
        numCols = columns;
    }

    /**
     * Return the data point in (r,c) cell, with border postponing. It can be
     * used by other method to support numeric different data types. Do not use
     * this method unless you are working with general purpose function. For
     * example, if you are writing a filter for int type channel use get
     * function instead.
     *
     * @param r
     * @param c
     * @param border
     * @see #get(int, int, double)
     * @return
     */
    @Override
    public Number getSafe(int r, int c, Number border) {
        return get(r, c, border.intValue());
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
        set(r, c, value.intValue());
    }

    @Override
    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public int[][] getInternalMatrix() {
        int[][] mat = new int[numRows()][numCols()];
        int index = 0;
        for (int[] vector : mat) {
            System.arraycopy(matrix, index, vector, 0, numCols());
            index += numCols();
        }
        return mat;

    }

    public void setInternalMatrix(int[] matrix) {
        this.matrix = matrix;
    }

    @Override
    public int numCols() {
        return numCols;
    }

    @Override
    public int numRows() {
        return numRows;
    }

    public IntegerChannel toShortChannel(double threshold, int ubound, int lbound) {

        int[] intmat = MatrixMath.toInteger(matrix, threshold, ubound, lbound);
        return new IntegerChannel('\0', intmat, numRows, numCols);
    }

    public int get(int r, int c) {
        return matrix[r * numCols() + c];
    }

    public void set(int r, int c, int value) {
        matrix[r * numCols() + c] = value;
    }

    public int get(int r, int c, int border) {
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
        return this.get(r, c);
    }

    public int[][] getSubmat(int startR, int startC, int endR, int endC) {

        int inmat[][] = new int[endC - startC][endR - startR];
        for (int i = startR; i < endR; i++) {
            for (int j = startC; j < endC; j++) {
                inmat[i - startR][j - startC] = get(i, j);
            }
        }

        return inmat;
    }

    public int[] getRawData() {
        return this.matrix;
    }

    @Override
    public IntegerChannel getSubChannel(int startR, int startC, int endR, int endC) {
        return new IntegerChannel(code, getSubmat(startR, startC, endR, endC));
    }

    /**
     * Get a sub matrix of size
     * <code> n*n </code> centered at (c,r).
     *
     * @param r
     * @param c
     * @param n
     * @return
     */
    public int[][] getNeighborsMatrix(int r, int c, int n) {
        int radius = n / 2 + 1;
        int[][] neighbors = new int[n][n];
        for (int a = -radius; a <= radius; a++) {
            for (int b = -radius; b <= radius; b++) {
                neighbors[a + radius][b + radius] = get(r + a, c + b, (int) -1);
            }
        }
        return neighbors;

    }

    /**
     * Get a sub matrix as in a row-wise vector of length
     * <code> n*n </code> centered at (c,r).
     *
     * @param r
     * @param c
     * @param n
     * @return
     */
    public int[] getNeighborsVector(int r, int c, int n) {
        int radius = n / 2 + 1;
        int[] neighbors = new int[n * n];
        for (int a = -radius; a <= radius; a++) {
            for (int b = -radius; b <= radius; b++) {
                neighbors[(a + radius) * n + (b + radius)] = get(r + a, c + b, (int) -1);
            }
        }
        return neighbors;

    }

    public DoubleChannel toDoubleChannel() {
        double[] doublemat = MatrixMath.toDouble(matrix);
        return new DoubleChannel(this.code, doublemat, numRows, numCols);
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
