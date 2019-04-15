/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering.plugins;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import util.DoubleLabledPoint;

/**
 * The Kmeans clustering approach.
 *
 * @author faroqal-tam
 */
public class Kmeans implements ClustererPlugin{

    

    /**
     * K-means clustering method for
     * @param k the number of clusters
     * @param dataPoints expected to be normalized between 0 and 1
     */
    public void kmeansLabeling(DoubleLabledPoint[] dataPoints, int k) {
        LinkedList<Double> means = new LinkedList();

        // extermes
        means.addFirst(1.0);
        means.addFirst(0.0);

        // update 
        for (int i = 0; i < dataPoints.length; i++) {
            // System.out.println("voxel id:" + i + "\t value:"+ voxels[i].getGrayValue());
            int nearest = nearest(dataPoints[i], means);

            double mean = means.get(nearest);
            mean = (dataPoints[i].getValue() + mean) / 2;
            means.set(nearest, mean);
            // label the pixel
            dataPoints[i].setLabel(nearest);
        }

        // post fix the clusters
        for (int i = 0; i < dataPoints.length; i++) {
            dataPoints[i].setLabel(nearest(dataPoints[i], means));
        }

    }

    private int nearest(DoubleLabledPoint voxel, List<Double> means) {
        int nearest = -1;

        double distance = Double.MAX_VALUE;
        int i = 0;
        for (Double cp : means) {

            double d = Math.pow(voxel.getValue() - cp, 2);
            if (d < distance) {
                nearest = i;
                distance = d;
            }
            i++;
        }

        return nearest;
    }

   

    @Override
    public void cluster(DoubleLabledPoint []dataPoints, double... params) {
        
        // params are expected to be
        // params[0]= the number of clusters
        // params[1]= lables
        kmeansLabeling(dataPoints, (int)params[0]);
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String description() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map cluster(double[] data, double... params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
