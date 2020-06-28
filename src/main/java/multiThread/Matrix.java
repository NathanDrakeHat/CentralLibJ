package multiThread;

import java.util.Objects;

public class Matrix {
    public double[][] matrixMultiply(double[][] A, double[][] B){
        Objects.requireNonNull(A);
        Objects.requireNonNull(B);
        if(A.length == 0 || B.length == 0){
            throw new IllegalArgumentException("not two dimension array.");
        }
        int r = A.length;
        int c = B[0].length;
        var res = new double[r][c];

        return res;
    }
}
