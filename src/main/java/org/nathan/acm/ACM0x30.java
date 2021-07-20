package org.nathan.acm;

import org.nathan.algsJ.numeric.NumberTheory;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.List;
import java.util.stream.IntStream;

public class ACM0x30{
  public static int primeDistance(int L, int R){
    var primes = NumberTheory.primesGenEulerSieve((int) Math.ceil(Math.sqrt(R)));
    var range = IntStream.range(L, R+1).filter(i->{
      for(var p : primes){
        if(i % p == 0) return false;
      }
      return true;
    }).toArray();
    int max = range[1] - range[0];
    for(int i = 1; i < range.length - 1; i++){
      max = Math.max(max, range[i+1] - range[i]);
    }
    return max;
  }


  /**
   * prime factorization of factorial(N!)
   * @param N N
   * @return prime factorization of factorial(N!)
   */
  public static List<Tuple<Integer,Integer>> factorialPrimeFactorization(int N){
    return null;
  }
}
