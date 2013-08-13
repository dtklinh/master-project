/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import Components.AminoAcid;
import Support.MyMatrix;
//import components.ColumnPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author linh
 */
public class MyPair {

    private String[] Sequences = new String[2];

    /**
     * @return the Sequences
     */
    public String[] getSequences() {
        return Sequences;
    }

    /**
     * @param Sequences the Sequences to set
     */
    public void setSequences(String[] Sequences) {
        this.Sequences = Sequences;
    }

    public MyPair() {
        this.Sequences = new String[2];
    }

    public MyPair(String[] str) {
        this.Sequences = str;
    }

    public MyPair(String s1, String s2) {
        String[] tmp = new String[2];
        tmp[0] = s1;
        tmp[1] = s2;
        this.Sequences = tmp;
    }

    public void EliminateGap() {
        if (this.Sequences[0] == null || this.Sequences[1] == null) {
            System.err.println("String is null");
        }

    }

    public int[][] CalculatePoP2(HashMap<String, Integer> PairIndex) {
        // hopefully faster
        //HashMap<String, Integer> a = AminoAcid.GetPairIndex();
        int[][] res = new int[400][400];
        int[] tmp = new int[400];
        for (int i = 0; i < 400; i++) {
            tmp[i] = 0;
        }
        String s1 = this.Sequences[0];
        String s2 = this.Sequences[1];
        int len = s1.length();
        for (int i = 0; i < len; i++) {
            String str = String.valueOf(s1.charAt(i)) + String.valueOf(s2.charAt(i));
            //int index = ConvertFromStringToNum(str);
            if(!PairIndex.containsKey(str)){
                continue;
            }
            int index = PairIndex.get(str);
//            int index = PairIndex.
            if (index >= 0) {
                tmp[index]++;
            }
        }
        for (int i = 0; i < 399; i++) {
            if (tmp[i] != 0) {
                int idx1 = i;
                String str1 = ConvertFromNumToString2(idx1);
                
                String c1 = String.valueOf(str1.charAt(0));
                String c2 = String.valueOf(str1.charAt(1));
                //int id1 = ConvertFromStringToNum(c1 + c2);
                
                int id1 = PairIndex.get(c1+c2);
                //int id2 = ConvertFromStringToNum(c2 + c1);
                int id2 = PairIndex.get(c2 + c1);
                for (int j = i; j < 400; j++) {
                    if (j == i) {
                        if (tmp[i] == 1) {
                            continue;
                        }
                    }
                    if (tmp[j] != 0) {
                        String str2 = ConvertFromNumToString2(j);
                        String c3 = String.valueOf(str2.charAt(0));
                        String c4 = String.valueOf(str2.charAt(1));
                        //int id3 = ConvertFromStringToNum(c3 + c4);
                        int id3 = PairIndex.get(c3+c4);
                        //int id4 = ConvertFromStringToNum(c4 + c3);
                        int id4 = PairIndex.get(c4+c3);
                        int kq = tmp[i] * tmp[j];
                        if(kq <0){
                            System.err.println("Negative freq: "+tmp[i] + " "+ tmp[j]);
                        }
                        res[id1][id3] = kq;
                        res[id3][id1] = kq;
                        res[id2][id4] = kq;
                        res[id4][id2] = kq;
                    }
                }
            }
        }
        return res;
    }
    public int[][] CalculatePoP3(HashMap<String, Integer> pairIndex) {
//        HashMap<String, Integer> pairIndex = alphabet.Standard.getPairIndex();
//        HashMap<String, Integer> pairIndex = AminoAcid.GetPairIndex();
        String pairOne = "";
        String pairOneReverse = "";
        String pairTwo = "";
        String pairTwoReverse = "";
        String s1 = this.Sequences[0];
        String s2 = this.Sequences[1];
        int[][] matrix = new int[400][400];
        for (int i = 0; i < s1.length(); ++i) {
            pairOne = "" + s1.charAt(i) + s2.charAt(i);
            if (pairIndex.containsKey(pairOne)) {
                for (int j = i + 1; j <  s1.length(); ++j) {
                    pairTwo = "" + s1.charAt(j) + s2.charAt(j);
                    if (pairIndex.containsKey(pairTwo)) {
                        pairOneReverse = "" + s2.charAt(i) + s1.charAt(i);
                        pairTwoReverse = "" + s2.charAt(j) + s1.charAt(j);
                        /*This part does the actual counting*/
                        matrix[pairIndex.get(pairOne)][pairIndex.get(pairTwo)] = ( matrix[pairIndex.get(pairOne)][pairIndex.get(pairTwo)] + 1 );
                        matrix[pairIndex.get(pairTwo)][pairIndex.get(pairOne)] = ( matrix[pairIndex.get(pairTwo)][pairIndex.get(pairOne)] + 1 );
                        matrix[pairIndex.get(pairOneReverse)][pairIndex.get(pairTwoReverse)] = ( matrix[pairIndex.get(pairOneReverse)][pairIndex.get(pairTwoReverse)] + 1 );
                        matrix[pairIndex.get(pairTwoReverse)][pairIndex.get(pairOneReverse)] = ( matrix[pairIndex.get(pairTwoReverse)][pairIndex.get(pairOneReverse)] + 1 );
                    }
                }
            }
        }
        return matrix;
    }
    
    public static double[][] getPairIntervalMatrix(double[][] pairProb) {
        double[][] matrix = new double[pairProb.length][pairProb.length];
        for (int i = 0; i < pairProb.length; ++i) {
           double[] p = new double[pairProb.length];
           for (int j = 0; j < p.length; ++j) {
               if (j > 0) {
                   p[j] = p[j-1] + pairProb[i][j];
               }
               else {
                   p[j] = pairProb[i][j];
               }
           }
           matrix[i] = p;
        }
        return matrix;
    }

    public int[][] CalculatePoP() {
        try {
            int[][] res = new int[400][400];
            String s1 = this.Sequences[0];
            String s2 = this.Sequences[1];
            int len = s1.length();
            for (int i = 0; i < len - 1; i++) {
                if (String.valueOf(s1.charAt(i)).equalsIgnoreCase("-")
                        || String.valueOf(s2.charAt(i)).equalsIgnoreCase("-")) {
                    continue;
                }
                String tmp_1 = String.valueOf(s1.charAt(i));
                String tmp_2 = String.valueOf(s2.charAt(i));
                for (int j = i + 1; j < len; j++) {
                    if (String.valueOf(s1.charAt(j)).equalsIgnoreCase("-")
                            || String.valueOf(s2.charAt(j)).equalsIgnoreCase("-")) {
                        continue;
                    }
                    String tmp_3 = String.valueOf(s1.charAt(j));
                    String tmp_4 = String.valueOf(s2.charAt(j));
                    String str1 = tmp_1 + tmp_2;
                    String str2 = tmp_2 + tmp_1;
                    String str3 = tmp_3 + tmp_4;
                    String str4 = tmp_4 + tmp_3;

                    int idx1 = ConvertFromStringToNum(str1);
                    int idx2 = ConvertFromStringToNum(str2);
                    int idx3 = ConvertFromStringToNum(str3);
                    int idx4 = ConvertFromStringToNum(str4);
                    res[idx1][idx3]++;
                    res[idx2][idx4]++;
                    res[idx3][idx1]++;
                    res[idx4][idx2]++;
//                    ArrayList<Integer> lst_int = new ArrayList<Integer>();
//                    lst_int.add(idx1);
//                    lst_int.add(idx2);
//                    lst_int.add(idx3);
//                    lst_int.add(idx4);
//                    HashSet<Integer> h = new HashSet<Integer>();
//                    h.addAll(lst_int);
//                    ArrayList<Integer> lst_idx = new ArrayList<Integer>();
//                    lst_idx.addAll(h);
//                    for (Integer index : lst_idx) {
//                        int index1 = index / 400;
//                        int index2 = index % 400;
//                        res[index1][index2]++;
//                        System.out.println("index1: index2 --> " + index1 + " : " + index2);
//                    }
                }
            }
            return res;
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public static String ConvertFromNumToString(int idx) {
        String res = "";
        if (idx < 0 || idx >= 160000) {
            return "--";
        }
//        char[] apb = alphabet.Complete.getAlphabet();
//        ArrayList<Character> alph = new ArrayList<Character>();
//        for (int i = 0; i < 20; i++) {
//            alph.add(apb[i]);
//        }
        ArrayList<String> alph = AminoAcid.getAA();
        for (int i = 0; i < 4; i++) {
            int r = idx % 20;
            res = alph.get(r).toString() + res;
            idx = (int) (idx / 20);
        }
        return res;
    }

    public static String ConvertFromNumToString2(int idx) {
        String res = "";
        if (idx < 0 || idx >= 400) {
            System.err.println("Out of Scope: " + idx);
            return "--";
        } else {
            ArrayList<String> alph = AminoAcid.getAA();
            for (int i = 0; i < 2; i++) {
                int r = idx % 20;
                res = alph.get(r).toString() + res;
                idx = (int) (idx / 20);
            }
            return res;
        }
    }

    public static int ConvertFromStringToNum(String str) {
        str = str.trim();
        if (str.indexOf("-") >= 0) {
            return -1;
        }
        int idx = 0;
//        char[] apb = alphabet.Complete.getAlphabet();
//        ArrayList<Character> alph = new ArrayList<Character>();
//        for (int i = 0; i < 20; i++) {
//            alph.add(apb[i]);
//        }
        ArrayList<String> alph = AminoAcid.getAA();
        int len = str.length();
        for (int i = len - 1; i >= 0; i--) {
            int pw = (int) Math.pow(20, i);
            int index = alph.indexOf(String.valueOf(str.charAt(len - 1 - i)));
            idx += index * pw;
        }
        return idx;
    }

    public MyMatrix CalculatePair() {
        try {
            MyMatrix res = new MyMatrix(20, 20);
            int[][] mat = new int[20][20];
            ArrayList<String> lst_amino = AminoAcid.getAA();
            int len = this.Sequences[0].length();
            String str1 = Sequences[0];
            String str2 = Sequences[1];
            for (int i = 0; i < len; i++) {
                String c1 = String.valueOf(str1.charAt(i));
                String c2 = String.valueOf(str2.charAt(i));
//                if(c1.equalsIgnoreCase("-") || c2.equalsIgnoreCase("-")
//                        || c1.equalsIgnoreCase("X") || c2.equalsIgnoreCase("X")){
//                    continue;
//                }
                int idx1 = lst_amino.indexOf(c1);
                int idx2 = lst_amino.indexOf(c2);
                if (idx1 == -1 || idx2 == -1) {
//                    System.out.println("c1: "+c1);
//                    System.out.println("c2: "+c2);
                    continue;
                }
                mat[idx1][idx2]++;
            }
            for (int i = 0; i < 20; i++) {
                for (int j = i; j < 20; j++) {
                    if (i == j) {
                        mat[i][j] *= 2;
                    } else {
                        int tmp = mat[i][j] + mat[j][i];
                        mat[i][j] = tmp;
                        mat[j][i] = tmp;
                    }
                }
            }
            res.setElement(mat);
            return res;
        } catch (Error e) {
            System.err.println(e.toString());
            return null;
        }
    }
}
