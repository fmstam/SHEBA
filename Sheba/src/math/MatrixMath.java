/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;


import java.util.Arrays;

/**
 * A matrix based basic math class.
 *
 * @author faroqal-tam
 */
public abstract class MatrixMath {

    
    /** 
     * public math plugins
     * 
     * */
    
    
    
    /**
     * ************************ min and max
     * *********************************************
     */
    /**
     * Max of a vector
     *
     * @param vector
     * @return the maximum in the vector
     */
    public static int max(int[] vector) {
        int max = vector[0];
        for (int o : vector) {
            if (o > max) {
                max = o;
            }
        }
        return max;
    }

    public static short max(short[] vector) {
        short max = vector[0];
        for (short o : vector) {
            if (o > max) {
                max = o;
            }
        }
        return max;
    }

    public static float max(float[] vector) {
        float max = vector[0];
        for (float o : vector) {
            if (o > max) {
                max = o;
            }
        }
        return max;
    }

    public static double max(double[] vector) {
        double max = vector[0];
        for (double o : vector) {
            if (o > max) {
                max = o;
            }
        }
        return max;
    }

    /**
     * Get max of a matrix
     *
     * @param matrix
     * @return the maximum of the matrix
     */
    public static int max(int[][] matrix) {
        int max = matrix[0][0];
        for (int[] vector : matrix) {
            int vmax = max(vector);
            if (vmax > max) {
                max = vmax;
            }
        }
        return max;
    }

    public static short max(short[][] matrix) {
        short max = matrix[0][0];
        for (short[] vector : matrix) {
            short vmax = max(vector);
            if (vmax > max) {
                max = vmax;
            }
        }
        return max;
    }

    public static float max(float[][] matrix) {
        float max = matrix[0][0];
        for (float[] vector : matrix) {
            float vmax = max(vector);
            if (vmax > max) {
                max = vmax;
            }
        }
        return max;
    }

    public static double max(double[][] matrix) {
        double max = matrix[0][0];
        for (double[] vector : matrix) {
            double vmax = max(vector);
            if (vmax > max) {
                max = vmax;
            }
        }
        return max;
    }

    /**
     * Min of a vector
     *
     * @param vector
     * @return the minimum in the vector
     */
    public static int min(int[] vector) {
        int min = vector[0];
        for (int o : vector) {
            if (o < min) {
                min = o;
            }
        }
        return min;
    }

    public static short min(short[] vector) {
        short min = vector[0];
        for (short o : vector) {
            if (o < min) {
                min = o;
            }
        }
        return min;
    }

    public static float min(float[] vector) {
        float min = vector[0];
        for (float o : vector) {
            if (o < min) {
                min = o;
            }
        }
        return min;
    }

    public static double min(double[] vector) {
        double min = vector[0];
        for (double o : vector) {
            if (o < min) {
                min = o;
            }
        }
        return min;
    }

    /**
     * Get min of a matrix
     *
     * @param matrix
     * @return the minimum of the matrix
     */
    public static int min(int[][] matrix) {
        int min = matrix[0][0];
        for (int[] vector : matrix) {
            int vmin = min(vector);
            if (vmin < min) {
                min = vmin;
            }
        }
        return min;
    }

    public static short min(short[][] matrix) {
        short min = matrix[0][0];
        for (short[] vector : matrix) {
            short vmin = min(vector);
            if (vmin < min) {
                min = vmin;
            }
        }
        return min;
    }

    public static float min(float[][] matrix) {
        float min = matrix[0][0];
        for (float[] vector : matrix) {
            float vmin = min(vector);
            if (vmin < min) {
                min = vmin;
            }
        }
        return min;
    }

    public static double min(double[][] matrix) {
        double min = matrix[0][0];
        for (double[] vector : matrix) {
            double vmin = min(vector);
            if (vmin < min) {
                min = vmin;
            }
        }
        return min;
    }

    /**
     * ************************************ sum, sum of differences, mean,
     * median, and standard deviation *******************************
     */
    /**
     * Sum elements in a vector
     *
     * @param vector
     * @return a double value of the sum.
     */
    public static double sum(int[] vector) {
        double sum = 0.0;
        for (int v : vector) {
            sum += v;
        }
        return sum;
    }

    public static double sum(short[] vector) {
        double sum = 0.0;
        for (short v : vector) {
            sum += v;
        }
        return sum;
    }

    public static double sum(float[] vector) {
        double sum = 0.0;
        for (float v : vector) {
            sum += v;
        }
        return sum;
    }

    public static double sum(double[] vector) {
        double sum = 0.0;
        for (double v : vector) {
            sum += v;
        }
        return sum;
    }

    /**
     * Sum of a given matrix
     *
     * @param matrix
     * @return a double value representing the sum of all elements.
     */
    public static double sum(int[][] matrix) {
        double sum = 0;
        for (int[] vector : matrix) {
            sum += sum(vector);
        }
        return sum;
    }

    public static double sum(short[][] matrix) {
        double sum = 0;
        for (short[] vector : matrix) {
            sum += sum(vector);
        }
        return sum;
    }

    public static double sum(float[][] matrix) {
        double sum = 0;
        for (float[] vector : matrix) {
            sum += sum(vector);
        }
        return sum;
    }

    public static double sum(double[][] matrix) {
        double sum = 0;
        for (double[] vector : matrix) {
            sum += sum(vector);
        }
        return sum;
    }

    /**
     * The mean of a vector
     *
     * @param vector
     * @return
     */
    public static double mean(int[] vector) {
        double size = vector.length;
        return sum(vector) / size;
    }

    public static double mean(short[] vector) {
        double size = vector.length;
        return sum(vector) / size;
    }

    public static double mean(float[] vector) {
        double size = vector.length;
        return sum(vector) / size;
    }

    public static double mean(double[] vector) {
        double size = vector.length;
        return sum(vector) / size;
    }

    /**
     * The mean of a matrix
     *
     * @param mat
     * @return
     */
    public static double mean(int[][] mat) {
        double size = mat.length * mat[0].length;
        return sum(mat) / size;
    }

    public static double mean(short[][] mat) {
        double size = mat.length * mat[0].length;
        return sum(mat) / size;
    }

    public static double mean(float[][] mat) {
        double size = mat.length * mat[0].length;
        return sum(mat) / size;
    }

    public static double mean(double[][] mat) {
        double size = mat.length * mat[0].length;
        return sum(mat) / size;
    }

    public static int median(int[] vector) {
        Arrays.sort(vector);
        return vector[vector.length / 2];

    }

    public static short median(short[] vector) {
        Arrays.sort(vector);
        return vector[vector.length / 2];

    }

    public static float median(float[] vector) {
        Arrays.sort(vector);
        return vector[vector.length / 2];

    }

    public static double median(double[] vector) {
        Arrays.sort(vector);
        return vector[vector.length / 2];

    }
    
    
   

    /**
     * Sum of squared differences (SSD) of a given vector.
     *
     * @param vector the input vector
     * @return
     */
    public static double SSD(int[] vector1, int[] vector2) {
        double ssd = 0.0;
        for (int v : vector1) {
            for (int u : vector2) {
                ssd += Math.pow(v - u, 2);
            }
        }
        return ssd;
    }

    public static double SSD(int[] vector, int v) {
        double ssd = 0.0;
        for (int u : vector) {
            ssd += Math.pow(v - u, 2);
        }
        return ssd;
    }

    public static double SSD(int[] vector, double v) {
        double ssd = 0.0;
        for (int u : vector) {
            ssd += Math.pow(v - u, 2);
        }
        return ssd;
    }

    public static double SSD(short[] vector1, short[] vector2) {
        double ssd = 0.0;
        for (short v : vector1) {
            for (short u : vector2) {
                ssd += Math.pow(v - u, 2);
            }
        }
        return ssd;
    }

    public static double SSD(short[] vector, short v) {
        double ssd = 0.0;
        for (short u : vector) {
            ssd += Math.pow(v - u, 2);
        }
        return ssd;
    }

    public static double SSD(short[] vector, double v) {
        double ssd = 0.0;
        for (short u : vector) {
            ssd += Math.pow(v - u, 2);
        }
        return ssd;
    }

    public static double SSD(float[] vector1, float[] vector2) {
        double ssd = 0.0;
        for (float v : vector1) {
            for (float u : vector2) {
                ssd += Math.pow(v - u, 2);
            }
        }
        return ssd;
    }

    public static double SSD(float[] vector, float v) {
        double ssd = 0.0;
        for (float u : vector) {
            ssd += Math.pow(v - u, 2);
        }
        return ssd;
    }

    public static double SSD(float[] vector, double v) {
        double ssd = 0.0;
        for (float u : vector) {
            ssd += Math.pow(v - u, 2);
        }
        return ssd;
    }

    public static double SSD(double[] vector1, double[] vector2) {
        double ssd = 0.0;
        for (double v : vector1) {
            for (double u : vector2) {
                ssd += Math.pow(v - u, 2);
            }
        }
        return ssd;
    }

    public static double SSD(double[] vector, double v) {
        double ssd = 0.0;
        for (double u : vector) {
            ssd += Math.pow(v - u, 2);
        }
        return ssd;
    }

    public static double SSD(int[][] mat1, int[][] mat2) {
        double ssd = 0.0;
        for (int[] vector1 : mat1) {
            for (int[] vector2 : mat2) {
                ssd += SSD(vector1, vector2);
            }
        }
        return ssd;
    }

    public static double SSD(int[][] mat1, int v) {
        double ssd = 0.0;
        for (int[] vector : mat1) {
            ssd += SSD(vector, v);
        }
        return ssd;
    }

    public static double SSD(int[][] mat1, double v) {
        double ssd = 0.0;
        for (int[] vector : mat1) {
            ssd += SSD(vector, v);
        }
        return ssd;
    }

    public static double SSD(short[][] mat1, short[][] mat2) {
        double ssd = 0.0;
        for (short[] vector1 : mat1) {
            for (short[] vector2 : mat2) {
                ssd += SSD(vector1, vector2);
            }
        }
        return ssd;
    }

    public static double SSD(short[][] mat1, short v) {
        double ssd = 0.0;
        for (short[] vector : mat1) {
            ssd += SSD(vector, v);
        }
        return ssd;
    }

    public static double SSD(short[][] mat1, double v) {
        double ssd = 0.0;
        for (short[] vector : mat1) {
            ssd += SSD(vector, v);
        }
        return ssd;
    }

    public static double SSD(float[][] mat1, float[][] mat2) {
        double ssd = 0.0;
        for (float[] vector1 : mat1) {
            for (float[] vector2 : mat2) {
                ssd += SSD(vector1, vector2);
            }
        }
        return ssd;
    }

    public static double SSD(float[][] mat1, float v) {
        double ssd = 0.0;
        for (float[] vector : mat1) {
            ssd += SSD(vector, v);
        }
        return ssd;
    }

    public static double SSD(float[][] mat1, double v) {
        double ssd = 0.0;
        for (float[] vector : mat1) {
            ssd += SSD(vector, v);
        }
        return ssd;
    }

    public static double SSD(double[][] mat1, double[][] mat2) {
        double ssd = 0.0;
        for (double[] vector1 : mat1) {
            for (double[] vector2 : mat2) {
                ssd += SSD(vector1, vector2);
            }
        }
        return ssd;
    }

    public static double SSD(double[][] mat1, double v) {
        double ssd = 0.0;
        for (double[] vector : mat1) {
            ssd += SSD(vector, v);
        }
        return ssd;
    }

    /**
     * Get the standard deviation
     *
     * @param vector the input vector
     * @param mean the mean of the vector
     * @return
     */
    public static double STDev(int[] vector, double mean) {
        double size = vector.length;
        double ssd = SSD(vector, mean);
        return Math.sqrt(ssd / (size - 1)); // sample standard deviation (n-1)
    }

    public static double STDev(short[] vector, double mean) {
        double size = vector.length;
        double ssd = SSD(vector, mean);
        return Math.sqrt(ssd / (size - 1)); // sample standard deviation (n-1)
    }

    public static double STDev(float[] vector, double mean) {
        double size = vector.length;
        double ssd = SSD(vector, mean);
        return Math.sqrt(ssd / (size - 1)); // sample standard deviation (n-1)
    }

    public static double STDev(double[] vector, double mean) {
        double size = vector.length;
        double ssd = SSD(vector, mean);
        return Math.sqrt(ssd / (size - 1)); // sample standard deviation (n-1)
    }

    /**
     * The standard deviation of a matrix
     *
     * @param mat
     * @param mean
     * @return
     */
    public static double STDev(int[][] mat, double mean) {
        double ssd = 0.0;
        double size = mat.length * mat[0].length;
        for (int[] vector : mat) {
            ssd += SSD(vector, mean);
        }
        return Math.sqrt(ssd / (size - 1)); // sample standard deviation (n-1) 
    }

    public static double STDev(short[][] mat, double mean) {
        double ssd = 0.0;
        double size = mat.length * mat[0].length;
        for (short[] vector : mat) {
            ssd += SSD(vector, mean);
        }
        return Math.sqrt(ssd / (size - 1)); // sample standard deviation (n-1) 
    }

    public static double STDev(float[][] mat, double mean) {
        double ssd = 0.0;
        double size = mat.length * mat[0].length;
        for (float[] vector : mat) {
            ssd += SSD(vector, mean);
        }
        return Math.sqrt(ssd / (size - 1)); // sample standard deviation (n-1) 
    }

    public static double STDev(double[][] mat, double mean) {
        double ssd = 0.0;
        double size = mat.length * mat[0].length;
        for (double[] vector : mat) {
            ssd += SSD(vector, mean);
        }
        return Math.sqrt(ssd / (size - 1)); // sample standard deviation (n-1) 
    }

    /**
     * ************************************************** probability
     * ***************************************
     */
    public static double[] normalize(int[] vector) {
        double[] normalizedVector = new double[vector.length];
        int min = min(vector);
        int max = max(vector);
        double range = max - min;
        for (int i = 0; i < vector.length; i++) {
            normalizedVector[i] = (double) (vector[i] - min) / range;
        }
        return normalizedVector;
    }
    
  

    public static double[] normalize(short[] vector) {
        double[] normalizedVector = new double[vector.length];
        short min = min(vector);
        short max = max(vector);
        double range = max - min;
        for (int i = 0; i < vector.length; i++) {
            normalizedVector[i] = (double) (vector[i] - min) / range;
        }
        return normalizedVector;
    }

    public static double[] normalize(float[] vector) {
        double[] normalizedVector = new double[vector.length];
        float min = min(vector);
        float max = max(vector);
        double range = max - min;
        for (int i = 0; i < vector.length; i++) {
            normalizedVector[i] = (double) (vector[i] - min) / range;
        }
        return normalizedVector;
    }

    public static double[] normalize(double[] vector) {
        double[] normalizedVector = new double[vector.length];
        double min = min(vector);
        double max = max(vector);
        double range = max - min;
        for (int i = 0; i < vector.length; i++) {
            normalizedVector[i] = (vector[i] - min) / range;
        }
        return normalizedVector;
    }
    
    /**
     * put the input vector in the range 0... maxbound
     * @param vector
     * @param maxbound
     * @return 
     */
        public static double[] normalize(double[] vector, double maxbound) {
        double[] normalizedVector = new double[vector.length];
        double min = min(vector);
        double max = max(vector);
        double range = max - min;
        for (int i = 0; i < vector.length; i++) {
            normalizedVector[i] = ((vector[i] - min) / range)* maxbound;
        }
        return normalizedVector;
    }
        
        
    public static double[] bound(double[] vector, double maxbound, double minbound) {
        double[] normalizedVector = new double[vector.length];
        
        for (int i = 0; i < vector.length; i++) {
            if(vector[i]>maxbound)
                normalizedVector[i] = maxbound;
            else
                if(vector[i]<minbound)
                    normalizedVector[i]=minbound;
            else
                    normalizedVector[i]=vector[i];
        }
        return normalizedVector;
    }

    public static double[][] normalize(int[][] mat) {
        int min = min(mat);
        int max = max(mat);
        double range = max - min;
        double[][] normalizedMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            normalizedMat[i] = normalize(mat[i]);
        }
        return normalizedMat;
    }

    public static double[][] normalize(short[][] mat) {
        short min = min(mat);
        short max = max(mat);
        double range = max - min;
        double[][] normalizedMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            normalizedMat[i] = normalize(mat[i]);
        }
        return normalizedMat;
    }

    public static double[][] normalize(float[][] mat) {
        float min = min(mat);
        float max = max(mat);
        double range = max - min;
        double[][] normalizedMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            normalizedMat[i] = normalize(mat[i]);
        }
        return normalizedMat;
    }

    public static double[][] normalize(double[][] mat) {
        double min = min(mat);
        double max = max(mat);
        double range = max - min;
        double[][] normalizedMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            normalizedMat[i] = normalize(mat[i]);
        }
        return normalizedMat;
    }

    
    /**
     * Change the range of the input vector from 0...1 to another range defined by the max and min
     * @param vector
     * @param max
     * @param min
     * @return 
     */
     public static double[] range(double[] vector, double min, double max) {
        double[] normalizedVector = new double[vector.length];
        double range = max - min;
        for (int i = 0; i < vector.length; i++) {
            normalizedVector[i] = vector[i]* range+min;
        }
        return normalizedVector;
    }
    
    /**
     * The histogram of an input vector
     *
     * @param vector
     * @param maxBound
     * @return
     */
    public static int[] histogram(int[] vector, int maxBound) {
        int[] histogram = new int[maxBound+1];

        for (int v : vector) {
            histogram[v]++;

        }
        return histogram;
    }

    /**
     * The probability density function PDF of a vector.
     *
     * @param vector
     * @return
     */
    public static double[] pdf(int[] vector) {
        double[] pdf = new double[vector.length];
        double sum = sum(vector);
        for (double d : pdf) {
            d = d / sum;
        }
        return pdf;
    }

    public static int[] histogram(short[] vector, int maxBound) {
        int[] histogram = new int[maxBound];

        for (short v : vector) {
            histogram[v]++;

        }
        return histogram;
    }

    public static double[] pdf(short[] vector) {
        double[] pdf = new double[vector.length];
        double sum = sum(vector);
        for (double d : pdf) {
            d = d / sum;
        }
        return pdf;
    }

    public static int[] histogram(float[] vector, int maxBound) {
        int[] histogram = new int[maxBound];

        for (float v : vector) {
            histogram[(int) v]++;

        }
        return histogram;
    }

    public static double[] pdf(float[] vector) {
        double[] pdf = new double[vector.length];
        double sum = sum(vector);
        for (double d : pdf) {
            d = d / sum;
        }
        return pdf;
    }

    public static int[] histogram(double[] vector, int maxBound) {
        int[] histogram = new int[maxBound];

        for (double v : vector) {
            int intv = (int) v;
            if (intv >= maxBound) {
                intv = maxBound - 1;
            }
            histogram[intv]++;

        }
        return histogram;
    }

    public static double[] pdf(double[] vector) {
        double[] pdf = new double[vector.length];
        double sum = sum(vector);
        for (double d : pdf) {
            d = d / sum;
        }
        return pdf;
    }

    public static double[][] pdf(int[][] mat) {
        double[][] pdf = new double[mat.length][mat[0].length];
        double sum = sum(mat);
        for (double[] d : pdf) {
            d = pdf(d);
        }
        return pdf;
    }

    public static int[] histogram(int[][] mat, int maxBound) {
        int[] histogram = new int[maxBound];

        for (int[] vector : mat) {
            for (int v : vector) {
                histogram[v]++;
            }
        }
        return histogram;
    }

    public static double[][] pdf(short[][] mat) {
        double[][] pdf = new double[mat.length][mat[0].length];
        double sum = sum(mat);
        for (double[] d : pdf) {
            d = pdf(d);
        }
        return pdf;
    }

    public static int[] histogram(short[][] mat, int maxBound) {
        int[] histogram = new int[maxBound];

        for (short[] vector : mat) {
            for (short v : vector) {
                histogram[v]++;
            }
        }
        return histogram;
    }

    public static double[][] pdf(float[][] mat) {
        double[][] pdf = new double[mat.length][mat[0].length];
        double sum = sum(mat);
        for (double[] d : pdf) {
            d = pdf(d);
        }
        return pdf;
    }

    public static int[] histogram(float[][] mat, int maxBound) {
        int[] histogram = new int[maxBound];

        for (float[] vector : mat) {
            for (float v : vector) {
                histogram[(int) v]++;
            }
        }
        return histogram;
    }

    public static double[][] pdf(double[][] mat) {
        double[][] pdf = new double[mat.length][mat[0].length];
        double sum = sum(mat);
        for (double[] d : pdf) {
            d = pdf(d);
        }
        return pdf;
    }

    public static int[] histogram(double[][] mat, int maxBound) {
        int[] histogram = new int[maxBound];

        for (double[] vector : mat) {
            for (double v : vector) {
                int intv = (int) v;
                if (intv >= maxBound) {
                    intv = maxBound - 1;
                }
                histogram[intv]++;
            }
        }
        return histogram;
    }

    /**
     * The CDF function
     *
     * @param mat a 2*n matrix where the first column is the index and the
     * second is the probability.
     * @return
     */
    public static double[] CDF(double[][] mat) {
        double[] cdf = new double[mat.length];
        double last_prob_sum = 0;
        for (int i = 0; i < mat.length; i++) {
            last_prob_sum += mat[i][1];
            cdf[(int) mat[i][0]] = last_prob_sum;
        }
        return cdf;
    }

    /**
     * ************************** general math
     * **********************************
     */
    /**
     * Add a value to a vector.
     *
     * @param vector
     * @param value
     * @return
     */
    public static int[] add(int[] vector, int value) {
        int[] newVector = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] + value;
        }
        return newVector;
    }

    public static double[] add(int[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] + value;
        }
        return newVector;
    }

    public static int[] add(short[] vector, short value) {
        int[] newVector = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] + value;
        }
        return newVector;
    }

    public static double[] add(short[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] + value;
        }
        return newVector;
    }

    public static float[] add(float[] vector, float value) {
        float[] newVector = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] + value;
        }
        return newVector;
    }

    public static double[] add(float[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] + value;
        }
        return newVector;
    }

    public static double[] add(double[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] + value;
        }
        return newVector;
    }

    /**
     * Add two vectors.
     *
     * @param vector1
     * @param vector2
     * @return
     */
    public static int[] add(int[] vector1, int[] vector2) {
        int[] newVector = new int[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] + vector2[i];
        }
        return newVector;
    }

    public static int[] add(short[] vector1, short[] vector2) {
        int[] newVector = new int[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] + vector2[i];
        }
        return newVector;
    }

    public static float[] add(float[] vector1, float[] vector2) {
        float[] newVector = new float[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] + vector2[i];
        }
        return newVector;
    }

    public static double[] add(double[] vector1, double[] vector2) {
        double[] newVector = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] + vector2[i];
        }
        return newVector;
    }

    public static int[][] add(int[][] mat, int value) {
        int[][] newMat = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = add(mat[i], value);
        }
        return newMat;
    }

    public static double[][] add(int[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = add(mat[i], value);
        }
        return newMat;
    }

    public static int[][] add(short[][] mat, short value) {
        int[][] newMat = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = add(mat[i], value);
        }
        return newMat;
    }

    public static double[][] add(short[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = add(mat[i], value);
        }
        return newMat;
    }

    public static float[][] add(float[][] mat, float value) {
        float[][] newMat = new float[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = add(mat[i], value);
        }
        return newMat;
    }

    public static double[][] add(double[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = add(mat[i], value);
        }
        return newMat;
    }

    /**
     * Add two matrices.
     *
     * @param mat1
     * @param mat2
     * @return
     */
    public static int[][] add(int[][] mat1, int[][] mat2) {
        int[][] newMat = new int[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = add(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static int[][] add(short[][] mat1, short[][] mat2) {
        int[][] newMat = new int[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = add(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static float[][] add(float[][] mat1, float[][] mat2) {
        float[][] newMat = new float[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = add(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static double[][] add(double[][] mat1, double[][] mat2) {
        double[][] newMat = new double[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = add(mat1[i], mat2[i]);
        }
        return newMat;
    }

    /**
     * Subtract a value from a vector.
     *
     * @param vector
     * @param value
     * @return
     */
    public static int[] subtract(int[] vector, int value) {
        int[] newVector = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] - value;
        }
        return newVector;
    }

    public static double[] subtract(int[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] - value;
        }
        return newVector;
    }

    public static int[] subtract(short[] vector, short value) {
        int[] newVector = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] - value;
        }
        return newVector;
    }

    public static double[] subtract(short[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] - value;
        }
        return newVector;
    }

    public static float[] subtract(float[] vector, float value) {
        float[] newVector = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] - value;
        }
        return newVector;
    }

    public static double[] subtract(float[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] - value;
        }
        return newVector;
    }

    public static double[] subtract(double[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] - value;
        }
        return newVector;
    }

    /**
     * Subtract two vectors.
     *
     * @param vector1
     * @param vector2
     * @return
     */
    public static int[] subtract(int[] vector1, int[] vector2) {
        int[] newVector = new int[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] - vector2[i];
        }
        return newVector;
    }

    public static int[] subtract(short[] vector1, short[] vector2) {
        int[] newVector = new int[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] - vector2[i];
        }
        return newVector;
    }

    public static float[] subtract(float[] vector1, float[] vector2) {
        float[] newVector = new float[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] - vector2[i];
        }
        return newVector;
    }

    public static double[] subtract(double[] vector1, double[] vector2) {
        int minlength= Math.min(vector1.length, vector2.length);
        double[] newVector = new double[minlength];
        for (int i = 0; i < minlength; i++) {
            newVector[i] = vector1[i] - vector2[i];
        }
        return newVector;
    }

    public static int[][] subtract(int[][] mat, int value) {
        int[][] newMat = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = subtract(mat[i], value);
        }
        return newMat;
    }

    public static double[][] subtract(int[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = subtract(mat[i], value);
        }
        return newMat;
    }

    public static int[][] subtract(short[][] mat, short value) {
        int[][] newMat = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = subtract(mat[i], value);
        }
        return newMat;
    }

    public static double[][] subtract(short[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = subtract(mat[i], value);
        }
        return newMat;
    }

    public static float[][] subtract(float[][] mat, float value) {
        float[][] newMat = new float[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = subtract(mat[i], value);
        }
        return newMat;
    }

    public static double[][] subtract(double[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = subtract(mat[i], value);
        }
        return newMat;
    }

    /**
     * Subtract two matrices.
     *
     * @param mat1
     * @param mat2
     * @return
     */
    public static int[][] subtract(int[][] mat1, int[][] mat2) {
        int[][] newMat = new int[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = subtract(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static int[][] subtract(short[][] mat1, short[][] mat2) {
        int[][] newMat = new int[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = subtract(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static float[][] subtract(float[][] mat1, float[][] mat2) {
        float[][] newMat = new float[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = subtract(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static double[][] subtract(double[][] mat1, double[][] mat2) {
        double[][] newMat = new double[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = subtract(mat1[i], mat2[i]);
        }
        return newMat;
    }

    /**
     * Multiply a value with a vector.
     *
     * @param vector
     * @param value
     * @return
     */
    public static int[] times(int[] vector, int value) {
        int[] newVector = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] * value;
        }
        return newVector;
    }

    public static double[] times(int[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] * value;
        }
        return newVector;
    }

    public static int[] times(short[] vector, short value) {
        int[] newVector = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] * value;
        }
        return newVector;
    }

    public static double[] times(short[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] * value;
        }
        return newVector;
    }

    public static float[] times(float[] vector, float value) {
        float[] newVector = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] * value;
        }
        return newVector;
    }

    public static double[] times(float[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] * value;
        }
        return newVector;
    }

    public static double[] times(double[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] * value;
        }
        return newVector;
    }

    /**
     * Multiply two vectors.
     *
     * @param vector1
     * @param vector2
     * @return
     */
    public static int[] times(int[] vector1, int[] vector2) {
        int[] newVector = new int[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] * vector2[i];
        }
        return newVector;
    }

    public static int[] times(short[] vector1, short[] vector2) {
        int[] newVector = new int[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] * vector2[i];
        }
        return newVector;
    }

    public static float[] times(float[] vector1, float[] vector2) {
        float[] newVector = new float[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] * vector2[i];
        }
        return newVector;
    }

    public static double[] times(double[] vector1, double[] vector2) {
        double[] newVector = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] * vector2[i];
        }
        return newVector;
    }

    public static int[][] times(int[][] mat, int value) {
        int[][] newMat = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = times(mat[i], value);
        }
        return newMat;
    }

    public static double[][] times(int[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = times(mat[i], value);
        }
        return newMat;
    }

    public static int[][] times(short[][] mat, short value) {
        int[][] newMat = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = times(mat[i], value);
        }
        return newMat;
    }

    public static double[][] times(short[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = times(mat[i], value);
        }
        return newMat;
    }

    public static float[][] times(float[][] mat, float value) {
        float[][] newMat = new float[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = times(mat[i], value);
        }
        return newMat;
    }

    public static double[][] times(double[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = times(mat[i], value);
        }
        return newMat;
    }

    public static int dotProduct(int[] vector1, int[] vector2) {
        int result = 0;
        for (int i = 0; i < vector1.length; i++) {
            result += vector1[i] * vector2[i];
        }
        return result;
    }

    public static int dotProduct(short[] vector1, short[] vector2) {
        int result = 0;
        for (int i = 0; i < vector1.length; i++) {
            result += vector1[i] * vector2[i];
        }
        return result;
    }

    public static float dotProduct(float[] vector1, float[] vector2) {
        float result = 0;
        for (int i = 0; i < vector1.length; i++) {
            result += vector1[i] * vector2[i];
        }
        return result;
    }

    public static double dotProduct(double[] vector1, double[] vector2) {
        double result = 0;
        for (int i = 0; i < vector1.length; i++) {
            result += vector1[i] * vector2[i];
        }
        return result;
    }

    /**
     * One-by-one Multiplication two matrices. Use dotProduct function for
     * normal matrix product.
     *
     * @param mat1
     * @param mat2
     * @return
     */
    public static int[][] times(int[][] mat1, int[][] mat2) {
        int[][] newMat = new int[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = times(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static int[][] times(short[][] mat1, short[][] mat2) {
        int[][] newMat = new int[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = times(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static float[][] times(float[][] mat1, float[][] mat2) {
        float[][] newMat = new float[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = times(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static double[][] times(double[][] mat1, double[][] mat2) {
        double[][] newMat = new double[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = times(mat1[i], mat2[i]);
        }
        return newMat;
    }

    /**
     * The dot product of two matrices.
     *
     * @param mat1
     * @param mat2
     * @return
     */
    public static int[][] dotProduct(int[][] mat1, int[][] mat2) {

        int[][] newMat = new int[mat1.length][mat2[0].length];
        if (mat1.length != mat2[0].length) {
            ArithmeticException ae = new ArithmeticException("The No. of rows of the first matrix should be equal the No. of columns in the second one");
            throw ae;
        } else {

            for (int i = 0; i < newMat.length; i++) {
                for (int j = 0; j < newMat[0].length; j++) {
                    for (int k = 0; k < mat1.length; k++) {
                        newMat[i][j] += mat1[i][k] * mat2[k][j];
                    }
                }
            }

        }
        return newMat;
    }

    public static int[][] dotProduct(short[][] mat1, short[][] mat2) {

        int[][] newMat = new int[mat1.length][mat2[0].length];
        if (mat1.length != mat2[0].length) {
            ArithmeticException ae = new ArithmeticException("The No. of rows of the first matrix should be equal the No. of columns in the second one");
            throw ae;
        } else {

            for (int i = 0; i < newMat.length; i++) {
                for (int j = 0; j < newMat[0].length; j++) {
                    for (int k = 0; k < mat1.length; k++) {
                        newMat[i][j] += mat1[i][k] * mat2[k][j];
                    }
                }
            }

        }
        return newMat;
    }

    public static float[][] dotProduct(float[][] mat1, float[][] mat2) {

        float[][] newMat = new float[mat1.length][mat2[0].length];
        if (mat1.length != mat2[0].length) {
            ArithmeticException ae = new ArithmeticException("The No. of rows of the first matrix should be equal the No. of columns in the second one");
            throw ae;
        } else {

            for (int i = 0; i < newMat.length; i++) {
                for (int j = 0; j < newMat[0].length; j++) {
                    for (int k = 0; k < mat1.length; k++) {
                        newMat[i][j] += mat1[i][k] * mat2[k][j];
                    }
                }
            }

        }
        return newMat;
    }

    public static double[][] dotProduct(double[][] mat1, double[][] mat2) {

        double[][] newMat = new double[mat1.length][mat2[0].length];
        if (mat1.length != mat2[0].length) {
            ArithmeticException ae = new ArithmeticException("The No. of rows of the first matrix should be equal the No. of columns in the second one");
            throw ae;
        } else {

            for (int i = 0; i < newMat.length; i++) {
                for (int j = 0; j < newMat[0].length; j++) {
                    for (int k = 0; k < mat1.length; k++) {
                        newMat[i][j] += mat1[i][k] * mat2[k][j];
                    }
                }
            }

        }
        return newMat;
    }

    /**
     * Divide two vectors.
     *
     * @param vector1
     * @param vector2
     * @return
     */
    public static double[] divide(int[] vector1, int[] vector2) {
        double[] newVector = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = (double) vector1[i] / (double) vector2[i];
        }
        return newVector;
    }

    public static double[] divide(short[] vector1, short[] vector2) {
        double[] newVector = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = (double) vector1[i] / (double) vector2[i];
        }
        return newVector;
    }

    public static float[] divide(float[] vector1, float[] vector2) {
        float[] newVector = new float[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            newVector[i] = vector1[i] / vector2[i];
        }
        return newVector;
    }

    public static double[] divide(double[] vector1, double[] vector2) {
        int length= Math.min(vector1.length, vector2.length);
        double[] newVector = new double[length];
        for (int i = 0; i < length; i++) {
            newVector[i] = vector1[i] / vector2[i];
        }
        return newVector;
    }

    public static double[] divide(int[] vector, int value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = (double) vector[i] / (double) value;
        }
        return newVector;
    }

    public static double[] divide(short[] vector, short value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = (double) vector[i] / (double) value;
        }
        return newVector;
    }

    public static float[] divide(float[] vector, float value) {
        float[] newVector = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] / value;
        }
        return newVector;
    }

    public static double[] divide(double[] vector, double value) {
        double[] newVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] / value;
        }
        return newVector;
    }

    public static double[][] divide(int[][] mat, int value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = divide(mat[i], value);
        }
        return newMat;
    }

    public static double[][] divide(short[][] mat, short value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = divide(mat[i], value);
        }
        return newMat;
    }

    public static float[][] divide(float[][] mat, float value) {
        float[][] newMat = new float[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = divide(mat[i], value);
        }
        return newMat;
    }

    public static double[][] divide(double[][] mat, double value) {
        double[][] newMat = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            newMat[i] = divide(mat[i], value);
        }
        return newMat;
    }

    public static double[][] divide(int[][] mat1, int[][] mat2) {
        double[][] newMat = new double[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = divide(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static double[][] divide(short[][] mat1, short[][] mat2) {
        double[][] newMat = new double[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = divide(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static float[][] divide(float[][] mat1, float[][] mat2) {
        float[][] newMat = new float[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = divide(mat1[i], mat2[i]);
        }
        return newMat;
    }

    public static double[][] divide(double[][] mat1, double[][] mat2) {
        double[][] newMat = new double[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++) {
            newMat[i] = divide(mat1[i], mat2[i]);
        }
        return newMat;
    }

    /**
     * A general math warper function. A specific function is sent as parameter.
     * Usually, the log, exp,... etc functions of the java.lang Math class.
     *
     * @param vector
     * @param mathFucntion
     * @return
     */
    public static double[] gMath(int[] vector, SimpleMathPulgin mathFucntion) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = mathFucntion.calc((double) vector[i]);
        }

        return result;
    }

    public static double[] gMath(short[] vector, SimpleMathPulgin mathFucntion) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = mathFucntion.calc((double) vector[i]);
        }

        return result;
    }
    
    

    public static double[] gMath(float[] vector, SimpleMathPulgin mathFucntion) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = mathFucntion.calc((double) vector[i]);
        }

        return result;
    }

    public static double[] gMath(double[] vector, SimpleMathPulgin mathFucntion) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = mathFucntion.calc(vector[i]);
        }

        return result;
    }
    
    

    public static double[] gMath(SimpleMathPulgin mathFucntion, int length) {
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = mathFucntion.calc();
        }

        return result;
    }

    
    

    public static double[][] gMath(SimpleMathPulgin mathFucntion, int rows, int cols) {
        double[][] result = new double[rows][cols];
        for (double[] vector : result) {
            vector = gMath(mathFucntion, cols);
        }
        return result;
    }

    public static double[][] gMath(int[][] mat, SimpleMathPulgin mathFucntion) {
        double[][] result = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            result[i] = gMath(mat[i], mathFucntion);
        }
        return result;
    }

    public static double[][] gMath(short[][] mat, SimpleMathPulgin mathFucntion) {
        double[][] result = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            result[i] = gMath(mat[i], mathFucntion);
        }
        return result;
    }

    public static double[][] gMath(float[][] mat, SimpleMathPulgin mathFucntion) {
        double[][] result = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            result[i] = gMath(mat[i], mathFucntion);
        }
        return result;
    }

    public static double[][] gMath(double[][] mat, SimpleMathPulgin mathFucntion) {
        double[][] result = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            result[i] = gMath(mat[i], mathFucntion);
        }
        return result;
    }

   
    /**
     * **************************** matrix general method**********************
     */
    /**
     * Create a double matrix given an int one.
     *
     * @param matrix the int matrix
     * @return a double matrix with the same dimensions as the input one.
     */
    public static double[][] toDouble(int[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        double[][] doubleMatrix = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                doubleMatrix[i][j] = matrix[i][j];
            }
        }
        return doubleMatrix;
    }

    public static double[] toDouble(int[] matrix) {
        int length = matrix.length;
        double[] doubleMatrix = new double[length];
        for (int i = 0; i < length; i++) {

            doubleMatrix[i] = matrix[i];
        }

        return doubleMatrix;
    }

    /**
     * Create an int matrix given a double one.
     *
     * @param matrix the double matrix
     * @return a int matrix with the same dimensions as the input one.
     */
    public static int[][] toInteger(double[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        int[][] intMatrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                intMatrix[i][j] = (int) matrix[i][j];
            }
        }
        return intMatrix;
    }

    public static int[] toInteger(double[] matrix) {
        int length = matrix.length;
        int[] intMatrix = new int[length];
        for (int i = 0; i < length; i++) {
            intMatrix[i] = (int) matrix[i];
        }
        return intMatrix;
    }

    /**
     * Create an integer matrix of only two values (e.g. 255 and 0). This can be
     * seen as a thresholding method.
     *
     * @param matrix the double matrix
     * @param threshold a double value threshold used to specify the values of
     * the output matrix. <i>If the input matrix value less than or equal
     * threshold, the corresponding value in the output matrix gets the <code>ubound<\code> value, the
     * lbound otherwise.<\i>
     * @param ubound the upper bound value
     * @param lbound the lower bound value
     * @return an int matrix with the same dimensions as the input one.
     */
    public static int[][] toInteger(double[][] matrix, double threshold, int ubound, int lbound) {
        int height = matrix.length;
        int width = matrix[0].length;
        int[][] intMatrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] >= threshold) {
                    intMatrix[i][j] = ubound;
                } else {
                    intMatrix[i][j] = lbound;
                }
            }
        }
        return intMatrix;
    }

    public static int[] toInteger(double[] matrix, double threshold, int ubound, int lbound) {
        int length = matrix.length;
        int[] intMatrix = new int[length];
        for (int i = 0; i < length; i++) {

            if (matrix[i] >= threshold) {
                intMatrix[i] = ubound;
            } else {
                intMatrix[i] = lbound;
            }
        }

        return intMatrix;
    }

    /**
     * Create an integer matrix of two values given a threshold.
     *
     * @param matrix
     * @param threshold
     * @param ubound
     * @param lbound
     * @see #toInteger(double[][], double, int, int)
     */
    public static int[][] toInteger(int[][] matrix, double threshold, int ubound, int lbound) {
        int height = matrix.length;
        int width = matrix[0].length;
        int[][] intMatrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] >= threshold) {
                    intMatrix[i][j] = ubound;
                } else {
                    intMatrix[i][j] = lbound;
                }
            }
        }
        return intMatrix;
    }

    public static int[] toInteger(int[] matrix, double threshold, int ubound, int lbound) {
        int length = matrix.length;
        int[] intMatrix = new int[length];
        for (int i = 0; i < length; i++) {

            if (matrix[i] >= threshold) {
                intMatrix[i] = ubound;
            } else {
                intMatrix[i] = lbound;
            }
        }

        return intMatrix;
    }
}