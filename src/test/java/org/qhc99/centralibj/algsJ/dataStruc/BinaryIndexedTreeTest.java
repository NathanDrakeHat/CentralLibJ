package org.qhc99.centralibj.algsJ.dataStruc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class BinaryIndexedTreeTest {

  static int[] a = new int[10];
  static BinaryIndexedTree treelikeArray = new BinaryIndexedTree(a.length);

  @Test
  void test() {
    treelikeArray.prefixSumAdd(5,1);
    treelikeArray.prefixSumAdd(3,1);
    treelikeArray.prefixSumAdd(10,1);
    assertEquals(2,treelikeArray.prefixSumOfRange(3,6));
    assertEquals(1,treelikeArray.prefixSumOfRange(3,3));
    assertEquals(3,treelikeArray.prefixSumOfRange(3,10));

  }
}

