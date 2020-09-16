package org.nathan.AlgorithmsJava.graph;

import org.jetbrains.annotations.NotNull;

import java.util.*;

// breath first search
public final class BFS {
    public static <T> void breathFirstSearch(@NotNull LinkedGraph<BFSVertex<T>> G,@NotNull  BFSVertex<T> s) {
        var vs = G.getAllVertices();
        for (var v : vs) {
            if (!v.equals(s)) {
                v.color = COLOR.WHITE;
                v.distance = Double.POSITIVE_INFINITY;
                v.parent = null;
            }
        }
        s.color = COLOR.GRAY;
        s.distance = 0;
        s.parent = null;
        Queue<BFSVertex<T>> Q = new LinkedList<>();
        Q.add(s);
        while (!Q.isEmpty()) {
            var u = Q.remove();
            var u_edges = G.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                if (v.color == COLOR.WHITE) {
                    v.color = COLOR.GRAY;
                    v.distance = u.distance + 1;
                    v.parent = u;
                    Q.add(v);
                }
            }
            u.color = COLOR.BLACK;
        }
    }

    public static <T> List<T> getPath(@NotNull BFSVertex<T> s, @NotNull BFSVertex<T> v) {
        List<T> t = new ArrayList<>();
        traverse(s, v, t);
        int idx = 0;
        List<T> res = new ArrayList<>(t.size());
        for (var i : t) {
            res.add(idx++, i);
        }
        return res;
    }

    private static <T> void traverse(BFSVertex<T> s, BFSVertex<T> v, List<T> res) {
        if (v == s) {
            res.add(s.content);
        }
        else if (v.parent != null) {
            traverse(s, v.parent, res);
            res.add(v.content);
        }
    }

    enum COLOR {WHITE, GRAY, BLACK}

    public static class BFSVertex<V> {
        private final V content;
        BFSVertex<V> parent;
        double distance; // d
        private COLOR color;

        public BFSVertex(V name) {
            Objects.requireNonNull(name);
            this.content = name;
        }

        BFSVertex() {
            content = null;
        }

        public V getContent() {
            return content;
        }

        public BFSVertex<V> getParent() {
            return parent;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            return String.format("BFS.Vertex: (%s)", content != null ? content.toString() : "()");
        }
    }
}
