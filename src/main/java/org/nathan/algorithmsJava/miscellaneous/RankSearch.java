package org.nathan.algorithmsJava.miscellaneous;


import java.util.concurrent.ThreadLocalRandom;

public final class RankSearch{
  private static int randPartition(int[] a, int start, int end){ // base case (end -start)
    int pivot_idx = ThreadLocalRandom.current().nextInt(start, end);
    var pivot = a[pivot_idx];

    var temp = a[end - 1];
    a[end - 1] = pivot;
    a[pivot_idx] = temp;

    int i = start - 1;
    for(int j = start; j < end - 1; j++){
      if(a[j] <= pivot){
        var t = a[j];
        a[j] = a[++i];
        a[i] = t;
      }
    }
    a[end - 1] = a[++i];
    a[i] = pivot;
    return i; //pivot idx
  }

  // select ith smallest element in array
  private static int rankSearch(int[] a, int start, int end, int ith){
    if((start - end) == 1){
      return a[start];
    }
    int pivot_idx = randPartition(a, start, end);
    int left_total = pivot_idx - start;
    if(ith == left_total){
      return a[pivot_idx];
    }
    else if(ith < left_total + 1){
      return rankSearch(a, start, pivot_idx, ith);
    }
    else{
      return rankSearch(a, pivot_idx + 1, end, ith - left_total - 1);
    }
  }

  public static int rankSearch(int[] a, int ith){
    if(a.length == 0){
      throw new IllegalArgumentException();
    }
    return rankSearch(a, 0, a.length, ith);
  }
}