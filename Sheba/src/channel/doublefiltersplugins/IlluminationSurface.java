/*
 * Copyright (C) 2013 faroqal-tam
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
package channel.doublefiltersplugins;

import channel.ChannelFilterPlugin;
import channel.DoubleChannel;
import math.PolynomialFactory;

/**
 *  Illumination surface estimator from the input image
 * @author faroqal-tam
 */
public class IlluminationSurface implements ChannelFilterPlugin<DoubleChannel, Object> {

    /**
     * The spline type
     */
    public static enum SurfaceType {

        Polynomial, BSpline
    };
    private SurfaceType type;
    private int polyDegree;

    public int getPolyDegree() {
        return polyDegree;
    }

    public void setPolyDegree(int polyDegree) {
        this.polyDegree = polyDegree;
    }

    public SurfaceType getType() {
        return type;
    }

    public void setType(SurfaceType type) {
        this.type = type;
    }

    public IlluminationSurface(SurfaceType type, int polyDegree) {
        this.type = type;
        this.polyDegree = polyDegree;
    }

   

    /**
     * Estimate a polynomial surface of the input channel given a polynomial degree.
     * @param inChannel
     * @param polyDegree
     * @return 
     */
    public DoubleChannel polynomialSurface(DoubleChannel inChannel) {
        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();
        
        // find the cofficentents
        PolynomialFactory factory = new PolynomialFactory();
        double[] paramVector = factory.polynomialFitQRpivot(inChannel.getRawData(), numRows, numCols, polyDegree);
        // build up the surface
        return new DoubleChannel(factory.polynomial2D(numRows, numCols, polyDegree, paramVector));

    }
    
    
     

    @Override
    public DoubleChannel filter(DoubleChannel inChannel, Object... params) {
        //if(type== SurfaceType.Polynomial){
        return polynomialSurface(inChannel);
        // }
        //    return cubicBSplineSurface(inChannel, (Integer)params[0]); 
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String description() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
