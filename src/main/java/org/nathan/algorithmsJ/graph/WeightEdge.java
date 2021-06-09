package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;

public class WeightEdge<V> extends BaseEdge<V>{

  double weight;

  public WeightEdge(@NotNull V f, @NotNull V l, double weight){
    super(f, l);
    this.weight = weight;
  }

  double weight(){
    return weight;
  }
}
