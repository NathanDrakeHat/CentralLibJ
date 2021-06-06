package org.nathan.algorithmsJava.graph;

public interface DirectedEdge<V extends Vertex<?>> extends Edge<V>{
  boolean directed();
}
