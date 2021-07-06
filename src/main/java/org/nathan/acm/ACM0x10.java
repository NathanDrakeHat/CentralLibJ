package org.nathan.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class ACM0x10 {
  /**
   * @param floatRects list of width and height of rectangle
   * @return range of largest inner connection of rectangles(left inclusive and right exclusive)
   */
  public static int largestRectInHist(List<Tuple<Integer, Integer>> floatRects) {
    Deque<Tuple<Integer, Integer>> rectRecord = new ArrayDeque<>(floatRects.size());
    int max_area = 0;
    for (var fr : floatRects) {
      if (!rectRecord.isEmpty() && fr.second() < rectRecord.getLast().second()) {
        int acc_width = 0;
        while (!rectRecord.isEmpty() && rectRecord.getLast().second() > fr.second()) {
          var r = rectRecord.removeLast();
          acc_width += r.first();
          max_area = Math.max(max_area, r.second() * acc_width);
        }
        if (!rectRecord.isEmpty()) {
          var l = rectRecord.removeLast();
          rectRecord.addLast(new Tuple<>(l.first() + acc_width, l.second()));
        }
      }
      rectRecord.addLast(fr);
    }
    {
      int acc_width = 0;
      while (!rectRecord.isEmpty()) {
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
  public static double monotonousQueue(double[] array, int M) {
    double[] s = new double[array.length + 1];
    System.arraycopy(array, 0, s, 1, array.length);
    for (int i = 2; i < s.length; i++) {
      s[i] += s[i - 1];
    }
    Deque<Integer> deque = new ArrayDeque<>();
    deque.addFirst(0);
    double ans = Double.NEGATIVE_INFINITY;
    for (int i = 1; i <= array.length; i++) {
      while (!deque.isEmpty() && i - deque.getFirst() > M) {
        deque.removeFirst();
      }
      ans = Math.max(ans, s[i] - s[deque.getFirst()]);
      while (!deque.isEmpty() && s[deque.getLast()] >= s[i]) {
        deque.removeLast();
      }
      deque.addLast(i);
    }
    return ans;
  }

  public static String Manacher(@NotNull String txt){
    return null;
  }
}
