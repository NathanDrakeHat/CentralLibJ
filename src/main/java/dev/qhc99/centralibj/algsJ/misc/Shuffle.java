package dev.qhc99.centralibj.algsJ.misc;

import it.unimi.dsi.util.XoRoShiRo128PlusRandom;

public class Shuffle{
  public static <T> void KnuthShuffle(T[] array){
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 1; i < array.length; i++){
      int r = rand.nextInt(i + 1);
      var t = array[i];
      array[i] = array[r];
      array[r] = t;
    }
  }
  public static void KnuthShuffle(int[] array){
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 1; i < array.length; i++){
      int r = rand.nextInt(i + 1);
      var t = array[i];
      array[i] = array[r];
      array[r] = t;
    }
  }
  public static void KnuthShuffle(double[] array){
    var rand = new XoRoShiRo128PlusRandom();
    for(int i = 1; i < array.length; i++){
      int r = rand.nextInt(i + 1);
      var t = array[i];
      array[i] = array[r];
      array[r] = t;
    }
  }
}
