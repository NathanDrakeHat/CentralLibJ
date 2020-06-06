package algorithms;

import org.junit.jupiter.api.Test;
import tools.LinkedGraph;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    public static class Data{
        public static String names = "rstuvwxy";
        public static BFS.Vertex[] makeVertexes(){
            var vs = new BFS.Vertex[8];
            for(int i = 0; i < 8; i++){
                vs[i] = new BFS.Vertex(names.charAt(i));
            }
            return vs;
        }
        public static LinkedGraph<BFS.Vertex> makeGraph(BFS.Vertex[] vs){ ;
            var G = new LinkedGraph<>(vs);
            G.putNeighbor(vs[0], vs[1]);
            G.putNeighbor(vs[0], vs[4]);
//            G.Nodes[0].next = G.buildNext(G.Nodes[1].vertex);
//            G.Nodes[0].next.next = G.buildNext(G.Nodes[4].vertex);
            G.putNeighbor(vs[1], vs[0]);
            G.putNeighbor(vs[1], vs[5]);
//            G.Nodes[1].next = G.buildNext(G.Nodes[0].vertex);
//            G.Nodes[1].next.next = G.buildNext(G.Nodes[5].vertex);
            G.putNeighbor(vs[2], vs[3]);
            G.putNeighbor(vs[2], vs[5]);
            G.putNeighbor(vs[2], vs[6]);
//            G.Nodes[2].next = G.buildNext(G.Nodes[3].vertex);
//            G.Nodes[2].next.next = G.buildNext(G.Nodes[5].vertex);
//            G.Nodes[2].next.next.next = G.buildNext(G.Nodes[6].vertex);
            G.putNeighbor(vs[3], vs[2]);
            G.putNeighbor(vs[3], vs[6]);
            G.putNeighbor(vs[3], vs[7]);
//            G.Nodes[3].next = G.buildNext(G.Nodes[2].vertex);
//            G.Nodes[3].next.next = G.buildNext(G.Nodes[6].vertex);
//            G.Nodes[3].next.next.next = G.buildNext(G.Nodes[7].vertex);
            G.putNeighbor(vs[4], vs[0]);
//            G.Nodes[4].next = G.buildNext(G.Nodes[0].vertex);
            G.putNeighbor(vs[5], vs[1]);
            G.putNeighbor(vs[5], vs[2]);
            G.putNeighbor(vs[5], vs[6]);
//            G.Nodes[5].next = G.buildNext(G.Nodes[1].vertex);
//            G.Nodes[5].next.next = G.buildNext(G.Nodes[2].vertex);
//            G.Nodes[5].next.next.next = G.buildNext(G.Nodes[6].vertex);
            G.putNeighbor(vs[6], vs[2]);
            G.putNeighbor(vs[6], vs[3]);
            G.putNeighbor(vs[6], vs[5]);
            G.putNeighbor(vs[6], vs[7]);
//            G.Nodes[6].next = G.buildNext(G.Nodes[2].vertex);
//            G.Nodes[6].next.next = G.buildNext(G.Nodes[3].vertex);
//            G.Nodes[6].next.next.next = G.buildNext(G.Nodes[5].vertex);
//            G.Nodes[6].next.next.next.next = G.buildNext(G.Nodes[7].vertex);
            G.putNeighbor(vs[7], vs[3]);
            G.putNeighbor(vs[7], vs[6]);
//            G.Nodes[7].next = G.buildNext(G.Nodes[3].vertex);
//            G.Nodes[7].next.next = G.buildNext(G.Nodes[6].vertex);

            return G;
        }
    }

    @Test
    void breathFirstSearch() {
        var vs = Data.makeVertexes();
        var t = Data.makeGraph(vs);
        BFS.breathFirstSearch(t, vs[1]);
        assertArrayEquals(BFS.getPath(vs[1], vs[7]), new char[] {'s','w','x','y'});
    }
}