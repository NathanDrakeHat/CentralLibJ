package org.nathan.AlgorithmsJava.miscellaneous;

import org.junit.jupiter.api.Test;
import org.nathan.AlgorithmsJava.tools.SimpleDate;

import java.io.*;

import static org.nathan.AlgorithmsJava.tools.TestUtils.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;


class SortTest {


    static SimpleDate[] buildDate() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        SimpleDate[] res = new SimpleDate[len];
        int[] years = randomIntArray(2000, 2022, len);
        int[] months = randomIntArray(1, 13, len);
        int[] days = randomIntArray(1, 29, len);
        for (int i = 0; i < len; i++) {
            res[i] = new SimpleDate(years[i], months[i], days[i]);
        }
        return res;
    }


    static boolean isSortedElseSave(int[] res, int[] origin) {
        boolean is_sorted = true;
        for (int i = 1; i < res.length; i++) {
            if (res[i - 1] > res[i]) {
                is_sorted = false;
                try {
                    serializeObjectTimeSuffix(origin, "Test:", TYPE_SUFFIX, res.getClass());
                } catch (IOException ignore) {
                    System.out.println(Arrays.toString(origin));
                }
                break;
            }
        }
        return is_sorted;
    }

    static boolean isSortedElseSave(double[] res, double[] origin) {
        boolean is_sorted = true;
        for (int i = 1; i < res.length; i++) {
            if (res[i - 1] > res[i]) {
                is_sorted = false;
                try {
                    serializeObjectTimeSuffix(origin, "Test:", TYPE_SUFFIX, res.getClass());
                } catch (IOException ignore) {
                    System.out.println(Arrays.toString(origin));
                }
                break;
            }
        }
        return is_sorted;
    }

    static boolean isSortedElseSave(SimpleDate[] res, SimpleDate[] origin) {
        boolean is_sorted = true;
        for (int i = 1; i < res.length; i++) {
            if (res[i - 1].year > res[i].year) {
                is_sorted = false;
                try {
                    serializeObjectTimeSuffix(origin, "Test:", TYPE_SUFFIX, res.getClass());
                } catch (IOException ignore) {
                    System.out.println(Arrays.toString(origin));
                }
                break;
            }
            else if ((res[i - 1].year == res[i].year) && (res[i - 1].month > res[i].month)) {
                is_sorted = false;
                try {
                    serializeObjectTimeSuffix(origin, "Test:", TYPE_SUFFIX, res.getClass());
                } catch (IOException ignore) {
                    System.out.println(Arrays.toString(origin));
                }
                break;
            }
            else if ((res[i - 1].year == res[i].year) && (res[i - 1].month == res[i].month) && (res[i - 1].day > res[i].day)) {
                is_sorted = false;
                try {
                    serializeObjectTimeSuffix(origin, "Test:", TYPE_SUFFIX, res.getClass());
                } catch (IOException ignore) {
                    System.out.println(Arrays.toString(origin));
                }
                break;
            }
        }
        return is_sorted;
    }

    // TODO failedCasesTest
    @Test
    void failedCasesTest() {

    }

    @Test
    void iterativeMergeSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            var t = Arrays.copyOf(origin, origin.length);
            Sort.iterativeMergeSort(t);
            assertTrue(isSortedElseSave(t, origin));
        }
    }

    @Test
    void recursiveMergeSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            var t = Arrays.copyOf(origin, origin.length);
            Sort.recursiveMergeSort(t);
            assertTrue(isSortedElseSave(t, origin));
        }

    }

    @Test
    void heapSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            var t = Arrays.copyOf(origin, origin.length);
            Sort.heapSort(t);
            assertTrue(isSortedElseSave(t, origin));
        }
    }

    @Test
    void quickSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            var t = Arrays.copyOf(origin, origin.length);
            Sort.quickSort(t);
            assertTrue(isSortedElseSave(t, origin));
        }
    }

    @Test
    void randQuickSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(-bound, bound, len);
            var t = Arrays.copyOf(origin, origin.length);
            Sort.randomQuickSort(t);
            assertTrue(isSortedElseSave(t, origin));
        }
    }

    @Test
    void countingSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5) + 10;
        for (int i = 0; i < 10; i++) {
            var origin = randomIntArray(-bound, bound, len);
            var t = Arrays.copyOf(origin, origin.length);
            Sort.countingSort(t);
            assertTrue(isSortedElseSave(t, origin));
        }
    }

    @Test
    void radixSortTest() {
        SimpleDate[] origin = buildDate();
        SimpleDate[] t = new SimpleDate[origin.length];
        for (int i = 0; i < origin.length; i++) {
            t[i] = origin[i].newCopy();
        }
        Sort.radixSort(t);
        assertTrue(isSortedElseSave(t, origin));
    }

    @Test
    void bucketSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        for (int i = 0; i < 10; i++) {
            var origin = randomDoubleArray(0, 1, len);
            var t = Arrays.copyOf(origin, origin.length);
            Sort.bucketSort(t);
            assertTrue(isSortedElseSave(t, origin));
        }
    }

}