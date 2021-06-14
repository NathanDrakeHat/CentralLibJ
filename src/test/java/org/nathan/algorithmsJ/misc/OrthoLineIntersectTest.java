package org.nathan.algorithmsJ.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OrthoLineIntersectTest{
  static class Point extends Tuple<Integer,Integer>{
    public Point(Integer f, Integer s){
      super(f, s);
    }
  }

  static class Line extends Tuple<Point, Point>{

    public Line(Point f, Point s){
      super(f, s);
    }
  }

  List<Line> linesData = new ArrayList<>();
  Map<Line, List<Line>> intersectAnswer = new HashMap<>();
  {
    List<Point> pointsData = new ArrayList<>();
    pointsData.add(new Point(43,448-395));
    pointsData.add(new Point(486,448-395));

    pointsData.add(new Point(82,448-285));
    pointsData.add(new Point(301,448-285));

    pointsData.add(new Point(115,448-240));
    pointsData.add(new Point(189,448-240));

    pointsData.add(new Point(157,448-120));
    pointsData.add(new Point(401,448-120));

    pointsData.add(new Point(259,448-186));
    pointsData.add(new Point(259,448-326));

    pointsData.add(new Point(466,448-377));
    pointsData.add(new Point(466,448-434));

    pointsData.add(new Point(493,448-313));
    pointsData.add(new Point(613,448-313));

    pointsData.add(new Point(400,448-269));
    pointsData.add(new Point(644,448-269));

    pointsData.add(new Point(314,448-208));
    pointsData.add(new Point(565,448-208));

    pointsData.add(new Point(494,448-100));
    pointsData.add(new Point(593,448-100));

    pointsData.add(new Point(532,448-42));
    pointsData.add(new Point(532,448-221));

    pointsData.add(new Point(691,448-71));
    pointsData.add(new Point(691,448-421));

    for(int i = 0; i < 24; i+=2){
      linesData.add(new Line(pointsData.get(i), pointsData.get(i+1)));
    }
    intersectAnswer.put(linesData.get(4), List.of(linesData.get(1)));
    intersectAnswer.put(linesData.get(5), List.of(linesData.get(0)));
    intersectAnswer.put(linesData.get(10), List.of(linesData.get(8), linesData.get(9)));
  }
  
  @Test
  void intersectsTest(){
    var res =
            OrthoLineIntersect.intersects(linesData, l->new Tuple<>(l.first(),l.second()), Tuple::first, Tuple::second);
    assertEquals(intersectAnswer, res);
  }
}