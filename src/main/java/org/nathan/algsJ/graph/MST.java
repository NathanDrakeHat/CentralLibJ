package org.nathan.algsJ.graph;

import org.jetbrains.annotations.NotNull;
import org.nathan.algsJ.dataStruc.DisjointSet;
import org.nathan.algsJ.dataStruc.ExtremumHeap;
import org.nathan.algsJ.dataStruc.FibonacciMinHeap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * minimum spanning tree
 */
public final class MST{
  public static <T>
  @NotNull Set<WeightEdge<VertKruskal<T>>> Kruskal(@NotNull LinkedGraph<VertKruskal<T>, WeightEdge<VertKruskal<T>>> graph){
    if(graph.directed){
      throw new IllegalArgumentException();
    }
    Set<WeightEdge<VertKruskal<T>>> res = new HashSet<>();
    var edges_set = graph.getAllEdges();
    var edges_list = new ArrayList<>(edges_set);
    edges_list.sort(Comparator.comparingDouble(WeightEdge::weight));
    for(var edge : edges_list){
      var v1 = edge.from();
      var v2 = edge.to();
      if(v1.setId.findGroupId() != v2.setId.findGroupId()){
        res.add(edge);
        v1.setId.union(v2.setId);
      }
    }
    return res;
  }

  public static <T> void MSTPrimFibonacciHeap(
          @NotNull LinkedGraph<VertPrim<T>, WeightEdge<VertPrim<T>>> graph,
          @NotNull MST.VertPrim<T> r){
    if(graph.directed){
      throw new IllegalArgumentException();
    }
    FibonacciMinHeap<Double, VertPrim<T>> Q = new FibonacciMinHeap<>(Comparator.comparingDouble(a -> a));
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
      var u_edges = graph.adjacentEdgesOf(u);
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

  public static <T> void MSTPrimMinHeap(@NotNull LinkedGraph<VertPrim<T>, WeightEdge<VertPrim<T>>> graph,
                                        @NotNull MST.VertPrim<T> r){
    if(graph.directed){
      throw new IllegalArgumentException();
    }
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
    ExtremumHeap<Double, VertPrim<T>> Q = new ExtremumHeap<>(true, vertices, VertPrim::getKey, Double::compare);
    while(Q.length() > 0) {
      var u = Q.extractExtremum().second();
      var u_edges = graph.adjacentEdgesOf(u);
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

  public static class VertKruskal<Id>{
    @NotNull
    private final Id identity;
    final DisjointSet setId = new DisjointSet();

    VertKruskal(@NotNull Id n){
      identity = n;
    }

    @SuppressWarnings("unused")
    public @NotNull Id identity(){
      return identity;
    }

    @Override
    public String toString(){
      return String.format("KruskalVertex: %s", identity);
    }

  }

  public static class VertPrim<Id> extends BaseVert<Id>{
    VertPrim<Id> parent;
    double key = 0;

    VertPrim(@NotNull Id name){
      super(name);
    }

    public double getKey(){
      return key;
    }

    @Override
    public String toString(){
      return String.format("PrimVertex: (%s)", identity);
    }
  }
}