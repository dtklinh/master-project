/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import Jama.Matrix;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author linh
 */
public class DSM {

    private Matrix SignalMat;
    private Matrix NullMat;

    /**
     * @return the SignalMat
     */
    public Matrix getSignalMat() {
        return SignalMat;
    }

    /**
     * @param SignalMat the SignalMat to set
     */
    public void setSignalMat(Matrix SignalMat) {
        this.SignalMat = SignalMat;
    }

    /**
     * @return the NullMat
     */
    public Matrix getNullMat() {
        return NullMat;
    }

    /**
     * @param NullMat the NullMat to set
     */
    public void setNullMat(Matrix NullMat) {
        this.NullMat = NullMat;
    }

    public DSM() {
    }

    public DSM(int[][] signal, int[][] nul) {
        try {
            double[][] null_tmp = new double[nul.length][nul[0].length];
            double[][] signal_tmp = new double[signal.length][signal[0].length];
            for (int i = 0; i < nul.length; i++) {
                for (int j = 0; j < nul[0].length; j++) {
                    null_tmp[i][j] = (double) nul[i][j];
                    signal_tmp[i][j] = (double) signal[i][j];
                }
            }
            this.NullMat = new Matrix(null_tmp);
            this.SignalMat = new Matrix(signal_tmp);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public DSM(double[][] signal, double[][] nul) {
        this.NullMat = new Matrix(nul);
        this.SignalMat = new Matrix(signal);
    }

    public void LoadFromFile(String signalfile, String nullfile, int dim) {
        try {
            this.Load(signalfile, "signal", dim);
            this.Load(nullfile, "null", dim);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void Load(String filename, String signal, int dim) {
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            int i = 0, j = 0;
            double[][] Mat = new double[dim][dim];
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                line = line.trim();
                double tmp = Double.parseDouble(line);
                if (i >= dim) {
                    break;
                }
                if (j >= dim) {
                    i++;
                    j = 0;
                }
                Mat[i][j] = tmp;
            }
            if (signal.equalsIgnoreCase("signal")) {
                this.SignalMat = new Matrix(Mat);
            } else {
                this.NullMat = new Matrix(Mat);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void NormalizeMat() {
    }

    public static double[][] Nornalize(double[][] m) {
        int row = m.length;
        int col = m[0].length;
        double s = 0.0;
        double[][] res = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                s += m[i][j];
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                res[i][j] = m[i][j] / s;
            }
        }
        return res;
    }

    public Matrix CreateDSM() {
        int row = this.SignalMat.getRowDimension();
        int col = this.SignalMat.getColumnDimension();
        if (row != this.NullMat.getRowDimension() || col != this.NullMat.getColumnDimension()) {
            System.err.println("Signal and null matrix have different size");
            return null;
        }
 //       Matrix res = new Matrix(row, col, 0.0);
        double[][] tmp = new double[row][col];
        double[][] MatSig = this.SignalMat.getArrayCopy();
        double[][] MatNull = this.NullMat.getArrayCopy();
        // step 2
        System.out.println("Begin to step 2");
        double sumsignal = 0.0;
        double sumnull = 0.0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                sumsignal += MatSig[i][j];
                sumnull += MatNull[i][j];
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == j) {
                    tmp[i][j] = MatSig[i][j];
                } else if (MatSig[i][j] / sumsignal > MatNull[i][j] / sumnull) {
                    tmp[i][j] = MatSig[i][j];
                } else {
                    tmp[i][j] = 0.0;
                }
            }
        }
        // end step 2
        // step 3
        /// skip this step b.c we don't care about dissimlarity right now.
        // end step 3
        // step 4
        // calculate the marginal distribution
        System.out.println("Begin to step 4");
        tmp = DSM.Nornalize(tmp);
        double[] MarginX = new double[row];
        double[] MarginY = new double[col];
        for (int i = 0; i < row; i++) {
            MarginX[i] = 0.0;
            for (int j = 0; j < col; j++) {
                MarginX[i] += tmp[i][j];
            }
        }
        for (int j = 0; j < col; j++) {
            MarginY[j] = 0.0;
            for (int i = 0; i < row; i++) {
                MarginY[j] += tmp[i][j];
            }
        }
        double[][] s4 = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (tmp[i][j] == 0) {
                    s4[i][j] = 0.0;
                } else {
                    double tam = tmp[i][j] / (MarginX[i] * MarginY[j]);
                    s4[i][j] = Math.log(tam)/Math.log(2);
                    if(s4[i][j]<0.0){
                        s4[i][j] = 0.0;
                    }
                }
            }
        }
        
        // end step 4
        // step 5
        System.out.println("Begin to step 5");
        s4 = DSM.DSM_Iteration(s4);
        // end step 5
        return new Matrix(s4);
    }
    public static double[][] DSM_Iteration(double[][] m){
        double thres = 0.0000001;
        boolean not_dsm = true;
        int nrow = m.length;
        int ncol = m[0].length;
        int count =0;
        while(not_dsm){
            // normalize row
            for(int i=0;i<nrow;i++){
                double srow = 0.0;
                for(int j=0;j<ncol;j++){
                    srow += m[i][j];
                }
                for(int j=0;j<ncol;j++){
                    m[i][j] = m[i][j]/srow;
                }
            }
            // normalize column
            for(int j=0;j<ncol;j++){
                double scol = 0.0;
                for(int i=0;i<nrow;i++){
                    scol += m[i][j];
                }
                for(int i=0;i<nrow;i++){
                    m[i][j] = m[i][j]/scol;
                }
            }
            count++;
            if(count>=100){
                System.out.println("Warning: 100 iter of DSM");
                break;
            }
            not_dsm = DSM.TestDSM(m, thres);
        }
        return m;
    }
    public static boolean TestDSM(double[][] m, double thres){
        double s = 0.0, srow = 0.0, scol=0.0;
        for(int i=0;i<m.length;i++){
            srow = 0.0;
            for(int j=0;j<m[0].length;j++){
                srow += m[i][j];
            }
            s += Math.abs(srow-1);
        }
        for(int i=0;i<m[0].length;i++){
            scol = 0.0;
            for(int j=0;j<m.length;j++){
                scol += m[j][i];
            }
            s += Math.abs(scol-1);
        }
        if (s<thres){
            return true;
        }
        return false;
    }
}
