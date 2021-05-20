package org.nathan.acm;

import org.junit.jupiter.api.Test;
import static org.nathan.acm.ACM.*;
import static org.junit.jupiter.api.Assertions.*;

public class ACMTest{

    @Test
    public void fastPowerModTest(){
        assertEquals(2, fastPowerMod(3,3,25));
        assertEquals(3, fastPowerMod(4,3, 61));
    }

    @Test
    public void longFastMultiplyModTest(){
        assertEquals(1, longFastMultiplyMod1(3,7,20));
        assertEquals(5, longFastMultiplyMod1(5,5,20));
        assertEquals(1, longFastMultiplyMod2(3,7,20));
        assertEquals(5, longFastMultiplyMod2(5,5,20));
    }
}
