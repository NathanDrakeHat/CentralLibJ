package multiThread;

public class Fib extends Thread{
    private final int n;
    private int res;

    private Fib(int n){ this.n = n; }

    @Override
    public void run(){
        if(n <= 1){
            res = n;
        }else{
            var t1 = new Fib(n-1);
            var t2 = new Fib(n-2);
            t1.start();
            t2.start();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            res = t1.res + t2.res;
        }
    }

    private int getRes(){ return res; }

    public static int getFibonacciSequence(int n){
        var t = new Fib(n);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t.getRes();
    }
}
