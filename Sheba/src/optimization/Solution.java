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
package optimization;

import util.LabeledPoint;

/**
 * A solution class that contains the solution value and may be the structure
 * @author faroqal-tam
 */
public class Solution {
    
    double value;       // best value found
    int [] soution1D; // in some cases the solution is a vector
    int [][] solution2D; // 2D solution
    LabeledPoint [] solutionPoints; 

    public Solution(LabeledPoint[] solutionPoints) {
        this.solutionPoints = solutionPoints;
    }

    public Solution(double value, LabeledPoint[] solutionPoints) {
        this.value = value;
        this.solutionPoints = solutionPoints;
    }
    
    
    public Solution(double value, int[] soution1D) {
        this.value = value;
        this.soution1D = soution1D;
    }

    public Solution(int value, int[][] solution2D) {
        this.value = value;
        this.solution2D = solution2D;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int[] getSoution1D() {
        return soution1D;
    }

    public void setSoution1D(int[] soution1D) {
        this.soution1D = soution1D;
    }

    public int[][] getSolution2D() {
        return solution2D;
    }

    public void setSolution2D(int[][] solution2D) {
        this.solution2D = solution2D;
    }

    public LabeledPoint[] getSolutionPoints() {
        return solutionPoints;
    }

    public void setSolutionPoints(LabeledPoint[] solutionPoints) {
        this.solutionPoints = solutionPoints;
    }
    
    
    
    
}
