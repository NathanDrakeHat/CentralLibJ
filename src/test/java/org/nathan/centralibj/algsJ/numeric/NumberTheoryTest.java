package org.nathan.centralibj.algsJ.numeric;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.util.XoRoShiRo128PlusRandom;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.*;

public class NumberTheoryTest{
  private static final int iter = 20;

  IntArrayList primes = NumberTheory.primesGenEulerSieve(200);
  @Test
  void MillerRabinTestTest(){
    for(var i : primes){
      assertTrue(NumberTheory.primeTestMillerRabin(i, 20));
    }
  }

  int[][] factorCases = new int[iter][];
  {
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 0; i < iter; i++){
      factorCases[i] = new int[4];
      var s = rand.nextInt(primes.size()-4);
      for(int j = 0; j < 4; j++){
        factorCases[i][j] = primes.getInt(s + j);
      }
    }
  }

  @Test
  void primesGenTest(){
    assertEquals(664579,	 NumberTheory.primesGenEulerSieve((int)Math.pow(10,7)).size());
  }

  @Test
  void factorTest(){
    for(int i = 0 ; i < iter; i++){
      var f = factorCases[i];
      var ans = NumberTheory.primeFactorizationPollardsRho(f[0] * f[1] * f[2] * f[3]);
      var sortAns = ans.toArray(new int[]{});
      Arrays.sort(sortAns);
      assertArrayEquals(f, sortAns);
    }
  }
}
