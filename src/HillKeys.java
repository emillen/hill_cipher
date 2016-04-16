import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

/**
 * A program that generates a matrix K, and its inverse
 * to be used as Hill cipher keys
 *
 * @author Emil Lengman
 * @author Simon Enerstrand
 */
public class HillKeys {

    private final static int NUM_IN_ALPHBET = 26;

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: HillKeys <file>");
            return;
        }
        String fileName = args[0];
        File file = new File(fileName);

        if(file.exists() && file.isDirectory()){
            System.out.println("File is a directory!");
            System.out.println("Usage: HillKeys <file>");
            return;
        } else if(file.exists()){
            System.out.println("File exists. Do you want to overwrite file?");
            return;
        }


        //;
        DenseMatrix<Real> K = generateK();
        System.out.println(K.toString()); // testing if it works
        DenseMatrix<Real> D = generateD(K);
        System.out.println(D.toString());
        writeToFile(fileName, K, D);
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
                    {
                            Real.valueOf(r.nextInt(NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(NUM_IN_ALPHBET))
                    },
                    {
                            Real.valueOf(r.nextInt(NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(NUM_IN_ALPHBET))
                    },
                    {
                            Real.valueOf(r.nextInt(NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(NUM_IN_ALPHBET))
                    }
            };

            K = DenseMatrix.valueOf(arr);
            LargeInteger determinant = LargeInteger.valueOf(K.determinant().longValue());
            if (determinant.doubleValue() != 0 &&
                    determinant.gcd(LargeInteger.valueOf(NUM_IN_ALPHBET)).equals(LargeInteger.valueOf(1)))
                break;
        }

        return K;
    }


    /**
     * Generates the decryption matrix D from the matrix K
     *
     * @param K the original matrix
     * @return the decryption matrix D
     */
    private static DenseMatrix<Real> generateD(DenseMatrix<Real> K) {

        Real det = K.determinant();
        Real detInv = Real.valueOf(LargeInteger.valueOf(det.longValue())
                .modInverse(LargeInteger.valueOf(NUM_IN_ALPHBET)).longValue());

        DenseMatrix<Real> inverse = K.inverse().times(det.times(detInv));

        Real[][] arr = new Real[inverse.getNumberOfRows()][inverse.getNumberOfColumns()];
        for (int i = 0; i < inverse.getNumberOfRows(); i++) {
            for (int j = 0; j < inverse.getNumberOfColumns(); j++) {

                LargeInteger mod = LargeInteger.valueOf(inverse.get(i, j).longValue()).mod(
                        LargeInteger.valueOf(NUM_IN_ALPHBET));

                Real n = Real.valueOf(mod.longValue());
                arr[i][j] = n;
            }
        }

        return DenseMatrix.valueOf(arr);
    }

    /**
     * Writes the matrix, and inverse to a file
     *
     * @param fileName the file to write to
     * @param K        the encryption matrix
     * @param D        the decryption matrix
     */
    private static void writeToFile(String fileName, DenseMatrix K, DenseMatrix D) {

        PrintWriter writer;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (Exception e) {
            System.out.println("Something went terribly wrong");
            return;
        }

        writer.println(K.toString());
        writer.println(D.toString());
        writer.close();
    }
}
