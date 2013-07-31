/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PDBFile;

import java.io.FileWriter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.ResidueNumber;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.align.util.AtomCache;
import org.biojava.bio.structure.io.mmcif.model.ChemComp;

/**
 *
 * @author mgueltas
 */
public class DistanceFinder {

    public static Object[][] basicLoad(String pdbId) {
//        String filename = "1A0J.pdb";
//        String chainName = "1A0J.A";
//        String targetSource = CsaSource.getNeighgbors(); // define the output ordner
        String targetSource = "AAA";

//        PDBFileReader pdbreader = new PDBFileReader();
        String tmp[] = pdbId.split("_"); // split the protein name as 4 letter pdb number + chain
        pdbId = tmp[0];//+ "." + tmp[1]; // biojava needs to pdbid with chain together
        String chain = tmp[1]; // define chain
        System.out.println(pdbId + " + " + chain);

//        System.out.println("********************************************:");
        try {
            AtomCache cache = new AtomCache(); //  getting  Atom Information from pdb file

            // alternative: try d4hhba_ 4hhb.A 4hhb.A:1-100
//            Structure s = cache.getStructure(chainName);
//            Structure s = cache.getStructure("1A05");
            Structure s = cache.getStructure(pdbId);
//            System.out.println( s.getName());
//            System.out.println("Name : " + s.getChains().get(0).getChainID());
//            for (int i = 0; i < s.getChains().size(); i++) {
//                for (int j = i; j < s.getChains().size(); j++) {
//                    System.out.println(pdbId.trim() + "" + s.getChains().get(i).getChainID().trim() + "" + s.getChains().get(j).getChainID().trim());
//                }
//            }
//            System.out.println(s);
//            Chain c1 = s.getChainByPDB("A");
            Chain c1 = s.getChainByPDB(chain);  // a lot of pdb file include more than one chain, therefore we must fix the chain
            List<Chain> lst_chain = s.getChains();
            for (Chain c : lst_chain) {
                System.out.println("Chain is: " + c.getAtomGroups().size());

            }
            System.out.println(" seq:" + c1.getAtomSequence());

            System.out.println("length:" + c1.getAtomSequence().length());

            char[] residue = c1.getAtomSequence().toCharArray();
            List<org.biojava.bio.structure.Group> sqgroups = c1.getAtomGroups();
//             System.out.println( c1.getSeqResSequence());
            Object pdbInfo[][] = new Object[residue.length][3];
            int i = 0;
            System.out.println(sqgroups.size() + " " + residue.length);
            double distance = 6;
            FileWriter writer = new FileWriter(targetSource + pdbId + "." + chain + "." + distance + "A" + ".out");
            writer.write("> Chain: " + pdbId + "\n");
            writer.write(c1.getAtomSequence() + "\n");
            writer.write("*NEIGHBORS \n");

            for (org.biojava.bio.structure.Group g : sqgroups) {
//                System.out.println(g.getType());
                if (g.getType().equalsIgnoreCase("amino")) {
                    List<Atom> atoms = g.getAtoms();
//                    System.out.println(g.getResidueNumber());
                    ResidueNumber rn = g.getResidueNumber();
//                            System.out.println("atom :" + atomName + " -- " + searchAtoms.get(i).getFullName() + " - " + searchAtoms.get(i));
                    //      String result = distanceCalculate(atoms, sqgroups, rn, distance);
                    String result = MyDistanceCalculate(atoms, lst_chain, rn, distance);
//                            System.out.println(result);
                    writer.write(result + "\n");
                    //    break;
                }
            }
            writer.close();
            return pdbInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String distanceCalculate(List<Atom> atom,
            List<org.biojava.bio.structure.Group> groups,
            ResidueNumber rnumber, double abstandt) {

        String neighbors = rnumber + " : ";
        int counter = 0;
        ArrayList<String> neighbor = new ArrayList<String>();
//        System.out.println("Size of g: "+groups.size());
        for (org.biojava.bio.structure.Group g : groups) {
            double distance = 0;
            //           System.out.println("GetType: " + g.getType());
            if (g.getType().equalsIgnoreCase("nucleotide")) {
                //       System.out.println("Group: "+g.toString());
                ResidueNumber rn = g.getResidueNumber();
                if (rn != rnumber) {
                    List<Atom> searchAtoms = g.getAtoms();
                    //      searchAtoms.get(0).
                    for (int id = 0; id < atom.size(); id++) {
                        if (!atom.get(id).getName().equalsIgnoreCase("CA")) {
                            continue;
                        }
                        double x1 = atom.get(id).getX();
                        double y1 = atom.get(id).getY();
                        double z1 = atom.get(id).getZ();
                        for (int i = 0; i < searchAtoms.size(); i++) {
                            double x2 = searchAtoms.get(i).getX();
                            double y2 = searchAtoms.get(i).getY();
                            double z2 = searchAtoms.get(i).getZ();
                            distance = Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2);
                            distance = Math.sqrt(distance);
                            if (distance < abstandt) {
                                if (!neighbor.contains(rn.toString())) {
                                    neighbor.add(rn.toString());
//                                    System.out.print(rn + " ,");
                                    neighbors += rn + ",";
                                }
                            }
                        }
                    }
                }
            }
        }
//        System.out.println(neighbors);

        return neighbors;
    }

    public static String MyDistanceCalculate(List<Atom> atom,
            List<Chain> lst_chain,
            ResidueNumber rnumber, double abstandt) {
        String res = "";
        int num_chain = lst_chain.size();
        for (Chain c : lst_chain) {
            if (IsNucleotideChain(c)) {
                List<Group> groups = c.getAtomGroups();
                String str = distanceCalculate(atom, groups, rnumber, abstandt);
                res = res + str + "\n";
            }
        }
        return res;
    }

    public static boolean IsNucleotideChain(Chain c) {
        List<Group> lst_group = c.getAtomGroups();
        boolean res = false;
        for (int i = 0; i < lst_group.size(); i++) {
            if (lst_group.get(i).getType().equalsIgnoreCase("nucleotide")) {
                res = true;
                return res;
            }
        }
        return res;
    }

    public static ArrayList<Atom> FindBinding(Group g,
            List<Chain> lst_chain,
            double threshold) {
        //////
        
        ArrayList<Atom> res = new ArrayList<Atom>();

        //       int num_chain = lst_chain.size();
        for (Chain c : lst_chain) {
            if (IsNucleotideChain(c) && !c.getChainID().equalsIgnoreCase(g.getChainId())) {
                List<Group> groups = c.getAtomGroups();
                for(Group tmp: groups){
                   Atom kq = DistanceBtwGroups(g, tmp, threshold);
                   if(kq!=null){
                       res.add(kq);
                   }
                }
                //             String str = distanceCalculate(atom, groups, rnumber, abstandt);

            }
        }
        return res;
    }

    public static double DistanceBtwAtoms(Atom A, Atom B) {
        double dis = 0.0;
        double X1 = A.getX(); //System.out.println("X1: " + X1);
        double Y1 = A.getY(); //System.out.println("Y1: " + Y1);
        double Z1 = A.getZ(); //System.out.println("Z1: " + Z1);
        double X2 = B.getX(); //System.out.println("X2: " + X2);
        double Y2 = B.getY(); //System.out.println("Y2: " + Y2);
        double Z2 = B.getZ(); //System.out.println("Z2: " + Z2);
        dis = Math.pow(X1 - X2, 2) + Math.pow(Y1 - Y2, 2) + Math.pow(Z1 - Z2, 2);
        dis = Math.sqrt(dis);
        //System.out.println("Dis: "+ dis);
        //System.exit(0);
        return dis;
    }

    public static Atom DistanceBtwGroups(Group A, Group B, double thres) {
        double dis = 10000000.0;
        List<Atom> lst_Atom_A = A.getAtoms();
        List<Atom> lst_Atom_B = B.getAtoms();
        Atom res = lst_Atom_B.get(0);
        for (Atom a : lst_Atom_A) {
            for (Atom b : lst_Atom_B) {
//                if (A.getType().equalsIgnoreCase("amino")) {
//                    if (!a.getName().equalsIgnoreCase("CA")) {
//                        continue;
//                    }
//                } else if (B.getType().equalsIgnoreCase("amino")) {
//                    if (b.getName().equalsIgnoreCase("CA")) {
//                        continue;
//                    }
//                }
                double tmp = DistanceBtwAtoms(a, b);
                if(dis>tmp){
                    dis = tmp;
                    res = b;
                }

            }
        }
        if(dis<=thres){
            return res;
        }
        return null;
    }
}
