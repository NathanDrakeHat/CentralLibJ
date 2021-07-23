package org.nathan.centralib.algsJ.dataStruc;

import org.junit.jupiter.api.Test;
import org.nathan.centralib.utils.ArrayUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.nathan.centralib.algsJ.misc.OrthoLineIntersectTest.Point;

class IntvalSerchTreeTest{

  static <Key, Val> boolean isISTree(IntvalSerchTree<Key, Val> tree){
    var funcWalk = new Object(){
      boolean b = true;

      void apply(IntvalSerchTree.Node<Key, Val> n){
        if(n == null){ throw new RuntimeException(); }
        if(!b || n == tree.sentinel){
          return;
        }
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
        else{
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
      List<Integer> shuffle = ArrayUtils.shuffledRangeList(0, rand.nextInt(16, len));
      IntvalSerchTree<Integer, Void> t = new IntvalSerchTree<>(Integer::compareTo);
      for(var item : shuffle){
        t.insertInterval(item, item + rand.nextInt(8, len / 2), null);
        assertTrue(isISTree(t));
      }

      Collections.shuffle(shuffle);
      for(var item : shuffle){
        t.deleteInterval(item);
        assertTrue(isISTree(t));
      }

      for(var item : shuffle){
        t.insertInterval(item, item + rand.nextInt(8, len / 2), null);
        assertTrue(isISTree(t));
      }

      Collections.shuffle(shuffle);
      for(var item : shuffle){
        t.deleteInterval(item);
        assertTrue(isISTree(t));
      }
    }
  }

  List<Point> intervals = new ArrayList<>();
  Point sect;
  Set<Point> answers = new HashSet<>();
  int len = 64;

  {
    var rand = new SplittableRandom();
    List<Integer> shuffle = ArrayUtils.shuffledRangeList(0, len);
    for(var s : shuffle){
      intervals.add(new Point(s, s + rand.nextInt(1, len / 2)));
    }
    sect = intervals.get(intervals.size() - 1);
    intervals.remove(intervals.size() - 1);
    for(var it : intervals){
      if(it.first() >= sect.first() && it.first() <= sect.second()){
        answers.add(it);
      }
      else if(it.second() >= sect.first() && it.second() <= sect.second()){
        answers.add(it);
      }
    }
  }

  @Test
  void intersectsTest(){
    IntvalSerchTree<Integer, Void> t = new IntvalSerchTree<>(Integer::compareTo);
    for(var it : intervals){
      t.intersects(it.first(), it.second());
    }
    var res = t.intersects(sect.first(), sect.second());
    for(var r : res){
      var p = new Point(r.first(), r.second());
      assertTrue(answers.contains(p));
    }
  }
}