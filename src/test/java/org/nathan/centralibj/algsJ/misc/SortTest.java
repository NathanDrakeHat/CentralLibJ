package org.nathan.centralibj.algsJ.misc;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.nathan.centralibj.utils.ArrayUtils.randomDoubleArray;
import static org.nathan.centralibj.utils.ArrayUtils.randomIntArray;


public class SortTest{
  static final int iteration = 100;

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

  public static boolean isSorted(double[] res){
    boolean is_sorted = true;
    for(int i = 1; i < res.length; i++){
      if(res[i - 1] > res[i]){
        is_sorted = false;
        break;
      }
    }
    return is_sorted;
  }

  static boolean isSorted(Sort.SimpleDate[] res){
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

  double[][] quickSort3WayCase = new double[iteration][];

  {
    var rand = new SplittableRandom();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      quickSort3WayCase[i] = randomDoubleArray(-bound, bound, len);
    }
  }

  @Test
  void quickSort3WayTest(){
    for(int i = 0; i < iteration; i++){
      Sort.quickSort3Way(quickSort3WayCase[i]);
      assertTrue(isSorted(quickSort3WayCase[i]));
    }
  }

  double[][] shellSortCase = new double[iteration][];

  {
    var rand = new SplittableRandom();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      shellSortCase[i] = randomDoubleArray(-bound, bound, len);
    }
  }

  @Test
  void shellSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.shellSort(shellSortCase[i]);
      assertTrue(isSorted(shellSortCase[i]));
    }
  }

  double[][] insertionSortCase = new double[iteration][];

  {
    var rand = new SplittableRandom();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      insertionSortCase[i] = randomDoubleArray(-bound, bound, len);
    }
  }

  @Test
  void insertionSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.insertionSort(insertionSortCase[i]);
      assertTrue(isSorted(insertionSortCase[i]));
    }
  }

  double[][] selectionSortCase = new double[iteration][];

  {
    var rand = new SplittableRandom();
    for(int i = 0; i < iteration; i++){
      int len = rand.nextInt(10) + 20;
      int bound = rand.nextInt(5) + 10;
      selectionSortCase[i] = randomDoubleArray(-bound, bound, len);
    }
  }

  @Test
  void selectionSortTest(){
    for(int i = 0; i < iteration; i++){
      Sort.selectionSort(selectionSortCase[i]);
      assertTrue(isSorted(selectionSortCase[i]));
    }
  }


  double[][] iterMergeSortCase = new double[iteration][];

  {
    var rand = new SplittableRandom();
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
    var rand = new SplittableRandom();
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
    var rand = new SplittableRandom();

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
    var rand = new SplittableRandom();

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
    var rand = new SplittableRandom();

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
    var rand = new SplittableRandom();
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

  static Sort.SimpleDate[] buildDate(){
    var rand = new SplittableRandom();
    int len = rand.nextInt(20) + 20;
    Sort.SimpleDate[] res = new Sort.SimpleDate[len];
    int[] years = randomIntArray(2000, 2022, len);
    int[] months = randomIntArray(1, 13, len);
    int[] days = randomIntArray(1, 29, len);
    for(int i = 0; i < len; i++){
      res[i] = new Sort.SimpleDate(years[i], months[i], days[i]);
    }
    return res;
  }

  Sort.SimpleDate[][] radixSortData = new Sort.SimpleDate[iteration][];

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
    var rand = new SplittableRandom();
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
  String[] names = ("Anderson,Brown,Davis,Garcia,Harris,Jackson,Johnson,Jones,Martin,Martinez,Miller,Moore,Robinson," +
          "Smith,Taylor,Thomas,Thompson,White,Williams,Wilson").split(",");
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

  List<String> LSDData =
          new ArrayList<>(Arrays.stream("dab,add,cab,fad,fee,bad,dad,bee,fed,bed,ebb,ace".split(",")).toList());
  List<String> LSDAnswer = Arrays.stream("ace,add,bad,bed,bee,cab,dab,dad,ebb,fad,fed,fee".split(",")).toList();

  @Test
  void LSDTest(){
    Sort.LSDRadixSort(LSDData);
    assertEquals(LSDAnswer.size(), LSDData.size());
    for(int i = 0; i < LSDData.size(); i++){
      assertEquals(LSDAnswer.get(i), LSDData.get(i));
    }
  }

  List<String> MSDData = new ArrayList<>(Arrays.stream(("she,sells,seashells,by,the,sea,shore,the,shells,she,sells," +
          "are,surely,seashells").split(",")).toList());
  List<String> MSDAnswer = Arrays.stream(("are,by,sea,seashells,seashells,sells,sells,she,she,shells,shore,surely,the," +
          "the").split(",")).toList();

  @Test
  void MSDTest(){
    Sort.MSDRadixSort(MSDData);
    assertEquals(MSDAnswer, MSDAnswer);
  }

  List<String> q3Data1 = new ArrayList<>(Arrays.stream(("she,sells,seashells,by,the,sea,shore,the,shells,she,sells," +
          "are,surely,seashells").split(",")).toList());
  List<String> q3Answer1 = Arrays.stream(("are,by,sea,seashells,seashells,sells,sells,she,she,shells,shore,surely,the," +
          "the").split(",")).toList();
  List<String> q3Data2 =
          new ArrayList<>(Arrays.stream("dab,add,cab,fad,fee,bad,dad,bee,fed,bed,ebb,ace".split(",")).toList());
  List<String> q3Answer2 = Arrays.stream("ace,add,bad,bed,bee,cab,dab,dad,ebb,fad,fed,fee".split(",")).toList();


  @Test
  void stringQuickSort3WayTest(){
    Sort.string3WayQuicksort(q3Data1);
    assertEquals(q3Answer1, q3Data1);

    Sort.string3WayQuicksort(q3Data2);
    assertEquals(q3Answer2, q3Data2);
  }

}