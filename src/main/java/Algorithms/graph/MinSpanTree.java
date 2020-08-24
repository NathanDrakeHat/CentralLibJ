package Algorithms.graph;

import Algorithms.structures.DisjointSet;
import Algorithms.structures.FibonacciHeap;
import Algorithms.tools.MinHeap;

import java.util.*;

// minimum spanning tree
public final class MinSpanTree {
    public static class KruskalVertex<V> implements DisjointSet<KruskalVertex<V>> {
        private final V content;
        private int rank = 0;
        private KruskalVertex<V> parent = this;

        public KruskalVertex(V n) {
            Objects.requireNonNull(n);
            content = n;
        }

        public V getContent() {
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

    public static <T> Set<LinkedGraph.Edge<KruskalVertex<T>>> algorithmOfKruskal(LinkedGraph<KruskalVertex<T>> graph) {
        Objects.requireNonNull(graph);
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

    public static class PrimVertex<V> {
        private final V content;
        PrimVertex<V> parent;
        private double key = 0;

        public PrimVertex(V name) {
            Objects.requireNonNull(name);
            this.content = name;
        }

        public V getContent() {
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

    public static <T> void algorithmOfPrimWithFibonacciHeap(LinkedGraph<PrimVertex<T>> graph, PrimVertex<T> r) {
        Objects.requireNonNull(r);
        Objects.requireNonNull(graph);
        FibonacciHeap<Double,PrimVertex<T>> Q = new FibonacciHeap<>(Comparator.comparingDouble(a->a));
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

    public static <T> void algorithmOfPrimWithMinHeap(LinkedGraph<PrimVertex<T>> graph, PrimVertex<T> r) {
        Objects.requireNonNull(r);
        Objects.requireNonNull(graph);
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
}