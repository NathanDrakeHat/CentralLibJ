package multiThread;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static multiThread.ParalleledFor.*;
import static org.junit.jupiter.api.Assertions.*;

class ParalleledForTest {
    @Test
    void matrixVectorTest(){
        double[][] A = new double[][]{{1,2,3},
                {4,5,6},
                {7,8,9}
        };
        double[] x = new double[]{1,2,3};
        var res = matrixVector(A,x);
        assertArrayEquals(new double[]{14.0, 32.0, 50.0}, res);
    }
}