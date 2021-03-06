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

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: HillKeys <fileToSaveK> <fileToSaveD>");
            return;
        }

        String KFileName = args[0];
        String DFileName = args[1];
        File Kfile = new File(KFileName);
        File Dfile = new File(DFileName);

        if (Kfile.isFile()) {
            if (!Util.overwrite(KFileName)) {
                return;
            }
        }

        if (Dfile.isFile()) {
            if (!Util.overwrite(DFileName)) {
                return;
            }
        }

        DenseMatrix<Real> K = generateK();
        DenseMatrix<Real> D = generateD(K);
        writeToFile(KFileName, K);
        writeToFile(DFileName, D);
    }


    /**
     * Generates an invertible matrix with gcd(detM, 26) == 1
     *
     * @return a invertible matrix
     */
    private static DenseMatrix<Real> generateK() {
        DenseMatrix<Real> K;
        Random r = new Random();

        while (true) {
            Real[][] arr = {
                    {
                            Real.valueOf(r.nextInt(Util.NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(Util.NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(Util.NUM_IN_ALPHBET))
                    },
                    {
                            Real.valueOf(r.nextInt(Util.NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(Util.NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(Util.NUM_IN_ALPHBET))
                    },
                    {
                            Real.valueOf(r.nextInt(Util.NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(Util.NUM_IN_ALPHBET)),
                            Real.valueOf(r.nextInt(Util.NUM_IN_ALPHBET))
                    }
            };

            K = DenseMatrix.valueOf(arr);
            LargeInteger determinant = LargeInteger.valueOf(K.determinant().longValue());
            if (determinant.doubleValue() != 0 &&
                    determinant.gcd(LargeInteger.valueOf(Util.NUM_IN_ALPHBET)).equals(LargeInteger.valueOf(1)))
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
        LargeInteger numInAlphabet = LargeInteger.valueOf(Util.NUM_IN_ALPHBET);
        LargeInteger detLI = LargeInteger.valueOf(det.longValue());
        Real detInv = Real.valueOf(detLI.modInverse(numInAlphabet).longValue());

        DenseMatrix<Real> inverse = K.inverse().times(det.times(detInv));

        return Util.mod(inverse, Util.NUM_IN_ALPHBET);
    }

    /**
     * Writes the matrix to a file
     *
     * @param fileName the file to write to
     * @param matrix   the encryption matrix
     */
    private static void writeToFile(String fileName, DenseMatrix matrix) {

        PrintWriter writer;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (Exception e) {
            System.out.println("Something went terribly wrong");
            return;
        }

        writer.println(matrix.toString());
        writer.close();
    }
}
