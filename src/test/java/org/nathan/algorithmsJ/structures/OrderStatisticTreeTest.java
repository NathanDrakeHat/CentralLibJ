package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStatisticTreeTest{

  OrderStatisticTree balanceTree;
  {
    balanceTree = new OrderStatisticTree();
    for(int i = 0; i < 127; i++){
      balanceTree.insertKey(i);
    }
    assertEquals(6, balanceTree.getHeight());
    for(int i = 0; i < 65; i++){
      balanceTree.deleteKey(i);
    }
  }

  @Test
  public void balanceTest(){
    assertEquals(5, balanceTree.getHeight());
  }

  @Test
  void case1(){
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
    assertEquals(7, root.key); // 7
    assertEquals(2, root.left.key); // 2
    assertEquals(11, root.right.key); // 11
    assertEquals(1, root.left.left.key); // 1
    assertEquals(5, root.left.right.key); // 5
    assertEquals(4, root.left.right.left.key); // 4
    assertEquals(8, root.right.left.key); // 8
    assertEquals(14, root.right.right.key); // 14
    assertEquals(15, root.right.right.right.key); // 15
  }
}