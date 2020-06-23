package graph;


import structures.FibonacciHeap;
import tools.MinHeap;

import java.util.Objects;
import java.util.stream.Collectors;

// single source shortest path
public final class SSSP {
    // general case algorithm: negative weight, cyclic
    public static <T> boolean algorithmBellmanFord(Graph<BFS.BFSVertex<T>> graph, BFS.BFSVertex<T> s){
        Objects.requireNonNull(s);
        Objects.requireNonNull(graph);
        initializeSingleSource(graph, s);
        int vertices_count = graph.getVerticesCount();
        var edges = graph.getAllEdges();
        for(int i = 1; i < vertices_count; i++){
            for(var edge : edges){
                relax(edge);
            }
        }
        for(var edge : edges){
            if(edge.getLaterVertex().distance > edge.getFormerVertex().distance + edge.getWeight()){
                return false;}
        }
        return true;
    }
    private static <T> void initializeSingleSource(Graph<BFS.BFSVertex<T>> G, BFS.BFSVertex<T> s){
        var vertices = G.getAllVertices();
        for(var v : vertices){
            v.distance = Double.POSITIVE_INFINITY;
            v.parent = null;
            if(s.equals(v)) s = v;
        }
        s.distance = 0;
    }
    private static <T> void relax(Graph.Edge<BFS.BFSVertex<T>> edge){
        var weight = edge.getWeight();
        var u = edge.getFormerVertex();
        var v = edge.getLaterVertex();
        var sum = u.distance + weight;
        if(v.distance > sum){
            v.distance = sum;
            v.parent = u;
        }
    }

    // shortest paths of directed acyclic graph
    public static <T>
    Graph<BFS.BFSVertex<T>> shortestPathOfDAG(Graph<DFS.DFSVertex<BFS.BFSVertex<T>>> DFS_graph,
                                                    Graph<BFS.BFSVertex<T>> BFS_graph,
                                                    BFS.BFSVertex<T> s){
        Objects.requireNonNull(s);
        Objects.requireNonNull(DFS_graph);
        Objects.requireNonNull(BFS_graph);
        var DFS_list = DFS.topologicalSort(DFS_graph);
        initializeSingleSource(BFS_graph, s);
        DFS_list.sort((d1,d2)->d2.finish -d1.finish);
        var BFS_list = DFS_list.stream().map(DFS.DFSVertex::getContent).collect(Collectors.toList());
        for(var u : BFS_list){
            var u_edges = BFS_graph.getEdgesAt(u);
            for(var edge : u_edges){
                relax(edge);
            }
        }
        return BFS_graph;
    }


    // non-negative weight,
    public enum HeapType{
        FIBONACCI, MIN_HEAP
    }
    public static <T> void algorithmDijkstra(Graph<BFS.BFSVertex<T>> G, BFS.BFSVertex<T> s, HeapType type){
        Objects.requireNonNull(s);
        Objects.requireNonNull(G);
        Objects.requireNonNull(type);
        if(type == HeapType.FIBONACCI)
            algorithmDijkstraWithFibonacciHeap(G, s);
        else
            algorithmDijkstraWithMinHeap(G,s);
    }
    // fibonacci heap, time complexity: O(V^2*lgV + V*E)
    private static <T> void algorithmDijkstraWithFibonacciHeap(Graph<BFS.BFSVertex<T>> G, BFS.BFSVertex<T> s){
        initializeSingleSource(G, s);
        var vertices = G.getAllVertices();
        FibonacciHeap<BFS.BFSVertex<T>> Q = new FibonacciHeap<>();
        for (var vertex : vertices) Q.insert(vertex.distance, vertex);
        while (Q.length() > 0) {
            var u = Q.extractMin();
            var u_edges = G.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                var original = v.distance;
                relax(edge);
                if (v.distance < original) Q.decreaseKey(v, v.distance);
            }
        }
    }
    // min heap, time complexity: O(V*E*lgV)
    private static <T> void algorithmDijkstraWithMinHeap(Graph<BFS.BFSVertex<T>> G, BFS.BFSVertex<T> s){
        initializeSingleSource(G, s);
        var vertices = G.getAllVertices();
        MinHeap<BFS.BFSVertex<T>> Q = new MinHeap<>(vertices, BFS.BFSVertex::getDistance);
        while (Q.length() > 0) {
            var u = Q.extractMin();
            var u_edges = G.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                var original = v.distance;
                relax(edge);
                if (v.distance < original) Q.decreaseKey(v, v.distance);
            }
        }
    }
}
