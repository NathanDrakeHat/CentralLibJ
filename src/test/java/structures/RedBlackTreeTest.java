package structures;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest{

    @Test
    public void insertFixUpTest(){
        var RBtree = new RedBlackTree<Integer, Integer>();
        RBtree.insert(11, 0);
        RBtree.insert(2, 0);
        RBtree.insert(14, 0);
        RBtree.insert(1, 0);
        RBtree.insert(7, 0);
        RBtree.insert(15, 0);
        RBtree.insert(5, 0);
        RBtree.insert(8, 0);
        RBtree.insert(4, 0);
        var root = RBtree.getRoot();
        assertEquals(root.getKey(), 7); // 7
        assertEquals(root.getLeft().getKey(), 2); // 2
        assertEquals(root.getRight().getKey(), 11); // 11
        assertEquals(root.getLeft().getLeft().getKey(), 1); // 1
        assertEquals(root.getLeft().getRight().getKey(), 5); // 5
        assertEquals(root.getLeft().getRight().getLeft().getKey(), 4); // 4
        assertEquals(root.getRight().getLeft().getKey(), 8); // 8
        assertEquals(root.getRight().getRight().getKey(), 14); // 14
        assertEquals(root.getRight().getRight().getRight().getKey(), 15); // 15
    }

    @Test
    public void balanceTest(){
        RedBlackTree<Integer,Integer> t = new RedBlackTree<>();
        for(int i = 0; i < 2047; i++){
            t.insert(i, i);
        }
        assertEquals(t.getHeight(), 10);
        for(int i = 0; i < 1025; i++){
            t.delete(i);
        }
        System.out.println(t.getHeight());
        assertEquals(t.getHeight(), 9);
    }

    @Test
    public void FunctionsTest(){
        var t = new RedBlackTree<Integer, String>();
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            t.insert(i, String.valueOf(i));
            l1.add(i);
        }
        assertEquals(t.getHeight(),4);
        assertEquals(t.getCount(), 16);
        assertEquals(t.getMinKey(), 0);
        assertEquals(t.getMaxKey(),15);
        assertEquals(t.getValueOfMaxKey(), "15");
        assertEquals(t.getValueOfMinKey(), "0");
        t.inOrderForEach((i, s)->l2.add(i));
        assertEquals(l1, l2);
        assertEquals(t.search(5), "5");
    }
}