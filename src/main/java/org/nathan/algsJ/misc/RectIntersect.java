package org.nathan.algsJ.misc;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.tuples.Quaternion;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;


// TODO rectangle intersection
public class RectIntersect{
  public static @NotNull <R, L, P> Map<R, Set<R>> intersects(
          @NotNull List<R> rectangles,
          @NotNull Function<R, Quaternion<L, L, L, L>> toLines,
          @NotNull Function<L, Tuple<P, P>> toPoints,
          @NotNull ToDoubleFunction<P> getX,
          @NotNull ToDoubleFunction<P> getY){

    return null;
  }
}
