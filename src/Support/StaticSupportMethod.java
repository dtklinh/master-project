/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

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
    public static void PSIBlast(String filename, int start, int end) throws FileNotFoundException, IOException, InterruptedException{
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        ArrayList<String> name = new ArrayList<String>();
        while(true){
            line = br.readLine();
            if(line==null){
                break;
            }
            line = line.trim();
            if(!line.equalsIgnoreCase("")){
<<<<<<< HEAD
                name.add(line.substring(0, 6));
//                String dir_target = "/home/linh/Master/master-project/Train/";
//                String dir_db = "/home/linh/Program/ncbi-blast-2.2.28+/database/";
//                String query = dir_target + line + ".txt";
//                
//                String cmd = "psiblast "+ "-query "+ query +" -db " + dir_db + "nr.01/nr.01";
//                cmd += " -out "+ dir_target+line+".out.txt";
//                cmd +=" -num_iterations 3 ";
//                cmd += "-out_ascii_pssm "+ dir_target+ line + ".pssm.txt";
//                cmd += " -comp_based_stats 1";
//                Runtime rt = Runtime.getRuntime();
//                Process pr = rt.exec(cmd);
//                rt.freeMemory();
=======
                String query = "D:\\Study\\Java Code\\getProteinName\\Train\\"+line+".txt";
                
                String cmd = "powershell.exe psiblast "+ "-query "+ query +" -db C:\\blast-2.2.28\\db\\nr.01\\nr.01";
                cmd += " -out D:\\Study\\Java Code\\getProteinName\\Train\\"+line+"_out.txt";
                cmd +=" -num_iterations 3 ";
                cmd += "-out_ascii_pssm D:\\Study\\Java Code\\getProteinName\\Train\\"+ line + ".pssm.txt";
                cmd += " -comp_based_stats 1";
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(cmd);
                pr.destroy();
                rt.freeMemory();
>>>>>>> 0ef97e09762697d685e0c3356a115f770f2a6288
            }
        }
        br.close();
        for(int i=start;i<=end;i++){
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("psiblast -query /home/linh/Master/master-project/Test/" + name.get(i) + ".txt" + " -db /home/linh/Program/ncbi-blast-2.2.28+/database/nr/nr -out /home/linh/Master/master-project/Test/" + name.get(i) + ".out.txt -num_iterations 3 -comp_based_stats 1 -out_ascii_pssm /home/linh/Master/master-project/Test/" + name.get(i) + ".pssm.txt");
            System.out.println(name.get(i) + " is done");
            pr.waitFor();
            System.out.println(name.get(i));
        }
    }
    
}
