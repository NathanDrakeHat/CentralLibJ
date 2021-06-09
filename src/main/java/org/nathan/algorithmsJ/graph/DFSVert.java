package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DFSVert<V> implements Vertex<V>{
  @NotNull
  final V id;
  @Nullable
  DFSVert<V> parent;
  int discover; //d
  int finish; // f
  DFS.COLOR color;

  DFSVert(@NotNull V name){
    this.id = name;
  }

  public @NotNull V getId(){
    return id;
  }

  public @Nullable DFSVert<V> getParent(){
    return parent;
  }

  @Override
  public String toString(){
    return String.format("DFS.Vertex: (%s)", id);
  }
}
