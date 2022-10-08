package dev.qhc99.centralibj.algsJ.misc;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.ToIntFunction;


public final class Sort {
  private static void exchange(double[] array, int i, int j) {
    var t = array[i];
    array[i] = array[j];
    array[j] = t;
  }

  private static <T> void exchange(List<T> array, int i, int j) {
    var t = array.get(i);
    array.set(i, array.get(j));
    array.set(j, t);
  }

  public static void selectionSort(double[] array) {
    for (int i = 0; i < array.length; i++) {
      int min_idx = i;
      for (int j = i + 1; j < array.length; j++) {
        if (array[j] < array[min_idx]) {
          min_idx = j;
        }
      }
      exchange(array, i, min_idx);
    }
  }

  public static void insertionSort(double[] array) {
    for (int i = 1; i < array.length; i++) {
      for (int j = i - 1; j >= 0 && array[j] > array[j + 1]; j--) {
        exchange(array, j, j + 1);
      }
    }
  }

  /**
   * better insertion sort
   *
   * @param array array
   */
  public static void shellSort(double[] array) {
    int h = 1;
    while (h < array.length / 3) {
      h = 3 * h + 1;
    }
    while (h >= 1) {
      for (int i = h; i < array.length; i += h) {
        for (int j = i; j >= h && array[j] < array[j - h]; j -= h) {
          exchange(array, j, j - h);
        }
      }
      h /= 3;
    }
  }

  private static void merge(double[] array, int start, double[] cache1, double[] cache2) {
    int right_idx = 0;
    int left_idx = 0;
    System.arraycopy(array, start, cache1, 0, cache1.length);
    System.arraycopy(array, start + cache1.length, cache2, 0, cache2.length);
    for (int i = start; (i < start + cache1.length + cache2.length) && (right_idx < cache2.length) && (left_idx < cache1.length); i++) {
      if (cache1[left_idx] <= cache2[right_idx]) {
        array[i] = cache1[left_idx++];
      }
      else {
        array[i] = cache2[right_idx++];
      }
    }
    if (left_idx < cache1.length) {
      System.arraycopy(cache1, left_idx, array, start + left_idx + right_idx, cache1.length - left_idx);
    }
    else if (right_idx < cache2.length) {
      System.arraycopy(cache2, right_idx, array, start + left_idx + right_idx, cache2.length - right_idx);
    }
  }

  public static void recursiveMergeSort(double[] array) {
    recursiveMergeSort(array, 0, array.length);
  }

  private static void recursiveMergeSort(double[] array, int start, int end) {
    if ((end - start) > 1) {
      int middle = (start + end) / 2;
      recursiveMergeSort(array, start, middle);
      recursiveMergeSort(array, middle, end);
      int left_len = middle - start;
      int right_len = end - middle;
      var left_cache = new double[left_len];
      var right_cache = new double[right_len];
      merge(array, start, left_cache, right_cache);
    }
  }

  public static void iterativeMergeSort(double[] array) {
    Objects.requireNonNull(array);
    if (array.length <= 1) {
      return;
    }
    int exp_times = (int) Math.floor(Math.log(array.length) / Math.log(2));
    int group_size = 2;
    int last_rest_len = 0;
    boolean not_exp_of_2 = (Math.pow(2, exp_times) != array.length);
    if (not_exp_of_2) {
      last_rest_len = array.length % 2 == 0 ? 2 : 1;
    }
    for (int i = 0; i < exp_times; i++) {
      int group_iter_times = array.length / group_size;
      double[] cache1 = new double[group_size / 2];
      double[] cache2 = new double[group_size / 2];
      for (int j = 0; j < group_iter_times; j++) {
        merge(array, j * group_size, cache1, cache2);
      }
      int current_rest_len = array.length - group_iter_times * group_size;
      if (current_rest_len > last_rest_len) {
        var rest_cache1 = new double[current_rest_len - last_rest_len];
        var rest_cache2 = new double[last_rest_len];
        merge(array, array.length - current_rest_len, rest_cache1, rest_cache2);
        last_rest_len = current_rest_len;
      }
      group_size *= 2;
    }
    if (not_exp_of_2) {
      var rest_cache1 = new double[group_size / 2];
      var rest_cache2 = new double[array.length - group_size / 2];
      merge(array, 0, rest_cache1, rest_cache2);
    }
  }

  private static void maxHeapify(double[] arr, int idx, int heap_size) {
    int l = 2 * (idx + 1);
    int l_idx = l - 1;
    int r = 2 * (idx + 1) + 1;
    int r_idx = r - 1;
    int max_idx = idx;
    if ((l_idx < heap_size) && (arr[l_idx] > arr[max_idx])) {
      max_idx = l_idx;
    }
    if ((r_idx < heap_size) && (arr[r_idx] > arr[max_idx])) {
      max_idx = r_idx;
    }
    if (max_idx != idx) {
      var t = arr[max_idx];
      arr[max_idx] = arr[idx];
      arr[idx] = t;
      maxHeapify(arr, max_idx, heap_size);
    }
  }

  private static void buildMaxHeap(double[] arr) {
    for (int i = arr.length / 2 - 1; i >= 0; i--) {
      maxHeapify(arr, i, arr.length);
    }
  }

  public static void heapSort(double[] a) {
    buildMaxHeap(a);
    int heap_size = a.length;
    for (int i = a.length - 1; i >= 1; i--) {
      var t = a[0];
      a[0] = a[i];
      a[i] = t;
      maxHeapify(a, 0, --heap_size);
    }
  }

  public static void quickSort(double[] a) {
    var funcPartition = new Object() {
      int apply(int start, int end) {
        var pivot = a[end - 1];
        int i = start - 1;
        for (int j = start; j < end - 1; j++) {
          if (a[j] <= pivot) {
            exchange(a, j, ++i);
          }
        }
        a[end - 1] = a[++i];
        a[i] = pivot;
        return i;
      }
    };

    var funcSort = new Object() {
      void apply(int start, int end) {
        if ((end - start) > 1) {
          int middle = funcPartition.apply(start, end);
          apply(start, middle);
          apply(middle, end);
        }
      }
    };
    funcSort.apply(0, a.length);
  }

  public static void quickSort3Way(double[] array) {
    var funcSort = new Object() {
      void apply(int lo, int hi) {
        if (hi - lo <= 0) {
          return;
        }
        int lt = lo, gt = hi, i = lo;
        double val = array[lo]; // Tukey's ninther to select better value
        while (i <= gt) {
          if (array[i] < val) {
            exchange(array, i++, lt++);
          }
          else if (array[i] > val) {
            exchange(array, i, gt--);
          }
          else {
            i++;
          }
        }
        apply(lo, lt - 1);
        apply(gt + 1, hi);
      }
    };
    funcSort.apply(0, array.length - 1);
  }

  public static void randomQuickSort(double[] a) {
    randomQuickSort(a, 0, a.length);
  }

  private static int randomPartition(double[] a, int start, int end) {
    int pivot_idx = ThreadLocalRandom.current().nextInt(start, end);
    var pivot = a[pivot_idx];
    exchange(a, pivot_idx, end - 1);
    int i = start - 1;
    for (int j = start; j < end - 1; j++) {
      if (a[j] <= pivot) {
        exchange(a, j, ++i);
      }
    }
    a[end - 1] = a[++i];
    a[i] = pivot;
    return i;
  }

  private static void randomQuickSort(double[] a, int start, int end) {
    if ((end - start) > 1) {
      int middle = randomPartition(a, start, end);
      randomQuickSort(a, start, middle);
      randomQuickSort(a, middle, end);
    }
  }

  /**
   * int data only, mean distribution
   *
   * @param a array
   */
  public static void countingSort(int[] a) {
    int[] b = new int[a.length];
    int min = a[0], max = a[0];
    for (int i = 1; i < a.length; i++) {
      if (a[i] > max) {
        max = a[i];
      }
      if (a[i] < min) {
        min = a[i];
      }
    }
    int range = max - min + 1;
    int[] c = new int[range];
    for (int value : a) {
      c[(value - min)]++;
    }
    for (int i = 1; i < range; i++) {
      c[i] = c[i] + c[i - 1];
    }
    for (int i = a.length - 1; i >= 0; i--) {
      b[c[(a[i] - min)] - 1] = a[i];
      c[(a[i] - min)]--;
    }
    System.arraycopy(b, 0, a, 0, b.length);
  }

  /**
   * sort from smaller bit to bigger bit
   *
   * @param a array
   */
  public static void radixSort(SimpleDate[] a) {
    Arrays.sort(a, Comparator.comparing(SimpleDate::getDay));
    Arrays.sort(a, Comparator.comparing(SimpleDate::getMonth));
    Arrays.sort(a, Comparator.comparing(SimpleDate::getYear));
  }

  /**
   * mean distribution
   * input [0, 1)
   *
   * @param a array
   */
  public static void bucketSort(double[] a) {
    List<List<Double>> b = new ArrayList<>();
    int n = a.length;
    for (int i = 0; i < n; i++) {
      b.add(new ArrayList<>());
    }
    for (var ai : a) {
      b.get((int) Math.floor(n * ai)).add(ai);
    }
    List<Double> res_list = new ArrayList<>();
    for (var list : b) {
      Collections.sort(list);
      res_list.addAll(list);
    }
    for (int i = 0; i < n; i++) {
      a[i] = res_list.get(i);
    }
  }

  /**
   * O(n)
   *
   * @param <E> element
   */
  static class KeyIndexedCountingSorter<E> {
    final int[] count;
    private boolean clear = false;
    public final int RADIX;

    public KeyIndexedCountingSorter() {
      count = new int[256 + 2];
      RADIX = 256;
    }

    public KeyIndexedCountingSorter(int radix) {
      count = new int[radix + 2];
      RADIX = radix;
    }

    public void sort(List<E> array, int lo, int hi, ToIntFunction<E> toKey) {
      if (clear) {
        Arrays.fill(count, 0);
      }
      List<E> aux = new ArrayList<>(array.size());
      for (int i = 0; i < array.size(); i++) {
        aux.add(null);
      }

      for (int i = lo; i <= hi; i++) {
        var a = toKey.applyAsInt(array.get(i));
        if (a >= RADIX) {
          throw new IllegalArgumentException("key >= radix");
        }
        count[a + 2]++;
      }

      for (int r = 0; r < RADIX + 1; r++) {
        count[r + 1] += count[r];
      }

      for (int i = lo; i <= hi; i++) {
        var a = toKey.applyAsInt(array.get(i));
        if (a >= RADIX) {
          throw new IllegalArgumentException("key >= radix");
        }
        aux.set(count[a + 1]++, array.get(i));
      }
      for (int i = lo; i <= hi; i++) {
        array.set(i, aux.get(i - lo));
      }

      clear = true;
    }

    /**
     * @param array data array
     * @param toKey key of data (-1 if at end)
     */
    public void sort(List<E> array, ToIntFunction<E> toKey) {
      sort(array, 0, array.size() - 1, toKey);
    }
  }

  /**
   * least significant digit radix sort
   *
   * @param strings strings
   */
  public static void LSDRadixSort(List<String> strings) {
    if (strings.size() == 0) {
      return;
    }

    var len = strings.get(0).length();
    if (strings.stream().parallel().anyMatch(s -> s.length() != len)) {
      throw new IllegalArgumentException("strings do not have the same length.");
    }
    var sorter = new KeyIndexedCountingSorter<String>();
    for (int i = len - 1; i >= 0; i--) {
      int finalI = i;
      sorter.sort(strings, s -> s.charAt(finalI));
    }
  }

  public static void MSDRadixSort(List<String> strings) {
    if (strings.size() == 0) {
      return;
    }

    var funcSmallSort = new Object() {
      void apply(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
          for (int j = i;
               j > lo && strings.get(j).substring(d).compareTo(strings.get(j - 1).substring(d)) < 0;
               j--) {
            exchange(strings, j, j - 1);
          }
        }
      }
    };

    var funcSort = new Object() {
      void apply(int lo, int hi, int d) {
        if (hi <= lo) {
          return;
        }
        if (hi - lo == 2) {
          funcSmallSort.apply(lo, hi, d);
        }
        else {
          KeyIndexedCountingSorter<String> sorter = new KeyIndexedCountingSorter<>();
          sorter.sort(strings, lo, hi, s -> {
            if (d < s.length()) {
              return s.charAt(d);
            }
            else {
              return -1;
            }
          });
          for (int r = 0; r < sorter.RADIX; r++) {
            apply(lo + sorter.count[r], lo + sorter.count[r + 1] - 1, d + 1);
          }
        }
      }
    };
    funcSort.apply(0, strings.size() - 1, 0);
  }

  public static void string3WayQuicksort(List<String> strings) {
    var funcSort = new Object() {
      void apply(int lo, int hi, int d) {
        if (hi <= lo) {
          return;
        }
        int lt = lo, gt = hi;
        int v = d < strings.get(lo).length() ? strings.get(lo).charAt(d) : -1;
        int i = lo + 1;
        while (i <= gt) {
          int t = d < strings.get(i).length() ? strings.get(i).charAt(d) : -1;
          if (t < v) {
            exchange(strings, lt++, i++);
          }
          else if (t > v) {
            exchange(strings, i, gt--);
          }
          else {
            i++;
          }
        }
        apply(lo, lt - 1, d);
        if (v >= 0) {
          apply(lt, gt, d + 1);
        }
        apply(gt + 1, hi, d);
      }
    };
    funcSort.apply(0, strings.size() - 1, 0);
  }

  static final class SimpleDate {
    public final int year;
    public final int month;
    public final int day;
    public final String s;

    public SimpleDate(int y, int m, int d) {
      this.year = y;
      this.month = m;
      this.day = d;
      s = String.format("[%d,%d,%d]", year, month, day);
    }

    public int getDay() {
      return day;
    }

    public int getMonth() {
      return month;
    }

    public int getYear() {
      return year;
    }

    public SimpleDate newCopy() {
      return new SimpleDate(this.year, this.month, this.day);
    }

    @Override
    public String toString() {
      return s;
    }
  }
}