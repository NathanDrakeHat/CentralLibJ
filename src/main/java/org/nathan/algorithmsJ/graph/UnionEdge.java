package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;

public class UnionEdge<V extends Vertex<?>>{
  @NotNull
  private final V former_vertex;
  @NotNull
  private final V later_vertex;
  double weight;

  UnionEdge(@NotNull V former, @NotNull V later, double weight){
    this.weight = weight;
    former_vertex = former;
    later_vertex = later;
  }

  public V former(){
    return former_vertex;
  }

  public V later(){
    return later_vertex;
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
}
