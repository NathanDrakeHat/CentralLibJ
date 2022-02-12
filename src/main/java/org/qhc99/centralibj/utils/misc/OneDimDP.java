package org.qhc99.centralibj.utils.misc;


public final class OneDimDP<V> {//matrix for dynamic programming problem
  private final Object[][] matrix;

  public final int Size;

  public OneDimDP(int size) {
    Size = size;
    matrix = new Object[size][];
    for (int i = 0; i < size; i++) {
      matrix[i] = new Object[size - i];
    }
  }

  @SuppressWarnings("unchecked")
  public V getAt(int r, int c) {
    return (V) matrix[r][c - r];
  }

  public void setAt(V v, int r, int c) {
    matrix[r][c - r] = v;
  }
}