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
    public void Load(String filename, String signal, int dim){
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            int i=0, j=0;
            double[][] Mat = new double[dim][dim];
            while(true){
                line = br.readLine();
                if(line==null){
                    break;
                }
                line = line.trim();
                double tmp = Double.parseDouble(line);
                if(i>=dim){
                    break;
                }
                if(j>=dim){
                    i++;
                    j = 0;
                }
                Mat[i][j] = tmp;
            }
            if(signal.equalsIgnoreCase("signal")){
                this.SignalMat = new Matrix(Mat);
            }
            else{
                this.NullMat = new Matrix(Mat);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    public void NormalizeMat(){
        
    }
}
