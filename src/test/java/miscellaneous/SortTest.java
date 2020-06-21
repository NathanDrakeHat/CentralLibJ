package miscellaneous;

import java.util.Arrays;
import tools.SimpleDate;
import static org.junit.jupiter.api.Assertions.*;


class SortTest {
     static class Data {
        static int[] data = new int[] {10, -5, 10, 4, 2, 6, 10, 10, 4, -9, -6, -7, 10, -7, 5, -6, 8, 8, -5, -8};
        static int[] test1 = new int[] {-2, 4, 2, -4, -9, 6, -4, 7, -3, -8, 2, -8, 0, 10, -9, -4, -3, -6, -8, 2};
        static int[] test2 = new int[] {-37, -8, 40, 5, -25, 15, -13, 12, 8, 44, 18, -6, -33, -37,
                -39, -24, -14, 15, 40, 27, -15, 46, -16, -2, 4};
        static int[] test3 = new int[] {2, 12, -24, -38, -17, -25, 5, -22, -34, 0, -18, -16, -39, -39, -36};
        static int[] test4 = new int[] {-9, -19, -3, 16, 6, 18, -15, -20, -8, 13, -18, 13, 17, -6, 16, -2, -3};
        static int[] test5 = new int[] {-30, -57, -55, -26, 26, -34, -8, -22, 31, -19, -49, -32, 2, 22, -8,
                31, 31, -55, -16};

        static double[] bucket_test = new double[] {0.885, 0.6497, 0.6566, 0.1263, 0.2799, 0.8863, 0.013, 0.8973,
                0.4261, 0.454, 0.7068, 0.5867, 0.1529, 0.8356, 0.7301, 0.1111, 0.8368};
        static double[] bucket_test1 = new double[] {0.536, 0.3182, 0.7075, 0.8828, 0.3912, 0.7055, 0.1893, 0.9803,
                0.5235, 0.4947, 0.6374, 0.9782};
        static double[] bucket_test2 = new double[] {0.9224, 0.8382, 0.2349, 0.4597, 0.2921, 0.2443, 0.2077,
                0.8726, 0.7065, 0.2447, 0.7945, 0.2701, 0.4575, 0.1834, 0.3771};

         static int[] years = new int[] {2020, 2019, 2017, 2018, 2019, 2016, 2018, 2017, 2016, 2020,
                 2015, 2019, 2020, 2017, 2015, 2015, 2020, 2020, 2016, 2018};
         static int[] months = new int[] {6, 9, 1, 7, 10, 11, 7, 4, 2, 7, 8, 9, 6, 3, 1, 6, 3, 4, 2, 5};
         static int[] days = new int[] {11, 10, 11, 24, 2, 3, 8, 5, 16, 3, 9, 19, 23, 22, 1, 11, 17, 29, 15, 11};
         static SimpleDate[] buildDate(){
            SimpleDate[] res = new SimpleDate[20];
            for(int i=0; i < 20; i++){
                res[i] = new SimpleDate(years[i], months[i], days[i]);
            }
            return res;
        }
    }
     static boolean isSorted(int[] t){
        boolean is_sorted = true;
        for(int i = 1; i < t.length; i++){
            if (t[i - 1] > t[i]) {
                is_sorted = false;
                break;
            }
        }
        return is_sorted;
    }
     static boolean isSorted(double[] t){
        boolean is_sorted = true;
        for(int i = 1; i < t.length; i++){
            if (t[i - 1] > t[i]) {
                is_sorted = false;
                break;
            }
        }
        return is_sorted;
    }
     static boolean isSorted(SimpleDate[] d){
        boolean is_sorted = true;
        for(int i=1; i < d.length; i++){
            if(d[i - 1].year > d[i].year ){
                is_sorted = false;
                break;
            }
            else if((d[i - 1].year == d[i].year) && (d[i - 1].month > d[i].month)){
                is_sorted = false;
                break;
            }
            else if((d[i - 1].year == d[i].year) && (d[i - 1].month == d[i].month) && (d[i - 1].day > d[i].day)) {
                is_sorted = false;
                break;
            }
        }
        return is_sorted;
    }

    @org.junit.jupiter.api.Test
    void mergeSortTest1() {
        var copy = Data.data;
        var t = Arrays.copyOf(copy, copy.length);
        Sort.mergeSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void mergeSortTest2() {
        var copy = Data.test1;
        var t = Arrays.copyOf(copy, copy.length);
        Sort.mergeSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void mergeSortTest3() {
        var copy = Data.test2;
        var t = Arrays.copyOf(copy, copy.length);
        Sort.mergeSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void mergeSortTest4() {
        var copy = Data.test3;
        var t = Arrays.copyOf(copy, copy.length);
        Sort.mergeSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void mergeSortTest5() {
        var copy = Data.test4;
        var t = Arrays.copyOf(copy, copy.length);
        Sort.mergeSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void mergeSortTest6() {
        var copy = Data.test5;
        var t = Arrays.copyOf(copy, copy.length);
        Sort.mergeSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void heapSort1() {
        var c = Data.data;
        var t = Arrays.copyOf(c, c.length);
        Sort.heapSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void heapSort2() {
        var c = Data.test1;
        var t = Arrays.copyOf(c, c.length);
        Sort.heapSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void heapSort3() {
        var c = Data.test2;
        var t = Arrays.copyOf(c, c.length);
        Sort.heapSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void heapSort4() {
        var c = Data.test3;
        var t = Arrays.copyOf(c, c.length);
        Sort.heapSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void heapSort5() {
        var c = Data.test4;
        var t = Arrays.copyOf(c, c.length);
        Sort.heapSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void heapSort6() {
        var c = Data.test5;
        var t = Arrays.copyOf(c, c.length);
        Sort.heapSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void quickSort1() {
        var c = Data.data;
        var t = Arrays.copyOf(c, c.length);
        Sort.quickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void quickSort2() {
        var c = Data.test1;
        var t = Arrays.copyOf(c, c.length);
        Sort.quickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void quickSort3() {
        var c = Data.test2;
        var t = Arrays.copyOf(c, c.length);
        Sort.quickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void quickSort4() {
        var c = Data.test3;
        var t = Arrays.copyOf(c, c.length);
        Sort.quickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void quickSort5() {
        var c = Data.test4;
        var t = Arrays.copyOf(c, c.length);
        Sort.quickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void quickSort6() {
        var c = Data.test5;
        var t = Arrays.copyOf(c, c.length);
        Sort.quickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void randQuickSort1() {
        var c = Data.data;
        var t = Arrays.copyOf(c, c.length);
        Sort.randQuickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void randQuickSort2() {
        var c = Data.test1;
        var t = Arrays.copyOf(c, c.length);
        Sort.randQuickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void randQuickSort3() {
        var c = Data.test2;
        var t = Arrays.copyOf(c, c.length);
        Sort.randQuickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void randQuickSort4() {
        var c = Data.test3;
        var t = Arrays.copyOf(c, c.length);
        Sort.randQuickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void randQuickSort5() {
        var c = Data.test4;
        var t = Arrays.copyOf(c, c.length);
        Sort.randQuickSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void randQuickSort6() {
        var c = Data.test5;
        var t = Arrays.copyOf(c, c.length);
        Sort.randQuickSort(t);
        assertTrue(isSorted(t));
        assertFalse(isSorted(c));
    }

    @org.junit.jupiter.api.Test
    void countingSort1() {
        var c = Data.data;
        var t = Arrays.copyOf(c, c.length);
        Sort.countingSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void countingSort2() {
        var c = Data.test1;
        var t = Arrays.copyOf(c, c.length);
        Sort.countingSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void countingSort3() {
        var c = Data.test2;
        var t = Arrays.copyOf(c, c.length);
        Sort.countingSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void countingSort4() {
        var c = Data.test3;
        var t = Arrays.copyOf(c, c.length);
        Sort.countingSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void countingSort5() {
        var c = Data.test4;
        var t = Arrays.copyOf(c, c.length);
        Sort.countingSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void countingSort6() {
        var c = Data.test5;
        var t = Arrays.copyOf(c, c.length);
        Sort.countingSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void radixSort1() {
        SimpleDate[] t = Data.buildDate();
        Sort.radixSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void bucketSort1() {
        var t = Data.bucket_test;
        Sort.bucketSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void bucketSort2() {
        var t = Data.bucket_test1;
        Sort.bucketSort(t);
        assertTrue(isSorted(t));
    }

    @org.junit.jupiter.api.Test
    void bucketSort3() {
        var t = Data.bucket_test2;
        Sort.bucketSort(t);
        assertTrue(isSorted(t));
    }
}