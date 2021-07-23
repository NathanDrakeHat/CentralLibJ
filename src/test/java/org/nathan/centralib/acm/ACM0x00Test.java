package org.nathan.centralib.acm;

import org.junit.jupiter.api.Test;
import org.nathan.centralib.algsJ.misc.SortTest;
import org.nathan.centralib.algsJ.numeric.NumberTheory;
import org.nathan.centralib.utils.ArrayUtils;
import org.nathan.centralib.utils.LambdaUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.nathan.centralib.acm.ACM0x00.*;
import static org.nathan.centralib.utils.ArrayUtils.randomDoubleArray;

public class ACM0x00Test {
  static final int iteration = 100; // iter of for

  @Test
  public void fastPowerModTest() {
    assertEquals(2, fastPowerMod(3, 3, 25));
    assertEquals(3, fastPowerMod(4, 3, 61));
  }

  @Test
  public void longFastMultiplyModTest() {
    assertEquals(1, longFastMultiplyMod1(3, 7, 20));
    assertEquals(5, longFastMultiplyMod1(5, 5, 20));
    assertEquals(1, longFastMultiplyMod2(3, 7, 20));
    assertEquals(5, longFastMultiplyMod2(5, 5, 20));
  }

  double[][] hamiltonCase = new double[5][5];

  {
    for (var r : hamiltonCase) {
      for (int i = 0; i < hamiltonCase.length; i++) {
        r[i] = Double.POSITIVE_INFINITY;
      }
    }
    for (int i = 0; i < 5; i++) {
      hamiltonCase[i][i] = 0;
    }

    LambdaUtils.TriConsumer<Integer, Integer, Double> putBiDirect = (Integer a, Integer b, Double val) -> {
      hamiltonCase[a][b] = val;
      hamiltonCase[b][a] = val;
    };

    putBiDirect.accept(0, 1, 2.);
    putBiDirect.accept(1, 2, 2.);
    putBiDirect.accept(2, 3, 2.);
    putBiDirect.accept(3, 4, 2.);
    putBiDirect.accept(0, 4, 9.);
    putBiDirect.accept(0, 3, 7.);
    putBiDirect.accept(0, 2, 5.);
  }

  @Test
  public void solveHamiltonTest() {
    assertEquals(8, solveHamilton(5, hamiltonCase));
  }

  String[][] ssCase1 = new String[][]{
          new String[]{"x", "o", "x"},
          new String[]{"o", "o", "o"},
          new String[]{"x", "o", "x"}
  };

  String[][] ssCase2 = new String[][]{
          new String[]{"x", "x", "x"},
          new String[]{"x", "x", "x"},
          new String[]{"x", "x", "x"}
  };

  String[][] ssCase3 = new String[][]{
          new String[]{"x", "x", "o"},
          new String[]{"x", "x", "o"},
          new String[]{"o", "o", "x"}
  };

  @Test
  public void strangeSwitchTest() {

    assertEquals(1, strangeSwitch(ssCase1));
    assertEquals(0, strangeSwitch(ssCase2));
    assertEquals(2, strangeSwitch(ssCase3));
  }

  int[][] laserBombCase = new int[][]{
          new int[]{1, 2, 3, 2, 1},
          new int[]{2, 3, 4, 3, 2},
          new int[]{3, 4, 5, 4, 3},
          new int[]{5, 6, 7, 5, 6},
          new int[]{2, 3, 4, 3, 2}
  };

  @Test
  public void laserBombTest() {
    assertEquals(22, laserBomb(laserBombCase, 2));
    assertEquals(7, laserBomb(laserBombCase, 1));
    assertEquals(85, laserBomb(laserBombCase, 5));
    assertEquals(85, laserBomb(laserBombCase, 6));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void sumDivTest() {
    assertEquals((NumberTheory.allDivisorsOf((int) Math.pow(6, 6)).stream().reduce(Integer::sum).get() % 9901),
            sumDiv(6, 6));
  }

  List<Integer> meCase1 = List.of(1, 2, 3, 2, 1);
  List<Integer> meCase2 = List.of(1, 2, 3, 4);
  List<Integer> meCase3 = List.of(4, 3, 2, 1);
  List<Integer> meCase4 = List.of(1, 3, 2, 1, 0);
  List<Integer> meCase5 = List.of(1, 2, 3, 2);
  List<Integer> meCase6 = List.of(1, 2, 1, 0);

  @Test
  public void maxExtremumTest() {
    assertEquals(2, maxExtremum(meCase1));
    assertEquals(3, maxExtremum(meCase2));
    assertEquals(0, maxExtremum(meCase3));
    assertEquals(1, maxExtremum(meCase4));
    assertEquals(2, maxExtremum(meCase5));
    assertEquals(1, maxExtremum(meCase6));
  }

  int[][] bestCowFencesCases = new int[iteration][];
  int[] bfAnswers = new int[iteration];
  int bfLimit = 5;

  {
    Random rand = new Random();
    for (int i = 0; i < iteration; i++) {
      bestCowFencesCases[i] = ArrayUtils.randomIntArray(-10, 11, rand.nextInt(20) + 10);

      var testCase = bestCowFencesCases[i];
      int[] sumTestCase = new int[testCase.length];
      System.arraycopy(testCase, 0, sumTestCase, 0, testCase.length);
      for (int j = 1; j < sumTestCase.length; j++) {
        sumTestCase[j] += sumTestCase[j - 1];
      }
      int dumbRes = -11;
      for (int j = 0; j < sumTestCase.length - bfLimit + 1; j++) {
        for (int k = j + bfLimit; k < sumTestCase.length; k++) {
          int t = (int) Math.floor((sumTestCase[k] - sumTestCase[j]) / (double) (k - j));
          if (t > dumbRes) {
            dumbRes = t;
          }
        }
      }
      bfAnswers[i] = dumbRes;
    }


  }

  @Test
  public void bestCowFencesTest() {
    // past 100000 iteration
    for (int i = 0; i < iteration; i++) {
      int[] testCase = bestCowFencesCases[i];
      var res = bestCowFences(testCase, bfLimit);
      assertEquals(bfAnswers[i], res);
    }
  }


  boolean[][] ibCases = new boolean[20][];

  {
    for (int i = 0; i < 20; i++) {
      ibCases[i] = new boolean[20];
    }
    var rand = new SplittableRandom();
    for (int i = 0; i < 20 - 1; i++) {
      for (int j = i + 1; j < 20; j++) {
        var t = rand.nextBoolean();
        ibCases[i][j] = t;
        ibCases[j][i] = !t;
      }
    }
  }

  @Test
  public void innovativeBusinessTest() {
    List<Integer> list = innovativeBusiness(ibCases);
    for (int i = 0; i < list.size() - 1; i++) {
      if (ibCases[list.get(i)][list.get(i + 1)]) {
        fail();
      }
    }
  }

  int[][] runningMedianCases;
  IntStream[] runningMedianAnswers;

  {
    runningMedianCases = new int[iteration][];
    runningMedianAnswers = new IntStream[iteration];
    var rand = new SplittableRandom();
    for (int i = 0; i < iteration; i++) {
      int len = rand.nextInt(30, 50);
      runningMedianCases[i] = ArrayUtils.shuffledIntArray(-len, len, 1);
      var b = IntStream.builder();
      for (int j = 1; j <= runningMedianCases[i].length; j += 2) {
        int[] temp_array = Arrays.copyOf(runningMedianCases[i], j);
        Arrays.sort(temp_array);
        b.add(temp_array[temp_array.length / 2]);
      }
      runningMedianAnswers[i] = b.build();
    }
  }

  @Test
  public void runningMedianTest() {
    for (int i = 0; i < iteration; i++) {
      var rand_int_stream = Arrays.stream(runningMedianCases[i]);
      var res = runningMedian(rand_int_stream);
      assertEquals(runningMedianAnswers[i].boxed().collect(Collectors.toList()), res.boxed().collect(Collectors.toList()));
    }
  }

  double[][] ultraQuickSortCases = new double[iteration][];
  int[] ultraQuickSortAnswers = new int[iteration];

  {
    var rand = new SplittableRandom();
    for (int i = 0; i < iteration; i++) {
      int len = rand.nextInt(3) + 3;
      int bound = rand.nextInt(5) + 10;
      ultraQuickSortCases[i] = randomDoubleArray(-bound, bound, len);
      double[] temp = new double[len];
      System.arraycopy(ultraQuickSortCases[i], 0, temp, 0, len);
      var funcBubbleSort = new Object() {
        public int count = 0;

        public void apply(double[] array) {
          for (int i = 1; i <= array.length; i++) {
            for (int j = 0; j < array.length - i; j++) {
              if (array[j] > array[j + 1]) {
                var t = array[j];
                array[j] = array[j + 1];
                array[j + 1] = t;
                count++;
              }
            }
          }
        }
      };
      funcBubbleSort.apply(temp);
      assertTrue(SortTest.isSorted(temp));
      ultraQuickSortAnswers[i] = funcBubbleSort.count;
    }
  }

  @Test
  public void ultraQuickSortTest() {
    for (int i = 0; i < iteration; i++) {
      var res = ultraQuickSort(ultraQuickSortCases[i]);
      assertEquals(ultraQuickSortAnswers[i], res);
    }
  }

  @Test
  public void geniusACMTest() {
    var a = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
    assertEquals(4, geniusACM(a, 1));
    assertEquals(2, geniusACM(a, 10));
    assertEquals(1, geniusACM(a, 84));
  }

  double[][] STCases;
  double[] STAnswers;

  {
    STCases = new double[iteration][];
    STAnswers = new double[iteration];
    var rand = new SplittableRandom();
    for (int i = 0; i < iteration; i++) {
      STCases[i] = ArrayUtils.randomDoubleArray(-30, 30, rand.nextInt(30, 40));
      var temp = new double[10];
      System.arraycopy(STCases[i], 10, temp, 0, 10);
      Arrays.sort(temp);
      STAnswers[i] = temp[temp.length - 1];
    }
  }

  @Test
  public void STTest() {
    for(int i = 0; i < iteration; i++){
      var st = new ST(STCases[i]);
      assertEquals(STAnswers[i], st.maxOfRange(10,20));
    }
  }
}
