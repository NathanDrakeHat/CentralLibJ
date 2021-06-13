package org.nathan.algorithmsJ.structures;

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

  RedBlackTree<Integer, Integer> balanceTree;
  {
    balanceTree = new RedBlackTree<>(Comparator.comparingInt(o -> o));
    for(int i = 0; i < 127; i++){
      balanceTree.insert(i, i);
    }
    assertEquals(6, balanceTree.getHeight());
    for(int i = 0; i < 65; i++){
      balanceTree.delete(i);
    }
  }

  @Test
  public void balanceTest(){
    assertEquals(5, balanceTree.getHeight());
  }

  RedBlackTree<Integer, String> funcTestCase;
  List<Integer> funcTestAnswer = new ArrayList<>();

  {
    funcTestCase = new RedBlackTree<>(Comparator.comparingInt(o -> o));

    for(int i = 0; i < 16; i++){
      funcTestCase.insert(i, String.valueOf(i));
      funcTestAnswer.add(i);
    }
  }

  @Test
  public void functionsTest(){
    List<Integer> l2 = new ArrayList<>();
    var ri = funcTestCase.reverseIterator();
    assertEquals(15, ri.next().first());

    assertEquals(5 + 1 - 2, funcTestCase.keyRangeSearch(2, 5).size());
    assertEquals(funcTestCase.getHeight(), 4);
    assertEquals(funcTestCase.getCount(), 16);
    assertEquals(funcTestCase.getMinKey(), 0);
    assertEquals(funcTestCase.getMaxKey(), 15);
    assertEquals(funcTestCase.getValueOfMaxKey(), "15");
    assertEquals(funcTestCase.getValueOfMinKey(), "0");
    funcTestCase.inOrderForEach((i, s) -> l2.add(i));
    assertEquals(funcTestAnswer, l2);
    assertEquals(funcTestCase.search(5), "5");
    assertThrows(IllegalStateException.class, () -> {
      for(var i : funcTestCase){
        funcTestCase.insert(i.first(), "i");
      }
    });
  }

}