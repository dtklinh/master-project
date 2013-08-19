package Support;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class Output {

    /*Writes the given double[][] matrix to the file (filename) in form of a list*/
     public static void outputAsList(String filename, double[][] matrix, HashMap<String, Integer> indeces) {

        try {
            String pairOne = "";
            String pairTwo = "";

            File list = new File(filename);
            if (!list.exists());
            {
                list.createNewFile();
            }
            FileWriter writer = new FileWriter(list);

            for (int i = 0; i < matrix.length; ++i) {
                for (int j = 0; j < matrix[i].length; ++j) {
                    pairOne = "";
                    pairTwo = "";
                    for (String pair : indeces.keySet()) {
                        if (indeces.get(pair) == i) {
                            pairOne = pair;
                        }
                        if (indeces.get(pair) == j) {
                            pairTwo = pair;
                        }
                        if ( (pairOne != "") && (pairTwo != "") ) {
                            break;
                        }
                    }
                    writer.write(pairOne + "-" + pairTwo + ": " + matrix[i][j] + "\n");
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
