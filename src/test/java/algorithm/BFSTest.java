package algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    public static class Data{
        public static String names = "rstuvwxy";
        public static BFS.Graph makeGraph(){
            var G = new BFS.Graph(8);
            for(int i = 0; i < 8; i++){
                G.Vertexs[i] = G.buildNext(new BFS.Graph.Vertex(names.charAt(i)));
            }
            G.Vertexs[0].next = G.buildNext(G.Vertexs[1].vertex);
            G.Vertexs[0].next.next = G.buildNext(G.Vertexs[4].vertex);

            G.Vertexs[1].next = G.buildNext(G.Vertexs[0].vertex);
            G.Vertexs[1].next.next = G.buildNext(G.Vertexs[5].vertex);

            G.Vertexs[2].next = G.buildNext(G.Vertexs[3].vertex);
            G.Vertexs[2].next.next = G.buildNext(G.Vertexs[5].vertex);
            G.Vertexs[2].next.next.next = G.buildNext(G.Vertexs[6].vertex);

            G.Vertexs[3].next = G.buildNext(G.Vertexs[2].vertex);
            G.Vertexs[3].next.next = G.buildNext(G.Vertexs[6].vertex);
            G.Vertexs[3].next.next.next = G.buildNext(G.Vertexs[7].vertex);

            G.Vertexs[4].next = G.buildNext(G.Vertexs[0].vertex);

            G.Vertexs[5].next = G.buildNext(G.Vertexs[1].vertex);
            G.Vertexs[5].next.next = G.buildNext(G.Vertexs[2].vertex);
            G.Vertexs[5].next.next.next = G.buildNext(G.Vertexs[6].vertex);

            G.Vertexs[6].next = G.buildNext(G.Vertexs[2].vertex);
            G.Vertexs[6].next.next = G.buildNext(G.Vertexs[3].vertex);
            G.Vertexs[6].next.next.next = G.buildNext(G.Vertexs[5].vertex);
            G.Vertexs[6].next.next.next.next = G.buildNext(G.Vertexs[7].vertex);

            G.Vertexs[7].next = G.buildNext(G.Vertexs[3].vertex);
            G.Vertexs[7].next.next = G.buildNext(G.Vertexs[6].vertex);

            return G;
        }
    }

    @Test
    void breathFirstSearch() {
        var t = Data.makeGraph();
        BFS.breathFirstSearch(t, t.Vertexs[1].vertex);
        assertArrayEquals(BFS.getPath(t, t.Vertexs[1].vertex, t.Vertexs[7].vertex), new char[] {'s','w','x','y'});
    }
}