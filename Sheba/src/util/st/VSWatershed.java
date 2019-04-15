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

package util.st;

import image.DoubleImage;
import image.filter.ImageFilterPlugin;
import java.util.Vector;
import java.util.Collections;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import util.Basin;
import util.Utilities;
import util.st.Pixel;

/**
 *
 * @author faroq
 */
public class VSWatershed{

final int INIT = -1;
    final int MASK = -2;
    public final static int WSHED = 0;
    final Pixel FICT = new Pixel(-1, -1, -1);
    List<Pixel> sortedPixels = null;
    int vSize;
    List<Pixel> neighbours = null;
    int curLab;
    Pixel[][] imageMatrix = null;
    int[][] labelMatrix = null;
    int[][] distanceMatrix = null;
    int height;
    int width;
    int[][] watershedLines = null;
    int[][] completeWatershed = null;
    int whiteValue;
    Basin[] basins = null;
    int bg = 0; // saves the background value of the label image (background is allways(?) the biggest CC

    public VSWatershed(Pixel[][] imageMatrix, int maxNumberOfIntensities) { //maxIntensity so I can build a watershed lines matrix with proper white value
        this.imageMatrix = imageMatrix;
        whiteValue = maxNumberOfIntensities - 1;
        height = imageMatrix.length;
        width = imageMatrix[0].length;
        sortedPixels = new Vector<Pixel>();
        initData();
        //System.out.println("Num ints: "+ sortedPixels.get(sortedPixels.size() - 1).getIntensity());
        flood(sortedPixels.get(0).getIntensity(), sortedPixels.get(sortedPixels.size() - 1).getIntensity() + 1); //without +1 it gets incomplete

        doWatershedLines(); // it will be useful later
    }

    void initData() {
        labelMatrix = new int[height][width];
        distanceMatrix = new int[height][width];
        watershedLines = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                labelMatrix[row][column] = INIT;
                distanceMatrix[row][column] = 0;
                sortedPixels.add(imageMatrix[row][column]);
                watershedLines[row][column] = whiteValue;
            }
        }
        vSize = sortedPixels.size();
        Collections.sort(sortedPixels);
    }

    void flood(int hMin, int hMax) {
        curLab = 0;
        int curDist = 0;
        int pos = 0;
        int lastPos = 0; //starting position of the sorted vector
        Pixel pix, pixTmp;
        Queue<Pixel> queue = new LinkedList<Pixel>();

        for (int h = hMin; h < hMax; h++) {
            if ((lastPos < vSize) && (((Pixel) sortedPixels.get(lastPos)).getIntensity() == h)) {

                while (true) {
                    if (pos == vSize) {
                        break;
                    }
                    pix = (Pixel) sortedPixels.get(pos);
                    if (pix.getIntensity() == h) {
                        pos++;
                    } else {
                        break;
                    }

                    labelMatrix[pix.getRow()][pix.getColumn()] = MASK;
                    neighboursOf(pix);

                    for (int i = 0; i < neighbours.size(); i++) {
                        pixTmp = (Pixel) neighbours.get(i);

                        if ((labelMatrix[pixTmp.getRow()][pixTmp.getColumn()] > 0) || (labelMatrix[pixTmp.getRow()][pixTmp.getColumn()] == WSHED)) {
                            distanceMatrix[pix.getRow()][pix.getColumn()] = 1;
                            queue.offer(pix);
                            break;
                        }
                    }
                }

                curDist = 1;
                queue.offer(FICT); //Ficticious


                //loop
                while (true) {
                    pix = queue.remove();
                    if (pix.getRow() == -1) { //Ficticious
                        if (queue.isEmpty()) {
                            break;
                        } else {
                            queue.offer(FICT);
                            curDist++;
                            pix = queue.remove();
                        }
                    }

                    neighboursOf(pix);

                    for (int i = 0; i < neighbours.size(); i++) {
                        pixTmp = (Pixel) neighbours.get(i);

                        if ((distanceMatrix[pixTmp.getRow()][pixTmp.getColumn()] < curDist) && ((labelMatrix[pixTmp.getRow()][pixTmp.getColumn()] > 0) || (labelMatrix[pixTmp.getRow()][pixTmp.getColumn()] == WSHED))) { //belongs to basin or watersheds
                            if (labelMatrix[pixTmp.getRow()][pixTmp.getColumn()] > 0) {
                                if ((labelMatrix[pix.getRow()][pix.getColumn()] == MASK) || (labelMatrix[pix.getRow()][pix.getColumn()] == WSHED)) {
                                    labelMatrix[pix.getRow()][pix.getColumn()] = labelMatrix[pixTmp.getRow()][pixTmp.getColumn()];
                                } else if (labelMatrix[pix.getRow()][pix.getColumn()] != labelMatrix[pixTmp.getRow()][pixTmp.getColumn()]) {
                                    labelMatrix[pix.getRow()][pix.getColumn()] = WSHED; //System.out.println(pix.row+ "," + pix.column + " = WSHED - (pix.label != pixTemp.label)");
                                }
                            } else if (labelMatrix[pix.getRow()][pix.getColumn()] == MASK) {
                                //printLabels(); printDists();
                                labelMatrix[pix.getRow()][pix.getColumn()] = WSHED; //System.out.println(pix.row+ "," + pix.column + " = WSHED - (pix.label == MASK)");
                            }
                        } else if ((labelMatrix[pixTmp.getRow()][pixTmp.getColumn()] == MASK) && (distanceMatrix[pixTmp.getRow()][pixTmp.getColumn()] == 0)) { // pixTemp is plateau
                            distanceMatrix[pixTmp.getRow()][pixTmp.getColumn()] = curDist + 1;
                            queue.offer(pixTmp);
                        }
                    }
                }

                //detect and process new minima at level h
                pos = lastPos;
                while (true) {
                    if (pos == vSize) {
                        break;
                    }
                    pix = (Pixel) sortedPixels.get(pos);
                    if (pix.getIntensity() == h) {
                        pos++;
                    } else {
                        break;
                    }

                    distanceMatrix[pix.getRow()][pix.getColumn()] = 0;

                    if (labelMatrix[pix.getRow()][pix.getColumn()] == MASK) { // pix is inside a new minimum
                        curLab++;
                        queue.offer(pix);
                        labelMatrix[pix.getRow()][pix.getColumn()] = curLab;

                        while (!(queue.isEmpty())) {
                            pixTmp = queue.remove();
                            neighboursOf(pixTmp);

                            for (int i = 0; i < neighbours.size(); i++) {
                                pixTmp = (Pixel) neighbours.get(i);
                                if (labelMatrix[pixTmp.getRow()][pixTmp.getColumn()] == MASK) {
                                    queue.offer(pixTmp);
                                    labelMatrix[pixTmp.getRow()][pixTmp.getColumn()] = curLab;
                                }
                            }
                        }
                    }
                }
                lastPos = pos;
            }//IF da TRETA
        }
    }

    // fill the list neighbours with with the 8-conn neighbours of pix
    public void neighboursOf(Pixel pix) {
        neighbours = new Vector<Pixel>();
        int r = pix.getRow();
        int c = pix.getColumn();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((c - 1 + j) >= 0 && (c - 1 + j) < width && (r - 1 + i) >= 0 && (r - 1 + i) < height && !((c - 1 + j) == c && (r - 1 + i) == r)) {
                    neighbours.add(imageMatrix[r - 1 + i][c - 1 + j]);
                }
            }
        }
    }

    public void neighboursOf(int r, int c) {
        neighbours = new Vector<Pixel>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((c - 1 + j) >= 0 && (c - 1 + j) < width && (r - 1 + i) >= 0 && (r - 1 + i) < height && !((c - 1 + j) == c && (r - 1 + i) == r)) {
                    neighbours.add(imageMatrix[r - 1 + i][c - 1 + j]);
                }
            }
        }
    }

    public Pixel[][] getImageMatrix() {
        return imageMatrix;
    }

    public int[][] getLabelMatrix() {
        return labelMatrix;
    }

    public int[][] getWatershedLines() {
        return watershedLines;
    }

    /*   public int[][] getCompleteWatershedLines(){
    if (completeWatershed == null)
    completeWatershedLines();
    return completeWatershed;
    }*/
    void doWatershedLines() {
        for (int i = 0; i < height; i++) { //complete the watersheds
            for (int j = 0; j < width; j++) {
                neighboursOf(imageMatrix[i][j]);
                for (int p = 0; p < neighbours.size(); p++) {
                    Pixel pixTemp = (Pixel) neighbours.get(p);
                    if (labelMatrix[i][j] != WSHED) {
                        if (labelMatrix[i][j] != labelMatrix[pixTemp.getRow()][pixTemp.getColumn()]) {
                            labelMatrix[pixTemp.getRow()][pixTemp.getColumn()] = WSHED;
                        }
                    }
                }
            }
        }

        // Do the lines. I don't remember what is the isAllWatershed for????!!!!
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (labelMatrix[i][j] == WSHED /*&& !(isAllWatershed(imageMatrix[i][j]))*/) {
                    watershedLines[i][j] = WSHED;
                }

            }
        }
    }

    public boolean isAllWatershed(Pixel pix) {
        Pixel pixTemp;
        neighboursOf(pix);

        for (int i = 0; i < neighbours.size(); i++) {
            pixTemp = (Pixel) neighbours.get(i);
            if (labelMatrix[pixTemp.getRow()][pixTemp.getColumn()] != WSHED) {
                return false;
            }
        }
        return true;
    }

    public boolean isAllWatershed(int r, int c, int val) {
        Pixel pixTemp;
        Pixel pix = new Pixel(r, c, val);
        neighboursOf(pix);

        for (int i = 0; i < neighbours.size(); i++) {
            pixTemp = (Pixel) neighbours.get(i);
            if (labelMatrix[pixTemp.getRow()][pixTemp.getColumn()] != WSHED) {
                return false;
            }
        }
        return true;
    }

    public Basin[] getBasins() {
        if (basins == null) {
            splitBasins();
        }

        return basins;
    }

    public Basin[] getBasins2() {
        if (basins == null) {
            splitBasins2();
        }

        return basins;
    }

    //splits the basins in to separate components
    private void splitBasins() {
        basins = new Basin[curLab + 1]; //+1??
        for (int i = 0; i < curLab + 1; i++) {
            basins[i] = new Basin(whiteValue);
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                //System.out.println("index: " + labelMatrix[row][col]);
                if ((labelMatrix[row][col] != WSHED) /*&& (labelMatrix[row][col] != bg)*/) // avoid the watershed lines
                {
                    basins[labelMatrix[row][col]].addPixel(imageMatrix[row][col]);
                }
            }
        }
    }

    //para compensar os 2 pixels que estou a perder em volta do componente
    private void splitBasins2() {
        basins = new Basin[curLab + 1]; //+1??
        for (int i = 0; i < curLab + 1; i++) {
            basins[i] = new Basin(whiteValue);
        }
        //Utilities.doSaveIntsAsImage(labelMatrix, "/home/aanjos/imgs/Anders/requantification/Minha/treta/labels.tiff", DataBuffer.TYPE_BYTE);
        int[] histogram = Utilities.getHistogram(labelMatrix, curLab + 1);
        int max = 0; // to figure out what is the background. It should be the largest CC! Find alternative way!!!!
//System.out.println("VERIFY THIS! What should be BG?");
        System.out.println("-------------");
        for (int i = 0; i < curLab + 1; i++) {
            if (histogram[i] > max) {
                max = histogram[i];
                bg = i;
            }
        }

        dilateComponents(2, bg);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                //System.out.println("index: " + labelMatrix[row][col]);
                if (labelMatrix[row][col] > 0) // avoid the watershed lines
                {
                    if (labelMatrix[row][col] != bg /*&& labelMatrix[row][col] != 1*/) { //TRATADO no validate (OLD:it's is not the background component and not the border component
                        basins[labelMatrix[row][col]].addPixel(imageMatrix[row][col]);
                    }
                }
            }
        }
    }

    //enche os componentes com a label até encontrar linhas de watershed
/*    private void dilateComponents__(int times) {
    int kCross[][] = {{0, 1, 0}, {1, 1, 1}, {0, 1, 0}};

    int[][] newLabelMx = new int[labelMatrix.length][labelMatrix[0].length];

    int label;
    int bg = 2;
    for (int t = 0; t < times; t++) {
    for (int row = 1; row < height - 1; row++) {
    for (int col = 1; col < width - 1; col++) {
    label = labelMatrix[row][col];
    //System.out.println("Label é: "+label);

    if (label == bg || label == WSHED) {
    //label = 0;
    for (int rk = -1; rk <= 1; rk++) {
    for (int ck = -1; ck <= 1; ck++) {
    if (kCross[rk + 1][ck + 1] == 1) { //System.out.println("r:"+ (row + rk) + " c: " +(col + ck) + " label: "+ labelMatrix[row + rk][col + ck]);
    if (labelMatrix[row + rk][col + ck] != bg && labelMatrix[row + rk][col + ck] != WSHED) {
    label = labelMatrix[row + rk][col + ck];
    //System.out.println("a label é:" + label);
    }
    }
    }
    }
    }
    newLabelMx[row][col] = label;
    }
    }
    for (int r = 1; r < height - 1; r++) {
    for (int c = 1; c < width - 1; c++) {
    labelMatrix[r][c] = newLabelMx[r][c];
    }
    }
    }

    //Utilities.doSaveIntsAsImage(newLabelMx, "/home/aanjos/imgs/Anders/requantification/Minha/treta/labels.tiff", DataBuffer.TYPE_BYTE);
    //System.exit(1);
    }*/
    int[][] dilateComponents_() {
        int times = 1;
        int[][] image = labelMatrix;
        int[][] labels = new int[image.length][image[0].length];


        for (int t = 0; t < times; t++) {
            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image[i].length; j++) {
                    if (image[i][j] != WSHED && image[i][j] != bg) {
                        if (i > 0 && (image[i - 1][j] == WSHED || image[i - 1][j] == bg)) {
                            labels[i - 1][j] = image[i][j];
                            image[i - 1][j] = -2;
                        }
                        if (j > 0 && (image[i][j - 1] == WSHED || image[i][j - 1] == bg)) {
                            labels[i][j - 1] = image[i][j];
                            image[i][j - 1] = -2;
                        }
                        if (i + 1 < image.length && (image[i + 1][j] == WSHED || image[i + 1][j] == bg)) {
                            labels[i + 1][j] = image[i][j];
                            image[i + 1][j] = -2;
                        }
                        if (j + 1 < image[i].length && (image[i][j + 1] == WSHED || image[i][j + 1] == bg)) {
                            labels[i][j + 1] = image[i][j];
                            image[i][j + 1] = -2;
                        }
                    }
                }
            }

            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image[i].length; j++) {
                    if (image[i][j] == -2) {
                        image[i][j] = labels[i][j];
                    }
                }
            }
        }
        //Utilities.doSaveIntsAsImage(image, "/home/aanjos/imgs/Anders/requantification/Minha/treta/labels.tiff", DataBuffer.TYPE_BYTE);

        return image;
    }

    private void dilateComponents(int times, int bg) {
        int kCross[][] = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};

        int[][] newLabelMx = new int[labelMatrix.length][labelMatrix[0].length];

        int label;

        for (int t = 0; t < times; t++) {
            for (int row = 1; row < height - 1; row++) {
                for (int col = 1; col < width - 1; col++) {
                    label = labelMatrix[row][col];
                    //System.out.println("Label é: "+label);

                    if (label == bg || label == WSHED) {
                        //label = 0;
                        for (int rk = -1; rk <= 1; rk++) {
                            for (int ck = -1; ck <= 1; ck++) {
                                if (kCross[rk + 1][ck + 1] == 1) { //System.out.println("r:"+ (row + rk) + " c: " +(col + ck) + " label: "+ labelMatrix[row + rk][col + ck]);
                                    if (labelMatrix[row + rk][col + ck] != bg && labelMatrix[row + rk][col + ck] != WSHED) {
                                        label = labelMatrix[row + rk][col + ck];
                                        //System.out.println("a label é:" + label);
                                    }
                                }
                            }
                        }
                    }
                    newLabelMx[row][col] = label;
                }
            }
            for (int r = 1; r < height - 1; r++) {
                for (int c = 1; c < width - 1; c++) {
                    labelMatrix[r][c] = newLabelMx[r][c];
                }
            }
        }

        //Utilities.doSaveIntsAsImage(newLabelMx, "/home/aanjos/imgs/Anders/requantification/Minha/treta/labels.tiff", DataBuffer.TYPE_BYTE);
        //System.exit(1);
    }

    public List<Pixel> getNeighbours() {
        return neighbours;
    }

    public int getBackgroundValue() {
        return bg;
    }

   
}
