/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyDivergence;

import Jama.Matrix;
import sun.security.util.Length;

/**
 *
 * @author linh
 */
public class MyDivergence {
    private Matrix MatX;
    private Matrix MatY;

    /**
     * @return the MatX
     */
    public Matrix getMatX() {
        return MatX;
    }

    /**
     * @param MatX the MatX to set
     */
    public void setMatX(Matrix MatX) {
        this.MatX = MatX;
    }

    /**
     * @return the MatY
     */
    public Matrix getMatY() {
        return MatY;
    }

    /**
     * @param MatY the MatY to set
     */
    public void setMatY(Matrix MatY) {
        this.MatY = MatY;
    }
    public MyDivergence(Matrix X, Matrix Y){
        this.MatX = X;
        this.MatY = Y;
    }
    public MyDivergence(double[] X, double[] Y){
        Matrix Mat1 = new Matrix(X, X.length);
        Matrix Mat2 = new Matrix(Y, Y.length);
        this.MatX = Mat1;
        this.MatY = Mat2;
    }
    public Matrix RetrieveJointDistribution(){
        return MatX.times(MatY.transpose());
    }
    public double[] RetrieveJointDistributionVec(){
        Matrix m = MatX.times(MatY.transpose());
        return m.getColumnPackedCopy();
    }
    public double CalculateNormMI(){
        double res = 0.0;
        double[] X = this.MatX.getColumnPackedCopy();
        double[] Y = this.MatY.getColumnPackedCopy();
        double[] XY = this.RetrieveJointDistributionVec();
        MyEntropy x = new MyEntropy(X);
        MyEntropy y = new MyEntropy(Y);
        MyEntropy xy = new MyEntropy(XY);
        double ex = x.CalculateEntropy();
        double ey = y.CalculateEntropy();
        double exy = xy.CalculateEntropy();
        return 2*(ex+ey-exy)/(ex+ey);
    }
    public double JSD(){
        
    }
}
