package org.nathan.centralibj.acm;

import org.junit.jupiter.api.Test;
import org.nathan.centralibj.utils.tuples.Triad;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ACM0x40Test {

  @Test
  void legendOfGalaxyHeroTest() {
    var le = new ACM0x40.LegendOfGalaxyHero(4);
    assertEquals(-1, le.distance(0, 1));
    le.concatenate(1, 0);
    le.concatenate(2, 0);
    assertEquals(2, le.distance(0, 2));
  }


  @Test
  void foodChainTest() {
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
  void treelikeArrayTest() {
    var ds = IntStream.range(1, 17).toArray();
    var ta = new ACM0x40.TreelikeArray(ds);
    var acc = ds[0];
    for (int i = 1; i < ds.length; i++) {
      acc += ds[i];
      ds[i] = acc;
    }
    for (int i = 0; i < ds.length; i++) {
      assertEquals(ds[i], ta.prefixSumOf(i + 1));
    }
    assertEquals(5,ta.prefixSumOfRange(2,3));
  }

  @Test
  void rangeAddRangeSumQueryTest() {
    var a = new int[16];
    var q = new ACM0x40.RangeAddRangeSumQuery(a);
    q.addRange(1, 16, 1);
    assertEquals(16, q.sumOfRange(1, 16));
    q.addRange(8, 11, 1);
    assertEquals(20, q.sumOfRange(1, 16));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  void maxSegmentTreeTest() {
    var t = ACM0x40.SegmentTreeTemplate.maxSegmentTree(List.of(3, 6, 4, 8, 1, 2, 5, 7, 0));
    assertEquals(8,t.query(2,8).get());
    assertEquals(7, t.query(8,10).get());
    assertEquals(8, t.query(2,4).get());
    t.update(4,9);
    assertEquals(9,t.query(2,8).get());
    assertEquals(7, t.query(8,10).get());
    assertEquals(9, t.query(2,4).get());
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  void maxContinuousSumSegmentTreeTest(){
    var t=  ACM0x40.SegmentTreeTemplate.maxContinuousSumSegmentTree(List.of(1,-2,3,-4,5,-6,7,-8,9,10,-11,12));
    assertEquals(10, t.query(1,11).get());
    assertEquals(10, t.query(1,10).get());
  }
}