/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import PDBFile.DistanceFinder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.ResidueNumber;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.align.util.AtomCache;

/**
 *
 * @author linh
 */
public class KeyProtein {

    private String Name;
    private String Chain;
    private ArrayList<Integer> BindingIndex;
    private int Offset;
    private String Sequence;

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the Chain
     */
    public String getChain() {
        return Chain;
    }

    /**
     * @param Chain the Chain to set
     */
    public void setChain(String Chain) {
        this.Chain = Chain;
    }

    /**
     * @return the BindingIndex
     */
    public ArrayList<Integer> getBindingIndex() {
        return BindingIndex;
    }

    /**
     * @param BindingIndex the BindingIndex to set
     */
    public void setBindingIndex(ArrayList<Integer> BindingIndex) {
        this.BindingIndex = BindingIndex;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return Offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.Offset = offset;
    }
    /////

    public KeyProtein() {
        this.BindingIndex = new ArrayList<Integer>();
        this.Chain = "";
        this.Name = "";
        this.Offset = 0;
    }

    public KeyProtein(String name, String chain) {
        this.BindingIndex = new ArrayList<Integer>();
        this.Chain = chain;
        this.Name = name;
        this.Offset = 0;
        this.Sequence = "";
    }

    public KeyProtein(String name, String chain, ArrayList<Integer> lst, int offset) {
        this.Name = name;
        this.Chain = chain;
        this.Offset = offset;
        this.BindingIndex = lst;
    }

    public KeyProtein(String name, String chain, int offset) {
        this.Name = name;
        this.Chain = chain;
        this.Offset = offset;
        this.BindingIndex = new ArrayList<Integer>();
    }

    public void AddBindingIndex(int idx) {
        this.BindingIndex.add(idx);
    }

    public void RemoveBindingIndex(int i) {
        this.BindingIndex.remove(i);
    }

    public void PrintOut(String filename) throws IOException {
        //
        FileWriter writer = new FileWriter(filename);
        String tmp = "";
        // index
        for (int i = 0; i < this.BindingIndex.size(); i++) {
            tmp = tmp + this.BindingIndex.get(i) + "\t";
        }
        tmp = tmp + "\n";
        tmp = tmp + this.Offset + "\n";
        tmp = tmp + this.Name + "\n";
        tmp = tmp + this.Chain + "\n";
        tmp = tmp + this.Sequence;
        writer.write(tmp);
        writer.close();
    }

    public void PrintToScreen() {
        String tmp = "";
        // index
        for (int i = 0; i < this.BindingIndex.size(); i++) {
            tmp = tmp + this.BindingIndex.get(i) + "\t";
        }
        tmp = tmp + "\n Size: "+ this.BindingIndex.size();
        tmp = tmp + "\n";
        tmp = tmp + this.Offset + "\n";
        tmp = tmp + this.Name + "\n";
        tmp = tmp + this.Chain + "\n";
        tmp = tmp + this.Sequence;
        System.out.println(tmp);
    }

    public void LoadingFromPDBFile() {
        try {
            AtomCache cache = new AtomCache();
            Structure s = cache.getStructure(this.Name);
            List<Chain> lst_chain = s.getChains();
            Chain chain_target = s.getChainByPDB(this.Chain);
            this.setSequence(chain_target.getAtomSequence().trim());
            List<Group> group_of_target = chain_target.getAtomGroups();
            boolean flag = true;
            double thres = 3.5;
            for (Group g : group_of_target) {
                if (g.getType().equalsIgnoreCase("amino")) {
                    //     List<Atom> atoms = g.getAtoms();
                    ResidueNumber rn = g.getResidueNumber();
                    if (flag) {
                        this.setOffset(rn.getSeqNum());
                        flag = false;
                    }
                    ArrayList<Atom> lst_tmp = DistanceFinder.FindBinding(g, lst_chain, thres);
                    if (lst_tmp.size() > 0) {
                        this.AddBindingIndex(rn.getSeqNum());
                    }
                }
            }
        } catch (Exception e) {
            //    System.out.println("Error: "+ e);
            System.err.println("Error: " + e);
        }
    }

    /**
     * @return the Sequence
     */
    public String getSequence() {
        return Sequence;
    }

    /**
     * @param Sequence the Sequence to set
     */
    public void setSequence(String Sequence) {
        this.Sequence = Sequence;
    }

    public ArrayList<int[]> RetrieveIndicatorPair(int distance) { // define the distance of neighborhood
        ArrayList<int[]> res = new ArrayList<int[]>();
        // adjust the offset
        ArrayList<Integer> Binding_Offset = new ArrayList<Integer>();
        for (Integer i : this.getBindingIndex()) {
            Binding_Offset.add(i - this.Offset);
        }
        // end
        for (Integer index : Binding_Offset) {
            ArrayList<Integer> IndexAndNearby = new ArrayList<Integer>();
            for (int k = index - distance; k <= index + distance; k++) {
                if (k >= 0 && k < this.Sequence.length()) {
                    IndexAndNearby.add(k);
                }
            }
            boolean flag = true;
            ArrayList<int[]> IndexOfPair = Combination(IndexAndNearby);
            for (int[] tmp : IndexOfPair) {
                for (int[] v : res) {
                    if ((tmp[0] == v[0] && tmp[1] == v[1]) || (tmp[0] == v[1] && tmp[1] == v[0])) {
                        flag = false;
                        break;
                    }

                }
                if (flag) {
                    res.add(tmp);
                }
            }
        }
        return res;
    }

    public ArrayList<int[]> RetrieveNullIndex(int distance) {

        ArrayList<int[]> res = new ArrayList<int[]>();
        // adjust the offset
        ArrayList<Integer> Binding_Offset = new ArrayList<Integer>();
        for (Integer i : this.getBindingIndex()) {
            Binding_Offset.add(i - this.Offset);
        }
        // end
        ArrayList<Integer> lst = new ArrayList<Integer>();
        for (int i = 0; i < this.Sequence.length(); i++) {
            lst.add(i);
        }
        for(int i=Binding_Offset.size()-1;i>=0;i--){
            for(int k = Binding_Offset.get(i)+distance; k>=Binding_Offset.get(i)-distance;k--){
                if(k>=0 && k <lst.size()){
                    lst.remove(k);
                }
            }
        }
        res = Combination(lst);
        return res;
    }

    public static ArrayList<int[]> Combination(ArrayList<Integer> lst) {
        ArrayList<int[]> res = new ArrayList<int[]>();
        for (int i = 0; i < lst.size() - 1; i++) {
            for (int j = i + 1; j < lst.size(); j++) {
                int[] tmp = new int[2];
                tmp[0] = lst.get(i);
                tmp[1] = lst.get(j);
                res.add(tmp);
            }
        }
        return res;
    }

    public void PrintIndicatorPair(boolean indicator, int distance) {
        ArrayList<int[]> tmp = new ArrayList<int[]>();
        if (indicator) {
            tmp = this.RetrieveIndicatorPair(distance);
        } else {
            tmp = this.RetrieveNullIndex(distance);
        }
        for (int[] i : tmp) {
            System.out.println("[" + i[0] + ":" + i[1] + "]");
        }
    }
}
