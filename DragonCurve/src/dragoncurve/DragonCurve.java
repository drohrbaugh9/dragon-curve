package dragoncurve;

import java.util.ArrayList;

public class DragonCurve {
    
    static ArrayList<ArrayList<Double>> curve = new ArrayList<>();
    static ArrayList<Double> point1 = new ArrayList<>(), point2 = new ArrayList<>();

    public static void main(String[] args) {
        initializeCurve();
        
        ArrayList<ArrayList<Double>> temp; boolean swap = true;
        
        for (int x = 0; x < 12; x++) {
            //System.out.println("\n---Iteration " + (x + 1) + "---");
            temp = new ArrayList<>(curve); curve = new ArrayList<>(); curve.add(temp.get(0));
            swap = true;
            for (int i = 1; i < temp.size(); i++) {
                curve.add(getQuasiMidpoint(temp.get(i - 1), temp.get(i), swap));
                curve.add(temp.get(i));
                swap = !swap;
            }
        }
        
        System.out.println("curve.toString(): " + curve.toString());
        
        System.out.println("toStringToSVG_Code(curve): " + toStringToSVG_Code(curve.toString()));
        
        double maxx = 0, minx = curve.get(0).get(0), maxy = 0, miny = curve.get(0).get(1), x, y;
        
        for (int i = 0; i < curve.size(); i++) {
            x = curve.get(i).get(0); y = curve.get(i).get(1);
            if (x > maxx) maxx = x;
            else if (x < minx) minx = x;
            if (y > maxy) maxy = y;
            else if (y < miny) miny = y;
        }
        
        System.out.println("max x: " + maxx + "; max y: " + maxy + "; min x: " + minx + "; min y: " + miny);
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
        ArrayList<Integer> toReturn = new ArrayList<>(); ArrayList<Double> temp = unitVector(start, end); //System.out.println("unitVector: " + temp.toString());
        if (Math.signum(temp.get(0)) == 1) toReturn.add(1);
        else if (Math.signum(temp.get(0)) == -1) toReturn.add(-1);
        else toReturn.add(0);
        if (Math.signum(temp.get(1)) == 1) toReturn.add(1);
        else if (Math.signum(temp.get(1)) == -1) toReturn.add(-1);
        else toReturn.add(0);
        //System.out.println("getDirection(" + start.toString() + ", " + end.toString() + "): " + toReturn.toString());
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
        point1.add(0.0); point1.add(0.0);
        point2.add(32.0); point2.add(32.0);
        /**/
        
        curve.add(point1); curve.add(point2);
    }
    
    private static ArrayList<Double> checkDirections(ArrayList<Double> start, ArrayList<Double> end, ArrayList<Integer> direction, ArrayList<Double> midpoint, boolean swap) {
        ArrayList<Double> toReturn = new ArrayList<>();
        if (direction.get(0) == 1 && direction.get(1) == 0) { // E
            //System.out.println("E");
            toReturn.add(midpoint.get(0));
            if (swap) toReturn.add(midpoint.get(1) + (getDistance(start, end) / 2));
            else toReturn.add(midpoint.get(1) - (getDistance(start, end) / 2));
        } else if (direction.get(0) == 1 && direction.get(1) == 1) { // NE
            //System.out.println("NE");
            if (swap) {
                toReturn.add(start.get(0));
                toReturn.add(end.get(1));
            }
            else {
                toReturn.add(end.get(0));
                toReturn.add(start.get(1));
            }
        } else if (direction.get(0) == 0 && direction.get(1) == 1) { // N
            //System.out.println("N");
	    if (swap) toReturn.add(midpoint.get(0) - (getDistance(start, end) / 2));
	    else toReturn.add(midpoint.get(0) + (getDistance(start, end) / 2));
	    toReturn.add(midpoint.get(1));
	} else if (direction.get(0) == -1 && direction.get(1) == 1) { // NW
            //System.out.println("NW");
            if (!swap) {
                toReturn.add(start.get(0));
                toReturn.add(end.get(1));
            }
            else {
                toReturn.add(end.get(0));
                toReturn.add(start.get(1));
            }
        } else if (direction.get(0) == -1 && direction.get(1) == 0) { // W
            //System.out.println("W");
            toReturn.add(midpoint.get(0));
            if (swap) toReturn.add(midpoint.get(1) - (getDistance(start, end) / 2));
            else toReturn.add(midpoint.get(1) + (getDistance(start, end) / 2));
        } else if (direction.get(0) == -1 && direction.get(1) == -1) { // SW
            //System.out.println("SW");
            if (swap) {
                toReturn.add(start.get(0));
                toReturn.add(end.get(1));
            }
            else {
                toReturn.add(end.get(0));
                toReturn.add(start.get(1));
            }
        } else if (direction.get(0) == 0 && direction.get(1) == -1) { // S
            //System.out.println("S");
            if (swap) toReturn.add(midpoint.get(0) + (getDistance(start, end) / 2));
            else toReturn.add(midpoint.get(0) - (getDistance(start, end) / 2));
            toReturn.add(midpoint.get(1));
        } else if (direction.get(0) == 1 && direction.get(1) == -1) { // SE
            //System.out.println("SE");
            if (!swap) {
                toReturn.add(start.get(0));
                toReturn.add(end.get(1));
            }
            else {
                toReturn.add(end.get(0));
                toReturn.add(start.get(1));
            }
        }
        //System.out.println("getQuasiMidpoint(" + start.toString() + ", " + end.toString() + ", " + swap + "): " + toReturn.toString());
        return toReturn;
    }
    
    private static String toStringToSVG_Code(String s) {
        s = s.replaceAll("\\[\\[", "M");
        s = s.replaceAll("\\]\\]", "");
        s = s.replaceAll("\\], \\[", " L");
        s = s.replaceAll(",", "");
        return s;
    }
}
