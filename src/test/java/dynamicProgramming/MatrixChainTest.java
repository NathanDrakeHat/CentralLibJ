package dynamicProgramming;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixChainTest {

    static class Data {
        static final int[][] a1 = new int[30][35];
        static final int[][] a2 = new int[35][15];
        static final int[][] a3 = new int[15][5];
        static final int[][] a4 = new int[5][10];
        static final int[][] a5 = new int[10][20];
        static final int[][] a6 = new int[20][25];
        static int[][][] test = {a1, a2, a3, a4, a5, a6};
        static int answer = (35 * 15 * 5) + (30 * 35 * 5) + (5 * 10 * 20) + (5 * 20 * 25) + (30 * 5 * 25);
        static String res = "((1(23))((45)6))";
    }

    @Test
    void matrixChainOrder() {
        var t = MatrixChain.matrixChainOrder(Data.test);
        assertEquals(Data.res, t.toString());
        assertEquals(Data.answer, t.min_cost);
    }
}