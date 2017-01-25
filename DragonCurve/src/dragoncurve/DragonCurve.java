package dragoncurve;

import java.util.ArrayList;

public class DragonCurve {
    
    static ArrayList<ArrayList<Double>> curve = new ArrayList<>();
    static ArrayList<Double> point1 = new ArrayList<>(), point2 = new ArrayList<>();

    public static void main(String[] args) {
        initializeCurve();
        
        System.out.println("getQuasiMidpoint(curve.get(0), curve.get(1), <boolean>): " + getQuasiMidpoint(curve.get(0), curve.get(1), true));
        
        ArrayList<ArrayList<Double>> temp = curve; curve = new ArrayList<>(); curve.add(temp.get(0));
        
        for (int i = 1; i < temp.size(); i++) {
            curve.add(new ArrayList<Double>());
            curve.add(temp.get(i));
        }
        
        System.out.println("curve.toString(): " + curve.toString());
        
        try{
            System.out.println("curve.get(1).get(0): " + curve.get(1).get(0));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("curve.get(1).get(0) throws an IndexOutOfBoundsException");
        }
        
        curve.remove(1);
        curve.add(1, getQuasiMidpoint(temp.get(0), temp.get(1), true));
        
        System.out.println("curve.toString(): " + curve.toString());
        
        for (int x = 0; x < 4; x++) {
            temp = new ArrayList<>(curve); curve = new ArrayList<>(); curve.add(temp.get(0));
            for (int i = 1; i < temp.size(); i++) {
                curve.add(getQuasiMidpoint(temp.get(i - 1), temp.get(i), true));
                curve.add(temp.get(i));
            }
        }
        
        System.out.println("curve.toString(): " + curve.toString());
    }
    
    public static ArrayList<Double> getQuasiMidpoint(ArrayList<Double> start, ArrayList<Double> end, boolean swap) {
        ArrayList<Double> midpoint = getMidpoint(start, end);
        ArrayList<Integer> direction = getDirection(start, end); //System.out.println("direction: " + Arrays.toString(direction));
        return checkDirections(start, end, direction, midpoint, swap);
    }
    
    public static ArrayList<Double> getMidpoint(ArrayList<Double> one, ArrayList<Double> two) {
        ArrayList<Double> toReturn = new ArrayList<>();
        toReturn.add((one.get(0) + two.get(0)) / 2.0);
        toReturn.add((one.get(1) + two.get(1)) / 2.0);
        return toReturn;
    }
    
    public static ArrayList<Integer> getDirection(ArrayList<Double> start, ArrayList<Double> end) {
        ArrayList<Integer> toReturn = new ArrayList<Integer>(); ArrayList<Double> temp = unitVector(start, end); //System.out.println("unitVector: " + Arrays.toString(temp));
        if (Math.signum(temp.get(0)) == 1) toReturn.add(1);
        else if (Math.signum(temp.get(0)) == -1) toReturn.add(-1);
        else toReturn.add(0);
        if (Math.signum(temp.get(1)) == 1) toReturn.add(1);
        else if (Math.signum(temp.get(1)) == -1) toReturn.add(1);
        else toReturn.add(0);
        return toReturn;
    }
    
    public static double getDistance(ArrayList<Double> start, ArrayList<Double> end) {
        ArrayList<Double> vec = new ArrayList<>(); vec.add(end.get(0) - start.get(0)); vec.add(end.get(1) - start.get(1));
        return getDistance(vec);
    }
    
    public static double getDistance(ArrayList<Double> vec) { 
        return Math.sqrt(Math.pow(vec.get(0),2) + Math.pow(vec.get(1),2));
    }
    
    public static ArrayList<Double> unitVector(ArrayList<Double> start, ArrayList<Double> end) {
        ArrayList<Double> vec = new ArrayList<>(); vec.add(end.get(0) - start.get(0)); vec.add(end.get(1) - start.get(1)); //System.out.println("vec: " + Arrays.toString(vec));
        return unitVector(vec);
    }
    
    public static ArrayList<Double> unitVector(ArrayList<Double> vec) {
        ArrayList<Double> toReturn = new ArrayList<>();
        double mag = getDistance(vec); //System.out.println("getDistance: " + mag);
        toReturn.add(vec.get(0) / mag);
        toReturn.add(vec.get(1) / mag);
        return toReturn;
    }
    
    private static void initializeCurve() {
        
        /**/
        point1.add(0.0); point1.add(0.0); //  ^
        point2.add(1.0); point2.add(1.0); // /
        /**/
        
        curve.add(point1); curve.add(point2);
    }
    
    private static ArrayList<Double> checkDirections(ArrayList<Double> start, ArrayList<Double> end, ArrayList<Integer> direction, ArrayList<Double> midpoint, boolean swap) {
        ArrayList<Double> toReturn = new ArrayList<>();
        if (direction.get(0) == 1 && direction.get(1) == 0) {
            toReturn.add(midpoint.get(0));
            if (swap) toReturn.add(midpoint.get(1) + (getDistance(start, end) / 2));
            else toReturn.add(midpoint.get(1) - (getDistance(start, end) / 2));
        } else if (direction.get(0) == 1 && direction.get(1) == 1) {
            if (swap) {
                toReturn.add(start.get(0));
                toReturn.add(end.get(1));
            }
            else {
                toReturn.add(end.get(0));
                toReturn.add(start.get(1));
            }
        } else if (direction.get(0) == 0 && direction.get(1) == 1) {
	    if (swap) toReturn.add(midpoint.get(0) - (getDistance(start, end) / 2));
	    else toReturn.add(midpoint.get(0) + (getDistance(start, end) / 2));
	    toReturn.add(midpoint.get(1));
	} else if (direction.get(0) == -1 && direction.get(1) == 1) {
            if (!swap) {
                toReturn.add(start.get(0));
                toReturn.add(end.get(1));
            }
            else {
                toReturn.add(end.get(0));
                toReturn.add(start.get(1));
            }
        } else if (direction.get(0) == -1 && direction.get(1) == 0) {
            toReturn.add(midpoint.get(0));
            if (swap) toReturn.add(midpoint.get(1) - (getDistance(start, end) / 2));
            else toReturn.add(midpoint.get(1) + (getDistance(start, end) / 2));
        } else if (direction.get(0) == -1 && direction.get(1) == -1) {
            if (swap) {
                toReturn.add(start.get(0));
                toReturn.add(end.get(1));
            }
            else {
                toReturn.add(end.get(0));
                toReturn.add(start.get(1));
            }
        } else if (direction.get(0) == 0 && direction.get(1) == -1) {
            if (swap) toReturn.add(midpoint.get(0) + (getDistance(start, end) / 2));
            else toReturn.add(midpoint.get(0) - (getDistance(start, end) / 2));
            toReturn.add(midpoint.get(1));
        }
        return toReturn;
    }
}
