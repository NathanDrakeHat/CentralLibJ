package multiThread;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FibTest {

    @Test
    void getFibonacciSequence() {
        assertEquals(13,Fib.getFibonacciSequence(7));
    }
}