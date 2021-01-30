package org.nathan.AlgorithmsJava.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class MinHeapTest {

    public static List<Integer> shuffleOfRange(int low, int high){
        List<Integer> l = new ArrayList<>();
        for (int i = low; i < high; i++){
            l.add(i);
        }
        Collections.shuffle(l);
        return l;
    }

    @Test
    void randomBuildTest() {
        for (int i = 0; i < 100; i++){
            List<Integer> l = shuffleOfRange(1,64);
            MinHeap<String> m = new MinHeap<>(l.stream().map(String::valueOf).collect(Collectors.toList()), Integer::valueOf);
            List<Integer> res = new ArrayList<>();
            res.add(Integer.valueOf(m.extractMin()));
            for(int j = 0; j < res.size() - 1; j++){
                if(res.get(j).compareTo(res.get(j+1)) < 0){
                    assertTrue(true);
                }
                else{
                    System.out.println(res);
                    fail();
                }
            }
        }
    }

    @Test
    void randomAddTest(){
        for (int i = 0; i < 100; i++){
            List<Integer> l = shuffleOfRange(1,64);
            MinHeap<String> m = new MinHeap<>();
            for (Integer integer : l) {
                m.Add(String.valueOf(integer), integer);
            }
            List<Integer> res = new ArrayList<>();
            res.add(Integer.valueOf(m.extractMin()));
            for(int j = 0; j < res.size() - 1; j++){
                if(res.get(j).compareTo(res.get(j+1)) < 0){
                    assertTrue(true);
                }
                else{
                    System.out.println(res);
                    fail();
                }
            }
        }
    }
}