package structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciHeapTest extends FibonacciHeap<Double>{

    private static FibonacciHeap<Double> buildExample(){
        var H = new FibonacciHeap<Double>();
        H.insert(3);
        var m = H.rootList();
        m.chainAdd(17).chainAdd(24).chainAdd(23).chainAdd(7).chainAdd(21);
        m.setChildList( H.new Node(18, true));
        m.degree = 2;
        var m_child = m.getChildList();
        m_child.degree = 1;
        m_child.setChildList(H.new Node(39, true));
        m_child.chainAdd(52).chainAdd(38);
        m_child.left.setChildList( H.new Node(41));
        m_child.left.degree = 1;
        m.right.setChildList(H.new Node(30));
        m.right.degree = 1;
        var t = H.new Node(26, true);
        t.degree = 1;
        t.setChildList(H.new Node(35));
        m.right.right.setChildList(t);
        m.right.right.degree = 2;
        t.chainAdd(46);
        H.number = 15;
        return H;
    }
    private static String bcl(FibonacciHeap<Double>.Node t){
        var res = new StringBuilder();
        var p = t;
        do{
            res.append(p.getKey());
            res.append(" ");
            p = p.right;
        }while(p != t);
        res.append("| ");
        p = t;
        do{
            res.append(p.getKey());
            res.append(" ");
            p = p.left;
        }while(p != t);
        //res.append('\n');
        return res.toString();
    }

    @Test
    void test(){
        var H = buildExample();
        var o = H.extractMin();
        //see <<introduction to  algorithm>> to find this test sample.
        assertEquals(bcl(H.rootList()), "7 18 38 | 7 38 18 ");
        assertEquals(bcl(H.rootList().getRight().getChildList()),"39 21 | 39 21 ");
        assertEquals(bcl(H.rootList().getRight().getChildList().getLeft().getChildList()), "52 | 52 ");
        assertEquals(bcl(H.rootList().getChildList()), "23 17 24 | 23 24 17 ");
        assertEquals(bcl(H.rootList().getChildList().getLeft().getChildList()), "26 46 | 26 46 ");
        assertEquals(bcl(H.rootList().getChildList().getLeft().getLeft().getChildList()),"30 | 30 ");
        assertEquals(bcl(H.rootList().getChildList().getLeft().getChildList().getChildList()),"35 | 35 ");
        assertEquals(bcl(H.rootList().getRight().getRight().getChildList()),"41 | 41 ");
        
        H.decreaseKey(H.rootList().getChildList().getLeft().getChildList().getLeft(), 15);
        H.decreaseKey(H.rootList().getChildList().getLeft().getChildList().getChildList(), 5);
        
        assertEquals(bcl(H.rootList()),"5 26 24 7 18 38 15 | 5 15 38 18 7 24 26 ");;
        assertEquals(bcl(H.rootList().getRight().getRight().getRight().getChildList()),"23 17 | 23 17 ");
        assertEquals(bcl(H.rootList().getRight().getRight().getRight().getChildList().getRight().getChildList()), "30 | 30 ");
        assertEquals(bcl(H.rootList().getRight().getRight().getRight().getRight().getChildList()),"39 21 | 39 21 ");
    }
}