package algorithms;

import org.junit.jupiter.api.Test;
import tools.LinkedGraph;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {

    public static class Data{
        public static String names = "uvwxyz";
        public static LinkedGraph<DFS.Vertex> makeGraph(){

            var vs = new DFS.Vertex[6];
            for(int i = 0; i < 6; i++){
                vs[i] = new DFS.Vertex(String.valueOf(names.charAt(i)));
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

        public static LinkedGraph<DFS.Vertex> makeTopographicalDemo(){
            DFS.Vertex[] A = new DFS.Vertex[9];
            String t = "undershorts,pants,belt,shirt,tie,jacket,socks,shoes,watch";
            var names = t.split(",");
            for(int i = 0; i < 9; i++) { A[i] = new DFS.Vertex(names[i]); }
            LinkedGraph<DFS.Vertex> G = new LinkedGraph<>(A);
            G.putNeighbor(A[0], A[1]);
            G.putNeighbor(A[0], A[6]);

            G.putNeighbor(A[1], A[2]);
            G.putNeighbor(A[1], A[6]);

            G.putNeighbor(A[2], A[5]);

            G.putNeighbor(A[3], A[2]);
            G.putNeighbor(A[3], A[4]);

            G.putNeighbor(A[4], A[5]);

            G.putNeighbor(A[6], A[7]);

            return G;
        }
    }

    @Test
    void depthFirstSearchTest() {
        var G = Data.makeGraph();
        DFS.depthFirstSearch(G);
        int idx = 0;
        for(var v : G.getVertexes()){
            assertEquals(v.d, Data.res[0][idx]);
            assertEquals(v.f, Data.res[1][idx++]);
        }
    }

    @Test
    void topologicalSortTest(){
        var graph = Data.makeTopographicalDemo();
        var l = DFS.topologicalSort(graph);
        boolean flag = true;
        for(int i = 1; i < l.size(); i++){
            for(int j = 0; j < i; j++){
                flag = recursiveSearch(l.get(j), l.get(i), graph);
                if(!flag) break;
            }
            if(!flag) break;
        }
        assertTrue(flag);
    }

    boolean recursiveSearch(DFS.Vertex target, DFS.Vertex current, LinkedGraph<DFS.Vertex> G){
        if(current.equals(target)) return false;
        var neighbors = G.getNeighbors(current);
        if(neighbors.isEmpty()) return true;
        else{
            boolean t = true;
            for(var i : neighbors){
                t = recursiveSearch(target, i, G);
                if(!t) break;
            }
            return t;
        }
    }

    @Test
    void recursiveSearchTest(){
        var graph = Data.makeTopographicalDemo();
        boolean flag = true;
        List<DFS.Vertex> t = new ArrayList<>(graph.getVertexes());
        for(int i = 1; i < t.size(); i++){
            for(int j = 0; j < i; j++){
                flag = recursiveSearch(t.get(j), t.get(i), graph);
                if(!flag) break;
            }
            if(!flag) break;
        }
        assertFalse(flag);
    }
}