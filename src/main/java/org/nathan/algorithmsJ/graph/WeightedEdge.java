package org.nathan.algorithmsJ.graph;

public interface WeightedEdge<V extends Vertex<?>> extends Edge<V>{
  double weight();
}
