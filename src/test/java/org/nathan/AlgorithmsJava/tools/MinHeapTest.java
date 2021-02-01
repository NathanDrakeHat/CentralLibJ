package org.nathan.AlgorithmsJava.tools;


import static org.junit.jupiter.api.Assertions.*;
import static org.nathan.AlgorithmsJava.tools.TestUtils.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


class MinHeapTest {
    // TODO load all failed cases
    @Test
    void failedCasesTest() {

    }

    @Test
    void randomBuildTest() {
        for (int i = 0; i < 10; i++) {
            List<Integer> l = randomIntegerList(1, 256, 63);
            MinHeap<Integer, String> m = new MinHeap<>(
                    l.stream().map(String::valueOf).collect(Collectors.toList()),
                    Integer::valueOf,
                    Integer::compareTo);
            List<Integer> res = new ArrayList<>();
            while (m.heapSize() > 0) {
                res.add(Integer.valueOf(m.extractMin()));
            }
            for (int j = 0; j < res.size() - 1; j++) {
                if (res.get(j).compareTo(res.get(j + 1)) <= 0) {
                    assertTrue(true);
                }
                else {
                    // TODO serialize when fail
                    fail();
                }
            }
        }
    }

    @Test
    void randomAddTest() {
        for (int i = 0; i < 10; i++) {
            List<Integer> l = randomIntegerList(1, 256, 63);
            MinHeap<Integer, String> m = new MinHeap<>(Integer::compare);
            for (Integer integer : l) {
                m.Add(String.valueOf(integer), integer);
            }
            List<Integer> res = new ArrayList<>();
            while (m.heapSize() > 0) {
                res.add(Integer.valueOf(m.extractMin()));
            }
            for (int j = 0; j < res.size() - 1; j++) {
                if (res.get(j).compareTo(res.get(j + 1)) <= 0) {
                    assertTrue(true);
                }
                else {
                    // TODO serialize when fail
                    fail();
                }
            }
        }
    }

    // TODO updateKeyTest
    @Test
    void randomUpdateKeyTest() {

    }
}