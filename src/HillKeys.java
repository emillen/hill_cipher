import org.jscience.mathematics.vector.Float64Matrix;

/**
 *  A program that generates a matrix K, and its inverse
 *  to be used as Hill cipher keys
 *  @author Emil Lengman
 *  @author Simon Enerstrand
 */
public class HillKeys {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: HillKeys <file>");
            return;
        }
        String fileName = args[1];
        Float64Matrix K = generateK();
        Float64Matrix inverse = K.inverse();
        writeToFile(fileName, K, inverse);
    }


    /**
     * Generates a invertible matrix
     * @return a invertible matrix
     */
    private static Float64Matrix generateK() {
        // TODO Generate a invertible matrix
        return null;
    }

    /**
     * Writes, the matrix, and inverse to a file
     * @param fileName  the name of the file to write to
     * @param matrix    the matrix
     * @param inverse   the inverse
     */
    private static void writeToFile(String fileName, Float64Matrix matrix, Float64Matrix inverse) {

        // TODO Write matrices and inverse to file

    }
}
