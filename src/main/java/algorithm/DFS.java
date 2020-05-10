package algorithm;

import tool.LinkedGraph;

public class DFS {
    // depth first search
    enum COLOR{ WHITE, GRAY, BLACK}

    public static class Vertex{
        private Vertex parent;
        private COLOR color;
        public int d; //discovered time
        public int f; // finished time
        private final Character name;

        Vertex(char name){ this.name = name; }

        public char getName() {return name;}

        public boolean equals(Vertex other){ return name == other.getName(); }

        public int hashCode(){
            return name.hashCode();
        }
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
//        int i = 0; // find u container
//        var t = G.getVertexAt(i);
//        while(!t.equals(u)){
//            t = G.getVertexAt(++i);
//        }
//
////        t = t.next;
////        while(t != null){ // search u adjacent vertex
//        for(var v : G.getNeighbors(t)) {
//            if (v.color == COLOR.WHITE) {
//                v.parent = u;
//                time = depthFirstSearchVisit(G, v, time);
//            }
//        }
////        }
        u.color = COLOR.BLACK;
        time++;
        u.f = time;
        return time;
    }

    //public static void typologicalSort(LinkedGraph G){
        // 1.call depthFirstSearch to compute f
        // 2.sort f in descend order
    //}
    //public static void stronglyConnectedComponents(LinkedGraph G){
        // 1.call depthFirstSearch on G to compute f
        // 2.compute G^T which is reverse directed G
        // 3.call depthFirstSearch on G^T in the order of decreasing f
        // 4.output forest formed by line 3
    //}
}
