import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;

import java.io.File;

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

            System.out.println("Usage: HillEncrypt <encryptKeyFile> <messageFile>");
        }

        String encryptFileName = args[0];
        String messageFileName = args[1];

        DenseMatrix<Real> encryptionKey;

        if((encryptionKey = getEncryptionKey(encryptFileName)) == null) {
            System.out.println("File <" + encryptFileName + "> does not exist, or does not contain a matrix");
        }

    }

    private static DenseMatrix<Real> getEncryptionKey(String encryptFileName){

        if(!(new File(encryptFileName).isFile()))
            return null;

        // TODO read from file, and create DenseMatrix

        return null;
    }

    private static DenseMatrix<Real> getMessageMatrix(String messageFileName){

        if(!(new File(messageFileName).isFile()))
            return null;

        // TODO read text from file, and create DenseMatrix

        return null;

    }
}
