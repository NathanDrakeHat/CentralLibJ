package algorithms;

import org.junit.jupiter.api.Test;
import tools.WeightedGraph;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MSTTest {
    static WeightedGraph<MST.Vertex> buildExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        MST.Vertex[] vertexes = new MST.Vertex[len];
        for(int i = 0; i < len; i++){ vertexes[i] = new MST.Vertex(names[i]); }
        WeightedGraph<MST.Vertex> res = new WeightedGraph<>();
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        for(int i = 0; i < len_; i++){
            putPairHelper(res, vertexes, indexes1[i], indexes2[i],weights[i]);
        }
        return res;
    }
    static void putPairHelper(WeightedGraph<MST.Vertex> res, MST.Vertex[] vertexes, int idx1, int idx2, int w){
        res.putNeighborPair(vertexes[idx1], vertexes[idx2], w);
    }
    static Set<WeightedGraph<MST.Vertex>.Edge> buildAnswer(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        MST.Vertex[] vertexes = new MST.Vertex[len];
        WeightedGraph<MST.Vertex> g = new WeightedGraph<>();
        for(int i = 0; i < len; i++){ vertexes[i] = new MST.Vertex(names[i]); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        int[] weights =  new int[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        Set<WeightedGraph<MST.Vertex>.Edge> res = new HashSet<>();
        int[] answers = new int[]{0, 2, 3, 5, 6, 7, 9, 12};
        for(var i : answers)
            res.add(g.new Edge(vertexes[indexes1[i]], vertexes[indexes2[i]], weights[i]));
        return res;
    }

    @Test
    public void algorithmKruskalTest(){
        var t = MST.algorithmKruskal(buildExample());
        assertEquals(t, buildAnswer());
    }
}