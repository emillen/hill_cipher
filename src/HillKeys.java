import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.*;

import java.util.Random;

/**
 * A program that generates a matrix K, and its inverse
 * to be used as Hill cipher keys
 *
 * @author Emil Lengman
 * @author Simon Enerstrand
 */
public class HillKeys {

    public static void main(String[] args) {

        /*if (args.length != 2) {
            System.out.println("Usage: HillKeys <file>");
            return;
        }*/
        //String fileName = args[1];
        DenseMatrix<Real> K = generateK();
        System.out.println(K.toString()); // testing if it works
        Matrix<Real> inverse = K.inverse();
        /*writeToFile(fileName, K, inverse);*/
    }

    /**
     * Generates an invertible matrix
     *
     * @return a invertible matrix
     */
    private static DenseMatrix<Real> generateK() {
        // TODO Generate a invertible matrix
        DenseMatrix<Real> K;
        Random r = new Random();

        while (true) {
            Real[][] arr = {
                    {Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26))},
                    {Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26))},
                    {Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26))}
            };


            K = DenseMatrix.valueOf(arr);
            if (K.determinant().doubleValue() != 0)
                break;
        }

        return K;
    }

    /**
     * Writes the matrix, and inverse to a file
     *
     * @param fileName the name of the file to write to
     * @param matrix   the matrix
     * @param inverse  the inverse
     */
    private static void writeToFile(String fileName, DenseMatrix matrix, DenseMatrix inverse) {

        // TODO Write matrices and inverse to file

    }
}
