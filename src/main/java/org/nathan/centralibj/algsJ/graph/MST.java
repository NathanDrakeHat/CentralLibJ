package org.nathan.centralibj.algsJ.graph;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralibj.algsJ.dataStruc.DisjointSet;
import org.nathan.centralibj.algsJ.dataStruc.DynamicPriQueue;
import org.nathan.centralibj.algsJ.dataStruc.FibonacciMinHeap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * minimum spanning tree
 */
public final class MST{
  public static <ID, V extends VertKruskal<ID>, E extends WeightEdge<V>>
  @NotNull Set<E> Kruskal(@NotNull LinkedGraph<V, E> graph){
    if(graph.directed){
      throw new IllegalArgumentException();
    }
    Set<E> res = new HashSet<>();
    var edges_set = graph.getAllEdges();
    var edges_list = new ArrayList<>(edges_set);
    edges_list.sort(Comparator.comparingDouble(WeightEdge::weight));
    for(var edge : edges_list){
      var v1 = edge.from();
      var v2 = edge.to();
      if(!DisjointSet.inSameSet(v1.setId, v2.setId)){
        res.add(edge);
        DisjointSet.union(v1.setId, v2.setId);
      }
    }
    return res;
  }

  public static <ID, V extends VertPrim<ID>, E extends WeightEdge<V>>
  void MSTPrimFibonacciHeap(@NotNull LinkedGraph<V, E> graph, @NotNull V r){
    if(graph.directed){
      throw new IllegalArgumentException();
    }
    FibonacciMinHeap<Double, V> Q = FibonacciMinHeap.ofDouble();
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

  public static <ID, V extends VertPrim<ID>, E extends WeightEdge<V>>
  void MSTPrimMinHeap(@NotNull LinkedGraph<V, E> graph, @NotNull V r){
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
    DynamicPriQueue<Double, V> Q = new DynamicPriQueue<>(vertices, VertPrim::getKey, Double::compare);
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