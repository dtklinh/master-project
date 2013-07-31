/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

/**
 *
 * @author linh
 */
public class MyMatrix {
    private int[][] Element;

    /**
     * @return the Element
     */
    public int[][] getElement() {
        return Element;
    }

    /**
     * @param Element the Element to set
     */
    public void setElement(int[][] Element) {
        this.Element = Element;
    }
    public MyMatrix(int[][] m){
        this.Element = m.clone();
    }
    public MyMatrix(int row, int col){
        this.Element = new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                this.Element[i][j] = 0;
            }
        }
    }
    public MyMatrix AddMatrix(MyMatrix m){
        int row = this.Element.length;
        int col = this.Element[0].length;
        MyMatrix res = new MyMatrix(row, col);
        if(this.Element.length != m.Element.length || this.Element[0].length != m.Element[0].length){
            System.err.println("Matrix does not agree in size");
        }
        for(int i=0; i<row;i++){
            for(int j=0; j<col; j++){
                res.getElement()[i][j] = this.Element[i][j] + m.getElement()[i][j];
            }
        }
        return res;
    }
    public double[][] Normalize(){
        int row = this.Element.length;
        int col = this.Element[0].length;
        double[][] res = new double[row][col];
        int sum = 0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                sum += this.Element[i][j];
            }
        }
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                res[i][j] = (double)this.Element[i][j]/sum;
            }
        }
        return res;
    }
    public static double[][] AddTwoMatrix(double[][] A, double[][] B){
        int row = A.length;
        int col = A[0].length;
        double[][] C = new double[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }
}
