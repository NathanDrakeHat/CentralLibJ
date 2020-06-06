package algorithms;

import tools.LinkedGraph;

import java.util.ArrayList;
import java.util.List;

public class DFS {
    // depth first search
    enum COLOR{ WHITE, GRAY, BLACK}

    public static class Vertex{
        private Vertex parent;
        private COLOR color;
        public int d; //discovered time
        public int f; // finished time
        private final String name;

        public Vertex(String name){ this.name = name; }

        public String getName() {return name;}

        public boolean equals(Vertex other){ return name.equals(other.getName()); }

        public int hashCode(){
            return name.hashCode();
        }

        public String toString(){ return name; }
    }

    public static void depthFirstSearch(LinkedGraph<Vertex> G) {
        for (var v : G.getVertexes()) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for(var v : G.getVertexes()){
            if(v.color == COLOR.WHITE){
                time = depthFirstSearchVisit(G, v, time);
            }
        }
    }
    private static int depthFirstSearchVisit(LinkedGraph<Vertex> G, Vertex u, int time){
        time++;
        u.d = time;
        u.color = COLOR.GRAY;
        for(var v : G.getNeighbors(u)){
            if(v.color == COLOR.WHITE){
                v.parent = null;
                time = depthFirstSearchVisit(G, v, time);
            }
        }
        u.color = COLOR.BLACK;
        time++;
        u.f = time;
        return time;
    }

    public static List<Vertex> topologicalSort(LinkedGraph<Vertex> G){
        depthFirstSearch(G);
        List<Vertex> l = new ArrayList<>(G.getVertexes());
        l.sort((o1, o2) -> o2.f - o1.f); // descend order
        return l;
    }
    //public static void stronglyConnectedComponents(LinkedGraph G){
        // 1.call depthFirstSearch on G to compute f
        // 2.compute G^T which is reverse directed G
        // 3.call depthFirstSearch on G^T in the order of decreasing f
        // 4.output forest formed by line 3
    //}
}
