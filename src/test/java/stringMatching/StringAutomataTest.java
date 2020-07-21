package stringMatching;

import org.junit.jupiter.api.Test;

import static stringMatching.StringAutomata.*;
import static org.junit.jupiter.api.Assertions.*;

class StringAutomataTest
{

    @Test
    void finiteAutomationMatcherTest()
    {
        var delta = computeTransitionPattern("ba", new char[]{'a', 'b'});
        var res = finiteAutomationMatcher("abbbbbba", delta, 2);
        assertEquals(5, res.get(0));
    }
}