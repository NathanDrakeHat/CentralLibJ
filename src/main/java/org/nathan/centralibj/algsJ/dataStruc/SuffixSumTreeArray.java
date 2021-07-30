package org.nathan.centralibj.algsJ.dataStruc;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SuffixSumTreeArray<T>{
  final List<T> list;
  final BiFunction<T, T, T> addition;
  final BiFunction<T, T, T> minus;

  public SuffixSumTreeArray(List<T> array, @NotNull BiFunction<T, T, T> add, @NotNull BiFunction<T, T, T> minus){
    int len = array.size();
    addition = add;
    this.minus = minus;
    list = new ArrayList<>(array.size() + 1);
    for(int i = 0; i <= len; i++){
      list.add(null);
    }

    for(int i = 1; i <= len; i++){
      int finalI = i;
      update(i, n -> addOrSelf(n, array.get(finalI-1)));
    }
  }

  public @NotNull T sumOf(int idx){
    if(idx <= 0 || idx >= list.size()){
      throw new IllegalArgumentException();
    }
    T ans = null;
    while(idx != 0) {
      if(ans != null){
        ans = addition.apply(ans, list.get(idx));
      }
      else{
        ans = list.get(idx);
      }
      idx -= lowBit(idx);
    }
    return ans;
  }

  /**
   * @param l inclusive >= 1
   * @param h inclusive
   * @return sum of range
   */
  public @NotNull T sumOfRange(int l, int h){
    T a = sumOf(h);
    if(l > 0){
      T b = sumOf(l - 1);
      return minus.apply(a, b);
    }
    else{return a;}
  }

  public void update(int idx, Function<T, T> change){
    int len = list.size();
    while(idx < len){
      list.set(idx, change.apply(list.get(idx)));
      idx += lowBit(idx);
    }
  }

  private T addOrSelf(T a, T b){
    if(a == null && b == null){
      throw new IllegalArgumentException();
    }
    else if(a == null){
      return b;
    }
    else if(b == null){
      return a;
    }
    else return addition.apply(a,b);
  }

  private static int lowBit(int x){
    return x & -x;
  }
}
