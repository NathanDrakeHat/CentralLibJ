package org.nathan.algsJ.structures;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProtoVEBTreeTest{

  public ProtoVEBTree T;

  @BeforeEach
  void setUp(){
    T = new ProtoVEBTree(2);
    T.insert(1);
    T.insert(3);
    T.insert(5);
    T.insert(11);
    T.insert(13);
    T.insert(15);
  }

  @AfterEach
  void clean(){
    T = null;
  }

  @Test
  void hasMemberTest(){
    assertTrue(T.hasMember(3));
  }

  @Test
  void predecessorTest(){
    assertEquals(1, T.predecessor(3));
  }

  @Test
  void successorTest(){
    assertEquals(5, T.successor(3));
  }

  @Test
  void maximumTest(){
    assertEquals(15, T.maximum());
  }

  @Test
  void minimumTest(){
    assertEquals(1, T.minimum());
  }

  @Test
  void exceptionTest(){
    assertThrows(NullPointerException.class, () -> T.successor(15));
  }
}