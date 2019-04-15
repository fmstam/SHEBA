/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import util.plugin.Plugin;

/**
 * This plugin is used to create a simple math function that accept a set of double values and return a single value. 
 * @author faroqal-tam
 */
public interface SimpleMathPulgin extends Plugin{
    public double calc(double ... value);
    
}
