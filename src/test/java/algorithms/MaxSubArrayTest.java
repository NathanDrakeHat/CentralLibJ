package algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxSubArrayTest {

    int[] test = {-3, -4, -1, -6, -5, -4, 2, -6, -6, 3, 3, 3, 2, 2, 1, -2, -1, 1, -6, -2, -1};
    
    @Test
    void divideAndConquer() {
        MaxSubArray.Interval res = MaxSubArray.divideAndConquer(test, 0, 20);
        assertEquals(res.start, 9);
        assertEquals(res.end, 15);
        assertEquals(res.max_sum, 14);
    }

    @Test
    void onlineMaxSub() {
        MaxSubArray.Interval res = MaxSubArray.onlineMaxSub(test, 20);
        assertEquals(res.start, 9);
        assertEquals(res.end, 15);
        assertEquals(res.max_sum, 14);
    }
}