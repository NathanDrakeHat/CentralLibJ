package graph;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    public static class Data{
        public static String names = "rstuvwxy";
        public static List<BFS.BFSVertex<Character>> makeVertexes(){
            List<BFS.BFSVertex<Character>> vs = new ArrayList<>(8);
            for(int i = 0; i < 8; i++){
                vs.add(i, new BFS.BFSVertex<>(names.charAt(i)));
            }
            return vs;
        }
        public static Graph<BFS.BFSVertex<Character>> makeGraph(List<BFS.BFSVertex<Character>> vs){
            var G = new Graph<>(vs, Graph.Direction.NON_DIRECTED);
            G.setNeighbor(vs.get(0), vs.get(1));
            G.setNeighbor(vs.get(0), vs.get(4));

            G.setNeighbor(vs.get(1), vs.get(5));

            G.setNeighbor(vs.get(2), vs.get(3));
            G.setNeighbor(vs.get(2), vs.get(5));
            G.setNeighbor(vs.get(2), vs.get(6));

            G.setNeighbor(vs.get(3), vs.get(6));

            G.setNeighbor(vs.get(5), vs.get(6));

            G.setNeighbor(vs.get(6), vs.get(7));

            return G;
        }
    }

    @Test
    void breathFirstSearch() {
        var vs = Data.makeVertexes();
        var t = Data.makeGraph(vs);
        BFS.breathFirstSearch(t, vs.get(1));
        assertEquals(List.of('s','w','x','y'),BFS.getPath(vs.get(1), vs.get(7)));
    }
}