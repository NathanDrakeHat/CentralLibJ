package structures;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FibonacciHeapTest{

    private static FibonacciHeap<Double> buildExample(){
        var H = new FibonacciHeap<Double>();
        var methods = H.getClass().getDeclaredMethods();
        H.insert(3, 3.0);
        var m = H.rootList();
        m.chainAdd(17).chainAdd(24).chainAdd(23).chainAdd(7).chainAdd(21);
        m.setChildList( H.new Node(18, true));
        m.setDegree(2);
        var m_child = m.getChildList();
        m_child.setDegree(1);
        m_child.setChildList(H.new Node(39, true));
        m_child.chainAdd(52).chainAdd(38);
        m_child.getLeft().setChildList( H.new Node(41));
        m_child.getLeft().setDegree(1);
        m.getRight().setChildList(H.new Node(30));
        m.getRight().setDegree(1);
        var t = H.new Node(26, true);
        t.setDegree(1);
        t.setChildList(H.new Node(35));
        m.getRight().getRight().setChildList(t);
        m.getRight().getRight().setDegree(2);
        t.chainAdd(46);
        H.setNumber(15);
        return H;
    }
    private static List<Double> bcl(FibonacciHeap<Double>.Node t){
        List<Double> res = new ArrayList<>();
        var p = t;
        do{
            res.add(p.getKey());
            p = p.getRight();
        }while(p != t);
        p = t;
        do{
            res.add(p.getKey());
            p = p.getLeft();
        }while(p != t);
        //res.append('\n');
        return res;
    }

    @Test
    void example1(){
        var H = buildExample();
        var o = H.extractMin();
        assertEquals(o,3.0);
        //see <<introduction to  algorithm>> to find this test sample.
        assertEquals(bcl(H.rootList()), List.of(7.0, 18.0, 38.0, 7.0, 38.0, 18.0));
        assertEquals(bcl(H.rootList().getRight().getChildList()),List.of(39.0, 21.0, 39.0, 21.0));
        assertEquals(bcl(H.rootList().getRight().getChildList().getLeft().getChildList()), List.of(52.0, 52.0));
        assertEquals(bcl(H.rootList().getChildList()), List.of(23.0, 17.0, 24.0,  23.0, 24.0, 17.0) );
        assertEquals(bcl(H.rootList().getChildList().getLeft().getChildList()), List.of(26.0, 46.0,  26.0, 46.0) );
        assertEquals(bcl(H.rootList().getChildList().getLeft().getLeft().getChildList()),List.of(30.0, 30.0));
        assertEquals(bcl(H.rootList().getChildList().getLeft().getChildList().getChildList()),List.of(35.0, 35.0));
        assertEquals(bcl(H.rootList().getRight().getRight().getChildList()),List.of(41.0, 41.0));
        
        H.decreaseKey(H.rootList().getChildList().getLeft().getChildList().getLeft(), 15);
        H.decreaseKey(H.rootList().getChildList().getLeft().getChildList().getChildList(), 5);
        
        assertEquals(bcl(H.rootList()),List.of(5.0, 26.0, 24.0, 7.0, 18.0, 38.0, 15.0, 5.0, 15.0, 38.0, 18.0, 7.0, 24.0, 26.0));;
        assertEquals(bcl(H.rootList().getRight().getRight().getRight().getChildList()),List.of(23.0, 17.0, 23.0, 17.0));
        assertEquals(bcl(H.rootList().getRight().getRight().getRight().getChildList().getRight().getChildList()), List.of(30.0, 30.0));
        assertEquals(bcl(H.rootList().getRight().getRight().getRight().getRight().getChildList()),List.of(39.0, 21.0, 39.0, 21.0));
    }
}