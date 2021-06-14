package org.nathan.algorithmsJ.misc;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.ArrayList;
import java.util.List;

class OrthoLineIntersectTest{
  List<Tuple<Tuple<Integer,Integer>, Tuple<Integer,Integer>>> linesData;
  {
    List<Tuple<Integer, Integer>> pointsData = new ArrayList<>();
    pointsData.add(new Tuple<>(43,448-395));
    pointsData.add(new Tuple<>(486,448-395));

    pointsData.add(new Tuple<>(82,448-285));
    pointsData.add(new Tuple<>(301,448-285));

    pointsData.add(new Tuple<>(115,448-240));
    pointsData.add(new Tuple<>(189,448-240));

    pointsData.add(new Tuple<>(157,448-120));
    pointsData.add(new Tuple<>(401,448-120));

    pointsData.add(new Tuple<>(259,448-186));
    pointsData.add(new Tuple<>(259,448-326));

    pointsData.add(new Tuple<>(466,448-377));
    pointsData.add(new Tuple<>(466,448-434));

    pointsData.add(new Tuple<>(493,448-313));
    pointsData.add(new Tuple<>(613,448-313));

    pointsData.add(new Tuple<>(400,448-269));
    pointsData.add(new Tuple<>(644,448-269));

    pointsData.add(new Tuple<>(314,448-208));
    pointsData.add(new Tuple<>(565,448-208));

    pointsData.add(new Tuple<>(494,448-100));
    pointsData.add(new Tuple<>(593,448-100));

    pointsData.add(new Tuple<>(532,448-42));
    pointsData.add(new Tuple<>(532,448-221));

    pointsData.add(new Tuple<>(691,448-71));
    pointsData.add(new Tuple<>(691,448-421));

    for(int i = 0; i < 12; i++){
      linesData.add(new Tuple<>(pointsData.get(i), pointsData.get(i+1)));
    }
  }
  
  @Test
  void intersectsTest(){
  }
}