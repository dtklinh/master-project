package Support;

public class NormalizeMatrix {

    /*This method divides each value in the given double[][] (matrix) by the sum of all values*
     *in order to determine the probabilities for every pair-of-pairs combination*/
    public static double[][] normalize(double[][] matrix) {

        double sum = 0.0;

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                sum += matrix[i][j];
            }
        }

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                matrix[i][j] = (matrix[i][j] / sum);
            }
        }

        return matrix;

    }
}
