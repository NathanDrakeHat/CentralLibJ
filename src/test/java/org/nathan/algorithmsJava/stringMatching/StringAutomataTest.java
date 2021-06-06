package org.nathan.algorithmsJava.stringMatching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.nathan.algorithmsJava.stringMatching.StringAutomata.computeTransitionPattern;
import static org.nathan.algorithmsJava.stringMatching.StringAutomata.finiteAutomationMatcher;

class StringAutomataTest{

  @Test
  void finiteAutomationMatcherTest(){
    var delta = computeTransitionPattern("ba", new char[]{'a', 'b'});
    var res = finiteAutomationMatcher("abbbbbba", delta, 2);
    assertEquals(5, res.get(0));
  }
}