package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.utils.ArrayUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OSTreeTest{

  OSTree<Integer, Integer> RBtree;

  {
    RBtree = new OSTree<>(Comparator.comparingInt(o -> o));
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

  private static boolean isBalanced(OSTree<Integer, Integer> tree){
    int black = 0;
    var x = tree.root;
    while(x != null) {
      if(x.color == OSTree.BLACK){ black++; }
      x = x.left;
    }
    var func = new Object(){
      private boolean apply(OSTree.Node<Integer, Integer> x, int black){
        if(x == null){
          return black == 0;
        }
        if(x.color == OSTree.BLACK){
          black--;
        }
        return apply(x.left, black) && apply(x.right, black);
      }
    };
    return func.apply(tree.root, black);
  }

  private static boolean is23(OSTree<Integer, Integer> tree){
    var func = new Object(){
      private boolean is23(OSTree.Node<Integer,Integer> x) {
        if (x == null) return true;
        if (x != tree.root && ((x.color == OSTree.RED && x.left.color == OSTree.RED || (x.color == OSTree.RED && x.right.color == OSTree.RED))))
          return false;
        return is23(x.left) && is23(x.right);
      }
    };
    return func.is23(tree.root);
  }

  private static boolean isSizeConsistent(OSTree<Integer,Integer> tree){
    var func = new Object(){
      private boolean apply(OSTree.Node<Integer,Integer> x){
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
    var rand = new SplittableRandom();
    for(int t = 0; t < 10; t++){
      OSTree<Integer, Integer> tree;
      tree = new OSTree<>(Comparator.comparingInt(o -> o));
      int len = rand.nextInt(512,1024);
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
        assertTrue(is23(tree));
        assertTrue(tree.getHeight() <= 2 * Math.log(tree.size() + 1) / Math.log(2));
        assertTrue(isSizeConsistent(tree));
      }
    }
  }

  OSTree<Integer, String> funcTestCase;
  List<Integer> funcTestAnswer = new ArrayList<>();

  {
    funcTestCase = new OSTree<>(Comparator.comparingInt(o -> o));

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

 static class BTreePrinter {
   public static <T extends Comparable<?>> void printNode(OSTree.Node<T,T> root) {
     int maxLevel = BTreePrinter.maxLevel(root);

     printNodeInternal(Collections.singletonList(root), 1, maxLevel);
   }

   private static <T extends Comparable<?>> void printNodeInternal(List<OSTree.Node<T,T>> nodes, int level, int maxLevel) {
     if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
       return;

     int floor = maxLevel - level;
     int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
     int firstSpaces = (int) Math.pow(2, (floor)) - 1;
     int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

     BTreePrinter.printWhitespaces(firstSpaces);

     List<OSTree.Node<T,T>> newNodes = new ArrayList<>();
     int backs = 0;
     for (OSTree.Node<T,T> node : nodes) {
       if (node != null) {
         System.out.print("\b".repeat(backs));
         String data = String.format("%s,%d",node.key != null ? node.key:"N",node.size);
         backs = data.length() - 1;
         System.out.print(data);
         newNodes.add(node.left);
         newNodes.add(node.right);
       } else {
         newNodes.add(null);
         newNodes.add(null);
         System.out.print(" ");
       }

       BTreePrinter.printWhitespaces(betweenSpaces);
     }
     System.out.println();

     for (int i = 1; i <= endgeLines; i++) {
       for (int j = 0; j < nodes.size(); j++) {
         BTreePrinter.printWhitespaces(firstSpaces - i);
         if (nodes.get(j) == null) {
           BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
           continue;
         }

         if (nodes.get(j).left != null)
           System.out.print("/");
         else
           BTreePrinter.printWhitespaces(1);

         BTreePrinter.printWhitespaces(i + i - 1);

         if (nodes.get(j).right != null)
           System.out.print("\\");
         else
           BTreePrinter.printWhitespaces(1);

         BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
       }

       System.out.println();
     }

     printNodeInternal(newNodes, level + 1, maxLevel);
   }

   private static void printWhitespaces(int count) {
     for (int i = 0; i < count; i++)
       System.out.print(" ");
   }

   private static <T extends Comparable<?>> int maxLevel(OSTree.Node<T,T> node) {
     if (node == null)
       return 0;

     return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
   }

   private static <T> boolean isAllElementsNull(List<T> list) {
     for (Object object : list) {
       if (object != null)
         return false;
     }

     return true;
   }

 }
}