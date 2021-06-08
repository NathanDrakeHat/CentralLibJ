package org.nathan.algorithmsJ.dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CutRodTest{

  public static int[] p = new int[]{1, 5, 8, 9, 10, 17, 17, 15, 24, 30};
  public static int[] price = new int[]{1, 5, 8, 10, 13, 17, 18, 22, 25, 30, 31};
  public static int[][] method = new int[][]{{1}, {2}, {3}, {2, 2}, {1, 2, 2}, {6}, {1, 6}, {1, 2, 5}, {3, 6}, {10},
          {1, 10}};

  @Test
  void recursiveCutRod(){
    for(int i = 1; i <= 11; i++){
      var t = CutRod.recursiveCutRod(p, i);
      assertEquals(price[i - 1], t.getPrice());
      assertArrayEquals(method[i - 1], t.getApproach());
    }

  }

  @Test
  void topDownCutRod(){
    for(int i = 1; i <= 11; i++){
      var t = CutRod.topDownCutRod(p, i);
      assertEquals(price[i - 1], t.getPrice());
      assertArrayEquals(method[i - 1], t.getApproach());
    }
  }

  @Test
  void bottomUpCutRod(){
    for(int i = 1; i <= 11; i++){
      var t = CutRod.bottomUpCutRod(p, i);
      assertEquals(price[i - 1], t.getPrice());
      assertArrayEquals(method[i - 1], t.getApproach());
    }
  }
}