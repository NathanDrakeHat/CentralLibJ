package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RBTreeTest{

  RBTree<Integer, Integer> RBtree;

  {
    RBtree = new RBTree<>(Comparator.comparingInt(o -> o));
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

  private static boolean isBalanced(RBTree<Integer, Integer> tree){
    int black = 0;
    var x = tree.root;
    while(x != null) {
      if(x.color == RBTree.BLACK){ black++; }
      x = x.left;
    }
    var func = new Object(){
      private boolean apply(RBTree.Node<Integer, Integer> x, int black){
        if(x == null){
          return black == 0;
        }
        if(x.color == RBTree.BLACK){
          black--;
        }
        return apply(x.left, black) && apply(x.right, black);
      }
    };
    return func.apply(tree.root, black);
  }

  private static boolean is23(RBTree<Integer, Integer> tree){
    var func = new Object(){
      private boolean is23(RBTree.Node<Integer,Integer> x) {
        if (x == null) return true;
        if (x != tree.root && ((x.color == RBTree.RED && x.left.color == RBTree.RED || (x.color == RBTree.RED && x.right.color == RBTree.RED))))
          return false;
        return is23(x.left) && is23(x.right);
      }
    };
    return func.is23(tree.root);
  }

  private static boolean isSizeConsistent(RBTree<Integer,Integer> tree){
    var func = new Object(){
      private boolean apply(RBTree.Node<Integer,Integer> x){
        if(x == tree.sentinel){
          return true;
        }
        if(x.size != x.left.size + x.right.size + 1){
          return false;
        }
        return apply(x.left) && apply(x.right);
      }
    };
    return func.apply(tree.root);
  }

//  private static boolean isRankConsistent(RBTree<Integer,Integer> tree){
//    for(int i = 0; i < tree.root.size; i++){
//      if(i != tree.getRankOfNode(tree.getNodeOfRank(i))){
//        return false;
//      }
//    }
//
//    for(var key : tree){
//      if(tree.comparator.compare(key, tree.getKeyOfRank(tree.getRankOfKey(key))) != 0){
//        return false;
//      }
//    }
//    return true;
//  }

  @Test
  public void implementationTest(){
    for(int t = 0; t < 10; t++){
      RBTree<Integer, Integer> tree;
      tree = new RBTree<>(Comparator.comparingInt(o -> o));
      int len = 2048;
      List<Integer> shuffle = ArrayUtils.shuffledSequence(0, len);
      for(int i = 0; i < len; i++){
        tree.insert(shuffle.get(i), shuffle.get(i));
        assertTrue(isBalanced(tree));
        assertTrue(is23(tree));
        assertTrue(tree.getHeight() <= 2 * Math.log(tree.size() + 1) / Math.log(2));
        assertTrue(isSizeConsistent(tree));
      }

      for(int i = 0; i < len / 2; i++){
        tree.delete(shuffle.get(i));
        assertTrue(isBalanced(tree));
        assertTrue(tree.getHeight() <= 2 * Math.log(tree.size() + 1) / Math.log(2));
        assertTrue(isSizeConsistent(tree));
      }
    }
  }

  RBTree<Integer, String> funcTestCase;
  List<Integer> funcTestAnswer = new ArrayList<>();

  {
    funcTestCase = new RBTree<>(Comparator.comparingInt(o -> o));

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
    assertTrue(funcTestCase.getHeight() <= 2 * Math.log(funcTestCase.size() + 1) / Math.log(2));
    assertEquals(16, funcTestCase.size());
    assertEquals(0, funcTestCase.getMinKey());
    assertEquals(15, funcTestCase.getMaxKey());
    assertEquals("15", funcTestCase.getValueOfMaxKey());
    assertEquals("0", funcTestCase.getValueOfMinKey());
    for(var kv : funcTestCase){
      l2.add(kv.first());
    }
    assertEquals(l2, funcTestAnswer);
    assertEquals("5", funcTestCase.search(5));
    assertThrows(IllegalStateException.class, () -> {
      for(var i : funcTestCase){
        funcTestCase.insert(-1, "i");
      }
    });
  }

}