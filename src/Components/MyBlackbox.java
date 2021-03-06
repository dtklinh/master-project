/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import CreateMSA.AlignedProtein;
import Jama.Matrix;
import Method.MyEvaluate;
import Method.MyRandomForest;
import Method.PairOfPair;
import Method.UvalueThreshold;
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
        String dir = "HSSP_Database/Train_AdjustIndex/";
        String Signal_filename = "SumSignalMatrix.txt";
        String Null_filename = "SumNullMatrix1.txt";
        String Null_filename2 = "SumNullMatrix2.txt";
        Matrix SignalMat = new Matrix(new double[400][400]);
        Matrix NullMat = new Matrix(new double[400][400]);
        Matrix NullMat2 = new Matrix(new double[400][400]);
        int distance = 5;
        HashMap<String, Integer> PairIndex = AminoAcid.GetPairIndex();
        int num_skip = 0;
        for (KeyProtein k : lst_prot) {
            if (k.getSequence().equalsIgnoreCase("")) {
                k.LoadingFromPDBFile();
            }
            String fasta = k.getName() + "_" + k.getChain() + ".fasta.msa";
            ArrayList<String> msa = MsaFilterer.filter(dir + "MSA/" + fasta);
//            if (msa.size() < 10) {
//                System.out.println("Skip protein: " + k.getName() + "_" + k.getChain());
//            }

            if (msa.size() < 100) {
                System.err.println("Skip " + k.getName() + " : " + k.getSequence() + ": " + msa.size());
                num_skip++;
                continue;
            }

            MSA m = new MSA(k, msa);
            m.AdjustLength();
//            ArrayList<int[]> signal_indicator = k.RetrieveIndicatorPair2(distance);
//            ArrayList<int[]> null_indicator = k.RetrieveNullIndex2(distance, false);
//            ArrayList<int[]> null_indicator2 = k.RetrieveNullIndex2(distance, true);
            //
            ArrayList<String> lst_cols = m.RetrieveColumnPair();
            ArrayList<int[]> signal_indicator = m.RetrieveIndicatorPair2(distance, lst_cols);
            ArrayList<int[]> null_indicator = m.RetrieveNullIndex2(distance, false, lst_cols);
            ArrayList<int[]> null_indicator2 = m.RetrieveNullIndex2(distance, true, lst_cols);

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
//            ArrayList<String> ColumnPair = PoP_signal.RetrieveColumnPair();
            ArrayList<String> ColumnPair = m.RetrieveColumnPair();
            System.out.println("Finish retrieved Column pair");

            Matrix tmp = PoP_signal.CalculatePoP(ColumnPair, PairIndex);
            MyIO.WritePoPToFile(dir + "SignalMatrix/" + k.getName() + "_" + k.getChain() + ".txt", tmp.getArray());
            SignalMat = SignalMat.plus(tmp);
            System.out.println("Signal matrix was calculated: " + k.getName());

            tmp = PoP_null.CalculatePoP(ColumnPair, PairIndex);
            MyIO.WritePoPToFile(dir + "NullMatrix1/" + k.getName() + "_" + k.getChain() + ".txt", tmp.getArray());
            NullMat = NullMat.plus(tmp);
            System.out.println("Null matrix was calculated: " + k.getName());
            //
            tmp = PoP_null2.CalculatePoP(ColumnPair, PairIndex);
            MyIO.WritePoPToFile(dir + "NullMatrix2/" + k.getName() + "_" + k.getChain() + ".txt", tmp.getArray());
            NullMat2 = NullMat2.plus(tmp);
            System.out.println("Null matrix 2 was calculated: " + k.getName());

        }
        System.err.println("# skipped protein: " + num_skip);
        MyIO.WritePoPToFile(dir + "SignalMatrix/" + Signal_filename, SignalMat.getArray());
        MyIO.WritePoPToFile(dir + "NullMatrix1/" + Null_filename, NullMat.getArray());
        MyIO.WritePoPToFile(dir + "NullMatrix2/" + Null_filename2, NullMat2.getArray());
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

                    String name = "HSSP_Database/Test/MSA/" + tmp.trim() + ".fasta.msa";
//                    String name = "pdb_file/pdb" + tmp.trim().substring(0,4).toLowerCase()+".ent";
//                    String name2 = "pdb_file/" + tmp.trim().substring(0,4).toUpperCase()+".pdb";
                    FileInputStream fstream2 = new FileInputStream(name);
//                    try{
//                    fstream2 = new FileInputStream(name);
//                    } catch(FileNotFoundException e){
//                        fstream2 = new FileInputStream(name2);
//                    }

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
        String dir = "HSSP_Database/Train_AdjustIndex/";
        String signal = dir + "SignalMatrix/SumSignalMatrix.txt";
        String nul = dir + "NullMatrix1/SumNullMatrix1.txt";
        String nul2 = dir + "NullMatrix2/SumNullMatrix2.txt";
        ParseBlosumMatrix pbm = new ParseBlosumMatrix();
        HashMap<String, Integer> PairIndex = AminoAcid.GetPairIndex();
        DSM d = new DSM();
        d.LoadFromFile(signal, nul, 400);
        double[][] m = Dsm.CalcDSM(d.getSignalMat().getArrayCopy(), d.getNullMat().getArrayCopy(),
                pbm, PairIndex, true);
        MyIO.WritePoPToFile(dir + "DSM_0_0", m);

        m = Dsm.CalcDSM(d.getSignalMat().getArrayCopy(), d.getNullMat().getArrayCopy(),
                pbm, PairIndex, false);
        MyIO.WritePoPToFile(dir + "DSM_0_1", m);
        //
        d.LoadFromFile(signal, nul2, 400);
        m = Dsm.CalcDSM(d.getSignalMat().getArrayCopy(), d.getNullMat().getArrayCopy(),
                pbm, PairIndex, true);
        MyIO.WritePoPToFile(dir + "DSM_1_0", m);
        m = Dsm.CalcDSM(d.getSignalMat().getArrayCopy(), d.getNullMat().getArrayCopy(),
                pbm, PairIndex, false);
        MyIO.WritePoPToFile(dir + "DSM_1_1", m);
    }

    static public void Test() throws FileNotFoundException, IOException, Exception {
//        KeyProtein k = new KeyProtein("1A6Y", "A");
//        k.LoadingFromPDBFile();
//        ArrayList<int[]> lst = k.RetrieveNullIndex2(3, true);
//        int offset = k.getOffset();
//        for(int i=0;i<lst.size();i++){
//            int start = lst.get(i)[0]+ offset;
//            int end = lst.get(i)[1]+ offset;
//            System.out.println("["+start + ":"+ end + "]");
//        }

        /////////**************////////////
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
//        String list_dir = "Train/SignalMatrix/list.txt";
//        ArrayList<KeyProtein> lst_prot = MyIO.LoadKeyProteins(list_dir);
//        ArrayList<String> amino = AminoAcid.getAA();
//        
//        double[] binding = new double[20];
//        double[] non_binding = new double[20];
//        for(KeyProtein k: lst_prot){
//            int offset = k.getOffset();
//            ArrayList<Integer> lst_indx = k.getBindingIndex();
//            String sequence = k.getSequence();
//            for(int i=0;i<lst_indx.size();i++){
//                int tmp = lst_indx.get(i) - offset;
//                lst_indx.set(i, tmp);
//            }
//            for(int i=0;i<sequence.length();i++){
//                String ch = sequence.substring(i, i+1);
//                int idx = amino.indexOf(ch);
//                if(lst_indx.indexOf(i)>=0){
//                    binding[idx]++;
//                }
//                else{
//                    non_binding[idx]++;
//                }
//            }
//        }
//        double sum_binding=0, sum_non=0;
//        for(int i=0;i<20;i++){
//            sum_binding += binding[i];
//            sum_non += non_binding[i];
//        }
//        for(int i=0;i<20;i++){
//            System.out.println(amino.get(i)+": "+binding[i]/sum_binding+" / "+non_binding[i]/sum_non);
//        }
        String list_dir = "BLAST_Database/Test/test.txt";
//        String signal_dir = "Train/SignalMatrix";
//        String null_dir = "Train/NullMatrix";
//        String null_dir2 = "Train/NullMatrix2";
//        ArrayList<double[]> Pos = new ArrayList<double[]>();
//        ArrayList<double[]> Neg = new ArrayList<double[]>();
//        Matrix DSM1 = MyIO.ReadDSM("HSSP_Database/Train/DSM_0_0");
//        Matrix DSM2 = MyIO.ReadDSM("HSSP_Database/Train/DSM_0_1");
        Matrix DSM3 = MyIO.ReadDSM("HSSP_Database/Train/DSM_1_0");
//        Matrix DSM4 = MyIO.ReadDSM("HSSP_Database/Train/DSM_1_1");
//        Matrix DSM5 = MyIO.ReadDSM("newDSM.out");
        ArrayList<String> amino = AminoAcid.getAA();

        ArrayList<KeyProtein> lst_prot = MyIO.LoadKeyProteins(list_dir);
        MyEvaluate me = new MyEvaluate();
        for (KeyProtein k : lst_prot) {
            if (k.getSequence().equalsIgnoreCase("")) {
                k.LoadingFromPDBFile();
            }
            String fasta = k.getName() + "_" + k.getChain() + ".msa";
            ArrayList<String> msa = MsaFilterer.filter("BLAST_Database/Test/MSA/" + fasta);
            if (msa.size() < 100) {
                System.out.println("Skip protein: " + k.getName() + "_" + k.getChain());
                continue;
            }
            MSA m = new MSA(k, msa);
            m.AdjustLength();
            ArrayList<String> lst_cols = m.RetrieveColumnPair();
            System.out.println("Protein: " + k.getName() + ", chain: " + k.getChain());
            ArrayList<Integer> idx_col = m.ScoreSignificantValueOfPair(msa, 5, DSM3, lst_cols);
            MyEvaluate e = m.Evaluate(idx_col, lst_cols);
            me.Add(e);
            System.out.println("Sensitivity: " + e.Sensitivity());
            System.out.println("Specificity: " + e.Specificity());
            System.out.println("MCC: " + e.MCC());
//            ArrayList<Integer> binding = m.getMyKeyProtein().getBindingIndex();

//            break;
        }
        System.out.println("Total tp: " + me.getTruePositive());
        System.out.println("Total tn: " + me.getTrueNegative());
        System.out.println("Total fp: " + me.getFalsePositive());
        System.out.println("Total fn: " + me.getFalseNegative());
        System.out.println("Final Sensitivity: " + me.Sensitivity());
        System.out.println("Final Specificity: " + me.Specificity());
        System.out.println("Final MCC: " + me.MCC());
    }

    public static void TongHop(String dir, String list_dir, Matrix MyDSM, String out_name) throws FileNotFoundException, IOException {
//        String pdb_dir = "pdb_file";
//        String dir = "HSSP_Database/Test62/";
//        String list_dir = dir + "AminoList374_New.txt";
//        String list_dir = dir + "PDNA62.txt";
//        String signal_dir = "Train/SignalMatrix";
//        String null_dir = "Train/NullMatrix";
//        String null_dir2 = "Train/NullMatrix2";
        ArrayList<double[]> Pos = new ArrayList<double[]>();
        ArrayList<double[]> Neg = new ArrayList<double[]>();
//        Matrix DSM1 = MyIO.ReadDSM("HSSP_Database/Train_AdjustIndex/DSM_0_0");
//        Matrix DSM2 = MyIO.ReadDSM("HSSP_Database/Train_AdjustIndex/DSM_0_1");
//        Matrix DSM3 = MyIO.ReadDSM("BLAST_Database/Train/DSM_1_0");
//        Matrix DSM4 = MyIO.ReadDSM("HSSP_Database/Train/DSM_1_1");
//        Matrix DSM5 = MyIO.ReadDSM("newDSM.out");
        ArrayList<String> amino = AminoAcid.getAA();
        ArrayList<UvalueThreshold> lst_thres = MyIO.LoadUvalueThres(dir + "Threshold.txt");

        ArrayList<KeyProtein> lst_prot = MyIO.LoadKeyProteins(list_dir);
        for (KeyProtein k : lst_prot) {
            if (k.getSequence().equalsIgnoreCase("")) {
                k.LoadingFromPDBFile();
            }
            int u_thres_idx=0;
            String n = k.getName()+"_"+ k.getChain();
            for(int i=0;i<lst_thres.size();i++){
                if(lst_thres.get(i).getProtName().equalsIgnoreCase(n)){
                    u_thres_idx = i;
                    break;
                }
            }
            String fasta = k.getName() + "_" + k.getChain() + ".msa";
            ArrayList<String> msa = MsaFilterer.filter(dir + "MSA/" + fasta);
            if (msa.size() < 100) {
                System.out.println("Skip protein: " + k.getName() + "_" + k.getChain());
                continue;
            }
            MSA m = new MSA(k, msa);
            m.AdjustLength();

//            Pos.addAll(m.RetrieveSlidingWindow(5, true, DSM1, DSM2, DSM3, DSM4));
//            Neg.addAll(m.RetrieveSlidingWindow(5, false, DSM1, DSM2, DSM3, DSM4));
//            Pos.addAll(m.CalculatePSSMAndSS(amino, 11, true));
//            Neg.addAll(m.CalculatePSSMAndSS(amino, 11, false));

//            Pos.addAll(m.CalculatePSSMandUvalue(amino, 11, true, MyDSM));
//            Neg.addAll(m.CalculatePSSMandUvalue(amino, 11, false, MyDSM));

//            Pos.addAll(m.ExtractPSSM(true, 11, dir + "PSSM/", true));
//            Neg.addAll(m.ExtractPSSM(true, 11, dir + "PSSM/", false));
            
//            double thres = lst_thres.get(u_thres_idx).getThres()[1]; // 10%
//            Pos.addAll(m.ExtractBooleanUvalue(amino, 11, true, MyDSM, thres));
//            Neg.addAll(m.ExtractBooleanUvalue(amino, 11, false, MyDSM, thres));
//            Pos.addAll(m.ExtractPSSMBooleanUvalue(11, true, MyDSM, thres, true, dir+"PSSM/"));
//            Neg.addAll(m.ExtractPSSMBooleanUvalue(11, false, MyDSM, thres, true, dir+"PSSM/"));
            
            Pos.addAll(m.ExtractPSSM_Pair(3,true, amino));
            Neg.addAll(m.ExtractPSSM_Pair(3, false, amino));
        }
        ARRF_Template.WriteToArrfFile(dir + out_name + ".arff", out_name, Pos, Neg);
    }

    public static void FindSafeNeg(String filename, String file_output) throws FileNotFoundException, IOException, Exception {
        MyRandomForest rf = new MyRandomForest(filename);

        Instances neg = rf.DownNeg((double) 1 / 9, (double) 2 / 9);
        Instances set = rf.GetPosIns();
        set.addAll(neg);
        ARRF_Template.WriteToArffFile(set, file_output);
    }

    public static void GetProteinSequence(String filename, String fileout) throws FileNotFoundException, IOException {
        ArrayList<KeyProtein> lst_protein = MyIO.LoadKeyProteins(filename);
        ArrayList<String> lst_str = new ArrayList<String>();
        for (KeyProtein k : lst_protein) {
            String str = ">" + k.getName() + "_" + k.getChain() + "\n";
            str += k.getSequence() + "\n";
            lst_str.add(str);
        }
        MyIO.WriteToFile(fileout, lst_str);
    }

    public static void EachProteinEachFile(String filename) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.indexOf(">") >= 0) {
                String name = line.substring(1);
                String seq = br.readLine().trim();
                ArrayList<String> lst_str = new ArrayList<String>();
                lst_str.add(">" + name);
                lst_str.add(seq);
                MyIO.WriteToFile("Train/" + name + ".txt", lst_str);
            }
        }
    }

    public static void GetBindingIdx(String Pdb, String chain) {
        KeyProtein k = new KeyProtein(Pdb, chain);
        k.LoadingFromPDBFile();
        System.out.print("Binding index: ");
        for (int i : k.getBindingIndex()) {
            System.out.print(i + "  ");
        }
        System.out.println();
    }

    public static void FromOutFileToMSA(String filelist, String dir) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(filelist);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String tmp = "";
        while (true) {
            tmp = br.readLine();
            if (tmp == null) {
                break;
            }
            tmp = tmp.trim();
            if (!tmp.isEmpty()) {
                AlignedProtein a = new AlignedProtein(tmp);
                MyIO.WriteToFile(dir + tmp + ".msa", a.CreateMSA());
            }
        }
        br.close();
    }

    public static void PredictBindingPosition(String name, String dir, int width) throws FileNotFoundException, IOException, Exception {
        MyRandomForest rf = new MyRandomForest("BLAST_Database/Train/NativePSSM_MSA_100_Train.arff");
        int radius = (width-1)/2;
        ArrayList<String> arr = MsaFilterer.filter(dir + "MSA/" + name + ".msa");
        if (arr.size() < 100) {
            System.err.println(name + " : don't have enough info to predict");
        } else {
            KeyProtein k = new KeyProtein(name.substring(0, 4), name.substring(5, 6));
            k.LoadingFromPDBFile();

            MSA m = new MSA(k, arr);
            ArrayList<double[]> feature = m.CreateFeature_PSSM(true, 11, dir);
            double[][] val = rf.Predict(feature);
            //
            ArrayList<Integer> lst_Binding = k.getBindingIndex();
            String seq = k.getSequence();
            System.out.println("Sequence: " + seq);
            System.out.print("Real idx: ");
            for (int i = 0; i < seq.length(); i++) {
                if (lst_Binding.indexOf(k.GetAbsoluteIndex(i)) >= 0) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
            System.out.print("Calc idx: -----");
            ArrayList<Integer> prediction = new ArrayList<Integer>();
            for (int i = 0; i < val.length; i++) {
                if (val[i][0] >= val[i][1]) {
                    System.out.print("*");
                    prediction.add(i+radius);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("-----\n");
            MyEvaluate me = new MyEvaluate(prediction, k, width);
                System.out.println("TP: "+ me.getTruePositive());
                System.out.println("TN: "+ me.getTrueNegative());
                System.out.println("FP: "+ me.getFalsePositive());
                System.out.println("FN: "+ me.getFalseNegative());
        }
    }

    public static void PredictBindingPosition(ArrayList<String> lst_name, String dir, int width) throws FileNotFoundException, IOException, Exception {
        MyRandomForest rf = new MyRandomForest("BLAST_Database/Train/NativePSSM_MSA_100_Train.arff");
        int radius = (width-1)/2;
        for (String name : lst_name) {
            ArrayList<String> arr = MsaFilterer.filter(dir + "MSA/" + name + ".msa");
            if (arr.size() < 0) {
                System.err.println(name + " : don't have enough info to predict");
            } else {
                KeyProtein k = new KeyProtein(name.substring(0, 4), name.substring(5, 6));
                k.LoadingFromPDBFile();

                MSA m = new MSA(k, arr);
                ArrayList<double[]> feature = m.CreateFeature_PSSM(true, 11, dir);
                double[][] val = rf.Predict(feature);
                //
                ArrayList<Integer> lst_Binding = k.getBindingIndex();
                String seq = k.getSequence();
                System.out.println(name+" :  " + seq);
                System.out.print("Real idx: ");
                for (int i = 0; i < seq.length(); i++) {
                    if (lst_Binding.indexOf(k.GetAbsoluteIndex(i)) >= 0) {
                        System.out.print("*");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("\n");
                System.out.print("Calc idx: -----");
                ArrayList<Integer> prediction = new ArrayList<Integer>();
                for (int i = 0; i < val.length; i++) {
                    if (val[i][0] >= val[i][1]) {
                        System.out.print("*");
                        prediction.add(i+radius);
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("-----\n");
                MyEvaluate me = new MyEvaluate(prediction, k, width);
                System.out.println("Number of row: "+ arr.size());
                System.out.println("TP: "+ me.getTruePositive());
                System.out.println("TN: "+ me.getTrueNegative());
                System.out.println("FP: "+ me.getFalsePositive());
                System.out.println("FN: "+ me.getFalseNegative());
            }
        }
    }
}
