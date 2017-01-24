package dragoncurve;

import java.util.Arrays;

public class DragonCurve {
    
    static double[][] curve;

    public static void main(String[] args) {
        initializeCurve();
        
        System.out.println("getQuasiMidpoint(curve[0], curve[1], <boolean>): " + Arrays.toString(getQuasiMidpoint(curve[0], curve[1], false)));
    }
    
    public static double[] getQuasiMidpoint(double[] start, double end[], boolean swap) {
        double[] toReturn = new double[2];
        double[] midpoint = getMidpoint(start, end);
        int[] direction = getDirection(start, end);
        if (direction[0] == 1 && direction[1] == 0) {
            toReturn[0] = midpoint[0];
            if (swap) toReturn[1] = midpoint[1] + (getDistance(start, end) / 2);
            else toReturn[1] = midpoint[1] - (getDistance(start, end) / 2);
        } else if (direction[0] == 1 && direction[1] == 1) {
            if (swap) {
                toReturn[0] = start[0];
                toReturn[1] = end[1];
            }
            else {
                toReturn[0] = end[0];
                toReturn[1] = start[1];
            }
        } else if (direction[0] == 0 && direction[1] == 1) {
	    toReturn[1] = midpoint[1];
	    if (swap) toReturn[0] = midpoint[0] - (getDistance(start, end) / 2);
	    else toReturn[0] = midpoint[0] + (getDistance(start, end) / 2);
	}
        return toReturn;
    }
    
    public static double[] getMidpoint(double[] one, double[] two) {
        double[] toReturn = new double[2];
        toReturn[0] = (one[0] + two[0]) / 2.0;
        toReturn[1] = (one[1] + two[1]) / 2.0;
        return toReturn;
    }
    
    public static int[] getDirection(double[] start, double[] end) {
        int[] toReturn = new int[2]; double[] temp = unitVector(start, end);
        if (Math.signum(temp[0]) == 1) toReturn[0] = 1;
        else if (Math.signum(temp[0]) == -1) toReturn[0] = -1;
        else toReturn[0] = 0;
        if (Math.signum(temp[1]) == 1) toReturn[1] = 1;
        else if (Math.signum(temp[1]) == -1) toReturn[1] = -1;
        else toReturn[1] = 0;
        return toReturn;
    }
    
    public static  double getDistance(double[] start, double[] end) {
        double[] vec = new double[2]; vec[0] = end[0] - start[0]; vec[1] = end[1] - start[1];
        return getDistance(vec);
    }
    
    public static  double getDistance(double[] vec) { 
        return Math.sqrt(Math.pow(vec[0],2) + Math.pow(vec[1],2));
    }
    
    public static double[] unitVector(double[] start, double[] end) {
        double[] vec = new double[2]; vec[0] = end[0] - start[0]; vec[1] = end[1] - start[1];
        return unitVector(vec);
    }
    
    public static double[] unitVector(double[] vec) {
        double[] toReturn = new double[2];
        double mag = getDistance(vec);
        toReturn[0] = vec[0] / mag;
        toReturn[1] = vec[1] / mag;
        return toReturn;
    }
    
    private static void initializeCurve() {
        curve = new double[100][2];
        
        curve[0][0] = 0; curve[0][1] = 0; //  /
        curve[1][0] = 1; curve[1][1] = 1; // /
        
        /*curve[0][0] = 0; curve[0][1] = 0; // __
        curve[1][0] = 1; curve[1][1] = 0;*/
    }
}
