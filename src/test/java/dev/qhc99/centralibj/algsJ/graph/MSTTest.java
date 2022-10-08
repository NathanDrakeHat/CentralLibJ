package dev.qhc99.centralibj.algsJ.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MSTTest{

  LinkedGraph<MST.VertKruskal<String>, WeightEdge<MST.VertKruskal<String>>> buildKruskalExample(){
    String n = "a,b,c,d,e,f,g,h,i";
    String[] names = n.split(",");
    int len = names.length;
    var vertices = new ArrayList<MST.VertKruskal<String>>(len);
    for(int i = 0; i < len; i++){
      vertices.add(i, new MST.VertKruskal<>(names[i]));
    }
    LinkedGraph<MST.VertKruskal<String>, WeightEdge<MST.VertKruskal<String>>> res =
            new LinkedGraph<>(false, vertices);
    int[] indexes1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
    int[] indexes2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
    double[] weights = new double[]{4, 8, 7, 9, 10, 2, 1, 8, 11, 2, 7, 6, 4, 14};
    int len_ = indexes1.length;
    for(int i = 0; i < len_; i++){
      res.addEdge(new WeightEdge<>(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i]));
    }
    return res;
  }

  LinkedGraph<MST.VertKruskal<String>, WeightEdge<MST.VertKruskal<String>>> KruskalGraph = buildKruskalExample();

  @Test
  public void KruskalTest(){
    var t = MST.Kruskal(KruskalGraph);
    int i = 0;
    for(var e : t){
      i += e.weight();
    }
    assertEquals(37, i);
  }


  Set<Set<String>> primAnswer1 = buildPrimAnswer1();
  Set<Set<String>> primAnswer2 = buildPrimAnswer2();


  GraphAndTarget primFibTarget = buildPrimExample();

  @Test
  public void MSTPrimFibTest(){
    var graph = primFibTarget.graph;
    var target = primFibTarget.target;
    MST.MSTPrimFibonacciHeap(graph, target);
    var vertices = graph.allVertices();
    Set<Set<String>> res = new HashSet<>();
    for(var vertex : vertices){
      if(vertex.parent != null){
        Set<String> t = new HashSet<>();
        t.add(vertex.identity());
        t.add(vertex.parent.identity());
        res.add(t);
      }
    }
    assertTrue(res.equals(primAnswer1) || res.equals(primAnswer2));
  }

  GraphAndTarget primMinHeap = buildPrimExample();

  @Test
  public void MSTPrimMinHeapTest(){
    var graph = primMinHeap.graph;
    var target = primMinHeap.target;

    MST.MSTPrimMinHeap(graph, target);
    var vertices = graph.allVertices();
    Set<Set<String>> res = new HashSet<>();
    for(var vertex : vertices){
      if(vertex.parent != null){
        Set<String> t = new HashSet<>();
        t.add(vertex.identity());
        t.add(vertex.parent.identity());
        res.add(t);
      }
    }
    assertTrue(res.equals(primAnswer1) || res.equals(primAnswer2));
  }

  GraphAndTarget buildPrimExample(){
    String n = "a,b,c,d,e,f,g,h,i";
    String[] names = n.split(",");
    int len = names.length;
    var vertices = new ArrayList<MST.VertPrim<String>>(len);
    for(int i = 0; i < len; i++){
      vertices.add(i, new MST.VertPrim<>(names[i]));
    }
    LinkedGraph<MST.VertPrim<String>, WeightEdge<MST.VertPrim<String>>> res = new LinkedGraph<>(false, vertices);
    int[] indices1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
    int[] indices2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
    double[] weights = new double[]{4, 8, 7, 9, 10, 2, 1, 8, 11, 2, 7, 6, 4, 14};
    int len_ = indices1.length;
    for(int i = 0; i < len_; i++){
      res.addEdge(new WeightEdge<>(vertices.get(indices1[i]), vertices.get(indices2[i]), weights[i]));
    }

    return new GraphAndTarget(res, vertices.get(0));
  }

  Set<Set<String>> buildPrimAnswer1(){
    String n = "a,b,c,d,e,f,g,h,i";
    String[] names = n.split(",");
    int[] indexes1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
    int[] indexes2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
    Set<Set<String>> res = new HashSet<>();
    int[] answers = new int[]{0, 1, 2, 3, 5, 6, 9, 12};
    for(int answer : answers){
      Set<String> t = new HashSet<>();
      t.add(names[indexes1[answer]]);
      t.add(names[indexes2[answer]]);
      res.add(t);
    }
    return res;
  }

  Set<Set<String>> buildPrimAnswer2(){
    String n = "a,b,c,d,e,f,g,h,i";
    String[] names = n.split(",");
    int[] indexes1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
    int[] indexes2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
    Set<Set<String>> res = new HashSet<>();
    int[] answers = new int[]{0, 7, 2, 3, 5, 6, 9, 12};
    for(int answer : answers){
      Set<String> t = new HashSet<>();
      t.add(names[indexes1[answer]]);
      t.add(names[indexes2[answer]]);
      res.add(t);
    }
    return res;
  }

  static class GraphAndTarget{
    LinkedGraph<MST.VertPrim<String>, WeightEdge<MST.VertPrim<String>>> graph;
    MST.VertPrim<String> target;

    public GraphAndTarget(LinkedGraph<MST.VertPrim<String>, WeightEdge<MST.VertPrim<String>>> graph, MST.VertPrim<String> target){
      this.graph = graph;
      this.target = target;
    }
  }

}