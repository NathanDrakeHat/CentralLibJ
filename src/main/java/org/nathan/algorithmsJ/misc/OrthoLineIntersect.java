package org.nathan.algorithmsJ.misc;

import org.jetbrains.annotations.NotNull;
import org.nathan.algorithmsJ.structures.OrderStatTree;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * orthogonal line segment intersection
 */
public class OrthoLineIntersect{
  /**
   * intersects of orthogonal lines which do not intersect at end
   * @param lines lines
   * @param toPoints get points of line
   * @param getX get x of point
   * @param getY get y of point
   * @param <L> Line type
   * @param <P> Point type
   * @return intersection result
   */
  public static @NotNull <L, P> Map<L, List<L>> intersects(
          @NotNull List<L> lines,
          @NotNull Function<L, Tuple<P, P>> toPoints,
          @NotNull ToDoubleFunction<P> getX,
          @NotNull ToDoubleFunction<P> getY){
    Map<P, L> pointsToLine = new HashMap<>();
    List<P> scanPointList = new ArrayList<>();
    Set<P> hPoints = new HashSet<>();
    Map<P, P> otherLineEnd = new HashMap<>();

    for(var line : lines){
      var point_pair = toPoints.apply(line);
      otherLineEnd.put(point_pair.first(), point_pair.second());
      otherLineEnd.put(point_pair.second(), point_pair.first());

      var x1 = getX.applyAsDouble(point_pair.first());
      var x2 = getX.applyAsDouble(point_pair.second());

      pointsToLine.put(point_pair.first(), line);
      scanPointList.add(point_pair.first());

      if(x1 != x2){
        pointsToLine.put(point_pair.second(), line);
        scanPointList.add(point_pair.second());

        hPoints.add(point_pair.first());
        hPoints.add(point_pair.second());
        if(getY.applyAsDouble(point_pair.first()) != getY.applyAsDouble(point_pair.second())){
          throw new IllegalArgumentException("lines are not orthogonal.");
        }
      }
    }

    scanPointList.sort(Comparator.comparing(getX::applyAsDouble));
    Set<P> inTree = new HashSet<>();
    OrderStatTree<Double, L> HYToHL_tree = new OrderStatTree<>(Double::compareTo);
    Map<L, List<L>> res = new HashMap<>();

    var funcAddIntersect = new Object(){
      void apply(P v_p){
        var vl = pointsToLine.get(v_p);
        var ap = otherLineEnd.get(v_p);
        var y1 = getY.applyAsDouble(ap);
        var y2 = getY.applyAsDouble(v_p);
        var hls = HYToHL_tree.keyRangeSearch(Math.min(y1, y2), Math.max(y1, y2));
        if(hls.size() > 0 && !res.containsKey(vl)){
          res.put(vl, hls.stream().map(Tuple::second).collect(Collectors.toList()));
        }
      }
    };

    var funcRmFromTree = new Object(){
      void apply(P h_p){
        inTree.remove(otherLineEnd.get(h_p));
        HYToHL_tree.deleteKey(getY.applyAsDouble(h_p));
      }
    };

    var funcAddToTree = new Object(){
      void apply(P h_p){
        inTree.add(h_p);
        HYToHL_tree.insertKV(getY.applyAsDouble(h_p), pointsToLine.get(h_p));
      }
    };

    for(int i = 0; i < scanPointList.size(); i++){
      var p = scanPointList.get(i);
      if(hPoints.contains(p)){
        // horizontal point
        if(inTree.contains(otherLineEnd.get(p))){
          funcRmFromTree.apply(p);
        }
        else{
          funcAddToTree.apply(p);
        }
      }
      else{
        // vertical point
        funcAddIntersect.apply(p);
      }
    }
    return res;
  }
}
