package algorithms.graph;


import org.junit.jupiter.api.Test;
import tools.Graph;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BreathFirstSearchTest {

    public static class Data{
        public static String names = "rstuvwxy";
        public static List<BreathFirstSearch.Vertex<Character>> makeVertexes(){
            List<BreathFirstSearch.Vertex<Character>> vs = new ArrayList<>(8);
            for(int i = 0; i < 8; i++){
                vs.add(i, new BreathFirstSearch.Vertex<>(names.charAt(i)));
            }
            return vs;
        }
        public static Graph<BreathFirstSearch.Vertex<Character>> makeGraph(List<BreathFirstSearch.Vertex<Character>> vs){ ;
            var G = new Graph<>(vs);
            G.putNeighborPair(vs.get(0), vs.get(1));
            G.putNeighborPair(vs.get(0), vs.get(4));

            G.putNeighborPair(vs.get(1), vs.get(5));

            G.putNeighborPair(vs.get(2), vs.get(3));
            G.putNeighborPair(vs.get(2), vs.get(5));
            G.putNeighborPair(vs.get(2), vs.get(6));

            G.putNeighborPair(vs.get(3), vs.get(6));

            G.putNeighborPair(vs.get(5), vs.get(6));

            G.putNeighborPair(vs.get(6), vs.get(7));

            return G;
        }
    }

    @Test
    void breathFirstSearch() {
        var vs = Data.makeVertexes();
        var t = Data.makeGraph(vs);
        BreathFirstSearch.breathFirstSearch(t, vs.get(1));
        assertEquals(BreathFirstSearch.getPath(vs.get(1), vs.get(7)), List.of('s','w','x','y'));
    }
}