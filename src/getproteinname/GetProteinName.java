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
import Jama.Matrix;
import Support.StaticSupportMethod;
import java.util.logging.Level;
import java.util.logging.Logger;

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
<<<<<<< HEAD

//        MyBlackbox.CalculateSig_NullMatrix("CompletePosList_2.txt");
=======
        
        MyBlackbox.CalculateSig_NullMatrix("HSSP_Database/Train/AminoList374_modified.txt");
>>>>>>> 0ef97e09762697d685e0c3356a115f770f2a6288
//        String path = "Train/NullMatrix2";
//        MyBlackbox.CalculateSum(path+"/list.txt", path);
//        MyBlackbox.TestSync("HSSP_Database/Test/TS75.txt");
//        System.out.println("1: "+MyPair.ConvertFromNumToString2(1));
//        MyBlackbox.PrepareDataSet("CompletePosList_2.txt");
//        MyBlackbox.CalculateDSM2();
//        MyBlackbox.Test();
//        MyBlackbox.TongHop();
//        Matrix m = new Matrix(10, 10, 1.0);
//        m = m.getMatrix(0, 1, 2, 4);
//        m.PrintToScreen();
//        MyBlackbox.FindSafeNeg("PSSM_Uvalue_DSM_1_Train.arff", "DownNeg_PSSM_Uvalue_DSM_1_Train.arff");
//        MyBlackbox.GetProteinSequence("Train/AminoList374.txt", "Train/AminoList374_Sequence.txt");
<<<<<<< HEAD
        MyBlackbox.EachProteinEachFile("Test/TS75.txt");
//        String s = "My     query     is   ";
//        s = s.trim();
//        String[] ls = s.split("\\s+");
//        for(int i=0; i<ls.length;i++){
//            System.out.println(i + " : "+ls[i]);
//        }
=======
//        MyBlackbox.EachProteinEachFile("Train/AminoList374_Sequence.txt");
//        StaticSupportMethod.PSIBlast("Train/AminoList374.txt");
//        Runtime rt = Runtime.getRuntime();
//        Process pr = rt.exec("powershell.exe mkdir D:\\khanhlinh");
//        MyBlackbox.GetBindingIdx("1A6Y", "A");
//        MyBlackbox.CalculateDSM("DSM.txt");
>>>>>>> 0ef97e09762697d685e0c3356a115f770f2a6288
        
        /*
        Runnable r1 = new Runnable() {
            public void run() {
                try {

                    StaticSupportMethod.PSIBlast("Test/TS75.txt", 0, 24);
                    Thread.sleep(1000L);

                } catch (InterruptedException iex) {
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GetProteinName.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GetProteinName.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Runnable r2 = new Runnable() {
            public void run() {
                try {
                    StaticSupportMethod.PSIBlast("Test/TS75.txt", 25, 49);
                    Thread.sleep(1000L);
                } catch (InterruptedException iex) {
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GetProteinName.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GetProteinName.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Runnable r3 = new Runnable() {
            public void run() {
                try {
                    StaticSupportMethod.PSIBlast("Test/TS75.txt", 50, 74);
                    Thread.sleep(1000L);
                } catch (InterruptedException iex) {
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GetProteinName.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GetProteinName.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread thr1 = new Thread(r1);
        Thread thr2 = new Thread(r2);
        Thread thr3 = new Thread(r3);
        thr1.start();
        thr2.start();
        thr3.start();
        */
        
        
//        StaticSupportMethod.PSIBlast("Train/AminoList374.txt", 0, 373);
//        String[] name = new String[]{"1MJE_B",         
//"1Q0T_A         
//"1TF3_A         
//"1ZME_C        
//"2DWL_A       
//"2NRA_C 
//"1AWC_A       
//"1ECR_A        
//"1HJB_A         
//"1JFI_A           
//"1MM8_A       
//"1Q9Y_A        
//"1TF6_A         
//"1ZQ3_P         
//"2ER8_A         
//"2NTC_A 
//"1AWC_B       
//"1EMH_A       
//"1HLO_A        
//"1JFI_B           
//"1MNM_A         
//"1QAI_A         
//"1TQE_P         
//"1ZS4_A         
//"2ES2_A         
//"2O4I_A 
//"1AZP_A         
//"1EO3_A         
//"1HLV_A        
//"1JMC_A        
//"1MNM_C         
//"1QBJ_A         
//"1TRO_A        
//"1ZTG_A        
//"2ETW_A       
//"2OAA_A 
//"1B2M_A        
//"1EQZ_C         
//"1HWT_C       
//"1JNM_A        
//"1MOW_A         
//"1QN3_A        
//"1TSR_A         
//"1ZX4_A         
//"2EX5_A         
//"2ODI_A 
//"1B3T_A         
//"1EQZ_D        
//"1I3J_A           
//"1JT0_A          
//"1MSE_C        
//"1QPI_A          
//"1TTU_A        
//"1ZZI_A          
//"2EZV_A        
//"2OFI_A 
//"1B72_A         
//"1EWN_A       
//"1I6H_A          
//"1K61_A         
//"1MTL_A        
//"1QRV_A        
//"1U1K_A        
//"2A07_F          
//"2F8N_K         
//"2OH2_A 
//"1B72_B          
//1EXI_A         
//1I6H_B          
//1K6O_B         
//1MVM_A         
//1QUM_A       
//1U3E_M        
//2A0I_A          
//2F8X_K         
//2OST_A 
//1B8I_A          
//1EYG_A        
//1I6H_C          
//1K78_A         
//1MW8_X       
//1QZG_A        
//1U78_A         
//2A1R_A         
//2F8X_M        
//2OWO_A 
//1B8I_B          
//1EYU_A        
//1I6H_E          
//1K8G_A        
//1NFK_A        
//1R0N_B         
//1U8B_A         
//2A3V_A        
//2FD8_A         
//2PJR_A 
//1BDH_A        
//1F0V_A         
//1I6H_F          
//1KB2_A         
//1NG9_A        
//1R0O_A        
//1U8R_A         
//2A66_A         
//2FIO_A          
//2PJR_B 
//1BF5_A         
//1F2I_G           
//1I6H_H          
//1KBU_A        
//1NGM_B       
//1R4I_A          
//1UBD_C        
//2A6O_A        
//2FKC_A        
//2STT_A 
//1BG1_A         
//1F4K_A         
//1I6H_I           
//1KC6_A         
//1NH2_C         
//1R4O_A        
//1V14_A         
//2ACJ_A         
//2FO1_D         
//3CRO_L 
//1BHM_A       
//1F4S_P          
//1I6H_J           
//1KDH_A       
//1NK2_P         
//1R71_A         
//1VAS_A        
//2AJQ_A         
//2FO1_E         
//3HTS_B 
//1BRN_L        
//1F5T_A         
//1I6H_K          
//1KQQ_A       
//1NKP_A        
//1R7M_A        
//1VFC_A        
//2AOQ_A       
//2FQZ_A        
//3KTQ_A 
//1BVO_A        
//1FIU_A          
//1I6H_L          
//1KSY_A        
//1NLW_A       
//1R8D_A        
//1VRR_A        
//2AQ4_A        
//2G1P_A         
//3ORC_A 
//1C7Y_A         
//1FJL_A          
//1I7D_A          
//1KU7_A        
//1NOP_A        
//1RC7_A         
//1W36_B         
//2ASD_A        
//2GAT_A        
//3PJR_A 
//1C9B_A         
//1FOK_A        
//1IAW_A        
//1L1M_A        
//1NOY_A       
//1RC8_A         
//1W36_C         
//2AYB_A        
//2GLI_A         
//4GAT_A 
//1CEZ_A         
//1FOS_E         
//1IC8_A          
//1L1T_A         
//1NWQ_A       
//1REP_C         
//1W36_D        
//2B9S_A         
//2GXA_A       
//6PAX_A 
//1CF7_A         
//1FZP_B         
//1ID3_C          
//1L3S_A         
//1O4X_A        
//1RIO_A         
//1WVL_A       
//2B9S_B         
//2GZK_A        
//10MH_A 
//1CF7_B         
//1G38_A         
//1ID3_D          
//1L9Z_A         
//1O4X_B         
//1RM1_A        
//1X9N_A        
//2BGW_A       
//2H1O_E          
//1CIT_A          
//1G4D_A        
//1IF1_A          
//1L9Z_C         
//1ODG_A       
//1RM1_B        
//1XF2_B         
//2BOP_A        
//2H27_A          
//1CKQ_A        
//1GCC_A        
//1IG4_A          
//1L9Z_D         
//1ODH_A       
//1RM1_C        
//1XHZ_A        
//2BSQ_A        
//2H7G_X         
//1CMA_A       
//1GD2_E         
//1IGN_A         
//1L9Z_E          
//1ORN_A        
//1RRQ_A        
//1XJV_A         
//2BSQ_E         
//2H8C_A         
//1CW0_A        
//1GDT_A        
//1IHF_A          
//1L9Z_H         
//1OSB_A        
//1RTD_B        
//1XPX_A        
//2BZF_A         
//2HDC_A         
//1D02_A         
//1GM5_A        
//1IO4_D          
//1LAU_E        
//1OUP_A        
//1RXV_A        
//1XS9_A         
//2C5R_A         
//2HVR_A };
//        for (int i = 0; i < 250; i++) {
//            Runtime rt = Runtime.getRuntime();
//            Process pr = rt.exec("psiblast -query /home/linh/Master/master-project/Train/" + name[i].trim() + ".txt" + " -db /home/linh/Program/ncbi-blast-2.2.28+/database/nr.01/nr.01 -out /home/linh/Master/master-project/Train/" + name[i].trim() + ".out.txt -num_iterations 3 -comp_based_stats 1 -out_ascii_pssm /home/linh/Master/master-project/Train/" + name[i].trim() + ".pssm.txt");
//            System.out.println(name[i]);
//        }

//        MyBlackbox.CalculateDSM("DSM.txt");

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
