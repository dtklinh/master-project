/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyDivergence;

/**
 *
 * @author linh
 */
public class MyEntropy {
    private double[] Vec;
    

    /**
     * @return the Vec
     */
    public double[] getVec() {
        return Vec;
    }

    /**
     * @param Vec the Vec to set
     */
    public void setVec(double[] Vec) {
        this.Vec = Vec;
    }
    /////////////////
    public MyEntropy(double[] d){
        this.Vec = d.clone();
    }
    public double CalculateEntropy(){
        double sum =0.0;
        for(int i=0;i<this.Vec.length;i++){
            if(this.Vec[i]<=0){
                continue;
            }
            sum += this.Vec[i]*Math.log10(this.Vec[i])/Math.log10(2);
        }
        return sum*(-1);
    }
    
}
