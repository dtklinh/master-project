/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author linh
 */
public class AminoAcid {
    private  static ArrayList<String> AA_Abbr = new ArrayList<String>(){{
    add("Ala"); //1
    add("Arg"); //2
    add("Asn"); //3
    add("Asp"); //4
    add("Cys"); //5
    add("Glu"); //6
    add("Gln"); //7
    add("Gly"); //8
    add("His"); //9
    add("Ile"); //10
    add("Leu"); //11
    add("Lys"); //12
    add("Met"); //13
    add("Phe"); //14
    add("Pro"); //15
    add("Ser"); //16
    add("Thr"); //17
    add("Trp"); //18
    add("Tyr"); //19
    add("Val"); //20
    }};
    private static ArrayList<String> AA_Fullname = new ArrayList<String>(){{
     add("Alanine"); //1   
     add("Arginine"); //2
     add("Asparagine"); //3
     add("Aspartic"); //4
     add("Cysteine"); //5
     add("Glutamic"); //6
     add("Glutamine"); //7
     add("Glycine"); //8
     add("Histidine"); //9
     add("Isoleucine"); //10
     add("Leucine"); //11
     add("Lysine"); //12
     add("Methionine"); //13
     add("Phenylalanine"); //14
     add("Proline"); //15
     add("Serine"); //16
     add("Threonine"); //17
     add("Tryptophan"); //18
     add("Tyrosine"); //19
     add("Valine"); //20
    }};
    private static ArrayList<String> AA= new ArrayList<String>(){{
     add("A"); //1
     add("R"); //2
     add("N"); //3
     add("D"); //4
     add("C"); //5
     add("E"); //6
     add("Q"); //7
     add("G"); //8
     add("H"); //9
     add("I"); //10
     add("L"); //11
     add("K"); //12
     add("M"); //13
     add("F"); //14
     add("P"); //15
     add("S"); //16
     add("T"); //17
     add("W"); //18
     add("Y"); //19
     add("V"); //20
    }};

    /**
     * @return the AA_Abbr
     */
    public static ArrayList<String> getAA_Abbr() {
        return AA_Abbr;
    }

    /**
     * @param AA_Abbr the AA_Abbr to set
     */
    public void setAA_Abbr(ArrayList<String> AA_Abbr) {
        this.AA_Abbr = AA_Abbr;
    }

    /**
     * @return the AA_Fullname
     */
    public static ArrayList<String> getAA_Fullname() {
        return AA_Fullname;
    }

    /**
     * @param AA_Fullname the AA_Fullname to set
     */
    public void setAA_Fullname(ArrayList<String> AA_Fullname) {
        this.AA_Fullname = AA_Fullname;
    }
    //

    /**
     * @return the AA
     */
    public static ArrayList<String> getAA() {
        return AA;
    }

    /**
     * @param AA the AA to set
     */
    public void setAA(ArrayList<String> AA) {
        this.AA = AA;
    }
    ///
    public int MatchPattern(String str, String chain){
        boolean res = false;
        for(int i=0; i< 20;i++){
            String pattern = ".*"+this.AA_Abbr.get(i)+"\\d+[(]"+chain+"[)].*";
            res = str.matches(pattern);
            if(res == true){
                String num = str.substring(str.indexOf(this.AA_Abbr.get(i))+3, str.indexOf("(", str.indexOf(this.AA_Abbr.get(i))));
                return Integer.parseInt(num);
            }
        }
        return -1;
    }
    public static HashMap<String, Integer> GetPairIndex(){
        HashMap<String, Integer> res = new HashMap<String, Integer>(400);
        int count = 0;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                res.put(AA.get(i)+AA.get(j), count);
                count++;
            }
        }
        return res;
    }
    public static HashMap<Integer, String> GetIndexPair(){
        HashMap<Integer, String> res = new HashMap<Integer, String>(400);
        int count = 0;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                res.put(count, AA.get(i)+AA.get(j));
                count++;
            }
        }
        return res;
    }
    
}
