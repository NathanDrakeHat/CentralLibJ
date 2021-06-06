package org.nathan.acm;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.utils.ArrayUtils;
import org.nathan.centralUtils.utils.LambdaUtils;
import org.nathan.centralUtils.utils.NumericUtils;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.nathan.acm.ACM0x00.*;

public class ACM0x00Test{

  @Test
  public void fastPowerModTest(){
    assertEquals(2, fastPowerMod(3, 3, 25));
    assertEquals(3, fastPowerMod(4, 3, 61));
  }

  @Test
  public void longFastMultiplyModTest(){
    assertEquals(1, longFastMultiplyMod1(3, 7, 20));
    assertEquals(5, longFastMultiplyMod1(5, 5, 20));
    assertEquals(1, longFastMultiplyMod2(3, 7, 20));
    assertEquals(5, longFastMultiplyMod2(5, 5, 20));
  }

  @Test
  public void solveHamiltonTest(){
    double[][] weights = new double[5][5];
    for(var r : weights){
      for(int i = 0; i < weights.length; i++){
        r[i] = Double.POSITIVE_INFINITY;
      }
    }
    for(int i = 0; i < 5; i++){
      weights[i][i] = 0;
    }

    LambdaUtils.TriConsumer<Integer, Integer, Double> putBiDirect = (Integer a, Integer b, Double val) -> {
      weights[a][b] = val;
      weights[b][a] = val;
    };

    putBiDirect.accept(0, 1, 2.);
    putBiDirect.accept(1, 2, 2.);
    putBiDirect.accept(2, 3, 2.);
    putBiDirect.accept(3, 4, 2.);
    putBiDirect.accept(0, 4, 9.);
    putBiDirect.accept(0, 3, 7.);
    putBiDirect.accept(0, 2, 5.);

    assertEquals(8, solve_hamilton(5, weights));
  }

  @Test
  public void strangeSwitchTest(){
    String[][] case1 = new String[][]{
            new String[]{"x", "o", "x"},
            new String[]{"o", "o", "o"},
            new String[]{"x", "o", "x"}
    };
    assertEquals(1, strangeSwitch(case1));

    String[][] case2 = new String[][]{
            new String[]{"x", "x", "x"},
            new String[]{"x", "x", "x"},
            new String[]{"x", "x", "x"}
    };
    assertEquals(0, strangeSwitch(case2));

    String[][] case3 = new String[][]{
            new String[]{"x", "x", "o"},
            new String[]{"x", "x", "o"},
            new String[]{"o", "o", "x"}
    };
    assertEquals(2, strangeSwitch(case3));
  }

  @Test
  public void laserBombTest(){
    int[][] case1 = new int[][]{
            new int[]{1, 2, 3, 2, 1},
            new int[]{2, 3, 4, 3, 2},
            new int[]{3, 4, 5, 4, 3},
            new int[]{5, 6, 7, 5, 6},
            new int[]{2, 3, 4, 3, 2}
    };

    assertEquals(22, laserBomb(case1, 2));
    assertEquals(7, laserBomb(case1, 1));
    assertEquals(85, laserBomb(case1, 5));
    assertEquals(85, laserBomb(case1, 6));
  }

  @Test
  public void sumDivTest(){
    //noinspection OptionalGetWithoutIsPresent
    assertEquals((NumericUtils.getAllDivisors((int) Math.pow(6, 6)).stream().reduce(Integer::sum).get() % 9901),
            sumDiv(6, 6));
  }

  @Test
  public void maxExtremumTest(){
    var t1 = List.of(1, 2, 3, 2, 1);
    var t2 = List.of(1, 2, 3, 4);
    var t3 = List.of(4, 3, 2, 1);
    var t4 = List.of(1, 3, 2, 1, 0);
    var t5 = List.of(1, 2, 3, 2);
    var t6 = List.of(1, 2, 1, 0);

    assertEquals(2, maxExtremum(t1));
    assertEquals(3, maxExtremum(t2));
    assertEquals(0, maxExtremum(t3));
    assertEquals(1, maxExtremum(t4));
    assertEquals(2, maxExtremum(t5));
    assertEquals(1, maxExtremum(t6));
  }

  @Test
  public void bestCowFencesTest(){
    // past 100000 iteration
    for(int i = 0; i < 20; i++){
      int[] testCase = ArrayUtils.randomIntArray(-10, 11, 20);
      int limit = 5;
      var res = bestCowFences(testCase, limit);

      int[] sumTestCase = new int[testCase.length];
      System.arraycopy(testCase, 0, sumTestCase, 0, testCase.length);
      for(int j = 1; j < sumTestCase.length; j++){
        sumTestCase[j] += sumTestCase[j - 1];
      }
      int dumbRes = -11;
      for(int j = 0; j < sumTestCase.length - limit + 1; j++){
        for(int k = j + limit; k < sumTestCase.length; k++){
          int t = (int) Math.floor((sumTestCase[k] - sumTestCase[j]) / (double) (k - j));
          if(t > dumbRes){
            dumbRes = t;
          }
        }
      }
      assertEquals(dumbRes, res);
    }
  }

  @Test
  public void innovativeBusinessTest(){
    int size = 20;
    boolean[][] greater = new boolean[size][];
    for(int i = 0; i < size; i++){
      greater[i] = new boolean[size];
    }
    Random rand = new Random();
    for(int i = 0; i < size - 1; i++){
      for(int j = i + 1; j < size; j++){
        var t = rand.nextBoolean();
        greater[i][j] = t;
        greater[j][i] = !t;
      }
    }
    List<Integer> list = innovativeBusiness(greater);
    for(int i = 0; i < list.size() - 1; i++){
      if(greater[list.get(i)][list.get(i + 1)]){
        fail();
      }
    }
  }
}
