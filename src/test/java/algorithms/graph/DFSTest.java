package algorithms.graph;

import algorithms.graph.DFS;
import org.junit.jupiter.api.Test;
import tools.LinkedGraph;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {
    static String names = "uvwxyz";
    static LinkedGraph<DFS.Vertex> makeGraph(){
        var vs = new DFS.Vertex[6];
        for(int i = 0; i < 6; i++){
            vs[i] = new DFS.Vertex(String.valueOf(names.charAt(i)));
        }
        var G = new LinkedGraph<>(vs);
        G.addOneNeighbor(vs[0], vs[1]);
        G.addOneNeighbor(vs[0], vs[3]);

        G.addOneNeighbor(vs[1], vs[4]);

        G.addOneNeighbor(vs[2], vs[4]);
        G.addOneNeighbor(vs[2], vs[5]);

        G.addOneNeighbor(vs[3], vs[1]);

        G.addOneNeighbor(vs[4], vs[3]);

        G.addOneNeighbor(vs[5], vs[5]);

        return G;
    }
    static int[][] res = new int[][] {{1, 2, 9, 4, 3, 10}, {8, 7, 12, 5, 6, 11}};

    static LinkedGraph<DFS.Vertex> makeTopographicalDemo(){
            DFS.Vertex[] A = new DFS.Vertex[9];
            String t = "undershorts,pants,belt,shirt,tie,jacket,socks,shoes,watch";
            var names = t.split(",");
            for(int i = 0; i < 9; i++) { A[i] = new DFS.Vertex(names[i]); }
            LinkedGraph<DFS.Vertex> G = new LinkedGraph<>(A);
            G.addOneNeighbor(A[0], A[1]);
            G.addOneNeighbor(A[0], A[6]);

            G.addOneNeighbor(A[1], A[2]);
            G.addOneNeighbor(A[1], A[6]);

            G.addOneNeighbor(A[2], A[5]);

            G.addOneNeighbor(A[3], A[2]);
            G.addOneNeighbor(A[3], A[4]);

            G.addOneNeighbor(A[4], A[5]);

            G.addOneNeighbor(A[6], A[7]);

            return G;
        }

    static LinkedGraph<DFS.Vertex> makeStronglyConnectedComponentsDemo(){
        String t = "a,b,c,d,e,f,g,h";
        var names = t.split(",");
        DFS.Vertex[] A = new DFS.Vertex[names.length];
        for(int i = 0; i < names.length; i++) { A[i] = new DFS.Vertex(names[i]); }
        LinkedGraph<DFS.Vertex> G = new LinkedGraph<>(A);
        G.addOneNeighbor(A[0], A[1]);

        G.addOneNeighbor(A[1], A[2]);
        G.addOneNeighbor(A[1], A[4]);
        G.addOneNeighbor(A[1], A[5]);

        G.addOneNeighbor(A[2], A[3]);
        G.addOneNeighbor(A[2], A[6]);

        G.addOneNeighbor(A[3], A[2]);
        G.addOneNeighbor(A[3], A[7]);

        G.addOneNeighbor(A[4], A[0]);
        G.addOneNeighbor(A[4], A[5]);

        G.addOneNeighbor(A[5], A[6]);

        G.addOneNeighbor(A[6], A[5]);
        G.addOneNeighbor(A[6], A[7]);

        G.addOneNeighbor(A[7], A[7]);

        return G;
    }

    @Test
    void depthFirstSearchTest() {
        var G = makeGraph();
        DFS.depthFirstSearch(G);
        int idx = 0;
        for(var v : G.getVertexes()){
            assertEquals(v.d, res[0][idx]);
            assertEquals(v.f, res[1][idx++]);
        }
        for(var i : G.getVertexes()){
            var t = i;
            while(t.parent != null){ t = t.parent; }
            System.out.println(String.format("vertex %s parent is %s", i.getName(), t.getName()));
        }
    }

    @Test
    void topologicalSortTest(){
        var graph = makeTopographicalDemo();
        var l = DFS.topologicalSort(graph);
        boolean flag = true;
        for(int i = 1; i < l.size(); i++){
            for(int j = 0; j < i; j++){
                flag = recursiveTopologicalTest(l.get(j), l.get(i), graph);
                if(!flag) break;
            }
            if(!flag) break;
        }
        assertTrue(flag);
    }

    boolean recursiveTopologicalTest(DFS.Vertex target, DFS.Vertex current, LinkedGraph<DFS.Vertex> G){
        if(current.equals(target)) return false;
        var neighbors = G.getNeighbors(current);
        if(neighbors.isEmpty()) return true;
        else{
            boolean t = true;
            for(var i : neighbors){
                t = recursiveTopologicalTest(target, i, G);
                if(!t) break;
            }
            return t;
        }
    }

    @Test
    void recursiveTopologicalTest(){
        var graph = makeTopographicalDemo();
        boolean flag = true;
        List<DFS.Vertex> t = new ArrayList<>(graph.getVertexes());
        for(int i = 1; i < t.size(); i++){
            for(int j = 0; j < i; j++){
                flag = recursiveTopologicalTest(t.get(j), t.get(i), graph);
                if(!flag) break;
            }
            if(!flag) break;
        }
        assertFalse(flag);
    }

    @Test
    void stronglyConnectedComponentsTest(){
        var G = makeStronglyConnectedComponentsDemo();
        DFS.stronglyConnectedComponents(G);
        var vertexes = G.getVertexes();
        List<DFS.Vertex> vs = new ArrayList<>(vertexes);
        assertTrue((getRoot(vs.get(0)) == getRoot(vs.get(1))) & (getRoot(vs.get(1)) == getRoot(vs.get(4))));
        assertSame(getRoot(vs.get(2)), getRoot(vs.get(3)));
        assertSame(getRoot(vs.get(5)), getRoot(vs.get(6)));
        assertSame(getRoot(vs.get(7)), vs.get(7));

    }

    DFS.Vertex getRoot(DFS.Vertex v){
        var t = v;
        while(t.parent != null){ t = t.parent; }
        return t;
    }
}