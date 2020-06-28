package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// depth first search
public final class DFS {
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

    public static <T> void depthFirstSearch(LinkedGraph<DFSVertex<T>> G) {
        Objects.requireNonNull(G);
        var vertices = G.getAllVertices();
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
    private static <T > int DFSVisit(LinkedGraph<DFSVertex<T>> G, DFSVertex<T> u, int time){
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

    public static <T> List<DFSVertex<T>> topologicalSort(LinkedGraph<DFSVertex<T>> G){
        Objects.requireNonNull(G);
        depthFirstSearch(G);
        List<DFSVertex<T>> l = new ArrayList<>(G.getAllVertices());
        l.sort((o1, o2) -> o2.finish - o1.finish); // descend order
        return l;
    }

    public static <T> void stronglyConnectedComponents(LinkedGraph<DFSVertex<T>> G){
        Objects.requireNonNull(G);
        var l = topologicalSort(G);
        var G_T = transposeGraph(G);
        depthFirstSearchOrderly(G_T, l);
    }
    private static <T> void depthFirstSearchOrderly(LinkedGraph<DFSVertex<T>> G, List<DFSVertex<T>> order){
        var vertices = G.getAllVertices();
        for (var v : vertices) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for(var v : order){
            if(v.color == COLOR.WHITE){ time = DFSVisit(G, v, time); }
        }
    }
    private static <T> LinkedGraph<DFSVertex<T>> transposeGraph(LinkedGraph<DFSVertex<T>> graph){
        var new_graph = new LinkedGraph<>(graph.getAllVertices(), LinkedGraph.Direction.DIRECTED);
        var vertices = graph.getAllVertices();
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