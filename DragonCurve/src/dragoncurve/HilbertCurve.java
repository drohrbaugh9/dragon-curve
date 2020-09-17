package dragoncurve;

import java.util.ArrayList;
import java.util.Arrays;

public class HilbertCurve {
    
    static ArrayList<ArrayList<Double>> curve = new ArrayList<>();
    static ArrayList<Double> point1 = new ArrayList<>(), point2 = new ArrayList<>(), point3 = new ArrayList<>(), point4 = new ArrayList<>(), point5 = new ArrayList<>();
    
    static int iterations = 1;
    
    public static void main(String[] args) {
        initializeCurve();
        
        ArrayList<ArrayList<Double>> temp;
        
        for (int x = 0; x < iterations; x++) {
            //System.out.println("\n---Iteration " + (x + 1) + "---");
            temp = new ArrayList<>(curve); curve = new ArrayList<>(); curve.add(temp.get(0));
            for (int i = 3; i < temp.size(); i+=4) {
                ArrayList<ArrayList<Double>> newPoints = transform(temp.get(i - 3), temp.get(i));
                for (ArrayList<Double> a : newPoints) {
                    curve.add(a);
                }
                curve.add(temp.get(i));
            }
        }
        
        System.out.println("curve.toString(): " + curve.toString());
        
        System.out.println("toStringToSVG_Code(curve): " + DragonCurve.toStringToSVG_Code(curve.toString()));
        
        double maxx = 0, minx = curve.get(0).get(0), maxy = 0, miny = curve.get(0).get(1), x, y;
        
        for (int i = 0; i < curve.size(); i++) {
            x = curve.get(i).get(0); y = curve.get(i).get(1);
            if (x > maxx) maxx = x;
            else if (x < minx) minx = x;
            if (y > maxy) maxy = y;
            else if (y < miny) miny = y;
        }
        
        System.out.println("max x: " + maxx + "; max y: " + maxy + "; min x: " + minx + "; min y: " + miny);
        System.out.println("width: " + (maxx - minx) + "; height: " + (maxy - miny));
    }
    
    private static void initializeCurve() {
        
        /**/
        point1.add(0.0); point1.add(0.0);
        point2.add(0.0); point2.add(30.0);
        point3.add(30.0); point3.add(30.0);
        point4.add(30.0); point4.add(0.0);
        //point5.add(0.0); point5.add(0.0);
        /**/
        
        curve.add(point1); curve.add(point2); curve.add(point3); curve.add(point4); //curve.add(point5);
    }
    
    private static ArrayList<ArrayList<Double>> transform(ArrayList<Double> start, ArrayList<Double> end) {
        double[] vec = new double[2]; vec[0] = end.get(0) - start.get(0); vec[1] = end.get(1) - start.get(1);
        ArrayList<ArrayList<Double>> middle = new ArrayList<>();
        
        if (vec[0] == 0) {
            if (vec[1] > 0) {
                
            }
        }
        
        return middle;
    }
}
