package algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {

    public static class Data{
        public static String names = "uvwxyz";
        public static DFS.Node[] makeGraph(){
            DFS.Node[] G = new DFS.Node[6];
            for(int i = 0; i < 6; i++){
                G[i] = new DFS.Node(new DFS.Node.Vertex(names.charAt(i)));
            }
            G[0].next = new DFS.Node(G[1].vertex);
            G[0].next.next = new DFS.Node(G[3].vertex);

            G[1].next = new DFS.Node(G[4].vertex);

            G[2].next = new DFS.Node(G[4].vertex);
            G[2].next.next = new DFS.Node(G[5].vertex);

            G[3].next = new DFS.Node(G[1].vertex);

            G[4].next = new DFS.Node(G[3].vertex);

            G[5].next = new DFS.Node(G[5].vertex);
            return G;
        }
        public static int[][] res = new int[][] {{1, 2, 9, 4, 3, 10}, {8, 7, 12, 5, 6, 11}};
    }

    @Test
    void depthFirstSearch() {
        var G = Data.makeGraph();
        DFS.depthFirstSearch(G);
        int idx = 0;
        for(var n : G){
            assertEquals(n.vertex.d, Data.res[0][idx]);
            assertEquals(n.vertex.f, Data.res[1][idx++]);
        }
    }
}