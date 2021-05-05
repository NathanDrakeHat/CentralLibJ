package org.nathan.algorithmsJava.tools;

import java.util.concurrent.Callable;


public final class FunctionTimer{
    private long time;

    public void proxyRun(Runnable runnable){
        long t1 = System.nanoTime();
        runnable.run();
        long t2 = System.nanoTime();
        time += (t2 - t1);
    }

    public <T> T proxyApply(Callable<T> callable){
        long t1 = System.nanoTime();
        T t;
        try{
            t = callable.call();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        long t2 = System.nanoTime();
        time += (t2 - t1);
        return t;
    }

    public long getNanoTime(){
        return time;
    }

    public void reset(){
        time = 0;
    }
}
