package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStatisticTreeTest{

  OrderStatisticTree<Integer> balanceTree;
  {
    balanceTree = new OrderStatisticTree<>(Integer::compareTo);
    for(int i = 0; i < 127; i++){
      balanceTree.insertKey( i);
    }
    assertEquals(6, balanceTree.getHeight());
    for(int i = 0; i < 65; i++){
      balanceTree.deleteKey( i);
    }
  }

  @Test
  public void balanceTest(){
    assertEquals(5, balanceTree.getHeight());
  }


  OrderStatisticTree<Double> caseTree;
  {
    caseTree = new OrderStatisticTree<>(Double::compareTo);
    caseTree.insertKey(11.);
    caseTree.insertKey(2.);
    caseTree.insertKey(14.);
    caseTree.insertKey(1.);
    caseTree.insertKey(7.);
    caseTree.insertKey(15.);
    caseTree.insertKey(5.);
    caseTree.insertKey(8.);
    caseTree.insertKey(4.);
  }
  @Test
  void case1Test(){
    OrderStatisticTree.Node<Double> root = caseTree.getRoot();
    assertEquals(7., root.key); // 7
    assertEquals(2., root.left.key); // 2
    assertEquals(11., root.right.key); // 11
    assertEquals(1., root.left.left.key); // 1
    assertEquals(5., root.left.right.key); // 5
    assertEquals(4., root.left.right.left.key); // 4
    assertEquals(8., root.right.left.key); // 8
    assertEquals(14., root.right.right.key); // 14
    assertEquals(15., root.right.right.right.key); // 15
  }

  @Test
  void functionsTest(){
    assertEquals(2, caseTree.floor(2.5));
    assertEquals(15, caseTree.ceiling(14.5));
  }
}