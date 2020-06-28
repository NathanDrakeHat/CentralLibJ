package multiThread;


import java.util.Arrays;
import java.util.Objects;

public final class ParalleledFor{

    public static void forParallel(int start, int end, ForRunnable runnable){
        Objects.requireNonNull(runnable);
        int[] range = new int[end - start];
        int idx = 0;
        for(int i = start; i < end; i++){
            range[idx++] = i;
        }
        Arrays.stream(range).parallel().forEach(runnable::run);
    }

    public static double[] matrixVector(double[][] A, double[] x){
        Objects.requireNonNull(A);
        Objects.requireNonNull(x);
        if(x.length != A.length){
            throw new IllegalArgumentException("dimension not match,");
        }
        double[] y = new double[A.length];
        forParallel(0,A.length,(index)->y[index] = 0);
        forParallel(0,A.length,(i)->{
            for(int j = 0; j < A.length; j++){
                y[i] = y[i] + A[i][j]*x[j];
            }
        });
        return y;
    }
}
