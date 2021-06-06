package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;

public class PrimVertex<V> implements Vertex<V>{
  @NotNull
  private final V id;
  PrimVertex<V> parent;
  double key = 0;

  PrimVertex(@NotNull V name){
    this.id = name;
  }

  public @NotNull V getId(){
    return id;
  }

  public double getKey(){
    return key;
  }

  @Override
  public String toString(){
    return String.format("PrimVertex: (%s)", id);
  }
}
