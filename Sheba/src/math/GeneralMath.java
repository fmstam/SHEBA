/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 * General mathematical functions class.
 *
 * @author faroqal-tam
 */
public abstract class GeneralMath {

    public static SimpleMathPulgin abs = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) { // abs only expect a single param, igonor anything else
            return Math.abs(input[0]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin ceil = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.ceil(input[0]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin exp = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.exp(input[0]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin floor = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.floor(input[0]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin log = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.log(input[0]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin log10 = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.log10(input[0]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin log1p = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.log1p(input[0]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin pow = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.pow(input[0], input[1]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin random = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.random();
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin sign = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.signum(input[0]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    public static SimpleMathPulgin sqrt = new SimpleMathPulgin() {
        @Override
        public double calc(double... input) {
            return Math.sqrt(input[0]);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    /**
     * ***** plugins for vector to vector calculations ***
     */
    public static VectorMathPlugin normalize = new VectorMathPlugin() {

        @Override
        public double[] calc(double[] params1, double... param2) {

            double[] result = null;
            if (param2.length == 0) {
                result=MatrixMath.normalize(params1); // one vector is expected only, ignore the reset
            } else {
                result=MatrixMath.normalize(params1, param2[0]); // we might need to change it from 0...1 to 0...n    
            }
            return result;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    
    
    /**
     * ***** plugins for vector to vector calculations ***
     */
    public static VectorMathPlugin bound = new VectorMathPlugin() {

        @Override
        public double[] calc(double[] params1, double... param2) {

            double[] result = null;
            result=MatrixMath.bound(params1, param2[0], param2[1]);
            return result;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    
    public static VectorMathPlugin times = new VectorMathPlugin() {
        @Override
        public double[] calc(double[] params1, double... params2) {
            double[] result;
            if (params2.length == 1) { // vector X value
                result = MatrixMath.times(params1, params2[0]);
            } else { // vector X vector
                result = MatrixMath.times(params1, params2);
            }

            return result;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    /**
     * change the range of the input vector from 0...1 to a range specified by
     * the min and max (params2)
     */
    public static VectorMathPlugin range = new VectorMathPlugin() {

        @Override
        public double[] calc(double[] params1, double... params2) {

            double[] result = MatrixMath.range(params1, params2[0] /*min*/, params2[1] /*max*/);

            return result;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    public static VectorMathPlugin add = new VectorMathPlugin() {
        @Override
        public double[] calc(double[] params1, double... params2) {
            double[] result = null;
            if (params2.length == 1) { // vector + value
                result = MatrixMath.add(params1, params2[0]);
            } else { // vector + vector
                result = MatrixMath.add(params1, params2);
            }

            return result;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };

    public static VectorMathPlugin subtract = new VectorMathPlugin() {
        @Override
        public double[] calc(double[] params1, double... params2) {
            double[] result = null;
            if (params2.length == 1) { // vector / value
                result = MatrixMath.subtract(params1, params2[0]);
            } else { // vector / vector
                result = MatrixMath.subtract(params1, params2);
            }

            return result;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };

    public static VectorMathPlugin divide = new VectorMathPlugin() {
        @Override
        public double[] calc(double[] params1, double... params2) {
            double[] result = null;
            if (params2.length == 1) { // vector / value
                result = MatrixMath.divide(params1, params2[0]);
            } else { // vector / vector
                result = MatrixMath.divide(params1, params2);
            }

            return result;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };

    
    
    public static BiMathPlugin plus = new BiMathPlugin() {

        @Override
        public double calc(double value1, double value2) {
            return value1 + value2;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    public static BiMathPlugin minus = new BiMathPlugin() {

        @Override
        public double calc(double value1, double value2) {
            return value1 - value2;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    public static BiMathPlugin by = new BiMathPlugin() {

        @Override
        public double calc(double value1, double value2) {
            return value1 * value2;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    public static BiMathPlugin div = new BiMathPlugin() {

        @Override
        public double calc(double value1, double value2) {
            return value1 / value2;
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    public static BiMathPlugin squaredDifference= new BiMathPlugin() {

        @Override
        public double calc(double value1, double value2) {
            return Math.pow(value1-value2,2);
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String description() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

}
