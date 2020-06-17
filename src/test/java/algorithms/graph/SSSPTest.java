package algorithms.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SSSPTest {
    static Graph<BFS.Vertex<String>> buildBellmanFordCase(){
        String[] names = "s,t,x,y,z".split(",");
        List<BFS.Vertex<String>> vertices = new ArrayList<>();
        for(var n : names) vertices.add(new BFS.Vertex<>(n));
        var res = new Graph<>(vertices,Graph.Direction.DIRECTED);
        int[] index1 =        new int[]{0,0,1,1, 1, 2, 3,3,4,4};
        int[] index2 =        new int[]{1,3,2,3, 4, 1, 2,4,0,2};
        double[] weights = new double[]{6,7,5,8,-4,-2,-3,9,2,7};
        for(int i = 0; i < index1.length; i++){
            res.setNeighbor(vertices.get(index1[i]),vertices.get(index2[i]), weights[i]);
        }
        return res;
    }
    @Test
    void algorithmBellmanFord() {
        var G = buildBellmanFordCase();
        var b = SSSP.algorithmBellmanFord(G, new BFS.Vertex<>("s"));
        BFS.Vertex<String> target = new BFS.Vertex<>("z");
        for(var v : G.getAllVertices()){
            if(v.equals(target)) { target = v; } }
        List<String> res = new ArrayList<>();
        while(target != null){
            res.add(target.getContent());
            target = target.parent;
        }
        assertTrue(b);
        assertEquals(res, List.of("z", "t", "x", "y","s"));
    }

    static Graph<DFS.Vertex<BFS.Vertex<String>>> buildShortestPathOfDAGCase(){
        String[] names = "r,s,t,x,y,z".split(",");
        List<BFS.Vertex<String>> vertices = new ArrayList<>();
        for (String name : names) { vertices.add(new BFS.Vertex<>(name)); }
        var g = new Graph<>(vertices,Graph.Direction.DIRECTED);
        int[] index1 =        new int[]{0,0,1,1,2,2,2, 3,3, 4};
        int[] index2 =        new int[]{1,2,2,3,3,4,5, 4,5, 5};
        double[] weights = new double[]{5,3,2,6,7,4,2,-1,1,-2};
        for(int i = 0; i < index1.length; i++){
            g.setNeighbor(vertices.get(index1[i]),vertices.get(index2[i]),weights[i]);
        }
        return Graph.convert(g, DFS.Vertex::new);
    }
    @Test
    void shortestPathOfDAG(){
        var graph = buildShortestPathOfDAGCase();
        var res = SSSP.shortestPathOfDAG(graph,new BFS.Vertex<>("s"));
        System.out.println(res);
        System.out.println(graph);
    }

}