/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import Jama.Matrix;
import Method.MyRandomForest;
import Method.PairOfPair;
import Support.Dsm;
import Support.MyMatrix;
import Support.ParseBlosumMatrix;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileParser;
import org.biojava.bio.structure.io.PDBFileReader;
import weka.core.Instances;
//import org.jfree.io.FileUtilities;
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
        String Signal_filename = "SumSignalMatrix.txt";
        String Null_filename = "SumNullMatrix.txt";
        String Null_filename2 = "SumNullMatrix2.txt";
        Matrix SignalMat = new Matrix(new double[400][400]);
        Matrix NullMat = new Matrix(new double[400][400]);
        Matrix NullMat2 = new Matrix(new double[400][400]);
        int distance = 2;
        HashMap<String, Integer> PairIndex = AminoAcid.GetPairIndex();
        for (KeyProtein k : lst_prot) {
            if (k.getSequence().equalsIgnoreCase("")) {
                k.LoadingFromPDBFile();
            }
            String fasta = k.getName() + "_" + k.getChain() + ".fasta.msa";
            ArrayList<String> msa = MsaFilterer.filter("MSA_file/Collection/" + fasta);
//            if (msa.size() < 10) {
//                System.out.println("Skip protein: " + k.getName() + "_" + k.getChain());
//            }
            if(msa.size()>=100){
                System.out.println(k.getName()+" : "+ msa.size());
            }
            /*
            MSA m = new MSA(k, msa);
            m.AdjustLength();
            ArrayList<int[]> signal_indicator = k.RetrieveIndicatorPair(distance);
            ArrayList<int[]> null_indicator = k.RetrieveNullIndex(distance, false);
            ArrayList<int[]> null_indicator2 = k.RetrieveNullIndex(distance, true);
            Collections.shuffle(null_indicator);
            Collections.shuffle(null_indicator);
            Collections.shuffle(null_indicator2);
            if (signal_indicator.size() < null_indicator.size()) {
                for (int i = null_indicator.size() - 1; i >= signal_indicator.size(); i--) {
                    null_indicator.remove(i);
                }
            }
            if (signal_indicator.size() < null_indicator2.size()) {
                for (int i = null_indicator2.size() - 1; i >= signal_indicator.size(); i--) {
                    null_indicator2.remove(i);
                }
            }
            System.out.println("Finish calculating indicator and null pair index");
            PairOfPair PoP_signal = new PairOfPair(m, signal_indicator);
            PairOfPair PoP_null = new PairOfPair(m, null_indicator);
            PairOfPair PoP_null2 = new PairOfPair(m, null_indicator2);
            ArrayList<String> ColumnPair = PoP_signal.RetrieveColumnPair();
            System.out.println("Finish retrieved Column pair");
            //Matrix tmp = PoP_signal.CalculatePoP(ColumnPair, PairIndex);
            //MyIO.WritePoPToFile("SignalMatrix/" + k.getName() + "_" + k.getChain() + ".txt", tmp.getArray());
            //SignalMat = SignalMat.plus(tmp);
            //System.out.println("Signal matrix was calculated: " + k.getName());
            Matrix tmp = PoP_null.CalculatePoP(ColumnPair, PairIndex);
            MyIO.WritePoPToFile("NullMatrix/" + k.getName() + "_" + k.getChain() + ".txt", tmp.getArray());
            NullMat = NullMat.plus(tmp);
            System.out.println("Null matrix was calculated: " + k.getName());
            //
            tmp = PoP_null2.CalculatePoP(ColumnPair, PairIndex);
            MyIO.WritePoPToFile("NullMatrix2/" + k.getName() + "_" + k.getChain() + ".txt", tmp.getArray());
            NullMat2 = NullMat2.plus(tmp);
            System.out.println("Null matrix 2 was calculated: " + k.getName());
            */
        }
        //    MyIO.WritePoPToFile(Signal_filename, SignalMat.getArray());
//        MyIO.WritePoPToFile("NullMatrix/" + Null_filename, NullMat.getArray());
//        MyIO.WritePoPToFile("NullMatrix2/" + Null_filename2, NullMat2.getArray());
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

          //          String name = "MSA_file\\Collection\\" + tmp.trim() + ".fasta.msa";
                    String name = "pdb_file/pdb" + tmp.trim().substring(0,4).toLowerCase()+".ent";
                    String name2 = "pdb_file/" + tmp.trim().substring(0,4).toUpperCase()+".pdb";
                    FileInputStream fstream2;
                    try{
                    fstream2 = new FileInputStream(name);
                    } catch(FileNotFoundException e){
                        fstream2 = new FileInputStream(name2);
                    }
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
            double[] vec = new double[160000];
            for (int i = 0; i < 160000; i++) {
                vec[i] = 0.0;
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
                FileInputStream fstream2 = new FileInputStream(signal + "/" + line);
                DataInputStream in2 = new DataInputStream(fstream2);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
                String str = "";
                int count = 0;
                while (true) {
                    str = br2.readLine();
                    if (str == null) {
                        break;
                    }
                    double tmp = Double.parseDouble(str.trim());
                    if (tmp < 0) {
                        System.err.println(line);
                    }
                    vec[count] = vec[count] + tmp;
                    count++;
                }
            }
            MyIO.WritePoPToFile(signal + "/Sum.txt", vec);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public static void CalculateDSM(String filename) {
        try {
            String signalfile = "SignalMatrix/Sum.txt";
            String nullfie = "NullMatrix/Sum.txt";
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

    public static void PrepareDataSet(String filename) {

        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            ArrayList<String> lst = new ArrayList<String>();
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                if (line.equalsIgnoreCase("")) {
                    continue;
                }
                lst.add(line.trim());
            }
            br.close();
            Collections.shuffle(lst);
            Collections.shuffle(lst);
            Collections.shuffle(lst);
            // copy train
            for (int i = 0; i < 300; i++) {
                File src = new File("SignalMatrix/" + lst.get(i) + ".txt");
                File des = new File("Train/SignalMatrix/" + lst.get(i) + ".txt");
                FileUtils.copyFile(src, des);
                //            Thread.sleep(1000);
                src = new File("NullMatrix/" + lst.get(i) + ".txt");
                des = new File("Train/NullMatrix/" + lst.get(i) + ".txt");
                FileUtils.copyFile(src, des);
                //
                src = new File("NullMatrix2/" + lst.get(i) + ".txt");
                des = new File("Train/NullMatrix2/" + lst.get(i) + ".txt");
                FileUtils.copyFile(src, des);
            }
            for (int i = 300; i < lst.size(); i++) {
                File src = new File("SignalMatrix/" + lst.get(i) + ".txt");
                File des = new File("Test/SignalMatrix/" + lst.get(i) + ".txt");
                FileUtils.copyFile(src, des);
                //
                src = new File("NullMatrix/" + lst.get(i) + ".txt");
                des = new File("Test/NullMatrix/" + lst.get(i) + ".txt");
                FileUtils.copyFile(src, des);
                //
                src = new File("NullMatrix2/" + lst.get(i) + ".txt");
                des = new File("Test/NullMatrix2/" + lst.get(i) + ".txt");
                FileUtils.copyFile(src, des);
            }
//            File src = new File("str");
//            File des = new File("str2");
//            FileUtils.copyFile(src, des);
        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        }
    }

    public static void CalculateDSM2() throws IOException {
        String signal = "Train/SignalMatrix/Sum.txt";
        String nul = "Train/NullMatrix/Sum.txt";
        String nul2 = "Train/NullMatrix2/Sum.txt";
        ParseBlosumMatrix pbm = new ParseBlosumMatrix();
        HashMap<String, Integer> PairIndex = AminoAcid.GetPairIndex();
        DSM d = new DSM();
        d.LoadFromFile(signal, nul, 400);
        double[][] m = Dsm.CalcDSM(d.getSignalMat().getArrayCopy(), d.getNullMat().getArrayCopy(),
                pbm, PairIndex, true);
        MyIO.WritePoPToFile("DSM_0_0", m);
        m = Dsm.CalcDSM(d.getSignalMat().getArrayCopy(), d.getNullMat().getArrayCopy(),
                pbm, PairIndex, false);
        MyIO.WritePoPToFile("DSM_0_1", m);
        //
        d.LoadFromFile(signal, nul2, 400);
        m = Dsm.CalcDSM(d.getSignalMat().getArrayCopy(), d.getNullMat().getArrayCopy(),
                pbm, PairIndex, true);
        MyIO.WritePoPToFile("DSM_1_0", m);
        m = Dsm.CalcDSM(d.getSignalMat().getArrayCopy(), d.getNullMat().getArrayCopy(),
                pbm, PairIndex, false);
        MyIO.WritePoPToFile("DSM_1_1", m);
    }

    static public void Test() throws FileNotFoundException, IOException, Exception {
//        String filename = "pdb_file/2HAN.pdb";
//        FileInputStream fstream = new FileInputStream(filename);
//        DataInputStream in = new DataInputStream(fstream);
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        PDBFileParser parser = new PDBFileParser();
//        Structure s = null;
//        s = parser.parsePDBFile(br);
        //       s.

        //System.out.println("");
//        MyRandomForest rf = new MyRandomForest("PSSM_SS_OBV_Train.arff");
//        Instances neg = rf.GetNegIns();
//        double[][] result = rf.Predict(neg);
//        for(int i=0;i<result.length;i++){
//            System.out.println("#: "+i+": "+ result[i][0]+ " / "+ result[i][1]);
//        }
        /// count frequency of 20 amino acid with binding site and non-binding
        String list_dir = "Train/SignalMatrix/list.txt";
        ArrayList<KeyProtein> lst_prot = MyIO.LoadKeyProteins(list_dir);
        ArrayList<String> amino = AminoAcid.getAA();
        
        double[] binding = new double[20];
        double[] non_binding = new double[20];
        for(KeyProtein k: lst_prot){
            int offset = k.getOffset();
            ArrayList<Integer> lst_indx = k.getBindingIndex();
            String sequence = k.getSequence();
            for(int i=0;i<lst_indx.size();i++){
                int tmp = lst_indx.get(i) - offset;
                lst_indx.set(i, tmp);
            }
            for(int i=0;i<sequence.length();i++){
                String ch = sequence.substring(i, i+1);
                int idx = amino.indexOf(ch);
                if(lst_indx.indexOf(i)>=0){
                    binding[idx]++;
                }
                else{
                    non_binding[idx]++;
                }
            }
        }
        double sum_binding=0, sum_non=0;
        for(int i=0;i<20;i++){
            sum_binding += binding[i];
            sum_non += non_binding[i];
        }
        for(int i=0;i<20;i++){
            System.out.println(amino.get(i)+": "+binding[i]/sum_binding+" / "+non_binding[i]/sum_non);
        }
    }

    public static void TongHop() throws FileNotFoundException, IOException {
//        String pdb_dir = "pdb_file";
        String list_dir = "Test/SignalMatrix/list.txt";
//        String signal_dir = "Train/SignalMatrix";
//        String null_dir = "Train/NullMatrix";
//        String null_dir2 = "Train/NullMatrix2";
        ArrayList<double[]> Pos = new ArrayList<double[]>();
        ArrayList<double[]> Neg = new ArrayList<double[]>();
        Matrix DSM1 = MyIO.ReadDSM("DSM_0_0");
        Matrix DSM2 = MyIO.ReadDSM("DSM_0_1");
        Matrix DSM3 = MyIO.ReadDSM("DSM_1_0");
        Matrix DSM4 = MyIO.ReadDSM("DSM_1_1");
        Matrix DSM5 = MyIO.ReadDSM("newDSM.out");
        ArrayList<String> amino = AminoAcid.getAA();

        ArrayList<KeyProtein> lst_prot = MyIO.LoadKeyProteins(list_dir);
        for (KeyProtein k : lst_prot) {
            if (k.getSequence().equalsIgnoreCase("")) {
                k.LoadingFromPDBFile();
            }
            String fasta = k.getName() + "_" + k.getChain() + ".fasta.msa";
            ArrayList<String> msa = MsaFilterer.filter("MSA_file/Collection/" + fasta);
            if (msa.size() < 10) {
                System.out.println("Skip protein: " + k.getName() + "_" + k.getChain());
                continue;
            }
            MSA m = new MSA(k, msa);
            m.AdjustLength();
            
//            Pos.addAll(m.RetrieveSlidingWindow(5, true, DSM1, DSM2, DSM3, DSM4));
//            Neg.addAll(m.RetrieveSlidingWindow(5, false, DSM1, DSM2, DSM3, DSM4));
//            Pos.addAll(m.CalculatePSSMAndSS(amino, 11, true));
//            Neg.addAll(m.CalculatePSSMAndSS(amino, 11, false));
            Pos.addAll(m.CalculatePSSMandUvalue(amino, 11, true, DSM5));
            Neg.addAll(m.CalculatePSSMandUvalue(amino, 11, false, DSM5));
        }
        ARRF_Template.WriteToArrfFile("PSSM_Uvalue_DSM_5_Test.arff", "SlidingWindows", Pos, Neg);
    }
    public static void FindSafeNeg(String filename, String file_output) throws FileNotFoundException, IOException, Exception{
        MyRandomForest rf = new MyRandomForest(filename);
        
        Instances neg = rf.DownNeg((double)1/20, (double)5/10);
        Instances set = rf.GetPosIns();
        set.addAll(neg);
        ARRF_Template.WriteToArffFile(set, file_output);
    }
    public static void GetProteinSequence(String filename, String fileout) throws FileNotFoundException, IOException{
        ArrayList<KeyProtein> lst_protein = MyIO.LoadKeyProteins(filename);
        ArrayList<String> lst_str = new ArrayList<String>();
        for(KeyProtein k:lst_protein){
            String str = ">" + k.getName() + "_" + k.getChain() + "\n";
            str += k.getSequence() + "\n";
            lst_str.add(str);
        }
        MyIO.WriteToFile(fileout, lst_str);
    }
    public static void EachProteinEachFile(String filename) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while(true){
            line = br.readLine();
            if(line==null){
                break;
            }
            line = line.trim();
            if(line.indexOf(">")>=0){
                String name = line.substring(1);
                String seq = br.readLine().trim();
                ArrayList<String> lst_str = new ArrayList<String>();
                lst_str.add(">"+name);
                lst_str.add(seq);
                MyIO.WriteToFile("Train/"+name+".txt", lst_str);
            }
        }
    }
}
