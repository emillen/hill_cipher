import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;

import java.io.File;

/**
 * A program used to decrypt a file using hill cipher algorithm
 *
 * @author Emil Lengman
 * @author Simon Enerstrand
 */
public class HillDecipher {


    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Usage: HillDecipher <fileContainingD> <fileContainingCipherText> <fileToSaveTo>");
            return;
        }

        String DFile = args[0];
        String CFile = args[1];
        String saveFile = args[2];

        DenseMatrix<Real> D;

        if ((D = Util.getMatrixFromFile(DFile)) == null) {
            System.out.println("File <" + DFile + "> does not exist, or does not contain a matrix");
        }

        if ((new File(saveFile).isFile())) {

            if (!(Util.overwrite(saveFile)))
                return;
        }

        DenseMatrix<Real> cipherMatrix;
        if ((cipherMatrix = Util.getStringMatrixFromFile(CFile)) == null) {
            System.out.println("The file does not exist, or does not contain a valid message");
        }

        Util.writeToFile(saveFile, decipher(D, cipherMatrix).transpose());
    }

    private static DenseMatrix<Real> decipher(DenseMatrix<Real> K, DenseMatrix<Real> message) {

        return Util.timesMod(K, message, Util.NUM_IN_ALPHBET);
    }
}
