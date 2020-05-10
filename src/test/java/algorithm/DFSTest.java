package algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {

    public static class Data{
        public static String names = "uvwxyz";
        public static DFS.Graph makeGraph(){
            var G = new DFS.Graph(6);
            for(int i = 0; i < 6; i++){
                G.Nodes[i] = G.buildVertex(new DFS.Graph.Vertex(names.charAt(i)));
            }
            G.Nodes[0].next = G.buildNext(G.Nodes[1].vertex);
            G.Nodes[0].next.next = G.buildNext(G.Nodes[3].vertex);

            G.Nodes[1].next = G.buildNext(G.Nodes[4].vertex);

            G.Nodes[2].next = G.buildNext(G.Nodes[4].vertex);
            G.Nodes[2].next.next = G.buildNext(G.Nodes[5].vertex);

            G.Nodes[3].next = G.buildNext(G.Nodes[1].vertex);

            G.Nodes[4].next = G.buildNext(G.Nodes[3].vertex);

            G.Nodes[5].next = G.buildNext(G.Nodes[5].vertex);
            return G;
        }
        public static int[][] res = new int[][] {{1, 2, 9, 4, 3, 10}, {8, 7, 12, 5, 6, 11}};
    }

    @Test
    void depthFirstSearch() {
        var G = Data.makeGraph();
        DFS.depthFirstSearch(G);
        int idx = 0;
        for(var n : G.Nodes){
            assertEquals(n.vertex.d, Data.res[0][idx]);
            assertEquals(n.vertex.f, Data.res[1][idx++]);
        }
    }
}