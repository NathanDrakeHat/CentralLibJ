package org.nathan.algs4;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PointSET{
  Set<Point2D> set = new HashSet<>();

  public PointSET(){

  }

  public boolean isEmpty(){
    return set.isEmpty();
  }


  public int size(){
    return set.size();
  }

  public void insert(Point2D p){
    if(p == null){
      throw new IllegalArgumentException();
    }
    set.add(p);
  }

  public boolean contains(Point2D p){
    return set.contains(p);
  }

  public void draw(){
    for(var p : set){
      p.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect){
    if(rect == null){
      throw new IllegalArgumentException();
    }
    List<Point2D> res = new ArrayList<>(size());
    for(var p : set){
      if(rect.contains(p)){
        res.add(p);
      }
    }
    return res;
  }

  public Point2D nearest(Point2D p){
    if(p == null){
      throw new IllegalArgumentException();
    }
    double min = Double.MAX_VALUE;
    Point2D min_p = null;
    for(var sp : set){
      var d = sp.distanceTo(p);
      if(d < min){
        min = d;
        min_p = sp;
      }
    }
    return min_p;
  }

}
