package dev.qhc99.centralibj.algsJ.dataStruc;

public class BinaryIndexedTree {
  final int[] arr;
  public final int ArrayLength;

  public BinaryIndexedTree(int[] array) {
    ArrayLength = array.length;
    arr = new int[ArrayLength + 1];

    for (int i = 1; i <= ArrayLength; i++) {
      prefixSumAdd(i, array[i - 1]);
    }
  }

  /**
   * zero initialization
   *
   * @param N number
   */
  public BinaryIndexedTree(int N) {
    ArrayLength = N;
    arr = new int[N + 1];
  }

  /**
   * sum of i items
   *
   * @param count count
   * @return suffix sum
   */
  public int prefixSumOf(int count) {
    if (count < 0 || count >= arr.length) {
      throw new IllegalArgumentException();
    }
    int ans = 0;
    while (count != 0) {
      ans += arr[count];
      count -= lowBit(count);
    }
    return ans;
  }

  /**
   * @param l inclusive
   * @param h inclusive
   * @return sum of range
   */
  public int prefixSumOfRange(int l, int h) {
    if (l < 0 || l >= arr.length || h < 0 || h >= arr.length || h < l) {
      throw new IllegalArgumentException();
    }
    return prefixSumOf(h) - prefixSumOf(l - 1);

  }

  /**
   *
   * @param idx start from 1
   * @param diff difference
   */
  public void prefixSumAdd(int idx, int diff) {
    if (idx <= 0 || idx >= arr.length) {
      throw new IllegalArgumentException();
    }
    int len = arr.length;
    while (idx < len) {
      arr[idx] += diff;
      idx += lowBit(idx);
    }
  }

  private static int lowBit(int x) {
    return x & -x;
  }
}
