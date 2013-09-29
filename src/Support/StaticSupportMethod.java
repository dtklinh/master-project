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

/**
 *
 * @author linh
 */
public class StaticSupportMethod {
    //public static 
    public static void PSIBlast(String filename) throws FileNotFoundException, IOException{
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
                String query = "D:\\Study\\Java Code\\getProteinName\\Train\\"+line+".txt";
                
                String cmd = "powershell.exe psiblast "+ "-query "+ query +" -db C:\\blast-2.2.28\\db\\nr.01\\nr.01";
                cmd += " -out D:\\Study\\Java Code\\getProteinName\\Train\\"+line+"_out.txt";
                cmd +=" -num_iterations 1 ";
                cmd += "-out_ascii_pssm D:\\Study\\Java Code\\getProteinName\\Train\\"+ line + "_pssm.txt";
                cmd += " -comp_based_stats 1";
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(cmd);
            }
        }
        br.close();
    }
}
