package org.nathan.algsJ.misc;

public class Simplex {
  private final double[][] a; // simplex tableaux
  private final int m;
  private final int n; // M constraints, N variables

  /**
   * <pre>
   *   max: A + B
   *   2A + B < 20
   *   A + 2B < 20
   *
   *   A: [2, 1]
   *      [1,2]
   *   b^T: [20]
   *        [20]
   *   c: [1,1]
   * </pre>
   *
   * @param A restraint coefficient
   * @param b restraint constant
   * @param c objective coefficient
   */
  public Simplex(double[][] A, double[] b, double[] c) {
    m = b.length;
    n = c.length;
    a = new double[m + 1][m + n + 1];
    for (int i = 0; i < m; i++)
      System.arraycopy(A[i], 0, a[i], 0, n);
    for (int j = n; j < m + n; j++) a[j - n][j] = 1.0;
    System.arraycopy(c, 0, a[m], 0, n);
    for (int i = 0; i < m; i++) a[i][m + n] = b[i];
  }

  private int bland() {
    int M = a.length - 1;
    for (int q = 0; q < m + n; q++)
      if (a[M][q] > 0) return q;
    return -1;
  }

  private int minRatioRule(int q) {
    int p = -1;
    for (int i = 0; i < m; i++) {
      if (a[i][q] <= 0) continue;
      else if (p == -1) p = i;
      else if (a[i][m + n] / a[i][q] < a[p][m + n] / a[p][q])
        p = i;
    }
    return p;
  }

  private void pivot(int p, int q) {
    for (int i = 0; i <= m; i++)
      for (int j = 0; j <= m + n; j++)
        if (i != p && j != q)
          a[i][j] -= a[p][j] * a[i][q] / a[p][q];
    for (int i = 0; i <= m; i++)
      if (i != p) a[i][q] = 0.0;
    for (int j = 0; j <= m + n; j++)
      if (j != q) a[p][j] /= a[p][q];
    a[p][q] = 1.0;
  }

  public void solve() {
    while (true) {
      int q = bland();
      if (q == -1) break;
      int p = minRatioRule(q);
      if (p == -1) {
        throw new IllegalStateException("do not have optimal answer.");
      }
      pivot(p, q);
    }
  }

  public double[][] resultMatrix() {
    double[][] res = new double[a.length][];
    for (int i = 0; i < a.length; i++) {
      res[i] = new double[a[i].length];
      System.arraycopy(a[i], 0, res[i], 0, a[i].length);
    }
    return res;
  }
}
