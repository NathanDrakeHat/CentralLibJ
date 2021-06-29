package org.nathan.algsJ.misc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SimplexTest {

  @Test
  void solveTest() {
    var sp = new Simplex(
            new double[][]
                    {
                            new double[]{5, 15},
                            new double[]{4, 4},
                            new double[]{35, 20}
                    },
            new double[]{480, 160, 1190},
            new double[]{13, 23});
    sp.solve();
    var res = sp.resultMatrix();
    assertTrue(Math.abs(-800 - res[3][5]) < Math.pow(10, -6));
    assertTrue(Math.abs(28 - res[1][5]) < Math.pow(10, -6));
    assertTrue(Math.abs(12 - res[2][5]) < Math.pow(10, -6));

  }
}