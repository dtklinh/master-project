/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import Jama.Matrix;
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
}
