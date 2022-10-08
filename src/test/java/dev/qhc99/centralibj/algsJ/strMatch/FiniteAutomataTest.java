package dev.qhc99.centralibj.algsJ.strMatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FiniteAutomataTest{

  @Test
  void caseTest(){
    var res = FiniteAutomata.search("abbbbbba", "ba", new char[]{'a', 'b'});
    assertEquals(5, res.get(0));

    res = FiniteAutomata.search("abbbbbba", "bbb", new char[]{'a', 'b'});
    assertEquals(0, res.get(0));
  }
}