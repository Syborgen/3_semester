package VVP_lab_2;



import java.util.Arrays;

import org.junit.Test;

public class VVP_lab_2Test {

    @Test
    public void variant() {
        VVP_lab_2 a = new VVP_lab_2();
        int[][] cur1={{1,5,6},{8,4,2},{9,0,4}};
        int[][] actual1 =VVP_lab_2.variant(cur1);
        int[][] expected1={{1,5,6},{8,4,2},{0,4,9}};
        Arrays.equals(expected1, actual1);
        int[][] cur2 = {{3,8,7},{44,2,48},{6,8,29}};
        int[][] actual2 = VVP_lab_2.variant(cur2);
        int[][] expected2 = {{3,7,8},{48,44,2},{6,8,29}};
        Arrays.equals(expected2, actual2);
        int[][] cur3 = {{2,1},{5,7}};
        int[][] actual3 = VVP_lab_2.variant(cur3);
        int[][] expected3 = {{1,2},{7,5}};
        Arrays.equals(expected3, actual3);
        int[][] cur4 = {{5,8,6},{6,6,2},{88,55,6}};
        int[][] actual4 = VVP_lab_2.variant(cur4);
        int[][] expected4 = {{5,6,8},{6,6,2},{88,55,6}};
        Arrays.equals(expected4, actual4);
        int[][] cur5 = {{12,99},{65,33}};
        int[][] actual5 = VVP_lab_2.variant(cur5);
        int[][] expected5 = {{12,99},{65,33}};
        Arrays.equals(expected5, actual5);
        int[][] cur6 = {{77,88,99},{1,2,3},{65,78,12}};
        int[][] actual6 = VVP_lab_2.variant(cur6);
        int[][] expected6 = {{77,88,99},{3,2,1},{78,65,12}};
        Arrays.equals(expected6, actual6);
        int[][] cur7 = {{11,33},{11,33}};
        int[][] actual7 = VVP_lab_2.variant(cur7);
        int[][] expected7 = {{11,33},{33,11}};
        Arrays.equals(expected7, actual7);
        int[][] cur9 = {{4,3,2,1},{45,78,12,45},{45,78,41,55},{22,45,14,78}};
        int[][] actual9 = VVP_lab_2.variant(cur9);
        int[][] expected9 = {{1,2,3,4},{78,45,45,12},{41,45,55,78},{14,22,45,78}};
        Arrays.equals(expected9, actual9);
        int[][] cur8 = {{56,96},{78,55}};
        int[][] actual8 = VVP_lab_2.variant(cur8);
        int[][] expected8 = {{56,95},{78,55}};
        Arrays.equals(expected8, actual8);
        int[][] cur10= {{63,48,72},{45,98,99},{1,2,3}};
        int[][] actual10= VVP_lab_2.variant(cur10);
        int[][] expected10= {{48,63,72},{99,98,45},{1,2,3}};
        Arrays.equals(expected10, actual10);
    }
}