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

package util;

/**
 *
 * @author faroq
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.Vector;
import java.awt.Point;
import util.st.Pixel;

/**
 *
 * @author aanjos
 */
public class Basin {

    int max = 0;
    int min;
    final int BIG = Integer.MAX_VALUE;
    int maxX = 0;
    int minX = BIG;
    int maxY = 0;
    int minY = BIG;
    int whiteValue;
    List<Pixel> pixels = null;
    int[][] mxCCImage = null;

    public Basin(int whiteValue) {
        this.whiteValue = whiteValue;
        min = whiteValue;
        pixels = new Vector<Pixel>();
    }

    public void addPixel(Pixel p) {
        if (p == null) {
            System.out.println("you suck");
        }
        pixels.add(p);
        int intensity = p.getIntensity();
        int row = p.getRow();
        int column = p.getColumn();
        if (intensity < min) {
            min = intensity;
        }
        if (intensity > max) {
            max = intensity;
        }
        if (row < minY) {
            minY = row;
        }
        if (column < minX) {
            minX = column;
        }
        if (row > maxY) {
            maxY = row;
        }
        if (column > maxX) {
            maxX = column;
        }
    }

    public List<Pixel> getBasinData() {
        return pixels;
    }

    public double getMean() {
        int size = pixels.size();
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += pixels.get(i).getIntensity();
        }
        return total / size;
    }

    public int getArea() {
        return pixels.size();
    }

    public int getVolume() {
        int size = pixels.size();
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += pixels.get(i).getIntensity();
        }
        return total;
    }

    public int getMaximum() {
        return max;
    }

    public int getMinimum() {
        return min;
    }

    public Point getMinPoint() {
        return new Point(minX, minY);
    }

    public Pixel getMinima() {
        int size = pixels.size();

        Pixel minima = new Pixel(-1, -1, whiteValue + 1);
        for (int i = 0; i < size; i++) {
            if (pixels.get(i).getIntensity() < minima.getIntensity()) {
                minima = pixels.get(i);
            }
        }
        return minima;
    }

    public Point getMinimumPoint() {
        int size = pixels.size();
        Point pt = new Point(0, 0);
        
        Pixel minima = new Pixel(-1, -1, whiteValue + 1);
        for (int i = 0; i < size; i++) {
            if (pixels.get(i).getIntensity() < minima.getIntensity()) {
                minima = pixels.get(i);
            }
        }
        pt.setLocation(minima.getColumn(), minima.getRow());
        
        return pt;
    }

    public int getCenterOfMassXX() {
        int size = pixels.size();
        double xx = 0, ints = 0;
        int intens = 0;
        for (int i = 0; i < size; i++) {
            intens = whiteValue - pixels.get(i).getIntensity();
            xx += (pixels.get(i).getColumn() * intens);
            ints += intens;
        }
        if (ints == 0) {
            ints = 1; // acontece se componente só tem pretos
        }
        return (int) (xx / ints);
    }

    public int getCenterOfMassYY() {
        int size = pixels.size();
        double yy = 0, ints = 0;
        int intens = 0;
        for (int i = 0; i < size; i++) {
            intens = whiteValue - pixels.get(i).getIntensity();
            ints += intens;
            yy += (pixels.get(i).getRow() * intens);
        }
        if (ints == 0) {
            ints = 1; // acontece se componente só tem pretos
        }
        return (int) (yy / ints);
    }

    public int getCentroidXX() {
        int size = pixels.size();
        int xx = 0, ints = 0;
        for (int i = 0; i < size; i++) {
            xx += pixels.get(i).getColumn();

        }
        if (ints == 0) {
            ints = 1; // acontece se componente só tem pretos
        }
        return (int) (xx / size);
    }

    public int getCentroidYY() {
        int size = pixels.size();
        int xx = 0, ints = 0;
        for (int i = 0; i < size; i++) {
            xx += pixels.get(i).getRow();

        }
        if (ints == 0) {
            ints = 1; // acontece se componente só tem pretos
        }
        return (int) (xx / size);
    }

    public double getStandardDev() {
        int[] histogram = new int[whiteValue + 1];
        double sum = 0, val;
        double mean = getMean();

        int size = pixels.size();

        for (int i = 0; i < whiteValue + 1; i++) {
            histogram[i] = 0;
        }

        for (int i = 0; i < size; i++) {
            histogram[pixels.get(i).getIntensity()]++;
        }

        for (int i = 0; i < whiteValue + 1; i++) {
            val = Math.pow((i - mean), 2);
            sum = sum + val * histogram[i];
        }
        sum = sum / size;

        return Math.sqrt(sum);
    }

    public int[][] fillMatrixWithBasin() { // the matrix has to be already created
        int border = 2;
        mxCCImage = new int[(maxY - minY + 1) + border][(maxX - minX + 1) + border]; // to have a border around the component
        int n = pixels.size();
        //System.out.println("Note: filling matrix with border of "+ border);
        //System.out.print(".");
        for (int i = 0; i < mxCCImage.length; i++) {
            for (int j = 0; j < mxCCImage[0].length; j++) {
                mxCCImage[i][j] = whiteValue; // not part of the image
            }
        }
        for (int i = 0; i < n; i++) {
            mxCCImage[pixels.get(i).getRow() - minY + 1][pixels.get(i).getColumn() - minX + 1] = pixels.get(i).getIntensity(); //start after the border (+1)
        }
        return mxCCImage;
    }
}