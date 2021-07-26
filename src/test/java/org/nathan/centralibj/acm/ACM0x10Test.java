package org.nathan.centralibj.acm;

import it.unimi.dsi.util.XoRoShiRo128PlusRandom;
import org.junit.jupiter.api.Test;
import org.nathan.centralibj.utils.tuples.Tuple;
import org.nathan.centralibj.utils.ArrayUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.nathan.centralibj.acm.ACM0x10.*;

class ACM0x10Test{
  final int iteration = 100;

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
  void largestRectInHistTest(){
    assertEquals(8, largestRectInHist(LRITCases));
  }

  double[][] mqCases;
  double[] mqAnswers;

  {
    mqCases = new double[iteration][];
    mqAnswers = new double[iteration];
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(6 - 3) + 3;
      mqCases[i] = ArrayUtils.randomDoubleArray(-5, 10, len);
      int m = len / 2;
      double[] temp = new double[mqCases[i].length + 1];
      System.arraycopy(mqCases[i], 0, temp, 1, temp.length - 1);
      for(int t = 2; t < temp.length; t++){
        temp[t] += temp[t - 1];
      }
      double ans = Double.NEGATIVE_INFINITY;
      for(int j = 0; j < temp.length; j++){
        for(int k = j + 1; k < temp.length && k - j <= m; k++){
          ans = Math.max(ans, temp[k] - temp[j]);
        }
      }
      mqAnswers[i] = ans;
    }
  }

  @Test
  void monotonousQueueTest(){
    for(int i = 0; i < iteration; i++){
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

  @Test
  void minCyclicShiftTest(){
    assertEquals("abcdef", minCyclicShift("abcdef"));
    assertEquals("abcdef", minCyclicShift("fabcde"));
    assertEquals("abcdef", minCyclicShift("efabcd"));
    assertEquals("abcdef", minCyclicShift("defabc"));
    assertEquals("abcdef", minCyclicShift("cdefab"));
    assertEquals("abcdef", minCyclicShift("bcdefa"));
  }

  int[][] xorCases;
  int[] xorAnswers;

  {
    xorCases = new int[iteration][];
    xorAnswers = new int[iteration];
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(4 - 2) + 2;
      xorCases[i] = ArrayUtils.randomIntArray(1, 256, len);
      int max = Integer.MIN_VALUE;
      for(int j = 0; j < xorCases[i].length; j++){
        for(int k = j + 1; k < xorCases[i].length; k++){
          max = Math.max(xorCases[i][j] ^ xorCases[i][k], max);
        }
      }
      xorAnswers[i] = max;
    }
  }

  @Test
  void largestXORPairTest(){
    for(int i = 0; i < iteration; i++){
      var ans = largestXORPair(xorCases[i]);
      assertEquals(xorAnswers[i], ans.first() ^ ans.second());
    }
  }

  @Test
  void sequenceTest(){
    var res = new int[]{0, 1, 2, 2, 3};
    var ans = sequence(new int[][]{
            new int[]{1, 2, 3, 4, 5},
            new int[]{2, 4, 6, 8, 10},
            new int[]{-3, -1, 1, 3, 5}
    });
    assertArrayEquals(ans, res);
  }
}