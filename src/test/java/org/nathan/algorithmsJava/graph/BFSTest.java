package org.nathan.algorithmsJava.graph;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BFSTest {


    List<BFSVertex<Character>> vertices;
    LinkedGraph<BFSVertex<Character>> graph;
    public BFSTest(){
        vertices = Data.makeVertexes();
        graph = Data.makeGraph(vertices);
    }

    @Test
    void breathFirstSearch() {
        BFS.breathFirstSearch(graph, vertices.get(1));
        assertEquals(List.of('s', 'w', 'x', 'y'), BFS.getPath(vertices.get(1), vertices.get(7)));
    }

    public static class Data {
        public static String names = "rstuvwxy";

        public static List<BFSVertex<Character>> makeVertexes() {
            List<BFSVertex<Character>> vs = new ArrayList<>(8);
            for (int i = 0; i < 8; i++) {
                vs.add(i, new BFSVertex<>(names.charAt(i)));
            }
            return vs;
        }

        public static LinkedGraph<BFSVertex<Character>> makeGraph(List<BFSVertex<Character>> vs) {
            var G = new LinkedGraph<>(vs, false);
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
}