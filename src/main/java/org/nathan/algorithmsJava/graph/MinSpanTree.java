package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;
import org.nathan.algorithmsJava.structures.FibonacciHeap;
import org.nathan.algorithmsJava.structures.MinHeap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * minimum spanning tree
 */
public final class MinSpanTree {
    public static <T>
    @NotNull Set<LinkedGraph.Edge<KruskalVertex<T>>> Kruskal(@NotNull LinkedGraph<KruskalVertex<T>> graph) {
        Set<LinkedGraph.Edge<KruskalVertex<T>>> res = new HashSet<>();
        var edges_set = graph.getAllEdges();
        var edges_list = new ArrayList<>(edges_set);
        edges_list.sort(Comparator.comparingDouble(LinkedGraph.Edge::getWeight));
        for (var edge : edges_list) {
            var v1 = edge.getFormerVertex();
            var v2 = edge.getLaterVertex();
            if (v1.findGroupId() != v2.findGroupId()) {
                res.add(edge);
                v1.union(v2);
            }
        }
        return res;
    }

    public static <T> void PrimFibonacciHeap(@NotNull LinkedGraph<PrimVertex<T>> graph,
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

    public static <T> void PrimMinHeap(@NotNull LinkedGraph<PrimVertex<T>> graph,
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
        MinHeap<Double,PrimVertex<T>> Q = new MinHeap<>(vertices, PrimVertex::getKey,Double::compare);
        while (Q.length() > 0) {
            var u = Q.extractMin();
            var u_edges = graph.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                if (Q.contains(v) && edge.getWeight() < v.key) {
                    v.parent = u;
                    v.key = edge.getWeight();
                    Q.updateKey(v, v.key);
                }
            }
        }
    }

}