package org.qhc99.centralibj.algsJ.strMatch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp{

  public static List<Integer> search(@NotNull String text, @NotNull String pattern, int radix, int prime){
    int n = text.length(), m = pattern.length();
    int h = (int) (Math.pow(radix, m - 1) % prime);
    int p = 0, t_i = 0;
    for(int i = 0; i < m; i++){
      p = (radix * p + pattern.charAt(i)) % prime;
      t_i = (radix * t_i + text.charAt(i)) % prime;
    }
    List<Integer> res = new ArrayList<>();
    for(int s = 0; s <= n - m; s++){
      if(p == t_i && pattern.equals(text.substring(s, s + m))){
        res.add(s);
      }
      if(s < n - m){
        t_i = (radix * (t_i - text.charAt(s) * h) + text.charAt(s + m)) % prime;
      }
    }
    return res;
  }
}
