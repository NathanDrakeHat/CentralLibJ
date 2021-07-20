package org.nathan.acm;

import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;
import org.nathan.algsJ.numeric.NumberTheory;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ACM0x30Test{
  static final int iteration = 100;

  List<Tuple<Integer, Integer>> pdCases;
  IntList primes;
  int[] pdAnswers = new int[iteration];

  {
    primes = NumberTheory.primesGenEulerSieve((int) Math.pow(10, 4));
    pdCases = new ArrayList<>(iteration);
    var rand = new SplittableRandom();
    for(int i = 0; i < iteration; i++){
      var s = rand.nextInt(0, primes.size() / 2);
      var e = rand.nextInt(primes.size() / 2, primes.size());
      pdCases.add(new Tuple<>(s, e));
      int max = -1;
      for(int j = s; j < e; j++){
        var a = primes.getInt(j);
        var b = primes.getInt(j + 1);
        max = Math.max(max, b - a);
      }
      pdAnswers[i] = max;
    }
  }


  @Test
  void primeDistanceTest(){
    for(int i = 0; i < iteration; i++){
      var t = pdCases.get(i);
      var a = t.first();
      var b = t.second();
      var ans = ACM0x30.primeDistance(primes.getInt(a), primes.getInt(b));
      assertEquals(pdAnswers[i], ans);
    }
  }
}