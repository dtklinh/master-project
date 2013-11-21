/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

import Components.AminoAcid;
import Components.KeyProtein;
import Components.MSA;
import Components.MsaFilterer;
import Components.MyIO;
import Jama.Matrix;
import Method.MyPair;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author linh
 */
public class StaticSupportMethod {
    //public static 

    public static void PSIBlast(String filename) throws FileNotFoundException, IOException {
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
            if (!line.equalsIgnoreCase("")) {
                String query = "D:\\Study\\Java Code\\getProteinName\\Train\\" + line + ".txt";

                String cmd = "powershell.exe psiblast " + "-query " + query + " -db C:\\blast-2.2.28\\db\\nr.01\\nr.01";
                cmd += " -out D:\\Study\\Java Code\\getProteinName\\Train\\" + line + "_out.txt";
                cmd += " -num_iterations 3 ";
                cmd += "-out_ascii_pssm D:\\Study\\Java Code\\getProteinName\\Train\\" + line + ".pssm.txt";
                cmd += " -comp_based_stats 1";
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(cmd);
                pr.destroy();
                rt.freeMemory();
            }
        }
        br.close();
    }

    public static void ModifyMSA(String filename, String dir) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            if (!line.isEmpty()) {
                line = line.trim();
                KeyProtein k = new KeyProtein(line.substring(0, 4), line.substring(5, 6));
                k.LoadingFromPDBFile();
                String seq_pdb = k.getSequence();
                ArrayList<String> lst_tmp = new ArrayList<String>();
                ArrayList<String> alignment = MyIO.ReadMSA(dir + line + ".fasta.msa");
                if (seq_pdb.equalsIgnoreCase(alignment.get(0))) {
                    System.out.println(line + " is correct");
                    //   continue;
                } else {
                    if (!seq_pdb.startsWith(alignment.get(0))) {
                        System.err.println("Something is wrong");
                        System.err.println("HSSP: " + line + " : " + alignment.get(0));
                        System.err.println(" PDB: " + line + " : " + seq_pdb);
                        //    continue;
                    } else {
                        int len = seq_pdb.length();
                        alignment.set(0, seq_pdb);
                        lst_tmp.add("> "+k.getName()+"|"+k.getChain());
                        lst_tmp.add(alignment.get(0));
                        
                        for (int i = 1; i < alignment.size(); i++) {
                            String tmp = alignment.get(i);
                            while (tmp.length() < len) {
                                tmp = tmp + "-";
                            }
                            lst_tmp.add("> sequence "+i);
                            lst_tmp.add(tmp);
                        }
                        
                        
                        MyIO.WriteToFile(dir + line + ".fasta.msa", lst_tmp);
                        System.out.println("rewrite: " + line);
                    }

                }

            }
        }
    }
    public static void ProteinNameToFile(String filename, String dir) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while(true){
            line = br.readLine();
            if(line==null){
                break;
            }
            if(!line.isEmpty()){
                String name = line.substring(0, 4);
                String chain = line.substring(5, 6);
                KeyProtein k = new KeyProtein(name, chain);
                k.LoadingFromPDBFile();
                ArrayList<String> lst = new ArrayList<String>();
                lst.add(">"+name+"_"+chain);
                lst.add(k.getSequence());
                MyIO.WriteToFile(dir+name+"_"+chain+".txt", lst);
            }
        }
    }
    public static void ChangeFileName(String dir, String path) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(path);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while(true){
            line = br.readLine();
            if(line==null){
                break;
            }
            if(!line.isEmpty()){
                String filename = line.substring(0, 6) + ".pssm.txt";
                File file = new File(dir + filename);
                file.renameTo(new File(dir + line.substring(0, 6)+".pssm"));
            }
        }
        br.close();
    }
    public static void GetThreshold(String dir, String filename, String fileout, Matrix dssm) throws FileNotFoundException, IOException{
        ArrayList<KeyProtein> lst_prot = MyIO.LoadKeyProteins(dir + filename);
        ArrayList<String> res = new ArrayList<String>();
        for(KeyProtein k: lst_prot){
            String tmp = k.getName()+"_"+k.getChain() +"\t";
            String fasta = dir + "MSA/"+ k.getName()+"_"+k.getChain()+".msa";
            ArrayList<String> msa = MsaFilterer.filter(fasta);
            MSA m = new MSA(k, msa);
            // calculate threshold at 5, 10, 15, 20, 25, 30 percent
            double[] percent = new double[]{0.05, 0.1, 0.15, 0.2, 0.25, 0.3};
           double[] v = m.CalculateThreshold(dssm, percent);
           
           tmp = tmp + v[0]+"\t" + v[1]+"\t" + v[2]+"\t" + v[3]+"\t" + v[4]+"\t" + v[5];
           res.add(tmp);
            System.out.println(tmp + "\t : done");
        }
        MyIO.WriteToFile(dir+fileout, res);
    }
    public static void WritePairFile(String filename, String dir) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        ArrayList<String> amino = AminoAcid.getAA();
        HashMap<Integer, String> map = AminoAcid.GetIndexPair();
        while(true){
            line = br.readLine();
            if(line==null){
                break;
            }
            line = line.trim();
            if(line.isEmpty()){
                continue;
            }
            
            KeyProtein k = new KeyProtein(line.substring(0, 4), line.substring(5, 6));
            k.LoadingFromPDBFile();
            String fasta = dir + "MSA/" + line + ".msa";
            ArrayList<String> msa = MsaFilterer.filter(fasta);
            MSA m = new MSA(k, msa);
            ArrayList<String> ColumnStr = m.RetrieveColumnPair();
            ArrayList<MyPair> lst_mp = m.CreatePSSM_Pair(ColumnStr, amino);
            //
            String[][] tmp = new String[401][lst_mp.size()+1];
            tmp[0][0] = "STT";
            for(int i=1;i<=400;i++){
                tmp[i][0] = map.get(i-1);
            }
            for(int i=1;i<=lst_mp.size();i++){
                tmp[0][i] = lst_mp.get(i-1).getString1_Index() + ":" + lst_mp.get(i-1).getString2_Index();
                double[] val = lst_mp.get(i-1).getMyArray();
                for(int j=1;j<=400;j++){
                    tmp[j][i] = String.valueOf(val[j-1]);
                }
            }
            MyIO.WriteAlignedRowFile(dir + "PSSM_PAIR/"+line+".pair", tmp);
        }
    }
}
