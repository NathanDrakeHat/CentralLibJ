package algorithms.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    static Result buildShortestPathOfDAGForBFS(){
        String[] names = "r,s,t,x,y,z".split(",");
        List<BFS.Vertex<String>> BFS_vertex = new ArrayList<>();
        for (String name : names) { BFS_vertex.add(new BFS.Vertex<>(name)); }
        var BFS_G = new Graph<>(BFS_vertex,Graph.Direction.DIRECTED);
        int[] index1 =        new int[]{0,0,1,1,2,2,2, 3,3, 4};
        int[] index2 =        new int[]{1,2,2,3,3,4,5, 4,5, 5};
        double[] weights = new double[]{5,3,2,6,7,4,2,-1,1,-2};
        for(int i = 0; i < index1.length; i++){
            BFS_G.setNeighbor(BFS_vertex.get(index1[i]),BFS_vertex.get(index2[i]),weights[i]);
        }
        var DFS_vertices = BFS_vertex.stream().map(DFS.Vertex::new).collect(Collectors.toList());
        var DFS_G = new Graph<>(DFS_vertices, Graph.Direction.DIRECTED);
        int len = DFS_vertices.size();
        for(int i = 0; i < len - 1; i++){
            DFS_G.setNeighbor(DFS_vertices.get(i), DFS_vertices.get(i+1));
        }
        var t = new Result();
        t.BFS_G = BFS_G;
        t.DFS_G = DFS_G;
        return t;
    }
    static class Result{
        public Graph<BFS.Vertex<String>> BFS_G;
        public Graph<DFS.Vertex<BFS.Vertex<String>>> DFS_G;
    }

    @Test
    void shortestPathOfDAG(){
        var two_graph = buildShortestPathOfDAGForBFS();
        var res = SSSP.shortestPathOfDAG(two_graph.DFS_G,two_graph.BFS_G,new BFS.Vertex<>("s"));
        System.out.println(res);
    }

}