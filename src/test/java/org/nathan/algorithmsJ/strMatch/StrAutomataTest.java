package org.nathan.algorithmsJ.strMatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.nathan.algorithmsJ.strMatch.StrAutomata.computeTransitionPattern;
import static org.nathan.algorithmsJ.strMatch.StrAutomata.finiteAutomationMatcher;

class StrAutomataTest{

  @Test
  void finiteAutomationMatcherTest(){
    var delta = computeTransitionPattern("ba", new char[]{'a', 'b'});
    var res = finiteAutomationMatcher("abbbbbba", delta, 2);
    assertEquals(5, res.get(0));
  }
}