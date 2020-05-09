package algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    public static class Data{
        public static String names = "rstuvwxy";
        public static BFS.Node[] makeGraph(){
            BFS.Node[] G = new BFS.Node[8];
            for(int i = 0; i < 8; i++){
                G[i] = new BFS.Node(new BFS.Vertex(names.charAt(i)));
            }
            G[0].next = new BFS.Node(G[1].vertex);
            G[0].next.next = new BFS.Node(G[4].vertex);

            G[1].next = new BFS.Node(G[0].vertex);
            G[1].next.next = new BFS.Node(G[5].vertex);

            G[2].next = new BFS.Node(G[3].vertex);
            G[2].next.next = new BFS.Node(G[5].vertex);
            G[2].next.next.next = new BFS.Node(G[6].vertex);

            G[3].next = new BFS.Node(G[2].vertex);
            G[3].next.next = new BFS.Node(G[6].vertex);
            G[3].next.next.next = new BFS.Node(G[7].vertex);

            G[4].next = new BFS.Node(G[0].vertex);

            G[5].next = new BFS.Node(G[1].vertex);
            G[5].next.next = new BFS.Node(G[2].vertex);
            G[5].next.next.next = new BFS.Node(G[6].vertex);

            G[6].next = new BFS.Node(G[2].vertex);
            G[6].next.next = new BFS.Node(G[3].vertex);
            G[6].next.next.next = new BFS.Node(G[5].vertex);
            G[6].next.next.next.next = new BFS.Node(G[7].vertex);

            G[7].next = new BFS.Node(G[3].vertex);
            G[7].next.next = new BFS.Node(G[6].vertex);

            return G;
        }
    }

    @Test
    void breathFirstSearch() {
        var t = Data.makeGraph();
        BFS.breathFirstSearch(t, t[1].vertex);
        assertArrayEquals(BFS.getPath(t, t[1].vertex, t[7].vertex), new char[] {'s','w','x','y'});
    }
}