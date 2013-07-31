/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author linh
 */
public class ARRF_Template {
    public static void WriteToArrfFile(String filename, String relation,ArrayList<double[]> arr_pos, ArrayList<double[]> arr_neg) throws IOException{
        FileWriter writer = new FileWriter(filename);
        String str = "@relation "+ relation + "\n";
        int len_vector = arr_pos.get(0).length;
        for(int i=0;i<len_vector;i++){
            str = str + "@attribute " + "attr_" + i + " real" + "\n";
        }
        str = str + "@attribute class {P, N} \n";
        str = str + "@data \n";
        for(int i=0;i<arr_pos.size();i++){
            double[] attr = arr_pos.get(i);
            for(int j=0;j<attr.length;j++){
                str = str + attr[j]+",";
            }
            str = str + "P" + "\n";
        }
        for(int i=0;i<arr_neg.size();i++){
            double[] attr = arr_neg.get(i);
            for(int j=0;j<attr.length;j++){
                str = str + attr[j]+",";
            }
            str = str + "N" + "\n";
        }
        writer.write(str);
        writer.close();
    }
}
