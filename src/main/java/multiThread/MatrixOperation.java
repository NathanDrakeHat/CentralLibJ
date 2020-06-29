package multiThread;

import java.util.Objects;
import static multiThread.ParalleledFor.*;

public class MatrixOperation {
    public static double[][] matrixMultiply(double[][] A, double[][] B){
        Objects.requireNonNull(A);
        Objects.requireNonNull(B);
        if(A.length == 0 || B.length == 0){
            throw new IllegalArgumentException("not two dimension array.");
        }else if(A[0].length != B.length){
            throw new IllegalArgumentException("two matrices'dimensions not match");
        }
        int count = B.length;
        int r = A.length;
        int c = B[0].length;
        var res = new double[r][c];
        forParallel(0, r, (i)-> ()->
                forParallel(0, c,(j)->()->{
                    for(int k = 0; k < count; k++){
                        res[i][j] += A[i][k]*B[k][j];
                    }
                }));
        return res;
    }

    public static double[] matrixVector(double[][] A, double[] x){
        Objects.requireNonNull(A);
        Objects.requireNonNull(x);
        if(x.length != A.length){
            throw new IllegalArgumentException("dimension not match,");
        }
        double[] y = new double[A.length];
        forParallel(0,A.length,(i)-> ()->y[i] = 0);
        forParallel(0,A.length,(i)-> ()->{
            for(int j = 0; j < A.length; j++){
                y[i] = y[i] + A[i][j]*x[j];
            }
        });
        return y;
    }
}
