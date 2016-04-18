import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Matrix;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    private static DenseMatrix<Real> getK(String KFileName) {

        if (!(new File(KFileName).isFile()))
            return null;


        // TODO read from file, and create DenseMatrix
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

    private static DenseVector<Real> getVector(String line) {

        // TODO parse lines to get vector
        return null;
    }

    private static DenseMatrix<Real> getMessageMatrix(String messageFileName) {

        if (!(new File(messageFileName).isFile()))
            return null;

        // TODO read text from file, and create DenseMatrix
        return null;

    }


}
