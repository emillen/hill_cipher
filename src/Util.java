import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A util class for all the programs
 *
 * @author Emil Lengman
 * @author Simon Enerstrand
 */
class Util {

    static final int NUM_IN_ALPHBET = 26;

    /**
     * Multiplies a two matrices and then performs a modulo operation on the resulting matrix
     *
     * @param a            first matrix
     * @param b            second matrix
     * @param moduloNumber the modulo number
     * @return a matrix
     */
    static DenseMatrix<Real> timesMod(DenseMatrix<Real> a, DenseMatrix<Real> b, int moduloNumber) {

        return mod(a.times(b), moduloNumber);
    }

    /**
     * Performs mod operation to a matrix
     *
     * @param matrix       the matrix
     * @param moduloNumber the modulo number
     * @return a matrix
     */
    static DenseMatrix<Real> mod(DenseMatrix<Real> matrix, int moduloNumber) {

        Real[][] arr = new Real[matrix.getNumberOfRows()][matrix.getNumberOfColumns()];
        for (int i = 0; i < matrix.getNumberOfRows(); i++) {
            for (int j = 0; j < matrix.getNumberOfColumns(); j++) {

                LargeInteger mod = LargeInteger.valueOf(matrix.get(i, j).longValue()).mod(
                        LargeInteger.valueOf(moduloNumber));

                Real n = Real.valueOf(mod.longValue());
                arr[i][j] = n;
            }
        }
        return DenseMatrix.valueOf(arr);
    }

    /**
     * Asks a the user if he/she wants to overwrite a file, and returns a boolean representing her/his answer
     *
     * @param filename the name of the file
     * @return true if yes, false if no
     */
    static boolean overwrite(String filename) {

        while (true) {
            System.out.println("File " + filename + " exists. Do you want to overwrite file? (yes or no)");
            Scanner input = new Scanner(System.in);
            String a = input.next();

            if (a.equals("yes")) {
                return true;
            } else if (a.equals("no")) {
                return false;
            }
        }
    }

    /**
     * Reads a matrix from a file
     *
     * @param filename the name of the file
     * @return a DenseMatrix
     */
    static DenseMatrix<Real> getMatrixFromFile(String filename) {

        if (!(new File(filename).isFile()))
            return null;

        List<DenseVector<Real>> vectors = new ArrayList<>();

        try (
                InputStream is = new FileInputStream(filename);
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
    static DenseMatrix<Real> getStringMatrixFromFile(String messageFileName) {

        if (!(new File(messageFileName).isFile()))
            return null;

        List<DenseVector<Real>> vectors = new ArrayList<>();
        try (
                InputStream is = new FileInputStream(messageFileName);
                InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(isr)
        ) {
            int last = 0;
            while (true) {
                List<Real> characters = new ArrayList<>();
                last = getCharacters(reader, characters, last);

                if (characters.size() == 0)
                    break;

                vectors.add(DenseVector.valueOf(characters));

                if(last == -1)
                    break;
            }
        } catch (Exception e) {

            return null;
        }
        return DenseMatrix.valueOf(vectors).transpose();

    }

    private static int getCharacters(BufferedReader reader, List<Real> characters, int last) throws IOException {
        for (int i = 0; i < 3; i++) {
            int charact = reader.read();
            if (charact == -1) {
                pad(characters, i , last);
                return -1;
            }
            charact -= 65;
            if (charact % Util.NUM_IN_ALPHBET != charact)
                return -1;

            characters.add(Real.valueOf(charact));
            last = charact;
        }
        return last;
    }

    private static void pad(List<Real> characters, int i, int last){

        System.out.println(last);
        System.out.println(i);
        int pad = padNumber(i);
        if(last == 1 && i == 0)
            pad = 3;
        for (int j = 0; j < pad; j++) {
            characters.add(Real.valueOf(pad));
        }
    }

    private static int padNumber(int i) {

        switch (i) {

            case 1:
                return 2;
            case 2:
                return 1;
            default:
                return 0;
        }
    }
}
