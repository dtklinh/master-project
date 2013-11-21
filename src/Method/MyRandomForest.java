/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import Components.MSA;
import Jama.Matrix;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import org.jfree.util.ArrayUtilities;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author linh
 */
public class MyRandomForest {
    private Instances Entities;
    private RandomForest RndForest;
    
    //private Instances InsNeg;
    
    public MyRandomForest(String filename) throws FileNotFoundException, IOException, Exception{
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        this.Entities = new Instances(br);
        int cIdx = this.Entities.numAttributes()-1;
        this.Entities.setClassIndex(cIdx);
        this.RndForest = new RandomForest();
        this.RndForest.setNumTrees(100);
        this.RndForest.buildClassifier(Entities);
    }
    public MyRandomForest(Instances ins) throws Exception{
        this.Entities = new Instances(ins);
        this.RndForest = new RandomForest();
        this.RndForest.setNumTrees(100);
        this.RndForest.buildClassifier(Entities);
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
//        RandomForest rf = new RandomForest();
//        rf.setNumTrees(100);
////        rf.setNumFeatures(20);
//        rf.buildClassifier(this.Entities);
        
        
        int num_ins = i.numInstances();
        int num_class = i.numClasses();
        double[][] res = new double[num_ins][num_class];
        for(int idx = 0;idx<num_ins;idx++){
//            res[idx] = rf.distributionForInstance(i.get(idx));
            res[idx] = this.RndForest.distributionForInstance(i.get(idx));
        }
        return res;
    }
    public double[][] CrossValidation(int numFolds) throws Exception{
        double[][] res = new double[this.Entities.numInstances()][Entities.numClasses()];
        // divide into pos and neg set
        Instances InsPos = new Instances(Entities);
        Instances InsNeg = new Instances(Entities);
        for(int i=InsPos.numInstances()-1; i>=0; i--){
            if(InsPos.get(i).classValue()!=0.0){
                InsPos.remove(i);
            }
        }
        for(int i=InsNeg.numInstances()-1; i>=0; i--){
            if(InsNeg.get(i).classValue()!=1.0){
                InsNeg.remove(i);
            }
        }
        
        // cross validation
        int count =0;
        for(int i = 0;i<numFolds;i++){
            Instances train = InsPos.trainCV(numFolds, i);
            train.addAll(InsNeg.trainCV(numFolds, i));
            Instances test = InsPos.testCV(numFolds, i);
            test.addAll(InsNeg.testCV(numFolds, i));
            train.setClassIndex(this.Entities.classIndex());
            test.setClassIndex(this.Entities.classIndex());
            RandomForest rf = new RandomForest();
            rf.buildClassifier(train);
            for(int j=0;j<test.numInstances();j++){
                res[count] = rf.distributionForInstance(test.get(j));
                count++;
            }
        }
        return res;
    }
    public Instances GetPosIns(){
        Instances res = new Instances(this.Entities);
        for(int i=res.numInstances()-1; i>=0; i--){
            if(res.get(i).classValue()==1.0){
                res.remove(i);
            }
        }
        return res;
    }
    public Instances GetNegIns(){
        Instances res = new Instances(this.Entities);
        for(int i=res.numInstances()-1; i>=0; i--){
            if(res.get(i).classValue()==0.0){
                res.remove(i);
            }
        }
        return res;
    }
    public Instances DownNeg(double low, double high) throws Exception{
        Instances PosSet = this.GetPosIns();
        System.out.println("# pos instance: "+ PosSet.numInstances());
        int num_pos_ins = PosSet.numInstances();
 //       System.out.println("#Pos: "+ PosSet.numInstances());
        Instances NegSet = this.GetNegIns();
        Matrix mt = new Matrix(new double[NegSet.numInstances()][NegSet.numClasses()]);
        int[] true_classified = new int[NegSet.numInstances()];
        for(int i=0;i<10;i++){
            Instances tmp = new Instances(NegSet);
            tmp.randomize(new Random());
            Instances NegRnd = new Instances(tmp, 0, tmp.numInstances()/10);
//            Instances NegRnd = new Instances(tmp, 0, (int)(num_pos_ins*2.0));
 //           System.out.println("# NegRnd: "+ NegRnd.numInstances());
            Instances train = new Instances(PosSet);
            train.addAll(NegRnd);
//            RandomForest rf = new RandomForest();
//            rf.buildClassifier(train);
            MyRandomForest rf = new MyRandomForest(train);
            double[][] dis = rf.Predict(NegSet);
            mt = mt.plus(new Matrix(dis));
            for(int j=0;j<dis.length;j++){
                if(dis[j][0]> high || dis[j][0] < low){
                    true_classified[j]++;
                }
            }
        }
        Instances res = new Instances(NegSet);
        double[][] mtrx = mt.getArrayCopy();
        int count =0;
        for(int i=res.numInstances()-1;i>=0; i--){
 //           if(mtrx[i][0]/20>high || mtrx[i][0]/10<low){
            if((double)true_classified[i]/10 > 0.5){
                res.remove(i);
                count++;
                System.out.println(i+" : "+ mtrx[i][0]+ " / "+ mtrx[i][1]);
            }
        }
        System.out.println("remove / neg instance: "+count + " / "+ NegSet.numInstances());
        return res;
    }
    public double[][] Predict(ArrayList<double[]> lst_values) throws Exception{
//        ArrayList<Integer> res = new ArrayList<Integer>();
        ArrayList<Attribute> att = new ArrayList<Attribute>();
        for(int i=0;i<this.Entities.numAttributes();i++){
            att.add(this.Entities.attribute(i));
        }
       Instances UnLable = new Instances("TestInstances", att, 0);
        for(double[] d: lst_values){
            double[] tmp = new double[d.length+1];
            System.arraycopy(d, 0, tmp, 0, d.length);
            tmp[d.length] = -1;
            Instance ins = new DenseInstance(1.0,tmp);
            UnLable.add(ins);
        }
        UnLable.setClassIndex(UnLable.numAttributes()-1);
        double[][] dis = this.Predict(UnLable);
       //
      //  ins.se
        return dis;
    }

    /**
     * @return the RndForest
     */
    public RandomForest getRndForest() {
        return RndForest;
    }

    /**
     * @param RndForest the RndForest to set
     */
    public void setRndForest(RandomForest RndForest) {
        this.RndForest = RndForest;
    }
}
