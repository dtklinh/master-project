package Support;

import java.util.HashMap;

public class CalculateSimMatrix {

   /*This method calculates the similarity matrix ("omega")*
    *Parameters are the counter matrix containing the pair of pair frequencies and the indeces*/
   public static double[][] process( double[][] counter, HashMap<String, Integer> index ) {
        
        double[][] sim = new double[counter.length][counter.length];
        double[] edgeDistribution = new double[counter.length];
        double edge = 0.0;
        double value = 0.0;

        for (String pair : index.keySet()) {
            edge = 0.0;
            for (int i = 0; i < counter.length; ++i) {
                edge += counter[index.get(pair)][i];
            }
            edgeDistribution[index.get(pair)] = edge;
        }

        for (String pair : index.keySet()) {
            for (String otherPair : index.keySet()) {
                if ( !(edgeDistribution[index.get(pair)] == 0) && !(edgeDistribution[index.get(otherPair)] == 0) && !(counter[index.get(pair)][index.get(otherPair)] == 0) ) {
                    value = (Math.log((counter[index.get(pair)][index.get(otherPair)] / (edgeDistribution[index.get(pair)] * edgeDistribution[index.get(otherPair)]))) / Math.log(2));
                    /*If and only if the value is greater than zero it is being added to the appropriate value in the counterMatrix*/
                    if (value > 0) {
                        sim[index.get(pair)][index.get(otherPair)] += value;
                    }
                    else
                        sim[index.get(pair)][index.get(otherPair)] = 0.0;
                }
                else
                    sim[index.get(pair)][index.get(otherPair)] = 0.0;
            }
        }
//        int ounter=0;
//        for(int i=0;i<sim.length;i++){
//            for(int j=0;j<sim.length;j++)
//            if(sim[i][j]>0)ounter++;
//        }
//        System.out.println("==>"+ ounter);
    //    Output.outputAsList("mehmetSim2.out", sim, index);
//        System.exit(0);

        return sim;
        
    }

}
