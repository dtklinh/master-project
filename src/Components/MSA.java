/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import CreateMSA.PSSM;
import Jama.Matrix;
import Method.MyEvaluate;
import Method.MyPair;
import MyDivergence.MyEntropy;
import SignificantFinder.GlobalVar;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.naming.spi.DirStateFactory;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.util.PDFTextStripper;

/**
 *
 * @author linh
 */
public class MSA {

//    private String ProtName;
    private KeyProtein MyKeyProtein;
    private ArrayList<String> LstSeqs;
//    private ArrayList<Integer> LstIndex;
//    private String Chain;
//    private int Offset;

    /**
     * @return the ProtName
     */
//    public String getProtName() {
//        return ProtName;
//    }
//
//    /**
//     * @param ProtName the ProtName to set
//     */
//    public void setProtName(String ProtName) {
//        this.ProtName = ProtName;
//    }
    /**
     * @return the LstSeqs
     */
    public ArrayList<String> getLstSeqs() {
        return LstSeqs;
    }

    /**
     * @param LstSeqs the LstSeqs to set
     */
    public void setLstSeqs(ArrayList<String> LstSeqs) {
        this.LstSeqs = LstSeqs;
    }

    /**
     * @return the LstIndex
     */
//    public ArrayList<Integer> getLstIndex() {
//        return LstIndex;
//    }
    /**
     * @param LstIndex the LstIndex to set
     */
//    public void setLstIndex(ArrayList<Integer> LstIndex) {
//        this.LstIndex = LstIndex;
//    }
    //////////////////////// Creator /////////////////////
    public MSA() {
        //      this.ProtName = "";
        this.LstSeqs = new ArrayList<String>();
        this.MyKeyProtein = new KeyProtein();
        //      this.LstIndex = new ArrayList<Integer>();
    }

    public MSA(ArrayList<String> msa) throws FileNotFoundException, IOException {
//        this.ProtName = "";
//        this.Chain = "";
//        this.LstIndex = new ArrayList<Integer>();
//        this.Offset = -1;
        this.MyKeyProtein = new KeyProtein();
        this.LstSeqs = msa;
    }

    public MSA(String filename, String file_idx) throws FileNotFoundException, IOException {
//        if (filename.indexOf(".txt") >= 0 || filename.indexOf(".msa") >= 0) {
//            this.ProtName = filename.substring(0, filename.indexOf(".txt") + 1);
//            this.ProtName = filename.substring(0,filename.length()-4);
//        } else {
//            this.ProtName = filename;
//        }
//        this.LstIndex = new ArrayList<Integer>();
        this.LstSeqs = new ArrayList<String>();
        // read strings
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
                    this.LstSeqs.add(container);
                }
                container = "";
            } else {
                container = container + tmp.trim();
            }
        }
        br.close();
        // read indexs
        FileInputStream fstreamm = new FileInputStream(file_idx);
        DataInputStream inn = new DataInputStream(fstreamm);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(inn));
        tmp = "";
        KeyProtein k = new KeyProtein();
        // index of binding
        tmp = br2.readLine().trim();
        String[] lst_idx = tmp.split("\t");
        for (int i = 0; i < lst_idx.length; i++) {
            k.getBindingIndex().add(Integer.parseInt(lst_idx[i]));
        }
        // offset
        tmp = br2.readLine().trim();
        k.setOffset(Integer.parseInt(tmp));
        // protein name
        k.setName(br2.readLine().trim());
        // chain of protein
        k.setChain(br2.readLine().trim());
        // sequence
        k.setSequence(br2.readLine().trim());
        br2.close();
        this.MyKeyProtein = k;
    }

    public MSA(String file_idx, ArrayList<String> msa) throws FileNotFoundException, IOException {
//        this.LstIndex = new ArrayList<Integer>();
        this.LstSeqs = new ArrayList<String>();
        FileInputStream fstreamm = new FileInputStream(file_idx);
        DataInputStream inn = new DataInputStream(fstreamm);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(inn));
        String tmp = "";
        KeyProtein k = new KeyProtein();
        tmp = br2.readLine().trim();
        String[] lst_idx = tmp.split("\t");
        for (int i = 0; i < lst_idx.length; i++) {
            k.getBindingIndex().add(Integer.parseInt(lst_idx[i]));
        }
        // offset
        tmp = br2.readLine().trim();
        k.setOffset(Integer.parseInt(tmp));
        // protein name
        k.setName(br2.readLine().trim());
        // chain of protein
        k.setChain(br2.readLine().trim());
        // sequence
        k.setSequence(br2.readLine().trim());
        br2.close();
        this.MyKeyProtein = k;
        //
        //       this.ProtName = prot_name;
        this.LstSeqs = (ArrayList<String>) msa.clone();
    }

    public MSA(KeyProtein key, ArrayList<String> msa) {
        this.LstSeqs = (ArrayList<String>) msa.clone();
//        this.Chain = key.getChain();
//        this.LstIndex = key.getBindingIndex();
//        this.Offset = key.getOffset();
//        this.ProtName = key.getName();
        this.MyKeyProtein = key;
    }

    public MSA MakeUnique() {
        MSA result = new MSA();
//        result.setProtName(ProtName);
//        result.setLstIndex(LstIndex);
        result.setMyKeyProtein(MyKeyProtein);
        Set<String> s = new LinkedHashSet<String>(this.LstSeqs);
        result.LstSeqs.addAll(s);
        return result;
    }

    public void PrintOutSimpleText(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        String tmp = this.MyKeyProtein.getName() + "\n";
        for (int i = 0; i < this.MyKeyProtein.getBindingIndex().size(); i++) {
            tmp = tmp + this.MyKeyProtein.getBindingIndex().get(i) + "\t";
        }
        tmp = tmp + "\n";
        for (int i = 0; i < this.LstSeqs.size(); i++) {
            tmp = tmp + this.LstSeqs.get(i) + "\n";
        }
        writer.write(tmp);
        writer.close();
    }

    public void PrintToHTML(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        String tmp = this.MyKeyProtein.getName() + "\n";
        for (int i = 0; i < this.MyKeyProtein.getBindingIndex().size(); i++) {
            tmp = tmp + this.MyKeyProtein.getBindingIndex().get(i) + "\t";
        }
        tmp = tmp + " \n";
        for (int i = 0; i < this.LstSeqs.size(); i++) {
            tmp = tmp + "<br><font face='Monospace'>";
            String str = this.LstSeqs.get(i);
            for (int j = 0; j < str.length(); j++) {
                if (this.MyKeyProtein.getBindingIndex().indexOf(j) >= 0) {
                    tmp = tmp + "<font color='red'>" + str.charAt(j) + "</font>";
                } else {
                    tmp = tmp + str.charAt(j);
                }
            }
            tmp = tmp + "</font></br>";
        }
        writer.write(tmp);
        writer.close();
    }

    public ArrayList<double[]> CalculateFrequency() {
        int num_seq = this.LstSeqs.size();
        // find minimum length
        int min_length = 10000;
        for (int i = 0; i < num_seq; i++) {
            if (LstSeqs.get(i).length() < min_length) {
                min_length = LstSeqs.get(i).length();
            }
        }

        ArrayList<double[]> result = new ArrayList<double[]>();
        for (int i = 0; i < min_length; i++) {
            ArrayList<Character> tmp = new ArrayList<Character>();
            for (int j = 0; j < num_seq; j++) {
                tmp.add(this.LstSeqs.get(j).charAt(i));
            }
            HashSet<Character> h = new HashSet<Character>();
            h.addAll(tmp);
            ArrayList<Character> tam = new ArrayList<Character>();
            tam.addAll(h);
            double[] freq = new double[h.size()];
            for (int j = 0; j < h.size(); j++) {
                freq[j] = (double) Collections.frequency(tmp, tam.get(j)) / num_seq;
            }
            result.add(freq);
        }
        return result;
    }

    public double[] CalculateEntropy() {
        ArrayList<double[]> fre = this.CalculateFrequency();
        double[] ressult = new double[fre.size()];
        for (int i = 0; i < fre.size(); i++) {
            MyEntropy en = new MyEntropy(fre.get(i));
            ressult[i] = en.CalculateEntropy();
        }
        return ressult;
    }
    // find position which bind to DNA

//    public static KeyProtein FindBindingPosition(String filename, String chain, int offset) throws FileNotFoundException, IOException {
//        // parse PDF file
//        KeyProtein key = new KeyProtein();
//        key.setName(filename.substring(0, filename.length() - 4));
//        key.setOffset(offset);
//        key.setChain(chain);
//        //
//        HashSet<Integer> set = new HashSet<Integer>();
//        ArrayList<Integer> binding_idx = new ArrayList<Integer>();
//        AminoAcid amino = new AminoAcid();
//        PDDocument pddDocument = PDDocument.load(new File(filename));
//        PDFTextStripper textStripper = new PDFTextStripper();
//        String str = textStripper.getText(pddDocument);
//        String[] lst_token = str.split("\n");
//        for (int i = 0; i < lst_token.length; i++) {
//            String token = lst_token[i].trim();
//            String[] lst_word = token.split(" ");
//            for (int j = 0; j < lst_word.length; j++) {
//                String word = lst_word[j].trim();
//                if (word.length() >= 7) {
//              //      String[] chain_word = new String[chain.length()];
//                    for (int k = 0; k < chain.length(); k++) {
//                        int idx = amino.MatchPattern(word, chain.substring(k, k+1));
//                        if (idx > 0) {
//                            set.add(idx - offset);
//                        }
//                    }
//                }
//            }
//            //   token.ind
//        }
//        key.getBindingIndex().addAll(set);
//        pddDocument.close();
//        return key;
//
//    }
    /**
     * @return the Chain
     */
//    public String getChain() {
//        return Chain;
//    }
    /**
     * @param Chain the Chain to set
     */
//    public void setChain(String Chain) {
//        this.Chain = Chain;
//    }
    /**
     * @return the Offset
     */
//    public int getOffset() {
//        return Offset;
//    }
//
//    /**
//     * @param Offset the Offset to set
//     */
//    public void setOffset(int Offset) {
//        this.Offset = Offset;
//    }
    /**
     * @return the MyKeyProtein
     */
    public KeyProtein getMyKeyProtein() {
        return MyKeyProtein;
    }

    /**
     * @param MyKeyProtein the MyKeyProtein to set
     */
    public void setMyKeyProtein(KeyProtein MyKeyProtein) {
        this.MyKeyProtein = MyKeyProtein;
    }

    public ArrayList<String> RetrieveColumnPair() {
//        this.AdjustLength();
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<String> lst_seq = this.getLstSeqs();
//        int min = 10000;
//        for (String s : lst_seq) {
//            s = s.trim();
//            if (s.length() < min) {
//                min = s.length();
//            }
//        }
        for (int i = 0; i < this.MyKeyProtein.getSequence().length(); i++) {
            String tmp = "";
            for (String s : lst_seq) {
                tmp = tmp + String.valueOf(s.charAt(i));
            }
            res.add(tmp);
        }

        return res;
    }

    public void AdjustLength() {
        int len = this.MyKeyProtein.getSequence().trim().length();
        for (int i = 0; i < this.LstSeqs.size(); i++) {
            String s = this.LstSeqs.get(i);
            if (s.trim().length() < len) {
                while (s.trim().length() < len) {
                    s = s + "-";
                }
                this.LstSeqs.set(i, s);
            }
        }
    }

    public ArrayList<double[]> RetrieveSlidingWindow(int width, boolean pos, Matrix DSM1,
            Matrix DSM2, Matrix DSM3, Matrix DSM4) {
        // width must be odd number
        int radius = (width - 1) / 2;
        ArrayList<double[]> res = new ArrayList<double[]>();
        ArrayList<Integer> BindingIdx = this.MyKeyProtein.getBindingIndex();
        if (BindingIdx == null) {
            System.err.println("No binding index");
            System.exit(1);
        }
        int offset = this.MyKeyProtein.getOffset();
        for (int i = 0; i < BindingIdx.size(); i++) {
            int adjust = BindingIdx.get(i) - offset;
            BindingIdx.set(i, adjust);
        }
        ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
        for (int i = (width - 1) / 2; i < Lst_ColumnPair.size() - (width - 1) / 2 - 1; i++) {
            if (pos) {
                if (BindingIdx.indexOf(i) < 0) {
                    continue;
                }
            } else {
                if (BindingIdx.indexOf(i) >= 0) {
                    continue;
                }
            }
            double[] tmp = new double[5 * width * (width - 1) / 2];
            int position = 0;
            for (int j = i - radius; j < i + radius; j++) {
                for (int k = j + 1; k <= i + radius; k++) {
                    MyPair p = new MyPair(Lst_ColumnPair.get(j), Lst_ColumnPair.get(k));
                    // calculate H2R
                    Matrix freMatrix = p.CalculatePair();
                    Matrix vec = MyPair.ConverToVector(freMatrix);
                    double v0 = freMatrix.CalculateUValue();
                    double v1 = MyPair.CalculateUAlphaValue(vec, DSM1);
                    double v2 = MyPair.CalculateUAlphaValue(vec, DSM2);
                    double v3 = MyPair.CalculateUAlphaValue(vec, DSM3);
                    double v4 = MyPair.CalculateUAlphaValue(vec, DSM4);
                    tmp[position] = v0;
                    tmp[position + 1] = v1;
                    tmp[position + 2] = v2;
                    tmp[position + 3] = v3;
                    tmp[position + 4] = v4;
                    position = position + 5;
                }
            }
            res.add(tmp);


        }
        return res;
    }

    public double[] CalculateUvalueForSlidingWin(ArrayList<String> ColumnPairs, int begin, int end, Matrix dsm) {
        int width = end - begin;
        double[] res = new double[width * (width - 1) / 2];
        int position = 0;
        for (int i = begin; i < end - 1; i++) {
            for (int j = i + 1; j < end; j++) {
                MyPair p = new MyPair(ColumnPairs.get(i), ColumnPairs.get(j));
                Matrix freq = p.CalculatePair();
                Matrix vec = MyPair.ConverToVector(freq);
                if (dsm != null) {
                    res[position] = MyPair.CalculateUAlphaValue(vec, dsm);
                } else {
                    res[position] = freq.CalculateUValue();
                }
                position++;
            }
        }
        return res;
    }

    public double[] CalculateUvalueForSlidingWin(ArrayList<String> ColumnPairs, int begin, int end, Matrix dsm, double thres) {
        int width = end - begin;
        double[] res = new double[width * (width - 1) / 2];
        int position = 0;
        for (int i = begin; i < end - 1; i++) {
            for (int j = i + 1; j < end; j++) {
                MyPair p = new MyPair(ColumnPairs.get(i), ColumnPairs.get(j));
                Matrix freq = p.CalculatePair();
                Matrix vec = MyPair.ConverToVector(freq);
                if (dsm != null) {
                    double val = MyPair.CalculateUAlphaValue(vec, dsm);
                    if (val >= thres) {
                        res[position] = 1.0;
                    } else {
                        res[position] = 0.0;
                    }
                } else {
                    double val = freq.CalculateUValue();
                    if (val >= thres) {
                        res[position] = 1.0;
                    } else {
                        res[position] = 0.0;
                    }
                }
                position++;
            }
        }
        return res;
    }

    public ArrayList<double[]> CalculatePSSMAndSS(ArrayList<String> amino, int width, boolean pos) throws FileNotFoundException, IOException {

        ArrayList<double[]> res = new ArrayList<double[]>();
        this.AdjustLength();
        ArrayList<Integer> BindingIdx = this.MyKeyProtein.getBindingIndex();
        if (BindingIdx == null) {
            System.err.println("No binding index");
            System.exit(1);
        }
        System.out.println("Protein length: " + this.MyKeyProtein.getSequence().length());
        int radius = (width - 1) / 2;
//        double[][] m = new double[20][this.MyKeyProtein.getSequence().length()];
//        int[] TotalCount = new int[20];
        ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
        System.out.println("Lst_Column: " + Lst_ColumnPair.size());
//        for(int i=0;i<ColumnStr.size();i++){
//            String str = ColumnStr.get(i);
//            ArrayList<Character> char_lst = new ArrayList<Character>();
//            for(int j=0;j<str.length();j++){
//                char_lst.add(str.charAt(j));
//            }
//            
//        }
        Matrix MatOBV = CreateOBVList(this.MyKeyProtein.getSequence());
        Matrix MatSS = CreateSSList();
        Matrix MatPSSM = CreatePSSMList(Lst_ColumnPair, amino);
        System.out.println("MatOBV: " + MatOBV.getRowDimension() + ":" + MatOBV.getColumnDimension());
        System.out.println("MatSS: " + MatSS.getRowDimension() + ":" + MatSS.getColumnDimension());
        for (int i = (width - 1) / 2; i < Lst_ColumnPair.size() - (width - 1) / 2 - 1; i++) {
            if (pos) {
                if (BindingIdx.indexOf(i) < 0) {
                    continue;
                }
            } else {
                if (BindingIdx.indexOf(i) >= 0) {
                    continue;
                }
            }
            // PSSM
            double[] feature = new double[width * 29];
//            ArrayList<String> tmp = (ArrayList<String>)Lst_ColumnPair.subList(i-radius, i+radius+1);
            ArrayList<String> tmp = new ArrayList<String>();
            for (int j = i - radius; j < i + radius + 1; j++) {
                tmp.add(Lst_ColumnPair.get(j));
            }
//            double[] pssm = CreatePSSM(tmp, amino);
            Matrix pssm = MatPSSM.getMatrix(0, 19, i - radius, i + radius);
            double[][] VecPSSM = pssm.getArrayCopy();
            // Secondary Structure
            System.out.println("i: " + i + "  // Radius: " + radius);
            Matrix ss = MatSS.getMatrix(0, 2, i - radius, i + radius);
            double[][] VecSS = ss.getArrayCopy();
            // OBV
            Matrix obv = MatOBV.getMatrix(0, 5, i - radius, i + radius);
            double[][] VecOBV = obv.getArrayCopy();
            int position = 0;
//            for(int j=0; j<pssm.length;j++){
//                feature[position] = pssm[j];
//                position++;
//            }
            for (int j = 0; j < VecPSSM[0].length; j++) {
                for (int k = 0; k < VecPSSM.length; k++) {
                    feature[position] = VecPSSM[k][j];
                    position++;
                }
            }
            for (int j = 0; j < VecSS[0].length; j++) {
                for (int k = 0; k < VecSS.length; k++) {
                    feature[position] = VecSS[k][j];
                    position++;
                }
            }
            for (int j = 0; j < VecOBV[0].length; j++) {
                for (int k = 0; k < VecOBV.length; k++) {
                    feature[position] = VecOBV[k][j];
                    position++;
                }
            }
            res.add(feature);
        }
        return res;

    }

    public ArrayList<double[]> CalculatePSSMandUvalue(ArrayList<String> amino, int width, boolean pos,
            Matrix dsm) throws FileNotFoundException, IOException {
        ArrayList<double[]> res = new ArrayList<double[]>();
        this.AdjustLength();
        ArrayList<Integer> BindingIdx = this.MyKeyProtein.getBindingIndex();
        if (BindingIdx == null) {
            System.err.println("No binding index");
            System.exit(1);
        }
        int offset = this.MyKeyProtein.getOffset();
        System.out.println("Protein length: " + this.MyKeyProtein.getSequence().length());
        int radius = (width - 1) / 2;
//        double[][] m = new double[20][this.MyKeyProtein.getSequence().length()];
//        int[] TotalCount = new int[20];
        ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
        System.out.println("Lst_Column: " + Lst_ColumnPair.size());

//        Matrix MatPSSM = CreatePSSMList(Lst_ColumnPair, amino);

        for (int i = (width - 1) / 2; i < Lst_ColumnPair.size() - (width - 1) / 2 - 1; i++) {
            if (pos) {
//                if (BindingIdx.indexOf(i+offset) < 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) < 0) {
//                if(this.MyKeyProtein.IsNearBindingResidual(i, (width-1)/2)){
                    continue;
                }
            } else {
//                if (BindingIdx.indexOf(i+offset) >= 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) >= 0) {
                    continue;
                }
            }
            // PSSM
//            int len = width*20 + width*(width-1)/2;
            int len = width * 20;
//            int len = width*(width-1)/2;
            double[] feature = new double[len];


//            Matrix pssm = MatPSSM.getMatrix(0, 19, i - radius, i + radius);
//            double[][] VecPSSM = pssm.getArrayCopy();
            //////     double[] uvalue_null = this.CalculateUvalueForSlidingWin(Lst_ColumnPair, i-radius, i+radius+1, null);
            double thres = this.CalculateThreshold(dsm, 0.1);
            double[] uvalue = this.CalculateUvalueForSlidingWin(Lst_ColumnPair, i - radius, i + radius + 1, dsm, thres);
            int position = 0;
//            for(int j=0; j<pssm.length;j++){
//                feature[position] = pssm[j];
//                position++;
//            }
//            for (int j = 0; j < VecPSSM[0].length; j++) {
//                for (int k = 0; k < VecPSSM.length; k++) {
//                    feature[position] = VecPSSM[k][j];
//                    position++;
//                }
//            }
//            Random rd = new Random();
            for (int j = 0; j < uvalue.length; j++) {
                feature[position] = uvalue[j];
//                feature[position] = rd.nextDouble();
                position++;
            }

//            for(int j=0;j<uvalue_null.length;j++){
//                feature[position] = uvalue_null[j];
//                position++;
//            }
            res.add(feature);
        }
        return res;
    }
    public ArrayList<double[]> ExtractBooleanUvalue(ArrayList<String> amino, int width, boolean pos,
            Matrix dsm, double thres) throws FileNotFoundException, IOException {
        ArrayList<double[]> res = new ArrayList<double[]>();
        this.AdjustLength();
        ArrayList<Integer> BindingIdx = this.MyKeyProtein.getBindingIndex();
        if (BindingIdx == null) {
            System.err.println("No binding index");
            System.exit(1);
        }
        int offset = this.MyKeyProtein.getOffset();
        System.out.println("Protein length: " + this.MyKeyProtein.getSequence().length());
        int radius = (width - 1) / 2;
//        double[][] m = new double[20][this.MyKeyProtein.getSequence().length()];
//        int[] TotalCount = new int[20];
        ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
        System.out.println("Lst_Column: " + Lst_ColumnPair.size());

        Matrix MatPSSM = CreatePSSMList(Lst_ColumnPair, amino);
        

        for (int i = (width - 1) / 2; i < Lst_ColumnPair.size() - (width - 1) / 2 - 1; i++) {
            if (pos) {
//                if (BindingIdx.indexOf(i+offset) < 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) < 0) {
//                if(this.MyKeyProtein.IsNearBindingResidual(i, (width-1)/2)){
                    continue;
                }
            } else {
//                if (BindingIdx.indexOf(i+offset) >= 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) >= 0) {
                    continue;
                }
            }
            // PSSM
//            int len = width*20 + width*(width-1)/2;
//            int len = width * 20;
            int len = width*(width-1)/2;
            double[] feature = new double[len];


//            Matrix pssm = MatPSSM.getMatrix(0, 19, i - radius, i + radius);
//            double[][] VecPSSM = pssm.getArrayCopy();
            //////     double[] uvalue_null = this.CalculateUvalueForSlidingWin(Lst_ColumnPair, i-radius, i+radius+1, null);
//            double thres = this.CalculateThreshold(dsm, 0.1);
            double[] uvalue = this.CalculateUvalueForSlidingWin(Lst_ColumnPair, i - radius, i + radius + 1, dsm, thres);
            int position = 0;
//            for(int j=0; j<pssm.length;j++){
//                feature[position] = pssm[j];
//                position++;
//            }
//            for (int j = 0; j < VecPSSM[0].length; j++) {
//                for (int k = 0; k < VecPSSM.length; k++) {
//                    feature[position] = VecPSSM[k][j];
//                    position++;
//                }
//            }
//            Random rd = new Random();
            for (int j = 0; j < uvalue.length; j++) {
                feature[position] = uvalue[j];
//                feature[position] = rd.nextDouble();
                position++;
            }

//            for(int j=0;j<uvalue_null.length;j++){
//                feature[position] = uvalue_null[j];
//                position++;
//            }
            res.add(feature);
        }
        return res;
    }
    public ArrayList<double[]> ExtractPSSMBooleanUvalue(int width, boolean pos,
            Matrix dsm, double thres, boolean useNative, String dir_pssm) throws FileNotFoundException, IOException {
        ArrayList<double[]> res = new ArrayList<double[]>();
        this.AdjustLength();
        ArrayList<Integer> BindingIdx = this.MyKeyProtein.getBindingIndex();
        if (BindingIdx == null) {
            System.err.println("No binding index");
            System.exit(1);
        }
        int offset = this.MyKeyProtein.getOffset();
        System.out.println("Protein length: " + this.MyKeyProtein.getSequence().length());
        int radius = (width - 1) / 2;
//        double[][] m = new double[20][this.MyKeyProtein.getSequence().length()];
//        int[] TotalCount = new int[20];
        ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
        System.out.println("Lst_Column: " + Lst_ColumnPair.size());

        Matrix MatPSSM;// = CreatePSSMList(Lst_ColumnPair, amino);
        if (!useNative) {

            ArrayList<String> amino = AminoAcid.getAA();
            MatPSSM = CreatePSSMList(Lst_ColumnPair, amino);


        } else {
            String name = this.MyKeyProtein.getName() + "_" + this.MyKeyProtein.getChain();
            PSSM p = new PSSM(name, dir_pssm);
            MatPSSM = p.GetPSSM_Logistic();
        }

        for (int i = (width - 1) / 2; i < Lst_ColumnPair.size() - (width - 1) / 2 - 1; i++) {
            if (pos) {
//                if (BindingIdx.indexOf(i+offset) < 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) < 0) {
//                if(this.MyKeyProtein.IsNearBindingResidual(i, (width-1)/2)){
                    continue;
                }
            } else {
//                if (BindingIdx.indexOf(i+offset) >= 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) >= 0) {
                    continue;
                }
            }
            // PSSM
            int len = width*20 + width*(width-1)/2;
//            int len = width * 20;
//            int len = width*(width-1)/2;
            double[] feature = new double[len];


            Matrix pssm = MatPSSM.getMatrix(0, 19, i - radius, i + radius);
            double[][] VecPSSM = pssm.getArrayCopy();
            //////     double[] uvalue_null = this.CalculateUvalueForSlidingWin(Lst_ColumnPair, i-radius, i+radius+1, null);
//            double thres = this.CalculateThreshold(dsm, 0.1);
            double[] uvalue = this.CalculateUvalueForSlidingWin(Lst_ColumnPair, i - radius, i + radius + 1, dsm, thres);
            int position = 0;
//            for(int j=0; j<pssm.length;j++){
//                feature[position] = pssm[j];
//                position++;
//            }
            for (int j = 0; j < VecPSSM[0].length; j++) {
                for (int k = 0; k < VecPSSM.length; k++) {
                    feature[position] = VecPSSM[k][j];
                    position++;
                }
            }
//            Random rd = new Random();
            for (int j = 0; j < uvalue.length; j++) {
                feature[position] = uvalue[j];
//                feature[position] = rd.nextDouble();
                position++;
            }

//            for(int j=0;j<uvalue_null.length;j++){
//                feature[position] = uvalue_null[j];
//                position++;
//            }
            res.add(feature);
        }
        return res;
    }
    

    public ArrayList<double[]> ExtractPSSM(boolean useNative, int width, String dir_pssm, boolean pos) throws FileNotFoundException, IOException {
        ArrayList<double[]> res = new ArrayList<double[]>();

        Matrix MatPSSM;
        ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
        ArrayList<Integer> BindingIdx = this.MyKeyProtein.getBindingIndex();
        if (!useNative) {

            ArrayList<String> amino = AminoAcid.getAA();
            MatPSSM = CreatePSSMList(Lst_ColumnPair, amino);


        } else {
            String name = this.MyKeyProtein.getName() + "_" + this.MyKeyProtein.getChain();
            PSSM p = new PSSM(name, dir_pssm);
            MatPSSM = p.getMy_PSSM();
        }
//        ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
        System.out.println("Mat: " + MatPSSM.getRowDimension() + " : " + MatPSSM.getColumnDimension());
        for (int i = (width - 1) / 2; i < Lst_ColumnPair.size() - (width - 1) / 2 - 1; i++) {
            if (pos) {
//                if (BindingIdx.indexOf(i+offset) < 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) < 0) {
//                if(this.MyKeyProtein.IsNearBindingResidual(i, (width-1)/2)){
                    continue;
                }
            } else {
//                if (BindingIdx.indexOf(i+offset) >= 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) >= 0) {
                    continue;
                }
            }
//            int start = i - (width - 1) / 2;
//            int end = i + (width - 1) / 2;
//            System.out.println("start:end: " + start + " : "+end);
            Matrix pssm = MatPSSM.getMatrix(0, 19, i - (width - 1) / 2, i + (width - 1) / 2);
            double[][] VecPSSM = pssm.getArrayCopy();
//            System.out.println("VecPSSM: " + VecPSSM.length + " : " + VecPSSM[0].length);
            int position = 0;
            double[] feature = new double[20 * width];
            for (int j = 0; j < VecPSSM.length; j++) {
                for (int k = 0; k < VecPSSM[0].length; k++) {
                    feature[position] = VecPSSM[j][k];
                    position++;
                }
            }
            res.add(feature);
        }
        return res;
    }
    public ArrayList<double[]> ExtractPSSM_Pair(int width, boolean pos, ArrayList<String> amino) throws FileNotFoundException, IOException {
        ArrayList<double[]> res = new ArrayList<double[]>();

        
        ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
        ArrayList<Integer> BindingIdx = this.MyKeyProtein.getBindingIndex();
        int radius = (width - 1) / 2;
        int len = width*radius*400;
        ArrayList<MyPair> lst_mp = this.CreatePSSM_Pair(Lst_ColumnPair, amino);
        for (int i = (width - 1) / 2; i < Lst_ColumnPair.size() - (width - 1) / 2 - 1; i++) {
            if (pos) {
//                if (BindingIdx.indexOf(i+offset) < 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) < 0) {
//                if(this.MyKeyProtein.IsNearBindingResidual(i, (width-1)/2)){
                    continue;
                }
            } else {
//                if (BindingIdx.indexOf(i+offset) >= 0) {
                if (BindingIdx.indexOf(this.MyKeyProtein.GetAbsoluteIndex(i)) >= 0) {
                    continue;
                }
            }
//            
            double[] feature = new double[len];
            int position =0;
            for(int j = i-radius;j<i+radius;j++){
                for(int k=j+1; k<=i+radius;k++){
                    for(int s =0;s<lst_mp.size();s++){
                        if(lst_mp.get(s).getString1_Index()==j && lst_mp.get(s).getString2_Index()==k){
                            double[] val = lst_mp.get(s).getMyArray();
                            System.arraycopy(val, 0, feature, position, val.length);
                            position = position + val.length;
                        }
                    }
                }
            }
            res.add(feature);
        }
        return res;
    }

//    public ArrayList<double[]> Extract
    public static double[] CreatePSSM(ArrayList<String> ColumnStr, ArrayList<String> amino) {
        int len = ColumnStr.size();
        double[] res = new double[20 * len];
        double[][] m = new double[20][len];
        double[] total = new double[20];
        for (int i = 0; i < ColumnStr.size(); i++) {
            String str = ColumnStr.get(i);
            ArrayList<Character> char_lst = new ArrayList<Character>();
            for (int j = 0; j < str.length(); j++) {
                char_lst.add(str.charAt(j));
            }
            int sum = 0;
            for (int j = 0; j < 20; j++) {
                int count = Collections.frequency(char_lst, amino.get(j).charAt(0));
                m[j][i] = (double) count;
                sum += count;
            }
            for (int j = 0; j < 20; j++) {
                m[j][i] = m[j][i] / sum;
                total[j] += m[j][i];
            }
        }
        int count = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < len; j++) {
                m[i][j] = m[i][j] * len / total[i];
                m[i][j] = m[i][j] / (m[i][j] + 1);
                res[count] = m[i][j];
                if (Double.isNaN(res[count])) {
                    System.err.println("NaN");
                    res[count] = 0.0;
                }
                count++;
            }

        }
        return res;
    }

    public Matrix CreatePSSMList(ArrayList<String> ColumnStr, ArrayList<String> amino) {
        try {
            int len = ColumnStr.size();
//        double[] res = new double[20 * len];
            double[][] m = new double[20][len];
            double[] total = new double[20];
            for (int i = 0; i < ColumnStr.size(); i++) {
                String str = ColumnStr.get(i);
                ArrayList<Character> char_lst = new ArrayList<Character>();
                for (int j = 0; j < str.length(); j++) {
                    char_lst.add(str.charAt(j));
                }
                int sum = 0;
                for (int j = 0; j < 20; j++) {
                    int count = Collections.frequency(char_lst, amino.get(j).charAt(0));
                    m[j][i] = (double) count;
                    sum += count;
                }
                for (int j = 0; j < 20; j++) {
                    m[j][i] = m[j][i] / sum;
                    total[j] += m[j][i];
                }
            }
//        int count = 0;
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < len; j++) {
                    //                 System.out.println("i/j: " + i + " / " + j);
                    m[i][j] = m[i][j] * len / total[i];
                    m[i][j] = m[i][j] / (m[i][j] + 1);
//                res[count] = m[i][j];
                    if (Double.isNaN(m[i][j])) {
                        System.err.println("NaN");
//                    res[count] = 0.0;
                        m[i][j] = 0.0;
                    }
//                count++;
                }

            }
            return new Matrix(m);
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }
    public ArrayList<MyPair> CreatePSSM_Pair(ArrayList<String> ColumnStr, ArrayList<String> amino){
        // count overall pair occurences
        double[] overall_freq = new double[amino.size()*amino.size()];
        int sum =0;
        for(int i=0;i<ColumnStr.size()-1;i++){
            String s1 = ColumnStr.get(i);
            for(int j=i+1; j<ColumnStr.size();j++){
                String s2 = ColumnStr.get(j);
                for(int k=0;k<s1.length();k++){
                    if(amino.indexOf(s1.substring(k, k+1))<0 ||amino.indexOf(s2.substring(k, k+1))<0){
                        continue;
                    }
                    int idx = amino.indexOf(s1.substring(k, k+1))*20 + amino.indexOf(s2.substring(k, k+1));
                    overall_freq[idx]++;
                    sum++;
                }
            }
        }
        sum =0;
        double epsilon = 0.1;
        for(int i=0;i<overall_freq.length;i++){
            overall_freq[i] += epsilon;
            overall_freq[i] = overall_freq[i]/(sum+ epsilon*400);
        }
        // 
        ArrayList<MyPair> res = new ArrayList<MyPair>();
        for(int i=0;i<ColumnStr.size()-1;i++){
            String s1 = ColumnStr.get(i);
            for(int j=i+1; j<ColumnStr.size();j++){
                String s2 = ColumnStr.get(j);
                double[] val = new double[amino.size()*amino.size()];
                MyPair mp = new MyPair(i, j, s1, s2);
                sum = 0;
                for(int k=0;k<s1.length();k++){
                    
                    
                    if(amino.indexOf(s1.substring(k, k+1))<0 ||amino.indexOf(s2.substring(k, k+1))<0){
                        continue;
                    }
                    int idx = amino.indexOf(s1.substring(k, k+1))*20 + amino.indexOf(s2.substring(k, k+1));
                    val[idx]++;
                    sum++;
                }
                for(int k=0;k<val.length;k++){
                    val[k] = (val[k] + epsilon)/(sum + epsilon*400);
                    val[k] = val[k]/overall_freq[k];
                }
                mp.setMyArray(val);
                res.add(mp);
            }
        }
        return res;
    }

    public static double[] CreateOBV(String str) {
        ArrayList<String> type1 = new ArrayList<String>();
        ArrayList<String> type2 = new ArrayList<String>();
        ArrayList<String> type3 = new ArrayList<String>();
        ArrayList<String> type4 = new ArrayList<String>();
        ArrayList<String> type5 = new ArrayList<String>();
        ArrayList<String> type6 = new ArrayList<String>();
        type1.add("A");
        type1.add("G");
        type1.add("V");
        type2.add("I");
        type2.add("L");
        type2.add("F");
        type2.add("P");
        type3.add("Y");
        type3.add("M");
        type3.add("T");
        type3.add("S");
        type3.add("C");
        type4.add("H");
        type4.add("N");
        type4.add("Q");
        type4.add("W");
        type5.add("K");
        type5.add("R");
        type6.add("D");
        type6.add("E");
        double[] res = new double[6 * str.length()];
        int position = -1;
        for (int i = 0; i < str.length(); i++) {
            String c = str.substring(i, i + 1);
            if (type1.indexOf(c) >= 0) {
                res[position + 1] = 1.0;
            } else if (type2.indexOf(c) >= 0) {
                res[position + 2] = 1.0;
            } else if (type3.indexOf(c) >= 0) {
                res[position + 3] = 1.0;
            } else if (type4.indexOf(c) >= 0) {
                res[position + 4] = 1.0;
            } else if (type5.indexOf(c) >= 0) {
                res[position + 5] = 1.0;
            } else if (type6.indexOf(c) >= 0) {
                res[position + 6] = 1.0;
            }
            position = position + 6;
        }
        return res;
    }

    public static Matrix CreateOBVList(String str) {
        ArrayList<String> type1 = new ArrayList<String>();
        ArrayList<String> type2 = new ArrayList<String>();
        ArrayList<String> type3 = new ArrayList<String>();
        ArrayList<String> type4 = new ArrayList<String>();
        ArrayList<String> type5 = new ArrayList<String>();
        ArrayList<String> type6 = new ArrayList<String>();
        type1.add("A");
        type1.add("G");
        type1.add("V");
        type2.add("I");
        type2.add("L");
        type2.add("F");
        type2.add("P");
        type3.add("Y");
        type3.add("M");
        type3.add("T");
        type3.add("S");
        type3.add("C");
        type4.add("H");
        type4.add("N");
        type4.add("Q");
        type4.add("W");
        type5.add("K");
        type5.add("R");
        type6.add("D");
        type6.add("E");
        double[][] mat = new double[6][str.length()];
        for (int i = 0; i < str.length(); i++) {
            String c = str.substring(i, i + 1);
            if (type1.indexOf(c) >= 0) {
                mat[0][i] = 1.0;
            } else if (type2.indexOf(c) >= 0) {
                mat[1][i] = 1.0;
            } else if (type3.indexOf(c) >= 0) {
                mat[2][i] = 1.0;
            } else if (type4.indexOf(c) >= 0) {
                mat[3][i] = 1.0;
            } else if (type5.indexOf(c) >= 0) {
                mat[4][i] = 1.0;
            } else if (type6.indexOf(c) >= 0) {
                mat[5][i] = 1.0;
            }
        }
        return new Matrix(mat);
    }

    public Matrix CreateSSList() throws FileNotFoundException, IOException {
        ArrayList<Integer> helix = this.MyKeyProtein.FindHelixIndex();
        ArrayList<Integer> strand = this.MyKeyProtein.FindStrandIndex();
        String prot = this.MyKeyProtein.getSequence();
        double[][] mat = new double[3][prot.length()];
        for (int i = 0; i < prot.length(); i++) {
            if (helix.indexOf(i) >= 0) {
                mat[0][i] = 1.0;
            } else if (strand.indexOf(i) >= 0) {
                mat[1][i] = 1.0;
            } else {
                mat[2][i] = 1.0;
            }
        }
        return new Matrix(mat);
    }

    public static double[] ConcatFeature(double[] a, double[] b) {
        int len_a = a.length;
        int len_b = b.length;
        int position = 0;
        double[] res = new double[len_a + len_b];
        for (int i = 0; i < len_a; i++) {
            res[position] = a[i];
            position++;
        }
        for (int i = 0; i < len_b; i++) {
            res[position] = b[i];
            position++;
        }
        return res;
    }

    public ArrayList<Integer> ScoreSignificantValueOfPair(ArrayList<String> amino, int width, Matrix dsm, ArrayList<String> lst_cols) throws IOException {
//        ArrayList<String> msa = this.getLstSeqs();
        ArrayList<String> msa = this.RetrieveColumnPair();
        double percent_pair = 0.20;
        double percent_index = 0.20;
        this.AdjustLength();
        int seq_len = this.MyKeyProtein.getSequence().length();
        ArrayList<MyPair> lst_pair = new ArrayList<MyPair>();
        ArrayList<Double> lst_score = new ArrayList<Double>();
        for (int i = 0; i < seq_len - 1; i++) {
            if (IsConservativeOrGap(i, lst_cols)) {
                System.err.println("Conservative or gap: " + lst_cols.get(i));
                continue;
            }
            for (int j = i + 1; j <= i + 10 && j < seq_len; j++) {
                if (IsConservativeOrGap(j, lst_cols)) {
                    continue;
                }
                MyPair m = new MyPair(i, j, msa.get(i), msa.get(j));
                lst_pair.add(m);
                lst_score.add(m.CalculateUAlphaValue(dsm));
            }
        }
        double[] arr = new double[lst_score.size()];
        for (int i = 0; i < lst_score.size(); i++) {
            arr[i] = lst_score.get(i);
        }
        GlobalVar gv = new GlobalVar();
        ArrayList<Double> lst_double = SignificantFinder.SignificanceFinder.process(arr, 0.01, "", gv);
        MyIO.WriteToFile_Double("BLAST_Database/Test/MSA/" + this.MyKeyProtein.getName() + "_" + this.MyKeyProtein.getChain() + ".txt", lst_score);
        // randomize
        int c = 0;
        while (c < lst_pair.size()) {
            Random a = new Random();
            int idx1 = a.nextInt(lst_pair.size());
            int idx2 = a.nextInt(lst_pair.size());
            if (idx1 != idx2) {
                Collections.swap(lst_pair, idx1, idx2);
                Collections.swap(lst_score, idx1, idx2);
                c++;
            }
        }
        // sort lst according to its U-alpha value
        for (int i = 0; i < lst_pair.size() - 1; i++) {
            for (int j = i + 1; j < lst_pair.size(); j++) {
                if (lst_score.get(i) < lst_score.get(j)) {
                    Collections.swap(lst_score, i, j);
                    Collections.swap(lst_pair, i, j);
                }
            }
        }
        // take top 15%, and count the occurence of column
        ArrayList<Integer> significant_col = new ArrayList<Integer>();
        ArrayList<Integer> significant_count = new ArrayList<Integer>();
        ArrayList<Double> significant_score = new ArrayList<Double>();
        for (int i = 0; i < lst_pair.size() * percent_pair; i++) {
            Integer tmp1 = lst_pair.get(i).getString1_Index();
            Integer tmp2 = lst_pair.get(i).getString2_Index();
            Double score = lst_score.get(i);
            //
            int idx1 = significant_col.indexOf(tmp1);
            int idx2 = significant_col.indexOf(tmp2);
            if (idx1 >= 0) {
                if (significant_score.get(idx1) < score) {
                    significant_score.set(idx1, score);
                }
                int count = significant_count.get(idx1) + 1;
                significant_count.set(idx1, count);
            } else {
                significant_col.add(tmp1);
                significant_score.add(score);
                significant_count.add(1);
            }
            // idx 2
            if (idx2 >= 0) {
                if (significant_score.get(idx2) < score) {
                    significant_score.set(idx2, score);
                }
                int count = significant_count.get(idx2) + 1;
                significant_count.set(idx2, count);
            } else {
                significant_col.add(tmp2);
                significant_score.add(score);
                significant_count.add(1);
            }


        }
        // sort according to the occurence
        for (int i = 0; i < significant_count.size() - 1; i++) {
            for (int j = i + 1; j < significant_count.size(); j++) {
                if (significant_count.get(i) < significant_count.get(j)) {
                    Collections.swap(significant_count, i, j);
                    Collections.swap(significant_col, i, j);
                    Collections.swap(significant_score, i, j);
                }
            }
        }
        // print for review
//        int offset = this.MyKeyProtein.getOffset();
//        for(int i=0;i<significant_col.size()*percent_index;i++){
//            int start = significant_col.get(i) + offset;
//            int count = significant_count.get(i);
//            double score = significant_score.get(i);
//            System.out.println("Col idx: "+ start + " count: "+ count + " score: "+ score);
//        }
        // print

//        for(int i=0;i<lst_pair.size();i++){
//            int start = lst_pair.get(i).getString1_Index() + offset;
//            int end = lst_pair.get(i).getString2_Index() + offset;
//            System.out.println("["+start+":"+end+"] : " + lst_score.get(i));
//        }
        int t = (int) (significant_col.size() * percent_index);
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i <= t; i++) {
            res.add(significant_col.get(i));
        }
        return res;
    }

    public MyEvaluate Evaluate(ArrayList<Integer> lst, ArrayList<String> lst_cols) { // lst of index without adjusting offset
        ArrayList<Integer> BindingIndex = this.MyKeyProtein.getBindingIndex();
        int offset = this.MyKeyProtein.getOffset();
        // find nearby binding sites, with distance d = 5.
        int distance = 5;
        ArrayList<Integer> Neighbor = new ArrayList<Integer>();
        int len = this.MyKeyProtein.getSequence().length();
        System.out.println("Neighbor:");
        for (int i = 0; i < len; i++) {
            if (this.MyKeyProtein.IsNearBindingResidual(i + offset, distance) && !IsConservativeOrGap(i, lst_cols)) {
                int tmp = i + offset;
                Neighbor.add(tmp);
//                System.out.print(tmp + " , ");
            }
        }
        System.out.println();
        // 
        // find true positive
        int tp = 0, tn = 0, fp = 0, fn = 0;
        for (int i = 0; i < lst.size(); i++) {
            if (Neighbor.indexOf(lst.get(i) + offset) >= 0) {
                tp++;
            } else {
                fp++;
            }
        }
        for (int i = 0; i < Neighbor.size(); i++) {
            if (lst.indexOf(Neighbor.get(i) - offset) < 0) {
                fn++;
            }
        }
        tn = this.MyKeyProtein.getSequence().length() - tp - fn - fp;
        System.out.println("True positive: " + tp);
        System.out.println("True negative: " + tn);
        System.out.println("False positive: " + fp);
        System.out.println("False negative: " + fn);
        return new MyEvaluate(tp, tn, fp, fn);
    }

    public ArrayList<int[]> RetrieveIndicatorPair2(int distance, ArrayList<String> lst_cols) {
        // define the distance of neighborhood
        ArrayList<int[]> res = new ArrayList<int[]>();

        int offset = this.MyKeyProtein.getOffset();
        for (int i = 0; i < this.MyKeyProtein.getSequence().length() - 1; i++) {
            int idx_adjust = this.MyKeyProtein.GetAbsoluteIndex(i);
//            if(this.MyKeyProtein.IsNearBindingResidual(i+offset, distance)){
            if (this.MyKeyProtein.IsNearBindingResidual(idx_adjust, distance)) {
                if (this.IsConservativeOrGap(i, lst_cols)) {
                    continue;
                }
                for (int j = i + 1; j <= i + 2 * distance; j++) {
                    if (j >= this.MyKeyProtein.getSequence().length() - 1) {
                        break;
                    }
                    if (this.MyKeyProtein.IsNearBindingResidual(j + offset, distance)) {
//                        if(this.IsConservativeOrGap(j, lst_cols)){
                        if (this.IsConservativeOrGap(this.MyKeyProtein.GetAbsoluteIndex(j), lst_cols)) {
                            continue;
                        }
                        res.add(new int[]{i, j});
                    }
                }
            }
        }
        // adjust the offset
//        ArrayList<Integer> Binding_Offset = new ArrayList<Integer>();
//        for (Integer i : this.getBindingIndex()) {
//            Binding_Offset.add(i - this.Offset);
//        }
        // end
        // find the neighbor with binding residual
//        for (Integer index : Binding_Offset) {
//            ArrayList<Integer> IndexAndNearby = new ArrayList<Integer>();
//            for (int k = index - distance; k <= index + distance; k++) {
//                if (k >= 0 && k < this.Sequence.length()) {
//                    IndexAndNearby.add(k);
//                }
//            }
//            boolean flag = true;
//            ArrayList<int[]> IndexOfPair = Combination(IndexAndNearby);
//            for (int[] tmp : IndexOfPair) {
//                for (int[] v : res) {
//                    if ((tmp[0] == v[0] && tmp[1] == v[1]) || (tmp[0] == v[1] && tmp[1] == v[0])) {
//                        flag = false;
//                        break;
//                    }
//
//                }
//                if (flag) {
//                    res.add(tmp);
//                }
//            }
//        }
        return res;
    }

    public boolean IsConservativeOrGap(int idx, ArrayList<String> lst_cols) { // index of column which is tested if conservative
        //      ArrayList<String> lst_cols = this.RetrieveColumnPair();
        ArrayList<String> aa = AminoAcid.getAA_Abbr();
        int len = lst_cols.get(0).length();

        for (String s : aa) {
            int f = Collections.frequency(lst_cols, s);
            if ((double) f / len >= 0.90) {
                return true;
            }
        }
        int g = Collections.frequency(lst_cols, "-");
        if ((double) g / len >= 0.25) {
            return true;
        }
        return false;
    }

    public ArrayList<int[]> RetrieveNullIndex2(int distance, boolean neighbor, ArrayList<String> lst_cols) {

        ArrayList<int[]> res = new ArrayList<int[]>();
        int offset = this.MyKeyProtein.getOffset();
        ArrayList<Integer> lst_idx = new ArrayList<Integer>();
        for (int i = 0; i < this.MyKeyProtein.getSequence().length(); i++) {
//            if(!this.MyKeyProtein.IsNearBindingResidual(i+offset, distance)){
            if (!this.MyKeyProtein.IsNearBindingResidual(this.MyKeyProtein.GetAbsoluteIndex(i), distance)) {
                if (this.IsConservativeOrGap(i, lst_cols)) {
                    continue;
                }
                lst_idx.add(i);
            }
        }
        res = KeyProtein.Combination(lst_idx);
        if (neighbor) {
            for (int i = res.size() - 1; i >= 0; i--) {
                int[] a = res.get(i);
                if (Math.abs(a[1] - a[0]) > 2 * distance) {
                    res.remove(i);
                }
            }
        }
        return res;
    }

    public ArrayList<double[]> CreateFeature_PSSM(boolean nativePSSM, int width, String dir) throws FileNotFoundException, IOException {
        ArrayList<double[]> res = new ArrayList<double[]>();
        Matrix MatPSSM;
        if (!nativePSSM) {
            ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
            ArrayList<String> amino = AminoAcid.getAA();
            MatPSSM = CreatePSSMList(Lst_ColumnPair, amino);

        } else {
            String name = this.MyKeyProtein.getName() + "_" + this.MyKeyProtein.getChain();
            String dir_pssm = dir + "PSSM/";
            PSSM p = new PSSM(name, dir_pssm);
            MatPSSM = p.getMy_PSSM();
        }

        for (int i = 0; i <= this.MyKeyProtein.getSequence().length() - width; i++) {
            Matrix pssm = MatPSSM.getMatrix(0, 19, i, i + width - 1);
            double[][] VecPSSM = pssm.getArrayCopy();
            double[] d = new double[20 * width];
            int position = 0;
            for (int j = 0; j < VecPSSM.length; j++) {
                for (int k = 0; k < VecPSSM[0].length; k++) {
                    d[position] = VecPSSM[j][k];
                    position++;
                }
            }
            res.add(d);
        }
        return res;
    }

    public double CalculateThreshold(Matrix dssm, double percent) {
        ArrayList<String> lst_col = this.RetrieveColumnPair();
        ArrayList<Double> lst_uvalue = new ArrayList<Double>();
        for (int i = 0; i < lst_col.size() - 1; i++) {
            for (int j = i + 1; j < lst_col.size(); j++) {
                MyPair mp = new MyPair(lst_col.get(i), lst_col.get(j));
                double d = mp.CalculateUAlphaValue(dssm);
                lst_uvalue.add(d);
            }
        }
        //
        Collections.sort(lst_uvalue);
        Collections.reverse(lst_uvalue);
        int idx = (int) (lst_uvalue.size() * percent);
        return lst_uvalue.get(idx);
    }

    public double[] CalculateThreshold(Matrix dssm, double[] percent) {
        ArrayList<String> lst_col = this.RetrieveColumnPair();
        ArrayList<Double> lst_uvalue = new ArrayList<Double>();
        for (int i = 0; i < lst_col.size() - 1; i++) {
            for (int j = i + 1; j < lst_col.size(); j++) {
                MyPair mp = new MyPair(lst_col.get(i), lst_col.get(j));
                double d = mp.CalculateUAlphaValue(dssm);
                lst_uvalue.add(d);
            }
        }
        //
        Collections.sort(lst_uvalue);
        Collections.reverse(lst_uvalue);
        double[] res = new double[percent.length];
        for (int i = 0; i < percent.length; i++) {
            int idx = (int) (lst_uvalue.size() * percent[i]);
            res[i] = lst_uvalue.get(idx);
        }
        return res;
    }
//    public boolean IsGapped(int idx){
}
