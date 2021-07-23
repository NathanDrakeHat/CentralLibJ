package org.nathan.centralib.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralib.algsJ.numeric.NumberTheory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ACM0x30{
  public static int primeDistance(int L, int R){
    var primes = NumberTheory.primesGenEulerSieve((int) Math.ceil(Math.sqrt(R)));
    var range = IntStream.range(L, R + 1).filter(i -> {
      for(var p : primes){
        if(i % p == 0){ return false; }
      }
      return true;
    }).toArray();
    int max = range[1] - range[0];
    for(int i = 1; i < range.length - 1; i++){
      max = Math.max(max, range[i + 1] - range[i]);
    }
    return max;
  }


  /**
   * prime factorization of factorial(N!)
   *
   * @param N N
   * @return prime factorization of factorial(N!)
   */
  public static @NotNull Map<Integer, Integer> factorialPrimeFactorization(int N){
    var primes = NumberTheory.primesGenEulerSieve(N);
    Map<Integer, Integer> ans = new HashMap<>(primes.size());
    for(var p : primes){
      int k = (int) (Math.floor(Math.log(N) / Math.log(p)));
      for(int i = 1; i <= k; i++){
        ans.put(p, (int) (ans.getOrDefault(p, 0) + Math.floor(N / Math.pow(p, i))));
      }
    }
    return ans;
  }

  /**
   * k % 1 + k % 2 + ... + k % n
   * @param k base
   * @param n number
   * @return sum of remainders of k from 1 to n
   */
  public static int sumOfRemainders(int k, int n){
    return 0;
  }
}
