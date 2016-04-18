import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;

import java.util.Scanner;

/**
 * Created by daseel on 2016-04-18.
 */
public class Util {

    public static DenseMatrix<Real> timesMod(DenseMatrix<Real> a, DenseMatrix<Real> b, int moduloNumber){

        return mod(a.times(b), moduloNumber);
    }


    public static DenseMatrix<Real> mod(DenseMatrix<Real> matrix, int moduloNumber) {

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
    public static boolean overwrite(String filename) {

        while (true) {
            System.out.println("File " + filename +" exists. Do you want to overwrite file? (yes or no)");
            Scanner input = new Scanner(System.in);
            String a = input.next();

            if (a.equals("yes")) {
                return true;
            } else if (a.equals("no")) {
                return false;
            }
        }
    }
}