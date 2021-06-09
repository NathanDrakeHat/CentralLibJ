package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;

public class UnionEdge<V>{
  @NotNull
  private final V former_vertex;
  @NotNull
  private final V latter_vertex;
  double weight;

  UnionEdge(@NotNull V former, @NotNull V later, double weight){
    this.weight = weight;
    former_vertex = former;
    latter_vertex = later;
  }

  public V former(){
    return former_vertex;
  }

  public V latter(){
    return latter_vertex;
  }


  public double weight(){
    return this.weight;
  }

  public V another(V vertex){
    if(vertex.equals(former_vertex)){
      return latter_vertex;
    }
    else if(vertex.equals(latter_vertex)){
      return former_vertex;
    }
    else{
      throw new IllegalArgumentException();
    }
  }
}
