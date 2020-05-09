import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Temp { }

class ExampleThread{
    public static void thread(){
        Thread a = new Thread(){
            public void run(){
                System.out.println("Thread start.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                System.out.println("Thread end.");
            }
        };
        Thread b = new Thread(){
            public void run(){
                System.out.println("Thread start.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                System.out.println("Thread end.");
            }
        };
        a.start();
        b.start();
    }
    public static void join() throws InterruptedException {
        Thread t = new Thread(){
            public void run(){
                System.out.println("Thread start.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                System.out.println("Thread end.");
            }
        };
        Thread tt = new Thread(){
            public void run(){
                System.out.println("Thread start.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                System.out.println("Thread end.");
            }
        };
        t.start();
        t.join();
        tt.start();
        tt.join();
    }
    public static void syn() throws InterruptedException {
        Object lock = new Object();
        final int[] c = {0};
        var add = new Thread(){
            public void run(){
                for(int i = 0; i < 10000; i++){
                    synchronized(lock){
                        c[0]++;
                    }
                }
            }
        };
        var dec = new Thread(){
            public void run(){
                for(int i = 0; i < 10000; i++){
                    synchronized(lock){
                        c[0]--;
                    }
                }
            }
        };
        add.start();
        dec.start();
        add.join();
        dec.join();
        System.out.println(c[0]);
    }
}


class ExampleIO{
    public static void io() throws IOException {
        var f = new File("temp.txt");
        if(f.createNewFile()){
            System.out.print("create");
            var t = new Scanner(System.in);
            t.nextLine();
            if(f.delete()) {
                System.out.print("delete");
                t.nextLine();
            }
        }

        try(var i = new FileInputStream("temp.txt")){

        }
        catch (Exception e){

        }
    }
}