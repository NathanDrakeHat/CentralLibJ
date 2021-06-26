package org.nathan.algsJ.strMatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoyerMooreTest{

  @Test
  void searchTest(){
    assertEquals(15, BoyerMoore.search("FINDINAHAYSTACKNEEDLEINA", "NEEDLE"));
  }
}