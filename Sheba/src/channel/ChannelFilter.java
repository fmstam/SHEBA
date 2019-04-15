/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package channel;

import util.plugin.Bullet;
import util.plugin.PipeLine;
import util.plugin.Plugin;
import util.Region;
import math.MatrixMath;
import math.SimpleMathPulgin;
import math.VectorMathPlugin;
import math.BiMathPlugin;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * A processing module for the channel class. It basically contains the image
 * processing operation on the matrix of the channel class. Three main
 * operations are implemented: filter, convolve, and combine.
 * @author faroqal-tam and António dos Anjos
 */
public abstract class ChannelFilter {

    // constants and enums
    // the gradient 
    public static enum GRADIENT_TYPE {

        Horizontal, Vertical, Magnitude, Direction
    };

    /**
     * Filter the input channel using an external plug in filter
     *
     * @param filter a plug in filter <link>ChannelFilterPlugin</link>
     * @param inChannel
     * @return a filter channel
     * @see ChannelFilterPlugin
     */
    public static DoubleChannel filter(DoubleChannel inChannel, ChannelFilterPlugin<DoubleChannel, Object> filter, Object... params) {
        return filter.filter(inChannel, params);
    }

    public static IntegerChannel filter(IntegerChannel inChannel, ChannelFilterPlugin<IntegerChannel, Object> filter, Object... params) {
        return filter.filter(inChannel, params);
    }

    /**
     * Apply a pipeline on the input channel
     *
     * @param inChannel
     * @param pipeLine
     * @return
     */
    public static DoubleChannel run(DoubleChannel inChannel, PipeLine pipeLine) {

        Bullet bullet = pipeLine.getNextBullet();
        DoubleChannel outChannel = inChannel;
        while (bullet != null) {
            Plugin plugin = bullet.getPlugin();
            Object payload = bullet.getPayload();

            // find the intende function/plugin and apply the command
            if (plugin instanceof ChannelFilterPlugin) {
                outChannel = filter(outChannel, (ChannelFilterPlugin) plugin, (Object[]) payload);
            } else if (plugin instanceof SimpleMathPulgin) {
                outChannel = calculate(outChannel, (SimpleMathPulgin) plugin);
            } else if (plugin instanceof VectorMathPlugin) {
                outChannel = calculate(outChannel, (VectorMathPlugin) plugin, (double[]) payload);
            }

            bullet = pipeLine.getNextBullet();  // get the next command;

        }

        return outChannel;
    }

    /**
     * Convolve the input channel by a given kernel. Resulted negative or over
     * bound (e.g 255 of grayscale) has to be dealt with in the calling method.
     *
     * @param inChannel
     * @param kernel
     * @return a convolved version of the input channel.
     */
    public static DoubleChannel convolve(DoubleChannel inChannel, double[][] kernel) {

        int uc = kernel.length / 2;
        int vc = kernel[0].length / 2;
        int rows = inChannel.numRows();
        int cols = inChannel.numCols();

        DoubleChannel channel = new DoubleChannel(rows, cols);
        double sum;

        int r = 0;
        int c = 0;
        // check the ROI first 
        // case 1: rectanglular ROI
        Region roi = inChannel.getROI();
        if (roi != null && roi.getType() == Region.REGION_TYPE.Rectangle) {// change the rectangle
            Rectangle rect = roi.getRect();
            r = rect.y;
            c = rect.x;
            rows = rect.height;
            cols = rect.width;
        }
        // convoluve 
        for (int row = r; row < rows; row++) {
            for (int col = c; col < cols; col++) {
                sum = 0.0;
                for (int v = -vc; v <= vc; v++) {
                    for (int u = -uc; u <= uc; u++) {
                        sum += inChannel.get((row - v), (col - u), -1) * kernel[v + vc][u + uc];
                    }
                }
                //CAUTION: take care of the conversion of image when <0 and >255
                channel.set(row, col, sum);
            }

        }

        // case 2: point list ROI
        if (roi != null && roi.getType() == Region.REGION_TYPE.PointList) {
            LinkedList<Point> points = roi.getPointList();
            for (Point p : points) {
                // comvoulve
                sum = 0.0;
                int row = p.y;
                int col = p.x;
                for (int v = -vc; v <= vc; v++) {
                    for (int u = -uc; u <= uc; u++) {
                        sum += inChannel.get((row - v), (col - u), -1) * kernel[v + vc][u + uc];
                    }
                }
            }
        }
        return channel;
    }

    /**
     * Combine a set of channels to a channel using the weighed sum method. This
     * is very helpful in conversion (e.g color to grayscale images)
     *
     * @param channels
     * @param weights
     * @return
     */
    public static DoubleChannel combine(DoubleChannel[] channels, double[] weights) {
        int numChannels = channels.length;
        int numWeights = weights.length;

        int rows = channels[0].numRows();
        int cols = channels[0].numCols();
        if (numChannels != numWeights) {
            throw new IllegalArgumentException("The number of weights(" + numWeights + ") has to be equal the number of channels(" + numChannels + ")");
        }

        // combine the channels
        DoubleChannel channel = new DoubleChannel('\0', rows, cols);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double sum = 0.0;
                for (int k = 0; k < numChannels; k++) {
                    sum += channels[k].get(r, c) * weights[k];
                }
                channel.set(r, c, sum);
            }
        }

        return channel;
    }

    /**
     * Apply general floating point math plugins to the input channel
     *
     * @param inChannel
     * @param method
     * @return
     * @see SimpleMathPulgin
     * @see MatrixMath
     * @see DoubleChannel
     * @see Math
     */
    public static DoubleChannel calculate(DoubleChannel inChannel, SimpleMathPulgin method) {
        double[] result = MatrixMath.gMath(inChannel.getRawData(), method);
        return new DoubleChannel(inChannel.getCode(), result, inChannel.numRows(), inChannel.numCols());
    }

    /**
     * Run several math operations on the same channel as a pipeline. The
     * pipeline should only contains plugins from simple and bimath.
     *
     * @param inChannel
     * @param pipeLine
     * @return
     * @see SimpleMathPulgin
     * @see BiMathPlugin
     */
    public static DoubleChannel calculate(DoubleChannel inChannel, PipeLine pipeLine) {
        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();
        // the result channel
        DoubleChannel outChannel = new DoubleChannel(numRows, numCols);

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                PipeLine temp = new PipeLine(pipeLine);
                Bullet bullet = temp.getNextBullet();
                double rsValue = inChannel.get(r, c);
                while (bullet != null) {
                    Plugin plugin = bullet.getPlugin();
                    Object payload = bullet.getPayload();
                    if (plugin instanceof SimpleMathPulgin) {
                        rsValue = ((SimpleMathPulgin) plugin).calc(rsValue);
                    } else if (plugin instanceof BiMathPlugin) {
                        Object[] v2=((Object[])(payload));
                        double v1=(double) v2[0];
                        rsValue = ((BiMathPlugin) plugin).calc(rsValue,v1);
                    }

                    // get next pip
                    bullet = temp.getNextBullet();
                }
                outChannel.set(r,c,rsValue);
            }
        }

        return outChannel;
    }

    public static DoubleChannel calculate(DoubleChannel inChannel, VectorMathPlugin method, double... params) {
        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();
        DoubleChannel outChannel = new DoubleChannel(inChannel.getCode(), method.calc(inChannel.getRawData(), params), numRows, numCols);
        return outChannel;
    }
    
    
    public static DoubleChannel calculate(DoubleChannel inChannel1, BiMathPlugin method, DoubleChannel inChannel2) {
        int numRows = Math.min(inChannel1.numRows(),inChannel2.numRows());
        int numCols = Math.min(inChannel1.numCols(),inChannel2.numCols());
        DoubleChannel outChannel = new DoubleChannel(numRows, numCols);
        
        for(int r=0;r<numRows;r++)
            for(int c=0; c<numCols; c++){
                outChannel.set(r, c, method.calc(inChannel1.get(r, c), inChannel2.get(r, c)));
            }
        return outChannel;
    }

    /**
     * Combine a set of channels to a channel using the weighed sum method. This
     * is very helpful in conversion (e.g color to grayscale images)
     *
     * @param channels
     * @param weights
     * @return
     */
    public static IntegerChannel combine(IntegerChannel[] channels, double[] weights) {
        int numChannels = channels.length;
        int numWeights = weights.length;

        int rows = channels[0].numRows();
        int cols = channels[0].numCols();
        if (numChannels != numWeights) {
            throw new IllegalArgumentException("The number of weights(" + numWeights + ") has to be equal the number of channels(" + numChannels + ")");
        }
        // combine the channels
        IntegerChannel channel = new IntegerChannel('\0', rows, cols);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                short sum = 0;
                for (int k = 0; k < numChannels; k++) {
                    sum += channels[k].get(r, c) * weights[k];
                }
                channel.set(r, c, sum);
            }
        }

        return channel;
    }
}
