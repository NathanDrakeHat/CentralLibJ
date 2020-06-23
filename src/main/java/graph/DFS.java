package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DFS {
    // depth first search
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class DFSVertex<V>  {
        DFSVertex<V> parent;
        private COLOR color;
        int discover; //d
        int finish; // f
        private final V content;
        private final String string;
        private final int hash_code;

        public DFSVertex(V name){
            Objects.requireNonNull(name);
            this.content = name;
            string = String.format("DFS.Vertex: (%s)",content.toString());
            hash_code = string.hashCode();
        }

        public V getContent() {return content;}

        public DFSVertex<V> getParent() { return parent; }

        @Override
        public boolean equals(Object other_vertex){
            if(other_vertex == this)return true;
            else if(!(other_vertex instanceof DFSVertex)) return false;
            else return content.equals(((DFSVertex<?>) other_vertex).content);
        }

        @Override
        public int hashCode(){ return hash_code; }

        @Override
        public String toString(){ return string; }
    }

    public static <T> void depthFirstSearch(Graph<DFSVertex<T>> G) {
        var vertices = G.GetAllVertices();
        for (var v : vertices) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for(var v : vertices){
            if(v.color == COLOR.WHITE){
                time = DFSVisit(G, v, time);
            }
        }
    }
    private static <T > int DFSVisit(Graph<DFSVertex<T>> G, DFSVertex<T> u, int time){
        time++;
        u.discover = time;
        u.color = COLOR.GRAY;
        var u_edges = G.getEdgesAt(u);
        for(var edge : u_edges){
            var v =edge.getAnotherSide(u);
            if(v.color == COLOR.WHITE){
                v.parent = u;
                time = DFSVisit(G, v, time);
            }
        }
        u.color = COLOR.BLACK;
        time++;
        u.finish = time;
        return time;
    }

    public static <T> List<DFSVertex<T>> topologicalSort(Graph<DFSVertex<T>> G){
        depthFirstSearch(G);
        List<DFSVertex<T>> l = new ArrayList<>(G.GetAllVertices());
        l.sort((o1, o2) -> o2.finish - o1.finish); // descend order
        return l;
    }

    public static <T> void stronglyConnectedComponents(Graph<DFSVertex<T>> G){
        var l = topologicalSort(G);
        var G_T = transposeGraph(G);
        depthFirstSearchOrderly(G_T, l);
    }
    private static <T> void depthFirstSearchOrderly(Graph<DFSVertex<T>> G, List<DFSVertex<T>> order){
        var vertices = G.GetAllVertices();
        for (var v : vertices) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for(var v : order){
            if(v.color == COLOR.WHITE){ time = DFSVisit(G, v, time); }
        }
    }
    private static <T> Graph<DFSVertex<T>> transposeGraph(Graph<DFSVertex<T>> graph){
        var new_graph = new Graph<>(graph.GetAllVertices(), Graph.Direction.DIRECTED);
        var vertices = graph.GetAllVertices();
        for(var v : vertices){
            var edges = graph.getEdgesAt(v);
            for(var edge : edges){
                var n = edge.getAnotherSide(v);
                new_graph.setNeighbor(n,v);
            }
        }
        return new_graph;
    }
}