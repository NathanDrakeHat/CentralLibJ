package Algorithms.structures;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciHeapTest {

    private static FibonacciHeap.Node<Integer,Integer> buildNode(Integer key){
        return new FibonacciHeap.Node<>(key,key);
    }
    private static FibonacciHeap.Node<Integer,Integer> buildNodeWithMark(Integer key){
        var res = new FibonacciHeap.Node<>(key, key);
        res.mark = true;
        return res;
    }

    static void addChild(FibonacciHeap.Node<Integer, Integer> n, int t)
    {
        var x = new FibonacciHeap.Node<>(t, t);
        x.mark = false;
        var listLeft = n.left;
        n.left = x;
        x.right = n;
        listLeft.right = x;
        x.left = listLeft;
        x.parent = n.parent;
    }

    static void addChildren(FibonacciHeap.Node<Integer, Integer> n, int... t)
    {
        for (var i : t)
            addChild(n, i);
    }

    private static FibonacciHeap<Integer,Integer> buildExample() {
        var H = new FibonacciHeap<Integer,Integer>(Comparator.comparingInt(a -> a));
        H.insert(3, 3);
        var m = H.rootList;
        addChildren(m,17,24,23,7,21);

        FibonacciHeap.Node<Integer,Integer> ptr;
        ptr = buildNodeWithMark(18);
        m.childList = ptr;
        ptr.parent = m;
        m.degree = 2;

        var m_child = m.childList;
        m_child.degree = 1;
        ptr = buildNodeWithMark(39);
        m_child.childList = ptr;
        ptr.parent = m_child;

        addChildren(m_child,52,38);
        ptr = buildNode(41);
        m_child.left.childList = ptr;
        ptr.parent = m_child.left;
        m_child.left.degree = 1;

        ptr = buildNode(30);
        m.right.childList = ptr;
        ptr.parent = m.right;
        m.right.degree = 1;

        FibonacciHeap.Node<Integer,Integer> t = buildNodeWithMark(26);
        t.degree = 1;
        ptr = buildNode(35);
        t.childList = ptr;
        ptr.parent = t;

        m.right.right.childList = t;
        t.parent = m.right.right;
        m.right.right.degree = 2;

        addChild(t,46);
        H.count = 15;
        return H;
    }

    private static List<Integer> bcl(FibonacciHeap.Node<Integer,Integer> t) {
        List<Integer> res = new ArrayList<>();
        var p = t;
        do {
            res.add(p.key);
            p = p.right;
        } while (p != t);
        p = t;
        do {
            res.add(p.key);
            p = p.left;
        } while (p != t);
        return res;
    }

    @Test
    void example1() {
        var H = buildExample();
        var o = H.extractMin();
        assertEquals(o, 3);
        //see <<introduction to  algorithm>> to find this test sample.
        assertEquals(List.of(7, 18, 38, 7, 38, 18), bcl(H.rootList));
        assertEquals(List.of(39, 21, 39, 21), bcl(H.rootList.right.childList));
        assertEquals(List.of(52, 52), bcl(H.rootList.right.childList.left.childList));
        assertEquals(List.of(23, 17, 24, 23, 24, 17), bcl(H.rootList.childList));
        assertEquals(List.of(26, 46, 26, 46), bcl(H.rootList.childList.left.childList));
        assertEquals(List.of(30, 30), bcl(H.rootList.childList.left.left.childList));
        assertEquals(List.of(35, 35), bcl(H.rootList.childList.left.childList.childList));
        assertEquals(List.of(41, 41), bcl(H.rootList.right.right.childList));

        H.decreaseKey(H.rootList.childList.left.childList.left, 15);
        H.decreaseKey(H.rootList.childList.left.childList.childList, 5);

        assertEquals(List.of(5, 26, 24, 7, 18, 38, 15, 5, 15, 38, 18, 7, 24, 26), bcl(H.rootList));
        assertEquals(List.of(23, 17, 23, 17), bcl(H.rootList.right.right.right.childList));
        assertEquals(List.of(30, 30), bcl(H.rootList.right.right.right.childList.right.childList));
        assertEquals(List.of(39, 21, 39, 21), bcl(H.rootList.right.right.right.right.childList));
    }
}