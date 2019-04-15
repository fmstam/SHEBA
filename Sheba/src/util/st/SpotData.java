/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.st;

import java.awt.*;

/**
 *
 * @author aanjos
 */
public class SpotData implements java.io.Serializable {

    int[][] componentImage;
    int[][] contourImage; // this image has the initial contour having value 0
    double contourSize = 0; // number of pixels of the contour
    Point start; // position relative to the original image
    int contourValue; //intensity of contour

    public SpotData(int[][] componentImage, Point start, int contourValue) {
        this.start = start;
        this.contourValue = contourValue;
        this.componentImage = componentImage;

        extractContour(componentImage);
        processContour1();
        processContour2();
        processContour3();
        processContour0();
        contourSizeCalc();

        binarizeInverted(componentImage);
    }

    // metido À pressão para os momentos
    private void binarizeInverted(int[][] image) {
        for (int r = 0; r < image.length; r++) {
            for (int c = 0; c < image[0].length; c++) {
                if (image[r][c] == contourValue) {
                    image[r][c] = 0;
                } else {
                    image[r][c] = contourValue;
                }
            }
        }
    }

    public int[][] getContour() {
        return contourImage;
    }

    public int[][] getComponent() {
        return componentImage;
    }

    public Point getStart() {
        return start;
    }

    private void extractContour(int[][] image) {
        int val;
        boolean pos_1 = false, pos_2 = false, pos_3 = false, pos_4 = false;
        int x = 0;
        int d_x = image[0].length;
        int y = 0;
        int d_y = image.length;
        contourImage = new int[d_y][d_x];

        //System.out.print("("+d_x+","+d_y+") ");

        for (int row = (y); row < (d_y); row++) {
            for (int col = (x); col < d_x; col++) {
                contourImage[row][col] = 0;
            }
        }

        for (int row = (y + 1); row < (d_y - 1); row++) {
            for (int col = (x + 1); col < (d_x - 1); col++) {
                pos_1 = pos_2 = pos_3 = pos_4 = false;
                val = image[row][col];
                if (val != contourValue) {
                    if (image[row - 1][col] == contourValue) {
                        pos_1 = true;
                    }
                    if (image[row][col + 1] == contourValue) {
                        pos_2 = true;
                    }
                    if (image[row + 1][col] == contourValue) {
                        pos_3 = true;
                    }
                    if (image[row][col - 1] == contourValue) {
                        pos_4 = true;
                    }

                    if (pos_1 || pos_2 || pos_3 || pos_4) {
                        contourImage[row][col] = contourValue;
                        //contourSize++;
                        //fprintf(stdout, "val %d, pos_1 %d, pos_2 %d, pos_3 %d, pos_4 %d\n", val, pos_1, pos_2, pos_3, pos_4);
                    }
                }
            }
        }
    }

    private void contourSizeCalc() {
        for (int row = 0; row < contourImage.length; row++) {
            for (int col = 0; col < contourImage[0].length; col++) {
                if (contourImage[row][col] == contourValue) {
                    contourSize++;
                }
            }
        }
    }

    public double getPerimeter(){
        return contourSize;
    }

    private void processContour0() { //avoid connected corners
        for (int row = 0; row < contourImage.length; row++) {
            for (int col = 0; col < contourImage[0].length; col++) {
                if (contourImage[row][col] == contourValue) {
                    if (((contourImage[row - 1][col] == contourValue) && (contourImage[row][col + 1] == contourValue)) || ((contourImage[row - 1][col] == contourValue) && (contourImage[row][col - 1] == contourValue))
                            || ((contourImage[row + 1][col] == contourValue) && (contourImage[row][col + 1] == contourValue)) || ((contourImage[row + 1][col] == contourValue) && (contourImage[row][col - 1] == contourValue))) {
                        contourImage[row][col] = 0;
                    }
                }
            }
        }
    }

    private void processContour1() { // avoid L connections
        for (int row = 0; row < contourImage.length; row++) {
            for (int col = 0; col < contourImage[0].length; col++) {
                if (contourImage[row][col] == contourValue) {
                    if (((contourImage[row - 1][col] == contourValue) && (contourImage[row][col + 1] == contourValue) && (contourImage[row - 1][col + 1] == contourValue)) || ((contourImage[row - 1][col] == contourValue) && (contourImage[row][col - 1] == contourValue) && (contourImage[row - 1][col - 1] == contourValue))
                            || ((contourImage[row + 1][col] == contourValue) && (contourImage[row][col + 1] == contourValue) && (contourImage[row + 1][col + 1] == contourValue)) || ((contourImage[row + 1][col] == contourValue) && (contourImage[row][col - 1] == contourValue) && (contourImage[row + 1][col - 1] == contourValue))) {
                        contourImage[row][col] = 0;
                    }
                }
            }
        }
    }

    private void processContour2() { //avoid apendices
        int numConn = 0;
        boolean changed;
        do {
            changed = false;
            for (int row = 0; row < contourImage.length; row++) {
                for (int col = 0; col < contourImage[0].length; col++) {
                    if (contourImage[row][col] == contourValue) {
                        numConn = 0;
                        if (contourImage[row][col + 1] == contourValue) {
                            numConn++;
                        }
                        if (contourImage[row - 1][col + 1] == contourValue) {
                            numConn++;
                        }
                        if (contourImage[row - 1][col] == contourValue) {
                            numConn++;
                        }
                        if (contourImage[row - 1][col - 1] == contourValue) {
                            numConn++;
                        }
                        if (contourImage[row][col - 1] == contourValue) {
                            numConn++;
                        }
                        if (contourImage[row + 1][col - 1] == contourValue) {
                            numConn++;
                        }
                        if (contourImage[row + 1][col] == contourValue) {
                            numConn++;
                        }
                        if (contourImage[row + 1][col + 1] == contourValue) {
                            numConn++;
                        }

                        if (numConn < 2) {
                            contourImage[row][col] = 0;
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);
    }

    private void processContour3() { //avoid one pixel apendices
        boolean pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8;

        for (int row = 0; row < contourImage.length; row++) {
            for (int col = 0; col < contourImage[0].length; col++) {
                pos1 = pos2 = pos3 = pos4 = pos5 = pos6 = pos7 = pos8 = false;
                if (contourImage[row][col] == contourValue) {
                    if (contourImage[row][col + 1] == contourValue) {
                        pos1 = true;
                    }
                    if (contourImage[row - 1][col + 1] == contourValue) {
                        pos2 = true;
                    }
                    if (contourImage[row - 1][col] == contourValue) {
                        pos3 = true;
                    }
                    if (contourImage[row - 1][col - 1] == contourValue) {
                        pos4 = true;
                    }
                    if (contourImage[row][col - 1] == contourValue) {
                        pos5 = true;
                    }
                    if (contourImage[row + 1][col - 1] == contourValue) {
                        pos6 = true;
                    }
                    if (contourImage[row + 1][col] == contourValue) {
                        pos7 = true;
                    }
                    if (contourImage[row + 1][col + 1] == contourValue) {
                        pos8 = true;
                    }

                    if ((pos2 && pos3 && pos4) && !(pos1 || pos5 || pos6 || pos7 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos6 && pos7 && pos8) && !(pos1 || pos2 || pos3 || pos4 || pos5)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos8 && pos1 && pos2) && !(pos3 || pos4 || pos5 || pos6 || pos7)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos4 && pos5 && pos6) && !(pos1 || pos2 || pos3 || pos7 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos3 && pos5) && !(pos1 || pos2 || pos4 || pos6 || pos7 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos5 && pos7) && !(pos1 || pos2 || pos3 || pos4 || pos6 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos1 && pos3) && !(pos2 || pos4 || pos5 || pos6 || pos7 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos1 && pos7) && !(pos2 || pos3 || pos4 || pos5 || pos6 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    //---------//
                    if ((pos1 && pos2) && !(pos3 || pos4 || pos5 || pos6 || pos7 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos2 && pos3) && !(pos1 || pos4 || pos5 || pos6 || pos7 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos3 && pos4) && !(pos1 || pos2 || pos5 || pos6 || pos7 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos4 && pos5) && !(pos1 || pos2 || pos3 || pos6 || pos7 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos5 && pos6) && !(pos1 || pos2 || pos3 || pos4 || pos7 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos6 && pos7) && !(pos1 || pos2 || pos3 || pos4 || pos5 || pos8)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos7 && pos8) && !(pos1 || pos2 || pos3 || pos4 || pos5 || pos6)) {
                        contourImage[row][col] = 0;
                    }
                    if ((pos8 && pos1) && !(pos2 || pos3 || pos4 || pos5 || pos6 || pos7)) {
                        contourImage[row][col] = 0;
                    }
                }
            }
        }
    }
    
}
