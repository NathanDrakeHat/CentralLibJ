package org.nathan.AlgorithmsJava.DP;

import org.junit.jupiter.api.Test;
import org.nathan.AlgorithmsJava.tools.containers.Tuple;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixChainTest {

    @Test
    void matrixChainOrder() {
        var t = MatrixChain.matrixChainOrder(Data.test);
        assertEquals(Data.res, t.toString());
        assertEquals(Data.answer, t.min_cost);
    }

    static class Data {

        static final int[][] a3 = new int[15][5];
        static final int[][] a4 = new int[5][10];
        static final int[][] a5 = new int[10][20];
        static final int[][] a6 = new int[20][25];
        static List<Tuple<Integer, Integer>> test = List.of(
                new Tuple<>(30, 35),
                new Tuple<>(35, 15),
                new Tuple<>(15, 5),
                new Tuple<>(5, 10),
                new Tuple<>(10, 20),
                new Tuple<>(20, 25));
        static int answer = (35 * 15 * 5) + (30 * 35 * 5) + (5 * 10 * 20) + (5 * 20 * 25) + (30 * 5 * 25);
        static String res = "((1(23))((45)6))";
    }
}