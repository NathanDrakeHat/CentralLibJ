package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciMinHeapTest{

  private static FibonacciMinHeap.Node<Integer, Integer> buildNode(Integer key, boolean mark){
    var res = new FibonacciMinHeap.Node<>(key, key);
    res.mark = mark;
    return res;
  }

  static void addChildren(FibonacciMinHeap.Node<Integer, Integer> n, int... t){
    for(var i : t){
      var x = new FibonacciMinHeap.Node<>(i, i);
      x.mark = false;
      var listLeft = n.left;
      n.left = x;
      x.right = n;
      listLeft.right = x;
      x.left = listLeft;
      x.parent = n.parent;
    }
  }

  private static FibonacciMinHeap<Integer, Integer> buildExample(){
    var H = new FibonacciMinHeap<Integer, Integer>(Comparator.comparingInt(a -> a));
    H.insert(3, 3);
    var m = H.rootList;
    addChildren(m, 17, 24, 23, 7, 21);

    FibonacciMinHeap.Node<Integer, Integer> ptr;
    ptr = buildNode(18, true);
    m.childList = ptr;
    ptr.parent = m;
    m.degree = 2;

    var m_child = m.childList;
    m_child.degree = 1;
    ptr = buildNode(39, true);
    m_child.childList = ptr;
    ptr.parent = m_child;

    addChildren(m_child, 52, 38);
    ptr = buildNode(41, false);
    m_child.left.childList = ptr;
    ptr.parent = m_child.left;
    m_child.left.degree = 1;

    ptr = buildNode(30, false);
    m.right.childList = ptr;
    ptr.parent = m.right;
    m.right.degree = 1;

    FibonacciMinHeap.Node<Integer, Integer> t = buildNode(26, true);
    t.degree = 1;
    ptr = buildNode(35, false);
    t.childList = ptr;
    ptr.parent = t;

    m.right.right.childList = t;
    t.parent = m.right.right;
    m.right.right.degree = 2;

    addChildren(t, 46);
    H.count = 15;
    return H;
  }

  private static List<Integer> bcl(FibonacciMinHeap.Node<Integer, Integer> t){
    List<Integer> res = new ArrayList<>();
    var p = t;
    do{
      res.add(p.key);
      p = p.right;
    }
    while(p != t);
    p = t;
    do{
      res.add(p.key);
      p = p.left;
    }
    while(p != t);
    return res;
  }


  FibonacciMinHeap<Integer, Integer> fibHeap = buildExample();

  @Test
  void case1(){

    var o = fibHeap.extractMin();
    assertEquals(o, 3);
    //see <<introduction to  algorithm>> to find this test sample.
    assertEquals(List.of(7, 18, 38, 7, 38, 18), bcl(fibHeap.rootList));
    assertEquals(List.of(39, 21, 39, 21), bcl(fibHeap.rootList.right.childList));
    assertEquals(List.of(52, 52), bcl(fibHeap.rootList.right.childList.left.childList));
    assertEquals(List.of(23, 17, 24, 23, 24, 17), bcl(fibHeap.rootList.childList));
    assertEquals(List.of(26, 46, 26, 46), bcl(fibHeap.rootList.childList.left.childList));
    assertEquals(List.of(30, 30), bcl(fibHeap.rootList.childList.left.left.childList));
    assertEquals(List.of(35, 35), bcl(fibHeap.rootList.childList.left.childList.childList));
    assertEquals(List.of(41, 41), bcl(fibHeap.rootList.right.right.childList));

    fibHeap.decreaseKey(fibHeap.rootList.childList.left.childList.left, 15);
    fibHeap.decreaseKey(fibHeap.rootList.childList.left.childList.childList, 5);

    assertEquals(List.of(5, 26, 24, 7, 18, 38, 15, 5, 15, 38, 18, 7, 24, 26), bcl(fibHeap.rootList));
    assertEquals(List.of(23, 17, 23, 17), bcl(fibHeap.rootList.right.right.right.childList));
    assertEquals(List.of(30, 30), bcl(fibHeap.rootList.right.right.right.childList.right.childList));
    assertEquals(List.of(39, 21, 39, 21), bcl(fibHeap.rootList.right.right.right.right.childList));
  }
}