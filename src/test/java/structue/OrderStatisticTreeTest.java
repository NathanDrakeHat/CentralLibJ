package structue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatisticTreeTest {

    @Test
    void testCase1() {
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
}