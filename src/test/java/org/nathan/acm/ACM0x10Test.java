package org.nathan.acm;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.tuples.Tuple;
import org.nathan.centralUtils.utils.ArrayUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.nathan.acm.ACM0x10.*;

class ACM0x10Test {
  final int iteration = 20;

  List<Tuple<Integer, Integer>> LRITCases = new ArrayList<>(7);

  {
    LRITCases.add(new Tuple<>(1, 2));
    LRITCases.add(new Tuple<>(1, 1));
    LRITCases.add(new Tuple<>(1, 4));
    LRITCases.add(new Tuple<>(1, 5));
    LRITCases.add(new Tuple<>(1, 1));
    LRITCases.add(new Tuple<>(1, 3));
    LRITCases.add(new Tuple<>(1, 3));
  }

  @Test
  void largestRectInHistTest() {
    assertEquals(8, largestRectInHist(LRITCases));
  }

  double[][] mqCases;
  double[] mqAnswers;

  {
    mqCases = new double[iteration][];
    mqAnswers = new double[iteration];
    var rand = new SplittableRandom();
    for (int i = 0; i < iteration; i++) {
      int len = rand.nextInt(3, 6);
      mqCases[i] = ArrayUtils.randomDoubleArray(-5, 10, len);
      int m = len / 2;
      double[] temp = new double[mqCases[i].length + 1];
      System.arraycopy(mqCases[i], 0, temp, 1, temp.length-1);
      for (int t = 2; t < temp.length; t++) {
        temp[t] += temp[t - 1];
      }
      double ans = Double.NEGATIVE_INFINITY;
      for (int j = 0; j < temp.length; j++) {
        for (int k = j + 1; k < temp.length && k - j <= m; k++) {
          ans = Math.max(ans, temp[k] - temp[j]);
        }
      }
      mqAnswers[i] = ans;
    }
  }

  @Test
  void monotonousQueueTest() {
    for (int i = 0; i < iteration; i++) {
      var ans = monotonousQueue(mqCases[i], mqCases[i].length / 2);
      assertEquals(mqAnswers[i], ans);
    }
  }

  String periodCase = "abababab";
  Map<String, Tuple<String, Integer>> periodAnswer;

  {
    periodAnswer = new HashMap<>();
    periodAnswer.put("ababab", new Tuple<>("ab", 3));
    periodAnswer.put("abab", new Tuple<>("ab", 2));
  }

  @Test
  void periodTest(){
    assertEquals(periodAnswer, period(periodCase));
  }
}