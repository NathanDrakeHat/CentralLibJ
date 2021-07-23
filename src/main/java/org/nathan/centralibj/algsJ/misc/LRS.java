package org.nathan.centralibj.algsJ.misc;


import java.util.Arrays;

/**
 * longest repeated string
 */
public class LRS{
  private static int lcp(String s, String t){
    int N = Math.min(s.length(), t.length());
    for(int i = 0; i < N; i++)
      if(s.charAt(i) != t.charAt(i)){ return i; }
    return N;
  }

  public static String lrs(String s){
    int N = s.length();
    String[] suffixes = new String[N];
    for(int i = 0; i < N; i++)
      suffixes[i] = s.substring(i, N);
    Arrays.sort(suffixes);
    String lrs = "";
    for(int i = 0; i < N - 1; i++){
      int len = lcp(suffixes[i], suffixes[i + 1]);
      if(len > lrs.length()){ lrs = suffixes[i].substring(0, len); }
    }
    return lrs;
  }
}
