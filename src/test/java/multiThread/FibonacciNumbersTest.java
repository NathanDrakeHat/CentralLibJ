package multiThread;

import org.junit.jupiter.api.Test;
import static multiThread.FibonacciNumbers.*;
import static org.junit.jupiter.api.Assertions.*;

class FibonacciNumbersTest {
    @Test
    void FibTest(){
        var t = new Fib(8);
        t.start();
        try{
            t.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        assertEquals(21, t.getRes());
    }

}