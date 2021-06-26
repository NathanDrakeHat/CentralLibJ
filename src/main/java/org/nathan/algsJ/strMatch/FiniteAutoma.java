package org.nathan.algsJ.strMatch;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FiniteAutoma{
  private static
  @NotNull Map<TransitionEntry, Integer> computeTransitionPattern(
          @NotNull String pattern,
          char[] input_char_set){
    int m = pattern.length();
    Map<TransitionEntry, Integer> map = new HashMap<>();
    for(int q = 0; q <= m; q++){
      for(var a : input_char_set){
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

  private static List<Integer> finiteAutomationSearch(
          @NotNull String T,
          @NotNull Map<TransitionEntry, Integer> delta,
          int states_count){
    List<Integer> res = new ArrayList<>();
    int n = T.length();
    int q = 0;
    for(int i = 0; i < n; i++){
      q = delta.get(new TransitionEntry(q, T.charAt(i)));
      if(q == states_count){
        res.add(i - states_count);
      }
    }
    return res;
  }

  public static List<Integer> search(@NotNull String data, @NotNull String pat, char[] input_char_set){
    var delta = computeTransitionPattern(pat, input_char_set);
    return finiteAutomationSearch(data, delta, pat.length());
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
