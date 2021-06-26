package org.nathan.algsJ.strMatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FiniteAutomaTest{

  @Test
  void caseTest(){
    var res = FiniteAutoma.search("abbbbbba", "ba", new char[]{'a', 'b'});
    assertEquals(5, res.get(0));

    res = FiniteAutoma.search("abbbbbba", "bbb", new char[]{'a', 'b'});
    assertEquals(0, res.get(0));
  }
}