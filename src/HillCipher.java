import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

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


    private static final int NUM_IN_ALPHBET = 26;

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

        List<DenseVector<Real>> reals = new ArrayList<>();
        DenseMatrix<Real> real = DenseMatrix.valueOf(reals);

        if (real == null)
            System.out.println("Yup den är null");
    }

    private static DenseMatrix<Real> cipher(DenseMatrix<Real> K, DenseMatrix<Real> message) {

        return mod26(K.times(message));
    }

    private static DenseMatrix<Real> mod26(DenseMatrix<Real> matrix) {

        Real[][] arr = new Real[matrix.getNumberOfRows()][matrix.getNumberOfColumns()];
        for (int i = 0; i < matrix.getNumberOfRows(); i++) {
            for (int j = 0; j < matrix.getNumberOfColumns(); j++) {

                LargeInteger mod = LargeInteger.valueOf(matrix.get(i, j).longValue()).mod(
                        LargeInteger.valueOf(NUM_IN_ALPHBET));

                Real n = Real.valueOf(mod.longValue());
                arr[i][j] = n;
            }
        }
        return DenseMatrix.valueOf(arr);
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

        if (vectors.size() == 0)
            return null;

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
        line = line.replace("{", "");
        line = line.replace("}", "");
        line = line.replaceAll("\\s+", "");
        String[] numbers = line.split(",");

        List<Real> reals = new ArrayList<>();

        for (String s : numbers) {
            try {
                reals.add(Real.valueOf(Integer.parseInt(s)));
            } catch (Exception e) {
                return null;
            }
        }
        return DenseVector.valueOf(reals);
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

        List<DenseVector<Real>> vectors = new ArrayList<>();
        try (
                InputStream is = new FileInputStream(messageFileName);
                InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(isr)
        ) {
            boolean notDone = true;
            while (notDone) {
                List<Real> characters = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    int charact = reader.read();
                    if (charact == -1) {
                        notDone = false;
                        break;
                    }
                    if (charact % NUM_IN_ALPHBET != charact)
                        return null;

                    characters.add(Real.valueOf(charact));
                }
                vectors.add(DenseVector.valueOf(characters));
            }
        } catch (Exception e) {

            return null;
        }
        return DenseMatrix.valueOf(vectors);

    }
}
