package algorithms.graph;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// single source shortest path
public final class SSSP {
    // general case algorithm: negative weight, cyclic
    public static <T> boolean algorithmBellmanFord(Graph<BFS.Vertex<T>> graph, BFS.Vertex<T> s){
        initializeSingleSource(graph, s);
        int len = graph.getAllVertices().toArray().length;
        for(int i = 1; i < len; i++){
            for(var edge : graph.getAllEdges()){
                relax(edge.getFormerVertex(), edge.getLaterVertex(), graph);
            }
        }
        for(var edge : graph.getAllEdges()){
            if(edge.getLaterVertex().d > edge.getFormerVertex().d + graph.computeWeight(edge)){
                return false;}
        }
        return true;
    }
    private static <T> void initializeSingleSource(Graph<BFS.Vertex<T>> G, BFS.Vertex<T> s){
        for(var v : G.getAllVertices()){
            v.d = Double.POSITIVE_INFINITY;
            v.parent = null;
            if(s.equals(v)) s = v;
        }
        s.d = 0;
    }
    private static <T> void relax(BFS.Vertex<T> u, BFS.Vertex<T> v, Graph<BFS.Vertex<T>> G){
        var weight = G.computeWeight(u,v);
        if(v.d > u.d + weight){
            v.d = u.d + weight;
            v.parent = u;
        }
    }

    // shortest paths of directed acyclic graph
    public static <T> Graph<BFS.Vertex<T>> shortestPathOfDAG(Graph<DFS.Vertex<BFS.Vertex<T>>> DFS_graph, BFS.Vertex<T> s){
        DFS.topologicalSort(DFS_graph);
        var BFS_graph = Graph.convert(DFS_graph, DFS.Vertex::getContent);
        initializeSingleSource(BFS_graph, s);
        var DFS_vertices = DFS_graph.getAllVertices();
        List<DFS.Vertex<BFS.Vertex<T>>> topological_order_list = new ArrayList<>(DFS_vertices);
        topological_order_list.sort((d1,d2)->d2.f-d1.f);
        for(var vertex : topological_order_list){
            var u = vertex.getContent();
            for(var neighbor : DFS_graph.getNeighborsAt(vertex)){
                var v = neighbor.getContent();
                relax(u,v,BFS_graph);
            }
        }
        return BFS_graph;
    }
}
