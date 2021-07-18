package org.nathan.algsJ.numeric;

import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SplittableRandom;

public class NumberTheory{
  /**
   * @param x x
   * @param y y
   * @return addition not overflow
   * @see Math#addExact(int, int)
   */
  public static boolean addNotOverflow(int x, int y){
    int r = x + y;
    // HD 2-12 Overflow iff both arguments have the opposite sign of the result
    return !(((x ^ r) & (y ^ r)) < 0);
  }

  public static IntArrayList sieveOfEratosthenes(int limit){
    boolean[] prime = new boolean[limit + 1];
    Arrays.fill(prime, true);
    for(long p = 2; p * p <= limit; p++){
      if(prime[(int) p]){
        for(long i = p * 2; i <= limit; i += p){
          prime[(int) i] = false;
        }
      }
    }
    IntArrayList primeNumbers = new IntArrayList((int) (limit / Math.log(limit)));
    for(int i = 2; i <= limit; i++){
      if(prime[i]){
        primeNumbers.add(i);
      }
    }
    return primeNumbers;
  }

  private final static BigInteger ZERO = new BigInteger("0");
  private final static BigInteger ONE = new BigInteger("1");
  private final static BigInteger TWO = new BigInteger("2");
  private final static SecureRandom random = new SecureRandom();
  private final static SplittableRandom sRandom = new SplittableRandom();

  private static BigInteger rho(BigInteger N){
    BigInteger divisor;
    BigInteger c = new BigInteger(N.bitLength(), random);
    BigInteger x = new BigInteger(N.bitLength(), random);
    BigInteger xx = x;

    // check divisibility by 2
    if(N.mod(TWO).compareTo(ZERO) == 0){ return TWO; }

    do{
      x = x.multiply(x).mod(N).add(c).mod(N);
      xx = xx.multiply(xx).mod(N).add(c).mod(N);
      xx = xx.multiply(xx).mod(N).add(c).mod(N);
      divisor = x.subtract(xx).gcd(N);
    }
    while((divisor.compareTo(ONE)) == 0);

    return divisor;
  }

  private static int bitLength(int i){
    var t = i < 0 ? -i : i + 1;
    return (int) Math.ceil(Math.log(t) / Math.log(2));
  }

  // TODO gcd
  public static int gcd(int a, int b){
    return 0;
  }

  private static int rho(int N){
    int divisor;
    int bLen = (int) Math.pow(2, bitLength(N));
    int c = sRandom.nextInt(bLen);
    int x = sRandom.nextInt(bLen);
    int xx = x;

    // check divisibility by 2
    if(N % 2 == 0){ return 2; }

    do{
      x = (((x * x) % N) + c) % N;
      xx = (((xx * xx) % N) + c) % N;
      xx = (((xx * xx) % N) + c) % N;
      divisor = gcd((x - xx), N);
    }
    while(divisor == 1);

    return divisor;
  }

  public static List<BigInteger> factor(BigInteger N){
    if(N.compareTo(ZERO) <= 0){
      throw new IllegalArgumentException();
    }
    List<BigInteger> ans = new ArrayList<>();
    var funcFactor = new Object(){
      void apply(BigInteger N){
        if(N.compareTo(ONE) == 0){ return; }
        if(N.isProbablePrime(20)){
          ans.add(N);
          return;
        }
        BigInteger divisor = rho(N);
        apply(divisor);
        apply(N.divide(divisor));
      }
    };
    funcFactor.apply(N);
    return ans;
  }

  // TODO Miller Rabin test
  public static boolean MillerRabinTest(int i, int capacity){
    return false;
  }

  public static IntArrayList factor(int N){
    if(N <= 0){
      throw new IllegalArgumentException();
    }
    var ans = new IntArrayList();
    var funcFactor = new Object(){
      void apply(int N){
        if(N == 1){ return; }
        if(MillerRabinTest(N,20)){
          ans.add(N);
          return;
        }
        int divisor = rho(N);
        apply(divisor);
        apply(N / divisor);
      }
    };
    funcFactor.apply(N);
    return ans;
  }

}
