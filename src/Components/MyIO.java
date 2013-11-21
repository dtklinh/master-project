/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import Jama.Matrix;
import Method.UvalueThreshold;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author linh
 */
public class MyIO {

    public static void WritePoPToFile(String filename, double[][] val) throws IOException {
//        FileWriter writer = new FileWriter(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
    //    String tmp = "";
        for (int i = 0; i < val.length; i++) {
            for (int j = 0; j < val[0].length; j++) {
                //         tmp = tmp + val[i][j] + "\n";
                writer.write(val[i][j] + "\n");
            }
        }
        writer.flush();
        //    System.out.println("Loading finish: "+ filename);
        //    writer.write(tmp);
        writer.close();
    }

    public static void WritePoPToFile(String filename, int[][] val) throws IOException {
        //    FileWriter writer = new FileWriter(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";
        for (int i = 0; i < val.length; i++) {
            for (int j = 0; j < val[0].length; j++) {
                //         tmp = tmp + val[i][j] + "\n";
                if(val[i][j]<0){
                    System.err.println("Error: val: "+ i+ ":"+j);
                    System.exit(1);
                }
                writer.write(val[i][j] + "\n");
            }
        }
        writer.flush();
        //    System.out.println("Loading finish: "+ filename);
        //    writer.write(tmp);
        writer.close();
    }

    public static void WritePoPToFile(String filename, int[] val) throws IOException {
        //    FileWriter writer = new FileWriter(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";
        for (int i = 0; i < val.length; i++) {

            //         tmp = tmp + val[i][j] + "\n";
            writer.write(val[i] + "\n");

        }
        writer.flush();
        //    System.out.println("Loading finish: "+ filename);
        //    writer.write(tmp);
        writer.close();
    }
    public static void WritePoPToFile(String filename, double[] val) throws IOException {
        //    FileWriter writer = new FileWriter(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";
        for (int i = 0; i < val.length; i++) {

            //         tmp = tmp + val[i][j] + "\n";
            writer.write(val[i] + "\n");

        }
        writer.flush();
        //    System.out.println("Loading finish: "+ filename);
        //    writer.write(tmp);
        writer.close();
    }
    public static void WriteToFile(String filename, ArrayList<String> lst) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";
        for(String s:lst){
            writer.write(s+"\n");
        }
        writer.flush();
        writer.close();
    }
    public static void WriteToFile_Double(String filename, ArrayList<Double> lst) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";
        for(Double s:lst){
            writer.write(s+"\n");
        }
        writer.flush();
        writer.close();
    }

    public static ArrayList<KeyProtein> LoadKeyProteins(String filename) throws FileNotFoundException, IOException {
        ArrayList<KeyProtein> res = new ArrayList<KeyProtein>();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            if (line.equalsIgnoreCase("")) {
                continue;
            }
            line = line.trim();
            String name = line.substring(0, 4);
            String chain = line.substring(5, 6);
      //      String[] lst_tmp = line.trim().split("_");
      //      KeyProtein k = new KeyProtein(lst_tmp[0], lst_tmp[1]);
            KeyProtein k = new KeyProtein(name, chain);
            k.LoadingFromPDBFile();
            res.add(k);
        }
        br.close();
        return res;
    }
    public static Matrix ReadDSM(String filename) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        double[][] tmp = new double[400][400];
        int row = 0, col = 0;
        while(true){
            line = br.readLine();
            if(line==null){
                break;
            }
            if(line.equalsIgnoreCase("")){
                continue;
            }
            if(Character.isLetter(line.charAt(0))){
                line = line.substring(7);
            }
            double v = Double.parseDouble(line.trim());
            tmp[row][col] = v;
            col++;
            if(col>=400){
                col = 0;
                row++;
            }
        }
        return new Matrix(tmp);
    }
    public static ArrayList<String> ReadMSA(String filename) throws FileNotFoundException, IOException{
        ArrayList<String> res = new ArrayList<String>();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String tmp = "";
        String container = "";
        while (true) {
            tmp = br.readLine();
            if (tmp == null) {
                break;
            }
            if (tmp.isEmpty()) {
                continue;
            }
            if (tmp.indexOf(">") >= 0) {
                if (!container.isEmpty()) {
                    res.add(container);
                }
                container = "";
            } else {
                container = container + tmp.trim();
            }
        }
        br.close();
        return res;
    }
    public static ArrayList<String> ReadLines(String filename) throws FileNotFoundException, IOException{
        ArrayList<String> res = new ArrayList<String>();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String tmp = "";
        while(true){
            tmp = br.readLine();
            if(tmp==null){
                break;
            }
            tmp = tmp.trim();
            if(!tmp.isEmpty()){
                res.add(tmp);
            }
        }
        return res;
    }
    public static ArrayList<UvalueThreshold> LoadUvalueThres(String filename) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        ArrayList<UvalueThreshold> res = new ArrayList<UvalueThreshold>();
        while(true){
            line = br.readLine();
            if(line==null){
                break;
            }
            
            else{
                line = line.trim();
                if(!line.isEmpty()){
                    String[] lst_tmp = line.split("\t");
                    String name = lst_tmp[0];
                    double[] d = new double[lst_tmp.length-1];
                    for(int i=0;i<lst_tmp.length-1;i++){
                        d[i] = Double.parseDouble(lst_tmp[i+1]);
                    }
                    UvalueThreshold u = new UvalueThreshold(name, d);
                    res.add(u);
                }
            }
        }
        return res;
    }
    public static void WriteAlignedRowFile(String filename, String[][] str) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        for(int i=0;i<str.length;i++){
            for(int j=0;j<str[0].length;j++){
                writer.write(String.format("%-30s", str[i][j]));
            }
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }
    public static void WriteAlignedColumnFile(String filename, ArrayList<String[]> str) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        for(int i=0;i<str.get(0).length;i++){
            for(int j=0;j<str.size();j++){
                writer.write(String.format("%-25s", str.get(j)[i]));
            }
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }
}
