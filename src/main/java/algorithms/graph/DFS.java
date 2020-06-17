package algorithms.graph;

import java.util.ArrayList;
import java.util.List;

public final class DFS {
    // depth first search
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class Vertex<V>{
        public Vertex<V> parent;
        private COLOR color;
        public int d; //discovered time
        public int f; // finished time
        private final V content; // this could changed into generic

        public Vertex(V name){ this.content = name; }

        public V getContent() {return content;}

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_vertex){
            if(other_vertex == null) return false;
            else if(this.getClass().equals(other_vertex.getClass())){
                return content.equals(((DFS.Vertex<V>) other_vertex).content);
            }else return false;
        }

        @Override
        public int hashCode(){ return toString().hashCode(); }

        @Override
        public String toString(){
            return String.format("DFS.Vertex: (%s)",content.toString());
        }
    }

    public static <T> void depthFirstSearch(Graph<Vertex<T>> G) {
        for (var v : G.getAllVertices()) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for(var v : G.getAllVertices()){
            if(v.color == COLOR.WHITE){
                time = DFSVisit(G, v, time);
            }
        }
    }
    private static <T > int DFSVisit(Graph<Vertex<T>> G, Vertex<T> u, int time){
        time++;
        u.d = time;
        u.color = COLOR.GRAY;
        for(var v : G.getNeighborsAt(u)){
            if(v.color == COLOR.WHITE){
                v.parent = u;
                time = DFSVisit(G, v, time);
            }
        }
        u.color = COLOR.BLACK;
        time++;
        u.f = time;
        return time;
    }

    public static <T> List<Vertex<T>> topologicalSort(Graph<Vertex<T>> G){
        depthFirstSearch(G);
        List<Vertex<T>> l = new ArrayList<>(G.getAllVertices());
        l.sort((o1, o2) -> o2.f - o1.f); // descend order
        return l;
    }

    public static <T> void stronglyConnectedComponents(Graph<Vertex<T>> G){
        var l = topologicalSort(G);
        var G_T = transposeGraph(G);
        depthFirstSearchOrderly(G_T, l);
    }
    private static <T> void depthFirstSearchOrderly(Graph<Vertex<T>> G, List<Vertex<T>> order){
        for (var v : G.getAllVertices()) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for(var v : order){
            if(v.color == COLOR.WHITE){ time = DFSVisit(G, v, time); }
        }
    }
    private static <T> Graph<Vertex<T>> transposeGraph(Graph<Vertex<T>> graph){
        var new_graph = new Graph<>(graph.getAllVertices(),Graph.Direction.DIRECTED);
        for(var v : graph.getAllVertices()){
            var neighbors = graph.getNeighborsAt(v);
            for(var n : neighbors){
                if(!new_graph.hasVertex(n)) { new_graph.putVertex(n); }
                if(!new_graph.hasOneNeighbor(n, v)){ new_graph.setNeighbor(n, v); }
            }
        }
        return new_graph;
    }
}