package org.qhc99.centralibj.algsJ.misc;

public final class MaxSubArray{
  public static Interval divideAndConquer(int[] array, int start, int end){
    if((end - start) > 1){
      int middle = (start + end) / 2;
      Interval left_sub = divideAndConquer(array, start, middle);
      Interval right_sub = divideAndConquer(array, middle, end);
      int max_left_sum = array[middle - 1], max_right_sum = array[middle];
      int left_p = middle - 1, right_p = middle;
      int acc_sum = max_left_sum;
      for(int i = left_p - 1; i >= start; i--){
        acc_sum += array[i];
        if(acc_sum > max_left_sum){
          max_left_sum = acc_sum;
          left_p = i;
        }
      }
      acc_sum = max_right_sum;
      for(int i = right_p + 1; i < end; i++){
        acc_sum += array[i];
        if(acc_sum > max_right_sum){
          max_right_sum = acc_sum;
          right_p = i;
        }
      }
      int middle_sum = max_left_sum + max_right_sum;
      int max_middle_sum, middle_low, middle_high;
      if(middle_sum >= max_left_sum && middle_sum >= max_right_sum){
        middle_low = left_p;
        middle_high = right_p + 1;
        max_middle_sum = middle_sum;
      }
      else if(middle_sum <= max_left_sum && max_left_sum >= max_right_sum){
        middle_low = left_p;
        middle_high = middle;
        max_middle_sum = max_left_sum;
      }
      else{
        middle_low = middle;
        middle_high = right_p + 1;
        max_middle_sum = max_right_sum;
      }
      Interval middle_sub = new Interval(middle_low, middle_high, max_middle_sum);
      int m = middle_sub.max_sum;
      int l = left_sub.max_sum;
      int r = right_sub.max_sum;
      if(m >= l && m >= r){
        return new Interval(middle_sub.start, middle_sub.end, middle_sub.max_sum);
      }
      if(l >= r){
        return new Interval(left_sub.start, left_sub.end, left_sub.max_sum);
      }
      return new Interval(right_sub.start, right_sub.end, right_sub.max_sum);

    }
    return new Interval(start, end, array[start]);
  }

  public static Interval onlineMaxSub(int[] array, int len){
    int left = 0, right, acc_sum;
    int start = 0, end = 1;
    acc_sum = 0;
    int max_sum = Integer.MIN_VALUE;
    for(int i = 0; i < len; i++){
      right = i + 1;
      acc_sum += array[i];
      if(acc_sum > max_sum){
        end = right;
        start = left;
        max_sum = acc_sum;
      }
      if(acc_sum < 0){
        left = i + 1;
        acc_sum = 0;
      }
    }
    return new Interval(start, end, max_sum);
  }

  // find max sum consequent sequence
  public static class Interval{
    public int start;
    public int end;
    public int max_sum;

    public Interval(int s, int e, int m){
      this.start = s;
      this.end = e;
      this.max_sum = m;
    }
  }
}