package org.nathan.algsJ.strMatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RabinKarpTest{

  @Test
  void searchTest(){
    var res = RabinKarp.search(
            new String(new char[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}),
            new String(new char[]{0,1,2,3,4}),
            256,
            997);
    assertEquals(1,res.size());
    assertEquals(0, res.get(0));
  }
}