package org.nathan.algorithmsJava.graph;

public interface NonDirectedEdge<V extends Vertex<?>> extends Edge<V>{
  boolean directed();
}
