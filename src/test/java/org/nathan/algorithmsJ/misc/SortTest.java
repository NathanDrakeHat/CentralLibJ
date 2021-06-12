package org.nathan.algorithmsJ.misc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.nathan.centralUtils.utils.ArrayUtils.randomDoubleArray;
import static org.nathan.centralUtils.utils.ArrayUtils.randomIntArray;


class SortTest{
  static int iteration = 20;

  static boolean isSorted(int[] res){
    boolean is_sorted = true;
    for(int i = 1; i < res.length; i++){
      if(res[i - 1] > res[i]){
        is_sorted = false;
        break;
      }
    }
    return is_sorted;
  }

  static boolean isSorted(double[] res){
    boolean is_sorted = true;
    for(int i = 1; i < res.length; i++){
      if(res[i - 1] > res[i]){
        is_sorted = false;
        break;
      }
    }
    return is_sorted;
  }

  static boolean isSorted(SimpleDate[] res){
    boolean is_sorted = true;
    for(int i = 1; i < res.length; i++){
      if(res[i - 1].year > res[i].year){
        is_sorted = false;
        break;
      }
      else if((res[i - 1].year == res[i].year) && (res[i - 1].month > res[i].month)){
        is_sorted = false;
        break;
      }
      else if((res[i - 1].year == res[i].year) && (res[i - 1].month == res[i].month) && (res[i - 1].day > res[i].day)){
        is_sorted = false;
        break;
      }
    }
    return is_sorted;
  }


  double[][] iterMergeSortCase = new double[iteration][];

  {
    var rand = ThreadLocalRandom.current();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      iterMergeSortCase[i] = randomDoubleArray(-bound, bound, len);
    }
  }

  @Test
  void iterativeMergeSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.iterativeMergeSort(iterMergeSortCase[i]);
      assertTrue(isSorted(iterMergeSortCase[i]));
    }
  }

  double[][] recurMergeSortCase = new double[iteration][];

  {
    var rand = ThreadLocalRandom.current();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      recurMergeSortCase[i] = randomDoubleArray(-bound, bound, len);
    }
  }

  @Test
  void recursiveMergeSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.recursiveMergeSort(recurMergeSortCase[i]);
      assertTrue(isSorted(recurMergeSortCase[i]));
    }
  }

  double[][] heapSortCase = new double[iteration][];

  {
    var rand = ThreadLocalRandom.current();

    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      heapSortCase[i] = randomDoubleArray(-bound, bound, len);
    }
  }

  @Test
  void heapSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.heapSort(heapSortCase[i]);
      assertTrue(isSorted(heapSortCase[i]));
    }
  }

  double[][] quickSortCase = new double[iteration][];

  {
    var rand = ThreadLocalRandom.current();

    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      quickSortCase[i] = randomDoubleArray(-bound, bound, len);
    }
  }

  @Test
  void quickSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.quickSort(quickSortCase[i]);
      assertTrue(isSorted(quickSortCase[i]));
    }
  }

  double[][] randQuickSortCase = new double[iteration][];

  {
    var rand = ThreadLocalRandom.current();

    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      randQuickSortCase[i] = randomDoubleArray(-bound, bound, len);
    }
  }

  @Test
  void randQuickSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.randomQuickSort(randQuickSortCase[i]);
      assertTrue(isSorted(randQuickSortCase[i]));
    }
  }

  int[][] countingSortCase = new int[iteration][];

  {
    var rand = ThreadLocalRandom.current();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      countingSortCase[i] = randomIntArray(-bound, bound, len);
    }
  }

  @Test
  void countingSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.countingSort(countingSortCase[i]);
      assertTrue(isSorted(countingSortCase[i]));
    }
  }

  static SimpleDate[] buildDate(){
    var rand = ThreadLocalRandom.current();
    int len = rand.nextInt(20) + 20;
    SimpleDate[] res = new SimpleDate[len];
    int[] years = randomIntArray(2000, 2022, len);
    int[] months = randomIntArray(1, 13, len);
    int[] days = randomIntArray(1, 29, len);
    for(int i = 0; i < len; i++){
      res[i] = new SimpleDate(years[i], months[i], days[i]);
    }
    return res;
  }

  SimpleDate[][] radixSortData = new SimpleDate[iteration][];

  {
    for(int i = 0; i < iteration; i++){
      radixSortData[i] = buildDate();
    }
  }

  @Test
  void radixSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.radixSort(radixSortData[i]);
      assertTrue(isSorted(radixSortData[i]));
    }
  }

  double[][] bucketSortCase = new double[iteration][];

  {
    var rand = ThreadLocalRandom.current();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      bucketSortCase[i] = randomDoubleArray(0, 1, len);
    }
  }

  @Test
  void bucketSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.bucketSort(bucketSortCase[i]);
      assertTrue(isSorted(bucketSortCase[i]));
    }
  }

  Map<String, Integer> nameToSection = new HashMap<>(20);
  String[] names = ("Anderson," +
          "Brown," +
          "Davis," +
          "Garcia," +
          "Harris," +
          "Jackson," +
          "Johnson," +
          "Jones," +
          "Martin," +
          "Martinez," +
          "Miller," +
          "Moore," +
          "Robinson," +
          "Smith," +
          "Taylor," +
          "Thomas," +
          "Thompson," +
          "White," +
          "Williams," +
          "Wilson").split(",");
  List<String> nameSortAnswer = Arrays.stream(("Harris,Martin,Moore,Anderson,Martinez,Miller,Robinson,White," +
          "Brown,Davis,Jackson,Jones,Taylor,Williams,Garcia,Johnson,Smith,Thomas,Thompson,Wilson").split(
          ",")).toList();

  {
    int[] section = new int[]{2, 3, 3, 4, 1, 3, 4, 3, 1, 2, 2, 1, 2, 4, 3, 4, 4, 2, 3, 4};
    for(int i = 0; i < names.length; i++){
      nameToSection.put(names[i], section[i]);
    }
  }


  @Test
  void keyIndexCountingSortTest(){
    var l = Arrays.stream(names).collect(Collectors.toList());
    var sorter = new Sort.KeyIndexedCountingSorter<String>(256);
    sorter.sort(l, name -> nameToSection.get(name));
    assertEquals(nameSortAnswer, l);
  }
}