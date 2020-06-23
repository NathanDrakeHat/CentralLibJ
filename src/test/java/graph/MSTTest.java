package graph;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MSTTest {
    @Test
    public void algorithmOfKruskalTest(){
        var G = buildKruskalExample();
        var t = MST.algorithmOfKruskal(G);
        assertTrue(t.equals(buildKruskalAnswer1()) || t.equals(buildKruskalAnswer2()));
        int i = 0;
        for(var e : t){
            i += e.getWeight();
        }
        assertEquals(37,i );
    }
    static Graph<MST.KruskalVertex<String>> buildKruskalExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.KruskalVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i, new MST.KruskalVertex<>(names[i])); }
        Graph<MST.KruskalVertex<String>> res = new Graph<>(vertices, Graph.Direction.NON_DIRECTED);
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        double[] weights =  new double[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indexes1.length;
        for(int i = 0; i < len_; i++){
            res.setNeighbor(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i]);
        }
        return res;
    }
    static Set<Graph.Edge<MST.KruskalVertex<String>>> buildKruskalAnswer1(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.KruskalVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i,new MST.KruskalVertex<>(names[i])); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        double[] weights =  new double[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        Set<Graph.Edge<MST.KruskalVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 2, 3, 5, 6, 7, 9, 12};
        for(var i : answers)
            res.add(new Graph.Edge<>(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i], Graph.Direction.NON_DIRECTED));
        return res;
    }
    static Set<Graph.Edge<MST.KruskalVertex<String>>> buildKruskalAnswer2(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.KruskalVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i,new MST.KruskalVertex<>(names[i])); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        double[] weights =  new double[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        Set<Graph.Edge<MST.KruskalVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 1, 2, 3, 5, 6, 9, 12};
        for(var i : answers)
            res.add(new Graph.Edge<>(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i], Graph.Direction.NON_DIRECTED));
        return res;
    }


    @Test
    public void algorithmOfPrimTestWithFibonacciHeap(){
        var graph = buildPrimExample();
        MST.algorithmOfPrimWithFibonacciHeap(graph, new MST.PrimVertex<>("a"));
        var vertices = graph.GetAllVertices();
        Set<Set<MST.PrimVertex<String>>> res = new HashSet<>();
        for(var vertex : vertices){
            if(vertex.parent != null){
                Set<MST.PrimVertex<String>> t = new HashSet<>();
                t.add(vertex);
                t.add(vertex.parent);
                res.add(t);
            }
        }
        assertTrue(res.equals(buildPrimAnswer1()) || res.equals(buildPrimAnswer2()));
    }

    @Test
    public void algorithmOfPrimTestWithMinHeap(){
        var graph = buildPrimExample();
        MST.algorithmOfPrimWithMinHeap(graph, new MST.PrimVertex<>("a"));
        var vertices = graph.GetAllVertices();
        Set<Set<MST.PrimVertex<String>>> res = new HashSet<>();
        for(var vertex : vertices){
            if(vertex.parent != null){
                Set<MST.PrimVertex<String>> t = new HashSet<>();
                t.add(vertex);
                t.add(vertex.parent);
                res.add(t);
            }
        }
        assertTrue(res.equals(buildPrimAnswer1()) || res.equals(buildPrimAnswer2()));
    }
    static Graph<MST.PrimVertex<String>> buildPrimExample(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.PrimVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i,new MST.PrimVertex<>(names[i])); }
        Graph<MST.PrimVertex<String>> res = new Graph<>(vertices, Graph.Direction.NON_DIRECTED);
        int[] indices1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indices2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        double[] weights =  new double[]{4,8,7,9,10,2,1,8,11,2,7,6,4,14};
        int len_ = indices1.length;
        for(int i = 0; i < len_; i++){
            res.setNeighbor(vertices.get(indices1[i]), vertices.get(indices2[i]), weights[i]);
        }
        return res;
    }
    static Set<Set<MST.PrimVertex<String>>> buildPrimAnswer1(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.PrimVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i,new MST.PrimVertex<>(names[i])); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        Set<Set<MST.PrimVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 1, 2, 3, 5, 6, 9, 12};
        for (int answer : answers) {
            Set<MST.PrimVertex<String>> t = new HashSet<>();
            t.add(vertices.get(indexes1[answer]));
            t.add(vertices.get(indexes2[answer]));
            res.add(t);
        }
        return res;
    }
    static Set<Set<MST.PrimVertex<String>>> buildPrimAnswer2(){
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<MST.PrimVertex<String>>(len);
        for(int i = 0; i < len; i++){ vertices.add(i,new MST.PrimVertex<>(names[i])); }
        int[] indexes1 = new int[]{0,1,2,3,4, 5,6,7,1, 2,8,8,2,3};
        int[] indexes2 = new int[]{1,2,3,4,5, 6,7,0,7, 8,7,6,5,5};
        Set<Set<MST.PrimVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 7, 2, 3, 5, 6, 9, 12};
        for (int answer : answers) {
            Set<MST.PrimVertex<String>> t = new HashSet<>();
            t.add(vertices.get(indexes1[answer]));
            t.add(vertices.get(indexes2[answer]));
            res.add(t);
        }
        return res;
    }

}