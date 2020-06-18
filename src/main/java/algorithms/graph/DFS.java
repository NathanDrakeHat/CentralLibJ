package algorithms.graph;

import java.util.ArrayList;
import java.util.List;

public final class DFS {
    // depth first search
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class Vertex<V>{
        Vertex<V> parent;
        private COLOR color;
        int discover; //d
        int finish; // f
        private final V content;
        private final String string;
        private final int hash_code;

        public Vertex(V name){
            this.content = name;
            string = String.format("DFS.Vertex: (%s)",content.toString());
            hash_code = string.hashCode();
        }

        public V getContent() {return content;}

        public Vertex<V> getParent() { return parent; }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_vertex){
            if(other_vertex == this)return true;
            else if(!(other_vertex instanceof Vertex)) return false;
            else return content.equals(((Vertex<V>) other_vertex).content);
        }

        @Override
        public int hashCode(){ return hash_code; }

        @Override
        public String toString(){ return string; }
    }

    public static <T> void depthFirstSearch(Graph<Vertex<T>> G) {
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
    private static <T > int DFSVisit(Graph<Vertex<T>> G, Vertex<T> u, int time){
        time++;
        u.discover = time;
        u.color = COLOR.GRAY;
        for(var v : G.getNeighborsAt(u)){
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

    public static <T> List<Vertex<T>> topologicalSort(Graph<Vertex<T>> G){
        depthFirstSearch(G);
        List<Vertex<T>> l = new ArrayList<>(G.getAllVertices());
        l.sort((o1, o2) -> o2.finish - o1.finish); // descend order
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
                new_graph.setNeighbor(n,v);
            }
        }
        return new_graph;
    }
}