package algorithms.graph;

import algorithms.graph.MST;
import org.junit.jupiter.api.Test;
import tools.WeightedGraph;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MSTTest {
    static WeightedGraph<MST.KruskalVertex> buildKruskalExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        MST.KruskalVertex[] vertexes = new MST.KruskalVertex[len];
        for(int i = 0; i < len; i++){ vertexes[i] = new MST.KruskalVertex(names[i]); }
        WeightedGraph<MST.KruskalVertex> res = new WeightedGraph<>();
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        for(int i = 0; i < len_; i++){
            putPairHelperKruskal(res, vertexes, indexes1[i], indexes2[i],weights[i]);
        }
        return res;
    }
    static void putPairHelperKruskal(WeightedGraph<MST.KruskalVertex> res, MST.KruskalVertex[] vertexes, int idx1, int idx2, int w){
        res.putNeighborPair(vertexes[idx1], vertexes[idx2], w);
    }
    static Set<WeightedGraph<MST.KruskalVertex>.Edge> buildKruskalAnswer(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        MST.KruskalVertex[] vertexes = new MST.KruskalVertex[len];
        WeightedGraph<MST.KruskalVertex> g = new WeightedGraph<>();
        for(int i = 0; i < len; i++){ vertexes[i] = new MST.KruskalVertex(names[i]); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        Set<WeightedGraph<MST.KruskalVertex>.Edge> res = new HashSet<>();
        int[] answers = new int[]{0, 2, 3, 5, 6, 7, 9, 12};
        for(var i : answers)
            res.add(g.new Edge(vertexes[indexes1[i]], vertexes[indexes2[i]], weights[i]));
        return res;
    }
    @Test
    public void algorithmKruskalTest(){
        var t = MST.algorithmKruskal(buildKruskalExample());
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
        MST.PrimVertex[] vertexes = new MST.PrimVertex[len];
        for(int i = 0; i < len; i++){ vertexes[i] = new MST.PrimVertex(names[i]); }
        WeightedGraph<MST.PrimVertex> res = new WeightedGraph<>();
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        for(int i = 0; i < len_; i++){
            putPairHelper(res, vertexes, indexes1[i], indexes2[i],weights[i]);
        }
        return res;
    }
    static void putPairHelper(WeightedGraph<MST.PrimVertex> res, MST.PrimVertex[] vertexes, int idx1, int idx2, int w){
        res.putNeighborPair(vertexes[idx1], vertexes[idx2], w);
    }
    static Set<Set<MST.PrimVertex>> buildPrimAnswer(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        MST.PrimVertex[] vertexes = new MST.PrimVertex[len];
        WeightedGraph<MST.KruskalVertex> g = new WeightedGraph<>();
        for(int i = 0; i < len; i++){ vertexes[i] = new MST.PrimVertex(names[i]); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        Set<Set<MST.PrimVertex>> res = new HashSet<>();
        int[] answers = new int[]{0, 1, 2, 3, 5, 6, 9, 12};
        for(int i = 0; i < answers.length; i++){
            Set<MST.PrimVertex> t = new HashSet<>();
            t.add(vertexes[indexes1[answers[i]]]);
            t.add(vertexes[indexes2[answers[i]]]);
            res.add(t);
        }
        return res;
    }
    @Test
    public void algorithmPrimTest(){
        var graph = MST.algorithmPrim(buildPrimExample(), new MST.PrimVertex("a"));
        var vertexes = graph.getVertexes();
        Set<Set<MST.PrimVertex>> res = new HashSet<>();
        for(var vertex : vertexes){
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