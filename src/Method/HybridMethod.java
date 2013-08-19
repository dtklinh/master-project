/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import Components.MSA;
import Jama.Matrix;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author linh
 */
public class HybridMethod {
    private MSA MyMSA;

    /**
     * @return the MyMSA
     */
    public MSA getMyMSA() {
        return MyMSA;
    }

    /**
     * @param MyMSA the MyMSA to set
     */
    public void setMyMSA(MSA MyMSA) {
        this.MyMSA = MyMSA;
    }
    public HybridMethod(MSA m){
        this.MyMSA = m;
    }
    public Matrix CalculatePSSM(ArrayList<String> amino){
        ArrayList<String> ColumnStr = MyMSA.RetrieveColumnPair();
        int len = ColumnStr.get(0).length();
        double[][] mtr = new double[20][len];
        for(int i =0;i<len;i++){
            String tmp = ColumnStr.get(i);
            ArrayList<Character> lst = new ArrayList<Character>();
            // add to list
            for(int j=0;j<tmp.length();j++){
                lst.add(tmp.charAt(j));
            }
            int sum = 0;
            for(int j=0;j<amino.size();j++){
                int freq = Collections.frequency(lst, amino.get(i));
                sum += freq;
                mtr[j][i] = freq;
            }
            for(int j=0;j<20;j++){
                mtr[j][i] = mtr[i][j]/sum;
            }
        }
        return null;
    }
}
