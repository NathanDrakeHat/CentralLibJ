package Algorithms.stringMatching;

import org.junit.jupiter.api.Test;

import static Algorithms.stringMatching.StringAutomata.computeTransitionPattern;
import static Algorithms.stringMatching.StringAutomata.finiteAutomationMatcher;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringAutomataTest {

    @Test
    void finiteAutomationMatcherTest() {
        var delta = computeTransitionPattern("ba", new char[]{'a', 'b'});
        var res = finiteAutomationMatcher("abbbbbba", delta, 2);
        assertEquals(5, res.get(0));
    }
}