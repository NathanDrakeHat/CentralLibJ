package algorithms.graph;

import tools.Graph;
import java.util.ArrayList;
import java.util.List;

public class DFS {
    // depth first search
    enum COLOR{ WHITE, GRAY, BLACK}

    public static class Vertex<V extends Comparable<V>> implements Comparable<Vertex<V>>{
        public Vertex<V> parent;
        private COLOR color;
        public int d; //discovered time
        public int f; // finished time
        private final V name; // this could changed into generic

        public Vertex(V name){ this.name = name; }

        public V getName() {return name;}

        public boolean equals(Vertex<V> other){ return name.equals(other.getName()); }

        @Override public int hashCode(){ return name.hashCode(); }

        @Override public String toString(){ return name.toString(); }

        @Override public int compareTo(Vertex<V> other) {return this.name.compareTo(other.name); }
    }

    public static <T extends Comparable<T>> void depthFirstSearch(Graph<Vertex<T>> G) {
        for (var v : G.getAllVertices()) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for(var v : G.getAllVertices()){
            if(v.color == COLOR.WHITE){
                time = depthFirstSearchVisit(G, v, time);
            }
        }
    }
    private static <T extends Comparable<T>> int depthFirstSearchVisit(Graph<Vertex<T>> G, Vertex<T> u, int time){
        time++;
        u.d = time;
        u.color = COLOR.GRAY;
        for(var v : G.getNeighborsAt(u)){
            if(v.color == COLOR.WHITE){
                v.parent = u;
                time = depthFirstSearchVisit(G, v, time);
            }
        }
        u.color = COLOR.BLACK;
        time++;
        u.f = time;
        return time;
    }

    public static <T extends Comparable<T>> List<Vertex<T>> topologicalSort(Graph<Vertex<T>> G){
        depthFirstSearch(G);
        List<Vertex<T>> l = new ArrayList<>(G.getAllVertices());
        l.sort((o1, o2) -> o2.f - o1.f); // descend order
        return l;
    }

    public static <T extends Comparable<T>> void stronglyConnectedComponents(Graph<Vertex<T>> G){
        var l = topologicalSort(G);
        var G_T = transposeGraph(G);
        depthFirstSearchWithOrder(G_T, l);
    }
    private static <T extends Comparable<T>> void depthFirstSearchWithOrder(Graph<Vertex<T>> G, List<Vertex<T>> order){
        for (var v : G.getAllVertices()) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for(var v : order){
            if(v.color == COLOR.WHITE){ time = depthFirstSearchVisit(G, v, time); }
        }
    }
    private static <T extends Comparable<T>> Graph<Vertex<T>> transposeGraph(Graph<Vertex<T>> graph){
        var new_graph = new Graph<Vertex<T>>();
        for(var v : graph.getAllVertices()){
            var neighbors = graph.getNeighborsAt(v);
            for(var n : neighbors){
                if(!new_graph.hasVertex(n)) { new_graph.putVertex(n); }
                if(!new_graph.hasOneNeighbor(n, v)){ new_graph.addOneNeighbor(n, v); }
            }
        }
        return new_graph;
    }
}