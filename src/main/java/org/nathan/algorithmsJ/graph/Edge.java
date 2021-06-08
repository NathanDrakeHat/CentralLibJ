package org.nathan.algorithmsJ.graph;

public interface Edge<V extends Vertex<?>>{

  V former();

  V later();

  V another(V vertex);
}
