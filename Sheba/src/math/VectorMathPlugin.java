/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import util.plugin.Plugin;

/**
 * A plugin interface for creating a function that accept any number of double parameters and return a vector of double.
 * @author faroqal-tam
 */
public interface VectorMathPlugin  extends Plugin{
    double [] calc(double []params1, double ... params2);
}
