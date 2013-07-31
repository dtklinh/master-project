/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import Components.AminoAcid;
import Components.MSA;
import Support.MyMatrix;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author linh
 */
public class PairOfPair {
    private MSA MyMSA;
    private ArrayList<int[]> Indicator;
    

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
    //
    public PairOfPair(MSA m){
        this.MyMSA = m;
        this.Indicator = new ArrayList<int[]>();
    }
    public PairOfPair(MSA m, ArrayList<int[]> idx){
        this.MyMSA = m;
        this.Indicator = idx;
    }
    
    public void AddIndicator(int[] i){
        for(int[] v: this.Indicator){
            if(v[0] == i[0] && v[1]== i[1]){
                return;
            }
            else if(v[0] == i[1] && v[1] == i[0]){
                return;
            }
        }
        this.Indicator.add(i);
    }
    public ArrayList<String> RetrieveColumnPair(){
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<String> lst_seq = this.MyMSA.getLstSeqs();
        int min = 10000;
        for(String s: lst_seq){
            s = s.trim();
            if(s.length()<min){
                min = s.length();
            }
        }
        for(int i=0;i<min;i++){
            String tmp = "";
            for(String s:lst_seq){
                tmp = tmp + String.valueOf(s.charAt(i));
            }
            res.add(tmp);
        }
        
        return res;
    }
    public MyMatrix CalculatePoP(ArrayList<String> ColumnString, HashMap<String, Integer> PairIndex){
    //    int[][] res = new int[400][400];
    //    HashMap<String, Integer> PairIndex = AminoAcid.GetPairIndex();
        MyMatrix m = new MyMatrix(400, 400);
        for(int[] idx: this.Indicator){
            MyPair p = new MyPair(ColumnString.get(idx[0]), ColumnString.get(idx[1]));
            int[][] tmp = p.CalculatePoP2(PairIndex);
            m = m.AddMatrix(new MyMatrix(tmp));
        }
        //res = m.Normalize();
        return m;
    }
    public MyMatrix CalculatePair(ArrayList<String> ColumnString){
        MyMatrix m = new MyMatrix(20, 20);
        for(int[] idx: this.Indicator){
            MyPair p = new MyPair(ColumnString.get(idx[0]), ColumnString.get(idx[1]));
            m = m.AddMatrix(p.CalculatePair());
        }
        return m;
    }

    /**
     * @return the Indicator
     */
    public ArrayList<int[]> getIndicator() {
        return Indicator;
    }

    /**
     * @param Indicator the Indicator to set
     */
    public void setIndicator(ArrayList<int[]> Indicator) {
        this.Indicator = Indicator;
    }
}
