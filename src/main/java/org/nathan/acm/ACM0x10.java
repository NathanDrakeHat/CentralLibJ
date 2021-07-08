package org.nathan.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.algsJ.dataStruc.TernaryTries;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.*;

public class ACM0x10{
  /**
   * @param floatRects list of width and height of rectangle
   * @return range of largest inner connection of rectangles(left inclusive and right exclusive)
   */
  public static int largestRectInHist(List<Tuple<Integer, Integer>> floatRects){
    Deque<Tuple<Integer, Integer>> rectRecord = new ArrayDeque<>(floatRects.size());
    int max_area = 0;
    for(var fr : floatRects){
      if(!rectRecord.isEmpty() && fr.second() < rectRecord.getLast().second()){
        int acc_width = 0;
        while(!rectRecord.isEmpty() && rectRecord.getLast().second() > fr.second()) {
          var r = rectRecord.removeLast();
          acc_width += r.first();
          max_area = Math.max(max_area, r.second() * acc_width);
        }
        if(!rectRecord.isEmpty()){
          var l = rectRecord.removeLast();
          rectRecord.addLast(new Tuple<>(l.first() + acc_width, l.second()));
        }
      }
      rectRecord.addLast(fr);
    }
    {
      int acc_width = 0;
      while(!rectRecord.isEmpty()) {
        var r = rectRecord.removeLast();
        acc_width += r.first();
        max_area = Math.max(max_area, r.second() * acc_width);
      }
    }
    return max_area;
  }

  /**
   * subarray of max sum
   *
   * @param array array
   * @param M     max length of subarray
   * @return subarray
   */
  public static double monotonousQueue(double[] array, int M){
    double[] s = new double[array.length + 1];
    System.arraycopy(array, 0, s, 1, array.length);
    for(int i = 2; i < s.length; i++){
      s[i] += s[i - 1];
    }
    Deque<Integer> deque = new ArrayDeque<>();
    deque.addFirst(0);
    double ans = Double.NEGATIVE_INFINITY;
    for(int i = 1; i <= array.length; i++){
      while(!deque.isEmpty() && i - deque.getFirst() > M) {
        deque.removeFirst();
      }
      ans = Math.max(ans, s[i] - s[deque.getFirst()]);
      while(!deque.isEmpty() && s[deque.getLast()] >= s[i]) {
        deque.removeLast();
      }
      deque.addLast(i);
    }
    return ans;
  }

  /**
   * min iterate cell and its max iterate count of every prefix txt
   *
   * @param txt string
   * @return prefix string to its min iterate cell and max iterate count
   */
  public static @NotNull Map<String, Tuple<String, Integer>> period(@NotNull String txt){
    var next = org.nathan.algsJ.strMatch.KMP.computePrefixFunction(txt);
    Map<String, Tuple<String, Integer>> ans = new HashMap<>();
    for(int i = 2; i < txt.length(); i++){
      if(i % (i - next[i]) == 0 && i / (i - next[i]) > 1){
        var prefix = txt.substring(0, i);
        var cell = txt.substring(0, i - next[i]);
        var repeat = i / (i - next[i]);
        ans.put(prefix, new Tuple<>(cell, repeat));
      }
    }
    return ans;
  }

  /**
   * <pre>
   * bca -> abc
   * cab -> abc
   * abc -> abc
   * </pre>
   *
   * @param s string
   * @return min cyclic representation
   */
  public static @NotNull String minCyclicShift(@NotNull String s){
    var ss = s + s;
    int i = 0, j = 1, min = -1, s_len = s.length();
    var funcCompare = new Object(){
      int k = 0;

      int apply(int i, int j){
        k = 0;
        while(k < s_len) {
          char i_c = ss.charAt(i), j_c = ss.charAt(j);
          if(i_c > j_c){
            return 1;
          }
          else if(i_c < j_c){
            return -1;
          }
          else{
            i++;
            j++;
            k++;
          }
        }
        return 0;
      }
    };
    while(i < s_len && j < s_len) {
      int compare = funcCompare.apply(i, j);
      switch(compare){
        case 0 -> { return s;}
        case 1 -> {
          i += funcCompare.k + 1;
          if(i == j){
            i++;
          }
          min = j;
        }
        case -1 -> {
          j += funcCompare.k + 1;
          if(i == j){
            j++;
          }
          min = i;
        }
      }
    }
    return ss.substring(min, min + s_len);
  }

  public static @NotNull Tuple<Integer, Integer> largestXORPair(int[] integers){
    var funcReversedBinStr = new Object(){
      String apply(int i){
        var s = Integer.toBinaryString(i);
        var b = new StringBuilder();
        if(s.length() != 32){
          b.append("0".repeat(32 - s.length()));
        }
        b.append(s);
        return b.reverse().toString();
      }
    };
    var funcGetTargetInTries = new Object(){

      String apply(TernaryTries.Node<Void> node, String integer){
        var b = new StringBuilder();
        int idx = 0;

        while(idx < 32){
          var node_c = node.getChar();
          var str_c = integer.charAt(idx);
          if(node_c == '0'){
            if(str_c == '0'){
              if(node.getRight() != null){
                b.append('1');
                node = node.getRight();
              }
              else{
                b.append('0');
                node = node.getMid();
              }
            }
            else{
              b.append('0');
              node = node.getMid();
            }
          }
          else{
            if(str_c == '0'){
              b.append('1');
              node = node.getMid();
            }
            else{
              if(node.getLeft() != null){
                b.append('0');
                node = node.getLeft();
              }
              else{
                b.append('1');
                node = node.getMid();
              }
            }
          }
          idx++;
        }

        return b.reverse().toString();
      }
    };
    var tries = new TernaryTries<Void>();
    int max = -1;
    Tuple<Integer,Integer> res = null;
    for(int i : integers){
      var s = funcReversedBinStr.apply(i);
      if(tries.size() > 0){
        var xor_s = funcGetTargetInTries.apply(tries.getRoot(), s);
        int xor_i = Integer.parseUnsignedInt(xor_s,2);
        if((xor_i ^ i) > max){
          max = xor_i ^ i ;
          res = new Tuple<>(xor_i, i);
        }
      }
      tries.put(s, null);
    }
    return res;
  }
}