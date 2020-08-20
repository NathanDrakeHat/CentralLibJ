package structures;

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
        m.setChildList(buildNodeWithMark(18));
        m.degree = 2;
        var m_child = m.getChildList();
        m_child.degree = 1;
        m_child.setChildList(buildNodeWithMark(39));
        addChildren(m_child,52,38);
        m_child.left.setChildList(buildNode(41));
        m_child.left.degree = 1;
        m.right.setChildList(buildNode(30));
        m.right.degree = 1;
        FibonacciHeap.Node<Integer,Integer> t = buildNodeWithMark(26);
        t.degree = 1;
        t.setChildList(buildNode(35));
        m.right.right.setChildList(t);
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
        assertEquals(List.of(39, 21, 39, 21), bcl(H.rootList.right.getChildList()));
        assertEquals(List.of(52, 52), bcl(H.rootList.right.getChildList().left.getChildList()));
        assertEquals(List.of(23, 17, 24, 23, 24, 17), bcl(H.rootList.getChildList()));
        assertEquals(List.of(26, 46, 26, 46), bcl(H.rootList.getChildList().left.getChildList()));
        assertEquals(List.of(30, 30), bcl(H.rootList.getChildList().left.left.getChildList()));
        assertEquals(List.of(35, 35), bcl(H.rootList.getChildList().left.getChildList().getChildList()));
        assertEquals(List.of(41, 41), bcl(H.rootList.right.right.getChildList()));

        H.decreaseKey(H.rootList.getChildList().left.getChildList().left, 15);
        H.decreaseKey(H.rootList.getChildList().left.getChildList().getChildList(), 5);

        assertEquals(List.of(5, 26, 24, 7, 18, 38, 15, 5, 15, 38, 18, 7, 24, 26), bcl(H.rootList));
        assertEquals(List.of(23, 17, 23, 17), bcl(H.rootList.right.right.right.getChildList()));
        assertEquals(List.of(30, 30), bcl(H.rootList.right.right.right.getChildList().right.getChildList()));
        assertEquals(List.of(39, 21, 39, 21), bcl(H.rootList.right.right.right.right.getChildList()));
    }
}