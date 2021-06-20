package org.nathan.algsJ.structures;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.nathan.centralUtils.utils.ArrayUtils.shuffledSequence;


class ExtremumHeapTest {

    List<List<Integer>> minRandomAddTestCase = new ArrayList<>();

    {
        for (int i = 0; i < iter; i++) {
            minRandomAddTestCase.add(shuffledSequence(1, 63));
        }
    }

    @Test
    void minRandomAddTest() {
        for (int i = 0; i < iter; i++) {
            List<Integer> l = minRandomAddTestCase.get(i);
            ExtremumHeap<Integer, String> m = new ExtremumHeap<>(true, Integer::compare);
            for (Integer integer : l) {
                m.add(String.valueOf(integer), integer);
            }
            List<Integer> res = new ArrayList<>();
            while (m.heapSize() > 0) {
                res.add(Integer.valueOf(m.extractExtremum()));
            }
            for (int j = 0; j < res.size() - 1; j++) {
                if (res.get(j).compareTo(res.get(j + 1)) < 0) {
                    assertTrue(true);
                } else {
                    fail();
                }
            }
        }
    }

    List<List<Integer>> maxRandomAddTestCase = new ArrayList<>();

    {
        for (int i = 0; i < iter; i++) {
            maxRandomAddTestCase.add(shuffledSequence(1, 63));
        }
    }

    @Test
    void maxRandomAddTest() {
        for (int i = 0; i < iter; i++) {
            List<Integer> l = maxRandomAddTestCase.get(i);
            ExtremumHeap<Integer, String> m = new ExtremumHeap<>(false, Integer::compare);
            for (Integer integer : l) {
                m.add(String.valueOf(integer), integer);
            }
            List<Integer> res = new ArrayList<>();
            while (m.heapSize() > 0) {
                res.add(Integer.valueOf(m.extractExtremum()));
            }
            for (int j = 0; j < res.size() - 1; j++) {
                if (res.get(j).compareTo(res.get(j + 1)) > 0) {
                    assertTrue(true);
                } else {
                    fail();
                }
            }
        }
    }

    static int iter = 10;
    List<List<Integer>> minRandUpdateKeyCase = new ArrayList<>(iter);

    {
        for (int i = 0; i < iter; i++) {
            minRandUpdateKeyCase.add(shuffledSequence(1, 63));
        }
    }

    @Test
    void minRandomUpdateKeyTest() {
        for (int i = 0; i < iter; i++) {
            List<Integer> l = minRandUpdateKeyCase.get(i);
            var rand = new SplittableRandom();
            ExtremumHeap<Integer, String> heap = new ExtremumHeap<>(
                    true,
                    l.stream().map(String::valueOf).collect(Collectors.toList()),
                    s -> rand.nextInt(127 - 1) + 1,
                    Integer::compareTo);
            List<Integer> res = new ArrayList<>();
            for (var elem : l) {
                heap.updateKey(String.valueOf(elem), elem);
            }
            while (heap.heapSize() > 0) {
                res.add(Integer.valueOf(heap.extractExtremum()));
            }
            for (int j = 0; j < res.size() - 1; j++) {
                if (!(res.get(j).compareTo(res.get(j + 1)) < 0)) {
                    fail();
                }
            }
        }
    }

    List<List<Integer>> maxRandUpdateKeyCase = new ArrayList<>(iter);

    {
        for (int i = 0; i < iter; i++) {
            maxRandUpdateKeyCase.add(shuffledSequence(1, 63));
        }
    }

    @Test
    void maxRandomUpdateKeyTest() {
        for (int i = 0; i < iter; i++) {
            List<Integer> l = maxRandUpdateKeyCase.get(i);
            var rand = new SplittableRandom();
            ExtremumHeap<Integer, String> heap = new ExtremumHeap<>(
                    false,
                    l.stream().map(String::valueOf).collect(Collectors.toList()),
                    s -> rand.nextInt(127 - 1) + 1,
                    Integer::compareTo);
            List<Integer> res = new ArrayList<>();
            for (var elem : l) {
                heap.updateKey(String.valueOf(elem), elem);
            }
            while (heap.heapSize() > 0) {
                res.add(Integer.valueOf(heap.extractExtremum()));
            }
            for (int j = 0; j < res.size() - 1; j++) {
                if (!(res.get(j).compareTo(res.get(j + 1)) > 0)) {
                    fail();
                }
            }
        }
    }

    @Test
    void modificationTest() {
        var m = new ExtremumHeap<Integer, Integer>(true, Integer::compareTo);
        m.add(1, 1);
        m.add(2, 2);
        assertThrows(IllegalStateException.class, () -> {
            for (var i : m) {
                m.add(i.first() + 3, i.second() + 3);
            }
        });
    }
}