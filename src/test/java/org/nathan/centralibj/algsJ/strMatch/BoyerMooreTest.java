package org.nathan.centralibj.algsJ.strMatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoyerMooreTest{

  @Test
  void searchTest(){
    assertEquals(15, BoyerMoore.search("FINDINAHAYSTACKNEEDLEINA", "NEEDLE"));
  }
}