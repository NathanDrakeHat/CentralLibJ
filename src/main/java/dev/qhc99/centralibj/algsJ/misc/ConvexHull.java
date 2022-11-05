package dev.qhc99.centralibj.algsJ.misc;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.ToDoubleFunction;

public  class ConvexHull {
    public static <E> List<E> GrahamScan(
            List<E> points,
            ToDoubleFunction<E> getX,
            ToDoubleFunction<E> getY) {
        if (points.size() <= 3) {
            return points;
        }
        var start = points.get(0);
        int start_idx = 0;
        for (var i = 0; i < points.size(); i++) {
            var p = points.get(i);
            if (getY.applyAsDouble(start) > getY.applyAsDouble(p)) {
                start = p;
                start_idx = i;
            }
        }

        final var final_start = start;
        swap(points, start_idx, points.size() - 1);
        points.remove(points.size() - 1);
        points.sort((a, b) -> {
            var comp_a = ccw(final_start, a, b, getX, getY);
            if (comp_a == 0) {
                var comp_d = distance(a, final_start, getX, getY) - distance(b, final_start, getX, getY);
                return comp_d < 0 ? -1 : comp_d > 0 ? 1 : 0;
            }
            else return -comp_a < 0 ? -1 : -comp_a > 0 ? 1 : 0;
        });
        List<E> temp = new ArrayList<>(points.size() + 1);
        temp.add(start);
        temp.addAll(points);
        points = temp;
        int s = points.size() - 1;
        while (s >= 0 && ccw(start, points.get(s), points.get(points.size() - 1), getX, getY) == 0) {
            s--;
        }
        s++;

        for (int e = points.size() - 1; s < e; e--, s++) {
            swap(points, s, e);
        }

        Deque<E> points_stack = new ArrayDeque<>();

        points_stack.addLast(points.get(0));
        points_stack.addLast(points.get(1));


        for (int i = 2; i < points.size(); i++) {
            var p = points.get(i);
            var last = points_stack.removeLast();
            while (!points_stack.isEmpty() && ccw(points_stack.getLast(), last, p, getX, getY) < 0) {
                last = points_stack.removeLast();
            }
            points_stack.addLast(last);
            points_stack.addLast(p);
        }

        List<E> res = new ArrayList<>(points_stack.size());
        res.addAll(points_stack);
        return res;
    }

    private static <E> void swap(List<E> points, int i, int j) {
        var t = points.get(i);
        points.set(i, points.get(j));
        points.set(j, t);
    }

    private static <E> double distance(E a, E b, ToDoubleFunction<E> getX, ToDoubleFunction<E> getY) {
        double ax = getX.applyAsDouble(a);
        double ay = getY.applyAsDouble(a);

        double bx = getX.applyAsDouble(b);
        double by = getY.applyAsDouble(b);
        return Math.pow(ax - bx, 2) + Math.pow(ay - by, 2);
    }

    private static <E> double ccw(E a, E b, E c, ToDoubleFunction<E> getX, ToDoubleFunction<E> getY) {
        double ax = getX.applyAsDouble(a);
        double ay = getY.applyAsDouble(a);

        double bx = getX.applyAsDouble(b);
        double by = getY.applyAsDouble(b);

        double cx = getX.applyAsDouble(c);
        double cy = getY.applyAsDouble(c);
        // (b[0] - a[0]) * (c[1] - b[1]) - (b[1] - a[1]) * (c[0] - b[0]);
        return (bx - ax) * (cy - by) - (by - ay) * (cx - bx);
    }
}
