package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.misc.BTreePrinter;
import org.nathan.centralUtils.utils.ArrayUtils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.*;

class IntvalSerchTreeTest{

  static <Key> boolean isISTree(IntvalSerchTree<Key> tree){
    var funcWalk = new Object(){
      boolean b = true;
      void apply(IntvalSerchTree.Node<Key> n){
        if(n ==null) throw new RuntimeException();
        if(!b || n == tree.sentinel){ return; }
        if(n.left != tree.sentinel && n.right != tree.sentinel){
          var c = tree.comparator.compare(n.left.max, n.right.max) < 0 ? n.right.max : n.left.max;
          var m = tree.comparator.compare(n.max, c) < 0 ? c : n.max;
          b = (n.max == m);
        }
        else if(n.left != tree.sentinel){
          var c = n.left.max;
          var m = tree.comparator.compare(n.max, c) < 0 ? c : n.max;
          b = (n.max == m);
        }
        else if(n.right != tree.sentinel){
          var c = n.right.max;
          var m = tree.comparator.compare(n.max, c) < 0 ? c : n.max;
          b = (n.max == m);
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
    var rand = new SplittableRandom();
    int len = 128;
    for(int i = 0; i < 5; i++){
      List<Integer> shuffle = ArrayUtils.shuffledSequence(0, rand.nextInt(8, len));
      IntvalSerchTree<Integer> t = new IntvalSerchTree<>(Integer::compareTo);
      for(var item : shuffle){
        t.insertInterval(item, item + rand.nextInt(8, 64));
        assertTrue(isISTree(t));
      }

      Collections.shuffle(shuffle);
      for(var item : shuffle){
        t.deleteInterval(item);
        if(!isISTree(t)){
          fail();
        }
      }

      for(var item : shuffle){
        t.insertInterval(item, item + rand.nextInt(8, 64));
        assertTrue(isISTree(t));
      }

      Collections.shuffle(shuffle);
      for(var item : shuffle){
        t.deleteInterval(item);
        if(!isISTree(t)){
          fail();
        }
      }
    }
  }
}