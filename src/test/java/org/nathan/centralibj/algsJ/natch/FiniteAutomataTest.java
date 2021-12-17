package org.nathan.centralibj.algsJ.natch;

import org.junit.jupiter.api.Test;
import org.nathan.centralibj.algsJ.strMatch.FiniteAutomata;

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