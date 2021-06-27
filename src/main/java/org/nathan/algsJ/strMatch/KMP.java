package org.nathan.algsJ.strMatch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class KMP{
  public static List<Integer> search(@NotNull String T, @NotNull String P){
    int n = T.length(), m = P.length();
    int[] pi = computePrefixFunction(P);
    int q = 0;
    List<Integer> res = new ArrayList<>();
    for(int i = 0; i < n; i++){
      while(q > 0 && P.charAt(q) != T.charAt(i)){
        q = pi[q];
      }
      if(P.charAt(q) == T.charAt(i)){
        q++;
      }
      if(q == m){
        res.add(i+1-m);
        q = pi[q];
      }
    }
    return res;
  }

  private static int[] computePrefixFunction(@NotNull String P){
    int m = P.length();
    int[] pi = new int[m+1];
    pi[1] = 0;
    int k = 0;
    for(int q = 1; q < m; q++){
      while(k > 0 && P.charAt(k) != P.charAt(q)){
        k = pi[k];
      }
      if(P.charAt(k) == P.charAt(q)){
        k++;
      }
      pi[q] = k;
    }
    return pi;
  }
}
