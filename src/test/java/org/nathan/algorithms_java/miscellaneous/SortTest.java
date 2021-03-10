package org.nathan.algorithms_java.miscellaneous;

import org.junit.jupiter.api.Test;
import org.nathan.algorithms_java.tools.RangeIterator;
import org.nathan.algorithms_java.tools.SimpleDate;

import static org.nathan.algorithms_java.tools.Utils.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;


class SortTest {


    static SimpleDate[] buildDate() {
        var rand = new Random();
        int len = rand.nextInt(20) + 20;
        SimpleDate[] res = new SimpleDate[len];
        int[] years = randomIntArray(2000, 2022, len);
        int[] months = randomIntArray(1, 13, len);
        int[] days = randomIntArray(1, 29, len);
        for (int i = 0; i < len; i++) {
            res[i] = new SimpleDate(years[i], months[i], days[i]);
        }
        return res;
    }


    static boolean isSorted(int[] res) {
        boolean is_sorted = true;
        for (int i = 1; i < res.length; i++) {
            if (res[i - 1] > res[i]) {
                is_sorted = false;
                break;
            }
        }
        return is_sorted;
    }

    static boolean isSorted(double[] res) {
        boolean is_sorted = true;
        for (int i = 1; i < res.length; i++) {
            if (res[i - 1] > res[i]) {
                is_sorted = false;
                break;
            }
        }
        return is_sorted;
    }

    static boolean isSorted(SimpleDate[] res) {
        boolean is_sorted = true;
        for (int i = 1; i < res.length; i++) {
            if (res[i - 1].year > res[i].year) {
                is_sorted = false;
                break;
            }
            else if ((res[i - 1].year == res[i].year) && (res[i - 1].month > res[i].month)) {
                is_sorted = false;
                break;
            }
            else if ((res[i - 1].year == res[i].year) && (res[i - 1].month == res[i].month) && (res[i - 1].day > res[i].day)) {
                is_sorted = false;
                break;
            }
        }
        return is_sorted;
    }


    @Test
    void iterativeMergeSortTest() {
        var rand = new Random();
        int len = rand.nextInt(10) + 20;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            Sort.iterativeMergeSort(origin);
            assertTrue(isSorted(origin));
        }
    }

    @Test
    void recursiveMergeSortTest() {
        var rand = new Random();
        int len = rand.nextInt(10) + 20;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            Sort.recursiveMergeSort(origin);
            assertTrue(isSorted(origin));
        }

    }

    @Test
    void heapSortTest() {
        var rand = new Random();
        int len = rand.nextInt(10) + 20;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            Sort.heapSort(origin);
            assertTrue(isSorted(origin));
        }
    }

    @Test
    void quickSortTest() {
        var rand = new Random();
        int len = rand.nextInt(10) + 20;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            Sort.quickSort(origin);
            assertTrue(isSorted(origin));
        }
    }

    @Test
    void randQuickSortTest() {
        var rand = new Random();
        int len = rand.nextInt(10) + 20;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            Sort.randomQuickSort(origin);
            assertTrue(isSorted(origin));
        }
    }

    @Test
    void countingSortTest() {
        var rand = new Random();
        int len = rand.nextInt(10) + 20;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomIntArray(-bound, bound, len);
            Sort.countingSort(origin);
            assertTrue(isSorted(origin));
        }
    }

    @Test
    void radixSortTest() {
        for(var ignored : new RangeIterator(0, 10)){
            SimpleDate[] origin = buildDate();
            Sort.radixSort(origin);
            assertTrue(isSorted(origin));
        }
    }

    @Test
    void bucketSortTest() {
        var rand = new Random();
        int len = rand.nextInt(10) + 20;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(0, 1, len);
            Sort.bucketSort(origin);
            assertTrue(isSorted(origin));
        }
    }

}