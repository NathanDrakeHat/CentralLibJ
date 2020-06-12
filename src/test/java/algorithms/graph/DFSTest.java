package algorithms.graph;

import algorithms.graph.DFS;
import org.junit.jupiter.api.Test;
import tools.Graph;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {
    static String names = "uvwxyz";
    static Graph<DFS.Vertex<String>> makeGraph(){
        var vs = new ArrayList<DFS.Vertex<String>>(6);
        
        for(int i = 0; i < 6; i++){
            vs.add(i, new DFS.Vertex<>(String.valueOf(names.charAt(i))));
        }
        var G = new Graph<>(vs);
        G.addOneNeighbor(vs.get(0), vs.get(1));
        G.addOneNeighbor(vs.get(0), vs.get(3));

        G.addOneNeighbor(vs.get(1), vs.get(4));

        G.addOneNeighbor(vs.get(2), vs.get(4));
        G.addOneNeighbor(vs.get(2), vs.get(5));

        G.addOneNeighbor(vs.get(3), vs.get(1));

        G.addOneNeighbor(vs.get(4), vs.get(3));

        G.addOneNeighbor(vs.get(5), vs.get(5));

        return G;
    }
    static int[][] res = new int[][] {{1, 2, 9, 4, 3, 10}, {8, 7, 12, 5, 6, 11}};

    static Graph<DFS.Vertex<String>> makeTopographicalDemo(){
//            DFS.Vertex[] A = new DFS.Vertex[9];
            var A = new ArrayList<DFS.Vertex<String>>(9);
            String t = "undershorts,pants,belt,shirt,tie,jacket,socks,shoes,watch";
            var names = t.split(",");
            for(int i = 0; i < 9; i++) { A.add(i, new DFS.Vertex<>(names[i])); }
            Graph<DFS.Vertex<String>> G = new Graph<>(A);
            G.addOneNeighbor(A.get(0), A.get(1));
            G.addOneNeighbor(A.get(0), A.get(6));

            G.addOneNeighbor(A.get(1), A.get(2));
            G.addOneNeighbor(A.get(1), A.get(6));

            G.addOneNeighbor(A.get(2), A.get(5));

            G.addOneNeighbor(A.get(3), A.get(2));
            G.addOneNeighbor(A.get(3), A.get(4));

            G.addOneNeighbor(A.get(4), A.get(5));

            G.addOneNeighbor(A.get(6), A.get(7));

            return G;
        }

    static Graph<DFS.Vertex<String>> makeStronglyConnectedComponentsDemo(){
        String t = "a,b,c,d,e,f,g,h";
        var names = t.split(",");
        var A = new ArrayList<DFS.Vertex<String>>(names.length);
        for(int i = 0; i < names.length; i++) { A.add(i,new DFS.Vertex<>(names[i])); }
        Graph<DFS.Vertex<String>> G = new Graph<>(A);
        G.addOneNeighbor(A.get(0), A.get(1));

        G.addOneNeighbor(A.get(1), A.get(2));
        G.addOneNeighbor(A.get(1), A.get(4));
        G.addOneNeighbor(A.get(1), A.get(5));

        G.addOneNeighbor(A.get(2), A.get(3));
        G.addOneNeighbor(A.get(2), A.get(6));

        G.addOneNeighbor(A.get(3), A.get(2));
        G.addOneNeighbor(A.get(3), A.get(7));

        G.addOneNeighbor(A.get(4), A.get(0));
        G.addOneNeighbor(A.get(4), A.get(5));

        G.addOneNeighbor(A.get(5), A.get(6));

        G.addOneNeighbor(A.get(6), A.get(5));
        G.addOneNeighbor(A.get(6), A.get(7));

        G.addOneNeighbor(A.get(7), A.get(7));

        return G;
    }

    @Test
    void depthFirstSearchTest() {
        var G = makeGraph();
        DFS.depthFirstSearch(G);
        int idx = 0;
        for(var v : G.getAllVertices()){
            assertEquals(v.d, res[0][idx]);
            assertEquals(v.f, res[1][idx++]);
        }
        for(var i : G.getAllVertices()){
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

    boolean recursiveTopologicalTest(DFS.Vertex<String> target, DFS.Vertex<String> current, Graph<DFS.Vertex<String>> G){
        if(current.equals(target)) return false;
        var neighbors = G.getNeighborsAt(current);
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
        List<DFS.Vertex<String>> t = new ArrayList<>(graph.getAllVertices());
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
        var vertices = G.getAllVertices();
        List<DFS.Vertex<String>> vs = new ArrayList<>(vertices);
        assertTrue((getRoot(vs.get(0)) == getRoot(vs.get(1))) & (getRoot(vs.get(1)) == getRoot(vs.get(4))));
        assertSame(getRoot(vs.get(2)), getRoot(vs.get(3)));
        assertSame(getRoot(vs.get(5)), getRoot(vs.get(6)));
        assertSame(getRoot(vs.get(7)), vs.get(7));

    }

    DFS.Vertex<String> getRoot(DFS.Vertex<String> v){
        var t = v;
        while(t.parent != null){ t = t.parent; }
        return t;
    }
}