package org.nathan.centralibj.algsJ.dataStruc;

public class SuffixSumArray{
  final int[] arr;
  public final int ArrayLength;

  public SuffixSumArray(int[] array){
    ArrayLength = array.length;
    arr = new int[ArrayLength + 1];

    for(int i = 1; i <= ArrayLength; i++){
      prefixSumAdd(i, array[i - 1]);
    }
  }

  /**
   * zero initialization
   *
   * @param N     number
   */
  public SuffixSumArray(int N){
    ArrayLength = N;
    arr = new int[N + 1];
  }

  /**
   * @param idx start from 1
   * @return suffix sum
   */
  public int prefixSumOf(int idx){
    if(idx <= 0 || idx >= arr.length){
      throw new IllegalArgumentException();
    }
    int ans = 0;
    while(idx != 0) {
      ans += arr[idx];
      idx -= lowBit(idx);
    }
    return ans;
  }

  /**
   * @param l inclusive >= 1
   * @param h inclusive >= 1
   * @return sum of range
   */
  public int prefixSumOfRange(int l, int h){
    if(l <= 0 || l >= arr.length || h <= 0 || h >= arr.length || h < l){
      throw new IllegalArgumentException();
    }
    int a = prefixSumOf(h);
    if(l > 0){
      int b = prefixSumOf(l - 1);
      return a - b;
    }
    else{return a;}
  }

  public void prefixSumAdd(int idx, int diff){
    if(idx <= 0 || idx >= arr.length){
      throw new IllegalArgumentException();
    }
    int len = arr.length;
    while(idx < len) {
      arr[idx] += diff;
      idx += lowBit(idx);
    }
  }

  private static int lowBit(int x){
    return x & -x;
  }
}
