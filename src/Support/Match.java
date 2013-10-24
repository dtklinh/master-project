package Support;

import java.util.HashMap;
//import h2r.ReducedAlphabet;

public class Match {

    /*This tiny method matches the randomSet counterMatrix against the top-75 counterMatrix.*
     *Only entries which are higher than in the randomSet counterMatrix survive*/
    public static double[][] match(double[][] matrixOne, double[][] matrixTwo, HashMap<String, Integer> indeces) {
        /**
         * matrixOne =  rndMatrix
         * matrixTwo =simMatrix
         */
        int placeOne = -1;
        int placeTwo = -1;

        for (String pairOne : indeces.keySet()) {
            for (String pairTwo : indeces.keySet()) {
                placeOne = indeces.get(pairOne);
                placeTwo = indeces.get(pairTwo);
                if (!(placeOne == placeTwo)) {
                    if (matrixOne[placeOne][placeTwo] > matrixTwo[placeOne][placeTwo]) {
                        matrixTwo[placeOne][placeTwo] = 0.0;
                    } 
                }
            }
        }

        return matrixTwo;

    }

    /*This method tests whether the mutation in both columns happens intra-cluster-wise or inter-cluster-wise.
     *If and only if both mutations are inter-cluster mutations "true" will be returned, otherwise the return value will be "false".*/
//    private static boolean categorize(String pairOne, String pairTwo) {
//
//        char[][] categories = ReducedAlphabet.getAlphabet();
//        int catOne = -1;
//        int catTwo = -1;
//        int catThree = -1;
//        int catFour = -1;
//
//        for (int i = 0; i < categories.length; ++i) {
//            for (int j = 0; j < categories[i].length; ++j) {
//                if (pairOne.charAt(0) == categories[i][j]) {
//                    catOne = i;
//                }
//                if (pairOne.charAt(1) == categories[i][j]) {
//                    catTwo = i;
//                }
//                if (pairTwo.charAt(0) == categories[i][j]) {
//                    catThree = i;
//                }
//                if (pairTwo.charAt(1) == categories[i][j]) {
//                    catFour = i;
//                }
//            }
//        }
//
//        if ((catOne != catThree) && (catTwo != catFour)) {
//            return true;
//        }
//
//        return false;
//    }
}
