package multiThread;

import org.junit.jupiter.api.Test;
import static multiThread.ParalleledFor.*;
import static org.junit.jupiter.api.Assertions.*;

class ParalleledForTest {
    @Test
    void matrixVectorTest(){
        double[][] A = new double[][]{{1,4,7},
                {2,5,8},
                {3,6,9}
        };
        double[] x = new double[]{1,2,3};
        var res = matrixVector(A,x);
        assertArrayEquals(new double[]{30,36,42}, res);
    }
}