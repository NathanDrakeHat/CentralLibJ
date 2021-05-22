package org.nathan.acm;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.Actions;

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

    @Test
    public void solveHamiltonTest(){
        double[][] weights = new double[5][5];
        for (var r : weights)
        {
            for (int i = 0; i < weights.length; i++)
            {
                r[i] = Double.POSITIVE_INFINITY;
            }
        }
        for (int i = 0; i < 5; i++)
        {
            weights[i][i] = 0;
        }

        Actions.TriConsumer<Integer, Integer, Double> putBiDirect = (Integer a, Integer b, Double val)->{
            weights[a][b] = val;
            weights[b][a] = val;
        };

        putBiDirect.accept(0, 1, 2.);
        putBiDirect.accept(1, 2, 2.);
        putBiDirect.accept(2, 3, 2.);
        putBiDirect.accept(3, 4, 2.);
        putBiDirect.accept(0, 4, 9.);
        putBiDirect.accept(0, 3, 7.);
        putBiDirect.accept(0, 2, 5.);

        assertEquals(8, solve_hamilton(5, weights));
    }
}
