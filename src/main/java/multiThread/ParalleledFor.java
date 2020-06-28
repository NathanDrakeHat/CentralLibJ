package multiThread;


import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public final class ParalleledFor{
    private static final int cores = Runtime.getRuntime().availableProcessors();

    private static final class ParalleledForThread extends Thread{
        private final int limit;
        private final AtomicInteger current;
        private final ForRunnable runnable;
        private final CountDownLatch thread_count;

        private ParalleledForThread(AtomicInteger index, int limit, ForRunnable runnable, CountDownLatch thread_count){
            this.current = index;
            this.limit = limit;
            this.runnable = runnable;
            this.thread_count = thread_count;
        }

        @Override
        public void run(){
            int index = current.getAndIncrement();
            while(index < limit){
                this.runnable.run(index);
                index = current.getAndIncrement();
            }
            thread_count.countDown();
        }
    }

    public static void forParallel(int start, int end, ForRunnable runnable){
        Objects.requireNonNull(runnable);
        AtomicInteger index = new AtomicInteger(start);
        ExecutorService pool = Executors.newFixedThreadPool(cores);
        CountDownLatch thread_count = new CountDownLatch(cores);
        for(int i = 0; i < cores; i++){
            var thread = new ParalleledForThread(index,end,runnable,thread_count);
            pool.submit(thread);
        }
        try{
            thread_count.await();
        }catch (InterruptedException e){
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
        forParallel(0,A.length,(index)->y[index] = 0);
        forParallel(0,A.length,(i)->{
            for(int j = 0; j < A.length; j++){
                y[i] = y[i] + A[i][j]*x[j];
            }
        });
        return y;
    }
}
