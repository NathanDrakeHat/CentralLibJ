package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;

public class PrimVert<V>{
  @NotNull
  private final V id;
  PrimVert<V> parent;
  double key = 0;

  PrimVert(@NotNull V name){
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
