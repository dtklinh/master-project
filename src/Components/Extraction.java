/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.util.ArrayList;

/**
 *
 * @author linh
 */
public class Extraction {
    public static ArrayList<Character> EntropyChange3(double[] entropy, double thres){
        // calculate average
        thres =0.0;
        for(int i=0;i<entropy.length;i++){
            thres += entropy[i];
        }
        thres = thres/entropy.length;
        ArrayList<Character> res = new ArrayList<Character>();
        for(int i=0;i<entropy.length-1;i++){
            if((entropy[i]<=thres && entropy[i+1]>thres) ||
                    (entropy[i+1]<=thres && entropy[i]>thres)){
                res.add('A');
            }
            else if(entropy[i]<=thres && entropy[i+1]<=thres){
                res.add('B');
            }
            else if(entropy[i]>thres && entropy[i+1]>thres){
                res.add('C');
            }
        }
        return res;
    }
}
