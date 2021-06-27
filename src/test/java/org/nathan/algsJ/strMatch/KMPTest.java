package org.nathan.algsJ.strMatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KMPTest{

  @Test
  void searchTest(){
    var res = KMP.search("ABAAAABAAAAAAAAA", "BAAAAAAAAA");
    assertEquals(1, res.size());
    assertEquals(6, res.get(0));
  }
}