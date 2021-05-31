package org.nathan.algorithmsJava.structures;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RedBlackTreeTest{

    RedBlackTree<Integer, Integer> RBtree;

    {
        RBtree = new RedBlackTree<>(Comparator.comparingInt(o -> o));
        RBtree.insert(11, 0);
        RBtree.insert(2, 0);
        RBtree.insert(14, 0);
        RBtree.insert(1, 0);
        RBtree.insert(7, 0);
        RBtree.insert(15, 0);
        RBtree.insert(5, 0);
        RBtree.insert(8, 0);
        RBtree.insert(4, 0);
    }

    @Test
    public void insertFixUpTest(){
        var root = RBtree.root;
        assertEquals(root.key, 7); // 7
        assertEquals(root.left.key, 2); // 2
        assertEquals(root.right.key, 11); // 11
        assertEquals(root.left.left.key, 1); // 1
        assertEquals(root.left.right.key, 5); // 5
        assertEquals(root.left.right.left.key, 4); // 4
        assertEquals(root.right.left.key, 8); // 8
        assertEquals(root.right.right.key, 14); // 14
        assertEquals(root.right.right.right.key, 15); // 15
    }

    @Test
    public void balanceTest(){
        RedBlackTree<Integer, Integer> t = new RedBlackTree<>(Comparator.comparingInt(o -> o));
        for(int i = 0; i < 127; i++){
            t.insert(i, i);
        }
        assertEquals(6, t.getHeight());
        for(int i = 0; i < 65; i++){
            t.delete(i);
        }
        assertEquals(5, t.getHeight());
    }

    @Test
    public void functionsTest(){
        var t = new RedBlackTree<Integer, String>(Comparator.comparingInt(o -> o));
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            t.insert(i, String.valueOf(i));
            l1.add(i);
        }
        var ri = t.reverseIterator();
        assertEquals(15, ri.next().first());

        assertEquals(5 + 1 - 2, t.keyRangeSearch(2, 5).size());
        assertEquals(t.getHeight(), 4);
        assertEquals(t.getCount(), 16);
        assertEquals(t.getMinKey(), 0);
        assertEquals(t.getMaxKey(), 15);
        assertEquals(t.getValueOfMaxKey(), "15");
        assertEquals(t.getValueOfMinKey(), "0");
        t.inOrderForEach((i, s) -> l2.add(i));
        assertEquals(l1, l2);
        assertEquals(t.search(5), "5");
        assertThrows(IllegalStateException.class, ()->{
            for(var i : t){
                t.insert(i.first(), "i");
            }
        });
    }

}