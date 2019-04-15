/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

/**
 *
 * @author faroqal-tam
 */
public abstract class GeneralClustering {
    /**
     * General iso histogram clustering approach. In this case clustering the input histogram 
     * @param histogram the histogram of the input image
     * @return an integer threshold value
     */
    public static int IsoData(int[] histogram) {
        
        int level;
        int maxValue = histogram.length - 1;
        double result, sum1, sum2, sum3, sum4;

        // locate the range of the histogram 
        int min = 0;
        while ((histogram[min] == 0) && (min < maxValue)) {
            min++;
        }
        int max = maxValue;
        while ((histogram[max] == 0) && (max > 0)) {
            max--;
        }


        if (min >= max) {
            level = histogram.length / 2;
            return level;

        }

        int movingIndex = min;

        // approximation of the means
        // the mean of an image can be calculated from the histogram as:
        //        sum i*hisogram(i)  / sum histogram(i)

        do {
            sum1 = sum2 = sum3 = sum4 = 0.0;
            for (int i = min; i <= movingIndex; i++) {
                sum1 += i * histogram[i];
                sum2 += histogram[i];
            }
            for (int i = (movingIndex + 1); i <= max; i++) {
                sum3 += i * histogram[i];
                sum4 += histogram[i];
            }
            result = (sum1 / sum2 + sum3 / sum4) / 2.0;
            movingIndex++;
        } while ((movingIndex + 1) <= result && movingIndex < max - 1);
         
        level = (int) Math.round(result);
        return level;

    }
}
