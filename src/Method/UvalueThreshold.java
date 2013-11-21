/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

/**
 *
 * @author linh
 */
public class UvalueThreshold {
    private String ProtName;
    private double[] Thres;

    /**
     * @return the ProtName
     */
    public String getProtName() {
        return ProtName;
    }

    /**
     * @param ProtName the ProtName to set
     */
    public void setProtName(String ProtName) {
        this.ProtName = ProtName;
    }

    /**
     * @return the Thres
     */
    public double[] getThres() {
        return Thres;
    }

    /**
     * @param Thres the Thres to set
     */
    public void setThres(double[] Thres) {
        this.Thres = Thres;
    }
    public UvalueThreshold(String s, double[] d){
        this.ProtName = s;
        this.Thres = d;
    }
}
