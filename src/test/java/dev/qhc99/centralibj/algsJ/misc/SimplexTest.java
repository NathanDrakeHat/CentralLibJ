package dev.qhc99.centralibj.algsJ.misc;

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
    var res = sp.solve();
    assertTrue(Math.abs(12 - res[0]) < Math.pow(10, -6));
    assertTrue(Math.abs(28 - res[1]) < Math.pow(10, -6));
    assertTrue(Math.abs(-800 - res[2]) < Math.pow(10, -6));
  }
}