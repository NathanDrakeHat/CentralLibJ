package org.nathan.centralibj.acm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.nathan.centralibj.utils.tuples.Triad;

import java.util.List;

class ACM0x40Test{

  @Test
  void legendOfGalaxyHeroTest(){
    var le = new ACM0x40.LegendOfGalaxyHero(4);
    assertEquals(-1, le.distance(0,1));
    le.concatenate(1,0);
    le.concatenate(2,0);
    assertEquals(2, le.distance(0,2));
  }


  @Test
  void foodChainTest(){
    // A B C A
    assertEquals(2, ACM0x40.foodChain(4,
            List.of(
                    new Triad<>(2, 0, 1),
                    new Triad<>(2, 1, 2),
                    new Triad<>(2, 2, 3),
                    new Triad<>(1, 0, 3),
                    new Triad<>(2, 1, 3),
                    new Triad<>(2, 0, 3))));
  }

  @Test
  void rangeAddRangeSumQueryTest(){
    var a = new int[16];
    var q = new ACM0x40.RangeAddRangeSumQuery(a);
    q.addRange(1,16, 1);
    assertEquals(16, q.sumOfRange(1,16));
    q.addRange(8,11, 1);
    assertEquals(20,q.sumOfRange(1,16));
  }
}