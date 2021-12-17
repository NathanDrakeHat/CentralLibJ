package org.nathan.centralibj.algsJ.natch;

import org.junit.jupiter.api.Test;
import org.nathan.centralibj.algsJ.strMatch.BoyerMoore;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoyerMooreTest{

  @Test
  void searchTest(){
    assertEquals(15, BoyerMoore.search("FINDINAHAYSTACKNEEDLEINA", "NEEDLE"));
  }
}