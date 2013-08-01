/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

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
    public MSA(ArrayList<String> msa) throws FileNotFoundException, IOException{
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
        int min_length =10000;
        for(int i=0;i<num_seq;i++){
            if(LstSeqs.get(i).length()<min_length){
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
    public ArrayList<String> RetrieveColumnPair(){
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<String> lst_seq = this.getLstSeqs();
        int min = 10000;
        for(String s: lst_seq){
            s = s.trim();
            if(s.length()<min){
                min = s.length();
            }
        }
        for(int i=0;i<min;i++){
            String tmp = "";
            for(String s:lst_seq){
                tmp = tmp + String.valueOf(s.charAt(i));
            }
            res.add(tmp);
        }
        
        return res;
    }
    public void AdjustLength(){
        int len = this.MyKeyProtein.getSequence().trim().length();
        for(int i=0;i< this.LstSeqs.size();i++){
            String s = this.LstSeqs.get(i);
            if(s.trim().length()<len){
                while(s.trim().length()<len){
                    s = s + "-";
                }
                this.LstSeqs.set(i, s);
            }
        }
    }
}
