/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package getproteinname;

import Components.ARRF_Template;
import Components.AminoAcid;
import PDBFile.DistanceFinder;
import Components.KeyProtein;
import Components.MSA;
import Components.MyBlackbox;
import Components.MyDraw;
import MyDivergence.MyEntropy;
import Components.Transformation;
import Method.MyPair;
import PDBFile.Test;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
//import JavaMI.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DebugGraphics;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.util.PDFTextStripper;
import Components.MsaFilterer;

/**
 *
 * @author linh
 */
public class GetProteinName {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        //////////////// main part //////////////////////
/*        
        ArrayList<String> msa = MsaFilterer.filter("MSA_file\\2C62_A.fasta.msa");
         if(msa.size()<10){
         System.out.println("MSA is too short");
         System.exit(1);
         }
//                 MSA m = new MSA("MSA_file\\2C62_A.fasta.msa", "index_file\\2C62_AB_index.txt");
         KeyProtein key = MSA.FindBindingPosition("pdf_file\\2C62.pdf", "AB", 62);
//         KeyProtein key = new KeyProtein("3KOS", "A", 0);
         //key.PrintOut("index_file\\2FQZ_ABCD_index.txt");
         MSA m = new MSA(key,msa);
         //     m.PrintToHTML("2HAN_A_out.html");
         //     m = m.MakeUnique();
 //        m.PrintToHTML("2HAN_A_out_unique.html");
         double[] entropy = m.CalculateEntropy();
//         for(int i=0;i<entropy.length;i++){
//         System.out.println("#"+ i +":\t "+entropy[i]);
//         }
         
         
         ArrayList<Double> val = new ArrayList<Double>();
         ArrayList<String> pos = new ArrayList<String>();
         for(int i=0;i<entropy.length;i++){
             val.add(entropy[i]);
             pos.add(String.valueOf(i));
         }
         MyDraw drw = new MyDraw("bar", pos, val);
         drw.setHighlightPosition(key.getBindingIndex());
         drw.AdjustColor();
         drw.PaintJFreeChart();
//         drw.PaintJFrame();
  */       
        ///////////////////End main part //////////////
        ///////////////// Test CTD part ////////////////
//        String s = "ABBABBBBBAAAABBBABABBBBBAA";
//        ArrayList<Character> lst = new ArrayList<Character>();
//        for(int i=0;i<s.length();i++){
//            lst.add(s.charAt(i));
//        }
//        double[] res = Transformation.CTD(lst);
//        for(int i=0;i<13;i++){
//            System.out.print(res[i] +"\t");
//        }
    ///////////////// End Test CTD part ////////////////
        //////////////Get feature vector //////////////
        // Read pos
//        ArrayList<double[]> pos_feature = MyBlackbox.FromFilenameToFeature("MSA_Pos_list.txt", 0.1, "Pos");
//        ArrayList<double[]> neg_feature = MyBlackbox.FromFilenameToFeature("MSA_Neg_list.txt", 0.1, "Neg");
//        System.out.println("#Pos: "+pos_feature.size());
//        System.out.println("#Neg: "+neg_feature.size());
//        ARRF_Template.WriteToArrfFile("Arrf_file.arff", "asd", pos_feature, neg_feature);
        //////////////End Get feature vector //////////////
//  
//        DistanceFinder.basicLoad("3FDQ_A");
//        Test.LoadFile();
        
//        KeyProtein k = new KeyProtein("1A0A", "A");
//        k.LoadingFromPDBFile();
//        k.PrintIndicatorPair(false,1);
//        k.PrintToScreen();
//        System.out.println(MyPair.ConvertFromStringToNum("ARMN"));
        
//        MyBlackbox.CalculateSig_NullMatrix("CompletePosList_2.txt");
        MyBlackbox.CalculateSum("CompletePosList_2.txt", "SignalMatrix");
//        MyBlackbox.TestSync("CompletePosList_2.txt");
//        System.out.println("1: "+MyPair.ConvertFromNumToString2(1));
        
  /*      
        PDDocument pddDocument = PDDocument.load(new File("2FQZ.pdf"));
        PDFTextStripper textStripper = new PDFTextStripper();
        String str = textStripper.getText(pddDocument);
        String[] lst = str.split("\n");
        for(int i=0;i<lst.length;i++){
            String[] token = lst[i].split(" ");
            for(int j=0;j<token.length;j++){
                System.out.println(token[j]);
            }
        }
       // System.out.println(str);
        pddDocument.close();
    */    

//        AminoAcid mino=new AminoAcid();
//        String s= "ad(A)Asn19823(A)";
//        System.out.println(mino.MatchPattern(s.trim(), "A"));
//        System.out.println(s.matches("\\w*Ala\\d+[(]A[)]"));

        
//        KeyProtein key = MSA.FindBindingPosition("2HAN.pdf", "B", 1);
//        key.PrintToScreen();

        // read file
//        String filename = "iDNA-Prot-DataSet_neg";
//        FileInputStream fstream = new FileInputStream(filename);
//        DataInputStream in = new DataInputStream(fstream);
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        String tmp="";
//        while(true){
//            String line = br.readLine();
//            if(line==null){
//                break;
//            }
//            if(line.indexOf(">")>=0){
//                tmp += line.substring(1,5) + "\n";
//            }
//        }
//        br.close();
//        // write to file
//        FileWriter writer = new FileWriter("name_neg.txt");
//        writer.write(tmp);
//        writer.close();
//        ArrayList<int[]> lst = new ArrayList<int[]>();
//        for(int i=0;i<10;i++){
//            int[] tmp = new int[2];
//            tmp[0] = i;
//            tmp[1] = i+1;
//            lst.add(tmp);
//        }
//        int[] aa = new int[2]; aa[0] = 0; aa[1] = 1;
//        System.out.println(lst.indexOf(aa));
    }
}
