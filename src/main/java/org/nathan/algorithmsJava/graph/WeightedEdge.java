package org.nathan.algorithmsJava.graph;

public interface WeightedEdge<V extends Vertex<?>> extends Edge<V>{
  double weight();
}
