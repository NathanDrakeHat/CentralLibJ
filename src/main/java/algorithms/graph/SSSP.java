package algorithms.graph;


import tools.Graph;

// single source shortest path
public final class SSSP {
    public static <T extends Comparable<T>>
    void initializeSingleSource(Graph<BFS.Vertex<T>> G, BFS.Vertex<T> s){
        for(var v : G.getAllVertices()){
            v.d = Double.POSITIVE_INFINITY;
            v.parent = null;
        }
        s.d = 0;
    }

    public static <T extends Comparable<T>>
    void relax(Graph<BFS.Vertex<T>>.Edge e){
        
    }
}
