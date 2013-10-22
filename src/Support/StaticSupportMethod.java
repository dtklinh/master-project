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
    public static void PSIBlast(String filename) throws FileNotFoundException, IOException, InterruptedException{
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
            if(!line.equalsIgnoreCase("")){
                String query = "/home/linh/Master/master-project/HSSP_Database/Test62/"+line+".txt";
                String db_dir = "/home/linh/Program/ncbi-blast-2.2.28+/database/nr/nr";
                String home_dir = "/home/linh/Master/master-project/HSSP_Database/Test62/";
                
                String cmd = "psiblast "+ "-query "+ query +" -db "+db_dir +" -out "+home_dir+line+".out "+" -num_iterations 3 -out_ascii_pssm "+home_dir+line+".pssm" + " -comp_based_stats 1";
//                cmd = cmd +" -out /home/linh/Master/master-project/HSSP_Database/Test62/"+line+"_out.txt";
//                cmd = cmd +" -num_iterations 3 ";
//                cmd = cmd + "-out_ascii_pssm /home/linh/Master/master-project/HSSP_Database/Test62/"+ line + ".pssm.txt";
//                cmd = cmd + " -comp_based_stats 1";
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(cmd);
                pr.waitFor();
                System.out.println(line + " is done");
              //  rt.freeMemory();
            }
        }
        br.close();
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
