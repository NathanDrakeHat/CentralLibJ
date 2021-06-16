package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ISTreeTest{

  static <Key> boolean isISTree(ISTree<Key> tree){
    var funcWalk = new Object(){
      boolean b = true;
      void apply(ISTree.Node<Key> n){
        if(!b || n == tree.sentinel){ return; }
        if(n.left != tree.sentinel && n.right != tree.sentinel){
          var c = tree.comparator.compare(n.left.max, n.right.max) < 0 ? n.right.max : n.left.max;
          var m = tree.comparator.compare(n.max, c) < 0 ? c : n.max;
          b = (n.max == m);
          if(b && n.high == m){
              b = (n.high == n.max);
          }
        }
        else if(n.left != tree.sentinel){
          var c = n.left.max;
          var m = tree.comparator.compare(n.max, c) < 0 ? c : n.max;
          b = (n.max == m);
          if(b && n.high == m){
              b = (n.high == n.max);
          }
        }
        else if(n.right != tree.sentinel){
          var c = n.right.max;
          var m = tree.comparator.compare(n.max, c) < 0 ? c : n.max;
          b = (n.max == m);
          if(b && n.high == m){
              b = (n.high == n.max);
          }
        }
        else if(n.left == tree.sentinel && n.right == tree.sentinel){
          b = (n.high == n.max);
        }
        else {
          throw new RuntimeException();
        }

        apply(n.left);
        apply(n.right);
      }
    };
    funcWalk.apply(tree.root);
    return funcWalk.b;
  }


  @Test
  void isISTreeTest(){
    List<Integer> shuffle = ArrayUtils.shuffledSequence(0, 128);
    ISTree<Integer> t = new ISTree<>(Integer::compareTo);
    var rand = new SplittableRandom();
    for(var item : shuffle){
      t.insertInterval(item, item + rand.nextInt(8, 64));
      assertTrue(isISTree(t));
    }

    Collections.shuffle(shuffle);
    for(var item : shuffle){
      t.deleteInterval(item);
      assertTrue(isISTree(t));
    }

    for(var item : shuffle){
      t.insertInterval(item, item + rand.nextInt(8, 64));
      assertTrue(isISTree(t));
    }

    Collections.shuffle(shuffle);
    for(var item : shuffle){
      t.deleteInterval(item);
      assertTrue(isISTree(t));
    }
  }

  @SuppressWarnings("unused")
  private static class BTreePrinter {
    public static <T extends Comparable<?>> void printNode(ISTree.Node<T> root) {
      int maxLevel = BTreePrinter.maxLevel(root);

      printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<ISTree.Node<T>> nodes, int level, int maxLevel) {
      if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
        return;

      int floor = maxLevel - level;
      int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
      int firstSpaces = (int) Math.pow(2, (floor)) - 1;
      int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

      BTreePrinter.printWhitespaces(firstSpaces);

      List<ISTree.Node<T> > newNodes = new ArrayList<>();
      int backs = 0;
      for (ISTree.Node<T>  node : nodes) {
        if (node != null) {
//          System.out.print("\b".repeat(backs));
          String data = String.format("(%s,%s,%s)",node.low != null ? node.low:"N",node.high != null ? node.high:"N",node.max != null ? node.max:"N");
//          backs = data.length() - 1;
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

    private static <T extends Comparable<?>> int maxLevel(ISTree.Node<T>  node) {
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