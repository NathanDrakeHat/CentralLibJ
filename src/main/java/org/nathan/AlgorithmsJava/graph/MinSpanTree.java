package org.nathan.AlgorithmsJava.graph;

import org.nathan.AlgorithmsJava.structures.DisjointSet;
import org.nathan.AlgorithmsJava.structures.FibonacciHeap;
import org.nathan.AlgorithmsJava.tools.MinHeap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

// minimum spanning tree
public final class MinSpanTree {
    public static <T> Set<LinkedGraph.Edge<KruskalVertex<T>>> algorithmOfKruskal(@NotNull LinkedGraph<KruskalVertex<T>> graph) {
        Set<LinkedGraph.Edge<KruskalVertex<T>>> res = new HashSet<>();
        var edges_set = graph.getAllEdges();
        var edges_list = new ArrayList<>(edges_set);
        edges_list.sort(Comparator.comparingDouble(LinkedGraph.Edge::getWeight));
        for (var edge : edges_list) {
            var v1 = edge.getFormerVertex();
            var v2 = edge.getLaterVertex();
            if (DisjointSet.findSet(v1) != DisjointSet.findSet(v2)) {
                res.add(edge);
                DisjointSet.union(v1, v2);
            }
        }
        return res;
    }

    public static <T> void algorithmOfPrimWithFibonacciHeap(@NotNull LinkedGraph<PrimVertex<T>> graph,
                                                            @NotNull PrimVertex<T> r) {
        FibonacciHeap<Double, PrimVertex<T>> Q = new FibonacciHeap<>(Comparator.comparingDouble(a -> a));
        var vertices = graph.getAllVertices();
        for (var u : vertices) {
            if (u != r) {
                u.key = Double.POSITIVE_INFINITY;
            }
            else {
                u.key = 0.0;
            }
            Q.insert(u.key, u);
            u.parent = null;
        }
        while (Q.count() > 0) {
            var u = Q.extractMin();
            var u_edges = graph.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                if (Q.contains(v) && edge.getWeight() < v.key) {
                    v.parent = u;
                    v.key = edge.getWeight();
                    Q.decreaseKey(v, v.key);
                }
            }
        }
    }

    public static <T> void algorithmOfPrimWithMinHeap(@NotNull LinkedGraph<PrimVertex<T>> graph,
                                                      @NotNull PrimVertex<T> r) {
        var vertices = graph.getAllVertices();
        for (var u : vertices) {
            if (u != r) {
                u.key = Double.POSITIVE_INFINITY;
            }
            else {
                u.key = 0.0;
            }
            u.parent = null;
        }
        MinHeap<PrimVertex<T>> Q = new MinHeap<>(vertices, PrimVertex::getKey);
        while (Q.length() > 0) {
            var u = Q.extractMin();
            var u_edges = graph.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                if (Q.contains(v) && edge.getWeight() < v.key) {
                    v.parent = u;
                    v.key = edge.getWeight();
                    Q.update(v, v.key);
                }
            }
        }
    }

    public static class KruskalVertex<V> implements DisjointSet<KruskalVertex<V>> {
        @NotNull private final V content;
        private int rank = 0;
        private KruskalVertex<V> parent = this;

        public KruskalVertex(@NotNull V n) {
            content = n;
        }

        public @NotNull V getContent() {
            return content;
        }


        @Override
        public int getRank() {
            return rank;
        }

        @Override
        public void setRank(int rank) {
            this.rank = rank;
        }

        @Override
        public KruskalVertex<V> getParent() {
            return parent;
        }

        @Override
        public void setParent(KruskalVertex<V> r) {
            this.parent = r;
        }

        @Override
        public String toString() {
            return String.format("KruskalVertex: %s", content.toString());
        }

    }

    public static class PrimVertex<V> {
        @NotNull private final V content;
        PrimVertex<V> parent;
        private double key = 0;

        public PrimVertex(@NotNull V name) {
            this.content = name;
        }

        public @NotNull V getContent() {
            return content;
        }

        public double getKey() {
            return key;
        }

        @Override
        public String toString() {
            return String.format("PrimVertex: (%s)", content.toString());
        }
    }
}