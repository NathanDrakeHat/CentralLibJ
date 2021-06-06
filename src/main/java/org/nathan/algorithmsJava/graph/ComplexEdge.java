package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;

public class ComplexEdge<V extends Vertex<?>>{
  @NotNull
  private final V former_vertex;
  @NotNull
  private final V later_vertex;
  private final boolean directed;
  double weight;

  ComplexEdge(@NotNull V former, @NotNull V later, double weight, boolean is_directed){
    this.weight = weight;
    former_vertex = former;
    later_vertex = later;
    this.directed = is_directed;
  }

  public V former(){
    return former_vertex;
  }

  public V later(){
    return later_vertex;
  }

  public boolean directed(){
    return directed;
  }

  public double weight(){
    return this.weight;
  }

  public V another(V vertex){
    if(vertex.equals(former_vertex)){
      return later_vertex;
    }
    else if(vertex.equals(later_vertex)){
      return former_vertex;
    }
    else{
      throw new IllegalArgumentException();
    }
  }

  @Override
  public String toString(){
    if(directed){
      return String.format("[Edge(%s >>> %s)], weight:%f", former_vertex, later_vertex, weight);
    }
    else{
      return String.format("[Edge(%s <-> %s)], weight:%f", former_vertex, later_vertex, weight);
    }
  }
}
