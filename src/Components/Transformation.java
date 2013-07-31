/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author linh
 */
public class Transformation {

    public static double[] CTD(ArrayList<Character> lst_char) { // Composition Transition Distribution (for string of 
        //two letters A & B)
        double[] res = new double[13];

        int len = lst_char.size();
        // Composition
        int num_A = Collections.frequency(lst_char, 'A');
        int num_B = Collections.frequency(lst_char, 'B');
        res[0] = (double) num_A / len;
        res[1] = (double) num_B / len;
        // Transition
        int count = 0;
        for (int i = 0; i < len - 1; i++) {
            if (lst_char.get(i).equals('A') && lst_char.get(i + 1).equals('B')) {
                count++;
            } else if (lst_char.get(i).equals('B') && lst_char.get(i + 1).equals('A')) {
                count++;
            }
        }
        res[2] = (double) count / (len - 1);
        // Distribution
        for (int i = 0; i < 5; i++) {
            int index_A = (int) Math.ceil((double) (i * 25) * num_A / 100);
            System.out.println("index_A: " + index_A);
            int demsoluongA = 0;
            int demchieudai = 0;
            while (index_A != demsoluongA) {
                if (lst_char.get(demchieudai).equals('A')) {
                    demsoluongA++;
                }
                demchieudai++;

            }
            res[3 + i] = (double) demchieudai / len;
        }
        for (int i = 0; i < 5; i++) {
            int index_B = (int) Math.ceil((double) (i * 25) * num_B / 100);
            if (index_B == 0) {
                index_B = 1;
            }
            int demsoluongB = 0;
            int demchieudai = 0;
            while (index_B != demsoluongB) {
                if (lst_char.get(demchieudai).equals('B')) {
                    demsoluongB++;
                }
                demchieudai++;

            }
            res[8 + i] = (double) demchieudai / len;
        }
        return res;
    }

    public static double[] CTD3(ArrayList<Character> lst_char) {
        //// CTD for 3 letters A, B, C
        double[] res = new double[21];
        // composition
        int len = lst_char.size();
        int num_A = Collections.frequency(lst_char, 'A');
        int num_B = Collections.frequency(lst_char, 'B');
        int num_C = Collections.frequency(lst_char, 'C');
        int[] num_ABC = new int[]{num_A, num_B, num_C};
        
        res[0] = (double) num_A / len;
        res[1] = (double) num_B / len;
        res[2] = (double) num_C / len;
        /// transition
        int trans_AB = 0, trans_AC = 0, trans_BC = 0;
        for (int i = 0; i < len - 1; i++) {
            if ((lst_char.get(i).equals('A') && lst_char.get(i + 1).equals('B'))
                    || (lst_char.get(i).equals('B') && lst_char.get(i + 1).equals('A'))) {
                trans_AB++;
            } else if ((lst_char.get(i).equals('A') && lst_char.get(i + 1).equals('C'))
                    || (lst_char.get(i).equals('C') && lst_char.get(i + 1).equals('A'))) {
                trans_AC++;
            } else if ((lst_char.get(i).equals('B') && lst_char.get(i + 1).equals('C'))
                    || (lst_char.get(i).equals('C') && lst_char.get(i + 1).equals('B'))) {
                trans_BC++;
            }
        }
        res[3] = trans_AB/(len-1);
        res[4] = trans_AC/(len-1);
        res[5] = trans_BC/(len-1);
        // distribution
        char[] tmp_char = new char[3];
        tmp_char[0] = 'A';
        tmp_char[1] = 'B';
        tmp_char[2] = 'C';
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 5; i++) {
                int index_B = (int) Math.ceil((double) (i * 25) * num_ABC[k] / 100);
                if (index_B == 0) {
                    index_B = 1;
                }
                int demsoluongB = 0;
                int demchieudai = 0;
                while (index_B != demsoluongB) {
                    if (lst_char.get(demchieudai).equals(tmp_char[k])) {
                        demsoluongB++;
                    }
                    demchieudai++;

                }
                res[6+k*5 + i] = (double) demchieudai / len;
            }
        }
        return res;
    }
//    public static ArrayList<Character> FromEntropyToSequence(double[] arr, double thres){
//        ArrayList<Character> res = new ArrayList<Character>();
//        
//        return res;
//    }
}
