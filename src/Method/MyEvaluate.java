/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

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
