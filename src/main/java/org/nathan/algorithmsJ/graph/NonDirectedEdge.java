package org.nathan.algorithmsJ.graph;

public interface NonDirectedEdge<V extends Vertex<?>> extends Edge<V>{
  boolean directed();
}
