package multiThread;


import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntFunction;

public final class ParalleledFor{
    public static void forParallel(ExecutorService pool, int start, int end, IntFunction<Runnable> get_runnable){
        Objects.requireNonNull(get_runnable);
        CountDownLatch count_down_latch = new CountDownLatch(end-start);
        for(int i = start; i < end; i++){
            Runnable task = get_runnable.apply(i);
            pool.submit(() -> {
                task.run();
                count_down_latch.countDown();
            });
        }
        try {
            count_down_latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
