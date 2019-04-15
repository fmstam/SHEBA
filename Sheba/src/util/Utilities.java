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

import java.awt.image.DataBuffer;
import javax.media.jai.*;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.operator.*;
import java.awt.image.BufferedImage;
import java.awt.color.ColorSpace;
import java.awt.image.ComponentColorModel;
import java.awt.image.SampleModel;
import java.awt.image.DataBufferUShort;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.Vector;

import java.awt.Transparency;
import java.util.ArrayList;
import java.util.Collections;
import util.st.MatrixAsImage;
import util.st.Pixel;

/**
 *
 * @author aanjos
 */
public class Utilities {

    public static final int GAUSSIAN = 0;
    public static final int MEAN = 1;
    public static final int MEDIAN = 2;
    public static final int FOURCONN = 4;
    public static final int EIGHTCONN = 8;

    public static void doSavePixelsAsImage(Pixel[][] pixels, String filename, int dataType) {
        MatrixAsImage mai = new MatrixAsImage(pixels, 1, dataType);
        JAI.create("filestore", mai.getPlanarImage(), filename, "TIFF");
    }

    public static void doSaveIntsAsImage(int[][] matrix, String filename, int dataType) {
        MatrixAsImage mai = new MatrixAsImage(matrix, 1, dataType);
        JAI.create("filestore", mai.getPlanarImage(), filename, "TIFF");
    }

    public static void doSaveIntsAsImagePNG(int[][] matrix, String filename, int dataType) {
        MatrixAsImage mai = new MatrixAsImage(matrix, 1, dataType);
        JAI.create("filestore", mai.getPlanarImage(), filename, "PNG");
    }

    public static void doSaveDoublesAsImage(double[][] matrix, String filename, int dataType) {
        int[][] matrixInt = new int[matrix.length][matrix[0].length];
        int value = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                value = (int) Math.round(matrix[i][j]);
                if (dataType == DataBuffer.TYPE_BYTE) {
                    if (matrix[i][j] > 255) {
                        value = 255;
                    }
                }
                if (dataType == DataBuffer.TYPE_DOUBLE) {
                    if (matrix[i][j] > 65535) {
                        value = 65535;
                    }
                }
                if (matrix[i][j] < 0) {
                    value = 0;
                }
                matrixInt[i][j] = value;
            }
        }
        MatrixAsImage mai = new MatrixAsImage(matrixInt, 1, dataType);
        JAI.create("filestore", mai.getPlanarImage(), filename, "PNG");
    }

    public static void doSaveImage(PlanarImage image, String filename) {
        JAI.create("filestore", image, filename, "TIFF");
    }

    public static void doSaveImage2(BufferedImage image, String filename) {
        JAI.create("filestore", image, filename, "TIFF");
    }

    public static void doSaveImage_____(short imageArray[], int width, int height, String filename) {

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        int[] nBits = {16};

        ComponentColorModel cm = new ComponentColorModel(cs, nBits, false, true, Transparency.OPAQUE, DataBuffer.TYPE_USHORT);

        SampleModel sm = cm.createCompatibleSampleModel(width, height);
        DataBufferUShort db = new DataBufferUShort(imageArray, width * height);
        WritableRaster raster = Raster.createWritableRaster(sm, db, null);


        BufferedImage bm = new BufferedImage(cm, raster, false, null);

        //bm.createGraphics();

        try {
            File output = new File(filename);
            ImageIO.write(bm, "jpeg2000", output);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static PlanarImage getGradientMagnitude(PlanarImage imageName) {
        int border = 10;
        //       PlanarImage imageWB = getImageWithBorder(imageName, border);

        ParameterBlockJAI pb1 = new ParameterBlockJAI("GradientMagnitude");
        //pb1.addSource(imageWB);
        pb1.addSource(imageName);
        pb1.setParameter("mask1", KernelJAI.GRADIENT_MASK_SOBEL_HORIZONTAL);
        pb1.setParameter("mask2", KernelJAI.GRADIENT_MASK_SOBEL_VERTICAL);

        PlanarImage gm = JAI.create("GradientMagnitude", pb1, null);

        //return getImageWithoutBorder(gm, border);
        return gm;
    }

    public static PlanarImage getGaussianSmoothedImage(PlanarImage image, float sigma) {
        //int size = (int) sigma;
        //KernelJAI kernelGauss = makeGaussianKernel(size);

        float[] k = getGaussianKernel(sigma);
        int diameter = k.length;
        int radius = (int) (diameter / 2);
        KernelJAI kernelGauss = new KernelJAI(diameter, diameter, radius, radius, k, k);
        //System.out.println(image.getHeight() + "x" + image.getWidth());

        PlanarImage imageWB = getImageWithBorder(image, radius);

        return getImageWithoutBorder(JAI.create("convolve", imageWB, kernelGauss), radius);
    }

    public static PlanarImage getFirstDerivativeH(PlanarImage img) {
        return JAI.create("Convolve", img, KernelJAI.GRADIENT_MASK_SOBEL_HORIZONTAL);
    }

    public static PlanarImage getFirstDerivativeV(PlanarImage img) {
        return JAI.create("Convolve", img, KernelJAI.GRADIENT_MASK_SOBEL_VERTICAL);
    }

    public static PlanarImage getImageWithBorder(PlanarImage img, int size) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(img);
        pb.add(size);
        pb.add(size);
        pb.add(size);
        pb.add(size);
        pb.add(BorderExtender.createInstance(BorderExtender.BORDER_COPY));
        pb.add(0);
        return JAI.create("Border", pb);
    }

    public static PlanarImage getImageWithoutBorder(PlanarImage pi, int border) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(pi);
        pb.add((float) 0);
        pb.add((float) 0);
        pb.add((float) pi.getWidth() - 2 * border);
        pb.add((float) pi.getHeight() - 2 * border);
        PlanarImage woBorder = JAI.create("crop", pb);
        //System.out.println(woBorder.getHeight() + "x" + woBorder.getWidth());
        //Utilities.doSaveImage(woBorder, "/home/aanjos/Desktop/woBorder.tiff");
        return woBorder;
    }

    public static KernelJAI makeGaussianKernel(int radius) {
        int diameter = 2 * radius + 1;
        double invR2 = 1.0F / (radius * radius);

        float[] gaussianData = new float[diameter];

        float sum = 0.0F;
        for (int i = 0; i < diameter; i++) {
            float d = i - radius;
            float val = (float) Math.exp(-d * d * invR2);
            gaussianData[i] = val;
            sum += val;
        }

        // Normalize
        float invsum = 1.0F / sum;
        for (int i = 0; i < diameter; i++) {
            gaussianData[i] *= invsum;
        }

        return new KernelJAI(diameter, diameter, radius, radius, gaussianData, gaussianData);
    }

    public static PlanarImage getMedianSmoothedImage(PlanarImage image, int size, int connection) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(image);
        switch (connection) {
            case FOURCONN:
                pb.add(MedianFilterDescriptor.MEDIAN_MASK_PLUS);
                break;
            default:
                pb.add(MedianFilterDescriptor.MEDIAN_MASK_SQUARE);
        }

        pb.add(size);
        return (JAI.create("medianfilter", pb));
    }

    public static PlanarImage getMeanSmoothedImage(PlanarImage image, int size) {
        float[] kernelMatrix = new float[size * size];

        for (int k = 0; k < kernelMatrix.length; k++) {
            kernelMatrix[k] = 1.0f / (size * size);
        }

        KernelJAI kernel = new KernelJAI(size, size, kernelMatrix);

        return (JAI.create("convolve", image, kernel));
    }

    public static int[] getHistogram(int[][] image, int maxIntensity) {
        int[] histogram = new int[maxIntensity]; // ao ser declarado é inicializado a zero automáticamente

        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[0].length; col++) {
                histogram[image[row][col]]++;
            }
        }
        return histogram;
    }

    public static double[][] generateGradientMagnitude(int[][] matrix, int notPart) { //returns the gradient magnitude of matrix, excluding values == notPart
        int pos_1, pos_2, pos_3, pos_4, pos_5, pos_6, pos_7, pos_8;
        int x = 0;
        int d_x = matrix[0].length;
        int y = 0;
        int d_y = matrix.length;
        double[][] gradientMagMx = new double[d_y][d_x];
        //contourImage = new int[d_y][d_x];

        for (int row = 0; row < d_y; row++) {
            for (int col = 0; col < d_x; col++) {
                gradientMagMx[row][col] = 0;
            }
        }

        for (int row = (y + 1); row < (d_y - 1); row++) {
            for (int col = (x + 1); col < (d_x - 1); col++) {
                if (matrix[row][col] != notPart) {
                    pos_1 = matrix[row - 1][col];
                    pos_2 = matrix[row - 1][col + 1];
                    pos_3 = matrix[row][col + 1];
                    pos_4 = matrix[row + 1][col + 1];
                    pos_5 = matrix[row + 1][col];
                    pos_6 = matrix[row + 1][col - 1];
                    pos_7 = matrix[row][col - 1];
                    pos_8 = matrix[row - 1][col - 1];

                    //if not at the undefined area of the image
                    if ((pos_1 != notPart) && (pos_2 != notPart) && (pos_3 != notPart) && (pos_4 != notPart) && (pos_5 != notPart) && (pos_6 != notPart) && (pos_7 != notPart) && (pos_8 != notPart)) {
                        double Gy = pos_8 + 2 * pos_1 + pos_2 - (pos_6 + 2 * pos_5 + pos_4); //Sobel y
                        double Gx = pos_8 + 2 * pos_7 + pos_6 - (pos_2 + 2 * pos_3 + pos_4); //Sobel x
                        gradientMagMx[row][col] = Math.sqrt(Gx * Gx + Gy * Gy); // magnitude of the gradient image
                        if (gradientMagMx[row][col] < 0) { //debugging
                            System.out.println("Gy: " + Gy + " Gx: " + Gx);
                            System.out.println("M: " + Math.sqrt(Gx * Gx + Gy * Gy) + "  minVal: " + Double.MIN_VALUE);
                            System.out.println("y: " + row + " x: " + col + "val: " + gradientMagMx[row][col]);
                            System.out.println("AQUI: " + pos_1 + "; " + pos_2 + "; " + pos_3 + "; " + pos_4 + "; " + pos_5 + "; " + pos_6 + "; " + pos_7 + "; " + pos_8);
                            System.exit(0);
                        }
                    } else {
                        gradientMagMx[row][col] = 0;
                    }
                }
            }
        }
        return gradientMagMx;
    }

    public static void smoothMedianDoubleMatrix(double[][] matrix) { // 8-conn median smooth of double matrix
        //int pos_1=0, pos_2=0, pos_3=0, pos_4=0, pos_5=0, pos_6=0, pos_7=0, pos_8=0;

        for (int row = 1; row < matrix.length - 1; row++) {
            for (int col = 1; col < matrix[0].length - 1; col++) {
                List<Double> neighs = new Vector<Double>();
                neighs.add(new Double(matrix[row - 1][col]));
                neighs.add(new Double(matrix[row - 1][col + 1]));
                neighs.add(new Double(matrix[row][col + 1]));
                neighs.add(new Double(matrix[row + 1][col + 1]));
                neighs.add(new Double(matrix[row][col]));
                neighs.add(new Double(matrix[row + 1][col]));
                neighs.add(new Double(matrix[row + 1][col - 1]));
                neighs.add(new Double(matrix[row][col - 1]));
                neighs.add(new Double(matrix[row - 1][col - 1]));
                Collections.sort(neighs);
                matrix[row][col] = neighs.get(4);
            }
        }

    }

    public static double[][] doubleMatrixMaxFilter(double[][] matrix) { // 8-conn median smooth of double matrix
        //int pos_1=0, pos_2=0, pos_3=0, pos_4=0, pos_5=0, pos_6=0, pos_7=0, pos_8=0;

        double[][] newMatrix = new double[matrix.length][matrix[0].length];

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                List<Double> neighs = new Vector<Double>(); // vector só com multiplos threads!
                neighs.add(new Double(getRC(row - 1, col, matrix)));
                neighs.add(new Double(getRC(row - 1, col + 1, matrix)));
                neighs.add(new Double(getRC(row, col + 1, matrix)));
                neighs.add(new Double(getRC(row + 1, col + 1, matrix)));
                neighs.add(new Double(getRC(row, col, matrix)));
                neighs.add(new Double(getRC(row + 1, col, matrix)));
                neighs.add(new Double(getRC(row + 1, col - 1, matrix)));
                neighs.add(new Double(getRC(row, col - 1, matrix)));
                neighs.add(new Double(getRC(row - 1, col - 1, matrix)));
                Collections.sort(neighs);
                newMatrix[row][col] = neighs.get(neighs.size() - 1); //System.out.println(newMatrix[row][col]);
            }
        }
        System.out.println("Modificar para meter size do kernel");
        return newMatrix;
    }

    //not working
    static float[] makeGaussianKernelSigma(float sigma) {

        int center = (int) (6.0 * sigma);
        float[] kernel = new float[2 * center + 1];

        float sigma2 = sigma * sigma;

        for (int i = 0; i < kernel.length; i++) {
            float r = center - i;
            kernel[i] = (float) Math.exp(-0.5f * (r * r) / sigma2);
        }

        return kernel;
    }

    // returns normalized gaussian kernel
    public static float[] getGaussianKernel(float sigma) {
        int halfDim = (int) (sigma * 3);
        float[] result = new float[halfDim * 2 + 1];

        int j = 0;
        for (int i = -halfDim; i <= halfDim; i++) {
            result[j++] = (float) oneValueGauss(i, sigma);
        }

        // normalize kernel to prevent image darkening
        float sum = 0;
        for (int i = 0; i < result.length; i++) {
            sum += result[i];
        }

        float normFactor = 1f / sum;
        for (int i = 0; i < result.length; i++) {
            result[i] *= normFactor;
        }

        return result;
    }

    private static double oneValueGauss(float x, float sigma) {
        return 1 / (Math.sqrt(2 * Math.PI) * sigma) * Math.pow(Math.E, (-(x * x) / (2 * sigma * sigma)));
    }

// funções para trabalhar dados em matrizes de doubles em vez de em imagens
    // will return the value as if matrix was added a replicated border the size of half of the kernel
    private static double getRC(int r, int c, double[][] matrix) {
        if (r < 0) {
            r = 0;
        }
        if (r >= matrix.length) {
            r = matrix.length - 1;
        }
        if (c < 0) {
            c = 0;
        }
        if (c >= matrix[0].length) {
            c = matrix[0].length - 1;
        }

        return (matrix[r][c]);
    }

    //kernel should be square and must have odd sizes
    public static double[][] convolve2d(double[][] kernel, double[][] matrix) {
        int uc = kernel.length / 2;
        int vc = kernel[0].length / 2;
        int height = matrix.length;
        int width = matrix[0].length;

        double[][] newMx = new double[height][width];
        double sum = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                sum = 0.0;
                for (int v = -vc; v <= vc; v++) {
                    for (int u = -uc; u <= uc; u++) {
                        sum += Utilities.getRC((row - v), (col - u), matrix) * kernel[v + vc][u + uc];
                    }
                }
                //take care of this on the conversion to image
                //if (sum < 0) sum = 0;
                //if (sum > 255) sum = 255;
                newMx[row][col] = sum;
            }
        }

        return newMx;
    }

    public static double[][] convolve2d_v2(double[][] matrix, float[][] kernel) {
        // Check if we have a good kernel
        assert (kernel[0].length % 2 != 1 && kernel.length % 2 != 1) : "Kernel size not odd!";

        double[][] result = new double[matrix.length][matrix[0].length];

        int xoff = (kernel.length - 1) / 2;
        int yoff = (kernel[0].length - 1) / 2;

        for (int x = xoff; x < matrix.length - xoff; x++) {
            for (int y = yoff; y < matrix[0].length - yoff; y++) {
                double res = 0.0;

                for (int xk = 0; xk < kernel.length; xk++) {
                    for (int yk = 0; yk < kernel[0].length; yk++) {
                        res += matrix[x - xoff + xk][y - yoff + yk] * kernel[xk][yk];
                    }
                }

                result[x][y] = res < 255.0 ? res : 255.0;
            }
        }

        return result;
    }

    public static double[][] convolve1DH(float[] kernel, double[][] matrix) {
        int uc = kernel.length / 2;
        int height = matrix.length;
        int width = matrix[0].length;

        double[][] newMx = new double[height][width];
        double sum = 0;

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                sum = 0.0;

                for (int u = -uc; u <= uc; u++) {
                    sum += Utilities.getRC(r, c - u, matrix) * kernel[u + uc];
                }

                //take care of this on the conversion to image
                //if (sum < 0) sum = 0;
                //if (sum > 255) sum = 255;
                newMx[r][c] = sum;
            }
        }

        return newMx;
    }

    public static double[][] convolve1DV(float[] kernel, double[][] matrix) {
        int uc = kernel.length / 2;
        int height = matrix.length;
        int width = matrix[0].length;

        double[][] newMx = new double[height][width];
        double sum = 0;

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                sum = 0.0;

                for (int u = -uc; u <= uc; u++) {
                    sum += Utilities.getRC(r - u, c, matrix) * kernel[u + uc];
                }

                //take care of this on the conversion to image
                //if (sum < 0) sum = 0;
                //if (sum > 255) sum = 255;
                newMx[r][c] = sum;
            }
        }

        return newMx;
    }

    public static double[][] convolve2DSeparable(double[][] matrix, float[] kernel) {
        return convolve1DV(kernel, convolve1DH(kernel, matrix));
    }

    public static void multiplyFactorByMatrix(float factor, double[][] matrix) {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                matrix[r][c] = factor * matrix[r][c];
            }
        }
    }

    public static void squareMatrix(double[][] matrix) {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                matrix[r][c] = matrix[r][c] * matrix[r][c];
            }
        }
    }

    protected static double gaussianFromPosition(double x, double y, double sigma) {
        double x2 = x * x;
        double y2 = y * y;
        double sigma2 = sigma * sigma;
        return (1 / (2 * Math.PI * sigma2)) * Math.exp(-((x2 + y2) / (2 * sigma2)));
    }

    public static double[][] getFullGaussianKernel(float sigma) {
        int width = (int) (6 * sigma + 1);

        int halfSize = width / 2;
        double[][] k = new double[halfSize * 2 + 1][halfSize * 2 + 1];
        for (int r = -halfSize; r <= halfSize; r++) {
            for (int c = -halfSize; c <= halfSize; c++) {
                k[r + halfSize][c + halfSize] = gaussianFromPosition(r, c, sigma);
            }
        }
        return k;
    }

    //LoG parece estar a funcar bem.
    protected static double LoGFromPosition(double x, double y, double sigma) {
        double x2 = x * x;
        double y2 = y * y;
        double sigma2 = sigma * sigma;
        return (- 1 / (Math.PI * sigma2*sigma2)) * (1.0 - ((x2 + y2) / (2 * sigma2))) * Math.exp(-((x2 + y2) / (2 * sigma2)));
    }

    public static double[][] getFullLoGKernel(float sigma) {
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
}
