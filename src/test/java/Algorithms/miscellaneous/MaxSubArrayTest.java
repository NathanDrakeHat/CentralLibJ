package Algorithms.miscellaneous;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxSubArrayTest {

    int[] test = {-3, -4, -1, -6, -5, -4, 2, -6, -6, 3, 3, 3, 2, 2, 1, -2, -1, 1, -6, -2, -1};

    @Test
    void divideAndConquer() {
        MaxSubArray.Interval res = MaxSubArray.divideAndConquer(test, 0, 20);
        assertEquals(9, res.start);
        assertEquals(15, res.end);
        assertEquals(14, res.max_sum);
    }

    @Test
    void onlineMaxSub() {
        MaxSubArray.Interval res = MaxSubArray.onlineMaxSub(test, 20);
        assertEquals(9, res.start);
        assertEquals(15, res.end);
        assertEquals(14, res.max_sum);
    }
}