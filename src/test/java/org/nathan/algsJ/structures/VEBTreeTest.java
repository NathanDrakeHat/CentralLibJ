package org.nathan.algsJ.structures;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class VEBTreeTest{

  private static boolean has(VEBTree V, int[] a){
    for(var i : a){
      if(!V.hasMember(i)){
        return false;
      }
    }
    return true;
  }

  @Test
  void testCase1(){
    var V = new VEBTree(4);
    assertTrue(V.tryGetMaximum().isEmpty());
    assertTrue(V.tryGetMinimum().isEmpty());
    V.safeInsert(1).safeInsert(9).safeInsert(5).safeInsert(3).safeInsert(15);
    V.safeInsert(5).safeInsert(3).safeInsert(15).safeInsert(1);
    assertThrows(NoSuchElementException.class, () -> V.forceGetSuccessor(15));
    assertTrue(V.tryGetSuccessor(15).isEmpty());
    assertThrows(NoSuchElementException.class, () -> V.forceGetPredecessor(1));
    assertTrue(V.tryGetPredecessor(1).isEmpty());
    assertEquals(1, (int) V.forceGetPredecessor(3));
    assertEquals(15, (int) V.forceGetSuccessor(9));
    assertEquals(3, (int) V.forceGetPredecessor(5));
    assertTrue(has(V, new int[]{1, 3, 5, 9, 15}));
    assertFalse(has(V, new int[]{2, 4, 6, 7, 8, 10, 11, 12, 13, 14}));
    assertEquals(1, (int) V.forceGetMinimum());
    assertEquals(15, (int) V.forceGetMaximum());
    V.safeDelete(1).safeDelete(5).safeDelete(15);
    assertFalse(V.hasMember(1));
    assertTrue(has(V, new int[]{3, 9}));
    assertEquals(3, (int) V.forceGetMinimum());
    assertEquals(9, (int) V.forceGetMaximum());
    assertFalse(has(V, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}));
    assertEquals(9, (int) V.forceGetSuccessor(3));
    assertEquals(3, (int) V.forceGetPredecessor(9));
  }
}