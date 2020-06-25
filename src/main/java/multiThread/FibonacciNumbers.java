package multiThread;

import java.util.function.IntConsumer;

public class FibonacciNumbers {
    public static class Fib extends Thread{
        private int n;
        private int res;
        public Fib(int n){
            this.n = n;
        }

        @Override
        public void run(){
            if(n <= 1){
                res = n;
            }else{
                var x = new Fib(n-1);
                var y = new Fib(n-2);
                x.start();
                y.start();
                try {
                    x.join();
                    y.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                res = x.getRes() + y.getRes();
            }
        }

        public int getRes() { return res; }
    }
    public static class AddToArray implements IntConsumer{
        private int[] a;
        public AddToArray(int[] a){ this.a = a; }
        @Override
        public void accept(int i){
            a[0] = i;
        }
    }
}
