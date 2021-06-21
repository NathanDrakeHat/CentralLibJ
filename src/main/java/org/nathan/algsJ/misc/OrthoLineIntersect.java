package org.nathan.algsJ.misc;

import org.jetbrains.annotations.NotNull;
import org.nathan.algsJ.structures.OrderStatTree;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;


/**
 * orthogonal line segment intersection
 */
public class OrthoLineIntersect {
  /**
   * intersects of orthogonal lines which do not intersect at end
   *
   * @param lines    lines
   * @param toPoints get points of line
   * @param getX     get x of point
   * @param getY     get y of point
   * @param <L>      Line type
   * @param <P>      Point type
   * @return intersection result
   */
  public static @NotNull <L, P> Map<L, Set<L>> intersects(
          @NotNull List<L> lines,
          @NotNull Function<L, Tuple<P, P>> toPoints,
          @NotNull ToDoubleFunction<P> getX,
          @NotNull ToDoubleFunction<P> getY) {
    Map<Double, Set<L>> lineOfX = new HashMap<>(lines.size() * 2);
    List<Double> scanX = new ArrayList<>(lines.size() * 2);

    for (var line : lines) {
      var points_tuple = toPoints.apply(line);
      var x1 = getX.applyAsDouble(points_tuple.first());
      var x2 = getX.applyAsDouble(points_tuple.second());
      var y1 = getY.applyAsDouble(points_tuple.first());
      var y2 = getY.applyAsDouble(points_tuple.second());
      if (x1 == x2 && y1 == y2) {
        throw new IllegalArgumentException("line cannot be a point.");
      }

      if (lineOfX.containsKey(getX.applyAsDouble(points_tuple.first()))) {
        lineOfX.get(getX.applyAsDouble(points_tuple.first())).add(line);
      } else {
        Set<L> set = new HashSet<>();
        set.add(line);
        lineOfX.put(getX.applyAsDouble(points_tuple.first()), set);
      }
      scanX.add(getX.applyAsDouble(points_tuple.first()));

      if (x1 != x2) {
        if (lineOfX.containsKey(getX.applyAsDouble(points_tuple.second()))) {
          lineOfX.get(getX.applyAsDouble(points_tuple.second())).add(line);
        } else {
          Set<L> set = new HashSet<>();
          set.add(line);
          lineOfX.put(getX.applyAsDouble(points_tuple.second()), set);
        }
        scanX.add(getX.applyAsDouble(points_tuple.second()));

        if (y1 != y2) {
          throw new IllegalArgumentException("lines are not orthogonal.");
        }
      }
    }

    scanX = scanX.stream().sorted().distinct().toList();

    var funcIsVertical = new Object() {
      boolean apply(L line) {
        var points_tuple = toPoints.apply(line);
        var x1 = getX.applyAsDouble(points_tuple.first());
        var x2 = getX.applyAsDouble(points_tuple.second());
        return x1 == x2;
      }
    };

    var funcIsEnd = new Object() {
      boolean apply(Double x, L line) {
        var points_tuple = toPoints.apply(line);
        var x1 = getX.applyAsDouble(points_tuple.first());
        var x2 = getX.applyAsDouble(points_tuple.second());
        return x == Math.max(x1, x2);
      }
    };
    Map<L, Set<L>> res = new HashMap<>();
    Queue<L> h_rm_queue = new ArrayDeque<>();
    Queue<L> v_queue = new ArrayDeque<>();
    OrderStatTree<Double, Set<L>> HYToHL_tree = new OrderStatTree<>(Double::compareTo);

    for (var x : scanX) {
      for (var line : lineOfX.get(x)) {
        if (funcIsVertical.apply(line)) {
          v_queue.add(line);
        } else {
          if (funcIsEnd.apply(x, line)) {
            h_rm_queue.add(line);
          } else {
            var yp = getY.applyAsDouble(toPoints.apply(line).first());
            if (HYToHL_tree.containKey(yp)) {
              var l = HYToHL_tree.getValOfKey(yp);
              l.add(line);
            } else {
              Set<L> set = new HashSet<>();
              set.add(line);
              HYToHL_tree.insertKV(yp, set);
            }
          }
        }
      }

      while (!v_queue.isEmpty()) {
        var vl = v_queue.remove();
        var vly1 = getY.applyAsDouble(toPoints.apply(vl).first());
        var vly2 = getY.applyAsDouble(toPoints.apply(vl).second());
        var hls = HYToHL_tree.keyRangeSearch(Math.min(vly1, vly2), Math.max(vly1, vly2));
        for (var t : hls) {
          if (res.containsKey(vl)) {
            res.get(vl).addAll(t.second());
          } else {
            res.put(vl, new HashSet<>(t.second()));
          }

        }
      }
      while (!h_rm_queue.isEmpty()) {
        var hl = h_rm_queue.remove();
        var y = getY.applyAsDouble(toPoints.apply(hl).first());
        var set = HYToHL_tree.getValOfKey(y);
        if (set != null && set.size() <= 1) {
          HYToHL_tree.deleteKey(y);
        } else {
          set.remove(hl);
        }
      }
    }

    return res;
  }
}
