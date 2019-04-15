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
 * A dynamic programming solver for several well-known problems
 *
 * @author faroqal-tam
 */
public class DynamicProgramming {

    /**
     * Finds the lowest possible path in a 2D grid. The search starts
     * from the first row to the last one in the cost matrix, i.e. creating a
     * vertical curve. To insure no loops in the path the possible moves are bottom left, bottom right and bottom.
     *
     * @param costMat the cost matrix
     * @return the path
     */
    public Solution cheapestPath(double[][] costMat) {

        LabeledPoint[] solutionPath = new LabeledPoint[costMat.length];

        double bestValue=Double.MAX_VALUE;
        int height = costMat.length;
        int width = costMat[0].length;

        // compute the cost configuration matrix
        double[][] costConfigurationMat = new double[height][width]; // contains the direction of the solution
        int[][] solutionMap = new int[height][width]; // keep track of the solutions
        // the cost at the first row is the cost at the cells only
        for (int j = 0; j < width; j++) {
            costConfigurationMat[0][j] = costMat[1][j];
        }
        // the cost of any other cell= cost of the cell+ min(cost of right bottom, cost of bottom and cost of left bottom)
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double cellCost = costMat[i][j];
                double bestCell = 0;
                if (j == 0) { // the first colum exception, there is only two cells involved
                    bestCell = Math.min(costConfigurationMat[i - 1][j], costConfigurationMat[i - 1][j + 1]);
                    if (bestCell == costConfigurationMat[i - 1][j + 1]) {
                        solutionMap[i][j] = 1; // go right
                    } else {
                        solutionMap[i][j] = 0; // keep the same column 
                    }
                } else if (j == width - 1) { // the last column exception
                    bestCell = Math.min(costConfigurationMat[i - 1][j], costConfigurationMat[i - 1][j - 1]);
                    if (bestCell == costConfigurationMat[i - 1][j - 1]) {
                        solutionMap[i][j] = -1; // go left
                    } else {
                        solutionMap[i][j] = 0; // keep the same column 
                    }
                } else {  // any other cell
                    bestCell= Math.min(Math.min(costConfigurationMat[i - 1][j], costConfigurationMat[i - 1][j - 1]), costConfigurationMat[i - 1][j + 1]);
                    //bestCell= Math.min(bestCell, costConfigurationMat[i][j-1]);
                    if (bestCell == costConfigurationMat[i - 1][j + 1]) {
                        solutionMap[i][j] = 1; // go right column
                    } else if (bestCell == costConfigurationMat[i - 1][j - 1]) {
                        solutionMap[i][j] = -1; // go left column
//                    } else if (bestCell == costConfigurationMat[i][j - 1]) {
//                        solutionMap[i][j] = -1; // keep the same column                       
                    }else
                        solutionMap[i][j] = 0;
                }
                
                costConfigurationMat[i][j]= cellCost+bestCell;

            }
        }
            // find which cell gave the minium at the last row to  track  the solution
               int minIndex=0;
               double minCost=costConfigurationMat[height-1][0];
               for(int j=1;j<width;j++){
                   if(costConfigurationMat[height-1][j]<minCost){
                       minCost=costConfigurationMat[height-1][j];
                       minIndex=j;
                   }
               }
          
           // convert the solution map to solution path
               int row= height-1;
               int col=minIndex;
               while(row>=0){
                   bestValue+=costMat[row][col];
                   
                   col+=solutionMap[row][col]; // where is the move in the column
                   solutionPath[row]= new LabeledPoint(0,col, row); // record the move
                   row--; // go one lower row
               }
        return new Solution(bestValue, solutionPath);
    }
}
