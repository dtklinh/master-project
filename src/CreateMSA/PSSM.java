/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreateMSA;

import Components.AminoAcid;
import Jama.Matrix;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author linh
 */
public class PSSM {

    private String name;
    private Matrix My_PSSM;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the My_PSSM
     */
    public Matrix getMy_PSSM() {
        return My_PSSM;
    }

    /**
     * @param My_PSSM the My_PSSM to set
     */
    public void setMy_PSSM(Matrix My_PSSM) {
        this.My_PSSM = My_PSSM;
    }
    ///

    public PSSM(String name, String dir) throws FileNotFoundException, IOException {
        String filename = dir + name + ".pssm";
        this.name = name;
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String tmp = "";
        ArrayList<double[]> arr = new ArrayList<double[]>();
        while (true) {
            tmp = br.readLine();
            if (tmp == null) {
                break;
            }
//            String[] lst_tmp = tmp.trim().split("\\s+");
//            if(IsNumeric(lst_tmp[0])){
//                double[] d = new double[20];
//                for(int i=2;i<22;i++){
//                    d[i-2] = Double.parseDouble(lst_tmp[i]);
//                }
//                arr.add(d);
//            }
            String[] lst_tmp = tmp.trim().split("\\s+");
            if (IsNumeric(lst_tmp[0])) {
                double[] d = new double[20];
                for (int i = 0; i < 20; i++) {
                    int start = 9 + i * 3;
                    int end = start + 3;
                    d[i] = Double.parseDouble(tmp.substring(start, end));
                }
                arr.add(d);
            }
        }
        br.close();
        in.close();
        fstream.close();
        double[][] mtr = new double[20][arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < 20; j++) {
                mtr[j][i] = arr.get(i)[j];
            }
        }
        this.My_PSSM = new Matrix(mtr);
    }

    public static boolean IsNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public Matrix GetPSSM_Logistic(){
        double[][] tmp = this.My_PSSM.getArrayCopy();
        for(int i=0; i<tmp.length;i++){
            for(int j=0; j<tmp[0].length;j++){
                tmp[i][j] = 1/(1+Math.exp(tmp[i][j]));
            }
        }
        return new Matrix(tmp);
    }
    public Matrix GetPSSM2(){
        double[][] mt = new double[400][this.My_PSSM.getColumnDimension()];
        double epsilon = 0.1;
        ArrayList<String> aa = AminoAcid.getAA();
        int num_aa = aa.size();
        AminoAcid.GetPairIndex();
        int len = this.My_PSSM.getColumnDimension();
        // count overall pair occurunce
        for(int i=0;i<len-1;i++){
            for(int j=i+1;j<len;i++){
                
            }
        }
        return null;
    }
}
