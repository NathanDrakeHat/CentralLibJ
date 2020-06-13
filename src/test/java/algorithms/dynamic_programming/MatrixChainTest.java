package algorithms.dynamic_programming;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixChainTest {

    private static class Data{
        private static int[][] a1 = new int[30][35];
        private static int[][] a2 = new int[35][15];
        private static int[][] a3 = new int[15][5];
        private static int[][] a4 = new int[5][10];
        private static int[][] a5 = new int[10][20];
        private static int[][] a6 = new int[20][25];
        public static int[][][] test= {a1, a2, a3, a4, a5, a6};
        public static int answer = (35 * 15 * 5) + (30 * 35 * 5) + (5 * 10 * 20) + (5 * 20 * 25) + (30 * 5 * 25);
        public static String res = "((1(23))((45)6))";
    }

    @Test
    void matrixChainOrder() {
        var t = MatrixChain.matrixChainOrder(Data.test);
        assertEquals(t.toString(), Data.res);
        assertEquals(t.min_cost, Data.answer);
    }
}