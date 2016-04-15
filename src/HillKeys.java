import org.jscience.mathematics.vector.Float64Matrix;

/**
 * Created by Emil Lengman on 2016-04-15.
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

    private static Float64Matrix generateK() {
        // TODO Generate a invertible matrix
        return null;
    }

    private static void writeToFile(String fileName, Float64Matrix matrix, Float64Matrix inverse) {

        // TODO Write matrices and inverse to file

    }
}
