package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.utils.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatTreeTest{

  OrderStatTree<Integer, Integer> fixUpTree;

  {
    fixUpTree = new OrderStatTree<>(Comparator.comparingInt(o -> o));
    fixUpTree.insertKV(11, 0);
    fixUpTree.insertKV(2, 0);
    fixUpTree.insertKV(14, 0);
    fixUpTree.insertKV(1, 0);
    fixUpTree.insertKV(7, 0);
    fixUpTree.insertKV(15, 0);
    fixUpTree.insertKV(5, 0);
    fixUpTree.insertKV(8, 0);
    fixUpTree.insertKV(4, 0);
  }

  @Test
  public void insertFixUpTest(){
    var root = fixUpTree.root;
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

  private static boolean isBalanced(OrderStatTree<Integer, Integer> tree){
    int black = 0;
    var x = tree.root;
    while(x != null) {
      if(x.color == RBNode.BLACK){ black++; }
      x = x.left;
    }
    var func = new Object(){
      private boolean apply(OrderStatTree.Node<Integer, Integer> x, int black){
        if(x == null){
          return black == 0;
        }
        if(x.color == RBNode.BLACK){
          black--;
        }
        return apply(x.left, black) && apply(x.right, black);
      }
    };
    return func.apply(tree.root, black);
  }

  private static boolean is23(OrderStatTree<Integer, Integer> tree){
    var func = new Object(){
      private boolean is23(OrderStatTree.Node<Integer,Integer> x) {
        if (x == null) return true;
        if (x != tree.root && ((x.color == RBNode.RED && x.left.color == RBNode.RED || (x.color == RBNode.RED && x.right.color == RBNode.RED))))
          return false;
        return is23(x.left) && is23(x.right);
      }
    };
    return func.is23(tree.root);
  }

  private static boolean isSizeConsistent(OrderStatTree<Integer,Integer> tree){
    var func = new Object(){
      private boolean apply(OrderStatTree.Node<Integer,Integer> x){
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

  private static boolean isRankConsistent(OrderStatTree<Integer,Integer> tree){
    for(int i = 0; i < tree.root.size; i++){
      if(i+1 != tree.getRankOfNode(tree.getNodeOfRank(i+1))){
        return false;
      }
    }

    for(var kv : tree){
      if(tree.comparator.compare(kv.first(), tree.getKeyOfRank(tree.getRankOfKey(kv.first()))) != 0){
        return false;
      }
    }
    return true;
  }

  @Test
  public void implementationTest(){
    var rand = new SplittableRandom();
    for(int t = 0; t < 5; t++){
      OrderStatTree<Integer, Integer> tree;
      tree = new OrderStatTree<>(Comparator.comparingInt(o -> o));
      int len = rand.nextInt(8,128);
      List<Integer> shuffle = ArrayUtils.shuffledSequence(0, len);
      for(int i = 0; i < len; i++){
        tree.insertKV(shuffle.get(i), shuffle.get(i));
        assertTrue(isBalanced(tree));
        assertTrue(is23(tree));
        assertTrue(tree.getHeight() <= (2 * Math.log(tree.size() + 1) / Math.log(2)));
        assertTrue(isSizeConsistent(tree));
        assertTrue(isRankConsistent(tree));
      }

      Collections.shuffle(shuffle);

      for(int i = 0; i < len; i++){
        tree.deleteKey(shuffle.get(i));
        assertTrue(isBalanced(tree));
        assertTrue(is23(tree));
        assertTrue(tree.getHeight() <= (2 * Math.log(tree.size() + 1) / Math.log(2)));
        assertTrue(isSizeConsistent(tree));
        assertTrue(isRankConsistent(tree));
      }

      for(int i = 0; i < len; i++){
        tree.insertKV(shuffle.get(i), shuffle.get(i));
        assertTrue(isBalanced(tree));
        assertTrue(is23(tree));
        assertTrue(tree.getHeight() <= (2 * Math.log(tree.size() + 1) / Math.log(2)));
        assertTrue(isSizeConsistent(tree));
        assertTrue(isRankConsistent(tree));
      }

      Collections.shuffle(shuffle);

      for(int i = 0; i < len; i++){
        tree.deleteKey(shuffle.get(i));
        assertTrue(isBalanced(tree));
        assertTrue(is23(tree));
        assertTrue(tree.getHeight() <= (2 * Math.log(tree.size() + 1) / Math.log(2)));
        assertTrue(isSizeConsistent(tree));
        assertTrue(isRankConsistent(tree));
      }
    }
  }

  OrderStatTree<Double, String> funcTestTree;
  List<Double> funcTestAnswer = new ArrayList<>();

  {
    funcTestTree = new OrderStatTree<>(Comparator.comparingDouble(o -> o));
    List<Double> shuffle = DoubleStream.iterate(0, d->d<16,d->++d).boxed().collect(Collectors.toList());
    Collections.shuffle(shuffle);
    for(int i = 0; i < 16; i++){
      funcTestTree.insertKV(shuffle.get(i), String.valueOf(shuffle.get(i)));
      funcTestAnswer.add((double)i);
    }
  }

  @Test
  public void functionsTest(){
    List<Double> l2 = new ArrayList<>();
    var ri = funcTestTree.reverseIterator();
    assertEquals(15, ri.next().first());

    assertEquals(5 + 1 - 2, funcTestTree.keyRangeSearch(2., 5.).size());
    assertTrue(funcTestTree.getHeight() <= 2 * Math.log(funcTestTree.size() + 1) / Math.log(2));
    assertEquals(16, funcTestTree.size());
    assertEquals(0, funcTestTree.getMinKey());
    assertEquals(15, funcTestTree.getMaxKey());
    assertEquals(15, funcTestTree.floorOfKey(16.));
    assertEquals(0, funcTestTree.ceilingOfKey(-1.));
    assertEquals(5, funcTestTree.ceilingOfKey(4.5));
    assertEquals(8, funcTestTree.floorOfKey(8.5));
    assertEquals("15.0", funcTestTree.getValueOfMaxKey());
    assertEquals("0.0", funcTestTree.getValueOfMinKey());
    for(var kv : funcTestTree){
      l2.add(kv.first());
    }
    assertEquals(l2, funcTestAnswer);
    assertEquals("5.0", funcTestTree.getValOfKey(5.));
    funcTestTree.updateKV(15.,"test");
    assertEquals("test", funcTestTree.getValOfKey(15.));
    assertThrows(IllegalStateException.class, () -> {
      for(var ignored : funcTestTree){
        funcTestTree.insertKV(-1., "i");
      }
    });
  }

  @SuppressWarnings("unused")
 private static class BTreePrinter {
   public static <T extends Comparable<?>> void printNode(OrderStatTree.Node<T,T> root) {
     int maxLevel = BTreePrinter.maxLevel(root);

     printNodeInternal(Collections.singletonList(root), 1, maxLevel);
   }

   private static <T extends Comparable<?>> void printNodeInternal(List<OrderStatTree.Node<T,T>> nodes, int level, int maxLevel) {
     if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
       return;

     int floor = maxLevel - level;
     int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
     int firstSpaces = (int) Math.pow(2, (floor)) - 1;
     int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

     BTreePrinter.printWhitespaces(firstSpaces);

     List<OrderStatTree.Node<T,T>> newNodes = new ArrayList<>();
     int backs = 0;
     for (OrderStatTree.Node<T,T> node : nodes) {
       if (node != null) {
         System.out.print("\b".repeat(backs));
         String data = String.format("%s,%s",node.key != null ? node.key:"N",node.color? "b":"r");
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

     for (int i = 1; i <= edgeLines; i++) {
       for (int j = 0; j < nodes.size(); j++) {
         BTreePrinter.printWhitespaces(firstSpaces - i);
         if (nodes.get(j) == null) {
           BTreePrinter.printWhitespaces(edgeLines + edgeLines + i + 1);
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

         BTreePrinter.printWhitespaces(edgeLines + edgeLines - i);
       }

       System.out.println();
     }

     printNodeInternal(newNodes, level + 1, maxLevel);
   }

   private static void printWhitespaces(int count) {
     for (int i = 0; i < count; i++)
       System.out.print(" ");
   }

   private static <T extends Comparable<?>> int maxLevel(OrderStatTree.Node<T,T> node) {
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