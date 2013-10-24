/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

import Components.KeyProtein;
import Components.MyIO;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
}
