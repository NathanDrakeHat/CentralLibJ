package org.nathan.acm;

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
      while (!rectRecord.isEmpty()){
        var r = rectRecord.removeLast();
        acc_width += r.first();
        max_area = Math.max(max_area, r.second() * acc_width);
      }
    }
    return max_area;
  }
}
