/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreateMSA;

import Components.KeyProtein;
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
public class AlignedProtein {

    private KeyProtein Protein;
    private ArrayList<AlignedBlock> Lst_AlignedBlock;

    /**
     * @return the Protein
     */
    public KeyProtein getProtein() {
        return Protein;
    }

    /**
     * @param Protein the Protein to set
     */
    public void setProtein(KeyProtein Protein) {
        this.Protein = Protein;
    }

    /**
     * @return the Lst_AlignedBlock
     */
    public ArrayList<AlignedBlock> getLst_AlignedBlock() {
        return Lst_AlignedBlock;
    }

    /**
     * @param Lst_AlignedBlock the Lst_AlignedBlock to set
     */
    public void setLst_AlignedBlock(ArrayList<AlignedBlock> Lst_AlignedBlock) {
        this.Lst_AlignedBlock = Lst_AlignedBlock;
    }

    public AlignedProtein(String filename) throws FileNotFoundException, IOException { // filename e.g 2HAN_A
        FileInputStream fstream = new FileInputStream(filename + ".out.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        this.Protein = new KeyProtein(filename.substring(0, 4), filename.substring(5, 6));
        this.Protein.LoadingFromPDBFile();
        this.Lst_AlignedBlock = new ArrayList<AlignedBlock>();
        String line = "";
        boolean isblock = false;
        AlignedBlock b = null;
        String query = "";
        String subject = "";
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            if (line.startsWith(">")) {
                isblock = true;
                if (b != null) {
                    b.setQuery_str(query);
                    b.setSubject_str(subject);
                    b.EliminateGap();
                    this.Lst_AlignedBlock.add(b);
                }
                b = new AlignedBlock();
                query = "";
                subject = "";
            }
            if (line.startsWith("Query")) {
                int query_start_idx = Integer.parseInt(line.substring(7, 11).trim());
                if (b.getQuery_start() > query_start_idx) {
                    b.setQuery_start(query_start_idx);
                }
                String query_tmp = line.substring(11, 73).trim();
                query = query + query_tmp;
                int query_end_idx = Integer.parseInt(line.substring(73).trim());
                if (b.getQuery_end() < query_end_idx) {
                    b.setQuery_end(query_end_idx);
                }

            }
            if (line.startsWith("Sbjct")) {
                subject = subject + line.substring(11, 73).trim();
            }
        }
    }

    public AlignedProtein() {
        this.Protein = null;
        this.Lst_AlignedBlock = new ArrayList<AlignedBlock>();
    }

    public ArrayList<String> CreateMSA() {
        ArrayList<String> msa = new ArrayList<String>();
//        int num = this.Lst_AlignedBlock.size();
        int pro_len = this.Protein.getSequence().length();
        String pro_name = this.Protein.getName() + "_" + this.Protein.getChain();
        msa.add(">" + pro_name);
        msa.add(this.Protein.getSequence());
        int count = 1;
        for (AlignedBlock b : this.Lst_AlignedBlock) {
            msa.add(">sequence_" + count);
            count++;
            String str = "";
            for (int i = 1; i < b.getQuery_start(); i++) {
                str = str + "-";
            }
            str = str + b.getSubject_str();
            for (int i = b.getQuery_end() + 1; i <= pro_len; i++) {
                str = str + "-";
            }
            if (!str.equalsIgnoreCase(this.Protein.getSequence())) {
                msa.add(str);
            }
        }
        return msa;
    }
}
