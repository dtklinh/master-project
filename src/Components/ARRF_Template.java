/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author linh
 */
public class ARRF_Template {

    public static void WriteToArrfFile(String filename, String relation, ArrayList<double[]> arr_pos, ArrayList<double[]> arr_neg) throws IOException {
        //      FileWriter writer = new FileWriter(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String str = "";
        writer.write("@relation " + relation + "\n");
        int len_vector = arr_pos.get(0).length;
        for (int i = 0; i < len_vector; i++) {
            writer.write("@attribute " + "attr_" + i + " real" + "\n");
        }
        writer.write("@attribute class {P, N} \n");
        writer.write("@data \n");
        for (int i = 0; i < arr_pos.size(); i++) {
            double[] attr = arr_pos.get(i);
            for (int j = 0; j < attr.length; j++) {
                str = str + attr[j] + ",";
            }
            str = str + "P" + "\n";
            writer.write(str);
            str = "";
        }
        str = "";
        for (int i = 0; i < arr_neg.size(); i++) {
            double[] attr = arr_neg.get(i);
            for (int j = 0; j < attr.length; j++) {
                str = str + attr[j] + ",";
            }
            str = str + "N" + "\n";
            writer.write(str);
            str = "";
        }
   //     writer.write(str);
        writer.flush();
        writer.close();
    }
    public static void WriteToArffFile(Instances ins, String filename) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String str = "";
        writer.write("@relation "+ ins.relationName()+"\n");
        for(int i=0;i<ins.numAttributes();i++){
            writer.write(ins.attribute(i).toString()+ "\n");
        }
        writer.write("@data" + "\n");
        for(int i=0;i<ins.numInstances();i++){
            Instance in = ins.instance(i);
            writer.write(in.toString()+ "\n");
        }
        writer.flush();
        writer.close();
    }
}
