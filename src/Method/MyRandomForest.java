/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author linh
 */
public class MyRandomForest {
    private Instances Entities;
    
    //private Instances InsNeg;
    
    public MyRandomForest(String filename) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        this.Entities = new Instances(br);
        int cIdx = this.Entities.numAttributes()-1;
        this.Entities.setClassIndex(cIdx);
    }

    /**
     * @return the Entities
     */
    public Instances getEntities() {
        return Entities;
    }

    /**
     * @param Entities the Entities to set
     */
    public void setEntities(Instances Entities) {
        this.Entities = Entities;
    }
    
    public double[][] Predict(Instances i) throws Exception{
        RandomForest rf = new RandomForest();
        rf.setNumTrees(30);
        rf.buildClassifier(this.Entities);
        int num_ins = i.numInstances();
        int num_class = i.numClasses();
        double[][] res = new double[num_ins][num_class];
        for(int idx = 0;idx<num_ins;idx++){
            res[idx] = rf.distributionForInstance(i.get(idx));
        }
        return res;
    }
    public double[][] CrossValidation(){
        this.Entities
        return null;
    }
}
