package Algorithms.miscellaneous;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IthSmallestTest {

    static class Data {
        static int[] test1;
        static int[] test2;
        static int[] test3;
        static int[] test4;
    }

    @BeforeEach
    void build() {
        Data.test1 = new int[]{-2, 4, 2, -4, -9, 6, -4, 7, -3, -8, 2, -8, 0, 10, -9, -4, -3, -6, -8, 2};
        Data.test2 = new int[]{10, -5, 10, 4, 2, 6, 10, 10, 4, -9, -6, -7, 10, -7, 5, -6, 8, 8, -5, -8};
        Data.test3 = new int[]{2, 12, -24, -38, -17, -25, 5, -22, -34, 0, -18, -16, -39, -39, -36};
        Data.test4 = new int[]{-9, -19, -3, 16, 6, 18, -15, -20, -8, 13, -18, 13, 17, -6, 16, -2, -3};
    }

    @Test
    void randomSelectCase1() {
        var res = IthSmallest.randomSelect(Data.test1, 0);
        Arrays.sort(Data.test1);
        assertEquals(Data.test1[0], res);

        res = IthSmallest.randomSelect(Data.test2, 1);
        Arrays.sort(Data.test2);
        assertEquals(Data.test2[1], res);

        res = IthSmallest.randomSelect(Data.test3, 2);
        Arrays.sort(Data.test3);
        assertEquals(Data.test3[2], res);

        res = IthSmallest.randomSelect(Data.test4, 3);
        Arrays.sort(Data.test4);
        assertEquals(Data.test4[3], res);
    }

    @Test
    void randomSelectCase2() {
        var res = IthSmallest.randomSelect(Data.test2, 4);
        Arrays.sort(Data.test2);
        assertEquals(Data.test2[4], res);

        res = IthSmallest.randomSelect(Data.test2, 5);
        Arrays.sort(Data.test2);
        assertEquals(Data.test2[5], res);

        res = IthSmallest.randomSelect(Data.test3, 6);
        Arrays.sort(Data.test3);
        assertEquals(Data.test3[6], res);

        res = IthSmallest.randomSelect(Data.test4, 7);
        Arrays.sort(Data.test4);
        assertEquals(Data.test4[7], res);
    }

    @Test
    void randomSelectCase3() {
        var res = IthSmallest.randomSelect(Data.test3, 8);
        Arrays.sort(Data.test3);
        assertEquals(Data.test3[8], res);

        res = IthSmallest.randomSelect(Data.test2, 9);
        Arrays.sort(Data.test2);
        assertEquals(Data.test2[9], res);

        res = IthSmallest.randomSelect(Data.test3, 10);
        Arrays.sort(Data.test3);
        assertEquals(Data.test3[10], res);

        res = IthSmallest.randomSelect(Data.test4, 11);
        Arrays.sort(Data.test4);
        assertEquals(Data.test4[11], res);
    }

    @Test
    void randomSelectCase4() {
        var res = IthSmallest.randomSelect(Data.test4, 12);
        Arrays.sort(Data.test4);
        assertEquals(Data.test4[12], res);

        res = IthSmallest.randomSelect(Data.test2, 13);
        Arrays.sort(Data.test2);
        assertEquals(Data.test2[13], res);

        res = IthSmallest.randomSelect(Data.test3, 14);
        Arrays.sort(Data.test3);
        assertEquals(Data.test3[14], res);

        res = IthSmallest.randomSelect(Data.test4, 15);
        Arrays.sort(Data.test4);
        assertEquals(Data.test4[15], res);
    }
}