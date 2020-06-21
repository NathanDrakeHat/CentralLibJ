package tools;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void caseTest1() {
        MinHeap<String> m_h = new MinHeap<>();
        for(int i = 0; i < 1024; i++){
            m_h.add(i,String.valueOf(i));
        }
        m_h.decreaseKey("1023",0);
        assertEquals("0",m_h.extractMin());
        assertEquals("1023",m_h.extractMin());

        m_h.decreaseKey("456",9);
        m_h.decreaseKey("654",7);
        m_h.decreaseKey("999",8);

        m_h.decreaseKey("512", -1);
        assertEquals("512",m_h.extractMin());
        assertEquals("1",m_h.extractMin());

        m_h.decreaseKey("254",1);
        m_h.decreaseKey("255",0);
        assertEquals("255",m_h.extractMin());
        assertEquals("254",m_h.extractMin());
        assertEquals("2",m_h.extractMin());
        assertEquals("3",m_h.extractMin());

        m_h.decreaseKey("123",6);
        m_h.decreaseKey("678",4);
        assertEquals("4",m_h.extractMin());
        assertEquals("678",m_h.extractMin());
        assertEquals("5",m_h.extractMin());
        assertEquals("6",m_h.extractMin());
        assertEquals("123",m_h.extractMin());

        assertEquals("7",m_h.extractMin());
        assertEquals("654",m_h.extractMin());
        assertEquals("8",m_h.extractMin());
        assertEquals("999",m_h.extractMin());
        assertEquals("9",m_h.extractMin());
        assertEquals("456",m_h.extractMin());
    }

    @Test
    void caseTest2(){
        List<String> l = new ArrayList<>();

        for(int i = 0; i < 1024; i++){
            l.add(String.valueOf(i));
        }
        MinHeap<String> m_h = new MinHeap<>(l,Double::valueOf);
        m_h.decreaseKey("1023",0);
        assertEquals("0",m_h.extractMin());
        assertEquals("1023",m_h.extractMin());

        m_h.decreaseKey("456",9);
        m_h.decreaseKey("654",7);
        m_h.decreaseKey("999",8);

        m_h.decreaseKey("512", -1);
        assertEquals("512",m_h.extractMin());
        assertEquals("1",m_h.extractMin());

        m_h.decreaseKey("254",1);
        m_h.decreaseKey("255",0);
        assertEquals("255",m_h.extractMin());
        assertEquals("254",m_h.extractMin());
        assertEquals("2",m_h.extractMin());
        assertEquals("3",m_h.extractMin());

        m_h.decreaseKey("123",6);
        m_h.decreaseKey("678",4);
        assertEquals("4",m_h.extractMin());
        assertEquals("678",m_h.extractMin());
        assertEquals("5",m_h.extractMin());
        assertEquals("6",m_h.extractMin());
        assertEquals("123",m_h.extractMin());

        assertEquals("7",m_h.extractMin());
        assertEquals("654",m_h.extractMin());
        assertEquals("8",m_h.extractMin());
        assertEquals("999",m_h.extractMin());
        assertEquals("9",m_h.extractMin());
        assertEquals("456",m_h.extractMin());
    }

}