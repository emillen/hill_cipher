import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;

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
        //DenseMatrix<Real> D = generateD(K.inverse());
        /*writeToFile(fileName, K, inverse);*/
    }

    /**
     * Generates an invertible matrix with gcd(detM, 26) != 1
     *
     * @return a invertible matrix
     */
    private static DenseMatrix<Real> generateK() {
        DenseMatrix<Real> K;
        Random r = new Random();

        while (true) {
            Real[][] arr = {
                    {Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26))},
                    {Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26))},
                    {Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26)), Real.valueOf(r.nextInt(26))}
            };

            K = DenseMatrix.valueOf(arr);
            LargeInteger determinant = LargeInteger.valueOf(K.determinant().longValue());
            if (determinant.doubleValue() != 0 &&
                    determinant.gcd(LargeInteger.valueOf(26)).equals(LargeInteger.valueOf(1)))
                break;
        }

        return K;
    }


    /**
     * Writes the matrix, and inverse to a file
     *
     * @param fileName the name of the file to write to
     * @param K        the encryption matrix
     * @param D        the decryption matrix
     */
    private static void writeToFile(String fileName, DenseMatrix K, DenseMatrix D) {

        // TODO Write matrices and inverse to file

    }
}
