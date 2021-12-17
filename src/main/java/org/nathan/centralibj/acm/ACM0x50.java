package org.nathan.centralibj.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralibj.utils.tuples.Tuple;

import java.util.*;

@SuppressWarnings("JavaDoc")
class ACM0x50 {

  /**
   * ith item has weights[i] and values[i]
   * @param weights
   * @param values
   * @param capacity
   * @return
   */
  public static int binaryBag(int[] weights, int[] values, int capacity){
    int[] f = new int[Arrays.stream(weights).sum()+1];
    Arrays.fill(f, Integer.MIN_VALUE);
    f[0] = 0;
    for(int i = 0; i < weights.length; i++){
      for(int w = capacity; w >= weights[i]; w--){
        f[w] = Math.max(f[w], f[w-weights[i]]+values[i]);
      }
    }
    return Arrays.stream(f).max().getAsInt();
  }

  /**
   * infinite item has weights[i] and values[i]
   * @param weights
   * @param values
   * @param capacity
   * @return
   */
  public static int completeBag(int[] weights, int[] values, int capacity){
    int[] f = new int[Arrays.stream(weights).sum()+1];
    Arrays.fill(f, Integer.MIN_VALUE);
    f[0] = 0;
    for(int i = 0; i < weights.length; i++){
      //noinspection OverflowingLoopIndex
      for(int w = weights[i]; w <= capacity; w--){
        f[w] = Math.max(f[w], f[w-weights[i]]+values[i]);
      }
    }
    return Arrays.stream(f).max().getAsInt();
  }
}
