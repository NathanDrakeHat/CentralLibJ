package org.nathan.algorithmsJava.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStatisticTreeTest {

    @Test
    void case1() {
        OrderStatisticTree RBtree = new OrderStatisticTree();
        RBtree.insertKey(11);
        RBtree.insertKey(2);
        RBtree.insertKey(14);
        RBtree.insertKey(1);
        RBtree.insertKey(7);
        RBtree.insertKey(15);
        RBtree.insertKey(5);
        RBtree.insertKey(8);
        RBtree.insertKey(4);
        OrderStatisticTree.ColorSizeNode root = RBtree.getRoot();
        assertEquals(7, root.getKey()); // 7
        assertEquals(2, root.getLeft().getKey()); // 2
        assertEquals(11, root.getRight().getKey()); // 11
        assertEquals(1, root.getLeft().getLeft().getKey()); // 1
        assertEquals(5, root.getLeft().getRight().getKey()); // 5
        assertEquals(4, root.getLeft().getRight().getLeft().getKey()); // 4
        assertEquals(8, root.getRight().getLeft().getKey()); // 8
        assertEquals(14, root.getRight().getRight().getKey()); // 14
        assertEquals(15, root.getRight().getRight().getRight().getKey()); // 15
    }
}