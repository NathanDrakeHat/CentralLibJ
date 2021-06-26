package org.nathan.algsJ.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.nathan.algsJ.DP.LCS.solve;

class LCSTest {

  @Test
  void solveTest() {
    char[] r = solve(Data.s1, Data.s2);
    assertArrayEquals(Data.res, r);
  }

  private static class Data {
    // gene sequence
    public static char[] s1 = {'A', 'C', 'C', 'G', 'G', 'T', 'C', 'G', 'A', 'G', 'T', 'G', 'C', 'G', 'C', 'G', 'G',
            'A', 'A', 'G', 'C', 'C', 'G', 'G', 'C', 'C', 'G', 'A', 'A'};
    public static char[] s2 = {'G', 'T', 'C', 'G', 'T', 'T', 'C', 'G', 'G', 'A', 'A', 'T', 'G', 'C', 'C', 'G', 'T',
            'T', 'G', 'C', 'T', 'C', 'T', 'G', 'T', 'A', 'A', 'A'};
    public static char[] res = {'G', 'T', 'C', 'G', 'T', 'C', 'G', 'G', 'A', 'A', 'G', 'C', 'C', 'G', 'G', 'C', 'C',
            'G', 'A', 'A'};
  }
}