import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A program used to encrypt a file using hill cipher algorithm
 *
 * @author Emil Lengman
 * @author Simon Enerstrand
 */
public class HillCipher {


    private final int NUM_IN_ALPHBET = 26;

    public static void main(String[] args) {

        // TODO write program
        if (args.length != 2) {

            System.out.println("Usage: HillEncrypt <KFile> <messageFile>");
        }

        String encryptFileName = args[0];
        String messageFileName = args[1];

        DenseMatrix<Real> K;

        if ((K = getK(encryptFileName)) == null) {
            System.out.println("File <" + encryptFileName + "> does not exist, or does not contain a matrix");
        }
        DenseMatrix<Real> messageMatrix;
        if ((messageMatrix = getMessageMatrix(messageFileName)) == null) {
            System.out.println("The file does not exist, or does not contain a valid message");
        }

        System.out.println(cipher(K, messageMatrix).toString());
    }

    private static DenseMatrix<Real> cipher(DenseMatrix<Real> K, DenseMatrix<Real> message) {

        return K.times(message);
    }

    /**
     * Reads a encryption matrix K from a file
     *
     * @param KFileName the name of the file
     * @return a DenseMatrix
     */
    private static DenseMatrix<Real> getK(String KFileName) {

        if (!(new File(KFileName).isFile()))
            return null;

        List<DenseVector<Real>> vectors = new ArrayList<>();

        try (
                InputStream is = new FileInputStream(KFileName);
                InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(isr)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {

                vectors.add(getVector(line));
            }
        } catch (Exception e) {

            return null;
        }
        return DenseMatrix.valueOf(vectors);
    }

    /**
     * Returns a vector that is parsed from a string
     *
     * @param line the string to parse
     * @return a DenseVector that the string represents
     */
    private static DenseVector<Real> getVector(String line) {

        // TODO parse lines to get vector
        return null;
    }

    /**
     * Creates a matrix from the contents of a file
     *
     * @param messageFileName the name of the file
     * @return a DenseMatrix that represents the content of the file
     */
    private static DenseMatrix<Real> getMessageMatrix(String messageFileName) {

        if (!(new File(messageFileName).isFile()))
            return null;

        // TODO read text from file, and create DenseMatrix
        return null;

    }


}
