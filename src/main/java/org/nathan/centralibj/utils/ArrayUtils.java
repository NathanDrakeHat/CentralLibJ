package org.nathan.centralibj.utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class ArrayUtils{
  public static <T> boolean isMatrix(@NotNull T[][] array2d){
    int len = array2d.length;
    return Arrays.stream(array2d).allMatch(array -> array.length == len);
  }

  public static boolean isMatrix(int[][] array2d){
    int len = array2d.length;
    return Arrays.stream(array2d).allMatch(array -> array.length == len);
  }

  public static boolean isMatrix(double[][] array2d){
    int len = array2d.length;
    return Arrays.stream(array2d).allMatch(array -> array.length == len);
  }

  public static boolean isMatrix(boolean[][] array2d){
    int len = array2d.length;
    return Arrays.stream(array2d).allMatch(array -> array.length == len);
  }

  public static boolean isMatrix(long[][] array2d){
    int len = array2d.length;
    return Arrays.stream(array2d).allMatch(array -> array.length == len);
  }

  public static boolean isMatrix(float[][] array2d){
    int len = array2d.length;
    return Arrays.stream(array2d).allMatch(array -> array.length == len);
  }

  public static boolean isMatrix(byte[][] array2d){
    int len = array2d.length;
    return Arrays.stream(array2d).allMatch(array -> array.length == len);
  }

  public static boolean isMatrix(short[][] array2d){
    int len = array2d.length;
    return Arrays.stream(array2d).allMatch(array -> array.length == len);
  }

  public static boolean isMatrix(char[][] array2d){
    int len = array2d.length;
    return Arrays.stream(array2d).allMatch(array -> array.length == len);
  }

  public static <T> @NotNull List<T> iterableToList(Iterable<T> i){
    List<T> a = new ArrayList<>();
    for(var item : i){
      a.add(item);
    }
    return a;
  }

  public static <T> @NotNull List<T> sampleList(List<T> list, int n){
    var r = ThreadLocalRandom.current();
    List<T> a = new ArrayList<>(n);
    Set<Integer> set = new HashSet<>(n);
    for(int i = 0; i < n; i++){
      int idx;
      do{
        idx = r.nextInt(list.size());
      }
      while(set.contains(idx));
      set.add(idx);
      a.add(list.get(idx));
    }
    return a;
  }

  public static int[] randomIntArray(int low, int high, int len){
    var rand = new SplittableRandom();
    return IntStream.generate(() -> rand.nextInt(high - low) + low).limit(len).toArray();
  }

  public static List<Integer> randomIntList(int low, int high, int len){
    var rand = new SplittableRandom();
    return IntStream.generate(() -> rand.nextInt(high - low) + low).limit(len).boxed().toList();
  }

  public static double[] randomDoubleArray(double low, double high, int len){
    var rand = new SplittableRandom();
    return DoubleStream.generate(() -> rand.nextDouble() * (high - low) + low).limit(len).toArray();
  }

  public static List<Double> randomDoubleList(double low, double high, int len){
    var rand = new SplittableRandom();
    return DoubleStream.generate(() -> rand.nextDouble() * (high - low) + low).limit(len).boxed().toList();
  }

  /**
   * shuffle of [low, high)
   *
   * @param low  inclusive
   * @param high exclusive
   * @return shuffle
   */
  public static @NotNull List<Integer> shuffledRangeList(int low, int high){
    var res = IntStream.range(low, high).boxed().collect(Collectors.toList());
    Collections.shuffle(res);
    return res;
  }

  /**
   * @param low  include
   * @param high include
   * @param step step
   * @return array
   */
  public static int[] shuffledIntArray(int low, int high, int step){
    var res = IntStream.iterate(low, i -> i <= high, i -> i += step).parallel().toArray();
    KnuthShuffle(res);
    return res;
  }

  public static void KnuthShuffle(double[] array){
    var rand = new SplittableRandom();
    for(int i = 1; i < array.length; i++){
      int r = rand.nextInt(i + 1);
      var t = array[i];
      array[i] = array[r];
      array[r] = t;
    }
  }

  public static void KnuthShuffle(int[] array){
    var rand = new SplittableRandom();
    for(int i = 1; i < array.length; i++){
      int r = rand.nextInt(i + 1);
      var t = array[i];
      array[i] = array[r];
      array[r] = t;
    }
  }

  /**
   * numpy linespace
   *
   * @param start start
   * @param end   end(inclusive)
   * @param step  step
   * @return linespace
   */
  public static @NotNull List<Integer> lineSpace(int start, int end, int step){
    return IntStream.iterate(start, i -> i <= end, i -> i += step).parallel().boxed().collect(Collectors.toList());
  }

  /**
   * numpy linespace
   *
   * @param start start
   * @param end   end(inclusive)
   * @param step  step
   * @return linespace
   */
  public static @NotNull List<Double> lineSpace(double start, double end, int step){
    return DoubleStream.iterate(start, d -> d <= end, d -> d += step).parallel().boxed().collect(Collectors.toList());
  }

  public static void serializeToFile(Object object, String fullName){
    try(var file_out = new FileOutputStream(fullName);
        var out = new ObjectOutputStream(file_out)){
      out.writeObject(object);
      out.flush();
    }
    catch(IOException e){
      throw new RuntimeException(e);
    }
  }

  public static @NotNull Object deserializeFile(String fullName){
    try(var in = new ObjectInputStream(new FileInputStream(fullName))){
      return in.readObject();
    }
    catch(IOException | ClassNotFoundException e){
      throw new RuntimeException(e);
    }
  }


  /**
   * dependency on {@link Arrays#copyOf(Object[], int)}
   * <br/>usage:
   * <pre>
   *     genericArray(3, "a", "b") -> ["a", "b", null]
   * </pre>
   * <br/>specify 0 for primitive type to resolve ambiguous call
   *
   * @param len  length
   * @param vars null or variable args
   * @param <T>  any
   * @return T[]
   */
  @SafeVarargs
  public static <T> @NotNull T[] primitiveArray(int len, T... vars){
    return Arrays.copyOf(vars, len);
  }

  public static int[] primitiveArray(int len, Integer... vars){
    var res = new int[len];
    for(int i = 0; i < vars.length; i++){
      if(vars[i] != null){
        res[i] = vars[i];
      }
    }
    return res;
  }

  public static double[] primitiveArray(int len, Double... vars){
    var res = new double[len];
    for(int i = 0; i < vars.length; i++){
      if(vars[i] != null){
        res[i] = vars[i];
      }
    }
    return res;
  }

  public static boolean[] primitiveArray(int len, Boolean... vars){
    var res = new boolean[len];
    for(int i = 0; i < vars.length; i++){
      if(vars[i] != null){
        res[i] = vars[i];
      }
    }
    return res;
  }

  public static char[] primitiveArray(int len, Character... vars){
    var res = new char[len];
    for(int i = 0; i < vars.length; i++){
      if(vars[i] != null){
        res[i] = vars[i];
      }
    }
    return res;
  }

  public static byte[] primitiveArray(int len, Byte... vars){
    var res = new byte[len];
    for(int i = 0; i < vars.length; i++){
      if(vars[i] != null){
        res[i] = vars[i];
      }
    }
    return res;
  }

  public static float[] primitiveArray(int len, Float... vars){
    var res = new float[len];
    for(int i = 0; i < vars.length; i++){
      if(vars[i] != null){
        res[i] = vars[i];
      }
    }
    return res;
  }

  public static long[] primitiveArray(int len, Long... vars){
    var res = new long[len];
    for(int i = 0; i < vars.length; i++){
      if(vars[i] != null){
        res[i] = vars[i];
      }
    }
    return res;
  }

  public static short[] primitiveArray(int len, Short... vars){
    var res = new short[len];
    for(int i = 0; i < vars.length; i++){
      if(vars[i] != null){
        res[i] = vars[i];
      }
    }
    return res;
  }
}