package org.nathan.algorithmsJ.graph;

public interface DirectedEdge<V extends Vertex<?>> extends Edge<V>{
  boolean directed();
}
