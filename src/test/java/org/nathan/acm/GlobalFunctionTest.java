package org.nathan.acm;

import org.junit.jupiter.api.Test;
import static org.nathan.acm.GlobalFunction.*;
import static org.junit.jupiter.api.Assertions.*;

public class GlobalFunctionTest{

    @Test
    public void fastPowerModTest(){
        assertEquals(2, fastPowerMod(3,3,25));
        assertEquals(3, fastPowerMod(4,3, 61));
    }

    @Test
    public void longFastMultiplyModTest(){
        assertEquals(1, longFastMultiplyMod(3,7,20));
        assertEquals(5, fastPowerMod(5,5,20));
    }
}
