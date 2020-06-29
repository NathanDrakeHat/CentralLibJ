package multiThread;


import java.util.Objects;

import java.util.function.IntFunction;

public final class ParalleledFor{
    private static final class SpawnThread extends Thread{
        private final int current;
        private final int end;
        private final IntFunction<Runnable> get_task;

        SpawnThread(int current, int end, IntFunction<Runnable> get_task){
            this.current = current;
            this.end = end;
            this.get_task = get_task;
        }
        @Override
        public void run(){
            if(current < end - 1){
                var next_thread = new SpawnThread(current+1,end, get_task);
                next_thread.start();
                get_task.apply(current).run();
                try {
                    next_thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                get_task.apply(current).run();
            }
        }
    }
    public static void forParallel(int start, int end, IntFunction<Runnable> get_runnable){
        Objects.requireNonNull(get_runnable);
        var thread = new SpawnThread(start,end,get_runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
