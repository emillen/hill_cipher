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
            while (true) {
                List<Real> characters = getCharacters(reader);

                if (characters == null || characters.size() == 0)
                    break;
                vectors.add(DenseVector.valueOf(characters));
            }
        } catch (Exception e) {

            return null;
        }
        return DenseMatrix.valueOf(vectors).transpose();

    }

    private static List<Real> getCharacters(BufferedReader reader) throws IOException {
        List<Real> characters = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int charact = reader.read();
            if (charact == -1) {
                if (i == 0)
                    break;
                int pad = 3 % i;
                for (int j = 0; j < pad; j++) {
                    characters.add(Real.valueOf(pad));
                }
                break;
            }
            charact -= 65;
            if (charact % Util.NUM_IN_ALPHBET != charact)
                return null;

            characters.add(Real.valueOf(charact));
        }

        return characters;
    }

    /**
     * Writes the matrix to a file
     *
     * @param fileName the file to write to
     * @param matrix   the encryption matrix
     */
    static void writeToFile(String fileName, DenseMatrix<Real> matrix) {

        PrintWriter writer;

        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (Exception e) {
            System.out.println("Something went terribly wrong");
            return;
        }

        for (int i = 0; i < matrix.getNumberOfRows(); i++) {
            for (int j = 0; j < matrix.getNumberOfColumns(); j++) {
                writer.write(matrix.get(i, j).intValue() + 65);
            }
        }
        writer.close();
    }
}
