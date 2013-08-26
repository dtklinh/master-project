/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import Jama.Matrix;
import Method.MyPair;
import MyDivergence.MyEntropy;
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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
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
        this.AdjustLength();
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

    public ArrayList<double[]> CalculatePSSMAndSS(ArrayList<String> amino, int width, boolean pos) throws FileNotFoundException, IOException {
       
        ArrayList<double[]> res = new ArrayList<double[]>();
        this.AdjustLength();
        ArrayList<Integer> BindingIdx = this.MyKeyProtein.getBindingIndex();
        if (BindingIdx == null) {
            System.err.println("No binding index");
            System.exit(1);
        }
        System.out.println("Protein length: "+ this.MyKeyProtein.getSequence().length());
        int radius = (width-1)/2;
//        double[][] m = new double[20][this.MyKeyProtein.getSequence().length()];
//        int[] TotalCount = new int[20];
        ArrayList<String> Lst_ColumnPair = this.RetrieveColumnPair();
        System.out.println("Lst_Column: "+ Lst_ColumnPair.size());
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
        System.out.println("MatOBV: "+MatOBV.getRowDimension()+":"+ MatOBV.getColumnDimension());
        System.out.println("MatSS: "+MatSS.getRowDimension()+":"+ MatSS.getColumnDimension());
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
            double[] feature = new double[width*29];
//            ArrayList<String> tmp = (ArrayList<String>)Lst_ColumnPair.subList(i-radius, i+radius+1);
            ArrayList<String> tmp = new ArrayList<String>();
            for(int j=i-radius; j<i+radius+1; j++){
                tmp.add(Lst_ColumnPair.get(j));
            }
            double[] pssm = CreatePSSM(tmp, amino);
            // Secondary Structure
            System.out.println("i: "+ i + "  // Radius: "+ radius);
            Matrix ss = MatSS.getMatrix(0, 2, i-radius, i+radius);
            double[][] VecSS = ss.getArrayCopy();
            // OBV
            Matrix obv = MatOBV.getMatrix(0, 5, i-radius, i+ radius);
            double[][] VecOBV = obv.getArrayCopy();
            int position = 0;
            for(int j=0; j<pssm.length;j++){
                feature[position] = pssm[j];
                position++;
            }
            for(int j=0;j<VecSS[0].length;j++){
                for(int k=0;k<VecSS.length;k++){
                    feature[position] = VecSS[k][j];
                    position++;
                }
            }
            for(int j=0;j<VecOBV[0].length;j++){
                for(int k=0;k<VecOBV.length;k++){
                    feature[position] = VecOBV[k][j];
                    position++;
                }
            }
            res.add(feature);
        }
        return res;
        
    }

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
                m[i][j] = m[i][j] * len / total[j];
                m[i][j] = m[i][j] / (m[i][j] + 1);
                res[count] = m[i][j];
                count++;
            }

        }
        return res;
    }
    public static double[] CreateOBV(String str){
        ArrayList<String> type1 = new ArrayList<String>();
        ArrayList<String> type2 = new ArrayList<String>();
        ArrayList<String> type3 = new ArrayList<String>();
        ArrayList<String> type4 = new ArrayList<String>();
        ArrayList<String> type5 = new ArrayList<String>();
        ArrayList<String> type6 = new ArrayList<String>();
        type1.add("A"); type1.add("G"); type1.add("V");
        type2.add("I"); type2.add("L"); type2.add("F"); type2.add("P");
        type3.add("Y");type3.add("M");type3.add("T");type3.add("S");type3.add("C");
        type4.add("H"); type4.add("N"); type4.add("Q"); type4.add("W");
        type5.add("K"); type5.add("R");
        type6.add("D"); type6.add("E");
        double[] res = new double[6*str.length()];
        int position =-1;
        for(int i=0; i<str.length(); i++){
            String c = str.substring(i, i+1);
            if(type1.indexOf(c)>=0){
                res[position+1] = 1.0;
            }
            else if(type2.indexOf(c)>=0){
                res[position+2] = 1.0;
            }
            else if(type3.indexOf(c)>=0){
                res[position+3] = 1.0;
            }
            else if(type4.indexOf(c)>=0){
                res[position+4] = 1.0;
            }
            else if(type5.indexOf(c)>=0){
                res[position+5] = 1.0;
            }
            else if(type6.indexOf(c)>=0){
                res[position+6] = 1.0;
            }
            position = position+6;
        }
        return res;
    }
    public static Matrix CreateOBVList(String str){
        ArrayList<String> type1 = new ArrayList<String>();
        ArrayList<String> type2 = new ArrayList<String>();
        ArrayList<String> type3 = new ArrayList<String>();
        ArrayList<String> type4 = new ArrayList<String>();
        ArrayList<String> type5 = new ArrayList<String>();
        ArrayList<String> type6 = new ArrayList<String>();
        type1.add("A"); type1.add("G"); type1.add("V");
        type2.add("I"); type2.add("L"); type2.add("F"); type2.add("P");
        type3.add("Y");type3.add("M");type3.add("T");type3.add("S");type3.add("C");
        type4.add("H"); type4.add("N"); type4.add("Q"); type4.add("W");
        type5.add("K"); type5.add("R");
        type6.add("D"); type6.add("E");
        double[][] mat = new double[6][str.length()];
        for(int i=0;i<str.length();i++){
            String c = str.substring(i, i+1);
            if(type1.indexOf(c)>=0){
                mat[0][i] = 1.0;
            }
            else if(type2.indexOf(c)>=0){
                mat[1][i] = 1.0;
            }
            else if(type3.indexOf(c)>=0){
                mat[2][i] = 1.0;
            }
            else if(type4.indexOf(c)>=0){
                mat[3][i] = 1.0;
            }
            else if(type5.indexOf(c)>=0){
                mat[4][i] = 1.0;
            }
            else if(type6.indexOf(c)>=0){
                mat[5][i] = 1.0;
            }
        }
        return new Matrix(mat);
    }
    public Matrix CreateSSList() throws FileNotFoundException, IOException{
        ArrayList<Integer> helix = this.MyKeyProtein.FindHelixIndex();
        ArrayList<Integer> strand = this.MyKeyProtein.FindStrandIndex();
        String prot = this.MyKeyProtein.getSequence();
        double[][] mat = new double[3][prot.length()];
        for(int i=0;i<prot.length();i++){
            if(helix.indexOf(i)>=0){
                mat[0][i] = 1.0;
            }
            else if(strand.indexOf(i)>=0){
                mat[1][i] = 1.0;
            }
            else{
                mat[2][i] = 1.0;
            }
        }
        return new Matrix(mat);
    }
}
