package dev.qhc99.centralibj.algsJ.misc;

import org.junit.jupiter.api.Test;
import dev.qhc99.centralibj.utils.tuples.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConvexHullTest{
  List<Tuple<Integer, Integer>> points = new ArrayList<>(13);
  Set<Tuple<Integer, Integer>> convexHullAnswer = new HashSet<>(5);

  {
    points.add(new Tuple<>(217, 463));
    points.add(new Tuple<>(515, 395));
    points.add(new Tuple<>(320, 374));
    points.add(new Tuple<>(409, 373));
    points.add(new Tuple<>(113, 278));
    points.add(new Tuple<>(252, 226));
    points.add(new Tuple<>(320, 224));
    points.add(new Tuple<>(444, 188));
    points.add(new Tuple<>(54, 222));
    points.add(new Tuple<>(140, 167));
    points.add(new Tuple<>(355, 80));
    points.add(new Tuple<>(515, 45));
    points.add(new Tuple<>(149, 90));
    /*
    [217,463]
    [515,395]
    [320,374]
    [409,373]
    [113,278]
    [252,226]
    [320,224]
    [444,188]
    [54,222]
    [140,167]
    [355,80]
    [515,45]
    [149,90]
    * */

    convexHullAnswer.add(new Tuple<>(515, 45));
    convexHullAnswer.add(new Tuple<>(515, 395));
    convexHullAnswer.add(new Tuple<>(217, 463));
    convexHullAnswer.add(new Tuple<>(54, 222));
    convexHullAnswer.add(new Tuple<>(149, 90));
  }

  @Test
  void GrahamScanTest(){
    var res = ConvexHull.GrahamScan(points, Tuple::first, Tuple::second);
    assertEquals(convexHullAnswer, new HashSet<>(res));
  }
}
