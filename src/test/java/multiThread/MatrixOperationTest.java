package multiThread;

import org.junit.jupiter.api.Test;
import static multiThread.MatrixOperation.*;
import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationTest {

    @Test
    void matrixMultiplyTest() {
        var A = new double[][]{{1,2,3},{4,5,6}};
        var B = new double[][]{{7,8,9},{10,11,12},{13,14,15}};
        var res = matrixMultiply(A, B);
        assertArrayEquals(new double[][]{{66.0, 72.0, 78.0},{156.0, 171.0, 186.0}},res);
    }
}