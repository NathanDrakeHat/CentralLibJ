package org.nathan.acm;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.nathan.acm.ACM0x10.*;

class ACM0x10Test {

  List<Tuple<Integer, Integer>> LRITCases = new ArrayList<>(7);

  {
    LRITCases.add(new Tuple<>(1,2));
    LRITCases.add(new Tuple<>(1,1));
    LRITCases.add(new Tuple<>(1,4));
    LRITCases.add(new Tuple<>(1,5));
    LRITCases.add(new Tuple<>(1,1));
    LRITCases.add(new Tuple<>(1,3));
    LRITCases.add(new Tuple<>(1,3));
  }

  @Test
  void largestRectInHistTest() {
    assertEquals(8, largestRectInHist(LRITCases));
  }
}