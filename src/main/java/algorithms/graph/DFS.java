package algorithms.graph;

import tools.Graph;
import java.util.ArrayList;
import java.util.List;

public class DFS {
    // depth first search
    enum COLOR{ WHITE, GRAY, BLACK}

    public static class Vertex implements Comparable<Vertex>{
        public Vertex parent;
        private COLOR color;
        public int d; //discovered time
        public int f; // finished time
        private final String name; // this could changed into generic

        public Vertex(String name){ this.name = name; }

        public String getName() {return name;}

        public boolean equals(Vertex other){ return name.equals(other.getName()); }

        @Override public int hashCode(){ return name.hashCode(); }

        @Override public String toString(){ return name; }

        @Override public int compareTo(Vertex other) {return this.name.compareTo(other.name); }
    }

    public static void depthFirstSearch(Graph<Vertex> G) {
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
    private static int depthFirstSearchVisit(Graph<Vertex> G, Vertex u, int time){
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

    public static List<Vertex> topologicalSort(Graph<Vertex> G){
        depthFirstSearch(G);
        List<Vertex> l = new ArrayList<>(G.getAllVertices());
        l.sort((o1, o2) -> o2.f - o1.f); // descend order
        return l;
    }

    public static void stronglyConnectedComponents(Graph<Vertex> G){
        var l = topologicalSort(G);
        var G_T = transposeGraph(G);
        depthFirstSearchWithOrder(G_T, l);
    }
    private static void depthFirstSearchWithOrder(Graph<Vertex> G, List<Vertex> order){
        for (var v : G.getAllVertices()) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for(var v : order){
            if(v.color == COLOR.WHITE){ time = depthFirstSearchVisit(G, v, time); }
        }
    }
    private static Graph<Vertex> transposeGraph(Graph<Vertex> graph){
        var new_graph = new Graph<Vertex>();
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