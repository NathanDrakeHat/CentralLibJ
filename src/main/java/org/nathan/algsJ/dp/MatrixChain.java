package org.nathan.algsJ.dp;

import org.nathan.centralUtils.tuples.Tuple;

import java.util.List;

/**
 * optimal matrix multiply complexity
 */
final class MatrixChain {
  public static class MatrixChainResult {
    private int min_cost;
    private PairNode res;

    static class PairNode {
      // arithmetic tree
      int a; // a * b
      PairNode left;
      PairNode right;

      PairNode() {
      }

      PairNode(int a) {
        this.a = a;
      }
    }

    public int getMinCost() {
      return min_cost;
    }

    private void walk(PairNode p, StringBuilder res) {
      if (p.left != null || p.right != null) {
        res.append("(");
      }
      if (p.left != null) {
        walk(p.left, res);
      }
      if (p.right != null) {
        walk(p.right, res);
      }
      if (p.right == null && p.left == null) {
        res.append(p.a + 1);
      }
      if (p.left != null || p.right != null) {
        res.append(")");
      }
    }

    @Override
    public String toString() {
      var t = new StringBuilder();
      walk(res, t);
      return t.toString();
    }
  }

  public static MatrixChainResult matrixChainOrder(List<Tuple<Integer, Integer>> p) { // [start, end] [mid, end]
    MatrixChainResult[][] m = new MatrixChainResult[p.size()][p.size()]; // memory
    for (int i = 0; i < p.size(); i++) {
      m[i][i] = new MatrixChainResult();
      m[i][i].min_cost = 0;
      m[i][i].res = new MatrixChainResult.PairNode(i);
    }
    for (int l = 2; l <= p.size(); l++) { // len
      for (int s = 0; s + l - 1 < p.size(); s++) { // start
        int e = s + l - 1; // end

        m[s][e] = new MatrixChainResult();
        if (l == 2) {
          m[s][e].min_cost = p.get(s).first() * p.get(s).second() * p.get(e).second();
          m[s][e].res = new MatrixChainResult.PairNode();
          m[s][e].res.left = m[s][s].res;
          m[s][e].res.right = m[e][e].res;
        } else {
          m[s][e].min_cost =
                  p.get(s).first() * p.get(s).second() * p.get(e).second() + m[s][s].min_cost + m[s + 1][e].min_cost;
          m[s][e].res = new MatrixChainResult.PairNode();
          m[s][e].res.left = m[s][s].res;
          m[s][e].res.right = m[s + 1][e].res;
          for (int i = 1; i < l - 1; i++) {
            int cost =
                    m[s][s + i].min_cost + m[s + i + 1][e].min_cost + p.get(s).first() * p.get(s + i + 1).first() * p.get(e).second();
            if (cost < m[s][e].min_cost) {
              m[s][e].min_cost = cost;
              m[s][e].res = new MatrixChainResult.PairNode();
              m[s][e].res.left = m[s][s + i].res;
              m[s][e].res.right = m[s + i + 1][e].res;
            }
          }
        }
      }
    }
    return m[0][p.size() - 1];
  }
}