import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;

import java.io.File;
import java.io.PrintWriter;

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

        writeToFile(saveFile, decipher(D, cipherMatrix).transpose());
    }


    private static DenseMatrix<Real> decipher(DenseMatrix<Real> K, DenseMatrix<Real> message) {

        return Util.timesMod(K, message, Util.NUM_IN_ALPHBET);
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

        for (int i = 0; i < matrix.getNumberOfRows() - 1; i++) {

            for (int j = 0; j < matrix.getNumberOfColumns(); j++) {
                writer.write(matrix.get(i, j).intValue() + 65);
            }
        }

        writeLastRow(writer, matrix);
        writer.close();
    }

    private static void writeLastRow(PrintWriter writer, DenseMatrix<Real> matrix){

        DenseVector<Real> lastRow = matrix.getRow(matrix.getNumberOfRows() - 1);
        int sameCount = countSame(lastRow);
        int amountToWrite = lastRow.getDimension();
        if(sameCount == lastRow.get(amountToWrite - 1).intValue())
            amountToWrite -= sameCount;

        for(int i = 0; i < amountToWrite; i++)
            writer.write(lastRow.get(i).intValue() + 65);
    }

    private static int countSame(DenseVector<Real> row){

        int last = row.getDimension() - 1;
        int value = row.get(last).intValue();

        int count = 1;
        for(int i = last - 1; i >= 0; i--){
            int iValue = row.get(i).intValue();
            if(value == iValue)
                count++;
            else
                break;
        }

        return count;
    }
}
