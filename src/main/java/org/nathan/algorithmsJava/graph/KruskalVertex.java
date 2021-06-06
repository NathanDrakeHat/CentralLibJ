package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;
import org.nathan.algorithmsJava.structures.DisjointSet;

public class KruskalVertex<V> extends DisjointSet implements Vertex<V>{
  @NotNull
  private final V id;

  KruskalVertex(@NotNull V n){
    id = n;
  }

  @SuppressWarnings("unused")
  public @NotNull V getId(){
    return id;
  }

  @Override
  public String toString(){
    return String.format("KruskalVertex: %s", id);
  }

}
