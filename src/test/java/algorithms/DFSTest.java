package algorithms;

import org.junit.jupiter.api.Test;
import tools.LinkedGraph;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {

    public static class Data{
        public static String names = "uvwxyz";
        public static LinkedGraph<DFS.Vertex> makeGraph(){

            var vs = new DFS.Vertex[6];
            for(int i = 0; i < 6; i++){
                vs[i] = new DFS.Vertex(names.charAt(i));
            }
            var G = new LinkedGraph<>(vs);
            G.putNeighbor(vs[0], vs[1]);
            G.putNeighbor(vs[0], vs[3]);
//            G.Nodes[0].next = G.buildNext(G.Nodes[1].vertex);
//            G.Nodes[0].next.next = G.buildNext(G.Nodes[3].vertex);
            G.putNeighbor(vs[1], vs[4]);
//            G.Nodes[1].next = G.buildNext(G.Nodes[4].vertex);

            G.putNeighbor(vs[2], vs[4]);
            G.putNeighbor(vs[2], vs[5]);
//            G.Nodes[2].next = G.buildNext(G.Nodes[4].vertex);
//            G.Nodes[2].next.next = G.buildNext(G.Nodes[5].vertex);
            G.putNeighbor(vs[3], vs[1]);
//            G.Nodes[3].next = G.buildNext(G.Nodes[1].vertex);
            G.putNeighbor(vs[4], vs[3]);
//            G.Nodes[4].next = G.buildNext(G.Nodes[3].vertex);
            G.putNeighbor(vs[5], vs[5]);
//            G.Nodes[5].next = G.buildNext(G.Nodes[5].vertex);
            return G;
        }
        public static int[][] res = new int[][] {{1, 2, 9, 4, 3, 10}, {8, 7, 12, 5, 6, 11}};
    }

    @Test
    void depthFirstSearch() {
        var G = Data.makeGraph();
        DFS.depthFirstSearch(G);
        int idx = 0;
        for(var v : G.getVertexes()){
            assertEquals(v.d, Data.res[0][idx]);
            assertEquals(v.f, Data.res[1][idx++]);
        }
    }
}