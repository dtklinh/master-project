/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PDBFile;

import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.FileParsingParameters;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author linh
 */
public class Test {

    public static void LoadFile() {
        PDBFileReader reader = new PDBFileReader();
        reader.setAutoFetch(false);
        FileParsingParameters params = new FileParsingParameters();
        params.setParseSecStruc(true);
        params.setAlignSeqRes(true);
        params.setParseCAOnly(false);
        params.setLoadChemCompInfo(true);
        reader.setFileParsingParameters(params);
        try {
            Structure structure = reader.getStructure("2HAN.pdb");
            System.out.println();
            for (Chain c : structure.getChains()) {
                System.out.println(String.format("Chain %s: %s", c.getChainID(),
                        c.getHeader().getMolName()));
                for (Group g : c.getSeqResGroups()) {
                    System.out.println(String.format("%s: %s", g.getClass().getSimpleName(),
                            g.toString()));
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
