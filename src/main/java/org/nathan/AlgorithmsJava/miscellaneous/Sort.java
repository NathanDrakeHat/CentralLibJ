package org.nathan.AlgorithmsJava.miscellaneous;

import org.nathan.AlgorithmsJava.tools.SimpleDate;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public final class Sort {
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

    private static int partition(double[] a, int start, int end) {
        var pivot = a[end - 1];
        int i = start - 1;
        for (int j = start; j < end - 1; j++) {
            if (a[j] <= pivot) {
                var t = a[j];
                a[j] = a[++i];
                a[i] = t;
            }
        }
        a[end - 1] = a[++i];
        a[i] = pivot;
        return i;
    }

    public static void quickSort(double[] a) {
        quickSort(a, 0, a.length);
    }

    private static void quickSort(double[] a, int start, int end) {
        if ((end - start) > 1) {
            int middle = partition(a, start, end);
            quickSort(a, start, middle);
            quickSort(a, middle, end);
        }
    }

    public static void randomQuickSort(double[] a) {
        randomQuickSort(a, 0, a.length);
    }

    private static int randomPartition(double[] a, int start, int end) {
        int pivot_idx = ThreadLocalRandom.current().nextInt(start, end);
        var pivot = a[pivot_idx];

        var temp = a[end - 1];
        a[end - 1] = pivot;
        a[pivot_idx] = temp;

        int i = start - 1;
        for (int j = start; j < end - 1; j++) {
            if (a[j] <= pivot) {
                var t = a[j];
                a[j] = a[++i];
                a[i] = t;
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

    //int data only, mean distribution
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

    //sort from smaller bit to bigger bit
    public static void radixSort(SimpleDate[] a) {
        Arrays.sort(a, Comparator.comparing(SimpleDate::getDay));
        Arrays.sort(a, Comparator.comparing(SimpleDate::getMonth));
        Arrays.sort(a, Comparator.comparing(SimpleDate::getYear));
    }

    // mean distribution
    public static void bucketSort(double[] a) {
        List<List<Double>> b = new ArrayList<>();
        int n = a.length;
        for (int i = 0; i < n; i++) {
            b.add(new ArrayList<>());
        }
        for (var i : a) {
            b.get((int) Math.floor(n * i)).add(i);
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

}