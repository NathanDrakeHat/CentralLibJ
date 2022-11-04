package dev.qhc99.centralibj.algsJ.misc;


import java.util.*;
import java.util.function.ToDoubleFunction;

public class ConvexHull {
    public static <E> List<E> GrahamScan(
            List<E> points,
            ToDoubleFunction<E> getX,
            ToDoubleFunction<E> getY) {
        if (points.size() <= 3) {
            return points;
        }
        List<E> ps = new ArrayList<>(points);
        var start = ps.get(0);
        for (var i = 0; i < ps.size(); i++) {
            var p = ps.get(i);
            if (getY.applyAsDouble(start) > getY.applyAsDouble(p)) {
                start = p;
            }
        }

        final var final_start = start;
        ps.sort((a,b)->{
            var comp_a = relativeAngle(final_start, a,getX,getY) - relativeAngle(final_start,b,getX,getY);
            if(comp_a == 0){
                var comp_d = distance(a,final_start, getX,getY) - distance(b,final_start, getX,getY);
                return comp_d < 0 ? - 1 : comp_d > 0 ? 1 : 0;
            }
            else return comp_a < 0 ? - 1 : comp_a > 0 ? 1 : 0;
        });
        // ignore empty body
        int s = ps.size() - 2;
        while (s >= 0 & ccw(start, ps.get(s), ps.get(ps.size()-1), getX, getY) == 0) {
            s--;
        }
        s++;

        for (int e = ps.size() - 1; e < s; e--, s++) {
            swap(ps,s,e);
        }

        Deque<E> points_stack = new ArrayDeque<>();
        points_stack.addLast(ps.get(0));
        points_stack.addLast(ps.get(1));


        for (int i = 2; i < ps.size(); i++) {
            var p = ps.get(i);
            var last = points_stack.removeLast();
            var ccw = ccw(points_stack.getLast(), last, p, getX, getY);
            if (ccw > 0) {
                points_stack.addLast(last);
                points_stack.addLast(p);
            }
            else if (ccw == 0) {
                points_stack.addLast(last);
            }
            else {
                do {
                    last = points_stack.removeLast();
                }
                while (ccw(points_stack.getLast(), last, p, getX, getY) < 0);
                points_stack.addLast(last);
                points_stack.addLast(p);
            }
        }

        List<E> res = new ArrayList<>(points_stack.size());
        res.addAll(points_stack);
        return res;
    }

    private static <E> void swap(List<E> points, int i, int j){
        var t=  points.get(i);
        points.set(i,points.get(j));
        points.set(j,t);
    }

    private static <E> double relativeAngle(E from, E to, ToDoubleFunction<E> getX, ToDoubleFunction<E> getY) {
        var angle = Math.atan2(getY.applyAsDouble(to) - getY.applyAsDouble(from),
                getX.applyAsDouble(to) - getX.applyAsDouble(from));
        if (angle < 0) {
            angle += Math.PI;
        }
        return angle;
    }

    private static <E> double distance(E a, E b, ToDoubleFunction<E> getX, ToDoubleFunction<E> getY) {
        double ax = getX.applyAsDouble(a);
        double ay = getY.applyAsDouble(a);

        double bx = getX.applyAsDouble(b);
        double by = getY.applyAsDouble(b);
        return Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));
    }

    private static <E> int ccw(E a, E b, E c, ToDoubleFunction<E> getX, ToDoubleFunction<E> getY) {
        double ax = getX.applyAsDouble(a);
        double ay = getY.applyAsDouble(a);

        double bx = getX.applyAsDouble(b);
        double by = getY.applyAsDouble(b);

        double cx = getX.applyAsDouble(c);
        double cy = getY.applyAsDouble(c);

        double area = (bx - ax) * (cy - ay) - (by - ay) * (cx - ax);
        if (area < 0) {
            return -1;
        }
        else if (area > 0) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
