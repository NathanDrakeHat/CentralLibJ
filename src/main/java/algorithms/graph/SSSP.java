package algorithms.graph;


import structures.FibonacciHeap;
import tools.MinHeap;

import java.util.stream.Collectors;

// single source shortest path
public final class SSSP {
    // general case algorithm: negative weight, cyclic
    public static <T> boolean algorithmBellmanFord(Graph<BFS.Vertex<T>> graph, BFS.Vertex<T> s){
        initializeSingleSource(graph, s);
        int len = graph.getVerticesCount();
        var edges = graph.getAllEdges();
        for(int i = 1; i < len; i++){
            for(var edge : edges){
                relax(edge.getFormerVertex(), edge.getLaterVertex(), graph);
            }
        }
        for(var edge : edges){
            if(edge.getLaterVertex().distance > edge.getFormerVertex().distance + graph.computeWeight(edge)){
                return false;}
        }
        return true;
    }
    private static <T> void initializeSingleSource(Graph<BFS.Vertex<T>> G, BFS.Vertex<T> s){
        for(var v : G.getAllVertices()){
            v.distance = Double.POSITIVE_INFINITY;
            v.parent = null;
            if(s.equals(v)) s = v;
        }
        s.distance = 0;
    }
    private static <T> void relax(BFS.Vertex<T> u, BFS.Vertex<T> v, Graph<BFS.Vertex<T>> G){
        var weight = G.computeWeight(u,v);
        var sum = u.distance + weight;
        if(v.distance > sum){
            v.distance = sum;
            v.parent = u;
        }
    }

    // shortest paths of directed acyclic graph
    public static <T>
    Graph<BFS.Vertex<T>> shortestPathOfDAG(Graph<DFS.Vertex<BFS.Vertex<T>>> DFS_graph,
                                           Graph<BFS.Vertex<T>> BFS_graph,
                                           BFS.Vertex<T> s){
        var DFS_list = DFS.topologicalSort(DFS_graph);
        initializeSingleSource(BFS_graph, s);
        DFS_list.sort((d1,d2)->d2.finish -d1.finish);
        var BFS_list = DFS_list.stream().map(DFS.Vertex::getContent).collect(Collectors.toList());
        for(var u : BFS_list){
            for(var v : BFS_graph.getNeighborsAt(u)){
                relax(u, v, BFS_graph);
            }
        }
        return BFS_graph;
    }

    // non-negative weight,
    // fibonacci heap time :O(V^2*lgV + V*E)
    // min heap time :O(V*E*lgV)
    public static <T> void algorithmDijkstraWithFibonacciHeap(Graph<BFS.Vertex<T>> G, BFS.Vertex<T> s){
        initializeSingleSource(G, s);
        var vertices = G.getAllVertices();
        FibonacciHeap<BFS.Vertex<T>> Q = new FibonacciHeap<>();
        for (var vertex : vertices) Q.insert(vertex.distance, vertex);
        while (Q.length() > 0) {
            var u = Q.extractMin();
            for (var v : G.getNeighborsAt(u)) {
                var original = v.distance;
                relax(u, v, G);
                if (v.distance < original) Q.decreaseKey(v, v.distance);
            }
        }
    }

    public static <T> void algorithmDijkstraWithMinHeap(Graph<BFS.Vertex<T>> G, BFS.Vertex<T> s){
        initializeSingleSource(G, s);
        var vertices = G.getAllVertices();
        MinHeap<BFS.Vertex<T>> Q = new MinHeap<>(vertices, BFS.Vertex::getDistance);
        while (Q.length() > 0) {
            var u = Q.extractMin();
            for (var v : G.getNeighborsAt(u)) {
                var original = v.distance;
                relax(u, v, G);
                if (v.distance < original) Q.decreaseKey(v, v.distance);
            }
        }
    }
}
