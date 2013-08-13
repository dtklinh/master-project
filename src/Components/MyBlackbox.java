/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import Jama.Matrix;
import Method.PairOfPair;
import Support.MyMatrix;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
//import org.apache.pdfbox.util.Matrix;

/**
 *
 * @author linh
 */
public class MyBlackbox {

    public static ArrayList<double[]> FromFilenameToFeature(String filename, double thres, String pos_neg) throws FileNotFoundException, IOException {
        ArrayList<double[]> res = new ArrayList<double[]>();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String tmp = "";
        while (true) {
            tmp = br.readLine();
            if (tmp == null) {
                break;
            }
            if (tmp.isEmpty() || tmp == "") {
                continue;
            }
            ArrayList<String> msa = MsaFilterer.filter("MSA_file\\" + pos_neg + "\\" + tmp);
            if (msa.size() < 10) {
                continue;
            }
            MSA m = new MSA(msa);
            double[] entropy = m.CalculateEntropy();
            ArrayList<Character> ABC_list = Extraction.EntropyChange3(entropy, thres);
            double[] feature = Transformation.CTD3(ABC_list);
            res.add(feature);
        }
        return res;
    }

    public static void CalculateSig_NullMatrix(String filename) throws FileNotFoundException, IOException {
        ArrayList<KeyProtein> lst_prot = MyIO.LoadKeyProteins(filename);
//        String Signal_filename = "SignalMatrix.txt";
//        String Null_filename = "NullMatrix.txt";
//        MyMatrix SignalMat = new MyMatrix(400, 400);
//        MyMatrix NullMat = new MyMatrix(400, 400);
        int distance = 2;
        HashMap<String, Integer> PairIndex = AminoAcid.GetPairIndex();
        for (KeyProtein k : lst_prot) {
            if (k.getSequence().equalsIgnoreCase("")) {
                k.LoadingFromPDBFile();
            }
            String fasta = k.getName() + "_" + k.getChain() + ".fasta.msa";
            ArrayList<String> msa = MsaFilterer.filter("MSA_file\\Collection\\" + fasta);
            if (msa.size() < 10) {
                System.out.println("Skip protein: " + k.getName() + "_" + k.getChain());
            }
            MSA m = new MSA(k, msa);
            m.AdjustLength();
            ArrayList<int[]> signal_indicator = k.RetrieveIndicatorPair(distance);
            ArrayList<int[]> null_indicator = k.RetrieveNullIndex(distance);
            System.out.println("Finish calculating indicator and null pair index");
            PairOfPair PoP_signal = new PairOfPair(m, signal_indicator);
            PairOfPair PoP_null = new PairOfPair(m, null_indicator);
            ArrayList<String> ColumnPair = PoP_signal.RetrieveColumnPair();
            System.out.println("Finish retrieved Column pair");
            MyMatrix tmp = PoP_signal.CalculatePoP(ColumnPair, PairIndex);
            MyIO.WritePoPToFile("SignalMatrix\\" + k.getName() + "_" + k.getChain() + ".txt", tmp.getElement());
            //SignalMat = SignalMat.AddMatrix(tmp);
            System.out.println("Signal matrix was calculated: " + k.getName());
            tmp = PoP_null.CalculatePoP(ColumnPair, PairIndex);
            MyIO.WritePoPToFile("NullMatrix\\" + k.getName() + "_" + k.getChain() + ".txt", tmp.getElement());
            //NullMat = NullMat.AddMatrix(tmp);
            System.out.println("Null matrix was calculated: " + k.getName());
        }
        //MyIO.WritePoPToFile(Signal_filename, SignalMat.getElement());
        //MyIO.WritePoPToFile(Null_filename, NullMat.getElement());
    }

    public static void TestSync(String filename) {
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String tmp = "";
            while (true) {
                tmp = br.readLine();
                if (tmp == null) {
                    break;
                }
                if (tmp.equalsIgnoreCase("")) {
                    continue;
                }
                try {

                    String name = "MSA_file\\Collection\\" + tmp.trim() + ".fasta.msa";
                    FileInputStream fstream2 = new FileInputStream(name);
                    DataInputStream in2 = new DataInputStream(fstream2);
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
                    br2.readLine();
                    br2.close();

                } catch (IOException err) {
                    System.err.println("Err: " + err.toString());
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Err: " + e.toString());
        }
    }

    public static void CalculateSum(String filename, String signal) {
        try {
            int[] vec = new int[160000];
            for (int i = 0; i < 160000; i++) {
                vec[i] = 0;
            }
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                if (line.trim().equalsIgnoreCase("")) {
                    continue;
                }
                line = line.trim();
                // read file
                FileInputStream fstream2 = new FileInputStream(signal + "\\" + line + ".txt");
                DataInputStream in2 = new DataInputStream(fstream2);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
                String str = "";
                int count = 0;
                while (true) {
                    str = br2.readLine();
                    if (str == null) {
                        break;
                    }
                    int tmp = Integer.parseInt(str.trim());
                    if(tmp<0){
                        System.err.println(line);
                    }
                    vec[count] = vec[count] + tmp;
                    count++;
                }
            }
            MyIO.WritePoPToFile(signal + "\\Sum.txt", vec);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public static void CalculateDSM(String filename) {
        try {
            String signalfile = "SignalMatrix\\Sum.txt";
            String nullfie = "NullMatrix\\Sum.txt";
            DSM d = new DSM();
            d.LoadFromFile(signalfile, nullfie, 400);
            MyIO.WritePoPToFile("AAAA.txt", d.getSignalMat().getArrayCopy());
            System.out.println("Finish loading file");
            Matrix m = d.CreateDSM();
            System.out.println("Finish calculating dsm \n Now writting to file");
            MyIO.WritePoPToFile(filename, m.getArrayCopy());
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
