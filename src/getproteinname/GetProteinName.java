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
import Components.MyIO;
import Jama.Matrix;
import Support.Dsm;
import Support.StaticSupportMethod;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author linh
 */
public class GetProteinName {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
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
        
//        MyBlackbox.CalculateSig_NullMatrix("HSSP_Database/Train_AdjustIndex/AminoList374_New.txt");
//        String path = "Train/NullMatrix2";
//        MyBlackbox.CalculateSum(path+"/list.txt", path);
//        MyBlackbox.TestSync("HSSP_Database/Test/TS75.txt");
//        System.out.println("1: "+MyPair.ConvertFromNumToString2(1));
//        MyBlackbox.PrepareDataSet("CompletePosList_2.txt");
//        MyBlackbox.CalculateDSM2();
//        MyBlackbox.Test();
        String dir = "BLAST_Database/Train/";
        String list_dir = dir + "TR299.txt";
//        StaticSupportMethod.WritePairFile(list_dir, dir);
//        Matrix DSM1 = MyIO.ReadDSM("HSSP_Database/Train_AdjustIndex/DSM_Rnd");
//        Matrix DSM2 = MyIO.ReadDSM("HSSP_Database/Train_AdjustIndex/DSM_0_1");
//        Matrix DSM3 = MyIO.ReadDSM("HSSP_Database/Train_AdjustIndex/DSM_1_0");
//        Matrix DSM4 = MyIO.ReadDSM("HSSP_Database/Train_AdjustIndex/DSM_1_1");
//        Matrix DSM5 = MyIO.ReadDSM("newDSM.out");
        MyBlackbox.TongHop(dir, list_dir,null, "PSSM_Pair_MSA_100_width_3_TR299");
//        MyBlackbox.TongHop(dir, list_dir,null, "NativePSSM_MSA_100_width_11_Train_TR299");
//        MyBlackbox.TongHop(dir, list_dir,DSM2, "PSSM_Uvalue_DSM_0_1_Test");
//        MyBlackbox.TongHop(dir, list_dir,DSM3, "PSSM_Uvalue_DSM_1_0_Test");
//        MyBlackbox.TongHop(dir, list_dir,DSM4, "PSSM_Uvalue_DSM_1_1_Test");
//        MyBlackbox.TongHop(dir, list_dir,DSM5, "PSSM_Uvalue_DSM_5_Test");
//        Matrix m = new Matrix(10, 10, 1.0);
//        m = m.getMatrix(0, 1, 2, 4);
//        m.PrintToScreen();
//        MyBlackbox.FindSafeNeg("BLAST_Database/Train/NativePSSM_MSA_100_Train.arff", "BLAST_Database/Train/NativePSSM_MSA_100_Train_FindSafeNeg.arff");
//        MyBlackbox.GetProteinSequence("Train/AminoList374.txt", "Train/AminoList374_Sequence.txt");
//        MyBlackbox.EachProteinEachFile("Train/AminoList374_Sequence.txt");
//        StaticSupportMethod.PSIBlast("Train/AminoList374.txt");
//        Runtime rt = Runtime.getRuntime();
//        Process pr = rt.exec("powershell.exe mkdir D:\\khanhlinh");
//        MyBlackbox.GetBindingIdx("1A6Y", "A");
//        MyBlackbox.CalculateDSM("DSM.txt");
//        StaticSupportMethod.ModifyMSA("HSSP_Database/Test75_New/TS75.txt", "HSSP_Database/Test75_New/MSA/");
//        double[][] m = Dsm.CreateRandomDSM();
//        MyIO.WritePoPToFile("HSSP_Database/Train_AdjustIndex/DSM_Rnd", m);
//        MyBlackbox.FromOutFileToMSA("BLAST_Database/Test62/PDNA62.txt", "BLAST_Database/Test62/MSA/");
//        StaticSupportMethod.ChangeFileName("BLAST_Database/Test75/PSSM/", "BLAST_Database/Test75/PSSM/TS75.txt");
//        ArrayList<String> lst_name = MyIO.ReadLines("BLAST_Database/Test75/TS75.txt");
//        MyBlackbox.PredictBindingPosition(lst_name, "BLAST_Database/Test75/", 11);
//        StaticSupportMethod.GetThreshold("BLAST_Database/Test62/", "PDNA62.txt", "Threshold.txt", null);
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
