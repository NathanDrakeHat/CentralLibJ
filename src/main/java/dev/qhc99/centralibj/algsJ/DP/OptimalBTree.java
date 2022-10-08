package dev.qhc99.centralibj.algsJ.DP;

/**
 * unfinished
 */
final class OptimalBTree{
  // class table arrangement
  public static double[] p = {0, 0.15, 0.10, 0.05, 0.10, 0.20};
  public static double[] q = {0.05, 0.10, 0.05, 0.05, 0.05, 0.10};

  //class table
  public static Tables optimalBST(double[] p, double[] q, int n){
    Tables res = new Tables();
    double[][] e = new double[n + 2][n + 1]; // cost
    double[][] w = new double[n + 2][n + 1]; // probability
    double[][] root = new double[n + 1][n + 1];
    // root[i][j] store root index of [i, j]
    for(int i = 1; i <= n + 1; i++){
      e[i][i - 1] = q[i - 1];
      w[i][i - 1] = q[i - 1];
    }
    for(int l = 1; l <= n; l++){
      for(int i = 1; i <= n - l + 1; i++){
        int j = i + l - 1;
        e[i][j] = Double.POSITIVE_INFINITY;
        w[i][j] = w[i][j - 1] + p[j] + q[j];
        for(int r = i; r <= j; r++){
          double t = e[i][r - 1] + e[r + 1][j] + w[i][j];
          if(t < e[i][j]){
            e[i][j] = t;
            root[i][j] = r;
          }
        }
      }
    }
    res.e = e;
    res.root = root;
    return res;
  }

  public static class Tables{
    double[][] e;
    double[][] root;
  }

}

