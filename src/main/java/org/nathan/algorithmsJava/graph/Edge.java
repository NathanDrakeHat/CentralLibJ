package org.nathan.algorithmsJava.graph;

public interface Edge<V extends Vertex<?>>{

  V former();

  V later();

  V another(V vertex);
}
