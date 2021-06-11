package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;
import org.nathan.algorithmsJ.structures.DisjointSet;
import org.nathan.algorithmsJ.structures.FibonacciHeap;
import org.nathan.algorithmsJ.structures.ExtremumHeap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * minimum spanning tree
 */
public final class MST{
  public static <T>
  @NotNull Set<WeightEdge<VertKruskal<T>>> Kruskal(@NotNull LinkGraph<VertKruskal<T>, WeightEdge<VertKruskal<T>>> graph){
    Set<WeightEdge<VertKruskal<T>>> res = new HashSet<>();
    var edges_set = graph.getAllEdges();
    var edges_list = new ArrayList<>(edges_set);
    edges_list.sort(Comparator.comparingDouble(WeightEdge::weight));
    for(var edge : edges_list){
      var v1 = edge.from();
      var v2 = edge.to();
      if(v1.findGroupId() != v2.findGroupId()){
        res.add(edge);
        v1.union(v2);
      }
    }
    return res;
  }

  public static <T> void MSTPrimFibonacciHeap(
          @NotNull LinkGraph<VertPrim<T>, WeightEdge<VertPrim<T>>> graph,
          @NotNull MST.VertPrim<T> r){
    FibonacciHeap<Double, VertPrim<T>> Q = new FibonacciHeap<>(Comparator.comparingDouble(a -> a));
    var vertices = graph.allVertices();
    for(var u : vertices){
      if(u != r){
        u.key = Double.POSITIVE_INFINITY;
      }
      else{
        u.key = 0.0;
      }
      Q.insert(u.key, u);
      u.parent = null;
    }
    while(Q.count() > 0) {
      var u = Q.extractMin();
      var u_edges = graph.edgesAt(u);
      for(var edge : u_edges){
        var v = edge.another(u);
        if(Q.contains(v) && edge.weight() < v.key){
          v.parent = u;
          v.key = edge.weight();
          Q.decreaseKey(v, v.key);
        }
      }
    }
  }

  public static <T> void MSTPrimMinHeap(@NotNull LinkGraph<VertPrim<T>, WeightEdge<VertPrim<T>>> graph,
                                        @NotNull MST.VertPrim<T> r){
    var vertices = graph.allVertices();
    for(var u : vertices){
      if(u != r){
        u.key = Double.POSITIVE_INFINITY;
      }
      else{
        u.key = 0.0;
      }
      u.parent = null;
    }
    ExtremumHeap<Double, VertPrim<T>> Q = new ExtremumHeap<>(true,vertices, VertPrim::getKey, Double::compare);
    while(Q.length() > 0) {
      var u = Q.extractExtremum();
      var u_edges = graph.edgesAt(u);
      for(var edge : u_edges){
        var v = edge.another(u);
        if(Q.contains(v) && edge.weight() < v.key){
          v.parent = u;
          v.key = edge.weight();
          Q.updateKey(v, v.key);
        }
      }
    }
  }

  public static class VertKruskal<V> extends DisjointSet{
    @NotNull
    private final V id;

    VertKruskal(@NotNull V n){
      id = n;
    }

    @SuppressWarnings("unused")
    public @NotNull V getId(){
      return id;
    }

    @Override
    public String toString(){
      return String.format("KruskalVertex: %s", id);
    }

  }

  public static class VertPrim<V>{
    @NotNull
    private final V id;
    VertPrim<V> parent;
    double key = 0;

    VertPrim(@NotNull V name){
      this.id = name;
    }

    public @NotNull V getId(){
      return id;
    }

    public double getKey(){
      return key;
    }

    @Override
    public String toString(){
      return String.format("PrimVertex: (%s)", id);
    }
  }
}