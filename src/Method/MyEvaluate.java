/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import Components.KeyProtein;
import java.util.ArrayList;

/**
 *
 * @author linh
 */
public class MyEvaluate {
    private int TruePositive;
    private int TrueNegative;
    private int FalsePositive;
    private int FalseNegative;

    /**
     * @return the TruePositive
     */
    public int getTruePositive() {
        return TruePositive;
    }

    /**
     * @param TruePositive the TruePositive to set
     */
    public void setTruePositive(int TruePositive) {
        this.TruePositive = TruePositive;
    }

    /**
     * @return the TrueNegative
     */
    public int getTrueNegative() {
        return TrueNegative;
    }

    /**
     * @param TrueNegative the TrueNegative to set
     */
    public void setTrueNegative(int TrueNegative) {
        this.TrueNegative = TrueNegative;
    }

    /**
     * @return the FalsePositive
     */
    public int getFalsePositive() {
        return FalsePositive;
    }

    /**
     * @param FalsePositive the FalsePositive to set
     */
    public void setFalsePositive(int FalsePositive) {
        this.FalsePositive = FalsePositive;
    }

    /**
     * @return the FalseNegative
     */
    public int getFalseNegative() {
        return FalseNegative;
    }

    /**
     * @param FalseNegative the FalseNegative to set
     */
    public void setFalseNegative(int FalseNegative) {
        this.FalseNegative = FalseNegative;
    }
    ////
    public MyEvaluate(int tp, int tn, int fp, int fn){
        this.TruePositive = tp;
        this.TrueNegative = tn;
        this.FalseNegative = fn;
        this.FalsePositive = fp;
    }
    public MyEvaluate(){
        this.FalseNegative =0;
        this.FalsePositive = 0;
        this.TrueNegative = 0;
        this.TruePositive =0;
    }
    public MyEvaluate(ArrayList<Integer> pre_test, KeyProtein k, int width){
        int radius = (width-1)/2;
        int len = k.getSequence().length();
        ArrayList<Integer> condition = k.getBindingIndex();
        for(int i=len-1; i>=len-radius;i--){
            int abs = k.GetAbsoluteIndex(i);
            int index = condition.indexOf(abs);
            if(index>=0){
                int cc = condition.remove(index);
            }
        }
        for(int i =radius -1; i>=0;i--){
            int abs = k.GetAbsoluteIndex(i);
            int index = condition.indexOf(abs);
            if(index>=0){
                int cc = condition.remove(index);
            }
        }
        int tp=0, tn=0, fp=0, fn=0;
        ArrayList<Integer> test = new ArrayList<Integer>();
        for(Integer i: pre_test){
            test.add(k.GetAbsoluteIndex(i));
        }
        for(int i=0;i<test.size();i++){
//            int idx = k.GetAbsoluteIndex(i);
            if(condition.indexOf(test.get(i))>=0){
                tp++;
            }
            else{
                fp++;
            }
        }
        for(int i=0;i<condition.size();i++){
            if(test.indexOf(condition.get(i))<0){
                fn++;
            }
        }
        tn = len - (width-1) - fn - tp-fp;
        this.FalseNegative = fn;
        this.FalsePositive = fp;
        this.TrueNegative = tn;
        this.TruePositive = tp;
    }
    public void Add(MyEvaluate e){
        this.TruePositive += e.TruePositive;
        this.FalseNegative += e.FalseNegative;
        this.FalsePositive += e.FalsePositive;
        this.TrueNegative += e.TrueNegative;
    }
    public void Add(int tp, int tn, int fp, int fn){
        this.TruePositive += tp;
        this.TrueNegative += tn;
        this.FalsePositive += fp;
        this.FalseNegative += fn;
    }
    public double Sensitivity(){
        return (double)this.TruePositive/(this.TruePositive+this.FalseNegative);
    }
    public double Specificity(){
        return (double)this.TrueNegative/(this.TrueNegative+ this.FalsePositive);
    }
    public double MCC(){
        int tp = this.TruePositive;
        int tn = this.TrueNegative;
        int fp = this.FalsePositive;
        int fn = this.FalseNegative;
        int tu = tp*tn - fp*fn;
        int mau = (tp+fp)*(tp+fn)*(tn+fp)*(tn+fn);
        double tmp = Math.pow(mau, 0.5);
        return tu/tmp;
    }
}
