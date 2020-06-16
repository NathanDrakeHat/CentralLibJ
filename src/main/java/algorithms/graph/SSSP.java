package algorithms.graph;


// single source shortest path
public final class SSSP {
    // general case algorithm
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

}
