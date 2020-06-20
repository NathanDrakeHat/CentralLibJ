package tools;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void decreaseKeyTest1() {
        String[] strings = new String[155];
        double[] doubles = new double[155];
        int idx = 0;
        for(double i = 0; i <= 308; i+=2) {
            strings[idx] = String.valueOf(i);
            doubles[idx] = i;
            idx++;
        }
        List<String> l = Arrays.asList(strings);
        MinHeap<String> h = new MinHeap<>(l, Double::valueOf);
        idx = 0;
        for(double i = 0; i <= 308; i+=4){
             h.decreaseKey(String.valueOf(i), i-3);
             doubles[idx] = i -3;
             idx += 2;
        }
        DoubleAndString[] answer = new DoubleAndString[155];
        idx = 0;
        for(int i = 0 ; i <= 308; i+=2){
            answer[idx] = new DoubleAndString(strings[idx],doubles[idx]);
            idx++;
        }

        Arrays.sort(answer, Comparator.comparingDouble(DoubleAndString::getDouble));
        idx=0;
        for(int i = 0 ; i <= 308; i+=2){
            var t = h.extractMin();
            assertEquals(answer[idx].getString(),t);
            idx++;
        }
    }

    @Test
    void decreaseKeyTest2() {
        String[] strings = new String[155];
        double[] doubles = new double[155];
        int idx = 0;
        for(double i = 0; i <= 308; i+=2) {
            strings[idx] = String.valueOf(i);
            doubles[idx] = i;
            idx++;
        }
        List<String> l = Arrays.asList(strings);
        MinHeap<String> h = new MinHeap<>(l, Double::valueOf);
        idx = 0;
        for(double i = 0; i <= 308; i+=4){
            h.decreaseKey(String.valueOf(i), i-3);
            doubles[idx] = i -3;
            idx += 2;
        }
        DoubleAndString[] answer = new DoubleAndString[155];
        idx = 154;
        for(int i = 308 ; i >= 0; i -= 2){
            answer[idx] = new DoubleAndString(strings[idx],doubles[idx]);
            idx--;
        }

        Arrays.sort(answer, Comparator.comparingDouble(DoubleAndString::getDouble));
        idx=0;
        for(int i = 0 ; i <= 308; i+=2){
            var t = h.extractMin();
            assertEquals(answer[idx].getString(),t);
            idx++;
        }
    }
    static class DoubleAndString{
        double d;
        String s;
        public DoubleAndString(String s, double d){
            this.s = s;
            this.d = d;
        }
        public double getDouble() { return d; }

        public String getString() { return s; }
    }
}