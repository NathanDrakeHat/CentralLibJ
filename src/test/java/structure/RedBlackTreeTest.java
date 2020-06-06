package structure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    @Test
    public void RedBlackTreeTestCase(){
        var RBtree = new RedBlackTree<Integer>();
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
}