package dev.qhc99.centralibj.algsJ.misc;

import org.junit.jupiter.api.Test;
import dev.qhc99.centralibj.utils.tuples.Tuple;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrthoLineIntersectTest{
  public static class Point extends Tuple<Integer, Integer>{
    public Point(Integer f, Integer s){
      super(f, s);
    }

    Integer x(){
      return first();
    }

    Integer y(){
      return second();
    }

    @Override
    public boolean equals(Object o){
      return super.equals(o);
    }

    @Override
    public int hashCode(){
      return super.hashCode();
    }

    @Override
    public String toString(){
      return super.toString();
    }
  }

  public static class Line extends Tuple<Point, Point>{

    public Line(Point f, Point s){
      super(f, s);
    }

    @Override
    public boolean equals(Object o){
      return super.equals(o);
    }

    @Override
    public String toString(){
      return super.toString();
    }

    @Override
    public int hashCode(){
      return super.hashCode();
    }
  }

  List<Line> linesData = new ArrayList<>();
  Map<Line, Set<Line>> intersectAnswer = new HashMap<>();

  {
    List<Point> pointsData = new ArrayList<>();
    pointsData.add(new Point(43, 448 - 395));
    pointsData.add(new Point(486, 448 - 395));

    pointsData.add(new Point(82, 448 - 285));
    pointsData.add(new Point(301, 448 - 285));

    pointsData.add(new Point(115, 448 - 240));
    pointsData.add(new Point(189, 448 - 240));

    pointsData.add(new Point(157, 448 - 120));
    pointsData.add(new Point(401, 448 - 120));

    pointsData.add(new Point(259, 448 - 186));
    pointsData.add(new Point(259, 448 - 326));

    pointsData.add(new Point(466, 448 - 377));
    pointsData.add(new Point(466, 448 - 434));

    pointsData.add(new Point(493, 448 - 313));
    pointsData.add(new Point(613, 448 - 313));

    pointsData.add(new Point(400, 448 - 269));
    pointsData.add(new Point(644, 448 - 269));

    pointsData.add(new Point(314, 448 - 208));
    pointsData.add(new Point(565, 448 - 208));

    pointsData.add(new Point(494, 448 - 100));
    pointsData.add(new Point(593, 448 - 100));

    pointsData.add(new Point(532, 448 - 42));
    pointsData.add(new Point(532, 448 - 221));

    pointsData.add(new Point(691, 448 - 71));
    pointsData.add(new Point(691, 448 - 421));

    for(int i = 0; i < 24; i += 2){
      linesData.add(new Line(pointsData.get(i), pointsData.get(i + 1)));
    }
    intersectAnswer.put(linesData.get(4), Set.of(linesData.get(1)));
    intersectAnswer.put(linesData.get(5), Set.of(linesData.get(0)));
    intersectAnswer.put(linesData.get(10), Set.of(linesData.get(8), linesData.get(9)));
  }

  @Test
  void intersectsTest(){
    var res =
            OrthoLineIntersect.intersects(linesData, l -> new Tuple<>(l.first(), l.second()), Tuple::first,
                    Tuple::second);
    assertEquals(intersectAnswer, res);
  }


  List<Line> cornerCaseData = new ArrayList<>();
  Map<Line, Set<Line>> cornerCaseAnswer = new HashMap<>();

  {
    cornerCaseData.add(new Line(new Point(-1, 0), new Point(0, 0)));
    cornerCaseData.add(new Line(new Point(-1, 1), new Point(0, 1)));
    cornerCaseData.add(new Line(new Point(-1, 2), new Point(0, 2)));
    cornerCaseData.add(new Line(new Point(0, 0), new Point(0, 5)));
    cornerCaseData.add(new Line(new Point(0, 3), new Point(1, 3)));
    cornerCaseData.add(new Line(new Point(0, 4), new Point(1, 4)));
    cornerCaseData.add(new Line(new Point(0, 5), new Point(1, 5)));

    cornerCaseAnswer.put(new Line(new Point(0, 0), new Point(0, 5)),
            Set.of(new Line(new Point(-1, 0), new Point(0, 0)),
                    new Line(new Point(-1, 1), new Point(0, 1)),
                    new Line(new Point(-1, 2), new Point(0, 2)),
                    new Line(new Point(0, 3), new Point(1, 3)),
                    new Line(new Point(0, 4), new Point(1, 4)),
                    new Line(new Point(0, 5), new Point(1, 5))));
  }

  @Test
  void cornerCaseTest(){
    var res =
            OrthoLineIntersect.intersects(cornerCaseData, l -> new Tuple<>(l.first(), l.second()), Tuple::first,
                    Tuple::second);
    assertEquals(cornerCaseAnswer, res);
  }

}