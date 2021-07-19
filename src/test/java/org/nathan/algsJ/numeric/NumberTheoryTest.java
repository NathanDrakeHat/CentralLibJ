package org.nathan.algsJ.numeric;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.*;

public class NumberTheoryTest{
  private static final int iter = 20;

  IntArrayList primes = NumberTheory.primesEratosthenes(200);
  @Test
  void MillerRabinTestTest(){
    for(var i : primes){
      assertTrue(NumberTheory.primeTestMillerRabin(i, 20));
    }
  }

  int[][] factorCases = new int[iter][];
  {
    var rand = new SplittableRandom();
    for(int i = 0; i < iter; i++){
      factorCases[i] = new int[4];
      var s = rand.nextInt(primes.size()-4);
      for(int j = 0; j < 4; j++){
        factorCases[i][j] = primes.getInt(s + j);
      }
    }
  }

  @Test
  void factorTest(){
    for(int i = 0 ; i < iter; i++){
      var f = factorCases[i];
      var ans = NumberTheory.factorPollardsRho(f[0] * f[1] * f[2] * f[3]);
      var sortAns = ans.toArray(new int[]{});
      Arrays.sort(sortAns);
      assertArrayEquals(f, sortAns);
    }
  }
}
