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


    private static final int NUM_IN_ALPHBET = 26;

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println(args.length);
            System.out.println("Usage: HillEncrypt <KFile> <messageFile> <cipherFile>");
            return;
        }

        String encryptFileName = args[0];
        String messageFileName = args[1];
        String saveFile = args[2];
        DenseMatrix<Real> K;

        if ((K = Util.getMatrixFromFile(encryptFileName)) == null) {
            System.out.println("File <" + encryptFileName + "> does not exist, or does not contain a matrix");
        }
        DenseMatrix<Real> messageMatrix;
        if ((messageMatrix = getMessageMatrix(messageFileName)) == null) {
            System.out.println("The file does not exist, or does not contain a valid message");
        }

        if(new File(saveFile).isFile()){
            if(!Util.overwrite(saveFile))
                return;
        }

        writeToFile(saveFile, cipher(K, messageMatrix).transpose());
    }



    private static DenseMatrix<Real> cipher(DenseMatrix<Real> K, DenseMatrix<Real> message) {

        return Util.timesMod(K, message, NUM_IN_ALPHBET);
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
            while (true) {
                List<Real> characters = getCharacters(reader);

                if(characters == null || characters.size() == 0)
                    break;
                vectors.add(DenseVector.valueOf(characters));
            }
        } catch (Exception e) {

            return null;
        }
        return DenseMatrix.valueOf(vectors).transpose();

    }

    private static List<Real> getCharacters(BufferedReader reader) throws IOException{
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
            if (charact % NUM_IN_ALPHBET != charact)
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
    private static void writeToFile(String fileName, DenseMatrix<Real> matrix) {

        PrintWriter writer;

        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (Exception e) {
            System.out.println("Something went terribly wrong");
            return;
        }

        for(int i = 0; i < matrix.getNumberOfRows(); i++){
            for(int j = 0; j < matrix.getNumberOfColumns(); j++){
                writer.write(matrix.get(i, j).intValue() + 65);
            }
        }
        writer.close();
    }
}
