import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;

import java.io.*;

/**
 * A program used to encrypt a file using hill cipher algorithm
 *
 * @author Emil Lengman
 * @author Simon Enerstrand
 */
public class HillCipher {

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
        if ((messageMatrix = Util.getStringMatrixFromFile(messageFileName)) == null) {
            System.out.println("The file does not exist, or does not contain a valid message");
        }

        if(new File(saveFile).isFile()){
            if(!Util.overwrite(saveFile))
                return;
        }

        Util.writeToFile(saveFile, cipher(K, messageMatrix).transpose());
    }



    private static DenseMatrix<Real> cipher(DenseMatrix<Real> K, DenseMatrix<Real> message) {

        return Util.timesMod(K, message, Util.NUM_IN_ALPHBET);
    }
}
