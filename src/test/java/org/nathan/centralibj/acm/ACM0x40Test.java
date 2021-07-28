package org.nathan.centralibj.acm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ACM0x40Test{

  @Test
  void legendOfGalaxyHeroTest(){
    var le = new ACM0x40.LegendOfGalaxyHero(4);
    assertEquals(-1, le.distance(0,1));
    le.concatenate(1,0);
    le.concatenate(2,0);
    assertEquals(2, le.distance(0,2));
  }
}