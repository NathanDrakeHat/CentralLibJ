package algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    public static class Data{
        public static String names = "rstuvwxy";
        public static BFS.Graph makeGraph(){
            var G = new BFS.Graph(8);
            for(int i = 0; i < 8; i++){
                G.Nodes[i] = G.buildNext(new BFS.Graph.Vertex(names.charAt(i)));
            }
            G.Nodes[0].next = G.buildNext(G.Nodes[1].vertex);
            G.Nodes[0].next.next = G.buildNext(G.Nodes[4].vertex);

            G.Nodes[1].next = G.buildNext(G.Nodes[0].vertex);
            G.Nodes[1].next.next = G.buildNext(G.Nodes[5].vertex);

            G.Nodes[2].next = G.buildNext(G.Nodes[3].vertex);
            G.Nodes[2].next.next = G.buildNext(G.Nodes[5].vertex);
            G.Nodes[2].next.next.next = G.buildNext(G.Nodes[6].vertex);

            G.Nodes[3].next = G.buildNext(G.Nodes[2].vertex);
            G.Nodes[3].next.next = G.buildNext(G.Nodes[6].vertex);
            G.Nodes[3].next.next.next = G.buildNext(G.Nodes[7].vertex);

            G.Nodes[4].next = G.buildNext(G.Nodes[0].vertex);

            G.Nodes[5].next = G.buildNext(G.Nodes[1].vertex);
            G.Nodes[5].next.next = G.buildNext(G.Nodes[2].vertex);
            G.Nodes[5].next.next.next = G.buildNext(G.Nodes[6].vertex);

            G.Nodes[6].next = G.buildNext(G.Nodes[2].vertex);
            G.Nodes[6].next.next = G.buildNext(G.Nodes[3].vertex);
            G.Nodes[6].next.next.next = G.buildNext(G.Nodes[5].vertex);
            G.Nodes[6].next.next.next.next = G.buildNext(G.Nodes[7].vertex);

            G.Nodes[7].next = G.buildNext(G.Nodes[3].vertex);
            G.Nodes[7].next.next = G.buildNext(G.Nodes[6].vertex);

            return G;
        }
    }

    @Test
    void breathFirstSearch() {
        var t = Data.makeGraph();
        BFS.breathFirstSearch(t, t.Nodes[1].vertex);
        assertArrayEquals(BFS.getPath(t, t.Nodes[1].vertex, t.Nodes[7].vertex), new char[] {'s','w','x','y'});
    }
}