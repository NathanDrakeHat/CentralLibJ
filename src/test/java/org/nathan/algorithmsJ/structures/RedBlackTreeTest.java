package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

  RedBlackTree<Integer, Integer> balanceTree;
  {
    balanceTree = new RedBlackTree<>(Comparator.comparingInt(o -> o));
    List<Integer> shuffle = ArrayUtils.shuffledSequence(0, 127);
    for(int i = 0; i < 127; i++){
      balanceTree.insert(shuffle.get(i), shuffle.get(i));
    }
    assertTrue(isBalanced(balanceTree));

    for(int i = 0; i < 65; i++){
      balanceTree.delete(shuffle.get(i));
    }
    assertTrue(isBalanced(balanceTree));
  }

  private static boolean isBalanced(RedBlackTree<Integer,Integer> tree) {
    int black = 0;
    var x = tree.root;
    while (x != null) {
      if (x.color== RedBlackTree.BLACK) black++;
      x = x.left;
    }

    var func = new Object(){
      private boolean apply(RedBlackTree.Node<Integer,Integer> x, int black) {
        if (x == null) {
          return black == 0;
        }
        if (x.color == RedBlackTree.BLACK) {
          black--;
        }
        return apply(x.left, black) && apply(x.right, black);
      }
    };
    return func.apply(tree.root, black);
  }

  @Test
  public void balanceTest(){
    assertEquals(6, balanceTree.getHeight());
    assertTrue(isBalanced(balanceTree));
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
    assertEquals(5, funcTestCase.getHeight());
    assertEquals(16, funcTestCase.getCount());
    assertEquals(0, funcTestCase.getMinKey());
    assertEquals(15, funcTestCase.getMaxKey());
    assertEquals("15", funcTestCase.getValueOfMaxKey());
    assertEquals("0", funcTestCase.getValueOfMinKey());
    funcTestCase.inOrderForEach((i, s) -> l2.add(i));
    assertEquals(l2, funcTestAnswer);
    assertEquals("5", funcTestCase.search(5));
    assertThrows(IllegalStateException.class, () -> {
      for(var i : funcTestCase){
        funcTestCase.insert(i.first(), "i");
      }
    });
  }

}