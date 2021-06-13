package org.nathan.algorithmsJ.strMatch;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StrAutomata{
  public static
  @NotNull Map<TransitionEntry, Integer> computeTransitionPattern(
          @NotNull String pattern,
          char[] char_set){
    int m = pattern.length();
    Map<TransitionEntry, Integer> map = new HashMap<>();
    for(int q = 0; q <= m; q++){
      for(var a : char_set){
        int k = Math.min(m + 1, q + 2);
        do{
          k--;
        }
        while(!(pattern.substring(0, q).concat(String.valueOf(a)).endsWith(pattern.substring(0, k))));
        map.put(new TransitionEntry(q, a), k);
      }
    }
    return map;
  }

  public static List<Integer> finiteAutomationMatcher(
          @NotNull String T,
          @NotNull Map<TransitionEntry, Integer> delta,
          int m){
    List<Integer> res = new ArrayList<>();
    int n = T.length();
    int q = 0;
    for(int i = 0; i < n; i++){
      q = delta.get(new TransitionEntry(q, T.charAt(i)));
      if(q == m){
        res.add(i - m);
      }
    }
    return res;
  }

  private static class TransitionEntry{
    private final int integer;
    private final char character;
    private final int hash;
    private final String string;

    public TransitionEntry(int i, char c){
      integer = i;
      character = c;
      hash = Objects.hash(integer, character);
      string = String.format("TransitionEntry(%d,%s)", integer, character);
    }

    @Override
    public boolean equals(Object other){
      if(other instanceof TransitionEntry){
        return (integer == ((TransitionEntry) other).integer) &&
                (character == ((TransitionEntry) other).character);
      }
      else{
        return false;
      }
    }

    @Override
    public int hashCode(){
      return hash;
    }

    @Override
    public String toString(){
      return string;
    }
  }
}
