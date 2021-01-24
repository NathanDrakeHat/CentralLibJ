package org.nathan.AlgorithmsJava.tools;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinHeapTest {

    @Test
    void caseTest1() {
        List<String> ll = new ArrayList<>();
        for (int i = 0; i < 1024; i++) {
            ll.add(String.valueOf(i));
        }
        MinHeap<String> m_h = new MinHeap<>(ll, Integer::valueOf);

        m_h.updateKey("1023", 0);
        assertEquals("0", m_h.extractMin());
        assertEquals("1023", m_h.extractMin());

        m_h.updateKey("456", 9);
        m_h.updateKey("654", 7);
        m_h.updateKey("999", 8);

        m_h.updateKey("512", -1);
        assertEquals("512", m_h.extractMin());
        assertEquals("1", m_h.extractMin());

        m_h.updateKey("254", 1);
        m_h.updateKey("255", 0);
        assertEquals("255", m_h.extractMin());
        assertEquals("254", m_h.extractMin());
        assertEquals("2", m_h.extractMin());
        assertEquals("3", m_h.extractMin());

        m_h.updateKey("123", 6);
        m_h.updateKey("678", 4);
        assertEquals("4", m_h.extractMin());
        assertEquals("678", m_h.extractMin());
        assertEquals("5", m_h.extractMin());
        assertEquals("6", m_h.extractMin());
        assertEquals("123", m_h.extractMin());

        assertEquals("7", m_h.extractMin());
        assertEquals("654", m_h.extractMin());
        assertEquals("8", m_h.extractMin());
        assertEquals("999", m_h.extractMin());
        assertEquals("9", m_h.extractMin());
        assertEquals("456", m_h.extractMin());
    }

    @Test
    void caseTest2() {
        List<String> l = new ArrayList<>();

        for (int i = 0; i < 1024; i++) {
            l.add(String.valueOf(i));
        }
        MinHeap<String> m_h = new MinHeap<>(l, Double::valueOf);
        m_h.updateKey("1023", 0);
        assertEquals("0", m_h.extractMin());
        assertEquals("1023", m_h.extractMin());

        m_h.updateKey("456", 9);
        m_h.updateKey("654", 7);
        m_h.updateKey("999", 8);

        m_h.updateKey("512", -1);
        assertEquals("512", m_h.extractMin());
        assertEquals("1", m_h.extractMin());

        m_h.updateKey("254", 1);
        m_h.updateKey("255", 0);
        assertEquals("255", m_h.extractMin());
        assertEquals("254", m_h.extractMin());
        assertEquals("2", m_h.extractMin());
        assertEquals("3", m_h.extractMin());

        m_h.updateKey("123", 6);
        m_h.updateKey("678", 4);
        assertEquals("4", m_h.extractMin());
        assertEquals("678", m_h.extractMin());
        assertEquals("5", m_h.extractMin());
        assertEquals("6", m_h.extractMin());
        assertEquals("123", m_h.extractMin());

        assertEquals("7", m_h.extractMin());
        assertEquals("654", m_h.extractMin());
        assertEquals("8", m_h.extractMin());
        assertEquals("999", m_h.extractMin());
        assertEquals("9", m_h.extractMin());
        assertEquals("456", m_h.extractMin());
    }
}