package multiThread;


public class ParallelFor extends Thread{
    private final int limit;
    private final int current;
    private final ForRunnable runnable;
    private ParallelFor(int start, int limit, ForRunnable runnable){
        this.current = start;
        this.limit = limit;
        this.runnable = runnable;
    }

    @Override
    public void run(){
        if(current < limit - 1) {
            var next_thread = new ParallelFor(current + 1, limit, runnable);
            next_thread.start();
        }
        this.runnable.run(current);
    }

    public static void forParallel(int start, int end, ForRunnable runnable){
        var task = new ParallelFor(start,end,runnable);
        task.start();
    }

    public static double[] matrixVector(double[][] A, double[] x){
        double[] y = new double[A.length];
        forParallel(0,A.length,(index)->y[index] = 0);
        forParallel(0,A.length,(i)->{
            for(int j = 0; j < A.length; j++){
                y[i] += A[i][j]*x[j];
            }
        });
        return y;
    }
}
