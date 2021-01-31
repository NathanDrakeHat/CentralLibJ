package org.nathan.AlgorithmsJava.miscellaneous;

import org.junit.jupiter.api.Test;
import org.nathan.AlgorithmsJava.tools.SimpleDate;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SortTest {

    public static int[] randomIntArray(int low, int high, int len){
        var rand = new Random();
        int[] res = new int[len];
        for(int i = 0; i < len; i++){
            res[i] = rand.nextInt(high - low) + low;
        }
        return res;
    }

    public static double[] randomDoubleArray(double low, double high, int len){
        var rand = new Random();
        double[] res = new double[len];
        for(int i = 0; i < len; i++){
            res[i] = rand.nextDouble()*(high - low) + low;
        }
        return res;
    }

    static void serializeArray(int[] t) throws IOException {
        StringBuilder file_name = new StringBuilder("Integer");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        file_name.append(dtf.format(now));
        file_name.append(".txt");
        FileOutputStream file_out=new FileOutputStream(file_name.toString());
        ObjectOutputStream out=new ObjectOutputStream(file_out);
        out.writeObject(t);
        out.flush();
        out.close();
    }

    static void serializeArray(double[] t) throws IOException {
        StringBuilder file_name = new StringBuilder("Double");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        file_name.append(dtf.format(now));
        file_name.append(".txt");
        FileOutputStream file_out=new FileOutputStream(file_name.toString());
        ObjectOutputStream out=new ObjectOutputStream(file_out);
        out.writeObject(t);
        out.flush();
        out.close();
    }

    static void serializeArray(SimpleDate[] t) throws IOException {
        StringBuilder file_name = new StringBuilder("SimpleDate");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        file_name.append(dtf.format(now));
        file_name.append(".txt");
        FileOutputStream file_out=new FileOutputStream(file_name.toString());
        ObjectOutputStream out=new ObjectOutputStream(file_out);
        out.writeObject(t);
        out.flush();
        out.close();
    }

    @SuppressWarnings("unused")
    public static int[] loadIntegerArray(String file_name){
        if(!file_name.startsWith("Integer")){
            throw new IllegalArgumentException();
        }
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] res = null;
        try {
            if(in == null){
                throw new RuntimeException();
            }
            res = (int[])in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException ignore) {
        }
        return res;
    }

    @SuppressWarnings("unused")
    public static double[] loadDoubleArray(String file_name){
        if(!file_name.startsWith("Double")){
            throw new IllegalArgumentException();
        }
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] res = new double[0];
        try {
            if(in == null){
                throw new RuntimeException();
            }
            res = (double[])in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException ignore) {
        }
        return res;
    }

    @SuppressWarnings("unused")
    public static SimpleDate[] loadSimpleDateArray(String file_name){
        if(!file_name.startsWith("SimpleDate")){
            throw new IllegalArgumentException();
        }
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDate[] res = null;
        try {
            if(in == null){
                throw new RuntimeException();
            }
            res = (SimpleDate[])in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException ignore) {
        }
        return res;
    }

    static SimpleDate[] buildDate() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        SimpleDate[] res = new SimpleDate[len];
        int[] years = randomIntArray(2000,2022,len);
        int[] months = randomIntArray(1,13,len);
        int[] days = randomIntArray(1,29, len);
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
                    serializeArray(origin);
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
                    serializeArray(origin);
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
                    serializeArray(origin);
                } catch (IOException ignore) {
                    System.out.println(Arrays.toString(origin));
                }
                break;
            }
            else if ((res[i - 1].year == res[i].year) && (res[i - 1].month > res[i].month)) {
                is_sorted = false;
                try {
                    serializeArray(origin);
                } catch (IOException ignore) {
                    System.out.println(Arrays.toString(origin));
                }
                break;
            }
            else if ((res[i - 1].year == res[i].year) && (res[i - 1].month == res[i].month) && (res[i - 1].day > res[i].day)) {
                is_sorted = false;
                try {
                    serializeArray(origin);
                } catch (IOException ignore) {
                    System.out.println(Arrays.toString(origin));
                }
                break;
            }
        }
        return is_sorted;
    }

    @Test
    void specificCaseTest(){

    }

    @Test
    void iterativeMergeSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5)+10;
        for(int i = 0; i < 50; i++){
            var origin = randomDoubleArray(-bound,bound,len);
            var t = Arrays.copyOf(origin,origin.length);
            Sort.iterativeMergeSort(t);
            assertTrue(isSortedElseSave(t,origin));
        }
    }

    @Test
    void recursiveMergeSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5)+10;
        for(int i = 0; i < 50; i++){
            var origin = randomDoubleArray(-bound,bound,len);
            var t = Arrays.copyOf(origin,origin.length);
            Sort.recursiveMergeSort(t);
            assertTrue(isSortedElseSave(t,origin));
        }

    }

    @Test
    void heapSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5)+10;
        for(int i = 0; i < 50; i++){
            var origin = randomDoubleArray(-bound,bound,len);
            var t = Arrays.copyOf(origin,origin.length);
            Sort.heapSort(t);
            assertTrue(isSortedElseSave(t,origin));
        }
    }

    @Test
    void quickSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5)+10;
        for(int i = 0; i < 50; i++){
            var origin = randomDoubleArray(-bound,bound,len);
            var t = Arrays.copyOf(origin,origin.length);
            Sort.quickSort(t);
            assertTrue(isSortedElseSave(t,origin));
        }
    }

    @Test
    void randQuickSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5)+10;
        for(int i = 0; i < 50; i++){
            var origin = randomDoubleArray(-bound,bound,len);
            var t = Arrays.copyOf(origin,origin.length);
            Sort.randomQuickSort(t);
            assertTrue(isSortedElseSave(t,origin));
        }
    }

    @Test
    void countingSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        int bound = rand.nextInt(5)+10;
        for(int i = 0; i < 50; i++){
            var origin = randomIntArray(-bound,bound,len);
            var t = Arrays.copyOf(origin,origin.length);
            Sort.countingSort(t);
            assertTrue(isSortedElseSave(t,origin));
        }
    }

    @Test
    void radixSortTest() {
        // TODO copy
        SimpleDate[] t = buildDate();
        Sort.radixSort(t);
        assertTrue(isSortedElseSave(t,t));
    }

    @Test
    void bucketSortTest() {
        var rand = new Random();
        int len = rand.nextInt(100) + 50;
        for(int i = 0; i < 50; i++){
            var origin = randomDoubleArray(0,1,len);
            var t = Arrays.copyOf(origin,origin.length);
            Sort.bucketSort(t);
            assertTrue(isSortedElseSave(t,origin));
        }
    }

}