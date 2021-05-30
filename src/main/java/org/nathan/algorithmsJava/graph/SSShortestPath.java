package org.nathan.algorithmsJava.graph;


import org.jetbrains.annotations.NotNull;
import org.nathan.algorithmsJava.structures.FibonacciHeap;
import org.nathan.algorithmsJava.structures.MinHeap;

import java.util.Comparator;
import java.util.stream.Collectors;

import static org.nathan.algorithmsJava.graph.DFS.topologicalSort;

/**
 * single source shortest path
 */
public final class SSShortestPath{
    /**
     * general case algorithm: negative weight, cyclic
     *
     * @param graph graph
     * @param s     start
     * @param <T>   id
     * @return has shortest path
     */
    public static <T> boolean BellmanFord(@NotNull LinkedGraph<BFSVertex<T>> graph, @NotNull BFSVertex<T> s){
        initializeSingleSource(graph, s);
        int vertices_count = graph.verticesCount();
        var edges = graph.getAllEdges();
        for(int i = 1; i < vertices_count; i++){
            for(var edge : edges){
                relax(edge);
            }
        }
        for(var edge : edges){
            if(edge.laterVertex().distance > edge.formerVertex().distance + edge.getWeight()){
                return false;
            }
        }
        return true;
    }

    private static <T> void initializeSingleSource(LinkedGraph<BFSVertex<T>> G, BFSVertex<T> s){
        var vertices = G.getAllVertices();
        for(var v : vertices){
            v.distance = Double.POSITIVE_INFINITY;
            v.parent = null;
            if(s == v){
                s.distance = 0;
            }
        }
    }

    private static <T> void relax(GraphEdge<BFSVertex<T>> edge){
        var weight = edge.getWeight();
        var u = edge.formerVertex();
        var v = edge.laterVertex();
        var sum = u.distance + weight;
        if(v.distance > sum){
            v.distance = sum;
            v.parent = u;
        }
    }

    /**
     * shortest paths of directed acyclic graph
     *
     * @param <T>              id
     * @param BFS_Linked_graph linked graph with bfs vertex wrapper
     * @param s                start
     */
    public static <T> void ssDAG(@NotNull LinkedGraph<BFSVertex<T>> BFS_Linked_graph, @NotNull BFSVertex<T> s){
        var DFS_Linked_graph = new LinkedGraph<>(BFS_Linked_graph, DFSVertex::new);
        var DFS_list = topologicalSort(DFS_Linked_graph);
        initializeSingleSource(BFS_Linked_graph, s);
        DFS_list.sort((d1, d2) -> d2.finish - d1.finish);
        var BFS_list = DFS_list.stream().map(DFSVertex::getId).collect(Collectors.toList());
        for(var u : BFS_list){
            var u_edges = BFS_Linked_graph.getEdgesAt(u);
            for(var edge : u_edges){
                relax(edge);
            }
        }
    }


    /**
     * fibonacci heap, time complexity: O(V^2*lgV + V*E)
     *
     * @param G   graph
     * @param s   start
     * @param <T> id
     */
    public static <T> void DijkstraFibonacciHeap(@NotNull LinkedGraph<BFSVertex<T>> G, @NotNull BFSVertex<T> s){
        initializeSingleSource(G, s);
        var vertices = G.getAllVertices();
        FibonacciHeap<Double, BFSVertex<T>> Q = new FibonacciHeap<>(Comparator.comparingDouble(a -> a));
        for(var vertex : vertices){
            Q.insert(vertex.distance, vertex);
        }
        while(Q.count() > 0) {
            var u = Q.extractMin();
            var u_edges = G.getEdgesAt(u);
            for(var edge : u_edges){
                var v = edge.getAnotherSide(u);
                var original = v.distance;
                relax(edge);
                if(v.distance < original){
                    Q.decreaseKey(v, v.distance);
                }
            }
        }
    }

    /**
     * min heap, time complexity: O(V*E*lgV)
     *
     * @param G   graph
     * @param s   start
     * @param <T> id
     */
    public static <T> void DijkstraMinHeap(@NotNull LinkedGraph<BFSVertex<T>> G, @NotNull BFSVertex<T> s){
        initializeSingleSource(G, s);
        var vertices = G.getAllVertices();
        MinHeap<Double, BFSVertex<T>> Q = new MinHeap<>(vertices, BFSVertex::getDistance, Double::compare);
        while(Q.length() > 0) {
            var u = Q.extractMin();
            var u_edges = G.getEdgesAt(u);
            for(var edge : u_edges){
                var v = edge.getAnotherSide(u);
                var original = v.distance;
                relax(edge);
                if(v.distance < original){
                    Q.updateKey(v, v.distance);
                }
            }
        }
    }

}
