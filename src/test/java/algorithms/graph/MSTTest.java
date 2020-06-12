package algorithms.graph;

import org.junit.jupiter.api.Test;
import tools.WeightedGraph;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MSTTest {
    static WeightedGraph<MST.KruskalVertex> buildKruskalExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        MST.KruskalVertex[] vertices = new MST.KruskalVertex[len];
        for(int i = 0; i < len; i++){ vertices[i] = new MST.KruskalVertex(names[i]); }
        WeightedGraph<MST.KruskalVertex> res = new WeightedGraph<>();
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        for(int i = 0; i < len_; i++){
            res.putNeighborPair(vertices[indexes1[i]], vertices[indexes2[i]], weights[i]);
        }
        return res;
    }
    static Set<WeightedGraph<MST.KruskalVertex>.Edge> buildKruskalAnswer(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        MST.KruskalVertex[] vertices = new MST.KruskalVertex[len];
        WeightedGraph<MST.KruskalVertex> g = new WeightedGraph<>();
        for(int i = 0; i < len; i++){ vertices[i] = new MST.KruskalVertex(names[i]); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        Set<WeightedGraph<MST.KruskalVertex>.Edge> res = new HashSet<>();
        int[] answers = new int[]{0, 2, 3, 5, 6, 7, 9, 12};
        for(var i : answers)
            res.add(g.new Edge(vertices[indexes1[i]], vertices[indexes2[i]], weights[i]));
        return res;
    }
    @Test
    public void algorithmOfKruskalTest(){
        var t = MST.algorithmOfKruskal(buildKruskalExample());
        assertEquals(t, buildKruskalAnswer());
        int i = 0;
        for(var e : t){
            i += e.getWeight();
        }
        assertEquals(i, 37);
    }


    static WeightedGraph<MST.PrimVertex> buildPrimExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        MST.PrimVertex[] vertices = new MST.PrimVertex[len];
        for(int i = 0; i < len; i++){ vertices[i] = new MST.PrimVertex(names[i]); }
        WeightedGraph<MST.PrimVertex> res = new WeightedGraph<>();
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        for(int i = 0; i < len_; i++){
            res.putNeighborPair(vertices[indexes1[i]], vertices[indexes2[i]], weights[i]);
        }
        return res;
    }
    static Set<Set<MST.PrimVertex>> buildPrimAnswer(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        MST.PrimVertex[] vertices = new MST.PrimVertex[len];
        WeightedGraph<MST.KruskalVertex> g = new WeightedGraph<>();
        for(int i = 0; i < len; i++){ vertices[i] = new MST.PrimVertex(names[i]); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        Set<Set<MST.PrimVertex>> res = new HashSet<>();
        int[] answers = new int[]{0, 1, 2, 3, 5, 6, 9, 12};
        for(int i = 0; i < answers.length; i++){
            Set<MST.PrimVertex> t = new HashSet<>();
            t.add(vertices[indexes1[answers[i]]]);
            t.add(vertices[indexes2[answers[i]]]);
            res.add(t);
        }
        return res;
    }
    @Test
    public void algorithmOfPrimTest(){
        var graph = MST.algorithmOfPrim(buildPrimExample(), new MST.PrimVertex("a"));
        var vertices = graph.getVertexes();
        Set<Set<MST.PrimVertex>> res = new HashSet<>();
        for(var vertex : vertices){
            if(vertex.parent != null){
                Set<MST.PrimVertex> t = new HashSet<>();
                t.add(vertex);
                t.add(vertex.parent);
                res.add(t);
            }
        }
        assertEquals(buildPrimAnswer(), res);
    }
}