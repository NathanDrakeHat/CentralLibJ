package Algorithms.graph;


import Algorithms.structures.FibonacciHeap;
import Algorithms.tools.MinHeap;

import static Algorithms.graph.BFS.*;
import static Algorithms.graph.DFS.*;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

// single source shortest path
public final class SSShortestPath {
    // general case algorithm: negative weight, cyclic
    public static <T> boolean algorithmBellmanFord(LinkedGraph<BFSVertex<T>> graph, BFSVertex<T> s) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(graph);
        initializeSingleSource(graph, s);
        int vertices_count = graph.getVerticesCount();
        var edges = graph.getAllEdges();
        for (int i = 1; i < vertices_count; i++) {
            for (var edge : edges) {
                relax(edge);
            }
        }
        for (var edge : edges) {
            if (edge.getLaterVertex().distance > edge.getFormerVertex().distance + edge.getWeight()) {
                return false;
            }
        }
        return true;
    }

    private static <T> void initializeSingleSource(LinkedGraph<BFSVertex<T>> G, BFSVertex<T> s) {
        var vertices = G.getAllVertices();
        for (var v : vertices) {
            v.distance = Double.POSITIVE_INFINITY;
            v.parent = null;
            if (s == v) {
                s.distance = 0;
            }
        }
    }

    private static <T> void relax(LinkedGraph.Edge<BFSVertex<T>> edge) {
        var weight = edge.getWeight();
        var u = edge.getFormerVertex();
        var v = edge.getLaterVertex();
        var sum = u.distance + weight;
        if (v.distance > sum) {
            v.distance = sum;
            v.parent = u;
        }
    }

    // shortest paths of directed acyclic Algorithms.graph
    public static <T>
    LinkedGraph<BFSVertex<T>> shortestPathOfDAG(LinkedGraph<DFSVertex<BFSVertex<T>>> DFS_Linked_graph,
                                                LinkedGraph<BFSVertex<T>> BFS_Linked_graph,
                                                BFSVertex<T> s) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(DFS_Linked_graph);
        Objects.requireNonNull(BFS_Linked_graph);
        var DFS_list = topologicalSort(DFS_Linked_graph);
        initializeSingleSource(BFS_Linked_graph, s);
        DFS_list.sort((d1, d2) -> d2.finish - d1.finish);
        var BFS_list = DFS_list.stream().map(DFSVertex::getContent).collect(Collectors.toList());
        for (var u : BFS_list) {
            var u_edges = BFS_Linked_graph.getEdgesAt(u);
            for (var edge : u_edges) {
                relax(edge);
            }
        }
        return BFS_Linked_graph;
    }


    // non-negative weight
    public enum Heap {
        FIBONACCI, MIN_HEAP
    }

    public static <T> void algorithmDijkstra(LinkedGraph<BFSVertex<T>> G, BFSVertex<T> s, Heap type) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(G);
        Objects.requireNonNull(type);
        if (type == Heap.FIBONACCI) {
            algorithmDijkstraWithFibonacciHeap(G, s);
        }
        else {
            algorithmDijkstraWithMinHeap(G, s);
        }
    }

    // fibonacci heap, time complexity: O(V^2*lgV + V*E)
    private static <T> void algorithmDijkstraWithFibonacciHeap(LinkedGraph<BFSVertex<T>> G, BFSVertex<T> s) {
        initializeSingleSource(G, s);
        var vertices = G.getAllVertices();
        FibonacciHeap<Double,BFSVertex<T>> Q = new FibonacciHeap<>(Comparator.comparingDouble(a->a));
        for (var vertex : vertices) {
            Q.insert(vertex.distance, vertex);
        }
        while (Q.count() > 0) {
            var u = Q.extractMin();
            var u_edges = G.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                var original = v.distance;
                relax(edge);
                if (v.distance < original) {
                    Q.decreaseKey(v, v.distance);
                }
            }
        }
    }

    // min heap, time complexity: O(V*E*lgV)
    private static <T> void algorithmDijkstraWithMinHeap(LinkedGraph<BFSVertex<T>> G, BFSVertex<T> s) {
        initializeSingleSource(G, s);
        var vertices = G.getAllVertices();
        MinHeap<BFSVertex<T>> Q = new MinHeap<>(vertices, BFSVertex::getDistance);
        while (Q.length() > 0) {
            var u = Q.extractMin();
            var u_edges = G.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                var original = v.distance;
                relax(edge);
                if (v.distance < original) {
                    Q.decreaseKey(v, v.distance);
                }
            }
        }
    }
}
