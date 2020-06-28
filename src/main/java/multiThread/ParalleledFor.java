package multiThread;


import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class ParalleledFor{
    private static final int cores = Runtime.getRuntime().availableProcessors();

    public static void forParallel(int start, int end, IntFunction<Runnable> for_get_runnable){
        Objects.requireNonNull(for_get_runnable);
        var pool = Executors.newFixedThreadPool(cores);
        CountDownLatch count_down_latch = new CountDownLatch(end-start);
        var add_count_down = new Function<Runnable,Runnable>(){
            @Override
            public Runnable apply(Runnable runnable){
                return ()->{
                    runnable.run();
                    count_down_latch.countDown();
                };
            }
        };
        for(int i = start; i < end; i++){
            pool.submit(add_count_down.apply(for_get_runnable.apply(i)));
        }
        try {
            count_down_latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
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
