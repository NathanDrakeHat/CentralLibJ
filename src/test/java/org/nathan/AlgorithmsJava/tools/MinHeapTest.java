package org.nathan.AlgorithmsJava.tools;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinHeapTest {

    @Test
    void caseTest1() {
        MinHeap<String> m_h = new MinHeap<>();
        for (int i = 0; i < 1024; i++) {
            m_h.add(i, String.valueOf(i));
        }
        m_h.update("1023", 0);
        assertEquals("0", m_h.extractMin());
        assertEquals("1023", m_h.extractMin());

        m_h.update("456", 9);
        m_h.update("654", 7);
        m_h.update("999", 8);

        m_h.update("512", -1);
        assertEquals("512", m_h.extractMin());
        assertEquals("1", m_h.extractMin());

        m_h.update("254", 1);
        m_h.update("255", 0);
        assertEquals("255", m_h.extractMin());
        assertEquals("254", m_h.extractMin());
        assertEquals("2", m_h.extractMin());
        assertEquals("3", m_h.extractMin());

        m_h.update("123", 6);
        m_h.update("678", 4);
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
        m_h.update("1023", 0);
        assertEquals("0", m_h.extractMin());
        assertEquals("1023", m_h.extractMin());

        m_h.update("456", 9);
        m_h.update("654", 7);
        m_h.update("999", 8);

        m_h.update("512", -1);
        assertEquals("512", m_h.extractMin());
        assertEquals("1", m_h.extractMin());

        m_h.update("254", 1);
        m_h.update("255", 0);
        assertEquals("255", m_h.extractMin());
        assertEquals("254", m_h.extractMin());
        assertEquals("2", m_h.extractMin());
        assertEquals("3", m_h.extractMin());

        m_h.update("123", 6);
        m_h.update("678", 4);
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
    public void TestCase3() {
        var mH = new MinHeap<String>();
        for (int i = 0; i < 512; i++) {
            mH.add(i, String.valueOf(i));
        }

        for (int i = 10; i >= 0; i--) {
            mH.extractMin();
        }

        for (int i = 10; i >= 0; i--) {
            mH.add(i, String.valueOf(i));
        }

        for (int i = 0; i <= 10; i++) {
            assertEquals(String.valueOf(i), mH.extractMin());
        }
    }

}