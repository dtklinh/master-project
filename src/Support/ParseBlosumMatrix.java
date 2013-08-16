/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

//import h2rFrame.MainFrame;
//import h2rRevised.Output;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mgueltas
 */
public class ParseBlosumMatrix {

    ArrayList<String> pair = new ArrayList<String>();
    ArrayList<Integer> value = new ArrayList<Integer>();
//    MainFrame mf = new MainFrame();

    public double[][] parseBlosumMatrix(HashMap<String, Integer> pairIndex, double[][] simMatrix) {
        try {
//            BufferedReader reader = new BufferedReader(new FileReader("blosum62Matrix.txt"));
             BufferedReader reader = new BufferedReader(new FileReader("blosum62.txt"));
            String lineReader = null;
            Object[][] matrix = new Object[21][21];
//              matrix[0][0]="";
            int i = 0;
            while (true) {
                lineReader = reader.readLine();
                if (lineReader == null) {
                    break;
                }
                String[] temp = lineReader.split("\t");
                for (int j = 0; j < temp.length; j++) {
                    matrix[i][j] = temp[j];
                }
                ++i;
            }
            int k = 0;
            for (i = 1; i < matrix.length; i++) {
                for (int j = 1; j < matrix.length; j++) {
                    pair.add(matrix[i][0] + "" + matrix[j][0]);
                    value.add(Integer.valueOf(matrix[i][j].toString()));
                    k++;
                }
            }
//            for (i = 0; i < finalMatrix.length; i++) {
//                      System.out.print(finalMatrix[i][0] + " :" +finalMatrix[i][1]);
//                      System.out.println();
//            }
//              i=0;
//              for (String pairOne : pairIndex.keySet()) {
//                   System.out.println(i+" : "+pairOne+" : "+pairIndex.get(pairOne));
//                   i++;
//              }

            return match(simMatrix, pairIndex);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*This tiny method matches the randomSet counterMatrix against the top-75 counterMatrix.*
     *Only entries which are higher than in the randomSet counterMatrix survive*/
    public double[][] match(double[][] simMatrix, HashMap<String, Integer> indeces) {

        int placeOne = -1;
        int placeTwo = -1;

        for (String pairOne : indeces.keySet()) {
            for (String pairTwo : indeces.keySet()) {
                placeOne = indeces.get(pairOne);
                placeTwo = indeces.get(pairTwo);
                if (!(placeOne == placeTwo)) {
                    boolean check = compareBlosum(pairOne, pairTwo);
                    if (!check) {
                        simMatrix[placeOne][placeTwo] = 0.0;
                    }

                }
            }
        }
//       Output.outputAsList("mehmet2.out", simMatrix, indeces);
//            int ounter=0;
//        for(int i=0;i<simMatrix.length;i++){
//            for(int j=0;j<simMatrix.length;j++)
//            if(simMatrix[i][j]>0)ounter++;
//        }
//             System.out.println("==>"+ ounter);
        return simMatrix;

    }

    /*This method tests whether the mutation in both columns happens intra-cluster-wise or inter-cluster-wise.
     *If and only if both mutations are inter-cluster mutations "true" will be returned, otherwise the return value will be "false".*/
    private boolean compareBlosum(String pairOne, String pairTwo) {

        int catOne = -1;
        int catTwo = -1;
        int catThree = -1;
        int catFour = -1;
        char one = pairOne.charAt(0);
        char two = pairOne.charAt(1);
        char three = pairTwo.charAt(0);
        char four = pairTwo.charAt(1);
        System.out.println("******************************************");
        boolean compare = false;
        String pOne = one + "" + three;
        String pTwo = two + "" + four;
        int blosumValue = value.get(pair.indexOf(pOne));
        System.out.print(pOne + " : " + pTwo + " : " + blosumValue + " : ");

        if (blosumValue < 0) {
            blosumValue = value.get(pair.indexOf(pTwo));
            if (blosumValue < 0) {
                compare = true;
                System.out.print(blosumValue);
            }
        }
        System.out.println(" :=> " + compare);
        return compare;
    }
}
