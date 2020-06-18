package algorithms.dynamicProgramming;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CutRodTest {

    public static int[] p = new int[]{1, 5, 8, 9, 10, 17, 17, 15, 24, 30};
    public static int[] price = new int[]{1, 5, 8, 10, 13, 17, 18, 22, 25, 30, 31, 35, 38, 40, 43};
    public static int[][] method = new int[][]{{1},{2},{3},{2,2},{1,2,2},{6},{1,6},{1,2,5},{3,6},{10},{1,10},
            {1,2,9},{1,3,9},{1,2,11},{1,2,12}};

    @Test
    void recursiveCutRod() {
        for(int i = 1; i <= 15; i++){
            var t = CutRod.recursiveCutRod(p, i);
            assertEquals(price[i-1],t.getPrice());
            assertArrayEquals(method[i-1],t.getApproach());
        }

    }

    @Test
    void topDownCutRod() {
        for(int i = 1; i <= 15; i++){
            var t = CutRod.topDownCutRod(p, i);
            assertEquals(t.getPrice(), price[i-1]);
            assertArrayEquals(t.getApproach(), method[i-1]);
        }
    }

    @Test
    void bottomUpCutRod() {
        for(int i = 1; i <= 15; i++){
            var t = CutRod.bottomUpCutRod(p, i);
            assertEquals(t.getPrice(), price[i-1]);
            assertArrayEquals(t.getApproach(), method[i-1]);
        }
    }
}